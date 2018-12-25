package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ChangeXByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ChangeXByNBrick() {
        addAllowedBrickField(BrickField.X_POSITION_CHANGE, R.id.brick_change_x_edit_text);
    }

    public ChangeXByNBrick(int xMovementValue) {
        this(new Formula(Integer.valueOf(xMovementValue)));
    }

    public ChangeXByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.X_POSITION_CHANGE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_change_x;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeXByNAction(sprite, getFormulaWithBrickField(BrickField.X_POSITION_CHANGE)));
        return null;
    }
}
