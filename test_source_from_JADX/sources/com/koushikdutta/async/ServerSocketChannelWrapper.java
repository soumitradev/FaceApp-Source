package com.koushikdutta.async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

class ServerSocketChannelWrapper extends ChannelWrapper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    ServerSocketChannel mChannel;

    public void shutdownOutput() {
    }

    public void shutdownInput() {
    }

    public int getLocalPort() {
        return this.mChannel.socket().getLocalPort();
    }

    ServerSocketChannelWrapper(ServerSocketChannel channel) throws IOException {
        super(channel);
        this.mChannel = channel;
    }

    public int read(ByteBuffer buffer) throws IOException {
        String msg = "Can't read ServerSocketChannel";
        throw new IOException("Can't read ServerSocketChannel");
    }

    public boolean isConnected() {
        return false;
    }

    public int write(ByteBuffer src) throws IOException {
        String msg = "Can't write ServerSocketChannel";
        throw new IOException("Can't write ServerSocketChannel");
    }

    public SelectionKey register(Selector sel) throws ClosedChannelException {
        return this.mChannel.register(sel, 16);
    }

    public int write(ByteBuffer[] src) throws IOException {
        String msg = "Can't write ServerSocketChannel";
        throw new IOException("Can't write ServerSocketChannel");
    }

    public long read(ByteBuffer[] byteBuffers) throws IOException {
        String msg = "Can't read ServerSocketChannel";
        throw new IOException("Can't read ServerSocketChannel");
    }

    public long read(ByteBuffer[] byteBuffers, int i, int i2) throws IOException {
        String msg = "Can't read ServerSocketChannel";
        throw new IOException("Can't read ServerSocketChannel");
    }

    public Object getSocket() {
        return this.mChannel.socket();
    }
}
