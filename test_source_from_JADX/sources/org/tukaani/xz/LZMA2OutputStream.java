package org.tukaani.xz;

import java.io.DataOutputStream;
import java.io.IOException;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import org.tukaani.xz.lz.LZEncoder;
import org.tukaani.xz.lzma.LZMAEncoder;
import org.tukaani.xz.rangecoder.RangeEncoder;

class LZMA2OutputStream extends FinishableOutputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int COMPRESSED_SIZE_MAX = 65536;
    static /* synthetic */ Class class$org$tukaani$xz$LZMA2OutputStream = class$("org.tukaani.xz.LZMA2OutputStream");
    private boolean dictResetNeeded = true;
    private IOException exception = null;
    private boolean finished = false;
    private final LZEncoder lz;
    private final LZMAEncoder lzma;
    private FinishableOutputStream out;
    private final DataOutputStream outData;
    private int pendingSize = 0;
    private final int props;
    private boolean propsNeeded = true;
    private final RangeEncoder rc;
    private boolean stateResetNeeded = true;
    private final byte[] tempBuf = new byte[1];

    static {
        if (class$org$tukaani$xz$LZMA2OutputStream == null) {
        } else {
            Class cls = class$org$tukaani$xz$LZMA2OutputStream;
        }
    }

    LZMA2OutputStream(FinishableOutputStream finishableOutputStream, LZMA2Options lZMA2Options) {
        if (finishableOutputStream == null) {
            throw new NullPointerException();
        }
        this.out = finishableOutputStream;
        this.outData = new DataOutputStream(finishableOutputStream);
        this.rc = new RangeEncoder(65536);
        int dictSize = lZMA2Options.getDictSize();
        int i = dictSize;
        this.lzma = LZMAEncoder.getInstance(this.rc, lZMA2Options.getLc(), lZMA2Options.getLp(), lZMA2Options.getPb(), lZMA2Options.getMode(), i, getExtraSizeBefore(dictSize), lZMA2Options.getNiceLen(), lZMA2Options.getMatchFinder(), lZMA2Options.getDepthLimit());
        this.lz = this.lzma.getLZEncoder();
        byte[] presetDict = lZMA2Options.getPresetDict();
        if (presetDict != null && presetDict.length > 0) {
            this.lz.setPresetDict(dictSize, presetDict);
            this.dictResetNeeded = false;
        }
        this.props = (((lZMA2Options.getPb() * 5) + lZMA2Options.getLp()) * 9) + lZMA2Options.getLc();
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static int getExtraSizeBefore(int i) {
        return 65536 > i ? 65536 - i : 0;
    }

    static int getMemoryUsage(LZMA2Options lZMA2Options) {
        int dictSize = lZMA2Options.getDictSize();
        return LZMAEncoder.getMemoryUsage(lZMA2Options.getMode(), dictSize, getExtraSizeBefore(dictSize), lZMA2Options.getMatchFinder()) + 70;
    }

    private void writeChunk() throws IOException {
        int finish = this.rc.finish();
        int uncompressedSize = this.lzma.getUncompressedSize();
        if (finish + 2 < uncompressedSize) {
            writeLZMA(uncompressedSize, finish);
        } else {
            this.lzma.reset();
            uncompressedSize = this.lzma.getUncompressedSize();
            writeUncompressed(uncompressedSize);
        }
        this.pendingSize -= uncompressedSize;
        this.lzma.resetUncompressedSize();
        this.rc.reset();
    }

    private void writeEndMarker() throws IOException {
        if (this.exception != null) {
            throw this.exception;
        }
        this.lz.setFinishing();
        while (this.pendingSize > 0) {
            try {
                this.lzma.encodeForLZMA2();
                writeChunk();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
        this.out.write(0);
        this.finished = true;
    }

    private void writeLZMA(int i, int i2) throws IOException {
        int i3 = this.propsNeeded ? this.dictResetNeeded ? AnalogMessageWriter.COMMAND : ReportAnalogPinMessageWriter.COMMAND : this.stateResetNeeded ? 160 : 128;
        i--;
        this.outData.writeByte(i3 | (i >>> 16));
        this.outData.writeShort(i);
        this.outData.writeShort(i2 - 1);
        if (this.propsNeeded) {
            this.outData.writeByte(this.props);
        }
        this.rc.write(this.out);
        this.propsNeeded = false;
        this.stateResetNeeded = false;
        this.dictResetNeeded = false;
    }

    private void writeUncompressed(int i) throws IOException {
        while (true) {
            int i2 = 1;
            if (i > 0) {
                int min = Math.min(i, 65536);
                DataOutputStream dataOutputStream = this.outData;
                if (!this.dictResetNeeded) {
                    i2 = 2;
                }
                dataOutputStream.writeByte(i2);
                this.outData.writeShort(min - 1);
                this.lz.copyUncompressed(this.out, i, min);
                i -= min;
                this.dictResetNeeded = false;
            } else {
                this.stateResetNeeded = true;
                return;
            }
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            if (!this.finished) {
                try {
                    writeEndMarker();
                } catch (IOException e) {
                }
            }
            try {
                this.out.close();
            } catch (IOException e2) {
                if (this.exception == null) {
                    this.exception = e2;
                }
            }
            this.out = null;
        }
        if (this.exception != null) {
            throw this.exception;
        }
    }

    public void finish() throws IOException {
        if (!this.finished) {
            writeEndMarker();
            try {
                this.out.finish();
                this.finished = true;
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void flush() throws IOException {
        if (this.exception != null) {
            throw this.exception;
        } else if (this.finished) {
            throw new XZIOException("Stream finished or closed");
        } else {
            try {
                this.lz.setFlushing();
                while (this.pendingSize > 0) {
                    this.lzma.encodeForLZMA2();
                    writeChunk();
                }
                this.out.flush();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void write(int i) throws IOException {
        this.tempBuf[0] = (byte) i;
        write(this.tempBuf, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i >= 0 && i2 >= 0) {
            int i3 = i + i2;
            if (i3 >= 0) {
                if (i3 <= bArr.length) {
                    if (this.exception != null) {
                        throw this.exception;
                    } else if (this.finished) {
                        throw new XZIOException("Stream finished or closed");
                    } else {
                        while (i2 > 0) {
                            try {
                                i3 = this.lz.fillWindow(bArr, i, i2);
                                i += i3;
                                i2 -= i3;
                                this.pendingSize += i3;
                                if (this.lzma.encodeForLZMA2()) {
                                    writeChunk();
                                }
                            } catch (IOException e) {
                                this.exception = e;
                                throw e;
                            }
                        }
                        return;
                    }
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
