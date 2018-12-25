package name.antonsmirnov.firmata.reader;

import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.Message;

public interface IMessageReader<ConcreteMessage extends Message> {
    boolean canRead(byte[] bArr, int i, int i2);

    boolean finishedReading();

    void fireEvent(Listener listener);

    ConcreteMessage getMessage();

    void read(byte[] bArr, int i);

    void startReading();
}
