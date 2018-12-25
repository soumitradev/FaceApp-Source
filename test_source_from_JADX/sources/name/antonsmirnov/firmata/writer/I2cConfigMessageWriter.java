package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.I2cConfigMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class I2cConfigMessageWriter extends SysexMessageWriter<I2cConfigMessage> {
    protected void writeData(I2cConfigMessage message, ISerial serial) throws SerialException {
        writeI2cConfigData(message, serial);
    }

    private void writeI2cConfigData(I2cConfigMessage message, ISerial serial) throws SerialException {
        serial.write(new byte[]{(byte) message.isOn(), (byte) BytesHelper.LSB(message.getDelay()), (byte) BytesHelper.MSB(message.getDelay())});
    }
}
