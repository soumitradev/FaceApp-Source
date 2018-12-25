package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class WaitUntilBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public WaitUntilBrick() {
        addAllowedBrickField(BrickField.IF_CONDITION, R.id.brick_wait_until_edit_text);
    }

    public WaitUntilBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.IF_CONDITION, formula);
    }

    public int getViewResource() {
        return R.layout.brick_wait_until;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createWaitUntilAction(sprite, getFormulaWithBrickField(BrickField.IF_CONDITION)));
        return null;
    }
}
