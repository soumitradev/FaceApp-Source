package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class BroadcastReceiverBrick extends BroadcastMessageBrick implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private BroadcastScript broadcastScript;

    public BroadcastReceiverBrick(BroadcastScript broadcastScript) {
        broadcastScript.setScriptBrick(this);
        this.commentedOut = broadcastScript.isCommentedOut();
        this.broadcastScript = broadcastScript;
    }

    public String getBroadcastMessage() {
        return this.broadcastScript.getBroadcastMessage();
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.broadcastScript.setBroadcastMessage(broadcastMessage);
    }

    public Script getScript() {
        return this.broadcastScript;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        BroadcastReceiverBrick clone = (BroadcastReceiverBrick) super.clone();
        clone.broadcastScript = (BroadcastScript) this.broadcastScript.clone();
        clone.broadcastScript.setScriptBrick(clone);
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_broadcast_receive;
    }

    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }
}
