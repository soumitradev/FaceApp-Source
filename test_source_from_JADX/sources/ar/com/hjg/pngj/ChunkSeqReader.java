package ar.com.hjg.pngj;

import ar.com.hjg.pngj.ChunkReader.ChunkReaderMode;
import ar.com.hjg.pngj.chunks.ChunkHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

public class ChunkSeqReader implements IBytesConsumer {
    protected static final int SIGNATURE_LEN = 8;
    private byte[] buf0;
    private int buf0len;
    private long bytesCount;
    private int chunkCount;
    private ChunkReader curChunkReader;
    private DeflatedChunksSet curReaderDeflatedSet;
    private boolean done;
    private long idatBytes;
    private boolean signatureDone;
    protected final boolean withSignature;

    public ChunkSeqReader() {
        this(true);
    }

    public ChunkSeqReader(boolean withSignature) {
        this.buf0 = new byte[8];
        this.buf0len = 0;
        this.signatureDone = false;
        this.done = false;
        this.chunkCount = 0;
        this.bytesCount = 0;
        this.withSignature = withSignature;
        this.signatureDone = withSignature ^ 1;
    }

    public int consume(byte[] buffer, int offset, int len) {
        if (this.done) {
            return -1;
        }
        if (len == 0) {
            return 0;
        }
        if (len < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad len: ");
            stringBuilder.append(len);
            throw new PngjInputException(stringBuilder.toString());
        }
        int processed;
        int read0;
        if (this.signatureDone) {
            if (this.curChunkReader != null) {
                if (!this.curChunkReader.isDone()) {
                    int read1 = this.curChunkReader.feedBytes(buffer, offset, len);
                    processed = 0 + read1;
                    this.bytesCount += (long) read1;
                }
            }
            read0 = 8 - this.buf0len;
            if (read0 > len) {
                read0 = len;
            }
            System.arraycopy(buffer, offset, this.buf0, this.buf0len, read0);
            this.buf0len += read0;
            processed = 0 + read0;
            this.bytesCount += (long) read0;
            len -= read0;
            offset += read0;
            if (this.buf0len == 8) {
                this.chunkCount++;
                startNewChunk(PngHelperInternal.readInt4fromBytes(this.buf0, 0), ChunkHelper.toString(this.buf0, 4, 4), this.bytesCount - 8);
                this.buf0len = 0;
            }
        } else {
            read0 = 8 - this.buf0len;
            if (read0 > len) {
                read0 = len;
            }
            System.arraycopy(buffer, offset, this.buf0, this.buf0len, read0);
            this.buf0len += read0;
            if (this.buf0len == 8) {
                checkSignature(this.buf0);
                this.buf0len = 0;
                this.signatureDone = true;
            }
            processed = 0 + read0;
            this.bytesCount += (long) read0;
        }
        return processed;
    }

    public boolean feedAll(byte[] buf, int off, int len) {
        while (len > 0) {
            int n = consume(buf, off, len);
            if (n < 1) {
                return false;
            }
            len -= n;
            off += n;
        }
        return true;
    }

    protected void startNewChunk(int len, String id, long offset) {
        int i;
        ChunkSeqReader chunkSeqReader = this;
        String str = id;
        if (str.equals("IDAT")) {
            i = len;
            chunkSeqReader.idatBytes += (long) i;
        } else {
            i = len;
        }
        boolean checkCrc = shouldCheckCrc(len, id);
        boolean skip = shouldSkipContent(len, id);
        boolean isIdatType = isIdatKind(str);
        boolean forCurrentIdatSet = false;
        if (chunkSeqReader.curReaderDeflatedSet != null) {
            forCurrentIdatSet = chunkSeqReader.curReaderDeflatedSet.ackNextChunkId(str);
        }
        boolean forCurrentIdatSet2 = forCurrentIdatSet;
        if (!isIdatType || skip) {
            chunkSeqReader.curChunkReader = createChunkReaderForNewChunk(str, i, offset, skip);
            if (!checkCrc) {
                chunkSeqReader.curChunkReader.setCrcCheck(false);
                return;
            }
            return;
        }
        if (!forCurrentIdatSet2) {
            if (chunkSeqReader.curReaderDeflatedSet != null) {
                throw new PngjInputException("too many IDAT (or idatlike) chunks");
            }
            chunkSeqReader.curReaderDeflatedSet = createIdatSet(str);
        }
        chunkSeqReader.curChunkReader = new DeflatedChunkReader(i, str, checkCrc, offset, chunkSeqReader.curReaderDeflatedSet) {
            protected void chunkDone() {
                ChunkSeqReader.this.postProcessChunk(this);
            }
        };
    }

