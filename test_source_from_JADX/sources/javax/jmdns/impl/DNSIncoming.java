package javax.jmdns.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSLabel;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import name.antonsmirnov.firmata.FormatHelper;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;

public final class DNSIncoming extends DNSMessage {
    public static boolean USE_DOMAIN_NAME_FORMAT_FOR_SRV_TARGET = true;
    private static final char[] _nibbleToHex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static Logger logger = Logger.getLogger(DNSIncoming.class.getName());
    private final MessageInputStream _messageInputStream;
    private final DatagramPacket _packet;
    private final long _receivedTime;
    private int _senderUDPPayload;

    public static class MessageInputStream extends ByteArrayInputStream {
        private static Logger logger1 = Logger.getLogger(MessageInputStream.class.getName());
        final Map<Integer, String> _names;

        public MessageInputStream(byte[] buffer, int length) {
            this(buffer, 0, length);
        }

        public MessageInputStream(byte[] buffer, int offset, int length) {
            super(buffer, offset, length);
            this._names = new HashMap();
        }

        public int readByte() {
            return read();
        }

        public int readUnsignedShort() {
            return (read() << 8) | read();
        }

        public int readInt() {
            return (readUnsignedShort() << 16) | readUnsignedShort();
        }

        public byte[] readBytes(int len) {
            byte[] bytes = new byte[len];
            read(bytes, 0, len);
            return bytes;
        }

        public String readUTF(int len) {
            StringBuilder buffer = new StringBuilder(len);
            int index = 0;
            while (index < len) {
                int ch = read();
                int i = ch >> 4;
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        break;
                    default:
                        switch (i) {
                            case 12:
                            case 13:
                                ch = ((ch & 31) << 6) | (read() & 63);
                                index++;
                                break;
                            case 14:
                                ch = (((ch & 15) << 12) | ((read() & 63) << 6)) | (read() & 63);
                                index = (index + 1) + 1;
                                break;
                            default:
                                ch = ((ch & 63) << 4) | (read() & 15);
                                index++;
                                break;
                        }
                }
                buffer.append((char) ch);
                index++;
            }
            return buffer.toString();
        }

        protected synchronized int peek() {
            return this.pos < this.count ? this.buf[this.pos] & 255 : -1;
        }

        public String readName() {
            Map<Integer, StringBuilder> names = new HashMap();
            StringBuilder buffer = new StringBuilder();
            boolean finished = false;
            String compressedLabel = null;
            while (!finished) {
                int len = read();
                if (len != 0) {
                    Logger logger;
                    StringBuilder stringBuilder;
                    switch (DNSLabel.labelForByte(len)) {
                        case Standard:
                            int offset = this.pos - 1;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append(readUTF(len));
                            stringBuilder2.append(".");
                            String label = stringBuilder2.toString();
                            buffer.append(label);
                            for (StringBuilder compressedLabel2 : names.values()) {
                                compressedLabel2.append(label);
                            }
                            names.put(Integer.valueOf(offset), new StringBuilder(label));
                            break;
                        case Compressed:
                            int index = (DNSLabel.labelValue(len) << 8) | read();
                            compressedLabel = (String) this._names.get(Integer.valueOf(index));
                            if (compressedLabel == null) {
                                logger = logger1;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("bad domain name: possible circular name detected. Bad offset: 0x");
                                stringBuilder.append(Integer.toHexString(index));
                                stringBuilder.append(" at 0x");
                                stringBuilder.append(Integer.toHexString(this.pos - 2));
                                logger.severe(stringBuilder.toString());
                                compressedLabel = "";
                            }
                            buffer.append(compressedLabel);
                            for (StringBuilder stringBuilder3 : names.values()) {
                                stringBuilder3.append(compressedLabel);
                            }
                            finished = true;
                            break;
                        case Extended:
                            logger1.severe("Extended label are not currently supported.");
                            break;
                        default:
                            logger = logger1;
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("unsupported dns label type: '");
                            stringBuilder3.append(Integer.toHexString(len & ReportAnalogPinMessageWriter.COMMAND));
                            stringBuilder3.append(FormatHelper.QUOTE);
                            logger.severe(stringBuilder3.toString());
                            break;
                    }
                }
                for (Integer index2 : names.keySet()) {
                    this._names.put(index2, ((StringBuilder) names.get(index2)).toString());
                }
                return buffer.toString();
            }
            for (Integer index22 : names.keySet()) {
                this._names.put(index22, ((StringBuilder) names.get(index22)).toString());
            }
            return buffer.toString();
        }

