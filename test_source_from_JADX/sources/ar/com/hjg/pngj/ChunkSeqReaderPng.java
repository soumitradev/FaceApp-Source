package ar.com.hjg.pngj;

import ar.com.hjg.pngj.ChunkReader.ChunkReaderMode;
import ar.com.hjg.pngj.chunks.ChunkFactory;
import ar.com.hjg.pngj.chunks.ChunkHelper;
import ar.com.hjg.pngj.chunks.ChunkLoadBehaviour;
import ar.com.hjg.pngj.chunks.ChunksList;
import ar.com.hjg.pngj.chunks.PngChunk;
import ar.com.hjg.pngj.chunks.PngChunkIHDR;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChunkSeqReaderPng extends ChunkSeqReader {
    private long bytesChunksLoaded = 0;
    protected final boolean callbackMode;
    private boolean checkCrc = true;
    private IChunkFactory chunkFactory;
    private ChunkLoadBehaviour chunkLoadBehaviour = ChunkLoadBehaviour.LOAD_CHUNK_ALWAYS;
    protected ChunksList chunksList = null;
    private Set<String> chunksToSkip = new HashSet();
    protected int currentChunkGroup = -1;
    protected Deinterlacer deinterlacer;
    protected ImageInfo imageInfo;
    private boolean includeNonBufferedChunks = false;
    private long maxBytesMetadata = 0;
    private long maxTotalBytesRead = 0;
    private long skipChunkMaxSize = 0;

    public ChunkSeqReaderPng(boolean callbackMode) {
        this.callbackMode = callbackMode;
        this.chunkFactory = new ChunkFactory();
    }

    private void updateAndCheckChunkGroup(String id) {
        StringBuilder stringBuilder;
        if (id.equals("IHDR")) {
            if (this.currentChunkGroup < 0) {
                this.currentChunkGroup = 0;
                return;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected chunk ");
            stringBuilder.append(id);
            throw new PngjInputException(stringBuilder.toString());
        } else if (id.equals("PLTE")) {
            if (this.currentChunkGroup != 0) {
                if (this.currentChunkGroup != 1) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("unexpected chunk ");
                    stringBuilder.append(id);
                    throw new PngjInputException(stringBuilder.toString());
                }
            }
            this.currentChunkGroup = 2;
        } else if (id.equals("IDAT")) {
            if (this.currentChunkGroup < 0 || this.currentChunkGroup > 4) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("unexpected chunk ");
                stringBuilder.append(id);
                throw new PngjInputException(stringBuilder.toString());
            }
            this.currentChunkGroup = 4;
        } else if (id.equals("IEND")) {
            if (this.currentChunkGroup >= 4) {
                this.currentChunkGroup = 6;
                return;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected chunk ");
            stringBuilder.append(id);
            throw new PngjInputException(stringBuilder.toString());
        } else if (this.currentChunkGroup <= 1) {
            this.currentChunkGroup = 1;
        } else if (this.currentChunkGroup <= 3) {
            this.currentChunkGroup = 3;
        } else {
            this.currentChunkGroup = 5;
        }
    }

    public boolean shouldSkipContent(int len, String id) {
        if (super.shouldSkipContent(len, id)) {
            return true;
        }
        if (ChunkHelper.isCritical(id)) {
            return false;
        }
        if (this.maxTotalBytesRead > 0 && ((long) len) + getBytesCount() > this.maxTotalBytesRead) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Maximum total bytes to read exceeeded: ");
            stringBuilder.append(this.maxTotalBytesRead);
            stringBuilder.append(" offset:");
            stringBuilder.append(getBytesCount());
            stringBuilder.append(" len=");
            stringBuilder.append(len);
            throw new PngjInputException(stringBuilder.toString());
        } else if (this.chunksToSkip.contains(id)) {
            return true;
        } else {
            if (this.skipChunkMaxSize > 0 && ((long) len) > this.skipChunkMaxSize) {
                return true;
            }
            if (this.maxBytesMetadata > 0 && ((long) len) > this.maxBytesMetadata - this.bytesChunksLoaded) {
                return true;
            }
            switch (this.chunkLoadBehaviour) {
                case LOAD_CHUNK_IF_SAFE:
                    if (!ChunkHelper.isSafeToCopy(id)) {
                        return true;
                    }
                    break;
                case LOAD_CHUNK_NEVER:
                    return true;
                default:
                    break;
            }
            return false;
        }
    }

    public long getBytesChunksLoaded() {
        return this.bytesChunksLoaded;
    }

    public int getCurrentChunkGroup() {
        return this.currentChunkGroup;
    }

    public void setChunksToSkip(String... chunksToSkip) {
        this.chunksToSkip.clear();
        for (String c : chunksToSkip) {
            this.chunksToSkip.add(c);
        }
    }

    public void addChunkToSkip(String chunkToSkip) {
        this.chunksToSkip.add(chunkToSkip);
    }

    public boolean firstChunksNotYetRead() {
        return getCurrentChunkGroup() < 4;
    }

    protected void postProcessChunk(ChunkReader chunkR) {
        super.postProcessChunk(chunkR);
        if (chunkR.getChunkRaw().id.equals("IHDR")) {
            PngChunkIHDR ch = new PngChunkIHDR(null);
            ch.parseFromRaw(chunkR.getChunkRaw());
            this.imageInfo = ch.createImageInfo();
            if (ch.isInterlaced()) {
                this.deinterlacer = new Deinterlacer(this.imageInfo);
            }
            this.chunksList = new ChunksList(this.imageInfo);
        }
        if (chunkR.mode == ChunkReaderMode.BUFFER || this.includeNonBufferedChunks) {
            this.chunksList.appendReadChunk(this.chunkFactory.createChunk(chunkR.getChunkRaw(), getImageInfo()), this.currentChunkGroup);
        }
        if (isDone()) {
            processEndPng();
        }
    }

    protected DeflatedChunksSet createIdatSet(String id) {
        IdatSet ids = new IdatSet(id, this.imageInfo, this.deinterlacer);
        ids.setCallbackMode(this.callbackMode);
        return ids;
    }

    public IdatSet getIdatSet() {
        DeflatedChunksSet c = getCurReaderDeflatedSet();
        return c instanceof IdatSet ? (IdatSet) c : null;
    }

    protected boolean isIdatKind(String id) {
        return id.equals("IDAT");
    }

    public int consume(byte[] buf, int off, int len) {
        return super.consume(buf, off, len);
    }

    public void setChunkFactory(IChunkFactory chunkFactory) {
        this.chunkFactory = chunkFactory;
    }

    protected void processEndPng() {
    }

    public ImageInfo getImageInfo() {
        return this.imageInfo;
    }

    public boolean isInterlaced() {
        return this.deinterlacer != null;
    }

    public Deinterlacer getDeinterlacer() {
        return this.deinterlacer;
    }

    protected void startNewChunk(int len, String id, long offset) {
        updateAndCheckChunkGroup(id);
        super.startNewChunk(len, id, offset);
    }

    public void close() {
        if (this.currentChunkGroup != 6) {
            this.currentChunkGroup = 6;
        }
        super.close();
    }

    public List<PngChunk> getChunks() {
        return this.chunksList.getChunks();
    }

    public void setMaxTotalBytesRead(long maxTotalBytesRead) {
        this.maxTotalBytesRead = maxTotalBytesRead;
    }

    public long getSkipChunkMaxSize() {
        return this.skipChunkMaxSize;
    }

    public void setSkipChunkMaxSize(long skipChunkMaxSize) {
        this.skipChunkMaxSize = skipChunkMaxSize;
    }

    public long getMaxBytesMetadata() {
        return this.maxBytesMetadata;
    }

    public void setMaxBytesMetadata(long maxBytesMetadata) {
        this.maxBytesMetadata = maxBytesMetadata;
    }

    public long getMaxTotalBytesRead() {
        return this.maxTotalBytesRead;
    }

    protected boolean shouldCheckCrc(int len, String id) {
        return this.checkCrc;
    }

    public boolean isCheckCrc() {
        return this.checkCrc;
    }

    public void setCheckCrc(boolean checkCrc) {
        this.checkCrc = checkCrc;
    }

    public boolean isCallbackMode() {
        return this.callbackMode;
    }

    public Set<String> getChunksToSkip() {
        return this.chunksToSkip;
    }

    public void setChunkLoadBehaviour(ChunkLoadBehaviour chunkLoadBehaviour) {
        this.chunkLoadBehaviour = chunkLoadBehaviour;
    }

    public void setIncludeNonBufferedChunks(boolean includeNonBufferedChunks) {
        this.includeNonBufferedChunks = includeNonBufferedChunks;
    }
}
