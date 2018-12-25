package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class FirmwareVersionMessageReader extends BaseSysexMessageReader<FirmwareVersionMessage> {
    public FirmwareVersionMessageReader() {
        super(Byte.valueOf(GeneralMidiConstants.BREATH_NOISE));
    }

    protected FirmwareVersionMessage buildSysexMessage(byte[] buffer, int bufferLength) {
        FirmwareVersionMessage message = new FirmwareVersionMessage();
        message.setMajor(buffer[2]);
        message.setMinor(buffer[3]);
        message.setName(extractStringFromBuffer(buffer, 4, bufferLength - 2));
        return message;
    }

    public void fireEvent(Listener listener) {
        listener.onFirmwareVersionMessageReceived((FirmwareVersionMessage) this.message);
    }
}
