package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class BroadcastBrick extends BroadcastMessageBrick {
    private static final long serialVersionUID = 1;
    protected String broadcastMessage;

    public BroadcastBrick(String broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    public String getBroadcastMessage() {
        return this.broadcastMessage;
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    public int getViewResource() {
        return R.layout.brick_broadcast;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createBroadcastAction(this.broadcastMessage, 1));
        return null;
    }
}
