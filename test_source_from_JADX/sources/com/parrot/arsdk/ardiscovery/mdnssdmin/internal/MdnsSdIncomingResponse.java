package com.parrot.arsdk.ardiscovery.mdnssdmin.internal;

import android.support.v4.internal.view.SupportMenu;
import com.parrot.arsdk.ardiscovery.mdnssdmin.MdnsSrvData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;

public class MdnsSdIncomingResponse {
    private final Map<MdnsRecord, Object> entries = new HashMap();

    private class Decoder {
        private byte[] buffer;
        private int pos;

        private Decoder() {
        }

        public void decode(byte[] data) {
            this.buffer = data;
            int cnt = 0;
            this.pos = 0;
            int id = readU16();
            int flags = readU16();
            int questionsCnt = readU16();
            int answersCnt = readU16();
            int authoritiesCnt = readU16();
            int additionalRRsCnt = readU16();
            if (id == 0 && questionsCnt >= 0 && answersCnt >= 0 && authoritiesCnt >= 0 && additionalRRsCnt >= 0) {
                int cnt2;
                for (cnt2 = 0; cnt2 < questionsCnt; cnt2++) {
                    String name = readName();
                    int type = readU16();
                    readU16();
                }
                for (cnt2 = 0; cnt2 < answersCnt; cnt2++) {
                    readResourceRecord();
                }
                for (cnt2 = 0; cnt2 < authoritiesCnt; cnt2++) {
                    readResourceRecord();
                }
                while (cnt < additionalRRsCnt) {
                    readResourceRecord();
                    cnt++;
                }
            }
        }

        private void readResourceRecord() {
            String name = readName();
            Type type = Type.get(readU16());
            int cls = readU16();
            long ttl = readU32();
            int datalen = readU16();
            if (type != null) {
                int end;
                switch (type) {
                    case A:
                        if (datalen == 4) {
                            MdnsSdIncomingResponse.this.entries.put(new MdnsRecord(name, type), String.format(Locale.US, "%d.%d.%d.%d", new Object[]{Integer.valueOf(readU8()), Integer.valueOf(readU8()), Integer.valueOf(readU8()), Integer.valueOf(readU8())}));
                            return;
                        }
                        return;
                    case PTR:
                        MdnsSdIncomingResponse.this.entries.put(new MdnsRecord(name, type), readName());
                        return;
                    case TXT:
                        end = this.pos + datalen;
                        List<String> txts = new ArrayList();
                        while (this.pos < end) {
                            txts.add(readString());
                        }
                        MdnsSdIncomingResponse.this.entries.put(new MdnsRecord(name, type), txts.toArray(new String[txts.size()]));
                        return;
                    case SRV:
                        end = readU16();
                        int weight = readU16();
                        MdnsSdIncomingResponse.this.entries.put(new MdnsRecord(name, type), new MdnsSrvData(readU16(), readName(), ttl));
                        return;
                    default:
                        return;
                }
            }
            this.pos += datalen;
        }

        private int readU8() {
            byte[] bArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            return bArr[i] & 255;
        }

        private int readU16() {
            return ((readU8() << 8) & SupportMenu.USER_MASK) | (readU8() & 255);
        }

        private long readU32() {
            return (((long) readU16()) << 16) | ((long) readU16());
        }

        private String readName() {
            StringBuilder sb = new StringBuilder();
            readNameSegment(sb);
            return sb.toString();
        }

        private void readNameSegment(StringBuilder sb) {
            int len = readU8();
            while (len != 0) {
                int offset;
                if ((len & ReportAnalogPinMessageWriter.COMMAND) != 0) {
                    offset = ((len & 63) << 8) | (readU8() & 255);
                    int savedPos = this.pos;
                    this.pos = offset;
                    readNameSegment(sb);
                    this.pos = savedPos;
                    len = 0;
                } else {
                    for (offset = 0; offset < len; offset++) {
                        byte[] bArr = this.buffer;
                        int i = this.pos;
                        this.pos = i + 1;
                        sb.append((char) bArr[i]);
                    }
                    sb.append('.');
                    len = readU8();
                }
            }
        }

        public String readString() {
            StringBuilder sb = new StringBuilder();
            int len = readU8();
            for (int cnt = 0; cnt < len; cnt++) {
                byte[] bArr = this.buffer;
                int i = this.pos;
                this.pos = i + 1;
                sb.append((char) bArr[i]);
            }
            return sb.toString();
        }
    }

    public MdnsSdIncomingResponse(byte[] data) {
        new Decoder().decode(data);
    }

    public String getAddress(String name) {
        return (String) this.entries.get(new MdnsRecord(name, Type.A));
    }

    public String getPtr(String name) {
        return (String) this.entries.get(new MdnsRecord(name, Type.PTR));
    }

    public MdnsSrvData getService(String name) {
        return (MdnsSrvData) this.entries.get(new MdnsRecord(name, Type.SRV));
    }

    public String[] getTexts(String name) {
        return (String[]) this.entries.get(new MdnsRecord(name, Type.TXT));
    }
}
