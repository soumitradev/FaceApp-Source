package name.antonsmirnov.firmata.writer;

import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.serial.ISerial;
import name.antonsmirnov.firmata.serial.SerialException;

public interface IMessageWriter<ConcreteMessage extends Message> {
    void write(ConcreteMessage concreteMessage, ISerial iSerial) throws SerialException;
}
