package ar.com.hjg.pngj;

import ar.com.hjg.pngj.ChunkReader.ChunkReaderMode;
import ar.com.hjg.pngj.chunks.ChunkRaw;
import java.util.ArrayList;
import java.util.List;

public class ChunkSeqSkipping extends ChunkSeqReader {
    private List<ChunkRaw> chunks;
    private boolean skip;

    public ChunkSeqSkipping(boolean skipAll) {
        super(true);
        this.chunks = new ArrayList();
        this.skip = true;
        this.skip = skipAll;
    }

    public ChunkSeqSkipping() {
        this(true);
    }

    protected ChunkReader createChunkReaderForNewChunk(String id, int len, long offset, boolean skip) {
        return new ChunkReader(len, id, offset, skip ? ChunkReaderMode.SKIP : ChunkReaderMode.PROCESS) {
            protected void chunkDone() {
                ChunkSeqSkipping.this.postProcessChunk(this);
            }

            protected void processData(int offsetinChhunk, byte[] buf, int off, int len) {
                ChunkSeqSkipping.this.processChunkContent(getChunkRaw(), offsetinChhunk, buf, off, len);
            }
        };
    }

    protected void processChunkContent(ChunkRaw chunkRaw, int offsetinChhunk, byte[] buf, int off, int len) {
    }

    protected void postProcessChunk(ChunkReader chunkR) {
        super.postProcessChunk(chunkR);
        this.chunks.add(chunkR.getChunkRaw());
    }

    protected boolean shouldSkipContent(int len, String id) {
        return this.skip;
    }

    protected boolean isIdatKind(String id) {
        return false;
    }

    public List<ChunkRaw> getChunks() {
        return this.chunks;
    }
}
