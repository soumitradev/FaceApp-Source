package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class StringSysexMessageReader extends BaseSysexMessageReader<StringSysexMessage> {
    public StringSysexMessageReader() {
        super(Byte.valueOf(GeneralMidiConstants.AGOGO));
    }

    protected StringSysexMessage buildSysexMessage(byte[] buffer, int bufferLength) {
        StringSysexMessage message = new StringSysexMessage();
        message.setData(extractStringFromBuffer(buffer, 2, bufferLength - 2));
        return message;
    }

    public void fireEvent(Listener listener) {
        listener.onStringSysexMessageReceived((StringSysexMessage) getMessage());
    }
}
