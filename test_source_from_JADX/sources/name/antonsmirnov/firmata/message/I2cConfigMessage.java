package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class I2cConfigMessage extends SysexMessage {
    public static final int COMMAND = 120;
    private int delay;
    private boolean on;

    public I2cConfigMessage() {
        super(120, null);
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String toString() {
        return MessageFormat.format("I2cConfigMessage[on={0}, delay={1}]", new Object[]{Boolean.valueOf(this.on), Integer.valueOf(this.delay)});
    }
}
