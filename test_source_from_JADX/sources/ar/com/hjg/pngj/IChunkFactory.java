package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.ChunkRaw;
import ar.com.hjg.pngj.chunks.PngChunk;

public interface IChunkFactory {
    PngChunk createChunk(ChunkRaw chunkRaw, ImageInfo imageInfo);
}
