package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.ReportDigitalPortMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class ReportDigitalPortMessageWriter implements IMessageWriter<ReportDigitalPortMessage> {
    public static final int COMMAND = 208;

    public void write(ReportDigitalPortMessage message, ISerial serial) throws SerialException {
        serial.write(BytesHelper.ENCODE_CHANNEL(message.getPort()) | COMMAND);
        serial.write(message.isEnable());
    }
}
