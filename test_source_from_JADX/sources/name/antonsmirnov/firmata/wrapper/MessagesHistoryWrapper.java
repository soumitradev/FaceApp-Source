package name.antonsmirnov.firmata.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.IFirmata.Listener;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.I2cReplyMessage;
import name.antonsmirnov.firmata.message.Message;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;
import name.antonsmirnov.firmata.serial.SerialException;

public class MessagesHistoryWrapper implements IFirmata, Listener {
    private IFirmata firmata;
    private List<MessageWithProperties> messages = new ArrayList();
    private IMessagePropertyManager[] propertyManagers;
    private IMessageFilter receivedFilter = new DirectionMessageFilter(this.receivedPropertyManager);
    private DirectionMessagePropertyManager receivedPropertyManager = new DirectionMessagePropertyManager(true);
    private IMessageFilter sentFilter = new DirectionMessageFilter(this.sentPropertyManager);
    private DirectionMessagePropertyManager sentPropertyManager = new DirectionMessagePropertyManager(false);
    private IMessageFilter stubMessageFilter = new StubMessageFilter();

    public MessagesHistoryWrapper(IFirmata firmata, IMessagePropertyManager... propertyManagers) {
        this.firmata = firmata;
        this.propertyManagers = propertyManagers;
        firmata.addListener(this);
        clear();
    }

    public void addListener(Listener listener) {
        this.firmata.addListener(listener);
    }

    public void removeListener(Listener listener) {
        this.firmata.removeListener(listener);
    }

    public boolean containsListener(Listener listener) {
        return this.firmata.containsListener(listener);
    }

    public void clearListeners() {
        this.firmata.clearListeners();
    }

    protected void addCommonProperties(MessageWithProperties data) {
        for (IMessagePropertyManager eachPropertyManager : this.propertyManagers) {
            eachPropertyManager.set(data);
        }
    }

    protected void rememberReceivedMessage(Message message) {
        MessageWithProperties newData = new MessageWithProperties(message);
        addCommonProperties(newData);
        this.receivedPropertyManager.set(newData);
        this.messages.add(newData);
    }

    private void rememberSentMessage(Message message) {
        MessageWithProperties newData = new MessageWithProperties(message);
        addCommonProperties(newData);
        this.sentPropertyManager.set(newData);
        this.messages.add(newData);
    }

    public List<MessageWithProperties> getMessages() {
        return getMessages(this.stubMessageFilter);
    }

    public List<MessageWithProperties> getMessages(IMessageFilter filter) {
        List<MessageWithProperties> filteredMessages = new CopyOnWriteArrayList();
        for (MessageWithProperties eachMessage : this.messages) {
            if (filter.isAllowed(eachMessage)) {
                filteredMessages.add(eachMessage);
            }
        }
        return filteredMessages;
    }

    public List<MessageWithProperties> getReceivedMessages() {
        return getMessages(this.receivedFilter);
    }

    public List<MessageWithProperties> getSentMessages() {
        return getMessages(this.sentFilter);
    }

    public MessageWithProperties getLastReceivedMessageWithProperties() {
        List<MessageWithProperties> receivedMessages = getReceivedMessages();
        return receivedMessages.size() > 0 ? (MessageWithProperties) receivedMessages.get(receivedMessages.size() - 1) : null;
    }

    public void clear() {
        this.messages.clear();
    }

    public void onAnalogMessageReceived(AnalogMessage message) {
        rememberReceivedMessage(message);
    }

    public void onDigitalMessageReceived(DigitalMessage message) {
        rememberReceivedMessage(message);
    }

    public void onFirmwareVersionMessageReceived(FirmwareVersionMessage message) {
        rememberReceivedMessage(message);
    }

    public void onProtocolVersionMessageReceived(ProtocolVersionMessage message) {
        rememberReceivedMessage(message);
    }

    public void onSysexMessageReceived(SysexMessage message) {
        rememberReceivedMessage(message);
    }

    public void onStringSysexMessageReceived(StringSysexMessage message) {
        rememberReceivedMessage(message);
    }

    public void onI2cMessageReceived(I2cReplyMessage message) {
        rememberReceivedMessage(message);
    }

    public void onUnknownByteReceived(int byteValue) {
    }

    public void send(Message message) throws SerialException {
        this.firmata.send(message);
        rememberSentMessage(message);
    }

    public void onDataReceived(int incomingByte) {
        this.firmata.onDataReceived(incomingByte);
    }
}
