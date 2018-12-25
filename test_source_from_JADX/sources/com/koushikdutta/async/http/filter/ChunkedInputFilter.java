package com.koushikdutta.async.http.filter;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.Util;

public class ChunkedInputFilter extends FilteredDataEmitter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int mChunkLength = 0;
    private int mChunkLengthRemaining = 0;
    private State mState = State.CHUNK_LEN;
    ByteBufferList pending = new ByteBufferList();

    private enum State {
        CHUNK_LEN,
        CHUNK_LEN_CR,
        CHUNK_LEN_CRLF,
        CHUNK,
        CHUNK_CR,
        CHUNK_CRLF,
        COMPLETE
    }

    private boolean checkByte(char b, char value) {
        if (b == value) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value);
        stringBuilder.append(" was expected, got ");
        stringBuilder.append(b);
        report(new ChunkedDataException(stringBuilder.toString()));
        return false;
    }

    private boolean checkLF(char b) {
        return checkByte(b, '\n');
    }

    private boolean checkCR(char b) {
        return checkByte(b, '\r');
    }

    protected void report(Exception e) {
        if (e == null && this.mState != State.COMPLETE) {
            e = new ChunkedDataException("chunked input ended before final chunk");
        }
        super.report(e);
    }

    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
        while (bb.remaining() > 0) {
            try {
                switch (this.mState) {
                    case CHUNK_LEN:
                        char c = bb.getByteChar();
                        if (c == '\r') {
                            this.mState = State.CHUNK_LEN_CR;
                        } else {
                            this.mChunkLength *= 16;
                            if (c >= 'a' && c <= 'f') {
                                this.mChunkLength += (c - 97) + 10;
                            } else if (c >= '0' && c <= '9') {
                                this.mChunkLength += c - 48;
                            } else if (c < 'A' || c > 'F') {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("invalid chunk length: ");
                                stringBuilder.append(c);
                                report(new ChunkedDataException(stringBuilder.toString()));
                                return;
                            } else {
                                this.mChunkLength += (c - 65) + 10;
                            }
                        }
                        this.mChunkLengthRemaining = this.mChunkLength;
                        break;
                    case CHUNK_LEN_CR:
                        if (checkLF(bb.getByteChar())) {
                            this.mState = State.CHUNK;
                            break;
                        }
                        return;
                    case CHUNK:
                        int reading = Math.min(this.mChunkLengthRemaining, bb.remaining());
                        this.mChunkLengthRemaining -= reading;
                        if (this.mChunkLengthRemaining == 0) {
                            this.mState = State.CHUNK_CR;
                        }
                        if (reading != 0) {
                            bb.get(this.pending, reading);
                            Util.emitAllData(this, this.pending);
                            break;
                        }
                        break;
                    case CHUNK_CR:
                        if (checkCR(bb.getByteChar())) {
                            this.mState = State.CHUNK_CRLF;
                            break;
                        }
                        return;
                    case CHUNK_CRLF:
                        if (checkLF(bb.getByteChar())) {
                            if (this.mChunkLength > 0) {
                                this.mState = State.CHUNK_LEN;
                            } else {
                                this.mState = State.COMPLETE;
                                report(null);
                            }
                            this.mChunkLength = 0;
                            break;
                        }
                        return;
                    case COMPLETE:
                        return;
                    default:
                        break;
                }
            } catch (Exception ex) {
                report(ex);
            }
        }
    }
}
