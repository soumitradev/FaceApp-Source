package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.message.ReportProtocolVersionMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class ReportProtocolVersionMessageWriter implements IMessageWriter<ReportProtocolVersionMessage> {
    public static final int COMMAND = 249;

    public void write(ReportProtocolVersionMessage message, ISerial serial) throws SerialException {
        serial.write(249);
    }
}
