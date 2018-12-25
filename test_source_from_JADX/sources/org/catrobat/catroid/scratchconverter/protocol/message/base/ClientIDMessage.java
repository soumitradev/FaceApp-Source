package org.catrobat.catroid.scratchconverter.protocol.message.base;

public class ClientIDMessage extends BaseMessage {
    private final long clientID;

    public ClientIDMessage(long clientID) {
        this.clientID = clientID;
    }

    public long getClientID() {
        return this.clientID;
    }
}
