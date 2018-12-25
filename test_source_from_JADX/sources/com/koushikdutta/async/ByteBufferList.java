package com.koushikdutta.async;

import android.annotation.TargetApi;
import android.os.Looper;
import com.koushikdutta.async.util.Charsets;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

@TargetApi(9)
public class ByteBufferList {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final ByteBuffer EMPTY_BYTEBUFFER = ByteBuffer.allocate(0);
    private static final Object LOCK = new Object();
    public static int MAX_ITEM_SIZE = 262144;
    private static int MAX_SIZE = 1048576;
    static int currentSize = 0;
    static int maxItem = 0;
    static PriorityQueue<ByteBuffer> reclaimed = new PriorityQueue(8, new Reclaimer());
    ArrayDeque<ByteBuffer> mBuffers = new ArrayDeque();
    ByteOrder order = ByteOrder.BIG_ENDIAN;
    private int remaining = 0;

    static class Reclaimer implements Comparator<ByteBuffer> {
        Reclaimer() {
        }

        public int compare(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
            if (byteBuffer.capacity() == byteBuffer2.capacity()) {
                return 0;
            }
            if (byteBuffer.capacity() > byteBuffer2.capacity()) {
                return 1;
            }
            return -1;
        }
    }

    public ByteOrder order() {
        return this.order;
    }

    public ByteBufferList order(ByteOrder order) {
        this.order = order;
        return this;
    }

    public ByteBufferList(ByteBuffer... b) {
        addAll(b);
    }

    public ByteBufferList(byte[] buf) {
        add(ByteBuffer.wrap(buf));
    }

    public ByteBufferList addAll(ByteBuffer... bb) {
        for (ByteBuffer b : bb) {
            add(b);
        }
        return this;
    }

    public ByteBufferList addAll(ByteBufferList... bb) {
        for (ByteBufferList b : bb) {
            b.get(this);
        }
        return this;
    }

    public byte[] getBytes(int length) {
        byte[] ret = new byte[length];
        get(ret);
        return ret;
    }

    public byte[] getAllByteArray() {
        if (this.mBuffers.size() == 1) {
            ByteBuffer peek = (ByteBuffer) this.mBuffers.peek();
            if (peek.capacity() == remaining() && peek.isDirect()) {
                this.remaining = 0;
                return ((ByteBuffer) this.mBuffers.remove()).array();
            }
        }
        byte[] ret = new byte[remaining()];
        get(ret);
        return ret;
    }

    public ByteBuffer[] getAllArray() {
        ByteBuffer[] ret = (ByteBuffer[]) this.mBuffers.toArray(new ByteBuffer[this.mBuffers.size()]);
        this.mBuffers.clear();
        this.remaining = 0;
        return ret;
    }

    public boolean isEmpty() {
        return this.remaining == 0;
    }

    public int remaining() {
        return this.remaining;
    }

    public boolean hasRemaining() {
        return remaining() > 0;
    }

    public short peekShort() {
        return read(2).duplicate().getShort();
    }

    public int peekInt() {
        return read(4).duplicate().getInt();
    }

    public long peekLong() {
        return read(8).duplicate().getLong();
    }

    public byte[] peekBytes(int size) {
        byte[] ret = new byte[size];
        read(size).duplicate().get(ret);
        return ret;
    }

    public ByteBufferList skip(int length) {
        get(null, 0, length);
        return this;
    }

    public int getInt() {
        int ret = read(4).getInt();
        this.remaining -= 4;
        return ret;
    }

    public char getByteChar() {
        char ret = (char) read(1).get();
        this.remaining--;
        return ret;
    }

    public short getShort() {
        short ret = read(2).getShort();
        this.remaining -= 2;
        return ret;
    }

    public byte get() {
        byte ret = read(1).get();
        this.remaining--;
        return ret;
    }

    public long getLong() {
        long ret = read(8).getLong();
        this.remaining -= 8;
        return ret;
    }

    public void get(byte[] bytes) {
        get(bytes, 0, bytes.length);
    }

    public void get(byte[] bytes, int offset, int length) {
        if (remaining() < length) {
            throw new IllegalArgumentException("length");
        }
        int offset2 = offset;
        offset = length;
        while (offset > 0) {
            ByteBuffer b = (ByteBuffer) this.mBuffers.peek();
            int read = Math.min(b.remaining(), offset);
            if (bytes != null) {
                b.get(bytes, offset2, read);
            } else {
                b.position(b.position() + read);
            }
            offset -= read;
            offset2 += read;
            if (b.remaining() == 0) {
                ByteBuffer removed = (ByteBuffer) this.mBuffers.remove();
                reclaim(b);
            }
        }
        this.remaining -= length;
    }

