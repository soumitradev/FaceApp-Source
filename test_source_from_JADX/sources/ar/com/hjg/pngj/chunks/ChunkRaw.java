package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjBadCrcException;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.PngjOutputException;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class ChunkRaw {
    private CRC32 crcengine;
    public byte[] crcval;
    public byte[] data;
    public final String id;
    public final byte[] idbytes;
    public final int len;
    private long offset;

    public ChunkRaw(int len, String id, boolean alloc) {
        this.data = null;
        this.offset = 0;
        this.crcval = new byte[4];
        this.len = len;
        this.id = id;
        this.idbytes = ChunkHelper.toBytes(id);
        int i = 0;
        while (i < 4) {
            if (this.idbytes[i] >= GeneralMidiConstants.ALTO_SAX && this.idbytes[i] <= GeneralMidiConstants.SEASHORE) {
                if (this.idbytes[i] <= GeneralMidiConstants.PAD_2_POLYSYNTH || this.idbytes[i] >= GeneralMidiConstants.FX_1_SOUNDTRACK) {
                    i++;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad id chunk: must be ascii letters ");
            stringBuilder.append(id);
            throw new PngjException(stringBuilder.toString());
        }
        if (alloc) {
            allocData();
        }
    }

    public ChunkRaw(int len, byte[] idbytes, boolean alloc) {
        this(len, ChunkHelper.toString(idbytes), alloc);
    }

    public void allocData() {
        if (this.data == null || this.data.length < this.len) {
            this.data = new byte[this.len];
        }
    }

    private void computeCrcForWriting() {
        this.crcengine = new CRC32();
        this.crcengine.update(this.idbytes, 0, 4);
        if (this.len > 0) {
            this.crcengine.update(this.data, 0, this.len);
        }
        PngHelperInternal.writeInt4tobytes((int) this.crcengine.getValue(), this.crcval, 0);
    }

    public void writeChunk(OutputStream os) {
        writeChunkHeader(os);
        if (this.len > 0) {
            if (this.data == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("cannot write chunk, raw chunk data is null [");
                stringBuilder.append(this.id);
                stringBuilder.append("]");
                throw new PngjOutputException(stringBuilder.toString());
            }
            PngHelperInternal.writeBytes(os, this.data, 0, this.len);
        }
        computeCrcForWriting();
        writeChunkCrc(os);
    }

    public void writeChunkHeader(OutputStream os) {
        if (this.idbytes.length != 4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bad chunkid [");
            stringBuilder.append(this.id);
            stringBuilder.append("]");
            throw new PngjOutputException(stringBuilder.toString());
        }
        PngHelperInternal.writeInt4(os, this.len);
        PngHelperInternal.writeBytes(os, this.idbytes);
    }

    public void writeChunkCrc(OutputStream os) {
        PngHelperInternal.writeBytes(os, this.crcval, 0, 4);
    }

    public void checkCrc() {
        int crcComputed = (int) this.crcengine.getValue();
        int crcExpected = PngHelperInternal.readInt4fromBytes(this.crcval, 0);
        if (crcComputed != crcExpected) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("chunk: ");
            stringBuilder.append(toString());
            stringBuilder.append(" expected=");
            stringBuilder.append(crcExpected);
            stringBuilder.append(" read=");
            stringBuilder.append(crcComputed);
            throw new PngjBadCrcException(stringBuilder.toString());
        }
    }

    public void updateCrc(byte[] buf, int off, int len) {
        if (this.crcengine == null) {
            this.crcengine = new CRC32();
        }
        this.crcengine.update(buf, off, len);
    }

    ByteArrayInputStream getAsByteStream() {
        return new ByteArrayInputStream(this.data);
    }

    public long getOffset() {
        return this.offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("chunkid=");
        stringBuilder.append(ChunkHelper.toString(this.idbytes));
        stringBuilder.append(" len=");
        stringBuilder.append(this.len);
        return stringBuilder.toString();
    }

    public int hashCode() {
        return (((1 * 31) + (this.id == null ? 0 : this.id.hashCode())) * 31) + ((int) (this.offset ^ (this.offset >>> 32)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChunkRaw other = (ChunkRaw) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.offset != other.offset) {
            return false;
        }
        return true;
    }
}
