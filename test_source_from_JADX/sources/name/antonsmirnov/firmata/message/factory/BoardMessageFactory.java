package name.antonsmirnov.firmata.message.factory;

import java.text.MessageFormat;
import java.util.Arrays;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.message.SetPinModeMessage.PIN_MODE;

public abstract class BoardMessageFactory implements MessageFactory {
    public static final int MIN_PIN = 0;
    protected int[] analogInPins;
    protected int[] analogOutPins;
    protected int maxPin;
    protected int minPin;

    public int getMinPin() {
        return this.minPin;
    }

    public int getMaxPin() {
        return this.maxPin;
    }

    public int[] getAnalogInPins() {
        return this.analogInPins;
    }

    public int[] getAnalogOutPins() {
        return this.analogOutPins;
    }

    protected static int[] union(int[] array1, int[] array2) {
        int[] array = new int[(array1.length + array2.length)];
        System.arraycopy(array1, 0, array, 0, array1.length);
        System.arraycopy(array2, 0, array, array1.length, array2.length);
        return array;
    }

    protected static int[] arrayFromTo(int from, int to) {
        int[] array = new int[((to - from) + 1)];
        int i = 0;
        while (i < array.length) {
            int from2 = from + 1;
            array[i] = from;
            i++;
            from = from2;
        }
        return array;
    }

    public BoardMessageFactory(int minPin, int maxPin, int[] analogInPins, int[] analogOutPins) {
        this.minPin = minPin;
        this.maxPin = maxPin;
        this.analogInPins = analogInPins;
        this.analogOutPins = analogOutPins;
        Arrays.sort(analogInPins);
        Arrays.sort(analogOutPins);
    }

    protected void validatePin(int pin) throws MessageValidationException {
        if (pin >= this.minPin) {
            if (pin <= this.maxPin) {
                return;
            }
        }
        throw new MessageValidationException(MessageFormat.format("Allowed pin values are [{0}-{1}]", new Object[]{Integer.valueOf(this.minPin), Integer.valueOf(this.maxPin)}));
    }

    protected void validatePort(int port) throws MessageValidationException {
        int ports = (int) Math.ceil(((double) (this.maxPin + 1)) / 8.0d);
        if (port >= 0) {
            if (port <= ports) {
                return;
            }
        }
        throw new MessageValidationException(MessageFormat.format("Allowed port values are [{0}-{1}]", new Object[]{Integer.valueOf(0), Integer.valueOf(ports)}));
    }

    protected void validateAnalogIn(int pin) throws MessageValidationException {
        if (Arrays.binarySearch(this.analogInPins, pin) < 0) {
            throw new MessageValidationException(MessageFormat.format("Allowed analog in pins are [{0}]", new Object[]{arrayToString(this.analogInPins)}));
        }
    }

    public boolean isAnalogIn(int pin) {
        try {
            validateAnalogIn(pin);
            return true;
        } catch (MessageValidationException e) {
            return false;
        }
    }

    protected void validateAnalogOut(int pin) throws MessageValidationException {
        if (Arrays.binarySearch(this.analogOutPins, pin) < 0) {
            throw new MessageValidationException(MessageFormat.format("Allowed analog out (PWM) pins are [{0}]", new Object[]{arrayToString(this.analogOutPins)}));
        }
    }

    public boolean isAnalogOut(int pin) {
        try {
            validateAnalogOut(pin);
            return true;
        } catch (MessageValidationException e) {
            return false;
        }
    }

    protected String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    protected void validateMode(int mode) throws MessageValidationException {
        if (PIN_MODE.find(mode) == null) {
            throw new MessageValidationException(MessageFormat.format("Allowed modes are [{0}]", PIN_MODE.values()));
        }
    }

    protected void validateDigitalValue(int value) throws MessageValidationException {
        if (value != 0 && value != 1) {
            throw new MessageValidationException("Allowed digital values are [0; 1]");
        }
    }

    protected void validateDigitalMask(int value) throws MessageValidationException {
        if (value >= 0) {
            if (value <= 255) {
                return;
            }
        }
        throw new MessageValidationException("Allowed digital mask values are [0-255]");
    }

    private void validateAnalogValue(int value) throws MessageValidationException {
        if (value >= 0) {
            if (value <= 255) {
                return;
            }
        }
        throw new MessageValidationException("Allowed analog values are [0-255]");
    }

    public ReportDigitalPortMessage digitalRead(int port) throws MessageValidationException {
        validatePort(port);
        return new ReportDigitalPortMessage(port, true);
    }

    public ReportAnalogPinMessage analogRead(int pin) throws MessageValidationException {
        validatePin(pin);
        validateAnalogIn(pin);
        return new ReportAnalogPinMessage(pin, true);
    }

    public SetPinModeMessage pinMode(int pin, int mode) throws MessageValidationException {
        validatePin(pin);
        validateMode(mode);
        if (mode == PIN_MODE.ANALOG.getMode()) {
            validateAnalogIn(pin);
        }
        if (mode == PIN_MODE.PWM.getMode()) {
            validateAnalogOut(pin);
        }
        return new SetPinModeMessage(pin, mode);
    }

    public DigitalMessage digitalWrite(int port, int value) throws MessageValidationException {
        validatePort(port);
        validateDigitalMask(value);
        return new DigitalMessage(port, value);
    }

    public AnalogMessage analogWrite(int pin, int value) throws MessageValidationException {
        validatePin(pin);
        validateAnalogOut(pin);
        validateAnalogValue(value);
        return new AnalogMessage(pin, value);
    }
}
