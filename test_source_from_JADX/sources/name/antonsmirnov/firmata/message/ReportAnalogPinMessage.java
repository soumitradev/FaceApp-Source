package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class ReportAnalogPinMessage extends Message {
    private boolean enable;
    private int pin;

    public ReportAnalogPinMessage(int pin, boolean enable) {
        this();
        setPin(pin);
        setEnable(enable);
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
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
        ReportAnalogPinMessage message = (ReportAnalogPinMessage) obj;
        if (message != null && message.getPin() == getPin() && message.isEnable() == isEnable()) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MessageFormat.format("ReportAnalogPinMessage[pin={0}, enable={1}]", new Object[]{Integer.valueOf(this.pin), Boolean.valueOf(this.enable)});
    }
}
