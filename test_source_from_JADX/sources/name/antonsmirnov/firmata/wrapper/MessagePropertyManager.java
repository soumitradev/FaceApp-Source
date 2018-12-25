package name.antonsmirnov.firmata.wrapper;

public abstract class MessagePropertyManager<ConcretePropertyClass> implements IMessagePropertyManager<ConcretePropertyClass> {
    private String key;

    protected abstract ConcretePropertyClass createProperty();

    public MessagePropertyManager(String key) {
        this.key = key;
    }

    public ConcretePropertyClass get(MessageWithProperties data) {
        return data.getProperty(this.key);
    }

    public void set(MessageWithProperties data) {
        data.setProperty(this.key, createProperty());
    }
}
