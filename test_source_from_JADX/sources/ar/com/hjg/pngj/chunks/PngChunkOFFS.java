package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkOFFS extends PngChunkSingle {
    public static final String ID = "oFFs";
    private long posX;
    private long posY;
    private int units;

    public PngChunkOFFS(ImageInfo info) {
        super(ID, info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_IDAT;
    }

    public ChunkRaw createRawChunk() {
        ChunkRaw c = createEmptyChunk(9, true);
        PngHelperInternal.writeInt4tobytes((int) this.posX, c.data, 0);
        PngHelperInternal.writeInt4tobytes((int) this.posY, c.data, 4);
        c.data[8] = (byte) this.units;
        return c;
    }

    public void parseFromRaw(ChunkRaw chunk) {
        if (chunk.len != 9) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bad chunk length ");
            stringBuilder.append(chunk);
            throw new PngjException(stringBuilder.toString());
        }
        this.posX = (long) PngHelperInternal.readInt4fromBytes(chunk.data, 0);
        if (this.posX < 0) {
            this.posX += 4294967296L;
        }
        this.posY = (long) PngHelperInternal.readInt4fromBytes(chunk.data, 4);
        if (this.posY < 0) {
            this.posY += 4294967296L;
        }
        this.units = PngHelperInternal.readInt1fromByte(chunk.data, 8);
    }

    public int getUnits() {
        return this.units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public long getPosX() {
        return this.posX;
    }

    public void setPosX(long posX) {
        this.posX = posX;
    }

    public long getPosY() {
        return this.posY;
    }

    public void setPosY(long posY) {
        this.posY = posY;
    }
}
