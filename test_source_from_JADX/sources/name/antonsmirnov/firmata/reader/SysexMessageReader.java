package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.SysexMessage;

public class SysexMessageReader extends BaseSysexMessageReader<SysexMessage> {
    public SysexMessageReader() {
        super(null);
    }

    protected SysexMessage buildSysexMessage(byte[] buffer, int bufferLength) {
        SysexMessage message = new SysexMessage();
        message.setCommand(buffer[1]);
        message.setData(extractStringFromBuffer(buffer, 2, bufferLength - 2));
        return message;
    }

    public void fireEvent(Listener listener) {
        listener.onSysexMessageReceived(this.message);
    }
}
