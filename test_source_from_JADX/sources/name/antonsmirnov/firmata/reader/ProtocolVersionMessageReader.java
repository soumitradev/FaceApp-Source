package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;

public class ProtocolVersionMessageReader implements IMessageReader<ProtocolVersionMessage> {
    private boolean isHandling;
    private ProtocolVersionMessage message;

    public boolean canRead(byte[] buffer, int bufferLength, int command) {
        return bufferLength == 1 && buffer[0] == (byte) -7;
    }

    public void startReading() {
        this.isHandling = true;
        this.message = new ProtocolVersionMessage();
    }

    public void read(byte[] buffer, int length) {
        byte incomingByte = buffer[length - 1];
        if (length == 2) {
            this.message.setMajor(incomingByte);
            return;
        }
        this.message.setMinor(incomingByte);
        this.isHandling = false;
    }

    public boolean finishedReading() {
        return this.isHandling ^ 1;
    }

    public ProtocolVersionMessage getMessage() {
        return this.message;
    }

    public void fireEvent(Listener listener) {
        listener.onProtocolVersionMessageReceived(getMessage());
    }
}
