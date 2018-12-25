package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class FirmwareVersionMessage extends SysexMessage {
    private int major;
    private int minor;
    private String name;

    public FirmwareVersionMessage(int major, int minor, String name) {
        this();
        setName(name);
        setMajor(major);
        setMinor(minor);
    }

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!super.equals(obj)) {
            return false;
        }
        FirmwareVersionMessage message = (FirmwareVersionMessage) obj;
        if (message != null && message.getMajor() == getMajor() && message.getMinor() == getMinor() && ((message.getName() == null && getName() == null) || (message.getName() != null && message.getName().equals(getName())))) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MessageFormat.format("FirmwareVersionMessage[major={0}, minor={1}, name=''{2}'']", new Object[]{Integer.valueOf(this.major), Integer.valueOf(this.minor), this.name});
    }
}