    public void get(ByteBufferList into, int length) {
        if (remaining() < length) {
            throw new IllegalArgumentException("length");
        }
        int offset = 0;
        while (offset < length) {
            ByteBuffer b = (ByteBuffer) this.mBuffers.remove();
            int remaining = b.remaining();
            if (remaining == 0) {
                reclaim(b);
            } else if (offset + remaining > length) {
                int need = length - offset;
                ByteBuffer subset = obtain(need);
                subset.limit(need);
                b.get(subset.array(), 0, need);
                into.add(subset);
                this.mBuffers.addFirst(b);
                break;
            } else {
                into.add(b);
                offset += remaining;
            }
        }
        this.remaining -= length;
    }

    public void get(ByteBufferList into) {
        get(into, remaining());
    }

    public ByteBufferList get(int length) {
        ByteBufferList ret = new ByteBufferList();
        get(ret, length);
        return ret.order(this.order);
    }

    public ByteBuffer getAll() {
        if (remaining() == 0) {
            return EMPTY_BYTEBUFFER;
        }
        read(remaining());
        return remove();
    }

    private ByteBuffer read(int count) {
        if (remaining() < count) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("count : ");
            stringBuilder.append(remaining());
            stringBuilder.append("/");
            stringBuilder.append(count);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        ByteBuffer first = (ByteBuffer) this.mBuffers.peek();
        while (first != null && !first.hasRemaining()) {
            reclaim((ByteBuffer) this.mBuffers.remove());
            first = (ByteBuffer) this.mBuffers.peek();
        }
        if (first == null) {
            return EMPTY_BYTEBUFFER;
        }
        if (first.remaining() >= count) {
            return first.order(this.order);
        }
        ByteBuffer ret = obtain(count);
        ret.limit(count);
        byte[] bytes = ret.array();
        int offset = 0;
        ByteBuffer bb = null;
        while (offset < count) {
            bb = (ByteBuffer) this.mBuffers.remove();
            int toRead = Math.min(count - offset, bb.remaining());
            bb.get(bytes, offset, toRead);
            offset += toRead;
            if (bb.remaining() == 0) {
                reclaim(bb);
                bb = null;
            }
        }
        if (bb != null && bb.remaining() > 0) {
            this.mBuffers.addFirst(bb);
        }
        this.mBuffers.addFirst(ret);
        return ret.order(this.order);
    }

    public void trim() {
        read(0);
    }

    public ByteBufferList add(ByteBufferList b) {
        b.get(this);
        return this;
    }

    public ByteBufferList add(ByteBuffer b) {
        if (b.remaining() <= 0) {
            reclaim(b);
            return this;
        }
        addRemaining(b.remaining());
        if (this.mBuffers.size() > 0) {
            ByteBuffer last = (ByteBuffer) this.mBuffers.getLast();
            if (last.capacity() - last.limit() >= b.remaining()) {
                last.mark();
                last.position(last.limit());
                last.limit(last.capacity());
                last.put(b);
                last.limit(last.position());
                last.reset();
                reclaim(b);
                trim();
                return this;
            }
        }
        this.mBuffers.add(b);
        trim();
        return this;
    }

    public void addFirst(ByteBuffer b) {
        if (b.remaining() <= 0) {
            reclaim(b);
            return;
        }
        addRemaining(b.remaining());
        if (this.mBuffers.size() > 0) {
            ByteBuffer first = (ByteBuffer) this.mBuffers.getFirst();
            if (first.position() >= b.remaining()) {
                first.position(first.position() - b.remaining());
                first.mark();
                first.put(b);
                first.reset();
                reclaim(b);
                return;
            }
        }
        this.mBuffers.addFirst(b);
    }

    private void addRemaining(int remaining) {
        if (remaining() >= 0) {
            this.remaining += remaining;
        }
    }

    public void recycle() {
        while (this.mBuffers.size() > 0) {
            reclaim((ByteBuffer) this.mBuffers.remove());
        }
        this.remaining = 0;
    }

    public ByteBuffer remove() {
        ByteBuffer ret = (ByteBuffer) this.mBuffers.remove();
        this.remaining -= ret.remaining();
        return ret;
    }

    public int size() {
        return this.mBuffers.size();
    }

    public void spewString() {
        System.out.println(peekString());
    }

    public String peekString() {
        return peekString(null);
    }

