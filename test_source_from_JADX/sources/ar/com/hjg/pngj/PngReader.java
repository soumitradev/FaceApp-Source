package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.ChunkLoadBehaviour;
import ar.com.hjg.pngj.chunks.ChunksList;
import ar.com.hjg.pngj.chunks.PngMetadata;
import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class PngReader {
    public static final long MAX_BYTES_METADATA_DEFAULT = 5024024;
    public static final long MAX_CHUNK_SIZE_SKIP = 2024024;
    public static final long MAX_TOTAL_BYTES_READ_DEFAULT = 901001001;
    protected ChunkSeqReaderPng chunkseq;
    CRC32 idatCrca;
    Adler32 idatCrcb;
    private IImageLineSetFactory<? extends IImageLine> imageLineSetFactory;
    public final ImageInfo imgInfo;
    protected IImageLineSet<? extends IImageLine> imlinesSet;
    public final boolean interlaced;
    protected final PngMetadata metadata;
    protected int rowNum;
    protected BufferedStreamFeeder streamFeeder;

    public PngReader(InputStream inputStream) {
        this(inputStream, true);
    }

    public PngReader(InputStream inputStream, boolean shouldCloseStream) {
        this.rowNum = -1;
        try {
            this.streamFeeder = new BufferedStreamFeeder(inputStream);
            this.streamFeeder.setCloseStream(shouldCloseStream);
            boolean z = false;
            this.chunkseq = new ChunkSeqReaderPng(false);
            this.streamFeeder.setFailIfNoFeed(true);
            if (this.streamFeeder.feedFixed(this.chunkseq, 36)) {
                this.imgInfo = this.chunkseq.getImageInfo();
                if (this.chunkseq.getDeinterlacer() != null) {
                    z = true;
                }
                this.interlaced = z;
                setMaxBytesMetadata(MAX_BYTES_METADATA_DEFAULT);
                setMaxTotalBytesRead(MAX_TOTAL_BYTES_READ_DEFAULT);
                setSkipChunkMaxSize(MAX_CHUNK_SIZE_SKIP);
                this.metadata = new PngMetadata(this.chunkseq.chunksList);
                setLineSetFactory(ImageLineSetDefault.getFactoryInt());
                this.rowNum = -1;
                return;
            }
            throw new PngjInputException("error reading first 21 bytes");
        } catch (RuntimeException e) {
            this.streamFeeder.close();
            if (this.chunkseq != null) {
                this.chunkseq.close();
            }
            throw e;
        }
    }

    public PngReader(File file) {
        this(PngHelperInternal.istreamFromFile(file), true);
    }

    protected void readFirstChunks() {
        while (this.chunkseq.currentChunkGroup < 4) {
            this.streamFeeder.feed(this.chunkseq);
        }
    }

    public void setChunkLoadBehaviour(ChunkLoadBehaviour chunkLoadBehaviour) {
        this.chunkseq.setChunkLoadBehaviour(chunkLoadBehaviour);
    }

    public ChunksList getChunksList() {
        if (this.chunkseq.firstChunksNotYetRead()) {
            readFirstChunks();
        }
        return this.chunkseq.chunksList;
    }

    int getCurrentChunkGroup() {
        return this.chunkseq.currentChunkGroup;
    }

    public PngMetadata getMetadata() {
        if (this.chunkseq.firstChunksNotYetRead()) {
            readFirstChunks();
        }
        return this.metadata;
    }

    public IImageLine readRow() {
        return readRow(this.rowNum + 1);
    }

    public boolean hasMoreRows() {
        return this.rowNum < this.imgInfo.rows - 1;
    }

    public IImageLine readRow(int nrow) {
        if (this.chunkseq.firstChunksNotYetRead()) {
            readFirstChunks();
        }
        if (this.interlaced) {
            if (this.imlinesSet == null) {
                this.imlinesSet = createLineSet(false, this.imgInfo.rows, 0, 1);
                loadAllInterlaced(this.imgInfo.rows, 0, 1);
            }
            this.rowNum = nrow;
            return this.imlinesSet.getImageLine(nrow);
        }
        if (this.imlinesSet == null) {
            this.imlinesSet = createLineSet(true, 1, 0, 1);
        }
        IImageLine line = this.imlinesSet.getImageLine(nrow);
        if (nrow == this.rowNum) {
            return line;
        }
        if (nrow < this.rowNum) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("rows must be read in increasing order: ");
            stringBuilder.append(nrow);
            throw new PngjInputException(stringBuilder.toString());
        }
        while (this.rowNum < nrow) {
            while (!this.chunkseq.getIdatSet().isRowReady()) {
                this.streamFeeder.feed(this.chunkseq);
            }
            this.rowNum++;
            this.chunkseq.getIdatSet().updateCrcs(this.idatCrca, this.idatCrcb);
            if (this.rowNum == nrow) {
                line.readFromPngRaw(this.chunkseq.getIdatSet().getUnfilteredRow(), this.imgInfo.bytesPerRow + 1, 0, 1);
                line.endReadFromPngRaw();
            }
            this.chunkseq.getIdatSet().advanceToNextRow();
        }
        return line;
    }

    public IImageLineSet<? extends IImageLine> readRows() {
        return readRows(this.imgInfo.rows, 0, 1);
    }

    public IImageLineSet<? extends IImageLine> readRows(int nRows, int rowOffset, int rowStep) {
        if (this.chunkseq.firstChunksNotYetRead()) {
            readFirstChunks();
        }
        if (nRows < 0) {
            nRows = (this.imgInfo.rows - rowOffset) / rowStep;
        }
        if (rowStep >= 1 && rowOffset >= 0 && nRows != 0) {
            if ((nRows * rowStep) + rowOffset <= this.imgInfo.rows) {
                if (this.rowNum >= 0) {
                    throw new PngjInputException("readRows cannot be mixed with readRow");
                }
                this.imlinesSet = createLineSet(false, nRows, rowOffset, rowStep);
                if (this.interlaced) {
                    loadAllInterlaced(nRows, rowOffset, rowStep);
                } else {
                    int m = -1;
                    while (m < nRows - 1) {
                        while (!this.chunkseq.getIdatSet().isRowReady()) {
                            this.streamFeeder.feed(this.chunkseq);
                        }
                        this.rowNum++;
                        this.chunkseq.getIdatSet().updateCrcs(this.idatCrca, this.idatCrcb);
                        m = (this.rowNum - rowOffset) / rowStep;
                        if (this.rowNum >= rowOffset && (rowStep * m) + rowOffset == this.rowNum) {
                            IImageLine line = this.imlinesSet.getImageLine(this.rowNum);
                            line.readFromPngRaw(this.chunkseq.getIdatSet().getUnfilteredRow(), this.imgInfo.bytesPerRow + 1, 0, 1);
                            line.endReadFromPngRaw();
                        }
                        this.chunkseq.getIdatSet().advanceToNextRow();
                    }
                }
                this.chunkseq.getIdatSet().done();
                end();
                return this.imlinesSet;
            }
        }
        throw new PngjInputException("bad args");
    }

    public void setLineSetFactory(IImageLineSetFactory<? extends IImageLine> factory) {
        this.imageLineSetFactory = factory;
    }

    protected IImageLineSet<? extends IImageLine> createLineSet(boolean singleCursor, int nlines, int noffset, int step) {
        return this.imageLineSetFactory.create(this.imgInfo, singleCursor, nlines, noffset, step);
    }

    protected void loadAllInterlaced(int nRows, int rowOffset, int rowStep) {
        int rowNumreal;
        IdatSet idat = this.chunkseq.getIdatSet();
        int nread = 0;
        while (true) {
            if (this.chunkseq.getIdatSet().isRowReady()) {
                IdatSet idatSet = this.chunkseq.getIdatSet();
                Checksum[] checksumArr = new Checksum[2];
                checksumArr[0] = this.idatCrca;
                boolean z = true;
                checksumArr[1] = this.idatCrcb;
                idatSet.updateCrcs(checksumArr);
                rowNumreal = idat.rowinfo.rowNreal;
                if ((rowNumreal - rowOffset) % rowStep != 0) {
                    z = false;
                }
                if (z) {
                    this.imlinesSet.getImageLine(rowNumreal).readFromPngRaw(idat.getUnfilteredRow(), idat.rowinfo.buflen, idat.rowinfo.oX, idat.rowinfo.dX);
                    nread++;
                }
                idat.advanceToNextRow();
                if (nread >= nRows && idat.isDone()) {
                    break;
                }
            }
            this.streamFeeder.feed(this.chunkseq);
        }
        idat.done();
        rowNumreal = 0;
        int j = rowOffset;
        while (rowNumreal < nRows) {
            this.imlinesSet.getImageLine(j).endReadFromPngRaw();
            rowNumreal++;
            j += rowStep;
        }
    }

    public void readSkippingAllRows() {
        this.chunkseq.addChunkToSkip("IDAT");
        if (this.chunkseq.firstChunksNotYetRead()) {
            readFirstChunks();
        }
        end();
    }

    public void setMaxTotalBytesRead(long maxTotalBytesToRead) {
        this.chunkseq.setMaxTotalBytesRead(maxTotalBytesToRead);
    }

    public void setMaxBytesMetadata(long maxBytesMetadata) {
        this.chunkseq.setMaxBytesMetadata(maxBytesMetadata);
    }

    public void setSkipChunkMaxSize(long skipChunkMaxSize) {
        this.chunkseq.setSkipChunkMaxSize(skipChunkMaxSize);
    }

    public void setChunksToSkip(String... chunksToSkip) {
        this.chunkseq.setChunksToSkip(chunksToSkip);
    }

    public void addChunkToSkip(String chunkToSkip) {
        this.chunkseq.addChunkToSkip(chunkToSkip);
    }

    public void setShouldCloseStream(boolean shouldCloseStream) {
        this.streamFeeder.setCloseStream(shouldCloseStream);
    }

    public void end() {
        try {
            if (this.chunkseq.firstChunksNotYetRead()) {
                readFirstChunks();
            }
            if (!(this.chunkseq.getIdatSet() == null || this.chunkseq.getIdatSet().isDone())) {
                this.chunkseq.getIdatSet().done();
            }
            while (!this.chunkseq.isDone()) {
                this.streamFeeder.feed(this.chunkseq);
            }
        } finally {
            close();
        }
    }

    public void close() {
        try {
            if (this.chunkseq != null) {
                this.chunkseq.close();
            }
        } catch (Exception e) {
            Logger logger = PngHelperInternal.LOGGER;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error closing chunk sequence:");
            stringBuilder.append(e.getMessage());
            logger.warning(stringBuilder.toString());
        }
        if (this.streamFeeder != null) {
            this.streamFeeder.close();
        }
    }

    public boolean isInterlaced() {
        return this.interlaced;
    }

    public void setCrcCheckDisabled() {
        this.chunkseq.setCheckCrc(false);
    }

    public ChunkSeqReaderPng getChunkseq() {
        return this.chunkseq;
    }

    public void prepareSimpleDigestComputation() {
        if (this.idatCrca == null) {
            this.idatCrca = new CRC32();
        } else {
            this.idatCrca.reset();
        }
        if (this.idatCrcb == null) {
            this.idatCrcb = new Adler32();
        } else {
            this.idatCrcb.reset();
        }
        this.idatCrca.update((byte) this.imgInfo.rows);
        this.idatCrca.update((byte) (this.imgInfo.rows >> 8));
        this.idatCrca.update((byte) (this.imgInfo.rows >> 16));
        this.idatCrca.update((byte) this.imgInfo.cols);
        this.idatCrca.update((byte) (this.imgInfo.cols >> 8));
        this.idatCrca.update((byte) (this.imgInfo.cols >> 16));
        this.idatCrca.update((byte) this.imgInfo.channels);
        this.idatCrca.update((byte) this.imgInfo.bitDepth);
        this.idatCrca.update((byte) (this.imgInfo.indexed ? 10 : 20));
        this.idatCrcb.update((byte) this.imgInfo.bytesPerRow);
        this.idatCrcb.update((byte) this.imgInfo.channels);
        this.idatCrcb.update((byte) this.imgInfo.rows);
    }

    long getSimpleDigest() {
        if (this.idatCrca == null) {
            return 0;
        }
        return this.idatCrca.getValue() ^ (this.idatCrcb.getValue() << 31);
    }

    public String getSimpleDigestHex() {
        return String.format("%016X", new Object[]{Long.valueOf(getSimpleDigest())});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.imgInfo.toString());
        stringBuilder.append(" interlaced=");
        stringBuilder.append(this.interlaced);
        return stringBuilder.toString();
    }

    public String toStringCompact() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.imgInfo.toStringBrief());
        stringBuilder.append(this.interlaced ? "i" : "");
        return stringBuilder.toString();
    }
}
