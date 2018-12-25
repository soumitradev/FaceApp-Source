package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkGAMA extends PngChunkSingle {
    public static final String ID = "gAMA";
    private double gamma;

    public PngChunkGAMA(ImageInfo info) {
        super("gAMA", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }

    public ChunkRaw createRawChunk() {
        ChunkRaw c = createEmptyChunk(4, true);
        PngHelperInternal.writeInt4tobytes((int) ((this.gamma * 100000.0d) + 0.5d), c.data, 0);
        return c;
    }

    public void parseFromRaw(ChunkRaw chunk) {
        if (chunk.len != 4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bad chunk ");
            stringBuilder.append(chunk);
            throw new PngjException(stringBuilder.toString());
        }
        this.gamma = ((double) PngHelperInternal.readInt4fromBytes(chunk.data, 0)) / 100000.0d;
    }

    public double getGamma() {
        return this.gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }
}
