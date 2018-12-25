package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class SamplingIntervalMessage extends SysexMessage {
    public static final int COMMAND = 122;
    private int interval;

    public SamplingIntervalMessage() {
        super(COMMAND, null);
    }

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String toString() {
        return MessageFormat.format("SamplingIntervalMessage[interval={0}]", new Object[]{Integer.valueOf(this.interval)});
    }
}
