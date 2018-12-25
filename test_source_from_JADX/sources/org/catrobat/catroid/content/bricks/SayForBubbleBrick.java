package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.generated70026.R;

public class SayForBubbleBrick extends ThinkForBubbleBrick {
    private static final long serialVersionUID = 1;

    public SayForBubbleBrick(String text, float duration) {
        super(text, duration);
    }

    public int getViewResource() {
        return R.layout.brick_say_for_bubble;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createThinkSayForBubbleAction(sprite, getFormulaWithBrickField(BrickField.STRING), 0));
        sequence.addAction(sprite.getActionFactory().createWaitForBubbleBrickAction(sprite, getFormulaWithBrickField(BrickField.DURATION_IN_SECONDS)));
        return null;
    }
}
