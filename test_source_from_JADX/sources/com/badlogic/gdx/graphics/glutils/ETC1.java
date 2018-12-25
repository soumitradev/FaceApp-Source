package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import name.antonsmirnov.firmata.FormatHelper;

public class ETC1 {
    public static int ETC1_RGB8_OES = 36196;
    public static int PKM_HEADER_SIZE = 16;

    public static final class ETC1Data implements Disposable {
        public final ByteBuffer compressedData;
        public final int dataOffset;
        public final int height;
        public final int width;

        public ETC1Data(int width, int height, ByteBuffer compressedData, int dataOffset) {
            this.width = width;
            this.height = height;
            this.compressedData = compressedData;
            this.dataOffset = dataOffset;
            checkNPOT();
        }

        public ETC1Data(FileHandle pkmFile) {
            byte[] buffer = new byte[10240];
            try {
                DataInputStream in = new DataInputStream(new BufferedInputStream(new GZIPInputStream(pkmFile.read())));
                this.compressedData = BufferUtils.newUnsafeByteBuffer(in.readInt());
                while (true) {
                    int read = in.read(buffer);
                    int readBytes = read;
                    if (read != -1) {
                        this.compressedData.put(buffer, 0, readBytes);
                    } else {
                        this.compressedData.position(0);
                        this.compressedData.limit(this.compressedData.capacity());
                        StreamUtils.closeQuietly(in);
                        this.width = ETC1.getWidthPKM(this.compressedData, 0);
                        this.height = ETC1.getHeightPKM(this.compressedData, 0);
                        this.dataOffset = ETC1.PKM_HEADER_SIZE;
                        this.compressedData.position(this.dataOffset);
                        checkNPOT();
                        return;
                    }
                }
            } catch (Exception e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't load pkm file '");
                stringBuilder.append(pkmFile);
                stringBuilder.append(FormatHelper.QUOTE);
                throw new GdxRuntimeException(stringBuilder.toString(), e);
            } catch (Throwable th) {
                StreamUtils.closeQuietly(null);
            }
        }

        private void checkNPOT() {
            if (!MathUtils.isPowerOfTwo(this.width) || !MathUtils.isPowerOfTwo(this.height)) {
                Gdx.app.debug("ETC1Data", "warning: non-power-of-two ETC1 textures may crash the driver of PowerVR GPUs");
            }
        }

        public boolean hasPKMHeader() {
            return this.dataOffset == 16;
        }

        public void write(FileHandle file) {
            byte[] buffer = new byte[10240];
            int writtenBytes = 0;
            this.compressedData.position(0);
            this.compressedData.limit(this.compressedData.capacity());
            try {
                DataOutputStream write = new DataOutputStream(new GZIPOutputStream(file.write(false)));
                write.writeInt(this.compressedData.capacity());
                while (writtenBytes != this.compressedData.capacity()) {
                    int bytesToWrite = Math.min(this.compressedData.remaining(), buffer.length);
                    this.compressedData.get(buffer, 0, bytesToWrite);
                    write.write(buffer, 0, bytesToWrite);
                    writtenBytes += bytesToWrite;
                }
                StreamUtils.closeQuietly(write);
                this.compressedData.position(this.dataOffset);
                this.compressedData.limit(this.compressedData.capacity());
            } catch (Exception e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't write PKM file to '");
                stringBuilder.append(file);
                stringBuilder.append(FormatHelper.QUOTE);
                throw new GdxRuntimeException(stringBuilder.toString(), e);
            } catch (Throwable th) {
                StreamUtils.closeQuietly(null);
            }
        }

        public void dispose() {
            BufferUtils.disposeUnsafeByteBuffer(this.compressedData);
        }

        public String toString() {
            if (hasPKMHeader()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ETC1.isValidPKM(this.compressedData, 0) ? "valid" : "invalid");
                stringBuilder.append(" pkm [");
                stringBuilder.append(ETC1.getWidthPKM(this.compressedData, 0));
                stringBuilder.append("x");
                stringBuilder.append(ETC1.getHeightPKM(this.compressedData, 0));
                stringBuilder.append("], compressed: ");
                stringBuilder.append(this.compressedData.capacity() - ETC1.PKM_HEADER_SIZE);
                return stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("raw [");
            stringBuilder.append(this.width);
            stringBuilder.append("x");
            stringBuilder.append(this.height);
            stringBuilder.append("], compressed: ");
            stringBuilder.append(this.compressedData.capacity() - ETC1.PKM_HEADER_SIZE);
            return stringBuilder.toString();
        }
    }

    private static native void decodeImage(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3, int i4, int i5);

    private static native ByteBuffer encodeImage(ByteBuffer byteBuffer, int i, int i2, int i3, int i4);

    private static native ByteBuffer encodeImagePKM(ByteBuffer byteBuffer, int i, int i2, int i3, int i4);

    public static native void formatHeader(ByteBuffer byteBuffer, int i, int i2, int i3);

    public static native int getCompressedDataSize(int i, int i2);

    static native int getHeightPKM(ByteBuffer byteBuffer, int i);

    static native int getWidthPKM(ByteBuffer byteBuffer, int i);

    static native boolean isValidPKM(ByteBuffer byteBuffer, int i);

    private static int getPixelSize(Pixmap$Format format) {
        if (format == Pixmap$Format.RGB565) {
            return 2;
        }
        if (format == Pixmap$Format.RGB888) {
            return 3;
        }
        throw new GdxRuntimeException("Can only handle RGB565 or RGB888 images");
    }

    public static ETC1Data encodeImage(Pixmap pixmap) {
        ByteBuffer compressedData = encodeImage(pixmap.getPixels(), 0, pixmap.getWidth(), pixmap.getHeight(), getPixelSize(pixmap.getFormat()));
        BufferUtils.newUnsafeByteBuffer(compressedData);
        return new ETC1Data(pixmap.getWidth(), pixmap.getHeight(), compressedData, 0);
    }

    public static ETC1Data encodeImagePKM(Pixmap pixmap) {
        ByteBuffer compressedData = encodeImagePKM(pixmap.getPixels(), 0, pixmap.getWidth(), pixmap.getHeight(), getPixelSize(pixmap.getFormat()));
        BufferUtils.newUnsafeByteBuffer(compressedData);
        return new ETC1Data(pixmap.getWidth(), pixmap.getHeight(), compressedData, 16);
    }

    public static Pixmap decodeImage(ETC1Data etc1Data, Pixmap$Format format) {
        int dataOffset;
        int width;
        int height;
        if (etc1Data.hasPKMHeader()) {
            dataOffset = 16;
            width = getWidthPKM(etc1Data.compressedData, 0);
            height = getHeightPKM(etc1Data.compressedData, 0);
        } else {
            dataOffset = 0;
            width = etc1Data.width;
            height = etc1Data.height;
        }
        int pixelSize = getPixelSize(format);
        Pixmap pixmap = new Pixmap(width, height, format);
        decodeImage(etc1Data.compressedData, dataOffset, pixmap.getPixels(), 0, width, height, pixelSize);
        return pixmap;
    }
}
