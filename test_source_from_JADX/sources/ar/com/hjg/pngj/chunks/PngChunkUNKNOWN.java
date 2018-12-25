package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.chunks.PngChunk.ChunkOrderingConstraint;

public class PngChunkUNKNOWN extends PngChunkMultiple {
    public PngChunkUNKNOWN(String id, ImageInfo info) {
        super(id, info);
    }

    public ChunkOrderingConstraint getOrderingConstraint() {
        return ChunkOrderingConstraint.NONE;
    }

    public ChunkRaw createRawChunk() {
        return this.raw;
    }

    public void parseFromRaw(ChunkRaw c) {
    }

    public byte[] getData() {
        return this.raw.data;
    }

    public void setData(byte[] data) {
        this.raw.data = data;
    }
}
