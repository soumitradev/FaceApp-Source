package javax.jmdns.impl;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.jmdns.impl.constants.DNSConstants;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;

public final class DNSOutgoing extends DNSMessage {
    private static final int HEADER_SIZE = 12;
    public static boolean USE_DOMAIN_NAME_COMPRESSION = true;
    private final MessageOutputStream _additionalsAnswersBytes;
    private final MessageOutputStream _answersBytes;
    private final MessageOutputStream _authoritativeAnswersBytes;
    private int _maxUDPPayload;
    Map<String, Integer> _names;
    private final MessageOutputStream _questionsBytes;

    public static class MessageOutputStream extends ByteArrayOutputStream {
        private final int _offset;
        private final DNSOutgoing _out;

        MessageOutputStream(int size, DNSOutgoing out) {
            this(size, out, 0);
        }

        MessageOutputStream(int size, DNSOutgoing out, int offset) {
            super(size);
            this._out = out;
            this._offset = offset;
        }

        void writeByte(int value) {
            write(value & 255);
        }

        void writeBytes(String str, int off, int len) {
            for (int i = 0; i < len; i++) {
                writeByte(str.charAt(off + i));
            }
        }

        void writeBytes(byte[] data) {
            if (data != null) {
                writeBytes(data, 0, data.length);
            }
        }

        void writeBytes(byte[] data, int off, int len) {
            for (int i = 0; i < len; i++) {
                writeByte(data[off + i]);
            }
        }

        void writeShort(int value) {
            writeByte(value >> 8);
            writeByte(value);
        }

        void writeInt(int value) {
            writeShort(value >> 16);
            writeShort(value);
        }

        void writeUTF(String str, int off, int len) {
            int i;
            int i2 = 0;
            int utflen = 0;
            for (i = 0; i < len; i++) {
                int ch = str.charAt(off + i);
                if (ch >= 1 && ch <= MetaEvent.SEQUENCER_SPECIFIC) {
                    utflen++;
                } else if (ch > 2047) {
                    utflen += 3;
                } else {
                    utflen += 2;
                }
            }
            writeByte(utflen);
            while (true) {
                i = i2;
                if (i < len) {
                    i2 = str.charAt(off + i);
                    if (i2 >= 1 && i2 <= MetaEvent.SEQUENCER_SPECIFIC) {
                        writeByte(i2);
                    } else if (i2 > 2047) {
                        writeByte(((i2 >> 12) & 15) | AnalogMessageWriter.COMMAND);
                        writeByte(((i2 >> 6) & 63) | 128);
                        writeByte(((i2 >> 0) & 63) | 128);
                    } else {
                        writeByte(((i2 >> 6) & 31) | ReportAnalogPinMessageWriter.COMMAND);
                        writeByte(((i2 >> 0) & 63) | 128);
                    }
                    i2 = i + 1;
                } else {
                    return;
                }
            }
        }

        void writeName(String name) {
            writeName(name, true);
        }

        void writeName(String name, boolean useCompression) {
            String aName = name;
            while (true) {
                int n = aName.indexOf(46);
                if (n < 0) {
                    n = aName.length();
                }
                if (n <= 0) {
                    writeByte(0);
                    return;
                }
                String label = aName.substring(0, n);
                if (useCompression && DNSOutgoing.USE_DOMAIN_NAME_COMPRESSION) {
                    Integer offset = (Integer) this._out._names.get(aName);
                    if (offset != null) {
                        int val = offset.intValue();
                        writeByte((val >> 8) | ReportAnalogPinMessageWriter.COMMAND);
                        writeByte(val & 255);
                        return;
                    }
                    this._out._names.put(aName, Integer.valueOf(size() + this._offset));
                    writeUTF(label, 0, label.length());
                } else {
                    writeUTF(label, 0, label.length());
                }
                aName = aName.substring(n);
                if (aName.startsWith(".")) {
                    aName = aName.substring(1);
                }
            }
        }

        void writeQuestion(DNSQuestion question) {
            writeName(question.getName());
            writeShort(question.getRecordType().indexValue());
            writeShort(question.getRecordClass().indexValue());
        }

        void writeRecord(DNSRecord rec, long now) {
            writeName(rec.getName());
            writeShort(rec.getRecordType().indexValue());
            int indexValue = rec.getRecordClass().indexValue();
            int i = (rec.isUnique() && this._out.isMulticast()) ? 32768 : 0;
            writeShort(indexValue | i);
            writeInt(now == 0 ? rec.getTTL() : rec.getRemainingTTL(now));
            MessageOutputStream record = new MessageOutputStream(512, this._out, (this._offset + size()) + 2);
            rec.write(record);
            byte[] byteArray = record.toByteArray();
            writeShort(byteArray.length);
            write(byteArray, 0, byteArray.length);
        }
    }

