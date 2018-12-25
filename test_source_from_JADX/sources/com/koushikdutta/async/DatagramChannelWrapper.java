package com.koushikdutta.async;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

class DatagramChannelWrapper extends ChannelWrapper {
    InetSocketAddress address;
    DatagramChannel mChannel;

    public int getLocalPort() {
        return this.mChannel.socket().getLocalPort();
    }

    public InetSocketAddress getRemoteAddress() {
        return this.address;
    }

    public void disconnect() throws IOException {
        this.mChannel.disconnect();
    }

    DatagramChannelWrapper(DatagramChannel channel) throws IOException {
        super(channel);
        this.mChannel = channel;
    }

    public int read(ByteBuffer buffer) throws IOException {
        if (isConnected()) {
            this.address = null;
            return this.mChannel.read(buffer);
        }
        int position = buffer.position();
        this.address = (InetSocketAddress) this.mChannel.receive(buffer);
        if (this.address == null) {
            return -1;
        }
        return buffer.position() - position;
    }

    public boolean isConnected() {
        return this.mChannel.isConnected();
    }

    public int write(ByteBuffer src) throws IOException {
        return this.mChannel.write(src);
    }

    public int write(ByteBuffer[] src) throws IOException {
        return (int) this.mChannel.write(src);
    }

    public SelectionKey register(Selector sel, int ops) throws ClosedChannelException {
        return this.mChannel.register(sel, ops);
    }

    public boolean isChunked() {
        return true;
    }

    public SelectionKey register(Selector sel) throws ClosedChannelException {
        return register(sel, 1);
    }

    public void shutdownOutput() {
    }

    public void shutdownInput() {
    }

    public long read(ByteBuffer[] byteBuffers) throws IOException {
        return this.mChannel.read(byteBuffers);
    }

    public long read(ByteBuffer[] byteBuffers, int i, int i2) throws IOException {
        return this.mChannel.read(byteBuffers, i, i2);
    }

    public Object getSocket() {
        return this.mChannel.socket();
    }
}