    protected ChunkReader createChunkReaderForNewChunk(String id, int len, long offset, boolean skip) {
        return new ChunkReader(len, id, offset, skip ? ChunkReaderMode.SKIP : ChunkReaderMode.BUFFER) {
            protected void chunkDone() {
                ChunkSeqReader.this.postProcessChunk(this);
            }

            protected void processData(int offsetinChhunk, byte[] buf, int off, int len) {
                throw new PngjExceptionInternal("should never happen");
            }
        };
    }

    protected void postProcessChunk(ChunkReader chunkR) {
        if (this.chunkCount == 1) {
            String cid = firstChunkId();
            if (!(cid == null || cid.equals(chunkR.getChunkRaw().id))) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad first chunk: ");
                stringBuilder.append(chunkR.getChunkRaw().id);
                stringBuilder.append(" expected: ");
                stringBuilder.append(firstChunkId());
                throw new PngjInputException(stringBuilder.toString());
            }
        }
        if (chunkR.getChunkRaw().id.equals(endChunkId())) {
            this.done = true;
        }
    }

    protected DeflatedChunksSet createIdatSet(String id) {
        return new DeflatedChunksSet(id, 1024, 1024);
    }

    protected boolean isIdatKind(String id) {
        return false;
    }

    protected boolean shouldSkipContent(int len, String id) {
        return false;
    }

    protected boolean shouldCheckCrc(int len, String id) {
        return true;
    }

    protected void checkSignature(byte[] buf) {
        if (!Arrays.equals(buf, PngHelperInternal.getPngIdSignature())) {
            throw new PngjInputException("Bad PNG signature");
        }
    }

    public boolean isSignatureDone() {
        return this.signatureDone;
    }

    public boolean isDone() {
        return this.done;
    }

    public long getBytesCount() {
        return this.bytesCount;
    }

    public int getChunkCount() {
        return this.chunkCount;
    }

    public ChunkReader getCurChunkReader() {
        return this.curChunkReader;
    }

    public DeflatedChunksSet getCurReaderDeflatedSet() {
        return this.curReaderDeflatedSet;
    }

    public void close() {
        if (this.curReaderDeflatedSet != null) {
            this.curReaderDeflatedSet.close();
        }
        this.done = true;
    }

    public boolean isAtChunkBoundary() {
        if (!(this.bytesCount == 0 || this.bytesCount == 8 || this.done || this.curChunkReader == null)) {
            if (!this.curChunkReader.isDone()) {
                return false;
            }
        }
        return true;
    }

    protected String firstChunkId() {
        return "IHDR";
    }

    public long getIdatBytes() {
        return this.idatBytes;
    }

    protected String endChunkId() {
        return "IEND";
    }

    public void feedFromFile(File f) {
        try {
            feedFromInputStream(new FileInputStream(f), true);
        } catch (FileNotFoundException e) {
            throw new PngjInputException(e.getMessage());
        }
    }

    public void feedFromInputStream(InputStream is, boolean closeStream) {
        BufferedStreamFeeder sf = new BufferedStreamFeeder(is);
        sf.setCloseStream(closeStream);
        while (sf.hasMoreToFeed()) {
            try {
                sf.feed(this);
            } finally {
                close();
                sf.close();
            }
        }
    }

    public void feedFromInputStream(InputStream is) {
        feedFromInputStream(is, true);
    }
}
