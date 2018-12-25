package ar.com.hjg.pngj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

abstract class ProgressiveOutputStream extends ByteArrayOutputStream {
    private long countFlushed = 0;
    private int size;

    protected abstract void flushBuffer(byte[] bArr, int i);

    public ProgressiveOutputStream(int size) {
        this.size = size;
    }

    public final void close() throws IOException {
        try {
            flush();
        } catch (Exception e) {
        }
        super.close();
    }

    public final void flush() throws IOException {
        super.flush();
        checkFlushBuffer(true);
    }

    public final void write(byte[] b, int off, int len) {
        super.write(b, off, len);
        checkFlushBuffer(false);
    }

    public final void write(byte[] b) throws IOException {
        super.write(b);
        checkFlushBuffer(false);
    }

    public final void write(int arg0) {
        super.write(arg0);
        checkFlushBuffer(false);
    }

    public final synchronized void reset() {
        super.reset();
    }

    private final void checkFlushBuffer(boolean forced) {
        while (true) {
            if (!forced) {
                if (this.count < this.size) {
                    return;
                }
            }
            int nb = this.size;
            if (nb > this.count) {
                nb = this.count;
            }
            if (nb != 0) {
                flushBuffer(this.buf, nb);
                this.countFlushed += (long) nb;
                int bytesleft = this.count - nb;
                this.count = bytesleft;
                if (bytesleft > 0) {
                    System.arraycopy(this.buf, nb, this.buf, 0, bytesleft);
                }
            } else {
                return;
            }
        }
    }

    public void setSize(int size) {
        this.size = size;
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setting size: ");
        stringBuilder.append(size);
        stringBuilder.append(" count");
        stringBuilder.append(this.count);
        printStream.println(stringBuilder.toString());
        checkFlushBuffer(false);
    }

    public long getCountFlushed() {
        return this.countFlushed;
    }
}
