package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.generated70026.R;

public class SayBubbleBrick extends ThinkBubbleBrick {
    private static final long serialVersionUID = 1;

    public SayBubbleBrick(String text) {
        super(text);
    }

    public int getViewResource() {
        return R.layout.brick_say_bubble;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createThinkSayBubbleAction(sprite, getFormulaWithBrickField(BrickField.STRING), 0));
        return null;
    }
}
