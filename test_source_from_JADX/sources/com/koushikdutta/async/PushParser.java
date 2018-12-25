package com.koushikdutta.async;

import android.util.Log;
import com.koushikdutta.async.callback.DataCallback;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class PushParser implements DataCallback {
    static Hashtable<Class, Method> mTable = new Hashtable();
    private ArrayList<Object> args = new ArrayList();
    private Waiter byteArgWaiter = new Waiter(1) {
        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            PushParser.this.args.add(Byte.valueOf(bb.get()));
            return null;
        }
    };
    private ParseCallback<byte[]> byteArrayArgCallback = new C10966();
    private ParseCallback<ByteBufferList> byteBufferListArgCallback = new C10977();
    private Waiter intArgWaiter = new Waiter(4) {
        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            PushParser.this.args.add(Integer.valueOf(bb.getInt()));
            return null;
        }
    };
    private Waiter longArgWaiter = new Waiter(8) {
        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            PushParser.this.args.add(Long.valueOf(bb.getLong()));
            return null;
        }
    };
    DataEmitter mEmitter;
    private LinkedList<Waiter> mWaiting = new LinkedList();
    private Waiter noopArgWaiter = new Waiter(0) {
        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            PushParser.this.args.add(null);
            return null;
        }
    };
    ByteOrder order = ByteOrder.BIG_ENDIAN;
    ByteBufferList pending = new ByteBufferList();
    private Waiter shortArgWaiter = new Waiter(2) {
        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            PushParser.this.args.add(Short.valueOf(bb.getShort()));
            return null;
        }
    };
    private ParseCallback<byte[]> stringArgCallback = new C10988();

    public interface ParseCallback<T> {
        void parsed(T t);
    }

    static abstract class Waiter {
        int length;

        public abstract Waiter onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList);

        public Waiter(int length) {
            this.length = length;
        }
    }

    /* renamed from: com.koushikdutta.async.PushParser$6 */
    class C10966 implements ParseCallback<byte[]> {
        C10966() {
        }

        public void parsed(byte[] data) {
            PushParser.this.args.add(data);
        }
    }

    /* renamed from: com.koushikdutta.async.PushParser$7 */
    class C10977 implements ParseCallback<ByteBufferList> {
        C10977() {
        }

        public void parsed(ByteBufferList data) {
            PushParser.this.args.add(data);
        }
    }

    /* renamed from: com.koushikdutta.async.PushParser$8 */
    class C10988 implements ParseCallback<byte[]> {
        C10988() {
        }

        public void parsed(byte[] data) {
            PushParser.this.args.add(new String(data));
        }
    }

    static class ByteArrayWaiter extends Waiter {
        ParseCallback<byte[]> callback;

        public ByteArrayWaiter(int length, ParseCallback<byte[]> callback) {
            super(length);
            if (length <= 0) {
                throw new IllegalArgumentException("length should be > 0");
            }
            this.callback = callback;
        }

        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            byte[] bytes = new byte[this.length];
            bb.get(bytes);
            this.callback.parsed(bytes);
            return null;
        }
    }

    static class ByteBufferListWaiter extends Waiter {
        ParseCallback<ByteBufferList> callback;

        public ByteBufferListWaiter(int length, ParseCallback<ByteBufferList> callback) {
            super(length);
            if (length <= 0) {
                throw new IllegalArgumentException("length should be > 0");
            }
            this.callback = callback;
        }

        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            this.callback.parsed(bb.get(this.length));
            return null;
        }
    }

    static class IntWaiter extends Waiter {
        ParseCallback<Integer> callback;

        public IntWaiter(ParseCallback<Integer> callback) {
            super(4);
            this.callback = callback;
        }

        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            this.callback.parsed(Integer.valueOf(bb.getInt()));
            return null;
        }
    }

    static class LenByteArrayWaiter extends Waiter {
        private final ParseCallback<byte[]> callback;

        public LenByteArrayWaiter(ParseCallback<byte[]> callback) {
            super(4);
            this.callback = callback;
        }

        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            int length = bb.getInt();
            if (length != 0) {
                return new ByteArrayWaiter(length, this.callback);
            }
            this.callback.parsed(new byte[0]);
            return null;
        }
    }

    static class LenByteBufferListWaiter extends Waiter {
        private final ParseCallback<ByteBufferList> callback;

        public LenByteBufferListWaiter(ParseCallback<ByteBufferList> callback) {
            super(4);
            this.callback = callback;
        }

        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            return new ByteBufferListWaiter(bb.getInt(), this.callback);
        }
    }

    private class TapWaiter extends Waiter {
        private final TapCallback callback;

        public TapWaiter(TapCallback callback) {
            super(null);
            this.callback = callback;
        }

        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            Method method = PushParser.getTap(this.callback);
            method.setAccessible(true);
            try {
                method.invoke(this.callback, PushParser.this.args.toArray());
            } catch (Exception e) {
                Log.e("PushParser", "Error while invoking tap callback", e);
            }
            PushParser.this.args.clear();
            return null;
        }
    }

    static class UntilWaiter extends Waiter {
        DataCallback callback;
        byte value;

        public UntilWaiter(byte value, DataCallback callback) {
            super(1);
            this.value = value;
            this.callback = callback;
        }

        public Waiter onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            boolean found = true;
            ByteBufferList cb = new ByteBufferList();
            while (bb.size() > 0) {
                ByteBuffer b = bb.remove();
                b.mark();
                boolean found2 = found;
                int index = 0;
                while (b.remaining() > 0) {
                    boolean z = b.get() == this.value;
                    found2 = z;
                    if (z) {
                        break;
                    }
                    index++;
                }
                b.reset();
                if (found2) {
                    bb.addFirst(b);
                    bb.get(cb, index);
                    bb.get();
                    found = found2;
                    break;
                }
                cb.add(b);
                found = found2;
            }
            this.callback.onDataAvailable(emitter, cb);
            if (found) {
                return null;
            }
            return this;
        }
    }

    public PushParser setOrder(ByteOrder order) {
        this.order = order;
        return this;
    }

    public PushParser(DataEmitter s) {
        this.mEmitter = s;
        this.mEmitter.setDataCallback(this);
    }

    public PushParser readInt(ParseCallback<Integer> callback) {
        this.mWaiting.add(new IntWaiter(callback));
        return this;
    }

    public PushParser readByteArray(int length, ParseCallback<byte[]> callback) {
        this.mWaiting.add(new ByteArrayWaiter(length, callback));
        return this;
    }

    public PushParser readByteBufferList(int length, ParseCallback<ByteBufferList> callback) {
        this.mWaiting.add(new ByteBufferListWaiter(length, callback));
        return this;
    }

    public PushParser until(byte b, DataCallback callback) {
        this.mWaiting.add(new UntilWaiter(b, callback));
        return this;
    }

    public PushParser readByte() {
        this.mWaiting.add(this.byteArgWaiter);
        return this;
    }

    public PushParser readShort() {
        this.mWaiting.add(this.shortArgWaiter);
        return this;
    }

    public PushParser readInt() {
        this.mWaiting.add(this.intArgWaiter);
        return this;
    }

    public PushParser readLong() {
        this.mWaiting.add(this.longArgWaiter);
        return this;
    }

    public PushParser readByteArray(int length) {
        return length == -1 ? readLenByteArray() : readByteArray(length, this.byteArrayArgCallback);
    }

    public PushParser readLenByteArray() {
        this.mWaiting.add(new LenByteArrayWaiter(this.byteArrayArgCallback));
        return this;
    }

    public PushParser readByteBufferList(int length) {
        return length == -1 ? readLenByteBufferList() : readByteBufferList(length, this.byteBufferListArgCallback);
    }

    public PushParser readLenByteBufferList() {
        return readLenByteBufferList(this.byteBufferListArgCallback);
    }

    public PushParser readLenByteBufferList(ParseCallback<ByteBufferList> callback) {
        this.mWaiting.add(new LenByteBufferListWaiter(callback));
        return this;
    }

    public PushParser readString() {
        this.mWaiting.add(new LenByteArrayWaiter(this.stringArgCallback));
        return this;
    }

    public PushParser noop() {
        this.mWaiting.add(this.noopArgWaiter);
        return this;
    }

    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
        bb.get(this.pending);
        while (this.mWaiting.size() > 0 && this.pending.remaining() >= ((Waiter) this.mWaiting.peek()).length) {
            this.pending.order(this.order);
            Waiter next = ((Waiter) this.mWaiting.poll()).onDataAvailable(emitter, this.pending);
            if (next != null) {
                this.mWaiting.addFirst(next);
            }
        }
        if (this.mWaiting.size() == 0) {
            this.pending.get(bb);
        }
    }

    public void tap(TapCallback callback) {
        this.mWaiting.add(new TapWaiter(callback));
    }

    static Method getTap(TapCallback callback) {
        Method found = (Method) mTable.get(callback.getClass());
        if (found != null) {
            return found;
        }
        for (Method method : callback.getClass().getMethods()) {
            if ("tap".equals(method.getName())) {
                mTable.put(callback.getClass(), method);
                return method;
            }
        }
        Method[] candidates = callback.getClass().getDeclaredMethods();
        if (candidates.length == 1) {
            return candidates[0];
        }
        throw new AssertionError("-keep class * extends com.koushikdutta.async.TapCallback {\n    *;\n}\n");
    }
}
