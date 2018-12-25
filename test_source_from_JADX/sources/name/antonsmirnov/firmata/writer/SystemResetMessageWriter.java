package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.message.SystemResetMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class SystemResetMessageWriter implements IMessageWriter<SystemResetMessage> {
    public static final int COMMAND = 255;

    public void write(SystemResetMessage message, ISerial serial) throws SerialException {
        serial.write(255);
    }
}
