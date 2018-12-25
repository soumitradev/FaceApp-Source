package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.PngjOutputException;
import java.io.OutputStream;
import java.util.zip.Deflater;

public class CompressorStreamDeflater extends CompressorStream {
    protected byte[] buf;
    protected Deflater deflater;
    protected boolean deflaterIsOwn;

    public CompressorStreamDeflater(OutputStream os, int maxBlockLen, long totalLen) {
        this(os, maxBlockLen, totalLen, null);
    }

    public CompressorStreamDeflater(OutputStream os, int maxBlockLen, long totalLen, Deflater def) {
        super(os, maxBlockLen, totalLen);
        this.buf = new byte[4092];
        boolean z = true;
        this.deflaterIsOwn = true;
        this.deflater = def == null ? new Deflater() : def;
        if (def != null) {
            z = false;
        }
        this.deflaterIsOwn = z;
    }

    public CompressorStreamDeflater(OutputStream os, int maxBlockLen, long totalLen, int deflaterCompLevel, int deflaterStrategy) {
        this(os, maxBlockLen, totalLen, new Deflater(deflaterCompLevel));
        this.deflaterIsOwn = true;
        this.deflater.setStrategy(deflaterStrategy);
    }

    public void mywrite(byte[] b, int off, int len) {
        if (!(this.deflater.finished() || this.done)) {
            if (!this.closed) {
                this.deflater.setInput(b, off, len);
                this.bytesIn += (long) len;
                while (!this.deflater.needsInput()) {
                    deflate();
                }
                return;
            }
        }
        throw new PngjOutputException("write beyond end of stream");
    }

    protected void deflate() {
        int len = this.deflater.deflate(this.buf, 0, this.buf.length);
        if (len > 0) {
            this.bytesOut += (long) len;
            try {
                if (this.os != null) {
                    this.os.write(this.buf, 0, len);
                }
            } catch (Throwable e) {
                throw new PngjOutputException(e);
            }
        }
    }

    public void done() {
        if (!this.done) {
            if (!this.deflater.finished()) {
                this.deflater.finish();
                while (!this.deflater.finished()) {
                    deflate();
                }
            }
            this.done = true;
            flush();
        }
    }

    public void close() {
        done();
        try {
            if (this.deflaterIsOwn) {
                this.deflater.end();
            }
        } catch (Exception e) {
        }
        super.close();
    }

    public void reset() {
        super.reset();
        this.deflater.reset();
    }
}
