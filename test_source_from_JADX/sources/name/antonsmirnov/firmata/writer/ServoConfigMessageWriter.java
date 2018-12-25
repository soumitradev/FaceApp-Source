package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.ServoConfigMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class ServoConfigMessageWriter extends SysexMessageWriter<ServoConfigMessage> {
    protected void writeData(ServoConfigMessage message, ISerial serial) throws SerialException {
        writeServoData(message, serial);
    }

    private void writeServoData(ServoConfigMessage message, ISerial serial) throws SerialException {
        serial.write(new byte[]{(byte) message.getPin(), (byte) BytesHelper.LSB(message.getMinPulse()), (byte) BytesHelper.MSB(message.getMinPulse()), (byte) BytesHelper.LSB(message.getMaxPulse()), (byte) BytesHelper.MSB(message.getMaxPulse()), (byte) BytesHelper.LSB(message.getAngle()), (byte) BytesHelper.MSB(message.getAngle())});
    }
}
