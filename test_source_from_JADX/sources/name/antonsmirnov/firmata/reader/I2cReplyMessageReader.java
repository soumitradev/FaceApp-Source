package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.I2cReplyMessage;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class I2cReplyMessageReader extends BaseSysexMessageReader<I2cReplyMessage> {
    public I2cReplyMessageReader() {
        super(Byte.valueOf(GeneralMidiConstants.REVERSE_CYMBAL));
    }

    protected I2cReplyMessage buildSysexMessage(byte[] buffer, int bufferLength) {
        I2cReplyMessage message = new I2cReplyMessage();
        message.setSlaveAddress(BytesHelper.DECODE_BYTE(buffer[2], buffer[3]));
        message.setRegister(BytesHelper.DECODE_BYTE(buffer[4], buffer[5]));
        message.setBinaryData(extractIntArrayFromBuffer(buffer, 6, bufferLength - 2));
        return message;
    }

    public void fireEvent(Listener listener) {
        listener.onI2cMessageReceived((I2cReplyMessage) getMessage());
    }
}
