package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.ChunkCopyBehaviour;
import ar.com.hjg.pngj.chunks.ChunkPredicate;
import ar.com.hjg.pngj.chunks.ChunksList;
import ar.com.hjg.pngj.chunks.ChunksListForWrite;
import ar.com.hjg.pngj.chunks.PngChunk;
import ar.com.hjg.pngj.chunks.PngChunkIEND;
import ar.com.hjg.pngj.chunks.PngChunkIHDR;
import ar.com.hjg.pngj.chunks.PngMetadata;
import ar.com.hjg.pngj.pixels.PixelsWriter;
import ar.com.hjg.pngj.pixels.PixelsWriterDefault;
import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

public class PngWriter {
    private final ChunksListForWrite chunksList;
    private ChunksList copyFromList;
    private ChunkPredicate copyFromPredicate;
    protected int currentChunkGroup;
    private int currentpass;
    private PngIDatChunkOutputStream datStream;
    protected StringBuilder debuginfo;
    private int idatMaxSize;
    public final ImageInfo imgInfo;
    private final PngMetadata metadata;
    private final OutputStream os;
    private int passes;
    protected PixelsWriter pixelsWriter;
    protected int rowNum;
    private boolean shouldCloseStream;

    public PngWriter(File file, ImageInfo imgInfo, boolean allowoverwrite) {
        this(PngHelperInternal.ostreamFromFile(file, allowoverwrite), imgInfo);
        setShouldCloseStream(true);
    }

    public PngWriter(File file, ImageInfo imgInfo) {
        this(file, imgInfo, true);
    }

    public PngWriter(OutputStream outputStream, ImageInfo imgInfo) {
        this.rowNum = -1;
        this.currentChunkGroup = -1;
        this.passes = 1;
        this.currentpass = 0;
        this.shouldCloseStream = true;
        this.idatMaxSize = 0;
        this.copyFromPredicate = null;
        this.copyFromList = null;
        this.debuginfo = new StringBuilder();
        this.os = outputStream;
        this.imgInfo = imgInfo;
        this.chunksList = new ChunksListForWrite(imgInfo);
        this.metadata = new PngMetadata(this.chunksList);
        this.pixelsWriter = createPixelsWriter(imgInfo);
        setCompLevel(9);
    }

    private void initIdat() {
        this.datStream = new PngIDatChunkOutputStream(this.os, this.idatMaxSize);
        this.pixelsWriter.setOs(this.datStream);
        writeSignatureAndIHDR();
        writeFirstChunks();
    }

    private void writeEndChunk() {
        PngChunkIEND c = new PngChunkIEND(this.imgInfo);
        c.createRawChunk().writeChunk(this.os);
        this.chunksList.getChunks().add(c);
    }

    private void writeFirstChunks() {
        if (this.currentChunkGroup < 4) {
            this.currentChunkGroup = 1;
            queueChunksFromOther();
            int nw = this.chunksList.writeChunks(this.os, this.currentChunkGroup);
            this.currentChunkGroup = 2;
            nw = this.chunksList.writeChunks(this.os, this.currentChunkGroup);
            if (nw > 0 && this.imgInfo.greyscale) {
                throw new PngjOutputException("cannot write palette for this format");
            } else if (nw == 0 && this.imgInfo.indexed) {
                throw new PngjOutputException("missing palette");
            } else {
                this.currentChunkGroup = 3;
                nw = this.chunksList.writeChunks(this.os, this.currentChunkGroup);
                this.currentChunkGroup = 4;
            }
        }
    }

