package com.koushikdutta.async;

import android.util.Log;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.util.Allocator;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class AsyncNetworkSocket implements AsyncSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    Allocator allocator;
    boolean closeReported;
    private ChannelWrapper mChannel;
    CompletedCallback mClosedHander;
    private CompletedCallback mCompletedCallback;
    DataCallback mDataHandler;
    boolean mEndReported;
    private SelectionKey mKey;
    boolean mPaused = false;
    Exception mPendingEndException;
    private AsyncServer mServer;
    WritableCallback mWriteableHandler;
    private ByteBufferList pending = new ByteBufferList();
    InetSocketAddress socketAddress;

    /* renamed from: com.koushikdutta.async.AsyncNetworkSocket$2 */
    class C06252 implements Runnable {
        C06252() {
        }

        public void run() {
            AsyncNetworkSocket.this.pause();
        }
    }

    /* renamed from: com.koushikdutta.async.AsyncNetworkSocket$3 */
    class C06263 implements Runnable {
        C06263() {
        }

        public void run() {
            AsyncNetworkSocket.this.resume();
        }
    }

    AsyncNetworkSocket() {
    }

    public void end() {
        this.mChannel.shutdownOutput();
    }

    public boolean isChunked() {
        return this.mChannel.isChunked();
    }

    void attach(SocketChannel channel, InetSocketAddress socketAddress) throws IOException {
        this.socketAddress = socketAddress;
        this.allocator = new Allocator();
        this.mChannel = new SocketChannelWrapper(channel);
    }

    void attach(DatagramChannel channel) throws IOException {
        this.mChannel = new DatagramChannelWrapper(channel);
        this.allocator = new Allocator(8192);
    }

    ChannelWrapper getChannel() {
        return this.mChannel;
    }

    public void onDataWritable() {
        if (!this.mChannel.isChunked()) {
            this.mKey.interestOps(this.mKey.interestOps() & -5);
        }
        if (this.mWriteableHandler != null) {
            this.mWriteableHandler.onWriteable();
        }
    }

    void setup(AsyncServer server, SelectionKey key) {
        this.mServer = server;
        this.mKey = key;
    }

    public void write(final ByteBufferList list) {
        if (this.mServer.getAffinity() != Thread.currentThread()) {
            this.mServer.run(new Runnable() {
                public void run() {
                    AsyncNetworkSocket.this.write(list);
                }
            });
        } else if (this.mChannel.isConnected()) {
            try {
                int before = list.remaining();
                ByteBuffer[] arr = list.getAllArray();
                this.mChannel.write(arr);
                list.addAll(arr);
                handleRemaining(list.remaining());
                this.mServer.onDataSent(before - list.remaining());
            } catch (IOException e) {
                closeInternal();
                reportEndPending(e);
                reportClose(e);
            }
        }
    }

    private void handleRemaining(int remaining) throws IOException {
        if (!this.mKey.isValid()) {
            throw new IOException(new CancelledKeyException());
        } else if (remaining > 0) {
            this.mKey.interestOps(this.mKey.interestOps() | 4);
        } else {
            this.mKey.interestOps(this.mKey.interestOps() & -5);
        }
    }

    int onReadable() {
        spitPending();
        if (this.mPaused) {
            return 0;
        }
        int total = 0;
        boolean closed = false;
        try {
            ByteBuffer b = this.allocator.allocate();
            long read = (long) this.mChannel.read(b);
            if (read < 0) {
                closeInternal();
                closed = true;
            } else {
                total = (int) (((long) total) + read);
            }
            if (read > 0) {
                this.allocator.track(read);
                b.flip();
                this.pending.add(b);
                Util.emitAllData(this, this.pending);
            } else {
                ByteBufferList.reclaim(b);
            }
            if (closed) {
                reportEndPending(null);
                reportClose(null);
            }
        } catch (Exception e) {
            closeInternal();
            reportEndPending(e);
            reportClose(e);
        }
        return total;
    }

    protected void reportClose(Exception e) {
        if (!this.closeReported) {
            this.closeReported = true;
            if (this.mClosedHander != null) {
                this.mClosedHander.onCompleted(e);
                this.mClosedHander = null;
            }
        }
    }

    public void close() {
        closeInternal();
        reportClose(null);
    }

    public void closeInternal() {
        this.mKey.cancel();
        try {
            this.mChannel.close();
        } catch (IOException e) {
        }
    }

    public void setWriteableCallback(WritableCallback handler) {
        this.mWriteableHandler = handler;
    }

    public void setDataCallback(DataCallback callback) {
        this.mDataHandler = callback;
    }

    public DataCallback getDataCallback() {
        return this.mDataHandler;
    }

    public void setClosedCallback(CompletedCallback handler) {
        this.mClosedHander = handler;
    }

    public CompletedCallback getClosedCallback() {
        return this.mClosedHander;
    }

    public WritableCallback getWriteableCallback() {
        return this.mWriteableHandler;
    }

    void reportEnd(Exception e) {
        if (!this.mEndReported) {
            this.mEndReported = true;
            if (this.mCompletedCallback != null) {
                this.mCompletedCallback.onCompleted(e);
            } else if (e != null) {
                Log.e(AsyncServer.LOGTAG, "Unhandled exception", e);
            }
        }
    }

    void reportEndPending(Exception e) {
        if (this.pending.hasRemaining()) {
            this.mPendingEndException = e;
        } else {
            reportEnd(e);
        }
    }

    public void setEndCallback(CompletedCallback callback) {
        this.mCompletedCallback = callback;
    }

    public CompletedCallback getEndCallback() {
        return this.mCompletedCallback;
    }

    public boolean isOpen() {
        return this.mChannel.isConnected() && this.mKey.isValid();
    }

    public void pause() {
        if (this.mServer.getAffinity() != Thread.currentThread()) {
            this.mServer.run(new C06252());
        } else if (!this.mPaused) {
            this.mPaused = true;
            try {
                this.mKey.interestOps(this.mKey.interestOps() & -2);
            } catch (Exception e) {
            }
        }
    }

    private void spitPending() {
        if (this.pending.hasRemaining()) {
            Util.emitAllData(this, this.pending);
        }
    }

    public void resume() {
        if (this.mServer.getAffinity() != Thread.currentThread()) {
            this.mServer.run(new C06263());
        } else if (this.mPaused) {
            this.mPaused = false;
            try {
                this.mKey.interestOps(this.mKey.interestOps() | 1);
            } catch (Exception e) {
            }
            spitPending();
            if (!isOpen()) {
                reportEndPending(this.mPendingEndException);
            }
        }
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    public AsyncServer getServer() {
        return this.mServer;
    }

    public InetSocketAddress getRemoteAddress() {
        return this.socketAddress;
    }

    public int getLocalPort() {
        return this.mChannel.getLocalPort();
    }

    public Object getSocket() {
        return getChannel().getSocket();
    }

    public String charset() {
        return null;
    }
}
