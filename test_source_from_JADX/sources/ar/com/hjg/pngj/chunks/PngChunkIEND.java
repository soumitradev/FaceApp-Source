package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkIEND extends PngChunkSingle {
    public static final String ID = "IEND";

    public PngChunkIEND(ImageInfo info) {
        super("IEND", info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NA;
    }

    public ChunkRaw createRawChunk() {
        return new ChunkRaw(0, ChunkHelper.b_IEND, false);
    }

    public void parseFromRaw(ChunkRaw c) {
    }
}
