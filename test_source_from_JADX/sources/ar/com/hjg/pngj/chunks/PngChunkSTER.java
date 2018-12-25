package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkSTER extends PngChunkSingle {
    public static final String ID = "sTER";
    private byte mode;

    public PngChunkSTER(ImageInfo info) {
        super(ID, info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.BEFORE_IDAT;
    }

    public ChunkRaw createRawChunk() {
        ChunkRaw c = createEmptyChunk(1, true);
        c.data[0] = this.mode;
        return c;
    }

    public void parseFromRaw(ChunkRaw chunk) {
        if (chunk.len != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bad chunk length ");
            stringBuilder.append(chunk);
            throw new PngjException(stringBuilder.toString());
        }
        this.mode = chunk.data[0];
    }

    public byte getMode() {
        return this.mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }
}
