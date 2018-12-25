package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class BroadcastWaitBrick extends BroadcastBrick {
    private static final long serialVersionUID = 1;

    public BroadcastWaitBrick(String broadcastMessage) {
        super(broadcastMessage);
    }

    public int getViewResource() {
        return R.layout.brick_broadcast_wait;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createBroadcastAction(this.broadcastMessage, 0));
        return null;
    }
}
