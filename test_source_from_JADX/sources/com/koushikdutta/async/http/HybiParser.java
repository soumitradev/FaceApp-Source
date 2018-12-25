package com.koushikdutta.async.http;

import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataEmitterReader;
import com.koushikdutta.async.callback.DataCallback;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

abstract class HybiParser {
    private static final long BASE = 2;
    private static final int BYTE = 255;
    private static final int FIN = 128;
    private static final List<Integer> FRAGMENTED_OPCODES = Arrays.asList(new Integer[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2)});
    private static final int LENGTH = 127;
    private static final int MASK = 128;
    private static final int MODE_BINARY = 2;
    private static final int MODE_TEXT = 1;
    private static final int OPCODE = 15;
    private static final List<Integer> OPCODES = Arrays.asList(new Integer[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10)});
    private static final int OP_BINARY = 2;
    private static final int OP_CLOSE = 8;
    private static final int OP_CONTINUATION = 0;
    private static final int OP_PING = 9;
    private static final int OP_PONG = 10;
    private static final int OP_TEXT = 1;
    private static final int RSV1 = 64;
    private static final int RSV2 = 32;
    private static final int RSV3 = 16;
    private static final String TAG = "HybiParser";
    private static final long _2_TO_16_ = 65536;
    private static final long _2_TO_24 = 16777216;
    private static final long _2_TO_32_ = 4294967296L;
    private static final long _2_TO_40_ = 1099511627776L;
    private static final long _2_TO_48_ = 281474976710656L;
    private static final long _2_TO_56_ = 72057594037927936L;
    private static final long _2_TO_8_ = 256;
    private ByteArrayOutputStream mBuffer = new ByteArrayOutputStream();
    private boolean mClosed = false;
    private boolean mDeflate = false;
    private boolean mDeflated;
    private boolean mFinal;
    private byte[] mInflateBuffer = new byte[4096];
    private Inflater mInflater = new Inflater(true);
    private int mLength;
    private int mLengthSize;
    private byte[] mMask = new byte[0];
    private boolean mMasked;
    private boolean mMasking = true;
    private int mMode;
    private int mOpcode;
    private byte[] mPayload = new byte[0];
    private DataEmitterReader mReader = new DataEmitterReader();
    private int mStage;
    DataCallback mStage0 = new C11351();
    DataCallback mStage1 = new C11362();
    DataCallback mStage2 = new C11373();
    DataCallback mStage3 = new C11384();
    DataCallback mStage4 = new C11395();

    public static class ProtocolError extends IOException {
        public ProtocolError(String detailMessage) {
            super(detailMessage);
        }
    }

    /* renamed from: com.koushikdutta.async.http.HybiParser$1 */
    class C11351 implements DataCallback {
        C11351() {
        }

        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            try {
                HybiParser.this.parseOpcode(bb.get());
            } catch (ProtocolError e) {
                HybiParser.this.report(e);
                e.printStackTrace();
            }
            HybiParser.this.parse();
        }
    }

    /* renamed from: com.koushikdutta.async.http.HybiParser$2 */
    class C11362 implements DataCallback {
        C11362() {
        }

        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            HybiParser.this.parseLength(bb.get());
            HybiParser.this.parse();
        }
    }

    /* renamed from: com.koushikdutta.async.http.HybiParser$3 */
    class C11373 implements DataCallback {
        C11373() {
        }

        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            byte[] bytes = new byte[HybiParser.this.mLengthSize];
            bb.get(bytes);
            try {
                HybiParser.this.parseExtendedLength(bytes);
            } catch (ProtocolError e) {
                HybiParser.this.report(e);
                e.printStackTrace();
            }
            HybiParser.this.parse();
        }
    }

    /* renamed from: com.koushikdutta.async.http.HybiParser$4 */
    class C11384 implements DataCallback {
        C11384() {
        }

        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            HybiParser.this.mMask = new byte[4];
            bb.get(HybiParser.this.mMask);
            HybiParser.this.mStage = 4;
            HybiParser.this.parse();
        }
    }

    /* renamed from: com.koushikdutta.async.http.HybiParser$5 */
    class C11395 implements DataCallback {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class cls = HybiParser.class;
        }

        C11395() {
        }

        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            HybiParser.this.mPayload = new byte[HybiParser.this.mLength];
            bb.get(HybiParser.this.mPayload);
            try {
                HybiParser.this.emitFrame();
            } catch (IOException e) {
                HybiParser.this.report(e);
                e.printStackTrace();
            }
            HybiParser.this.mStage = 0;
            HybiParser.this.parse();
        }
    }

    protected abstract void onDisconnect(int i, String str);

    protected abstract void onMessage(String str);

    protected abstract void onMessage(byte[] bArr);

    protected abstract void onPing(String str);

    protected abstract void onPong(String str);

    protected abstract void report(Exception exception);

    protected abstract void sendFrame(byte[] bArr);

    private static byte[] mask(byte[] payload, byte[] mask, int offset) {
        if (mask.length == 0) {
            return payload;
        }
        for (int i = 0; i < payload.length - offset; i++) {
            payload[offset + i] = (byte) (payload[offset + i] ^ mask[i % 4]);
        }
        return payload;
    }

    private byte[] inflate(byte[] payload) throws DataFormatException {
        ByteArrayOutputStream inflated = new ByteArrayOutputStream();
        this.mInflater.setInput(payload);
        while (!this.mInflater.needsInput()) {
            inflated.write(this.mInflateBuffer, 0, this.mInflater.inflate(this.mInflateBuffer));
        }
        this.mInflater.setInput(new byte[]{(byte) 0, (byte) 0, (byte) -1, (byte) -1});
        while (!this.mInflater.needsInput()) {
            inflated.write(this.mInflateBuffer, 0, this.mInflater.inflate(this.mInflateBuffer));
        }
        return inflated.toByteArray();
    }

    public void setMasking(boolean masking) {
        this.mMasking = masking;
    }

    public void setDeflate(boolean deflate) {
        this.mDeflate = deflate;
    }

    void parse() {
        switch (this.mStage) {
            case 0:
                this.mReader.read(1, this.mStage0);
                return;
            case 1:
                this.mReader.read(1, this.mStage1);
                return;
            case 2:
                this.mReader.read(this.mLengthSize, this.mStage2);
                return;
            case 3:
                this.mReader.read(4, this.mStage3);
                return;
            case 4:
                this.mReader.read(this.mLength, this.mStage4);
                return;
            default:
                return;
        }
    }

    public HybiParser(DataEmitter socket) {
        socket.setDataCallback(this.mReader);
        parse();
    }

    private void parseOpcode(byte data) throws ProtocolError {
        boolean rsv1 = (data & 64) == 64;
        boolean rsv2 = (data & 32) == 32;
        boolean rsv3 = (data & 16) == 16;
        if ((this.mDeflate || !rsv1) && !rsv2) {
            if (!rsv3) {
                this.mFinal = (data & 128) == 128;
                this.mOpcode = data & 15;
                this.mDeflated = rsv1;
                this.mMask = new byte[0];
                this.mPayload = new byte[0];
                if (!OPCODES.contains(Integer.valueOf(this.mOpcode))) {
                    throw new ProtocolError("Bad opcode");
                } else if (FRAGMENTED_OPCODES.contains(Integer.valueOf(this.mOpcode)) || this.mFinal) {
                    this.mStage = 1;
                    return;
                } else {
                    throw new ProtocolError("Expected non-final packet");
                }
            }
        }
        throw new ProtocolError("RSV not zero");
    }

    private void parseLength(byte data) {
        this.mMasked = (data & 128) == 128;
        this.mLength = data & 127;
        if (this.mLength < 0 || this.mLength > 125) {
            this.mLengthSize = this.mLength == 126 ? 2 : 8;
            this.mStage = 2;
            return;
        }
        this.mStage = this.mMasked ? 3 : 4;
    }

    private void parseExtendedLength(byte[] buffer) throws ProtocolError {
        this.mLength = getInteger(buffer);
        this.mStage = this.mMasked ? 3 : 4;
    }

    public byte[] frame(String data) {
        return frame(1, data, -1);
    }

    public byte[] frame(byte[] data) {
        return frame(2, data, -1);
    }

    public byte[] frame(byte[] data, int offset, int length) {
        return frame(2, data, -1, offset, length);
    }

    public byte[] pingFrame(String data) {
        return frame(9, data, -1);
    }

    public byte[] pongFrame(String data) {
        return frame(10, data, -1);
    }

    private byte[] frame(int opcode, byte[] data, int errorCode) {
        return frame(opcode, data, errorCode, 0, data.length);
    }

    private byte[] frame(int opcode, String data, int errorCode) {
        return frame(opcode, decode(data), errorCode);
    }

    private byte[] frame(int opcode, byte[] data, int errorCode, int dataOffset, int dataLength) {
        int i = errorCode;
        int i2 = dataOffset;
        if (this.mClosed) {
            return null;
        }
        int insert;
        byte[] buffer = data;
        int insert2 = i > 0 ? 2 : 0;
        int length = (dataLength + insert2) - i2;
        int header = length <= 125 ? 2 : length <= SupportMenu.USER_MASK ? 4 : 10;
        int offset = (r0.mMasking ? 4 : 0) + header;
        int masked = r0.mMasking ? 128 : 0;
        byte[] frame = new byte[(length + offset)];
        frame[0] = (byte) (((byte) opcode) | -128);
        if (length <= 125) {
            frame[1] = (byte) (masked | length);
        } else if (length <= SupportMenu.USER_MASK) {
            frame[1] = (byte) (masked | 126);
            frame[2] = (byte) (length / 256);
            frame[3] = (byte) (length & 255);
        } else {
            frame[1] = (byte) (masked | 127);
            insert = insert2;
            frame[2] = (byte) ((int) ((((long) length) / _2_TO_56_) & 255));
            frame[3] = (byte) ((int) ((((long) length) / _2_TO_48_) & 255));
            frame[4] = (byte) ((int) ((((long) length) / _2_TO_40_) & 255));
            frame[5] = (byte) ((int) ((((long) length) / _2_TO_32_) & 255));
            frame[6] = (byte) ((int) ((((long) length) / _2_TO_24) & 255));
            frame[7] = (byte) ((int) ((((long) length) / 65536) & 255));
            frame[8] = (byte) ((int) ((((long) length) / 256) & 255));
            frame[9] = (byte) (length & 255);
            if (i > 0) {
                frame[offset] = (byte) ((i / 256) & 255);
                frame[offset + 1] = (byte) (i & 255);
            }
            System.arraycopy(buffer, i2, frame, offset + insert, dataLength - i2);
            if (r0.mMasking) {
                byte[] mask = new byte[]{(byte) ((int) Math.floor(Math.random() * 256.0d)), (byte) ((int) Math.floor(Math.random() * 256.0d)), (byte) ((int) Math.floor(Math.random() * 256.0d)), (byte) ((int) Math.floor(Math.random() * 256.0d))};
                System.arraycopy(mask, 0, frame, header, mask.length);
                mask(frame, mask, offset);
            }
            return frame;
        }
        insert = insert2;
        if (i > 0) {
            frame[offset] = (byte) ((i / 256) & 255);
            frame[offset + 1] = (byte) (i & 255);
        }
        System.arraycopy(buffer, i2, frame, offset + insert, dataLength - i2);
        if (r0.mMasking) {
            byte[] mask2 = new byte[]{(byte) ((int) Math.floor(Math.random() * 256.0d)), (byte) ((int) Math.floor(Math.random() * 256.0d)), (byte) ((int) Math.floor(Math.random() * 256.0d)), (byte) ((int) Math.floor(Math.random() * 256.0d))};
            System.arraycopy(mask2, 0, frame, header, mask2.length);
            mask(frame, mask2, offset);
        }
        return frame;
    }

    public void close(int code, String reason) {
        if (!this.mClosed) {
            sendFrame(frame(8, reason, code));
            this.mClosed = true;
        }
    }

    private void emitFrame() throws IOException {
        int code = 0;
        byte[] payload = mask(this.mPayload, this.mMask, 0);
        if (this.mDeflated) {
            try {
                payload = inflate(payload);
            } catch (DataFormatException e) {
                throw new IOException("Invalid deflated data");
            }
        }
        int opcode = this.mOpcode;
        if (opcode == 0) {
            if (this.mMode == 0) {
                throw new ProtocolError("Mode was not set.");
            }
            this.mBuffer.write(payload);
            if (this.mFinal) {
                byte[] message = this.mBuffer.toByteArray();
                if (this.mMode == 1) {
                    onMessage(encode(message));
                } else {
                    onMessage(message);
                }
                reset();
            }
        } else if (opcode == 1) {
            if (this.mFinal) {
                onMessage(encode(payload));
                return;
            }
            this.mMode = 1;
            this.mBuffer.write(payload);
        } else if (opcode == 2) {
            if (this.mFinal) {
                onMessage(payload);
                return;
            }
            this.mMode = 2;
            this.mBuffer.write(payload);
        } else if (opcode == 8) {
            if (payload.length >= 2) {
                code = ((payload[0] & 255) * 256) + (payload[1] & 255);
            }
            onDisconnect(code, payload.length > 2 ? encode(slice(payload, 2)) : null);
        } else if (opcode == 9) {
            if (payload.length > 125) {
                throw new ProtocolError("Ping payload too large");
            }
            String message2 = encode(payload);
            sendFrame(frame(10, payload, -1));
            onPing(message2);
        } else if (opcode == 10) {
            onPong(encode(payload));
        }
    }

    private void reset() {
        this.mMode = 0;
        this.mBuffer.reset();
    }

    private String encode(byte[] buffer) {
        try {
            return new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] decode(String string) {
        try {
            return string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private int getInteger(byte[] bytes) throws ProtocolError {
        long i = byteArrayToLong(bytes, 0, bytes.length);
        if (i >= 0) {
            if (i <= 2147483647L) {
                return (int) i;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad integer: ");
        stringBuilder.append(i);
        throw new ProtocolError(stringBuilder.toString());
    }

    private byte[] slice(byte[] array, int start) {
        byte[] copy = new byte[(array.length - start)];
        System.arraycopy(array, start, copy, 0, array.length - start);
        return copy;
    }

    protected void finalize() throws Throwable {
        Inflater inflater = this.mInflater;
        if (inflater != null) {
            try {
                inflater.end();
            } catch (Exception e) {
                Log.e(TAG, "inflater.end failed", e);
            }
        }
        super.finalize();
    }

    private static long byteArrayToLong(byte[] b, int offset, int length) {
        if (b.length < length) {
            throw new IllegalArgumentException("length must be less than or equal to b.length");
        }
        long value = 0;
        int i = 0;
        while (i < length) {
            int shift = ((length - 1) - i) * 8;
            i++;
            value += (long) ((b[i + offset] & 255) << shift);
        }
        return value;
    }
}
