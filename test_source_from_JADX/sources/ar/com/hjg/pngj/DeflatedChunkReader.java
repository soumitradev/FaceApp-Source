package ar.com.hjg.pngj;

import ar.com.hjg.pngj.ChunkReader.ChunkReaderMode;

public class DeflatedChunkReader extends ChunkReader {
    protected boolean alsoBuffer = false;
    protected final DeflatedChunksSet deflatedChunksSet;

    public DeflatedChunkReader(int clen, String chunkid, boolean checkCrc, long offsetInPng, DeflatedChunksSet iDatSet) {
        super(clen, chunkid, offsetInPng, ChunkReaderMode.PROCESS);
        this.deflatedChunksSet = iDatSet;
        iDatSet.appendNewChunk(this);
    }

    protected void processData(int offsetInchunk, byte[] buf, int off, int len) {
        if (len > 0) {
            this.deflatedChunksSet.processBytes(buf, off, len);
            if (this.alsoBuffer) {
                System.arraycopy(buf, off, getChunkRaw().data, this.read, len);
            }
        }
    }

    protected void chunkDone() {
    }

    public void setAlsoBuffer() {
        if (this.read > 0) {
            throw new RuntimeException("too late");
        }
        this.alsoBuffer = true;
        getChunkRaw().allocData();
    }
}
