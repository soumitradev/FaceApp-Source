package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.BytesHelper;
import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;

public class AnalogMessageReader implements IMessageReader<AnalogMessage> {
    private boolean isHandling;
    private AnalogMessage message;

    public boolean canRead(byte[] buffer, int bufferLength, int command) {
        return command == AnalogMessageWriter.COMMAND;
    }

    public void startReading() {
        this.isHandling = true;
        this.message = new AnalogMessage();
    }

    public void read(byte[] buffer, int length) {
        if (length == 2) {
            this.message.setPin(BytesHelper.DECODE_CHANNEL(buffer[0]));
            return;
        }
        this.message.setValue(BytesHelper.DECODE_BYTE(buffer[1], buffer[2]));
        this.isHandling = false;
    }

    public boolean finishedReading() {
        return this.isHandling ^ 1;
    }

    public AnalogMessage getMessage() {
        return this.message;
    }

    public void fireEvent(Listener listener) {
        listener.onAnalogMessageReceived(getMessage());
    }
}