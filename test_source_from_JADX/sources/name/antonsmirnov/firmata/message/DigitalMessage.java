package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class DigitalMessage extends Message {
    private int port;
    private int value;

    public DigitalMessage(int port, int value) {
        this();
        setPort(port);
        setValue(value);
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!super.equals(obj)) {
            return false;
        }
        DigitalMessage message = (DigitalMessage) obj;
        if (message != null && message.getPort() == getPort() && message.getValue() == getValue()) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MessageFormat.format("DigitalMessage[port={0}, value={1}]", new Object[]{Integer.valueOf(this.port), Integer.valueOf(this.value)});
    }
}
