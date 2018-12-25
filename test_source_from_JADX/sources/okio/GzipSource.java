package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public final class GzipSource implements Source {
    private static final byte FCOMMENT = (byte) 4;
    private static final byte FEXTRA = (byte) 2;
    private static final byte FHCRC = (byte) 1;
    private static final byte FNAME = (byte) 3;
    private static final byte SECTION_BODY = (byte) 1;
    private static final byte SECTION_DONE = (byte) 3;
    private static final byte SECTION_HEADER = (byte) 0;
    private static final byte SECTION_TRAILER = (byte) 2;
    private final CRC32 crc = new CRC32();
    private final Inflater inflater;
    private final InflaterSource inflaterSource;
    private int section = 0;
    private final BufferedSource source;

    public GzipSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.inflater = new Inflater(true);
        this.source = Okio.buffer(source);
        this.inflaterSource = new InflaterSource(this.source, this.inflater);
    }

    public long read(Buffer sink, long byteCount) throws IOException {
        GzipSource gzipSource = this;
        Buffer buffer = sink;
        long j = byteCount;
        if (j < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("byteCount < 0: ");
            stringBuilder.append(j);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (j == 0) {
            return 0;
        } else {
            if (gzipSource.section == 0) {
                consumeHeader();
                gzipSource.section = 1;
            }
            if (gzipSource.section == 1) {
                long offset = buffer.size;
                long result = gzipSource.inflaterSource.read(buffer, j);
                if (result != -1) {
                    updateCrc(buffer, offset, result);
                    return result;
                }
                gzipSource.section = 2;
            }
            if (gzipSource.section == 2) {
                consumeTrailer();
                gzipSource.section = 3;
                if (!gzipSource.source.exhausted()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1;
        }
    }

    private void consumeHeader() throws IOException {
        this.source.require(10);
        byte flags = this.source.buffer().getByte(3);
        boolean fhcrc = ((flags >> 1) & 1) == 1;
        if (fhcrc) {
            updateCrc(r6.source.buffer(), 0, 10);
        }
        checkEqual("ID1ID2", 8075, r6.source.readShort());
        r6.source.skip(8);
        if (((flags >> 2) & 1) == 1) {
            r6.source.require(2);
            if (fhcrc) {
                updateCrc(r6.source.buffer(), 0, 2);
            }
            int xlen = r6.source.buffer().readShortLe();
            r6.source.require((long) xlen);
            if (fhcrc) {
                updateCrc(r6.source.buffer(), 0, (long) xlen);
            }
            r6.source.skip((long) xlen);
        }
        if (((flags >> 3) & 1) == 1) {
            long index = r6.source.indexOf((byte) 0);
            if (index == -1) {
                throw new EOFException();
            }
            if (fhcrc) {
                updateCrc(r6.source.buffer(), 0, index + 1);
            }
            r6.source.skip(index + 1);
        }
        if (((flags >> 4) & 1) == 1) {
            long index2 = r6.source.indexOf((byte) 0);
            if (index2 == -1) {
                throw new EOFException();
            }
            if (fhcrc) {
                updateCrc(r6.source.buffer(), 0, index2 + 1);
            }
            r6.source.skip(index2 + 1);
        }
        if (fhcrc) {
            checkEqual("FHCRC", r6.source.readShortLe(), (short) ((int) r6.crc.getValue()));
            r6.crc.reset();
        }
    }

    private void consumeTrailer() throws IOException {
        checkEqual("CRC", this.source.readIntLe(), (int) this.crc.getValue());
        checkEqual("ISIZE", this.source.readIntLe(), this.inflater.getTotalOut());
    }

    public Timeout timeout() {
        return this.source.timeout();
    }

    public void close() throws IOException {
        this.inflaterSource.close();
    }

    private void updateCrc(Buffer buffer, long offset, long byteCount) {
        Segment s = buffer.head;
        while (offset >= ((long) (s.limit - s.pos))) {
            long offset2 = offset - ((long) (s.limit - s.pos));
            s = s.next;
            offset = offset2;
        }
        while (byteCount > 0) {
            int pos = (int) (((long) s.pos) + offset);
            int toUpdate = (int) Math.min((long) (s.limit - pos), byteCount);
            this.crc.update(s.data, pos, toUpdate);
            long byteCount2 = byteCount - ((long) toUpdate);
            offset = 0;
            s = s.next;
            byteCount = byteCount2;
        }
    }

    private void checkEqual(String name, int expected, int actual) throws IOException {
        if (actual != expected) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", new Object[]{name, Integer.valueOf(actual), Integer.valueOf(expected)}));
        }
    }
}
