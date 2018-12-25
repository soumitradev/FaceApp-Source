package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;

public class ServoConfigMessage extends SysexMessage {
    public static final int COMMAND = 112;
    private int angle;
    private int maxPulse;
    private int minPulse;
    private int pin;

    public ServoConfigMessage() {
        super(112, null);
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getMinPulse() {
        return this.minPulse;
    }

    public void setMinPulse(int minPulse) {
        this.minPulse = minPulse;
    }

    public int getMaxPulse() {
        return this.maxPulse;
    }

    public void setMaxPulse(int maxPulse) {
        this.maxPulse = maxPulse;
    }

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            if (obj.getClass().equals(getClass())) {
                ServoConfigMessage message = (ServoConfigMessage) obj;
                if (message != null && message.getPin() == getPin()) {
                    z = true;
                }
                return z;
            }
        }
        return false;
    }

    public String toString() {
        return MessageFormat.format("ServoConfigMessage[pin={0}, minPulse={1}, maxPulse={2}, angle={3}]", new Object[]{Integer.valueOf(this.pin), Integer.valueOf(this.minPulse), Integer.valueOf(this.maxPulse), Integer.valueOf(this.angle)});
    }
}
