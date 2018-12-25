package name.antonsmirnov.firmata.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class SetPinModeMessage extends Message {
    private static Map<Integer, PIN_MODE> modes = new HashMap();
    private int mode;
    private int pin;

    public enum PIN_MODE {
        INPUT(0),
        OUTPUT(1),
        ANALOG(2),
        PWM(3),
        SERVO(4);
        
        private int mode;

        public int getMode() {
            return this.mode;
        }

        private PIN_MODE(int mode) {
            this.mode = mode;
            SetPinModeMessage.modes.put(Integer.valueOf(mode), this);
        }

        public static PIN_MODE find(int mode) {
            return (PIN_MODE) SetPinModeMessage.modes.get(Integer.valueOf(mode));
        }
    }

    protected SetPinModeMessage() {
    }

    public SetPinModeMessage(int pin, int mode) {
        this();
        setPin(pin);
        setMode(mode);
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!super.equals(obj)) {
            return false;
        }
        SetPinModeMessage message = (SetPinModeMessage) obj;
        if (message != null && message.getPin() == getPin() && message.getMode() == getMode()) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MessageFormat.format("SetPinModeMessage[pin={0}, mode={1}]", new Object[]{Integer.valueOf(this.pin), Integer.valueOf(this.mode)});
    }
}
