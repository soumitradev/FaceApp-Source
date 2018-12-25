package name.antonsmirnov.firmata.wrapper;

import java.util.HashMap;
import java.util.Map;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.serial.SerialException;

public class PinModeWrapper implements IFirmata {
    private IFirmata firmata;
    private Listener listener;
    private Map<Integer, Integer> pinsConfig;

    public interface Listener {
        void onSetPinMode(int i, int i2);
    }

    public void addListener(name.antonsmirnov.firmata.IFirmata.Listener listener) {
        this.firmata.addListener(listener);
    }

    public void removeListener(name.antonsmirnov.firmata.IFirmata.Listener listener) {
        this.firmata.removeListener(listener);
    }

    public boolean containsListener(name.antonsmirnov.firmata.IFirmata.Listener listener) {
        return this.firmata.containsListener(listener);
    }

    public void clearListeners() {
        this.firmata.clearListeners();
    }

    public Map<Integer, Integer> getPinsConfig() {
        return this.pinsConfig;
    }

    public PinModeWrapper(IFirmata firmata) {
        this(firmata, null);
    }

    public PinModeWrapper(IFirmata firmata, Listener listener) {
        this.pinsConfig = new HashMap();
        this.firmata = firmata;
        this.listener = listener;
        clear();
    }

    public void clear() {
        this.pinsConfig.clear();
    }

    public void send(Message message) throws SerialException {
        this.firmata.send(message);
        if (message instanceof SetPinModeMessage) {
            SetPinModeMessage setPinModeMessage = (SetPinModeMessage) message;
            this.pinsConfig.put(Integer.valueOf(setPinModeMessage.getPin()), Integer.valueOf(setPinModeMessage.getMode()));
            if (this.listener != null) {
                this.listener.onSetPinMode(setPinModeMessage.getPin(), setPinModeMessage.getMode());
            }
        }
    }

    public void onDataReceived(int incomingByte) {
        this.firmata.onDataReceived(incomingByte);
    }
}
