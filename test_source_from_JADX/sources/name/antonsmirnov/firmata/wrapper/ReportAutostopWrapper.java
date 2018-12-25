package name.antonsmirnov.firmata.wrapper;

import java.util.HashSet;
import java.util.Set;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.IFirmata.StubListener;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.serial.SerialException;

public class ReportAutostopWrapper extends StubListener implements IFirmata {
    private Set<Integer> analogReporting = new HashSet();
    private Set<Integer> digitalReporting = new HashSet();
    private IFirmata firmata;

    public ReportAutostopWrapper(IFirmata firmata) {
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

    public void send(Message message) throws SerialException {
        this.firmata.send(message);
        if (message instanceof ReportDigitalPortMessage) {
            ReportDigitalPortMessage digitalMessage = (ReportDigitalPortMessage) message;
            if (digitalMessage.isEnable()) {
                this.digitalReporting.add(Integer.valueOf(digitalMessage.getPort()));
            }
        }
        if (message instanceof ReportAnalogPinMessage) {
            ReportAnalogPinMessage analogMessage = (ReportAnalogPinMessage) message;
            if (analogMessage.isEnable()) {
                this.analogReporting.add(Integer.valueOf(analogMessage.getPin()));
            }
        }
    }

    public void onAnalogMessageReceived(AnalogMessage message) {
        if (this.analogReporting.contains(Integer.valueOf(message.getPin()))) {
            this.analogReporting.remove(Integer.valueOf(message.getPin()));
            disableAnalogReporting(message);
        }
    }

    private void disableAnalogReporting(AnalogMessage message) {
        try {
            this.firmata.send(new ReportAnalogPinMessage(message.getPin(), false));
        } catch (SerialException e) {
        }
    }

    public void onDigitalMessageReceived(DigitalMessage message) {
        if (this.digitalReporting.contains(Integer.valueOf(message.getPort()))) {
            this.digitalReporting.remove(Integer.valueOf(message.getPort()));
            disableDigitalReporting(message);
        }
    }

    private void disableDigitalReporting(DigitalMessage message) {
        try {
            this.firmata.send(new ReportDigitalPortMessage(message.getPort(), false));
        } catch (SerialException e) {
        }
    }

    public void clear() {
        this.digitalReporting.clear();
        this.analogReporting.clear();
    }

    public void onDataReceived(int incomingByte) {
        this.firmata.onDataReceived(incomingByte);
    }
}
