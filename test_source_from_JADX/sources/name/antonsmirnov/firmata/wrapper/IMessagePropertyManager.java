package name.antonsmirnov.firmata.wrapper;

public interface IMessagePropertyManager<ConcretePropertyClass> {
    ConcretePropertyClass get(MessageWithProperties messageWithProperties);

    void set(MessageWithProperties messageWithProperties);
}
