package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public final class Buffer implements BufferedSource, BufferedSink, Cloneable {
    private static final byte[] DIGITS = new byte[]{(byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, GeneralMidiConstants.TRUMPET, GeneralMidiConstants.TROMBONE, GeneralMidiConstants.FX_1_SOUNDTRACK, GeneralMidiConstants.FX_2_CRYSTAL, GeneralMidiConstants.FX_3_ATMOSPHERE, GeneralMidiConstants.FX_4_BRIGHTNESS, GeneralMidiConstants.FX_5_GOBLINS, GeneralMidiConstants.FX_6_ECHOES};
    Segment head;
    long size;

    /* renamed from: okio.Buffer$1 */
    class C17261 extends OutputStream {
        C17261() {
        }

        public void write(int b) {
            Buffer.this.writeByte((byte) b);
        }

        public void write(byte[] data, int offset, int byteCount) {
            Buffer.this.write(data, offset, byteCount);
        }

        public void flush() {
        }

        public void close() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this);
            stringBuilder.append(".outputStream()");
            return stringBuilder.toString();
        }
    }

    /* renamed from: okio.Buffer$2 */
    class C17272 extends InputStream {
        C17272() {
        }

        public int read() {
            if (Buffer.this.size > 0) {
                return Buffer.this.readByte() & 255;
            }
            return -1;
        }

        public int read(byte[] sink, int offset, int byteCount) {
            return Buffer.this.read(sink, offset, byteCount);
        }

        public int available() {
            return (int) Math.min(Buffer.this.size, 2147483647L);
        }

        public void close() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Buffer.this);
            stringBuilder.append(".inputStream()");
            return stringBuilder.toString();
        }
    }

    public long size() {
        return this.size;
    }

    public Buffer buffer() {
        return this;
    }

    public OutputStream outputStream() {
        return new C17261();
    }

    public Buffer emitCompleteSegments() {
        return this;
    }

    public BufferedSink emit() {
        return this;
    }

    public boolean exhausted() {
        return this.size == 0;
    }

    public void require(long byteCount) throws EOFException {
        if (this.size < byteCount) {
            throw new EOFException();
        }
    }

    public boolean request(long byteCount) {
        return this.size >= byteCount;
    }

    public InputStream inputStream() {
        return new C17272();
    }

    public Buffer copyTo(OutputStream out) throws IOException {
        return copyTo(out, 0, this.size);
    }

    public Buffer copyTo(OutputStream out, long offset, long byteCount) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, offset, byteCount);
        if (byteCount == 0) {
            return this;
        }
        Segment s = this.head;
        while (offset >= ((long) (s.limit - s.pos))) {
            long offset2 = offset - ((long) (s.limit - s.pos));
            s = s.next;
            offset = offset2;
        }
        while (byteCount > 0) {
            int pos = (int) (((long) s.pos) + offset);
            int toCopy = (int) Math.min((long) (s.limit - pos), byteCount);
            out.write(s.data, pos, toCopy);
            long byteCount2 = byteCount - ((long) toCopy);
            offset = 0;
            s = s.next;
            byteCount = byteCount2;
        }
        return this;
    }

    public Buffer copyTo(Buffer out, long offset, long byteCount) {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, offset, byteCount);
        if (byteCount == 0) {
            return this;
        }
        out.size += byteCount;
        Segment s = this.head;
        while (offset >= ((long) (s.limit - s.pos))) {
            long offset2 = offset - ((long) (s.limit - s.pos));
            s = s.next;
            offset = offset2;
        }
        while (byteCount > 0) {
            Segment copy = new Segment(s);
            copy.pos = (int) (((long) copy.pos) + offset);
            copy.limit = Math.min(copy.pos + ((int) byteCount), copy.limit);
            if (out.head == null) {
                copy.prev = copy;
                copy.next = copy;
                out.head = copy;
            } else {
                out.head.prev.push(copy);
            }
            long byteCount2 = byteCount - ((long) (copy.limit - copy.pos));
            offset = 0;
            s = s.next;
            byteCount = byteCount2;
        }
        return this;
    }

    public Buffer writeTo(OutputStream out) throws IOException {
        return writeTo(out, this.size);
    }

    public Buffer writeTo(OutputStream out, long byteCount) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, 0, byteCount);
        Segment s = this.head;
        while (byteCount > 0) {
            int toCopy = (int) Math.min(byteCount, (long) (s.limit - s.pos));
            out.write(s.data, s.pos, toCopy);
            s.pos += toCopy;
            this.size -= (long) toCopy;
            long byteCount2 = byteCount - ((long) toCopy);
            if (s.pos == s.limit) {
                Segment toRecycle = s;
                Segment pop = toRecycle.pop();
                s = pop;
                this.head = pop;
                SegmentPool.recycle(toRecycle);
            }
            byteCount = byteCount2;
        }
        return this;
    }

    public Buffer readFrom(InputStream in) throws IOException {
        readFrom(in, Long.MAX_VALUE, true);
        return this;
    }

    public Buffer readFrom(InputStream in, long byteCount) throws IOException {
        if (byteCount < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("byteCount < 0: ");
            stringBuilder.append(byteCount);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        readFrom(in, byteCount, false);
        return this;
    }

    private void readFrom(InputStream in, long byteCount, boolean forever) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("in == null");
        }
        while (true) {
            if (byteCount <= 0) {
                if (!forever) {
                    return;
                }
            }
            Segment tail = writableSegment(1);
            int bytesRead = in.read(tail.data, tail.limit, (int) Math.min(byteCount, (long) (2048 - tail.limit)));
            if (bytesRead == -1) {
                break;
            }
            tail.limit += bytesRead;
            this.size += (long) bytesRead;
            byteCount -= (long) bytesRead;
        }
        if (!forever) {
            throw new EOFException();
        }
    }

    public long completeSegmentByteCount() {
        long result = this.size;
        if (result == 0) {
            return 0;
        }
        Segment tail = this.head.prev;
        if (tail.limit < 2048 && tail.owner) {
            result -= (long) (tail.limit - tail.pos);
        }
        return result;
    }

    public byte readByte() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.head;
        byte b = segment.pos;
        int limit = segment.limit;
        int pos = b + 1;
        b = segment.data[b];
        this.size--;
        if (pos == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos;
        }
        return b;
    }

    public byte getByte(long pos) {
        Util.checkOffsetAndCount(this.size, pos, 1);
        Segment s = this.head;
        while (true) {
            int segmentByteCount = s.limit - s.pos;
            if (pos < ((long) segmentByteCount)) {
                return s.data[s.pos + ((int) pos)];
            }
            long pos2 = pos - ((long) segmentByteCount);
            s = s.next;
            pos = pos2;
        }
    }

    public short readShort() {
        if (this.size < 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("size < 2: ");
            stringBuilder.append(this.size);
            throw new IllegalStateException(stringBuilder.toString());
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 2) {
            return (short) (((readByte() & 255) << 8) | (readByte() & 255));
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        int pos3 = pos2 + 1;
        pos = ((data[pos] & 255) << 8) | (data[pos2] & 255);
        this.size -= 2;
        if (pos3 == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos3;
        }
        return (short) pos;
    }

    public int readInt() {
        if (this.size < 4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("size < 4: ");
            stringBuilder.append(this.size);
            throw new IllegalStateException(stringBuilder.toString());
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 4) {
            return ((((readByte() & 255) << 24) | ((readByte() & 255) << 16)) | ((readByte() & 255) << 8)) | (readByte() & 255);
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        int pos3 = pos2 + 1;
        pos = ((data[pos] & 255) << 24) | ((data[pos2] & 255) << 16);
        pos2 = pos3 + 1;
        pos |= (data[pos3] & 255) << 8;
        pos3 = pos2 + 1;
        pos |= data[pos2] & 255;
        this.size -= 4;
        if (pos3 == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos3;
        }
        return pos;
    }

    public long readLong() {
        if (this.size < 8) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("size < 8: ");
            stringBuilder.append(r0.size);
            throw new IllegalStateException(stringBuilder.toString());
        }
        Segment segment = r0.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 8) {
            return ((((long) readInt()) & 4294967295L) << 32) | (((long) readInt()) & 4294967295L);
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        pos = pos2 + 1;
        pos2 = pos + 1;
        pos = pos2 + 1;
        int pos3 = pos + 1;
        pos = pos3 + 1;
        pos3 = pos + 1;
        pos = pos3 + 1;
        long v = ((((((((((long) data[pos]) & 255) << 56) | ((((long) data[pos2]) & 255) << 48)) | ((((long) data[pos]) & 255) << 40)) | ((((long) data[pos2]) & 255) << 32)) | ((((long) data[pos]) & 255) << 24)) | ((((long) data[pos3]) & 255) << 16)) | ((((long) data[pos]) & 255) << 8)) | (((long) data[pos3]) & 255);
        r0.size -= 8;
        if (pos == limit) {
            r0.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos;
        }
        return v;
    }

    public short readShortLe() {
        return Util.reverseBytesShort(readShort());
    }

    public int readIntLe() {
        return Util.reverseBytesInt(readInt());
    }

    public long readLongLe() {
        return Util.reverseBytesLong(readLong());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long readDecimalLong() {
        /*
        r21 = this;
        r0 = r21;
        r1 = r0.size;
        r3 = 0;
        r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r5 != 0) goto L_0x0012;
    L_0x000a:
        r1 = new java.lang.IllegalStateException;
        r2 = "size == 0";
        r1.<init>(r2);
        throw r1;
    L_0x0012:
        r1 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r8 = -7;
    L_0x001e:
        r10 = r0.head;
        r11 = r10.data;
        r12 = r10.pos;
        r13 = r10.limit;
    L_0x0026:
        if (r12 >= r13) goto L_0x00c7;
    L_0x0028:
        r14 = r11[r12];
        r15 = 48;
        if (r14 < r15) goto L_0x008a;
    L_0x002e:
        r15 = 57;
        if (r14 > r15) goto L_0x008a;
    L_0x0032:
        r15 = 48;
        r15 = r15 - r14;
        r16 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
        if (r16 < 0) goto L_0x0057;
    L_0x0039:
        r16 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
        if (r16 != 0) goto L_0x0047;
    L_0x003d:
        r19 = r5;
        r17 = r6;
        r5 = (long) r15;
        r7 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1));
        if (r7 >= 0) goto L_0x004b;
    L_0x0046:
        goto L_0x005b;
    L_0x0047:
        r19 = r5;
        r17 = r6;
    L_0x004b:
        r5 = 10;
        r1 = r1 * r5;
        r5 = (long) r15;
        r15 = r1 + r5;
        r20 = r11;
        r1 = r15;
        goto L_0x009c;
    L_0x0057:
        r19 = r5;
        r17 = r6;
    L_0x005b:
        r5 = new okio.Buffer;
        r5.<init>();
        r5 = r5.writeDecimalLong(r1);
        r5 = r5.writeByte(r14);
        if (r4 != 0) goto L_0x006d;
    L_0x006a:
        r5.readByte();
    L_0x006d:
        r6 = new java.lang.NumberFormatException;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r20 = r11;
        r11 = "Number too large: ";
        r7.append(r11);
        r11 = r5.readUtf8();
        r7.append(r11);
        r7 = r7.toString();
        r6.<init>(r7);
        throw r6;
    L_0x008a:
        r19 = r5;
        r17 = r6;
        r20 = r11;
        r5 = 45;
        if (r14 != r5) goto L_0x00a8;
    L_0x0094:
        if (r3 != 0) goto L_0x00a8;
    L_0x0096:
        r4 = 1;
        r5 = 1;
        r15 = r8 - r5;
        r8 = r15;
    L_0x009c:
        r12 = r12 + 1;
        r3 = r3 + 1;
        r6 = r17;
        r5 = r19;
        r11 = r20;
        goto L_0x0026;
    L_0x00a8:
        if (r3 != 0) goto L_0x00c5;
    L_0x00aa:
        r5 = new java.lang.NumberFormatException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Expected leading [0-9] or '-' character but was 0x";
        r6.append(r7);
        r7 = java.lang.Integer.toHexString(r14);
        r6.append(r7);
        r6 = r6.toString();
        r5.<init>(r6);
        throw r5;
    L_0x00c5:
        r5 = 1;
        goto L_0x00cd;
    L_0x00c7:
        r19 = r5;
        r17 = r6;
        r20 = r11;
    L_0x00cd:
        if (r12 != r13) goto L_0x00d9;
    L_0x00cf:
        r6 = r10.pop();
        r0.head = r6;
        okio.SegmentPool.recycle(r10);
        goto L_0x00db;
    L_0x00d9:
        r10.pos = r12;
    L_0x00db:
        if (r5 != 0) goto L_0x00e6;
    L_0x00dd:
        r6 = r0.head;
        if (r6 != 0) goto L_0x00e2;
    L_0x00e1:
        goto L_0x00e6;
    L_0x00e2:
        r6 = r17;
        goto L_0x001e;
    L_0x00e6:
        r6 = r0.size;
        r10 = (long) r3;
        r12 = r6 - r10;
        r0.size = r12;
        if (r4 == 0) goto L_0x00f1;
    L_0x00ef:
        r6 = r1;
        goto L_0x00f2;
    L_0x00f1:
        r6 = -r1;
    L_0x00f2:
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Buffer.readDecimalLong():long");
    }

    public long readHexadecimalUnsignedLong() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        long value = 0;
        int seen = 0;
        boolean done = false;
        do {
            Segment segment = r0.head;
            byte[] data = segment.data;
            int pos = segment.pos;
            int limit = segment.limit;
            while (pos < limit) {
                int digit;
                int b = data[pos];
                if (b >= (byte) 48 && b <= GeneralMidiConstants.TROMBONE) {
                    digit = b - 48;
                } else if (b >= GeneralMidiConstants.FX_1_SOUNDTRACK && b <= GeneralMidiConstants.FX_6_ECHOES) {
                    digit = (b - 97) + 10;
                } else if (b < GeneralMidiConstants.ALTO_SAX || b > GeneralMidiConstants.BASSOON) {
                    if (seen != 0) {
                        done = true;
                        if (pos != limit) {
                            r0.head = segment.pop();
                            SegmentPool.recycle(segment);
                        } else {
                            segment.pos = pos;
                        }
                        if (!done) {
                            break;
                        }
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Expected leading [0-9a-fA-F] character but was 0x");
                        stringBuilder.append(Integer.toHexString(b));
                        throw new NumberFormatException(stringBuilder.toString());
                    }
                } else {
                    digit = (b - 65) + 10;
                }
                if ((value & -1152921504606846976L) != 0) {
                    Buffer buffer = new Buffer().writeHexadecimalUnsignedLong(value).writeByte(b);
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Number too large: ");
                    stringBuilder2.append(buffer.readUtf8());
                    throw new NumberFormatException(stringBuilder2.toString());
                }
                pos++;
                seen++;
                value = (value << 4) | ((long) digit);
            }
            if (pos != limit) {
                segment.pos = pos;
            } else {
                r0.head = segment.pop();
                SegmentPool.recycle(segment);
            }
            if (!done) {
                break;
            }
        } while (r0.head != null);
        r0.size -= (long) seen;
        return value;
    }

    public ByteString readByteString() {
        return new ByteString(readByteArray());
    }

    public ByteString readByteString(long byteCount) throws EOFException {
        return new ByteString(readByteArray(byteCount));
    }

    public void readFully(Buffer sink, long byteCount) throws EOFException {
        if (this.size < byteCount) {
            sink.write(this, this.size);
            throw new EOFException();
        } else {
            sink.write(this, byteCount);
        }
    }

    public long readAll(Sink sink) throws IOException {
        long byteCount = this.size;
        if (byteCount > 0) {
            sink.write(this, byteCount);
        }
        return byteCount;
    }

    public String readUtf8() {
        try {
            return readString(this.size, Util.UTF_8);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String readUtf8(long byteCount) throws EOFException {
        return readString(byteCount, Util.UTF_8);
    }

    public String readString(Charset charset) {
        try {
            return readString(this.size, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String readString(long byteCount, Charset charset) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, byteCount);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (byteCount > 2147483647L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("byteCount > Integer.MAX_VALUE: ");
            stringBuilder.append(byteCount);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (byteCount == 0) {
            return "";
        } else {
            Segment s = this.head;
            if (((long) s.pos) + byteCount > ((long) s.limit)) {
                return new String(readByteArray(byteCount), charset);
            }
            String result = new String(s.data, s.pos, (int) byteCount, charset);
            s.pos = (int) (((long) s.pos) + byteCount);
            this.size -= byteCount;
            if (s.pos == s.limit) {
                this.head = s.pop();
                SegmentPool.recycle(s);
            }
            return result;
        }
    }

    public String readUtf8Line() throws EOFException {
        long newline = indexOf(10);
        if (newline != -1) {
            return readUtf8Line(newline);
        }
        return this.size != 0 ? readUtf8(this.size) : null;
    }

    public String readUtf8LineStrict() throws EOFException {
        long newline = indexOf(10);
        if (newline != -1) {
            return readUtf8Line(newline);
        }
        Buffer data = new Buffer();
        copyTo(data, 0, Math.min(32, this.size));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\n not found: size=");
        stringBuilder.append(size());
        stringBuilder.append(" content=");
        stringBuilder.append(data.readByteString().hex());
        stringBuilder.append("...");
        throw new EOFException(stringBuilder.toString());
    }

    String readUtf8Line(long newline) throws EOFException {
        if (newline <= 0 || getByte(newline - 1) != (byte) 13) {
            String result = readUtf8(newline);
            skip(1);
            return result;
        }
        String result2 = readUtf8(newline - 1);
        skip(2);
        return result2;
    }

    public byte[] readByteArray() {
        try {
            return readByteArray(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public byte[] readByteArray(long byteCount) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0, byteCount);
        if (byteCount > 2147483647L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("byteCount > Integer.MAX_VALUE: ");
            stringBuilder.append(byteCount);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        byte[] result = new byte[((int) byteCount)];
        readFully(result);
        return result;
    }

    public int read(byte[] sink) {
        return read(sink, 0, sink.length);
    }

    public void readFully(byte[] sink) throws EOFException {
        int offset = 0;
        while (offset < sink.length) {
            int read = read(sink, offset, sink.length - offset);
            if (read == -1) {
                throw new EOFException();
            }
            offset += read;
        }
    }

    public int read(byte[] sink, int offset, int byteCount) {
        Util.checkOffsetAndCount((long) sink.length, (long) offset, (long) byteCount);
        Segment s = this.head;
        if (s == null) {
            return -1;
        }
        int toCopy = Math.min(byteCount, s.limit - s.pos);
        System.arraycopy(s.data, s.pos, sink, offset, toCopy);
        s.pos += toCopy;
        this.size -= (long) toCopy;
        if (s.pos == s.limit) {
            this.head = s.pop();
            SegmentPool.recycle(s);
        }
        return toCopy;
    }

    public void clear() {
        try {
            skip(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public void skip(long byteCount) throws EOFException {
        while (byteCount > 0) {
            if (this.head == null) {
                throw new EOFException();
            }
            int toSkip = (int) Math.min(byteCount, (long) (this.head.limit - this.head.pos));
            this.size -= (long) toSkip;
            long byteCount2 = byteCount - ((long) toSkip);
            Segment segment = this.head;
            segment.pos += toSkip;
            if (this.head.pos == this.head.limit) {
                segment = this.head;
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            }
            byteCount = byteCount2;
        }
    }

    public Buffer write(ByteString byteString) {
        if (byteString == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        byteString.write(this);
        return this;
    }

    public Buffer writeUtf8(String string) {
        Buffer buffer = this;
        String str = string;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        int i = 0;
        int length = string.length();
        while (i < length) {
            int c = str.charAt(i);
            int segmentOffset;
            if (c < 128) {
                Segment tail = writableSegment(1);
                byte[] data = tail.data;
                segmentOffset = tail.limit - i;
                int runLimit = Math.min(length, 2048 - segmentOffset);
                int i2 = i + 1;
                data[i + segmentOffset] = (byte) c;
                while (i2 < runLimit) {
                    c = str.charAt(i2);
                    if (c >= 128) {
                        break;
                    }
                    i = i2 + 1;
                    data[i2 + segmentOffset] = (byte) c;
                    i2 = i;
                }
                i = (i2 + segmentOffset) - tail.limit;
                tail.limit += i;
                buffer.size += (long) i;
                i = i2;
            } else if (c < 2048) {
                writeByte((c >> 6) | ReportAnalogPinMessageWriter.COMMAND);
                writeByte((c & 63) | 128);
                i++;
            } else {
                if (c >= 55296) {
                    if (c <= 57343) {
                        int low = i + 1 < length ? str.charAt(i + 1) : 0;
                        if (c <= 56319 && low >= 56320) {
                            if (low <= 57343) {
                                segmentOffset = (((-55297 & c) << 10) | (-56321 & low)) + 65536;
                                writeByte((segmentOffset >> 18) | SysexMessageWriter.COMMAND_START);
                                writeByte(((segmentOffset >> 12) & 63) | 128);
                                writeByte(((segmentOffset >> 6) & 63) | 128);
                                writeByte((segmentOffset & 63) | 128);
                                i += 2;
                            }
                        }
                        writeByte(63);
                        i++;
                    }
                }
                writeByte((c >> 12) | AnalogMessageWriter.COMMAND);
                writeByte(((c >> 6) & 63) | 128);
                writeByte((c & 63) | 128);
                i++;
            }
        }
        return buffer;
    }

    public Buffer writeString(String string, Charset charset) {
        if (string == null) {
            throw new IllegalArgumentException("string == null");
        } else if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (charset.equals(Util.UTF_8)) {
            return writeUtf8(string);
        } else {
            byte[] data = string.getBytes(charset);
            return write(data, 0, data.length);
        }
    }

    public Buffer write(byte[] source) {
        if (source != null) {
            return write(source, 0, source.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    public Buffer write(byte[] source, int offset, int byteCount) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        Util.checkOffsetAndCount((long) source.length, (long) offset, (long) byteCount);
        int limit = offset + byteCount;
        while (offset < limit) {
            Segment tail = writableSegment(1);
            int toCopy = Math.min(limit - offset, 2048 - tail.limit);
            System.arraycopy(source, offset, tail.data, tail.limit, toCopy);
            offset += toCopy;
            tail.limit += toCopy;
        }
        this.size += (long) byteCount;
        return this;
    }

    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long totalBytesRead = 0;
        while (true) {
            long read = source.read(this, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
            long readCount = read;
            if (read == -1) {
                return totalBytesRead;
            }
            totalBytesRead += readCount;
        }
    }

    public BufferedSink write(Source source, long byteCount) throws IOException {
        while (byteCount > 0) {
            long read = source.read(this, byteCount);
            if (read == -1) {
                throw new EOFException();
            }
            byteCount -= read;
        }
        return this;
    }

    public Buffer writeByte(int b) {
        Segment tail = writableSegment(1);
        byte[] bArr = tail.data;
        int i = tail.limit;
        tail.limit = i + 1;
        bArr[i] = (byte) b;
        this.size++;
        return this;
    }

    public Buffer writeShort(int s) {
        Segment tail = writableSegment(2);
        byte[] data = tail.data;
        int limit = tail.limit;
        int limit2 = limit + 1;
        data[limit] = (byte) ((s >>> 8) & 255);
        limit = limit2 + 1;
        data[limit2] = (byte) (s & 255);
        tail.limit = limit;
        this.size += 2;
        return this;
    }

    public Buffer writeShortLe(int s) {
        return writeShort(Util.reverseBytesShort((short) s));
    }

    public Buffer writeInt(int i) {
        Segment tail = writableSegment(4);
        byte[] data = tail.data;
        int limit = tail.limit;
        int limit2 = limit + 1;
        data[limit] = (byte) ((i >>> 24) & 255);
        limit = limit2 + 1;
        data[limit2] = (byte) ((i >>> 16) & 255);
        limit2 = limit + 1;
        data[limit] = (byte) ((i >>> 8) & 255);
        limit = limit2 + 1;
        data[limit2] = (byte) (i & 255);
        tail.limit = limit;
        this.size += 4;
        return this;
    }

    public Buffer writeIntLe(int i) {
        return writeInt(Util.reverseBytesInt(i));
    }

    public Buffer writeLong(long v) {
        Segment tail = writableSegment(8);
        byte[] data = tail.data;
        int limit = tail.limit;
        int limit2 = limit + 1;
        data[limit] = (byte) ((int) ((v >>> 56) & 255));
        limit = limit2 + 1;
        data[limit2] = (byte) ((int) ((v >>> 48) & 255));
        limit2 = limit + 1;
        data[limit] = (byte) ((int) ((v >>> 40) & 255));
        limit = limit2 + 1;
        data[limit2] = (byte) ((int) ((v >>> 32) & 255));
        limit2 = limit + 1;
        data[limit] = (byte) ((int) ((v >>> 24) & 255));
        limit = limit2 + 1;
        data[limit2] = (byte) ((int) ((v >>> 16) & 255));
        limit2 = limit + 1;
        data[limit] = (byte) ((int) ((v >>> 8) & 255));
        int limit3 = limit2 + 1;
        data[limit2] = (byte) ((int) (v & 255));
        tail.limit = limit3;
        this.size += 8;
        return this;
    }

    public Buffer writeLongLe(long v) {
        return writeLong(Util.reverseBytesLong(v));
    }

    public Buffer writeDecimalLong(long v) {
        if (v == 0) {
            return writeByte(48);
        }
        boolean negative = false;
        if (v < 0) {
            v = -v;
            if (v < 0) {
                return writeUtf8("-9223372036854775808");
            }
            negative = true;
        }
        int width = v < 100000000 ? v < 10000 ? v < 100 ? v < 10 ? 1 : 2 : v < 1000 ? 3 : 4 : v < 1000000 ? v < 100000 ? 5 : 6 : v < 10000000 ? 7 : 8 : v < 1000000000000L ? v < 10000000000L ? v < 1000000000 ? 9 : 10 : v < 100000000000L ? 11 : 12 : v < 1000000000000000L ? v < 10000000000000L ? 13 : v < 100000000000000L ? 14 : 15 : v < 100000000000000000L ? v < 10000000000000000L ? 16 : 17 : v < 1000000000000000000L ? 18 : 19;
        if (negative) {
            width++;
        }
        Segment tail = writableSegment(width);
        byte[] data = tail.data;
        int pos = tail.limit + width;
        while (v != 0) {
            pos--;
            data[pos] = DIGITS[(int) (v % 10)];
            v /= 10;
        }
        if (negative) {
            data[pos - 1] = GeneralMidiConstants.PIZZICATO_STRINGS;
        }
        tail.limit += width;
        this.size += (long) width;
        return this;
    }

    public Buffer writeHexadecimalUnsignedLong(long v) {
        if (v == 0) {
            return writeByte(48);
        }
        int width = (Long.numberOfTrailingZeros(Long.highestOneBit(v)) / 4) + 1;
        Segment tail = writableSegment(width);
        byte[] data = tail.data;
        int start = tail.limit;
        for (int pos = (tail.limit + width) - 1; pos >= start; pos--) {
            data[pos] = DIGITS[(int) (v & 15)];
            v >>>= 4;
        }
        tail.limit += width;
        this.size += (long) width;
        return this;
    }

    Segment writableSegment(int minimumCapacity) {
        if (minimumCapacity >= 1) {
            if (minimumCapacity <= 2048) {
                Segment segment;
                if (this.head == null) {
                    this.head = SegmentPool.take();
                    Segment segment2 = this.head;
                    segment = this.head;
                    Segment segment3 = this.head;
                    segment.prev = segment3;
                    segment2.next = segment3;
                    return segment3;
                }
                segment = this.head.prev;
                if (segment.limit + minimumCapacity > 2048 || !segment.owner) {
                    segment = segment.push(SegmentPool.take());
                }
                return segment;
            }
        }
        throw new IllegalArgumentException();
    }

    public void write(Buffer source, long byteCount) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        } else if (source == this) {
            throw new IllegalArgumentException("source == this");
        } else {
            Util.checkOffsetAndCount(source.size, 0, byteCount);
            while (byteCount > 0) {
                Segment tail;
                if (byteCount < ((long) (source.head.limit - source.head.pos))) {
                    tail = this.head != null ? this.head.prev : null;
                    if (tail != null && tail.owner) {
                        if ((byteCount + ((long) tail.limit)) - ((long) (tail.shared ? 0 : tail.pos)) <= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) {
                            source.head.writeTo(tail, (int) byteCount);
                            source.size -= byteCount;
                            this.size += byteCount;
                            return;
                        }
                    }
                    source.head = source.head.split((int) byteCount);
                }
                tail = source.head;
                long movedByteCount = (long) (tail.limit - tail.pos);
                source.head = tail.pop();
                if (this.head == null) {
                    this.head = tail;
                    Segment segment = this.head;
                    Segment segment2 = this.head;
                    Segment segment3 = this.head;
                    segment2.prev = segment3;
                    segment.next = segment3;
                } else {
                    this.head.prev.push(tail).compact();
                }
                source.size -= movedByteCount;
                this.size += movedByteCount;
                byteCount -= movedByteCount;
            }
        }
    }

    public long read(Buffer sink, long byteCount) {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (byteCount < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("byteCount < 0: ");
            stringBuilder.append(byteCount);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (this.size == 0) {
            return -1;
        } else {
            if (byteCount > this.size) {
                byteCount = this.size;
            }
            sink.write(this, byteCount);
            return byteCount;
        }
    }

    public long indexOf(byte b) {
        return indexOf(b, 0);
    }

    public long indexOf(byte b, long fromIndex) {
        Buffer buffer = this;
        long offset = 0;
        if (fromIndex < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment s = buffer.head;
        if (s == null) {
            return -1;
        }
        long fromIndex2 = fromIndex;
        while (true) {
            int segmentByteCount = s.limit - s.pos;
            byte b2;
            if (fromIndex2 >= ((long) segmentByteCount)) {
                b2 = b;
                fromIndex2 -= (long) segmentByteCount;
            } else {
                byte[] data = s.data;
                long limit = (long) s.limit;
                for (long pos = ((long) s.pos) + fromIndex2; pos < limit; pos++) {
                    if (data[(int) pos] == b) {
                        return (offset + pos) - ((long) s.pos);
                    }
                    fromIndex2 = fromIndex2;
                }
                b2 = b;
                long j = fromIndex2;
                fromIndex2 = 0;
            }
            long offset2 = offset + ((long) segmentByteCount);
            s = s.next;
            if (s == buffer.head) {
                return -1;
            }
            offset = offset2;
        }
    }

    public long indexOfElement(ByteString targetBytes) {
        return indexOfElement(targetBytes, 0);
    }

    public long indexOfElement(ByteString targetBytes, long fromIndex) {
        Buffer buffer = this;
        if (fromIndex < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment s = buffer.head;
        if (s == null) {
            return -1;
        }
        long offset = 0;
        byte[] toFind = targetBytes.toByteArray();
        long fromIndex2 = fromIndex;
        while (true) {
            byte[] toFind2;
            int segmentByteCount = s.limit - s.pos;
            if (fromIndex2 >= ((long) segmentByteCount)) {
                toFind2 = toFind;
                fromIndex2 -= (long) segmentByteCount;
            } else {
                long fromIndex3;
                byte[] data = s.data;
                long pos = ((long) s.pos) + fromIndex2;
                long limit = (long) s.limit;
                while (pos < limit) {
                    byte b = data[(int) pos];
                    byte[] arr$ = toFind;
                    int len$ = arr$.length;
                    int i$ = 0;
                    while (true) {
                        toFind2 = toFind;
                        toFind = i$;
                        if (toFind >= len$) {
                            break;
                        }
                        fromIndex3 = fromIndex2;
                        fromIndex2 = arr$[toFind];
                        if (b == fromIndex2) {
                            byte targetByte = fromIndex2;
                            return (offset + pos) - ((long) s.pos);
                        }
                        i$ = toFind + 1;
                        toFind = toFind2;
                        fromIndex2 = fromIndex3;
                    }
                    pos += 1;
                    toFind = toFind2;
                    fromIndex2 = fromIndex2;
                }
                toFind2 = toFind;
                fromIndex3 = fromIndex2;
                fromIndex2 = 0;
            }
            long offset2 = offset + ((long) segmentByteCount);
            s = s.next;
            if (s == buffer.head) {
                return -1;
            }
            offset = offset2;
            toFind = toFind2;
        }
    }

    public void flush() {
    }

    public void close() {
    }

    public Timeout timeout() {
        return Timeout.NONE;
    }

    List<Integer> segmentSizes() {
        if (this.head == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList();
        result.add(Integer.valueOf(this.head.limit - this.head.pos));
        Segment s = this.head;
        while (true) {
            s = s.next;
            if (s == this.head) {
                return result;
            }
            result.add(Integer.valueOf(s.limit - s.pos));
        }
    }

    public boolean equals(Object o) {
        Buffer buffer = o;
        if (this == buffer) {
            return true;
        }
        if (!(buffer instanceof Buffer)) {
            return false;
        }
        Buffer that = buffer;
        if (r0.size != that.size) {
            return false;
        }
        long pos = 0;
        if (r0.size == 0) {
            return true;
        }
        Segment sa = r0.head;
        Segment sb = that.head;
        int posA = sa.pos;
        int posB = sb.pos;
        while (pos < r0.size) {
            long count = (long) Math.min(sa.limit - posA, sb.limit - posB);
            int posB2 = posB;
            posB = posA;
            posA = 0;
            while (((long) posA) < count) {
                int posA2 = posB + 1;
                int posB3 = posB2 + 1;
                if (sa.data[posB] != sb.data[posB2]) {
                    return false;
                }
                posA++;
                posB = posA2;
                posB2 = posB3;
            }
            if (posB == sa.limit) {
                sa = sa.next;
                posA = sa.pos;
            } else {
                posA = posB;
            }
            if (posB2 == sb.limit) {
                sb = sb.next;
                posB = sb.pos;
            } else {
                posB = posB2;
            }
            pos += count;
        }
        return true;
    }

    public int hashCode() {
        Segment s = this.head;
        if (s == null) {
            return 0;
        }
        int result = 1;
        do {
            for (int pos = s.pos; pos < s.limit; pos++) {
                result = (result * 31) + s.data[pos];
            }
            s = s.next;
        } while (s != this.head);
        return result;
    }

    public String toString() {
        if (this.size == 0) {
            return "Buffer[size=0]";
        }
        if (this.size <= 16) {
            ByteString data = clone().readByteString();
            return String.format("Buffer[size=%s data=%s]", new Object[]{Long.valueOf(this.size), data.hex()});
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            md5.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
            for (Segment s = this.head.next; s != this.head; s = s.next) {
                md5.update(s.data, s.pos, s.limit - s.pos);
            }
            return String.format("Buffer[size=%s md5=%s]", new Object[]{Long.valueOf(this.size), ByteString.of(md5.digest()).hex()});
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

    public Buffer clone() {
        Buffer result = new Buffer();
        if (this.size == 0) {
            return result;
        }
        result.head = new Segment(this.head);
        Segment segment = result.head;
        Segment segment2 = result.head;
        Segment segment3 = result.head;
        segment2.prev = segment3;
        segment.next = segment3;
        segment = this.head;
        while (true) {
            segment = segment.next;
            if (segment != this.head) {
                result.head.prev.push(new Segment(segment));
            } else {
                result.size = this.size;
                return result;
            }
        }
    }

    public ByteString snapshot() {
        if (this.size <= 2147483647L) {
            return snapshot((int) this.size);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size > Integer.MAX_VALUE: ");
        stringBuilder.append(this.size);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public ByteString snapshot(int byteCount) {
        if (byteCount == 0) {
            return ByteString.EMPTY;
        }
        return new SegmentedByteString(this, byteCount);
    }
}
