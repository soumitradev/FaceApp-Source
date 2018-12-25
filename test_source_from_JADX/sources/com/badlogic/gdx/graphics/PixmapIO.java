package com.badlogic.gdx.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import name.antonsmirnov.firmata.FormatHelper;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class PixmapIO {

    private static class CIM {
        private static final int BUFFER_SIZE = 32000;
        private static final byte[] readBuffer = new byte[BUFFER_SIZE];
        private static final byte[] writeBuffer = new byte[BUFFER_SIZE];

        private CIM() {
        }

        public static void write(FileHandle file, Pixmap pixmap) {
            DataOutputStream out = null;
            try {
                out = new DataOutputStream(new DeflaterOutputStream(file.write(false)));
                out.writeInt(pixmap.getWidth());
                out.writeInt(pixmap.getHeight());
                out.writeInt(Pixmap$Format.toGdx2DPixmapFormat(pixmap.getFormat()));
                ByteBuffer pixelBuf = pixmap.getPixels();
                pixelBuf.position(0);
                pixelBuf.limit(pixelBuf.capacity());
                int remainingBytes = pixelBuf.capacity() % BUFFER_SIZE;
                int iterations = pixelBuf.capacity() / BUFFER_SIZE;
                synchronized (writeBuffer) {
                    for (int i = 0; i < iterations; i++) {
                        pixelBuf.get(writeBuffer);
                        out.write(writeBuffer);
                    }
                    pixelBuf.get(writeBuffer, 0, remainingBytes);
                    out.write(writeBuffer, 0, remainingBytes);
                }
                pixelBuf.position(0);
                pixelBuf.limit(pixelBuf.capacity());
                StreamUtils.closeQuietly(out);
            } catch (Exception e) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't write Pixmap to file '");
                    stringBuilder.append(file);
                    stringBuilder.append(FormatHelper.QUOTE);
                    throw new GdxRuntimeException(stringBuilder.toString(), e);
                } catch (Throwable th) {
                    StreamUtils.closeQuietly(out);
                }
            }
        }

        public static Pixmap read(FileHandle file) {
            DataInputStream in = null;
            try {
                in = new DataInputStream(new InflaterInputStream(new BufferedInputStream(file.read())));
                Pixmap pixmap = new Pixmap(in.readInt(), in.readInt(), Pixmap$Format.fromGdx2DPixmapFormat(in.readInt()));
                ByteBuffer pixelBuf = pixmap.getPixels();
                pixelBuf.position(0);
                pixelBuf.limit(pixelBuf.capacity());
                synchronized (readBuffer) {
                    while (true) {
                        int read = in.read(readBuffer);
                        int readBytes = read;
                        if (read > 0) {
                            pixelBuf.put(readBuffer, 0, readBytes);
                        }
                    }
                }
                pixelBuf.position(0);
                pixelBuf.limit(pixelBuf.capacity());
                StreamUtils.closeQuietly(in);
                return pixmap;
            } catch (Exception e) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't read Pixmap from file '");
                    stringBuilder.append(file);
                    stringBuilder.append(FormatHelper.QUOTE);
                    throw new GdxRuntimeException(stringBuilder.toString(), e);
                } catch (Throwable th) {
                    StreamUtils.closeQuietly(in);
                }
            }
        }
    }

    public static class PNG implements Disposable {
        private static final byte COLOR_ARGB = (byte) 6;
        private static final byte COMPRESSION_DEFLATE = (byte) 0;
        private static final byte FILTER_NONE = (byte) 0;
        private static final int IDAT = 1229209940;
        private static final int IEND = 1229278788;
        private static final int IHDR = 1229472850;
        private static final byte INTERLACE_NONE = (byte) 0;
        private static final byte PAETH = (byte) 4;
        private static final byte[] SIGNATURE = new byte[]{(byte) -119, GeneralMidiConstants.LEAD_0_SQUARE, GeneralMidiConstants.WHISTLE, GeneralMidiConstants.CLARINET, (byte) 13, (byte) 10, (byte) 26, (byte) 10};
        private final ChunkBuffer buffer;
        private ByteArray curLineBytes;
        private final Deflater deflater;
        private final DeflaterOutputStream deflaterOutput;
        private boolean flipY;
        private int lastLineLen;
        private ByteArray lineOutBytes;
        private ByteArray prevLineBytes;

        static class ChunkBuffer extends DataOutputStream {
            final ByteArrayOutputStream buffer;
            final CRC32 crc;

            ChunkBuffer(int initialSize) {
                this(new ByteArrayOutputStream(initialSize), new CRC32());
            }

            private ChunkBuffer(ByteArrayOutputStream buffer, CRC32 crc) {
                super(new CheckedOutputStream(buffer, crc));
                this.buffer = buffer;
                this.crc = crc;
            }

            public void endChunk(DataOutputStream target) throws IOException {
                flush();
                target.writeInt(this.buffer.size() - 4);
                this.buffer.writeTo(target);
                target.writeInt((int) this.crc.getValue());
                this.buffer.reset();
                this.crc.reset();
            }
        }

        public PNG() {
            this(16384);
        }

        public PNG(int initialBufferSize) {
            this.flipY = true;
            this.buffer = new ChunkBuffer(initialBufferSize);
            this.deflater = new Deflater();
            this.deflaterOutput = new DeflaterOutputStream(this.buffer, this.deflater);
        }

        public void setFlipY(boolean flipY) {
            this.flipY = flipY;
        }

        public void setCompression(int level) {
            this.deflater.setLevel(level);
        }

        public void write(FileHandle file, Pixmap pixmap) throws IOException {
            OutputStream output = file.write(null);
            try {
                write(output, pixmap);
            } finally {
                StreamUtils.closeQuietly(output);
            }
        }

        public void write(OutputStream output, Pixmap pixmap) throws IOException {
            byte[] lineOut;
            byte[] curLine;
            byte[] prevLine;
            int n;
            DataOutputStream dataOutput = new DataOutputStream(output);
            dataOutput.write(SIGNATURE);
            this.buffer.writeInt(IHDR);
            this.buffer.writeInt(pixmap.getWidth());
            this.buffer.writeInt(pixmap.getHeight());
            this.buffer.writeByte(8);
            this.buffer.writeByte(6);
            int i = 0;
            this.buffer.writeByte(0);
            this.buffer.writeByte(0);
            this.buffer.writeByte(0);
            this.buffer.endChunk(dataOutput);
            this.buffer.writeInt(IDAT);
            this.deflater.reset();
            int lineLen = pixmap.getWidth() * 4;
            if (this.lineOutBytes == null) {
                lineOut = new ByteArray(lineLen);
                r0.lineOutBytes = lineOut;
                lineOut = lineOut.items;
                curLine = new ByteArray(lineLen);
                r0.curLineBytes = curLine;
                curLine = curLine.items;
                prevLine = new ByteArray(lineLen);
                r0.prevLineBytes = prevLine;
                prevLine = prevLine.items;
            } else {
                lineOut = r0.lineOutBytes.ensureCapacity(lineLen);
                curLine = r0.curLineBytes.ensureCapacity(lineLen);
                prevLine = r0.prevLineBytes.ensureCapacity(lineLen);
                n = r0.lastLineLen;
                for (int i2 = 0; i2 < n; i2++) {
                    prevLine[i2] = (byte) 0;
                }
            }
            r0.lastLineLen = lineLen;
            ByteBuffer pixels = pixmap.getPixels();
            n = pixels.position();
            int i3 = 1;
            boolean rgba8888 = pixmap.getFormat() == Pixmap$Format.RGBA8888;
            int y = 0;
            int h = pixmap.getHeight();
            while (y < h) {
                boolean rgba88882;
                int h2;
                int x;
                OutputStream outputStream;
                int py = r0.flipY ? (h - y) - i3 : y;
                if (rgba8888) {
                    pixels.position(py * lineLen);
                    pixels.get(curLine, i, lineLen);
                    rgba88882 = rgba8888;
                    h2 = h;
                } else {
                    i3 = 0;
                    x = 0;
                    while (i3 < pixmap.getWidth()) {
                        rgba88882 = rgba8888;
                        rgba8888 = pixmap.getPixel(i3, py);
                        int x2 = x + 1;
                        curLine[x] = (byte) ((rgba8888 >> 24) & 255);
                        i = x2 + 1;
                        curLine[x2] = (byte) ((rgba8888 >> 16) & 255);
                        x = i + 1;
                        h2 = h;
                        curLine[i] = (byte) ((rgba8888 >> 8) & 255);
                        i = x + 1;
                        curLine[x] = (byte) (rgba8888 & 255);
                        i3++;
                        x = i;
                        rgba8888 = rgba88882;
                        h = h2;
                    }
                    rgba88882 = rgba8888;
                    h2 = h;
                }
                lineOut[0] = (byte) (curLine[0] - prevLine[0]);
                lineOut[1] = (byte) (curLine[1] - prevLine[1]);
                lineOut[2] = (byte) (curLine[2] - prevLine[2]);
                lineOut[3] = (byte) (curLine[3] - prevLine[3]);
                x = 4;
                while (x < lineLen) {
                    rgba8888 = curLine[x - 4] & 255;
                    i3 = prevLine[x] & 255;
                    h = prevLine[x - 4] & 255;
                    int p = (rgba8888 + i3) - h;
                    i = p - rgba8888;
                    if (i < 0) {
                        i = -i;
                    }
                    int py2 = py;
                    py = p - i3;
                    if (py < 0) {
                        py = -py;
                    }
                    int pc = p - h;
                    if (pc < 0) {
                        pc = -pc;
                    }
                    if (i <= py && i <= pc) {
                        h = rgba8888;
                    } else if (py <= pc) {
                        h = i3;
                    }
                    lineOut[x] = (byte) (curLine[x] - h);
                    x++;
                    py = py2;
                    outputStream = output;
                }
                r0.deflaterOutput.write(4);
                r0.deflaterOutput.write(lineOut, 0, lineLen);
                byte[] temp = curLine;
                curLine = prevLine;
                prevLine = temp;
                y++;
                rgba8888 = rgba88882;
                h = h2;
                outputStream = output;
                i = 0;
                i3 = 1;
            }
            pixels.position(n);
            r0.deflaterOutput.finish();
            r0.buffer.endChunk(dataOutput);
            r0.buffer.writeInt(IEND);
            r0.buffer.endChunk(dataOutput);
            output.flush();
        }

        public void dispose() {
            this.deflater.end();
        }
    }

    public static void writeCIM(FileHandle file, Pixmap pixmap) {
        CIM.write(file, pixmap);
    }

    public static Pixmap readCIM(FileHandle file) {
        return CIM.read(file);
    }

    public static void writePNG(FileHandle file, Pixmap pixmap) {
        PNG writer;
        try {
            writer = new PNG((int) (((float) (pixmap.getWidth() * pixmap.getHeight())) * 1.5f));
            writer.setFlipY(false);
            writer.write(file, pixmap);
            writer.dispose();
        } catch (IOException ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error writing PNG: ");
            stringBuilder.append(file);
            throw new GdxRuntimeException(stringBuilder.toString(), ex);
        } catch (Throwable th) {
            writer.dispose();
        }
    }
}
