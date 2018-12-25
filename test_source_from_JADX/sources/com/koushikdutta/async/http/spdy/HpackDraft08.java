package com.koushikdutta.async.http.spdy;

import com.google.firebase.analytics.FirebaseAnalytics$Param;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.http.spdy.BitArray.FixedCapacity;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.billthefarmer.mididriver.GeneralMidiConstants;

final class HpackDraft08 {
    private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX = nameToFirstIndex();
    private static final int PREFIX_4_BITS = 15;
    private static final int PREFIX_6_BITS = 63;
    private static final int PREFIX_7_BITS = 127;
    private static final Header[] STATIC_HEADER_TABLE = new Header[]{new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header(FirebaseAnalytics$Param.LOCATION, ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};

    static final class Reader {
        private final List<Header> emittedHeaders = new ArrayList();
        BitArray emittedReferencedHeaders = new FixedCapacity();
        int headerCount = 0;
        Header[] headerTable = new Header[8];
        int headerTableByteCount = 0;
        private int maxHeaderTableByteCount;
        private int maxHeaderTableByteCountSetting;
        int nextHeaderIndex = (this.headerTable.length - 1);
        BitArray referencedHeaders = new FixedCapacity();
        private final ByteBufferList source = new ByteBufferList();

        Reader(int maxHeaderTableByteCountSetting) {
            this.maxHeaderTableByteCountSetting = maxHeaderTableByteCountSetting;
            this.maxHeaderTableByteCount = maxHeaderTableByteCountSetting;
        }

        public void refill(ByteBufferList bb) {
            bb.get(this.source);
        }

        int maxHeaderTableByteCount() {
            return this.maxHeaderTableByteCount;
        }

        void maxHeaderTableByteCountSetting(int newMaxHeaderTableByteCountSetting) {
            this.maxHeaderTableByteCountSetting = newMaxHeaderTableByteCountSetting;
            this.maxHeaderTableByteCount = this.maxHeaderTableByteCountSetting;
            adjustHeaderTableByteCount();
        }

        private void adjustHeaderTableByteCount() {
            if (this.maxHeaderTableByteCount >= this.headerTableByteCount) {
                return;
            }
            if (this.maxHeaderTableByteCount == 0) {
                clearHeaderTable();
            } else {
                evictToRecoverBytes(this.headerTableByteCount - this.maxHeaderTableByteCount);
            }
        }

        private void clearHeaderTable() {
            clearReferenceSet();
            Arrays.fill(this.headerTable, null);
            this.nextHeaderIndex = this.headerTable.length - 1;
            this.headerCount = 0;
            this.headerTableByteCount = 0;
        }

        private int evictToRecoverBytes(int bytesToRecover) {
            int entriesToEvict = 0;
            if (bytesToRecover > 0) {
                for (int j = this.headerTable.length - 1; j >= this.nextHeaderIndex && bytesToRecover > 0; j--) {
                    bytesToRecover -= this.headerTable[j].hpackSize;
                    this.headerTableByteCount -= this.headerTable[j].hpackSize;
                    this.headerCount--;
                    entriesToEvict++;
                }
                this.referencedHeaders.shiftLeft(entriesToEvict);
                this.emittedReferencedHeaders.shiftLeft(entriesToEvict);
                System.arraycopy(this.headerTable, this.nextHeaderIndex + 1, this.headerTable, (this.nextHeaderIndex + 1) + entriesToEvict, this.headerCount);
                this.nextHeaderIndex += entriesToEvict;
            }
            return entriesToEvict;
        }

        void readHeaders() throws IOException {
            while (this.source.hasRemaining()) {
                int b = this.source.get() & 255;
                if (b == 128) {
                    throw new IOException("index == 0");
                } else if ((b & 128) == 128) {
                    readIndexedHeader(readInt(b, 127) - 1);
                } else if (b == 64) {
                    readLiteralHeaderWithIncrementalIndexingNewName();
                } else if ((b & 64) == 64) {
                    readLiteralHeaderWithIncrementalIndexingIndexedName(readInt(b, 63) - 1);
                } else if ((b & 32) != 32) {
                    if (b != 16) {
                        if (b != 0) {
                            readLiteralHeaderWithoutIndexingIndexedName(readInt(b, 15) - 1);
                        }
                    }
                    readLiteralHeaderWithoutIndexingNewName();
                } else if ((b & 16) != 16) {
                    this.maxHeaderTableByteCount = readInt(b, 15);
                    if (this.maxHeaderTableByteCount >= 0) {
                        if (this.maxHeaderTableByteCount <= this.maxHeaderTableByteCountSetting) {
                            adjustHeaderTableByteCount();
                        }
                    }
                    r2 = new StringBuilder();
                    r2.append("Invalid header table byte count ");
                    r2.append(this.maxHeaderTableByteCount);
                    throw new IOException(r2.toString());
                } else if ((b & 15) != 0) {
                    r2 = new StringBuilder();
                    r2.append("Invalid header table state change ");
                    r2.append(b);
                    throw new IOException(r2.toString());
                } else {
                    clearReferenceSet();
                }
            }
        }

        private void clearReferenceSet() {
            this.referencedHeaders.clear();
            this.emittedReferencedHeaders.clear();
        }

        void emitReferenceSet() {
            int i = this.headerTable.length - 1;
            while (i != this.nextHeaderIndex) {
                if (this.referencedHeaders.get(i) && !this.emittedReferencedHeaders.get(i)) {
                    this.emittedHeaders.add(this.headerTable[i]);
                }
                i--;
            }
        }

        List<Header> getAndReset() {
            List<Header> result = new ArrayList(this.emittedHeaders);
            this.emittedHeaders.clear();
            this.emittedReferencedHeaders.clear();
            return result;
        }

        private void readIndexedHeader(int index) throws IOException {
            if (isStaticHeader(index)) {
                index -= this.headerCount;
                if (index > HpackDraft08.STATIC_HEADER_TABLE.length - 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Header index too large ");
                    stringBuilder.append(index + 1);
                    throw new IOException(stringBuilder.toString());
                }
                Header staticEntry = HpackDraft08.STATIC_HEADER_TABLE[index];
                if (this.maxHeaderTableByteCount == 0) {
                    this.emittedHeaders.add(staticEntry);
                } else {
                    insertIntoHeaderTable(-1, staticEntry);
                }
                return;
            }
            int headerTableIndex = headerTableIndex(index);
            if (!this.referencedHeaders.get(headerTableIndex)) {
                this.emittedHeaders.add(this.headerTable[headerTableIndex]);
                this.emittedReferencedHeaders.set(headerTableIndex);
            }
            this.referencedHeaders.toggle(headerTableIndex);
        }

        private int headerTableIndex(int index) {
            return (this.nextHeaderIndex + 1) + index;
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int index) throws IOException {
            this.emittedHeaders.add(new Header(getName(index), readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            this.emittedHeaders.add(new Header(HpackDraft08.checkLowercase(readByteString()), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int nameIndex) throws IOException {
            insertIntoHeaderTable(-1, new Header(getName(nameIndex), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            insertIntoHeaderTable(-1, new Header(HpackDraft08.checkLowercase(readByteString()), readByteString()));
        }

        private ByteString getName(int index) {
            if (isStaticHeader(index)) {
                return HpackDraft08.STATIC_HEADER_TABLE[index - this.headerCount].name;
            }
            return this.headerTable[headerTableIndex(index)].name;
        }

        private boolean isStaticHeader(int index) {
            return index >= this.headerCount;
        }

        private void insertIntoHeaderTable(int index, Header entry) {
            int delta = entry.hpackSize;
            if (index != -1) {
                delta -= this.headerTable[headerTableIndex(index)].hpackSize;
            }
            if (delta > this.maxHeaderTableByteCount) {
                clearHeaderTable();
                this.emittedHeaders.add(entry);
                return;
            }
            int entriesEvicted = evictToRecoverBytes((this.headerTableByteCount + delta) - this.maxHeaderTableByteCount);
            if (index == -1) {
                if (this.headerCount + 1 > this.headerTable.length) {
                    Header[] doubled = new Header[(this.headerTable.length * 2)];
                    System.arraycopy(this.headerTable, 0, doubled, this.headerTable.length, this.headerTable.length);
                    if (doubled.length == 64) {
                        this.referencedHeaders = ((FixedCapacity) this.referencedHeaders).toVariableCapacity();
                        this.emittedReferencedHeaders = ((FixedCapacity) this.emittedReferencedHeaders).toVariableCapacity();
                    }
                    this.referencedHeaders.shiftLeft(this.headerTable.length);
                    this.emittedReferencedHeaders.shiftLeft(this.headerTable.length);
                    this.nextHeaderIndex = this.headerTable.length - 1;
                    this.headerTable = doubled;
                }
                int i = this.nextHeaderIndex;
                this.nextHeaderIndex = i - 1;
                index = i;
                this.referencedHeaders.set(index);
                this.headerTable[index] = entry;
                this.headerCount++;
            } else {
                index += headerTableIndex(index) + entriesEvicted;
                this.referencedHeaders.set(index);
                this.headerTable[index] = entry;
            }
            this.headerTableByteCount += delta;
        }

        private int readByte() throws IOException {
            return this.source.get() & 255;
        }

        int readInt(int firstByte, int prefixMask) throws IOException {
            int prefix = firstByte & prefixMask;
            if (prefix < prefixMask) {
                return prefix;
            }
            int result = prefixMask;
            int shift = 0;
            while (true) {
                int b = readByte();
                if ((b & 128) == 0) {
                    return result + (b << shift);
                }
                result += (b & 127) << shift;
                shift += 7;
            }
        }

        ByteString readByteString() throws IOException {
            int firstByte = readByte();
            boolean huffmanDecode = (firstByte & 128) == 128;
            int length = readInt(firstByte, 127);
            if (huffmanDecode) {
                return ByteString.of(Huffman.get().decode(this.source.getBytes(length)));
            }
            return ByteString.of(this.source.getBytes(length));
        }
    }

    static final class Writer {
        Writer() {
        }

        ByteBufferList writeHeaders(List<Header> headerBlock) throws IOException {
            ByteBufferList out = new ByteBufferList();
            ByteBuffer current = ByteBufferList.obtain(8192);
            int size = headerBlock.size();
            for (int i = 0; i < size; i++) {
                if (current.remaining() < current.capacity() / 2) {
                    current.flip();
                    out.add(current);
                    current = ByteBufferList.obtain(current.capacity() * 2);
                }
                ByteString name = ((Header) headerBlock.get(i)).name.toAsciiLowercase();
                Integer staticIndex = (Integer) HpackDraft08.NAME_TO_FIRST_INDEX.get(name);
                if (staticIndex != null) {
                    writeInt(current, staticIndex.intValue() + 1, 15, 0);
                    writeByteString(current, ((Header) headerBlock.get(i)).value);
                } else {
                    current.put((byte) 0);
                    writeByteString(current, name);
                    writeByteString(current, ((Header) headerBlock.get(i)).value);
                }
            }
            out.add(current);
            return out;
        }

        void writeInt(ByteBuffer out, int value, int prefixMask, int bits) throws IOException {
            if (value < prefixMask) {
                out.put((byte) (bits | value));
                return;
            }
            out.put((byte) (bits | prefixMask));
            value -= prefixMask;
            while (value >= 128) {
                out.put((byte) ((value & 127) | 128));
                value >>>= 7;
            }
            out.put((byte) value);
        }

        void writeByteString(ByteBuffer out, ByteString data) throws IOException {
            writeInt(out, data.size(), 127, 0);
            out.put(data.toByteArray());
        }
    }

    private HpackDraft08() {
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        Map<ByteString, Integer> result = new LinkedHashMap(STATIC_HEADER_TABLE.length);
        for (int i = 0; i < STATIC_HEADER_TABLE.length; i++) {
            if (!result.containsKey(STATIC_HEADER_TABLE[i].name)) {
                result.put(STATIC_HEADER_TABLE[i].name, Integer.valueOf(i));
            }
        }
        return Collections.unmodifiableMap(result);
    }

    private static ByteString checkLowercase(ByteString name) throws IOException {
        int i = 0;
        int length = name.size();
        while (i < length) {
            byte c = name.getByte(i);
            if (c < GeneralMidiConstants.ALTO_SAX || c > GeneralMidiConstants.PAD_2_POLYSYNTH) {
                i++;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PROTOCOL_ERROR response malformed: mixed case name: ");
                stringBuilder.append(name.utf8());
                throw new IOException(stringBuilder.toString());
            }
        }
        return name;
    }
}
