package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class DigitalMessageWriter implements IMessageWriter<DigitalMessage> {
    public static final int COMMAND = 144;

    public void write(DigitalMessage message, ISerial serial) throws SerialException {
        serial.write(BytesHelper.ENCODE_CHANNEL(message.getPort()) | 144);
        serial.write(BytesHelper.LSB(message.getValue()));
        serial.write(BytesHelper.MSB(message.getValue()));
    }
}
