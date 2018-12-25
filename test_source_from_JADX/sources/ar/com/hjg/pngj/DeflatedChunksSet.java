package ar.com.hjg.pngj;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class DeflatedChunksSet {
    private boolean callbackMode;
    public final String chunkid;
    private DeflatedChunkReader curChunk;
    private Inflater inf;
    private final boolean infOwn;
    private long nBytesIn;
    private long nBytesOut;
    protected byte[] row;
    private int rowfilled;
    private int rowlen;
    private int rown;
    State state;

    private enum State {
        WAITING_FOR_INPUT,
        ROW_READY,
        WORK_DONE,
        TERMINATED;

        public boolean isDone() {
            if (this != WORK_DONE) {
                if (this != TERMINATED) {
                    return false;
                }
            }
            return true;
        }

        public boolean isTerminated() {
            return this == TERMINATED;
        }
    }

    public DeflatedChunksSet(String chunkid, int initialRowLen, int maxRowLen, Inflater inflater, byte[] buffer) {
        this.state = State.WAITING_FOR_INPUT;
        this.callbackMode = true;
        this.nBytesIn = 0;
        this.nBytesOut = 0;
        this.chunkid = chunkid;
        this.rowlen = initialRowLen;
        if (initialRowLen >= 1) {
            if (maxRowLen >= initialRowLen) {
                if (inflater != null) {
                    this.inf = inflater;
                    this.infOwn = false;
                } else {
                    this.inf = new Inflater();
                    this.infOwn = true;
                }
                byte[] bArr = (buffer == null || buffer.length < initialRowLen) ? new byte[maxRowLen] : buffer;
                this.row = bArr;
                this.rown = -1;
                this.state = State.WAITING_FOR_INPUT;
                try {
                    prepareForNextRow(initialRowLen);
                    return;
                } catch (RuntimeException e) {
                    close();
                    throw e;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad inital row len ");
        stringBuilder.append(initialRowLen);
        throw new PngjException(stringBuilder.toString());
    }

    public DeflatedChunksSet(String chunkid, int initialRowLen, int maxRowLen) {
        this(chunkid, initialRowLen, maxRowLen, null, null);
    }

    protected void appendNewChunk(DeflatedChunkReader cr) {
        if (this.chunkid.equals(cr.getChunkRaw().id)) {
            this.curChunk = cr;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad chunk inside IdatSet, id:");
        stringBuilder.append(cr.getChunkRaw().id);
        stringBuilder.append(", expected:");
        stringBuilder.append(this.chunkid);
        throw new PngjInputException(stringBuilder.toString());
    }

    protected void processBytes(byte[] buf, int off, int len) {
        this.nBytesIn += (long) len;
        if (len >= 1) {
            if (!this.state.isDone()) {
                if (this.state == State.ROW_READY) {
                    throw new PngjInputException("this should only be called if waitingForMoreInput");
                }
                if (!this.inf.needsDictionary()) {
                    if (this.inf.needsInput()) {
                        this.inf.setInput(buf, off, len);
                        if (isCallbackMode()) {
                            while (inflateData()) {
                                prepareForNextRow(processRowCallback());
                                if (isDone()) {
                                    processDoneCallback();
                                }
                            }
                        } else {
                            inflateData();
                        }
                        return;
                    }
                }
                throw new RuntimeException("should not happen");
            }
        }
    }

    private boolean inflateData() {
        try {
            if (this.state == State.ROW_READY) {
                throw new PngjException("invalid state");
            } else if (this.state.isDone()) {
                return false;
            } else {
                State nextstate;
                if (this.row == null || this.row.length < this.rowlen) {
                    this.row = new byte[this.rowlen];
                }
                if (this.rowfilled < this.rowlen && !this.inf.finished()) {
                    int ninflated = this.inf.inflate(this.row, this.rowfilled, this.rowlen - this.rowfilled);
                    this.rowfilled += ninflated;
                    this.nBytesOut += (long) ninflated;
                }
                if (this.rowfilled == this.rowlen) {
                    nextstate = State.ROW_READY;
                } else if (!this.inf.finished()) {
                    nextstate = State.WAITING_FOR_INPUT;
                } else if (this.rowfilled > 0) {
                    nextstate = State.ROW_READY;
                } else {
                    nextstate = State.WORK_DONE;
                }
                this.state = nextstate;
                if (this.state != State.ROW_READY) {
                    return false;
                }
                preProcessRow();
                return true;
            }
        } catch (DataFormatException e) {
            throw new PngjInputException("error decompressing zlib stream ", e);
        } catch (RuntimeException e2) {
            close();
            throw e2;
        }
    }

    protected void preProcessRow() {
    }

    protected int processRowCallback() {
        throw new PngjInputException("not implemented");
    }

    protected void processDoneCallback() {
    }

    public byte[] getInflatedRow() {
        return this.row;
    }

    public void prepareForNextRow(int len) {
        this.rowfilled = 0;
        this.rown++;
        if (len < 1) {
            this.rowlen = 0;
            done();
        } else if (this.inf.finished()) {
            this.rowlen = 0;
            done();
        } else {
            this.state = State.WAITING_FOR_INPUT;
            this.rowlen = len;
            if (!this.callbackMode) {
                inflateData();
            }
        }
    }

    public boolean isWaitingForMoreInput() {
        return this.state == State.WAITING_FOR_INPUT;
    }

    public boolean isRowReady() {
        return this.state == State.ROW_READY;
    }

    public boolean isDone() {
        return this.state.isDone();
    }

    public boolean isTerminated() {
        return this.state.isTerminated();
    }

    public boolean ackNextChunkId(String id) {
        if (this.state.isTerminated()) {
            return false;
        }
        if (id.equals(this.chunkid) || allowOtherChunksInBetween(id)) {
            return true;
        }
        if (this.state.isDone()) {
            if (!isTerminated()) {
                terminate();
            }
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected chunk ");
        stringBuilder.append(id);
        stringBuilder.append(" while ");
        stringBuilder.append(this.chunkid);
        stringBuilder.append(" set is not done");
        throw new PngjInputException(stringBuilder.toString());
    }

    protected void terminate() {
        close();
    }

    public void close() {
        try {
            if (!this.state.isTerminated()) {
                this.state = State.TERMINATED;
            }
            if (this.infOwn && this.inf != null) {
                this.inf.end();
                this.inf = null;
            }
        } catch (Exception e) {
        }
    }

    public void done() {
        if (!isDone()) {
            this.state = State.WORK_DONE;
        }
    }

    public int getRowLen() {
        return this.rowlen;
    }

    public int getRowFilled() {
        return this.rowfilled;
    }

    public int getRown() {
        return this.rown;
    }

    public boolean allowOtherChunksInBetween(String id) {
        return false;
    }

    public boolean isCallbackMode() {
        return this.callbackMode;
    }

    public void setCallbackMode(boolean callbackMode) {
        this.callbackMode = callbackMode;
    }

    public long getBytesIn() {
        return this.nBytesIn;
    }

    public long getBytesOut() {
        return this.nBytesOut;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("idatSet : ");
        stringBuilder.append(this.curChunk.getChunkRaw().id);
        stringBuilder.append(" state=");
        stringBuilder.append(this.state);
        stringBuilder.append(" rows=");
        stringBuilder.append(this.rown);
        stringBuilder.append(" bytes=");
        stringBuilder.append(this.nBytesIn);
        stringBuilder.append("/");
        stringBuilder.append(this.nBytesOut);
        return new StringBuilder(stringBuilder.toString()).toString();
    }
}
