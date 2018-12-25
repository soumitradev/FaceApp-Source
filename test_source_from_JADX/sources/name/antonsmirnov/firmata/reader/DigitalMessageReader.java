package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.DigitalMessage;

public class DigitalMessageReader implements IMessageReader<DigitalMessage> {
    private boolean isHandling;
    private DigitalMessage message;

    public boolean canRead(byte[] buffer, int bufferLength, int command) {
        return command == 144;
    }

    public void startReading() {
        this.isHandling = true;
        this.message = new DigitalMessage();
    }

    public void read(byte[] buffer, int length) {
        if (length == 2) {
            this.message.setPort(BytesHelper.DECODE_CHANNEL(buffer[0]));
            return;
        }
        this.message.setValue(BytesHelper.DECODE_BYTE(buffer[1], buffer[2]));
        this.isHandling = false;
    }

    public boolean finishedReading() {
        return this.isHandling ^ 1;
    }

    public DigitalMessage getMessage() {
        return this.message;
    }

    public void fireEvent(Listener listener) {
        listener.onDigitalMessageReceived(getMessage());
    }
}
