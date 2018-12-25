package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkHIST extends PngChunkSingle {
    public static final String ID = "hIST";
    private int[] hist = new int[0];

    public PngChunkHIST(ImageInfo info) {
        super("hIST", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
    }

    public void parseFromRaw(ChunkRaw c) {
        if (this.imgInfo.indexed) {
            this.hist = new int[(c.data.length / 2)];
            for (int i = 0; i < this.hist.length; i++) {
                this.hist[i] = PngHelperInternal.readInt2fromBytes(c.data, i * 2);
            }
            return;
        }
        throw new PngjException("only indexed images accept a HIST chunk");
    }

    public ChunkRaw createRawChunk() {
        if (this.imgInfo.indexed) {
            ChunkRaw c = createEmptyChunk(this.hist.length * 2, true);
            for (int i = 0; i < this.hist.length; i++) {
                PngHelperInternal.writeInt2tobytes(this.hist[i], c.data, i * 2);
            }
            return c;
        }
        throw new PngjException("only indexed images accept a HIST chunk");
    }

    public int[] getHist() {
        return this.hist;
    }

    public void setHist(int[] hist) {
        this.hist = hist;
    }
}