    public DNSOutgoing(int flags) {
        this(flags, true, DNSConstants.MAX_MSG_TYPICAL);
    }

    public DNSOutgoing(int flags, boolean multicast) {
        this(flags, multicast, DNSConstants.MAX_MSG_TYPICAL);
    }

    public DNSOutgoing(int flags, boolean multicast, int senderUDPPayload) {
        super(flags, 0, multicast);
        this._names = new HashMap();
        this._maxUDPPayload = senderUDPPayload > 0 ? senderUDPPayload : DNSConstants.MAX_MSG_TYPICAL;
        this._questionsBytes = new MessageOutputStream(senderUDPPayload, this);
        this._answersBytes = new MessageOutputStream(senderUDPPayload, this);
        this._authoritativeAnswersBytes = new MessageOutputStream(senderUDPPayload, this);
        this._additionalsAnswersBytes = new MessageOutputStream(senderUDPPayload, this);
    }

    public int availableSpace() {
        return ((((this._maxUDPPayload - 12) - this._questionsBytes.size()) - this._answersBytes.size()) - this._authoritativeAnswersBytes.size()) - this._additionalsAnswersBytes.size();
    }

    public void addQuestion(DNSQuestion rec) throws IOException {
        MessageOutputStream record = new MessageOutputStream(512, this);
        record.writeQuestion(rec);
        byte[] byteArray = record.toByteArray();
        if (byteArray.length < availableSpace()) {
            this._questions.add(rec);
            this._questionsBytes.write(byteArray, 0, byteArray.length);
            return;
        }
        throw new IOException("message full");
    }

    public void addAnswer(DNSIncoming in, DNSRecord rec) throws IOException {
        if (in == null || !rec.suppressedBy(in)) {
            addAnswer(rec, 0);
        }
    }

    public void addAnswer(DNSRecord rec, long now) throws IOException {
        if (rec == null) {
            return;
        }
        if (now == 0 || !rec.isExpired(now)) {
            MessageOutputStream record = new MessageOutputStream(512, this);
            record.writeRecord(rec, now);
            byte[] byteArray = record.toByteArray();
            if (byteArray.length < availableSpace()) {
                this._answers.add(rec);
                this._answersBytes.write(byteArray, 0, byteArray.length);
                return;
            }
            throw new IOException("message full");
        }
    }

    public void addAuthorativeAnswer(DNSRecord rec) throws IOException {
        MessageOutputStream record = new MessageOutputStream(512, this);
        record.writeRecord(rec, 0);
        byte[] byteArray = record.toByteArray();
        if (byteArray.length < availableSpace()) {
            this._authoritativeAnswers.add(rec);
            this._authoritativeAnswersBytes.write(byteArray, 0, byteArray.length);
            return;
        }
        throw new IOException("message full");
    }

    public void addAdditionalAnswer(DNSIncoming in, DNSRecord rec) throws IOException {
        MessageOutputStream record = new MessageOutputStream(512, this);
        record.writeRecord(rec, 0);
        byte[] byteArray = record.toByteArray();
        if (byteArray.length < availableSpace()) {
            this._additionals.add(rec);
            this._additionalsAnswersBytes.write(byteArray, 0, byteArray.length);
            return;
        }
        throw new IOException("message full");
    }

    public byte[] data() {
        long now = System.currentTimeMillis();
        this._names.clear();
        MessageOutputStream message = new MessageOutputStream(this._maxUDPPayload, this);
        message.writeShort(this._multicast ? 0 : getId());
        message.writeShort(getFlags());
        message.writeShort(getNumberOfQuestions());
        message.writeShort(getNumberOfAnswers());
        message.writeShort(getNumberOfAuthorities());
        message.writeShort(getNumberOfAdditionals());
        for (DNSQuestion question : this._questions) {
            message.writeQuestion(question);
        }
        for (DNSRecord record : this._answers) {
            message.writeRecord(record, now);
        }
        for (DNSRecord record2 : this._authoritativeAnswers) {
            message.writeRecord(record2, now);
        }
        for (DNSRecord record22 : this._additionals) {
            message.writeRecord(record22, now);
        }
        return message.toByteArray();
    }

    public boolean isQuery() {
        return (getFlags() & 32768) == 0;
    }

    String print(boolean dump) {
        StringBuilder buf = new StringBuilder();
        buf.append(print());
        if (dump) {
            buf.append(print(data()));
        }
        return buf.toString();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(isQuery() ? "dns[query:" : "dns[response:");
        buf.append(" id=0x");
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
        buf.append("\nnames=");
        buf.append(this._names);
        buf.append("]");
        return buf.toString();
    }

    public int getMaxUDPPayload() {
        return this._maxUDPPayload;
    }
}
