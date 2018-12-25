package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.BroadcastReceiverBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.eventids.BroadcastEventId;
import org.catrobat.catroid.content.eventids.EventId;

public class BroadcastScript extends Script {
    private static final long serialVersionUID = 1;
    private String receivedMessage;

    public BroadcastScript(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new BroadcastReceiverBrick(this);
        }
        return this.scriptBrick;
    }

    public String getBroadcastMessage() {
        return this.receivedMessage;
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.receivedMessage = broadcastMessage;
    }

    public EventId createEventId(Sprite sprite) {
        return new BroadcastEventId(this.receivedMessage);
    }
}
