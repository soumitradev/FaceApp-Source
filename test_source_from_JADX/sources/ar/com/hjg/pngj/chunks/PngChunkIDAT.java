package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkIDAT extends PngChunkMultiple {
    public static final String ID = "IDAT";

    public PngChunkIDAT(ImageInfo i) {
        super("IDAT", i);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NA;
    }

    public ChunkRaw createRawChunk() {
        return null;
    }

    public void parseFromRaw(ChunkRaw c) {
    }
}
