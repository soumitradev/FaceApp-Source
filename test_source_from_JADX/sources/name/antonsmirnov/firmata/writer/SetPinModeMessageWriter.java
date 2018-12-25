package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.message.SetPinModeMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class SetPinModeMessageWriter implements IMessageWriter<SetPinModeMessage> {
    public static final int COMMAND = 244;

    public void write(SetPinModeMessage message, ISerial serial) throws SerialException {
        serial.write(244);
        serial.write(message.getPin());
        serial.write(message.getMode());
    }
}
