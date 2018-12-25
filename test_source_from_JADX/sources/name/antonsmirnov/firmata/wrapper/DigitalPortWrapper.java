package name.antonsmirnov.firmata.wrapper;

import java.util.HashMap;
import java.util.Map;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.serial.SerialException;

public class DigitalPortWrapper implements IFirmata {
    private IFirmata firmata;
    private Map<Integer, Integer> portValues = new HashMap();

    public DigitalPortWrapper(IFirmata firmata) {
        this.firmata = firmata;
    }

    public void addListener(Listener listener) {
        this.firmata.addListener(listener);
    }

    public void removeListener(Listener listener) {
        this.firmata.removeListener(listener);
    }

    public boolean containsListener(Listener listener) {
        return this.firmata.containsListener(listener);
    }

    public void clearListeners() {
        this.firmata.clearListeners();
    }

    public Map<Integer, Integer> getPortValues() {
        return this.portValues;
    }

    public void clear() {
        this.portValues.clear();
    }

    public void send(Message message) throws SerialException {
        this.firmata.send(message);
        if (message instanceof DigitalMessage) {
            DigitalMessage digitalMessage = (DigitalMessage) message;
            this.portValues.put(Integer.valueOf(digitalMessage.getPort()), Integer.valueOf(digitalMessage.getValue()));
        }
    }

    public void onDataReceived(int incomingByte) {
        this.firmata.onDataReceived(incomingByte);
    }
}
