package name.antonsmirnov.firmata.wrapper;

public class DirectionMessagePropertyManager extends MessagePropertyManager<Boolean> {
    private static final String KEY = "isIncoming";
    private boolean isIncoming;

    public boolean isIncoming() {
        return this.isIncoming;
    }

    public DirectionMessagePropertyManager(boolean isIncoming) {
        super(KEY);
        this.isIncoming = isIncoming;
    }

    protected Boolean createProperty() {
        return Boolean.valueOf(this.isIncoming);
    }
}
