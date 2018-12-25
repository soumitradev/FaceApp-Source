package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class ReportDigitalPortMessage extends Message {
    private boolean enable;
    private int port;

    public ReportDigitalPortMessage(int port, boolean enable) {
        this();
        setPort(port);
        setEnable(enable);
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!super.equals(obj)) {
            return false;
        }
        ReportDigitalPortMessage message = (ReportDigitalPortMessage) obj;
        if (message != null && message.getPort() == getPort() && message.isEnable() == isEnable()) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MessageFormat.format("ReportDigitalPortMessage[port={0}, enable={1}]", new Object[]{Integer.valueOf(this.port), Boolean.valueOf(this.enable)});
    }
}
