package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ChangeYByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ChangeYByNBrick() {
        addAllowedBrickField(BrickField.Y_POSITION_CHANGE, R.id.brick_change_y_edit_text);
    }

    public ChangeYByNBrick(int yMovementValue) {
        this(new Formula(Integer.valueOf(yMovementValue)));
    }

    public ChangeYByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.Y_POSITION_CHANGE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_change_y;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeYByNAction(sprite, getFormulaWithBrickField(BrickField.Y_POSITION_CHANGE)));
        return null;
    }
}
