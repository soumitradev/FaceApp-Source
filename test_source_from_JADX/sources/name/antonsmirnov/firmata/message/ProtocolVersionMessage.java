package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class ProtocolVersionMessage extends Message {
    private int major;
    private int minor;

    public int getMajor() {
        return this.major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return this.minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public ProtocolVersionMessage(int major, int minor) {
        this();
        setMajor(major);
        setMinor(minor);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!super.equals(obj)) {
            return false;
        }
        ProtocolVersionMessage message = (ProtocolVersionMessage) obj;
        if (message != null && message.getMajor() == getMajor() && message.getMinor() == getMinor()) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MessageFormat.format("ProtocolVersionMessage[major={0}, minor={1}]", new Object[]{Integer.valueOf(this.major), Integer.valueOf(this.minor)});
    }
}