        public String readNonNameString() {
            return readUTF(read());
        }
    }

    public DNSIncoming(DatagramPacket packet) throws IOException {
        int i = 0;
        super(0, 0, packet.getPort() == DNSConstants.MDNS_PORT);
        this._packet = packet;
        InetAddress source = packet.getAddress();
        this._messageInputStream = new MessageInputStream(packet.getData(), packet.getLength());
        this._receivedTime = System.currentTimeMillis();
        this._senderUDPPayload = DNSConstants.MAX_MSG_TYPICAL;
        try {
            int i2;
            DNSRecord rec;
            setId(this._messageInputStream.readUnsignedShort());
            setFlags(this._messageInputStream.readUnsignedShort());
            int numQuestions = this._messageInputStream.readUnsignedShort();
            int numAnswers = this._messageInputStream.readUnsignedShort();
            int numAuthorities = this._messageInputStream.readUnsignedShort();
            int numAdditionals = this._messageInputStream.readUnsignedShort();
            if (numQuestions > 0) {
                for (i2 = 0; i2 < numQuestions; i2++) {
                    this._questions.add(readQuestion());
                }
            }
            if (numAnswers > 0) {
                for (i2 = 0; i2 < numAnswers; i2++) {
                    rec = readAnswer(source);
                    if (rec != null) {
                        this._answers.add(rec);
                    }
                }
            }
            if (numAuthorities > 0) {
                for (i2 = 0; i2 < numAuthorities; i2++) {
                    rec = readAnswer(source);
                    if (rec != null) {
                        this._authoritativeAnswers.add(rec);
                    }
                }
            }
            if (numAdditionals > 0) {
                while (i < numAdditionals) {
                    DNSRecord rec2 = readAnswer(source);
                    if (rec2 != null) {
                        this._additionals.add(rec2);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            Logger logger = logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DNSIncoming() dump ");
            stringBuilder.append(print(true));
            stringBuilder.append("\n exception ");
            logger.log(level, stringBuilder.toString(), e);
            IOException ioe = new IOException("DNSIncoming corrupted message");
            ioe.initCause(e);
            throw ioe;
        }
    }

    private DNSIncoming(int flags, int id, boolean multicast, DatagramPacket packet, long receivedTime) {
        super(flags, id, multicast);
        this._packet = packet;
        this._messageInputStream = new MessageInputStream(packet.getData(), packet.getLength());
        this._receivedTime = receivedTime;
    }

    public DNSIncoming clone() {
        DNSIncoming in = new DNSIncoming(getFlags(), getId(), isMulticast(), this._packet, this._receivedTime);
        in._senderUDPPayload = this._senderUDPPayload;
        in._questions.addAll(this._questions);
        in._answers.addAll(this._answers);
        in._authoritativeAnswers.addAll(this._authoritativeAnswers);
        in._additionals.addAll(this._additionals);
        return in;
    }

    private DNSQuestion readQuestion() {
        String domain = this._messageInputStream.readName();
        DNSRecordType type = DNSRecordType.typeForIndex(this._messageInputStream.readUnsignedShort());
        if (type == DNSRecordType.TYPE_IGNORE) {
            Logger logger = logger;
            Level level = Level.SEVERE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find record type: ");
            stringBuilder.append(print(true));
            logger.log(level, stringBuilder.toString());
        }
        int recordClassIndex = this._messageInputStream.readUnsignedShort();
        DNSRecordClass recordClass = DNSRecordClass.classForIndex(recordClassIndex);
        return DNSQuestion.newQuestion(domain, type, recordClass, recordClass.isUnique(recordClassIndex));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private javax.jmdns.impl.DNSRecord readAnswer(java.net.InetAddress r41) {
        /*
        r40 = this;
        r1 = r40;
        r2 = r1._messageInputStream;
        r2 = r2.readName();
        r3 = r1._messageInputStream;
        r3 = r3.readUnsignedShort();
        r12 = javax.jmdns.impl.constants.DNSRecordType.typeForIndex(r3);
        r3 = javax.jmdns.impl.constants.DNSRecordType.TYPE_IGNORE;
        r4 = 1;
        if (r12 != r3) goto L_0x003b;
    L_0x0017:
        r3 = logger;
        r5 = java.util.logging.Level.SEVERE;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Could not find record type. domain: ";
        r6.append(r7);
        r6.append(r2);
        r7 = "\n";
        r6.append(r7);
        r7 = r1.print(r4);
        r6.append(r7);
        r6 = r6.toString();
        r3.log(r5, r6);
    L_0x003b:
        r3 = r1._messageInputStream;
        r13 = r3.readUnsignedShort();
        r3 = javax.jmdns.impl.constants.DNSRecordType.TYPE_OPT;
        if (r12 != r3) goto L_0x0048;
    L_0x0045:
        r3 = javax.jmdns.impl.constants.DNSRecordClass.CLASS_UNKNOWN;
        goto L_0x004c;
    L_0x0048:
        r3 = javax.jmdns.impl.constants.DNSRecordClass.classForIndex(r13);
    L_0x004c:
        r14 = r3;
        r3 = javax.jmdns.impl.constants.DNSRecordClass.CLASS_UNKNOWN;
        if (r14 != r3) goto L_0x0081;
    L_0x0051:
        r3 = javax.jmdns.impl.constants.DNSRecordType.TYPE_OPT;
        if (r12 == r3) goto L_0x0081;
    L_0x0055:
        r3 = logger;
        r5 = java.util.logging.Level.SEVERE;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "Could not find record class. domain: ";
        r6.append(r7);
        r6.append(r2);
        r7 = " type: ";
        r6.append(r7);
        r6.append(r12);
        r7 = "\n";
        r6.append(r7);
        r7 = r1.print(r4);
        r6.append(r7);
        r6 = r6.toString();
        r3.log(r5, r6);
    L_0x0081:
        r15 = r14.isUnique(r13);
        r3 = r1._messageInputStream;
        r11 = r3.readInt();
        r3 = r1._messageInputStream;
        r10 = r3.readUnsignedShort();
        r16 = 0;
        r3 = javax.jmdns.impl.DNSIncoming.C17141.$SwitchMap$javax$jmdns$impl$constants$DNSRecordType;
        r5 = r12.ordinal();
        r3 = r3[r5];
        switch(r3) {
            case 1: goto L_0x04f8;
            case 2: goto L_0x04dd;
            case 3: goto L_0x0494;
            case 4: goto L_0x0494;
            case 5: goto L_0x0478;
            case 6: goto L_0x042e;
            case 7: goto L_0x03d9;
            case 8: goto L_0x00c9;
            default: goto L_0x009e;
        };
    L_0x009e:
        r20 = r11;
        r29 = r12;
        r28 = r13;
        r12 = r10;
        r3 = logger;
        r4 = java.util.logging.Level.FINER;
        r3 = r3.isLoggable(r4);
        if (r3 == 0) goto L_0x0518;
    L_0x00af:
        r3 = logger;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "DNSIncoming() unknown type:";
        r4.append(r5);
        r5 = r29;
        r4.append(r5);
        r4 = r4.toString();
        r3.finer(r4);
        goto L_0x051a;
    L_0x00c9:
        r3 = r40.getFlags();
        r3 = javax.jmdns.impl.constants.DNSResultCode.resultCodeForFlags(r3, r11);
        r6 = 16711680; // 0xff0000 float:2.3418052E-38 double:8.256667E-317;
        r6 = r6 & r11;
        r7 = 16;
        r6 = r6 >> r7;
        if (r6 != 0) goto L_0x03ab;
    L_0x00d9:
        r1._senderUDPPayload = r13;
        r9 = 0;
        r17 = 0;
        r18 = 0;
        r19 = 0;
        r20 = 0;
    L_0x00e4:
        r7 = r1._messageInputStream;
        r7 = r7.available();
        if (r7 <= 0) goto L_0x03a3;
    L_0x00ec:
        r7 = 0;
        r22 = 0;
        r4 = r1._messageInputStream;
        r4 = r4.available();
        r8 = 2;
        if (r4 < r8) goto L_0x0393;
    L_0x00f8:
        r4 = r1._messageInputStream;
        r4 = r4.readUnsignedShort();
        r7 = javax.jmdns.impl.constants.DNSOptionCode.resultCodeForFlags(r4);
        r22 = 0;
        r5 = r1._messageInputStream;
        r5 = r5.available();
        if (r5 < r8) goto L_0x037f;
    L_0x010c:
        r5 = r1._messageInputStream;
        r5 = r5.readUnsignedShort();
        r26 = r9;
        r8 = 0;
        r9 = new byte[r8];
        r8 = r1._messageInputStream;
        r8 = r8.available();
        if (r8 < r5) goto L_0x0125;
    L_0x011f:
        r8 = r1._messageInputStream;
        r9 = r8.readBytes(r5);
    L_0x0125:
        r8 = javax.jmdns.impl.DNSIncoming.C17141.$SwitchMap$javax$jmdns$impl$constants$DNSOptionCode;
        r22 = r7.ordinal();
        r8 = r8[r22];
        switch(r8) {
            case 1: goto L_0x01a7;
            case 2: goto L_0x0169;
            case 3: goto L_0x0169;
            case 4: goto L_0x0169;
            case 5: goto L_0x013e;
            default: goto L_0x0130;
        };
    L_0x0130:
        r37 = r11;
        r29 = r12;
        r28 = r13;
        r21 = 16;
        r23 = 1;
        r24 = 0;
        goto L_0x0372;
    L_0x013e:
        r8 = logger;
        r27 = r5;
        r5 = java.util.logging.Level.WARNING;
        r28 = r13;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r29 = r12;
        r12 = "There was an OPT answer. Not currently handled. Option code: ";
        r13.append(r12);
        r13.append(r4);
        r12 = " data: ";
        r13.append(r12);
        r12 = r1._hexString(r9);
        r13.append(r12);
        r12 = r13.toString();
        r8.log(r5, r12);
        goto L_0x019d;
    L_0x0169:
        r27 = r5;
        r29 = r12;
        r28 = r13;
        r5 = logger;
        r8 = java.util.logging.Level.FINE;
        r5 = r5.isLoggable(r8);
        if (r5 == 0) goto L_0x019d;
    L_0x0179:
        r5 = logger;
        r8 = java.util.logging.Level.FINE;
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "There was an OPT answer. Option code: ";
        r12.append(r13);
        r12.append(r7);
        r13 = " data: ";
        r12.append(r13);
        r13 = r1._hexString(r9);
        r12.append(r13);
        r12 = r12.toString();
        r5.log(r8, r12);
    L_0x019d:
        r37 = r11;
        r21 = 16;
        r23 = 1;
        r24 = 0;
        goto L_0x0372;
    L_0x01a7:
        r27 = r5;
        r29 = r12;
        r28 = r13;
        r5 = 0;
        r8 = 0;
        r12 = 0;
        r13 = 0;
        r24 = 0;
        r17 = r24;
        r18 = 0;
        r19 = r9[r18];	 Catch:{ Exception -> 0x02c1 }
        r5 = r19;
        r18 = 1;
        r19 = r9[r18];	 Catch:{ Exception -> 0x02b6 }
        r8 = r19;
        r30 = r4;
        r4 = 6;
        r31 = r5;
        r5 = new byte[r4];	 Catch:{ Exception -> 0x02ad }
        r18 = 2;
        r19 = r9[r18];	 Catch:{ Exception -> 0x02ad }
        r18 = 0;
        r5[r18] = r19;	 Catch:{ Exception -> 0x02ad }
        r18 = 3;
        r19 = r9[r18];	 Catch:{ Exception -> 0x02ad }
        r20 = 1;
        r5[r20] = r19;	 Catch:{ Exception -> 0x02ad }
        r4 = 4;
        r19 = r9[r4];	 Catch:{ Exception -> 0x02ad }
        r20 = 2;
        r5[r20] = r19;	 Catch:{ Exception -> 0x02ad }
        r19 = 5;
        r20 = r9[r19];	 Catch:{ Exception -> 0x02ad }
        r5[r18] = r20;	 Catch:{ Exception -> 0x02ad }
        r20 = 6;
        r22 = r9[r20];	 Catch:{ Exception -> 0x02ad }
        r5[r4] = r22;	 Catch:{ Exception -> 0x02ad }
        r20 = 7;
        r22 = r9[r20];	 Catch:{ Exception -> 0x02ad }
        r5[r19] = r22;	 Catch:{ Exception -> 0x02ad }
        r12 = r5;
        r13 = r12;
        r5 = r9.length;	 Catch:{ Exception -> 0x02ad }
        r4 = 8;
        if (r5 <= r4) goto L_0x0232;
    L_0x01f8:
        r5 = 6;
        r4 = new byte[r5];	 Catch:{ Exception -> 0x0228 }
        r5 = 8;
        r22 = r9[r5];	 Catch:{ Exception -> 0x0228 }
        r5 = 0;
        r4[r5] = r22;	 Catch:{ Exception -> 0x0228 }
        r5 = 9;
        r5 = r9[r5];	 Catch:{ Exception -> 0x0228 }
        r22 = 1;
        r4[r22] = r5;	 Catch:{ Exception -> 0x0228 }
        r5 = 10;
        r5 = r9[r5];	 Catch:{ Exception -> 0x0228 }
        r22 = 2;
        r4[r22] = r5;	 Catch:{ Exception -> 0x0228 }
        r5 = 11;
        r5 = r9[r5];	 Catch:{ Exception -> 0x0228 }
        r4[r18] = r5;	 Catch:{ Exception -> 0x0228 }
        r5 = 12;
        r5 = r9[r5];	 Catch:{ Exception -> 0x0228 }
        r22 = 4;
        r4[r22] = r5;	 Catch:{ Exception -> 0x0228 }
        r5 = 13;
        r5 = r9[r5];	 Catch:{ Exception -> 0x0228 }
        r4[r19] = r5;	 Catch:{ Exception -> 0x0228 }
        r13 = r4;
        goto L_0x0232;
    L_0x0228:
        r0 = move-exception;
        r4 = r0;
        r5 = r31;
        r21 = 16;
        r23 = 1;
        goto L_0x02c9;
    L_0x0232:
        r4 = r9.length;	 Catch:{ Exception -> 0x02ad }
        r22 = 15;
        r26 = 14;
        r5 = 18;
        if (r4 != r5) goto L_0x025a;
    L_0x023b:
        r4 = 4;
        r5 = new byte[r4];	 Catch:{ Exception -> 0x0228 }
        r4 = r9[r26];	 Catch:{ Exception -> 0x0228 }
        r25 = 0;
        r5[r25] = r4;	 Catch:{ Exception -> 0x0228 }
        r4 = r9[r22];	 Catch:{ Exception -> 0x0228 }
        r23 = 1;
        r5[r23] = r4;	 Catch:{ Exception -> 0x0228 }
        r4 = 16;
        r32 = r9[r4];	 Catch:{ Exception -> 0x0228 }
        r4 = 2;
        r5[r4] = r32;	 Catch:{ Exception -> 0x0228 }
        r4 = 17;
        r32 = r9[r4];	 Catch:{ Exception -> 0x0228 }
        r5[r18] = r32;	 Catch:{ Exception -> 0x0228 }
        r4 = r5;
        r17 = r4;
    L_0x025a:
        r4 = r9.length;	 Catch:{ Exception -> 0x02ad }
        r5 = 22;
        if (r4 != r5) goto L_0x02a2;
    L_0x025f:
        r4 = 8;
        r4 = new byte[r4];	 Catch:{ Exception -> 0x02ad }
        r5 = r9[r26];	 Catch:{ Exception -> 0x02ad }
        r25 = 0;
        r4[r25] = r5;	 Catch:{ Exception -> 0x02ad }
        r5 = r9[r22];	 Catch:{ Exception -> 0x02ad }
        r23 = 1;
        r4[r23] = r5;	 Catch:{ Exception -> 0x029e }
        r21 = 16;
        r5 = r9[r21];	 Catch:{ Exception -> 0x029c }
        r22 = 2;
        r4[r22] = r5;	 Catch:{ Exception -> 0x029c }
        r5 = 17;
        r5 = r9[r5];	 Catch:{ Exception -> 0x029c }
        r4[r18] = r5;	 Catch:{ Exception -> 0x029c }
        r5 = 18;
        r5 = r9[r5];	 Catch:{ Exception -> 0x029c }
        r18 = 4;
        r4[r18] = r5;	 Catch:{ Exception -> 0x029c }
        r5 = 19;
        r5 = r9[r5];	 Catch:{ Exception -> 0x029c }
        r4[r19] = r5;	 Catch:{ Exception -> 0x029c }
        r5 = 20;
        r5 = r9[r5];	 Catch:{ Exception -> 0x029c }
        r18 = 6;
        r4[r18] = r5;	 Catch:{ Exception -> 0x029c }
        r5 = 21;
        r5 = r9[r5];	 Catch:{ Exception -> 0x029c }
        r4[r20] = r5;	 Catch:{ Exception -> 0x029c }
        r17 = r4;
        goto L_0x02a6;
    L_0x029c:
        r0 = move-exception;
        goto L_0x02b2;
    L_0x029e:
        r0 = move-exception;
        r21 = 16;
        goto L_0x02b2;
    L_0x02a2:
        r21 = 16;
        r23 = 1;
    L_0x02a6:
        r35 = r7;
        r4 = r17;
        r5 = r31;
        goto L_0x02ed;
    L_0x02ad:
        r0 = move-exception;
        r21 = 16;
        r23 = 1;
    L_0x02b2:
        r4 = r0;
        r5 = r31;
        goto L_0x02c9;
    L_0x02b6:
        r0 = move-exception;
        r30 = r4;
        r31 = r5;
        r21 = 16;
        r23 = 1;
        r4 = r0;
        goto L_0x02c9;
    L_0x02c1:
        r0 = move-exception;
        r30 = r4;
        r21 = 16;
        r23 = 1;
        r4 = r0;
    L_0x02c9:
        r33 = r4;
        r4 = logger;
        r34 = r5;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r35 = r7;
        r7 = "Malformed OPT answer. Option code: Owner data: ";
        r5.append(r7);
        r7 = r1._hexString(r9);
        r5.append(r7);
        r5 = r5.toString();
        r4.warning(r5);
        r4 = r17;
        r5 = r34;
    L_0x02ed:
        r7 = logger;
        r36 = r9;
        r9 = java.util.logging.Level.FINE;
        r7 = r7.isLoggable(r9);
        if (r7 == 0) goto L_0x0364;
    L_0x02f9:
        r7 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r37 = r11;
        r11 = "Unhandled Owner OPT version: ";
        r9.append(r11);
        r9.append(r5);
        r11 = " sequence: ";
        r9.append(r11);
        r9.append(r8);
        r11 = " MAC address: ";
        r9.append(r11);
        r11 = r1._hexString(r12);
        r9.append(r11);
        if (r13 == r12) goto L_0x0338;
    L_0x0320:
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r38 = r5;
        r5 = " wakeup MAC address: ";
        r11.append(r5);
        r5 = r1._hexString(r13);
        r11.append(r5);
        r5 = r11.toString();
        goto L_0x033c;
    L_0x0338:
        r38 = r5;
        r5 = "";
    L_0x033c:
        r9.append(r5);
        if (r4 == 0) goto L_0x0357;
    L_0x0341:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r11 = " password: ";
        r5.append(r11);
        r11 = r1._hexString(r4);
        r5.append(r11);
        r5 = r5.toString();
        goto L_0x0359;
    L_0x0357:
        r5 = "";
    L_0x0359:
        r9.append(r5);
        r5 = r9.toString();
        r7.fine(r5);
        goto L_0x0368;
    L_0x0364:
        r38 = r5;
        r37 = r11;
    L_0x0368:
        r18 = r4;
        r20 = r8;
        r9 = r12;
        r17 = r13;
        r19 = r38;
        goto L_0x0374;
    L_0x0372:
        r9 = r26;
    L_0x0374:
        r13 = r28;
        r12 = r29;
        r11 = r37;
        r4 = 1;
        r7 = 16;
        goto L_0x00e4;
    L_0x037f:
        r30 = r4;
        r35 = r7;
        r37 = r11;
        r29 = r12;
        r28 = r13;
        r4 = logger;
        r5 = java.util.logging.Level.WARNING;
        r7 = "There was a problem reading the OPT record. Ignoring.";
        r4.log(r5, r7);
        goto L_0x03d2;
    L_0x0393:
        r37 = r11;
        r29 = r12;
        r28 = r13;
        r4 = logger;
        r5 = java.util.logging.Level.WARNING;
        r8 = "There was a problem reading the OPT record. Ignoring.";
        r4.log(r5, r8);
        goto L_0x03d2;
    L_0x03a3:
        r28 = r13;
        r20 = r11;
        r5 = r12;
        r12 = r10;
        goto L_0x0520;
    L_0x03ab:
        r37 = r11;
        r29 = r12;
        r28 = r13;
        r4 = logger;
        r5 = java.util.logging.Level.WARNING;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = "There was an OPT answer. Wrong version number: ";
        r7.append(r8);
        r7.append(r6);
        r8 = " result code: ";
        r7.append(r8);
        r7.append(r3);
        r7 = r7.toString();
        r4.log(r5, r7);
    L_0x03d2:
        r12 = r10;
        r5 = r29;
        r20 = r37;
        goto L_0x0520;
    L_0x03d9:
        r37 = r11;
        r29 = r12;
        r28 = r13;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r11 = r3;
        r3 = r1._messageInputStream;
        r3 = r3.readUTF(r10);
        r11.append(r3);
        r3 = " ";
        r12 = r11.indexOf(r3);
        if (r12 <= 0) goto L_0x03fc;
    L_0x03f6:
        r3 = 0;
        r3 = r11.substring(r3, r12);
        goto L_0x0400;
    L_0x03fc:
        r3 = r11.toString();
    L_0x0400:
        r13 = r3.trim();
        if (r12 <= 0) goto L_0x040d;
    L_0x0406:
        r3 = r12 + 1;
        r3 = r11.substring(r3);
        goto L_0x040f;
    L_0x040d:
        r3 = "";
    L_0x040f:
        r17 = r3.trim();
        r18 = new javax.jmdns.impl.DNSRecord$HostInformation;
        r3 = r18;
        r4 = r2;
        r5 = r14;
        r6 = r15;
        r7 = r37;
        r8 = r13;
        r9 = r17;
        r3.<init>(r4, r5, r6, r7, r8, r9);
        r16 = r18;
        r12 = r10;
        r3 = r16;
        r5 = r29;
        r20 = r37;
        goto L_0x0522;
    L_0x042e:
        r37 = r11;
        r29 = r12;
        r28 = r13;
        r3 = r1._messageInputStream;
        r12 = r3.readUnsignedShort();
        r3 = r1._messageInputStream;
        r13 = r3.readUnsignedShort();
        r3 = r1._messageInputStream;
        r17 = r3.readUnsignedShort();
        r3 = "";
        r4 = USE_DOMAIN_NAME_FORMAT_FOR_SRV_TARGET;
        if (r4 == 0) goto L_0x0455;
    L_0x044c:
        r4 = r1._messageInputStream;
        r3 = r4.readName();
    L_0x0452:
        r18 = r3;
        goto L_0x045c;
    L_0x0455:
        r4 = r1._messageInputStream;
        r3 = r4.readNonNameString();
        goto L_0x0452;
    L_0x045c:
        r19 = new javax.jmdns.impl.DNSRecord$Service;
        r3 = r19;
        r4 = r2;
        r5 = r14;
        r6 = r15;
        r7 = r37;
        r8 = r12;
        r9 = r13;
        r11 = r10;
        r10 = r17;
        r39 = r12;
        r20 = r37;
        r12 = r11;
        r11 = r18;
        r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11);
        r16 = r19;
        goto L_0x0513;
    L_0x0478:
        r20 = r11;
        r29 = r12;
        r28 = r13;
        r12 = r10;
        r9 = new javax.jmdns.impl.DNSRecord$Text;
        r3 = r1._messageInputStream;
        r8 = r3.readBytes(r12);
        r3 = r9;
        r4 = r2;
        r5 = r14;
        r6 = r15;
        r7 = r20;
        r3.<init>(r4, r5, r6, r7, r8);
        r16 = r9;
        goto L_0x0513;
    L_0x0494:
        r20 = r11;
        r29 = r12;
        r28 = r13;
        r12 = r10;
        r3 = "";
        r4 = r1._messageInputStream;
        r9 = r4.readName();
        r3 = r9.length();
        if (r3 <= 0) goto L_0x04b9;
    L_0x04a9:
        r10 = new javax.jmdns.impl.DNSRecord$Pointer;
        r3 = r10;
        r4 = r2;
        r5 = r14;
        r6 = r15;
        r7 = r20;
        r8 = r9;
        r3.<init>(r4, r5, r6, r7, r8);
        r16 = r10;
        goto L_0x0513;
    L_0x04b9:
        r3 = logger;
        r4 = java.util.logging.Level.WARNING;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "PTR record of class: ";
        r5.append(r6);
        r5.append(r14);
        r6 = ", there was a problem reading the service name of the answer for domain:";
        r5.append(r6);
        r5.append(r2);
        r5 = r5.toString();
        r3.log(r4, r5);
        r5 = r29;
        goto L_0x0520;
    L_0x04dd:
        r20 = r11;
        r29 = r12;
        r28 = r13;
        r12 = r10;
        r9 = new javax.jmdns.impl.DNSRecord$IPv6Address;
        r3 = r1._messageInputStream;
        r8 = r3.readBytes(r12);
        r3 = r9;
        r4 = r2;
        r5 = r14;
        r6 = r15;
        r7 = r20;
        r3.<init>(r4, r5, r6, r7, r8);
        r16 = r9;
        goto L_0x0513;
    L_0x04f8:
        r20 = r11;
        r29 = r12;
        r28 = r13;
        r12 = r10;
        r9 = new javax.jmdns.impl.DNSRecord$IPv4Address;
        r3 = r1._messageInputStream;
        r8 = r3.readBytes(r12);
        r3 = r9;
        r4 = r2;
        r5 = r14;
        r6 = r15;
        r7 = r20;
        r3.<init>(r4, r5, r6, r7, r8);
        r16 = r9;
    L_0x0513:
        r3 = r16;
        r5 = r29;
        goto L_0x0522;
    L_0x0518:
        r5 = r29;
    L_0x051a:
        r3 = r1._messageInputStream;
        r6 = (long) r12;
        r3.skip(r6);
    L_0x0520:
        r3 = r16;
    L_0x0522:
        if (r3 == 0) goto L_0x052a;
    L_0x0524:
        r4 = r41;
        r3.setRecordSource(r4);
        goto L_0x052c;
    L_0x052a:
        r4 = r41;
    L_0x052c:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.jmdns.impl.DNSIncoming.readAnswer(java.net.InetAddress):javax.jmdns.impl.DNSRecord");
    }

    String print(boolean dump) {
        StringBuilder buf = new StringBuilder();
        buf.append(print());
        if (dump) {
            byte[] data = new byte[this._packet.getLength()];
            System.arraycopy(this._packet.getData(), 0, data, 0, data.length);
            buf.append(print(data));
        }
        return buf.toString();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(isQuery() ? "dns[query," : "dns[response,");
        if (this._packet.getAddress() != null) {
            buf.append(this._packet.getAddress().getHostAddress());
        }
        buf.append(':');
        buf.append(this._packet.getPort());
        buf.append(", length=");
        buf.append(this._packet.getLength());
        buf.append(", id=0x");
        buf.append(Integer.toHexString(getId()));
        if (getFlags() != 0) {
            buf.append(", flags=0x");
            buf.append(Integer.toHexString(getFlags()));
            if ((getFlags() & 32768) != 0) {
                buf.append(":r");
            }
            if ((getFlags() & 1024) != 0) {
                buf.append(":aa");
            }
            if ((getFlags() & 512) != 0) {
                buf.append(":tc");
            }
        }
        if (getNumberOfQuestions() > 0) {
            buf.append(", questions=");
            buf.append(getNumberOfQuestions());
        }
        if (getNumberOfAnswers() > 0) {
            buf.append(", answers=");
            buf.append(getNumberOfAnswers());
        }
        if (getNumberOfAuthorities() > 0) {
            buf.append(", authorities=");
            buf.append(getNumberOfAuthorities());
        }
        if (getNumberOfAdditionals() > 0) {
            buf.append(", additionals=");
            buf.append(getNumberOfAdditionals());
        }
        if (getNumberOfQuestions() > 0) {
            buf.append("\nquestions:");
            for (DNSQuestion question : this._questions) {
                buf.append("\n\t");
                buf.append(question);
            }
        }
        if (getNumberOfAnswers() > 0) {
            buf.append("\nanswers:");
            for (DNSRecord record : this._answers) {
                buf.append("\n\t");
                buf.append(record);
            }
        }
        if (getNumberOfAuthorities() > 0) {
            buf.append("\nauthorities:");
            for (DNSRecord record2 : this._authoritativeAnswers) {
                buf.append("\n\t");
                buf.append(record2);
            }
        }
        if (getNumberOfAdditionals() > 0) {
            buf.append("\nadditionals:");
            for (DNSRecord record22 : this._additionals) {
                buf.append("\n\t");
                buf.append(record22);
            }
        }
        buf.append("]");
        return buf.toString();
    }

    void append(DNSIncoming that) {
        if (isQuery() && isTruncated() && that.isQuery()) {
            this._questions.addAll(that.getQuestions());
            this._answers.addAll(that.getAnswers());
            this._authoritativeAnswers.addAll(that.getAuthorities());
            this._additionals.addAll(that.getAdditionals());
            return;
        }
        throw new IllegalArgumentException();
    }

    public int elapseSinceArrival() {
        return (int) (System.currentTimeMillis() - this._receivedTime);
    }

    public int getSenderUDPPayload() {
        return this._senderUDPPayload;
    }

    private String _hexString(byte[] bytes) {
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (int b : bytes) {
            int b2 = b2 & 255;
            result.append(_nibbleToHex[b2 / 16]);
            result.append(_nibbleToHex[b2 % 16]);
        }
        return result.toString();
    }
}
