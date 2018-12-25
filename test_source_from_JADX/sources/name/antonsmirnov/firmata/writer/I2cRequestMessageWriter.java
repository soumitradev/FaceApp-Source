package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.message.I2cRequestMessage;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public class I2cRequestMessageWriter<ConcreteRequestMessage extends I2cRequestMessage> extends SysexMessageWriter<ConcreteRequestMessage> {
    protected void writeData(ConcreteRequestMessage message, ISerial serial) throws SerialException {
        writeI2cRequestData(message, serial);
    }

    private void writeI2cRequestData(ConcreteRequestMessage message, ISerial serial) throws SerialException {
        byte[] buffer = new byte[2];
        buffer[0] = (byte) BytesHelper.LSB(message.getSlaveAddress());
        int modeByte = BytesHelper.setBit(BytesHelper.setBit(BytesHelper.setBit(0, 7, false), 6, false), 5, message.isTenBitsMode()) | ((byte) (message.getMode().getValue() << 3));
        if (message.isTenBitsMode()) {
            modeByte |= BytesHelper.MSB(message.getSlaveAddress() & 7);
        }
        buffer[1] = (byte) modeByte;
        serial.write(buffer);
        int[] binaryData = message.getBinaryData();
        if (binaryData != null) {
            serial.write(BytesHelper.ENCODE_INT_ARRAY(binaryData));
        }
    }
}
