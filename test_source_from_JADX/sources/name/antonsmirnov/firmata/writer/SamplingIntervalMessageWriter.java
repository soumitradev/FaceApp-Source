package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.SamplingIntervalMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class SamplingIntervalMessageWriter extends SysexMessageWriter<SamplingIntervalMessage> {
    protected void writeData(SamplingIntervalMessage message, ISerial serial) throws SerialException {
        writeIntervalData(message, serial);
    }

    private void writeIntervalData(SamplingIntervalMessage message, ISerial serial) throws SerialException {
        serial.write(new byte[]{(byte) BytesHelper.LSB(message.getInterval()), (byte) BytesHelper.MSB(message.getInterval())});
    }
}
