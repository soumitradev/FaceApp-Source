package name.antonsmirnov.firmata.wrapper;

public class DirectionMessageFilter implements IMessageFilter {
    private DirectionMessagePropertyManager propertyManager;

    public DirectionMessageFilter(DirectionMessagePropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public boolean isAllowed(MessageWithProperties data) {
        return ((Boolean) this.propertyManager.get(data)).equals(Boolean.valueOf(this.propertyManager.isIncoming()));
    }
}
