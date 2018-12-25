package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.ReportAnalogPinMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class ReportAnalogPinMessageWriter implements IMessageWriter<ReportAnalogPinMessage> {
    public static final int COMMAND = 192;

    public void write(ReportAnalogPinMessage message, ISerial serial) throws SerialException {
        serial.write(BytesHelper.ENCODE_CHANNEL(message.getPin()) | COMMAND);
        serial.write(message.isEnable());
    }
}
