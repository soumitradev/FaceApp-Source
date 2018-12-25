package com.google.common.io;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

@Beta
public final class ByteStreams {
    static final int BUF_SIZE = 8192;
    private static final OutputStream NULL_OUTPUT_STREAM = new C05721();
    private static final int ZERO_COPY_CHUNK_SIZE = 524288;
    static final byte[] skipBuffer = new byte[8192];

    /* renamed from: com.google.common.io.ByteStreams$1 */
    static class C05721 extends OutputStream {
        C05721() {
        }

        public void write(int b) {
        }

        public void write(byte[] b) {
            Preconditions.checkNotNull(b);
        }

        public void write(byte[] b, int off, int len) {
            Preconditions.checkNotNull(b);
        }

        public String toString() {
            return "ByteStreams.nullOutputStream()";
        }
    }

    private static final class FastByteArrayOutputStream extends ByteArrayOutputStream {
        private FastByteArrayOutputStream() {
        }

        void writeTo(byte[] b, int off) {
            System.arraycopy(this.buf, 0, b, off, this.count);
        }
    }

    private static final class LimitedInputStream extends FilterInputStream {
        private long left;
        private long mark = -1;

        LimitedInputStream(InputStream in, long limit) {
            super(in);
            Preconditions.checkNotNull(in);
            Preconditions.checkArgument(limit >= 0, "limit must be non-negative");
            this.left = limit;
        }

        public int available() throws IOException {
            return (int) Math.min((long) this.in.available(), this.left);
        }

        public synchronized void mark(int readLimit) {
            this.in.mark(readLimit);
            this.mark = this.left;
        }

        public int read() throws IOException {
            if (this.left == 0) {
                return -1;
            }
            int result = this.in.read();
            if (result != -1) {
                this.left--;
            }
            return result;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            if (this.left == 0) {
                return -1;
            }
            int result = this.in.read(b, off, (int) Math.min((long) len, this.left));
            if (result != -1) {
                this.left -= (long) result;
            }
            return result;
        }

        public synchronized void reset() throws IOException {
            if (!this.in.markSupported()) {
                throw new IOException("Mark not supported");
            } else if (this.mark == -1) {
                throw new IOException("Mark not set");
            } else {
                this.in.reset();
                this.left = this.mark;
            }
        }

        public long skip(long n) throws IOException {
            long skipped = this.in.skip(Math.min(n, this.left));
            this.left -= skipped;
            return skipped;
        }
    }

    private static class ByteArrayDataInputStream implements ByteArrayDataInput {
        final DataInput input;

        ByteArrayDataInputStream(ByteArrayInputStream byteArrayInputStream) {
            this.input = new DataInputStream(byteArrayInputStream);
        }