    public String peekString(Charset charset) {
        if (charset == null) {
            charset = Charsets.US_ASCII;
        }
        StringBuilder builder = new StringBuilder();
        Iterator it = this.mBuffers.iterator();
        while (it.hasNext()) {
            byte[] bytes;
            int offset;
            int length;
            ByteBuffer bb = (ByteBuffer) it.next();
            if (bb.isDirect()) {
                bytes = new byte[bb.remaining()];
                offset = 0;
                length = bb.remaining();
                bb.get(bytes);
            } else {
                bytes = bb.array();
                offset = bb.arrayOffset() + bb.position();
                length = bb.remaining();
            }
            builder.append(new String(bytes, offset, length, charset));
        }
        return builder.toString();
    }

    public String readString() {
        return readString(null);
    }

    public String readString(Charset charset) {
        String ret = peekString(charset);
        recycle();
        return ret;
    }

    private static PriorityQueue<ByteBuffer> getReclaimed() {
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper == null || Thread.currentThread() != mainLooper.getThread()) {
            return reclaimed;
        }
        return null;
    }

    public static void setMaxPoolSize(int size) {
        MAX_SIZE = size;
    }

    public static void setMaxItemSize(int size) {
        MAX_ITEM_SIZE = size;
    }

    private static boolean reclaimedContains(ByteBuffer b) {
        Iterator it = reclaimed.iterator();
        while (it.hasNext()) {
            if (((ByteBuffer) it.next()) == b) {
                return true;
            }
        }
        return false;
    }

    public static void reclaim(ByteBuffer b) {
        if (b != null) {
            if (!b.isDirect()) {
                if (b.arrayOffset() == 0) {
                    if (b.array().length == b.capacity()) {
                        if (b.capacity() >= 8192 && b.capacity() <= MAX_ITEM_SIZE) {
                            PriorityQueue<ByteBuffer> r = getReclaimed();
                            if (r != null) {
                                synchronized (LOCK) {
                                    while (currentSize > MAX_SIZE && r.size() > 0 && ((ByteBuffer) r.peek()).capacity() < b.capacity()) {
                                        currentSize -= ((ByteBuffer) r.remove()).capacity();
                                    }
                                    if (currentSize > MAX_SIZE) {
                                        return;
                                    }
                                    b.position(0);
                                    b.limit(b.capacity());
                                    currentSize += b.capacity();
                                    r.add(b);
                                    maxItem = Math.max(maxItem, b.capacity());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static ByteBuffer obtain(int size) {
        if (size <= maxItem) {
            PriorityQueue<ByteBuffer> r = getReclaimed();
            if (r != null) {
                synchronized (LOCK) {
                    while (r.size() > 0) {
                        ByteBuffer ret = (ByteBuffer) r.remove();
                        if (r.size() == 0) {
                            maxItem = 0;
                        }
                        currentSize -= ret.capacity();
                        if (ret.capacity() >= size) {
                            return ret;
                        }
                    }
                }
            }
        }
        return ByteBuffer.allocate(Math.max(8192, size));
    }

    public static void obtainArray(ByteBuffer[] arr, int size) {
        PriorityQueue<ByteBuffer> r = getReclaimed();
        int i = 0;
        int total = 0;
        if (r != null) {
            synchronized (LOCK) {
                while (r.size() > 0 && total < size && i < arr.length - 1) {
                    try {
                        ByteBuffer b = (ByteBuffer) r.remove();
                        currentSize -= b.capacity();
                        total += Math.min(size - total, b.capacity());
                        int index = i + 1;
                        try {
                            arr[i] = b;
                            i = index;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            i = index;
                        }
                    } catch (Throwable th3) {
                        th2 = th3;
                    }
                }
            }
        }
        if (total < size) {
            int index2 = i + 1;
            arr[i] = ByteBuffer.allocate(Math.max(8192, size - total));
            i = index2;
        }
        for (int i2 = i; i2 < arr.length; i2++) {
            arr[i2] = EMPTY_BYTEBUFFER;
        }
        return;
        throw th2;
    }

    public static void writeOutputStream(OutputStream out, ByteBuffer b) throws IOException {
        byte[] bytes;
        int offset;
        int length;
        if (b.isDirect()) {
            bytes = new byte[b.remaining()];
            offset = 0;
            length = b.remaining();
            b.get(bytes);
        } else {
            bytes = b.array();
            offset = b.arrayOffset() + b.position();
            length = b.remaining();
        }
        out.write(bytes, offset, length);
    }
}
