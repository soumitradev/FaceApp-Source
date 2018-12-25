package name.antonsmirnov.firmata.wrapper;

public class StubMessageFilter implements IMessageFilter {
    public boolean isAllowed(MessageWithProperties data) {
        return true;
    }
}