    private void writeLastChunks() {
        queueChunksFromOther();
        this.currentChunkGroup = 5;
        this.chunksList.writeChunks(this.os, this.currentChunkGroup);
        List<PngChunk> pending = this.chunksList.getQueuedChunks();
        if (pending.isEmpty()) {
            this.currentChunkGroup = 6;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(pending.size());
        stringBuilder.append(" chunks were not written! Eg: ");
        stringBuilder.append(((PngChunk) pending.get(0)).toString());
        throw new PngjOutputException(stringBuilder.toString());
    }

    private void writeSignatureAndIHDR() {
        this.currentChunkGroup = 0;
        PngHelperInternal.writeBytes(this.os, PngHelperInternal.getPngIdSignature());
        PngChunkIHDR ihdr = new PngChunkIHDR(this.imgInfo);
        ihdr.setCols(this.imgInfo.cols);
        ihdr.setRows(this.imgInfo.rows);
        ihdr.setBitspc(this.imgInfo.bitDepth);
        int colormodel = 0;
        if (this.imgInfo.alpha) {
            colormodel = 0 + 4;
        }
        if (this.imgInfo.indexed) {
            colormodel++;
        }
        if (!this.imgInfo.greyscale) {
            colormodel += 2;
        }
        ihdr.setColormodel(colormodel);
        ihdr.setCompmeth(0);
        ihdr.setFilmeth(0);
        ihdr.setInterlaced(0);
        ihdr.createRawChunk().writeChunk(this.os);
        this.chunksList.getChunks().add(ihdr);
    }

    private void queueChunksFromOther() {
        if (this.copyFromList != null) {
            if (this.copyFromPredicate != null) {
                boolean idatDone = this.currentChunkGroup >= 4;
                for (PngChunk chunk : this.copyFromList.getChunks()) {
                    if (chunk.getRaw().data != null) {
                        int group = chunk.getChunkGroup();
                        if (group > 4 || !idatDone) {
                            if (group < 4 || idatDone) {
                                if (!chunk.crit || chunk.id.equals("PLTE")) {
                                    if (this.copyFromPredicate.match(chunk) && this.chunksList.getEquivalent(chunk).isEmpty() && this.chunksList.getQueuedEquivalent(chunk).isEmpty()) {
                                        this.chunksList.queue(chunk);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void queueChunk(PngChunk chunk) {
        for (PngChunk other : this.chunksList.getQueuedEquivalent(chunk)) {
            getChunksList().removeChunk(other);
        }
        this.chunksList.queue(chunk);
    }

    public void copyChunksFrom(ChunksList chunks, int copyMask) {
        copyChunksFrom(chunks, ChunkCopyBehaviour.createPredicate(copyMask, this.imgInfo));
    }

    public void copyChunksFrom(ChunksList chunks) {
        copyChunksFrom(chunks, 8);
    }

    public void copyChunksFrom(ChunksList chunks, ChunkPredicate predicate) {
        if (!(this.copyFromList == null || chunks == null)) {
            PngHelperInternal.LOGGER.warning("copyChunksFrom should only be called once");
        }
        if (predicate == null) {
            throw new PngjOutputException("copyChunksFrom requires a predicate");
        }
        this.copyFromList = chunks;
        this.copyFromPredicate = predicate;
    }

    public double computeCompressionRatio() {
        if (this.currentChunkGroup >= 6) {
            return ((double) this.datStream.getCountFlushed()) / ((double) ((this.imgInfo.bytesPerRow + 1) * this.imgInfo.rows));
        }
        throw new PngjOutputException("must be called after end()");
    }

    public void end() {
        if (this.rowNum != this.imgInfo.rows - 1) {
            throw new PngjOutputException("all rows have not been written");
        }
        try {
            this.datStream.flush();
            writeLastChunks();
            writeEndChunk();
            close();
        } catch (Throwable e) {
            throw new PngjOutputException(e);
        } catch (Throwable th) {
            close();
        }
    }

    public void close() {
        try {
            if (this.datStream != null) {
                this.datStream.close();
            }
        } catch (Exception e) {
        }
        if (this.pixelsWriter != null) {
            this.pixelsWriter.close();
        }
        if (this.shouldCloseStream && this.os != null) {
            try {
                this.os.close();
            } catch (Exception e2) {
                Logger logger = PngHelperInternal.LOGGER;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error closing writer ");
                stringBuilder.append(e2.toString());
                logger.warning(stringBuilder.toString());
            }
        }
    }

    public ChunksListForWrite getChunksList() {
        return this.chunksList;
    }

    public PngMetadata getMetadata() {
        return this.metadata;
    }

    public void setFilterType(FilterType filterType) {
        this.pixelsWriter.setFilterType(filterType);
    }

    public void setCompLevel(int complevel) {
        this.pixelsWriter.setDeflaterCompLevel(Integer.valueOf(complevel));
    }

    public void setFilterPreserve(boolean filterPreserve) {
        if (filterPreserve) {
            this.pixelsWriter.setFilterType(FilterType.FILTER_PRESERVE);
        } else if (this.pixelsWriter.getFilterType() == null) {
            this.pixelsWriter.setFilterType(FilterType.FILTER_DEFAULT);
        }
    }

    public void setIdatMaxSize(int idatMaxSize) {
        this.idatMaxSize = idatMaxSize;
    }

    public void setShouldCloseStream(boolean shouldCloseStream) {
        this.shouldCloseStream = shouldCloseStream;
    }

    public void writeRow(IImageLine imgline) {
        writeRow(imgline, this.rowNum + 1);
    }

    public void writeRows(IImageLineSet<? extends IImageLine> imglines) {
        for (int i = 0; i < this.imgInfo.rows; i++) {
            writeRow(imglines.getImageLine(i));
        }
    }

    public void writeRow(IImageLine imgline, int rownumber) {
        this.rowNum++;
        if (this.rowNum == this.imgInfo.rows) {
            this.rowNum = 0;
        }
        if (rownumber == this.imgInfo.rows) {
            rownumber = 0;
        }
        if (rownumber < 0 || this.rowNum == rownumber) {
            if (this.rowNum == 0) {
                this.currentpass++;
            }
            if (rownumber == 0 && this.currentpass == this.passes) {
                initIdat();
                writeFirstChunks();
            }
            byte[] rowb = this.pixelsWriter.getRowb();
            imgline.writeToPngRaw(rowb);
            this.pixelsWriter.processRow(rowb);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("rows must be written in order: expected:");
        stringBuilder.append(this.rowNum);
        stringBuilder.append(" passed:");
        stringBuilder.append(rownumber);
        throw new PngjOutputException(stringBuilder.toString());
    }

    public void writeRowInt(int[] buf) {
        writeRow(new ImageLineInt(this.imgInfo, buf));
    }

    protected PixelsWriter createPixelsWriter(ImageInfo imginfo) {
        return new PixelsWriterDefault(imginfo);
    }

    public final PixelsWriter getPixelsWriter() {
        return this.pixelsWriter;
    }

    public String getDebuginfo() {
        return this.debuginfo.toString();
    }
}
