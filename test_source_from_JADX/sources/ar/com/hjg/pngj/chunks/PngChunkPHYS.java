package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkPHYS extends PngChunkSingle {
    public static final String ID = "pHYs";
    private long pixelsxUnitX;
    private long pixelsxUnitY;
    private int units;

    public PngChunkPHYS(ImageInfo info) {
        super("pHYs", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_IDAT;
    }

    public ChunkRaw createRawChunk() {
        ChunkRaw c = createEmptyChunk(9, true);
        PngHelperInternal.writeInt4tobytes((int) this.pixelsxUnitX, c.data, 0);
        PngHelperInternal.writeInt4tobytes((int) this.pixelsxUnitY, c.data, 4);
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
        this.pixelsxUnitX = (long) PngHelperInternal.readInt4fromBytes(chunk.data, 0);
        if (this.pixelsxUnitX < 0) {
            this.pixelsxUnitX += 4294967296L;
        }
        this.pixelsxUnitY = (long) PngHelperInternal.readInt4fromBytes(chunk.data, 4);
        if (this.pixelsxUnitY < 0) {
            this.pixelsxUnitY += 4294967296L;
        }
        this.units = PngHelperInternal.readInt1fromByte(chunk.data, 8);
    }

    public long getPixelsxUnitX() {
        return this.pixelsxUnitX;
    }

    public void setPixelsxUnitX(long pixelsxUnitX) {
        this.pixelsxUnitX = pixelsxUnitX;
    }

    public long getPixelsxUnitY() {
        return this.pixelsxUnitY;
    }

    public void setPixelsxUnitY(long pixelsxUnitY) {
        this.pixelsxUnitY = pixelsxUnitY;
    }

    public int getUnits() {
        return this.units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getAsDpi() {
        if (this.units == 1) {
            if (this.pixelsxUnitX == this.pixelsxUnitY) {
                return ((double) this.pixelsxUnitX) * 0.0254d;
            }
        }
        return -1.0d;
    }

    public double[] getAsDpi2() {
        if (this.units != 1) {
            return new double[]{-1.0d, -1.0d};
        }
        return new double[]{((double) this.pixelsxUnitX) * 0.0254d, ((double) this.pixelsxUnitY) * 0.0254d};
    }

    public void setAsDpi(double dpi) {
        this.units = 1;
        this.pixelsxUnitX = (long) ((dpi / 0.0254d) + 0.5d);
        this.pixelsxUnitY = this.pixelsxUnitX;
    }

    public void setAsDpi2(double dpix, double dpiy) {
        this.units = 1;
        this.pixelsxUnitX = (long) ((dpix / 0.0254d) + 0.5d);
        this.pixelsxUnitY = (long) ((dpiy / 0.0254d) + 0.5d);
    }
}