        public void readFully(byte[] b) {
            try {
                this.input.readFully(b);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public void readFully(byte[] b, int off, int len) {
            try {
                this.input.readFully(b, off, len);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public int skipBytes(int n) {
            try {
                return this.input.skipBytes(n);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public boolean readBoolean() {
            try {
                return this.input.readBoolean();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public byte readByte() {
            try {
                return this.input.readByte();
            } catch (EOFException e) {
                throw new IllegalStateException(e);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public int readUnsignedByte() {
            try {
                return this.input.readUnsignedByte();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public short readShort() {
            try {
                return this.input.readShort();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public int readUnsignedShort() {
            try {
                return this.input.readUnsignedShort();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public char readChar() {
            try {
                return this.input.readChar();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public int readInt() {
            try {
                return this.input.readInt();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public long readLong() {
            try {
                return this.input.readLong();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public float readFloat() {
            try {
                return this.input.readFloat();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public double readDouble() {
            try {
                return this.input.readDouble();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public String readLine() {
            try {
                return this.input.readLine();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public String readUTF() {
            try {
                return this.input.readUTF();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class ByteArrayDataOutputStream implements ByteArrayDataOutput {
        final ByteArrayOutputStream byteArrayOutputSteam;
        final DataOutput output;

        ByteArrayDataOutputStream(ByteArrayOutputStream byteArrayOutputSteam) {
            this.byteArrayOutputSteam = byteArrayOutputSteam;
            this.output = new DataOutputStream(byteArrayOutputSteam);
        }

        public void write(int b) {
            try {
                this.output.write(b);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void write(byte[] b) {
            try {
                this.output.write(b);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void write(byte[] b, int off, int len) {
            try {
                this.output.write(b, off, len);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeBoolean(boolean v) {
            try {
                this.output.writeBoolean(v);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeByte(int v) {
            try {
                this.output.writeByte(v);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeBytes(String s) {
            try {
                this.output.writeBytes(s);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeChar(int v) {
            try {
                this.output.writeChar(v);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeChars(String s) {
            try {
                this.output.writeChars(s);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeDouble(double v) {
            try {
                this.output.writeDouble(v);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeFloat(float v) {
            try {
                this.output.writeFloat(v);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeInt(int v) {
            try {
                this.output.writeInt(v);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeLong(long v) {
            try {
                this.output.writeLong(v);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeShort(int v) {
            try {
                this.output.writeShort(v);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public void writeUTF(String s) {
            try {
                this.output.writeUTF(s);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public byte[] toByteArray() {
            return this.byteArrayOutputSteam.toByteArray();
        }
    }

    private ByteStreams() {
    }

    public static long copy(InputStream from, OutputStream to) throws IOException {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(to);
        byte[] buf = new byte[8192];
        long total = 0;
        while (true) {
            int r = from.read(buf);
            if (r == -1) {
                return total;
            }
            to.write(buf, 0, r);
            total += (long) r;
        }
    }

    public static long copy(ReadableByteChannel from, WritableByteChannel to) throws IOException {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(to);
        long total = 0;
        if (from instanceof FileChannel) {
            FileChannel sourceChannel = (FileChannel) from;
            long oldPosition = sourceChannel.position();
            long position = oldPosition;
            while (true) {
                long position2 = position;
                position = sourceChannel.transferTo(position2, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED, to);
                long position3 = position2 + position;
                sourceChannel.position(position3);
                if (position <= 0 && position3 >= sourceChannel.size()) {
                    return position3 - oldPosition;
                }
                position = position3;
            }
        } else {
            ByteBuffer buf = ByteBuffer.allocate(8192);
            while (from.read(buf) != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    total += (long) to.write(buf);
                }
                buf.clear();
            }
            return total;
        }
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        return out.toByteArray();
    }

    static byte[] toByteArray(InputStream in, int expectedSize) throws IOException {
        int off;
        byte[] bytes = new byte[expectedSize];
        int remaining = expectedSize;
        while (remaining > 0) {
            off = expectedSize - remaining;
            int read = in.read(bytes, off, remaining);
            if (read == -1) {
                return Arrays.copyOf(bytes, off);
            }
            remaining -= read;
        }
        off = in.read();
        if (off == -1) {
            return bytes;
        }
        OutputStream out = new FastByteArrayOutputStream();
        out.write(off);
        copy(in, out);
        byte[] result = new byte[(bytes.length + out.size())];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        out.writeTo(result, bytes.length);
        return result;
    }

    public static ByteArrayDataInput newDataInput(byte[] bytes) {
        return newDataInput(new ByteArrayInputStream(bytes));
    }

    public static ByteArrayDataInput newDataInput(byte[] bytes, int start) {
        Preconditions.checkPositionIndex(start, bytes.length);
        return newDataInput(new ByteArrayInputStream(bytes, start, bytes.length - start));
    }

    public static ByteArrayDataInput newDataInput(ByteArrayInputStream byteArrayInputStream) {
        return new ByteArrayDataInputStream((ByteArrayInputStream) Preconditions.checkNotNull(byteArrayInputStream));
    }

    public static ByteArrayDataOutput newDataOutput() {
        return newDataOutput(new ByteArrayOutputStream());
    }

    public static ByteArrayDataOutput newDataOutput(int size) {
        if (size >= 0) {
            return newDataOutput(new ByteArrayOutputStream(size));
        }
        throw new IllegalArgumentException(String.format("Invalid size: %s", new Object[]{Integer.valueOf(size)}));
    }

    public static ByteArrayDataOutput newDataOutput(ByteArrayOutputStream byteArrayOutputSteam) {
        return new ByteArrayDataOutputStream((ByteArrayOutputStream) Preconditions.checkNotNull(byteArrayOutputSteam));
    }

    public static OutputStream nullOutputStream() {
        return NULL_OUTPUT_STREAM;
    }

    public static InputStream limit(InputStream in, long limit) {
        return new LimitedInputStream(in, limit);
    }

    public static void readFully(InputStream in, byte[] b) throws IOException {
        readFully(in, b, 0, b.length);
    }

    public static void readFully(InputStream in, byte[] b, int off, int len) throws IOException {
        int read = read(in, b, off, len);
        if (read != len) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reached end of stream after reading ");
            stringBuilder.append(read);
            stringBuilder.append(" bytes; ");
            stringBuilder.append(len);
            stringBuilder.append(" bytes expected");
            throw new EOFException(stringBuilder.toString());
        }
    }

    public static void skipFully(InputStream in, long n) throws IOException {
        long skipped = skipUpTo(in, n);
        if (skipped < n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reached end of stream after skipping ");
            stringBuilder.append(skipped);
            stringBuilder.append(" bytes; ");
            stringBuilder.append(n);
            stringBuilder.append(" bytes expected");
            throw new EOFException(stringBuilder.toString());
        }
    }

    static long skipUpTo(InputStream in, long n) throws IOException {
        InputStream inputStream = in;
        long totalSkipped = 0;
        while (totalSkipped < n) {
            long remaining = n - totalSkipped;
            long skipped = skipSafely(inputStream, remaining);
            if (skipped == 0) {
                long read = (long) inputStream.read(skipBuffer, 0, (int) Math.min(remaining, (long) skipBuffer.length));
                skipped = read;
                if (read == -1) {
                    break;
                }
            }
            totalSkipped += skipped;
        }
        return totalSkipped;
    }

    private static long skipSafely(InputStream in, long n) throws IOException {
        int available = in.available();
        return available == 0 ? 0 : in.skip(Math.min((long) available, n));
    }

    public static <T> T readBytes(InputStream input, ByteProcessor<T> processor) throws IOException {
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(processor);
        byte[] buf = new byte[8192];
        int read;
        do {
            read = input.read(buf);
            if (read == -1) {
                break;
            }
        } while (processor.processBytes(buf, 0, read));
        return processor.getResult();
    }

    public static int read(InputStream in, byte[] b, int off, int len) throws IOException {
        Preconditions.checkNotNull(in);
        Preconditions.checkNotNull(b);
        if (len < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int total = 0;
        while (total < len) {
            int result = in.read(b, off + total, len - total);
            if (result == -1) {
                break;
            }
            total += result;
        }
        return total;
    }
}
