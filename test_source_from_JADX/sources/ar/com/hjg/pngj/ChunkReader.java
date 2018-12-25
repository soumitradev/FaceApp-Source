package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.ChunkRaw;

public abstract class ChunkReader {
    private final ChunkRaw chunkRaw;
    private boolean crcCheck;
    private int crcn = 0;
    public final ChunkReaderMode mode;
    protected int read = 0;

    public enum ChunkReaderMode {
        BUFFER,
        PROCESS,
        SKIP
    }

    protected abstract void chunkDone();

    protected abstract void processData(int i, byte[] bArr, int i2, int i3);

    public ChunkReader(int clen, String id, long offsetInPng, ChunkReaderMode mode) {
        boolean z = false;
        if (mode != null && id.length() == 4) {
            if (clen >= 0) {
                this.mode = mode;
                this.chunkRaw = new ChunkRaw(clen, id, mode == ChunkReaderMode.BUFFER);
                this.chunkRaw.setOffset(offsetInPng);
                if (mode != ChunkReaderMode.SKIP) {
                    z = true;
                }
                this.crcCheck = z;
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad chunk paramenters: ");
        stringBuilder.append(mode);
        throw new PngjExceptionInternal(stringBuilder.toString());
    }

    public ChunkRaw getChunkRaw() {
        return this.chunkRaw;
    }

    public final int feedBytes(byte[] buf, int off, int len) {
        if (len == 0) {
            return 0;
        }
        if (len < 0) {
            throw new PngjException("negative length??");
        }
        if (this.read == 0 && this.crcn == 0 && this.crcCheck) {
            this.chunkRaw.updateCrc(this.chunkRaw.idbytes, 0, 4);
        }
        int bytesForData = this.chunkRaw.len - this.read;
        if (bytesForData > len) {
            bytesForData = len;
        }
        if (bytesForData > 0 || this.crcn == 0) {
            if (this.crcCheck && this.mode != ChunkReaderMode.BUFFER && bytesForData > 0) {
                this.chunkRaw.updateCrc(buf, off, bytesForData);
            }
            if (this.mode == ChunkReaderMode.BUFFER) {
                if (this.chunkRaw.data != buf && bytesForData > 0) {
                    System.arraycopy(buf, off, this.chunkRaw.data, this.read, bytesForData);
                }
            } else if (this.mode == ChunkReaderMode.PROCESS) {
                processData(this.read, buf, off, bytesForData);
            }
            this.read += bytesForData;
            off += bytesForData;
            len -= bytesForData;
        }
        int crcRead = 0;
        if (this.read == this.chunkRaw.len) {
            crcRead = 4 - this.crcn;
            if (crcRead > len) {
                crcRead = len;
            }
            if (crcRead > 0) {
                if (buf != this.chunkRaw.crcval) {
                    System.arraycopy(buf, off, this.chunkRaw.crcval, this.crcn, crcRead);
                }
                this.crcn += crcRead;
                if (this.crcn == 4) {
                    if (this.crcCheck) {
                        if (this.mode == ChunkReaderMode.BUFFER) {
                            this.chunkRaw.updateCrc(this.chunkRaw.data, 0, this.chunkRaw.len);
                        }
                        this.chunkRaw.checkCrc();
                    }
                    chunkDone();
                }
            }
        }
        return bytesForData + crcRead;
    }

    public final boolean isDone() {
        return this.crcn == 4;
    }

    public void setCrcCheck(boolean crcCheck) {
        if (this.read == 0 || !crcCheck || this.crcCheck) {
            this.crcCheck = crcCheck;
            return;
        }
        throw new PngjException("too late!");
    }

    public int hashCode() {
        return (1 * 31) + (this.chunkRaw == null ? 0 : this.chunkRaw.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChunkReader other = (ChunkReader) obj;
        if (this.chunkRaw == null) {
            if (other.chunkRaw != null) {
                return false;
            }
        } else if (!this.chunkRaw.equals(other.chunkRaw)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return this.chunkRaw.toString();
    }
}
