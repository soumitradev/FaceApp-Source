package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class SysexMessage extends Message {
    private int command;
    private String data;

    public SysexMessage(int command, String data) {
        this();
        setCommand(command);
        setData(data);
    }

    public int getCommand() {
        return this.command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!super.equals(obj)) {
            return false;
        }
        SysexMessage message = (SysexMessage) obj;
        if (message != null && message.getCommand() == getCommand() && ((message.getData() == null && getData() == null) || (message.getData() != null && message.getData().equals(getData())))) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MessageFormat.format("SysexMessage[command={0}, data=\"{1}\"]", new Object[]{Integer.valueOf(this.command), this.data});
    }
}
