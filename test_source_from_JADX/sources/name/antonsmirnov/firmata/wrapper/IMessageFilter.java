package name.antonsmirnov.firmata.wrapper;

public interface IMessageFilter {
    boolean isAllowed(MessageWithProperties messageWithProperties);
}
