package name.antonsmirnov.firmata.wrapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import name.antonsmirnov.firmata.message.Message;

public class MessageWithProperties implements Serializable {
    private Message message;
    private Map<String, Object> properties = new HashMap();

    public MessageWithProperties(Message message) {
        setMessage(message);
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Object getProperty(String key) {
        return this.properties.get(key);
    }

    public void setProperty(String key, Object property) {
        this.properties.put(key, property);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.message.toString());
        stringBuilder.append(" -> ");
        stringBuilder.append(this.properties.toString());
        return stringBuilder.toString();
    }
}
