package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.SysexMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class SysexMessageWriter<ConcreteSysexMessage extends SysexMessage> implements IMessageWriter<ConcreteSysexMessage> {
    public static final int COMMAND_END = 247;
    public static final int COMMAND_START = 240;

    public void write(ConcreteSysexMessage message, ISerial serial) throws SerialException {
        serial.write((int) COMMAND_START);
        writeCommand(message, serial);
        writeData(message, serial);
        serial.write(247);
    }

    protected void writeCommand(ConcreteSysexMessage message, ISerial serial) throws SerialException {
        serial.write(message.getCommand());
    }

    protected void writeData(ConcreteSysexMessage message, ISerial serial) throws SerialException {
        if (message.getData() != null) {
            serial.write(BytesHelper.ENCODE_STRING(message.getData()));
        }
    }
}
