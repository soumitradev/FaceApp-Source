package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class AnalogMessageWriter implements IMessageWriter<AnalogMessage> {
    public static final int COMMAND = 224;

    public void write(AnalogMessage message, ISerial serial) throws SerialException {
        serial.write(BytesHelper.ENCODE_CHANNEL(message.getPin()) | COMMAND);
        serial.write(BytesHelper.LSB(message.getValue()));
        serial.write(BytesHelper.MSB(message.getValue()));
    }
}
