package name.antonsmirnov.firmata;

import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.I2cReplyMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;
import name.antonsmirnov.firmata.serial.SerialException;

public interface IFirmata {

    public interface Listener {
        void onAnalogMessageReceived(AnalogMessage analogMessage);

        void onDigitalMessageReceived(DigitalMessage digitalMessage);

        void onFirmwareVersionMessageReceived(FirmwareVersionMessage firmwareVersionMessage);

        void onI2cMessageReceived(I2cReplyMessage i2cReplyMessage);

        void onProtocolVersionMessageReceived(ProtocolVersionMessage protocolVersionMessage);

        void onStringSysexMessageReceived(StringSysexMessage stringSysexMessage);

        void onSysexMessageReceived(SysexMessage sysexMessage);

        void onUnknownByteReceived(int i);
    }

    public static class StubListener implements Listener {
        public void onAnalogMessageReceived(AnalogMessage message) {
        }

        public void onDigitalMessageReceived(DigitalMessage message) {
        }

        public void onFirmwareVersionMessageReceived(FirmwareVersionMessage message) {
        }

        public void onProtocolVersionMessageReceived(ProtocolVersionMessage message) {
        }

        public void onSysexMessageReceived(SysexMessage message) {
        }

        public void onStringSysexMessageReceived(StringSysexMessage message) {
        }

        public void onI2cMessageReceived(I2cReplyMessage message) {
        }

        public void onUnknownByteReceived(int byteValue) {
        }
    }

    void addListener(Listener listener);

    void clearListeners();

    boolean containsListener(Listener listener);

    void onDataReceived(int i);

    void removeListener(Listener listener);

    void send(Message message) throws SerialException;
}
