package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class AnalogMessage extends Message {
    private int pin;
    private int value;

    public AnalogMessage(int pin, int value) {
        this();
        setPin(pin);
        setValue(value);
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
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
        AnalogMessage message = (AnalogMessage) obj;
        if (message != null && message.getPin() == getPin() && message.getValue() == getValue()) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MessageFormat.format("AnalogMessage[pin={0}, value={1}]", new Object[]{Integer.valueOf(this.pin), Integer.valueOf(this.value)});
    }
}
