package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class StringSysexMessage extends SysexMessage {
    public static final int COMMAND = 113;

    public StringSysexMessage() {
        super(113, null);
    }

    public StringSysexMessage(String data) {
        this();
        setData(data);
    }

    public String toString() {
        return MessageFormat.format("StringSysexMessage[data=\"{0}\"]", new Object[]{getData()});
    }
}
