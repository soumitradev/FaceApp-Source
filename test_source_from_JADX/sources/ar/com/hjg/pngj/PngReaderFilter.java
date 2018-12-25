package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.PngChunk;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PngReaderFilter extends FilterInputStream {
    private ChunkSeqReaderPng chunkseq = createChunkSequenceReader();

    public PngReaderFilter(InputStream arg0) {
        super(arg0);
    }

    protected ChunkSeqReaderPng createChunkSequenceReader() {
        return new ChunkSeqReaderPng(true) {
            public boolean shouldSkipContent(int len, String id) {
                if (!super.shouldSkipContent(len, id)) {
                    if (!id.equals("IDAT")) {
                        return false;
                    }
                }
                return true;
            }

            protected boolean shouldCheckCrc(int len, String id) {
                return false;
            }

            protected void postProcessChunk(ChunkReader chunkR) {
                super.postProcessChunk(chunkR);
            }
        };
    }

    public void close() throws IOException {
        super.close();
        this.chunkseq.close();
    }

    public int read() throws IOException {
        int r = super.read();
        if (r > 0) {
            this.chunkseq.feedAll(new byte[]{(byte) r}, 0, 1);
        }
        return r;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int res = super.read(b, off, len);
        if (res > 0) {
            this.chunkseq.feedAll(b, off, res);
        }
        return res;
    }

    public int read(byte[] b) throws IOException {
        int res = super.read(b);
        if (res > 0) {
            this.chunkseq.feedAll(b, 0, res);
        }
        return res;
    }

    public void readUntilEndAndClose() throws IOException {
        BufferedStreamFeeder br = new BufferedStreamFeeder(this.in);
        while (!this.chunkseq.isDone() && br.hasMoreToFeed()) {
            br.feed(this.chunkseq);
        }
        close();
    }

    public List<PngChunk> getChunksList() {
        return this.chunkseq.getChunks();
    }

    public ChunkSeqReaderPng getChunkseq() {
        return this.chunkseq;
    }
}
