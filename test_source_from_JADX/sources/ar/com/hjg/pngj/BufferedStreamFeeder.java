package ar.com.hjg.pngj;

import java.io.InputStream;

public class BufferedStreamFeeder {
    private static final int DEFAULTSIZE = 8192;
    private byte[] buf;
    private boolean closeStream;
    private boolean eof;
    private boolean failIfNoFeed;
    private int offset;
    private int pendinglen;
    private InputStream stream;

    public BufferedStreamFeeder(InputStream is) {
        this(is, 8192);
    }

    public BufferedStreamFeeder(InputStream is, int bufsize) {
        this.eof = false;
        this.closeStream = true;
        this.failIfNoFeed = false;
        this.stream = is;
        this.buf = new byte[(bufsize < 1 ? 8192 : bufsize)];
    }

    public InputStream getStream() {
        return this.stream;
    }

    public int feed(IBytesConsumer consumer) {
        return feed(consumer, -1);
    }

    public int feed(IBytesConsumer consumer, int maxbytes) {
        int n = 0;
        if (this.pendinglen == 0) {
            refillBuffer();
        }
        int tofeed = (maxbytes <= 0 || maxbytes >= this.pendinglen) ? this.pendinglen : maxbytes;
        if (tofeed > 0) {
            n = consumer.consume(this.buf, this.offset, tofeed);
            if (n > 0) {
                this.offset += n;
                this.pendinglen -= n;
            }
        }
        if (n >= 1 || !this.failIfNoFeed) {
            return n;
        }
        throw new PngjInputException("failed feed bytes");
    }

    public boolean feedFixed(IBytesConsumer consumer, int nbytes) {
        int remain = nbytes;
        while (remain > 0) {
            int n = feed(consumer, remain);
            if (n < 1) {
                return false;
            }
            remain -= n;
        }
        return true;
    }

    protected void refillBuffer() {
        if (this.pendinglen <= 0) {
            if (!this.eof) {
                try {
                    this.offset = 0;
                    this.pendinglen = this.stream.read(this.buf);
                    if (this.pendinglen < 0) {
                        close();
                    }
                } catch (Throwable e) {
                    throw new PngjInputException(e);
                }
            }
        }
    }

    public boolean hasMoreToFeed() {
        boolean z = false;
        if (this.eof) {
            if (this.pendinglen > 0) {
                z = true;
            }
            return z;
        }
        refillBuffer();
        if (this.pendinglen > 0) {
            z = true;
        }
        return z;
    }

    public void setCloseStream(boolean closeStream) {
        this.closeStream = closeStream;
    }

    public void close() {
        this.eof = true;
        this.buf = null;
        this.pendinglen = 0;
        this.offset = 0;
        if (this.stream != null && this.closeStream) {
            try {
                this.stream.close();
            } catch (Exception e) {
            }
        }
        this.stream = null;
    }

    public void setInputStream(InputStream is) {
        this.stream = is;
        this.eof = false;
    }

    public boolean isEof() {
        return this.eof;
    }

    public void setFailIfNoFeed(boolean failIfNoFeed) {
        this.failIfNoFeed = failIfNoFeed;
    }
}
