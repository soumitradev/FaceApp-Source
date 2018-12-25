package com.koushikdutta.async.http.cache;

import com.koushikdutta.async.util.Charsets;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

class StrictLineReader implements Closeable {
    private static final byte CR = (byte) 13;
    private static final byte LF = (byte) 10;
    private byte[] buf;
    private int end;
    private final InputStream in;
    private int pos;

    public StrictLineReader(InputStream in) {
        this(in, 8192);
    }

    public StrictLineReader(InputStream in, int capacity) {
        this(in, capacity, Charsets.US_ASCII);
    }

    public StrictLineReader(InputStream in, Charset charset) {
        this(in, 8192, charset);
    }

    public StrictLineReader(InputStream in, int capacity, Charset charset) {
        if (in == null) {
            throw new NullPointerException("in == null");
        } else if (charset == null) {
            throw new NullPointerException("charset == null");
        } else if (capacity < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (charset.equals(Charsets.US_ASCII) || charset.equals(Charsets.UTF_8)) {
            this.in = in;
            this.buf = new byte[capacity];
        } else {
            throw new IllegalArgumentException("Unsupported encoding");
        }
    }

    public void close() throws IOException {
        synchronized (this.in) {
            if (this.buf != null) {
                this.buf = null;
                this.in.close();
            }
        }
    }

    public String readLine() throws IOException {
        synchronized (this.in) {
            if (this.buf == null) {
                throw new IOException("LineReader is closed");
            }
            int lineEnd;
            String res;
            if (this.pos >= this.end) {
                fillBuf();
            }
            int i = this.pos;
            while (i != this.end) {
                if (this.buf[i] == (byte) 10) {
                    lineEnd = (i == this.pos || this.buf[i - 1] != (byte) 13) ? i : i - 1;
                    res = new String(this.buf, this.pos, lineEnd - this.pos);
                    this.pos = i + 1;
                    return res;
                }
                i++;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream((this.end - this.pos) + 80) {
                public String toString() {
                    int length = (this.count <= 0 || this.buf[this.count - 1] != (byte) 13) ? this.count : this.count - 1;
                    return new String(this.buf, 0, length);
                }
            };
            loop1:
            while (true) {
                out.write(this.buf, this.pos, this.end - this.pos);
                this.end = -1;
                fillBuf();
                lineEnd = this.pos;
                while (lineEnd != this.end) {
                    if (this.buf[lineEnd] == (byte) 10) {
                        break loop1;
                    }
                    lineEnd++;
                }
            }
            if (lineEnd != this.pos) {
                out.write(this.buf, this.pos, lineEnd - this.pos);
            }
            this.pos = lineEnd + 1;
            res = out.toString();
            return res;
        }
    }

    public int readInt() throws IOException {
        String intString = readLine();
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected an int but was \"");
            stringBuilder.append(intString);
            stringBuilder.append("\"");
            throw new IOException(stringBuilder.toString());
        }
    }

    public boolean hasUnterminatedLine() {
        return this.end == -1;
    }

    private void fillBuf() throws IOException {
        int result = this.in.read(this.buf, 0, this.buf.length);
        if (result == -1) {
            throw new EOFException();
        }
        this.pos = 0;
        this.end = result;
    }
}
