package name.antonsmirnov.firmata.serial;

public interface ISerialListener<ConcreteSerialImpl> {
    void onDataReceived(ConcreteSerialImpl concreteSerialImpl);

    void onException(Throwable th);
}
