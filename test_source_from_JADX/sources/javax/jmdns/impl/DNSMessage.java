package javax.jmdns.impl;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;

public abstract class DNSMessage {
    public static final boolean MULTICAST = true;
    public static final boolean UNICAST = false;
    protected final List<DNSRecord> _additionals = Collections.synchronizedList(new LinkedList());
    protected final List<DNSRecord> _answers = Collections.synchronizedList(new LinkedList());
    protected final List<DNSRecord> _authoritativeAnswers = Collections.synchronizedList(new LinkedList());
    private int _flags;
    private int _id;
    boolean _multicast;
    protected final List<DNSQuestion> _questions = Collections.synchronizedList(new LinkedList());

    protected DNSMessage(int flags, int id, boolean multicast) {
        this._flags = flags;
        this._id = id;
        this._multicast = multicast;
    }

    public int getId() {
        return this._multicast ? 0 : this._id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public int getFlags() {
        return this._flags;
    }

    public void setFlags(int flags) {
        this._flags = flags;
    }

    public boolean isMulticast() {
        return this._multicast;
    }

    public Collection<? extends DNSQuestion> getQuestions() {
        return this._questions;
    }

    public int getNumberOfQuestions() {
        return getQuestions().size();
    }

    public Collection<? extends DNSRecord> getAllAnswers() {
        List<DNSRecord> aList = new ArrayList((this._answers.size() + this._authoritativeAnswers.size()) + this._additionals.size());
        aList.addAll(this._answers);
        aList.addAll(this._authoritativeAnswers);
        aList.addAll(this._additionals);
        return aList;
    }

    public Collection<? extends DNSRecord> getAnswers() {
        return this._answers;
    }

    public int getNumberOfAnswers() {
        return getAnswers().size();
    }

    public Collection<? extends DNSRecord> getAuthorities() {
        return this._authoritativeAnswers;
    }

    public int getNumberOfAuthorities() {
        return getAuthorities().size();
    }

    public Collection<? extends DNSRecord> getAdditionals() {
        return this._additionals;
    }

    public int getNumberOfAdditionals() {
        return getAdditionals().size();
    }

    public boolean isTruncated() {
        return (this._flags & 512) != 0;
    }

    public boolean isQuery() {
        return (this._flags & 32768) == 0;
    }

    public boolean isResponse() {
        return (this._flags & 32768) == 32768;
    }

    public boolean isEmpty() {
        return ((getNumberOfQuestions() + getNumberOfAnswers()) + getNumberOfAuthorities()) + getNumberOfAdditionals() == 0;
    }

    String print() {
        StringBuffer buf = new StringBuffer(200);
        buf.append(toString());
        buf.append("\n");
        for (DNSQuestion question : this._questions) {
            buf.append("\tquestion:      ");
            buf.append(question);
            buf.append("\n");
        }
        for (DNSRecord answer : this._answers) {
            buf.append("\tanswer:        ");
            buf.append(answer);
            buf.append("\n");
        }
        for (DNSRecord answer2 : this._authoritativeAnswers) {
            buf.append("\tauthoritative: ");
            buf.append(answer2);
            buf.append("\n");
        }
        for (DNSRecord answer22 : this._additionals) {
            buf.append("\tadditional:    ");
            buf.append(answer22);
            buf.append("\n");
        }
        return buf.toString();
    }

    protected String print(byte[] data) {
        StringBuilder buf = new StringBuilder(4000);
        int len = data.length;
        for (int off = 0; off < len; off += 32) {
            int i;
            int n = Math.min(32, len - off);
            if (off < 16) {
                buf.append(' ');
            }
            if (off < 256) {
                buf.append(' ');
            }
            if (off < 4096) {
                buf.append(' ');
            }
            buf.append(Integer.toHexString(off));
            buf.append(':');
            int index = 0;
            while (index < n) {
                if (index % 8 == 0) {
                    buf.append(' ');
                }
                buf.append(Integer.toHexString((data[off + index] & SysexMessageWriter.COMMAND_START) >> 4));
                buf.append(Integer.toHexString((data[off + index] & 15) >> 0));
                index++;
            }
            if (index < 32) {
                for (i = index; i < 32; i++) {
                    if (i % 8 == 0) {
                        buf.append(' ');
                    }
                    buf.append("  ");
                }
            }
            buf.append("    ");
            for (index = 0; index < n; index++) {
                if (index % 8 == 0) {
                    buf.append(' ');
                }
                i = data[off + index] & 255;
                char c = (i <= 32 || i >= MetaEvent.SEQUENCER_SPECIFIC) ? '.' : (char) i;
                buf.append(c);
            }
            buf.append("\n");
            if (off + 32 >= 2048) {
                buf.append("....\n");
                break;
            }
        }
        return buf.toString();
    }
}
