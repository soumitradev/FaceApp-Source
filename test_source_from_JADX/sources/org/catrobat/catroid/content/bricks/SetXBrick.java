package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetXBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetXBrick() {
        addAllowedBrickField(BrickField.X_POSITION, R.id.brick_set_x_edit_text);
    }

    public SetXBrick(int xPositionValue) {
        this(new Formula(Integer.valueOf(xPositionValue)));
    }

    public SetXBrick(Formula xPosition) {
        this();
        setFormulaWithBrickField(BrickField.X_POSITION, xPosition);
    }

    public int getViewResource() {
        return R.layout.brick_set_x;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetXAction(sprite, getFormulaWithBrickField(BrickField.X_POSITION)));
        return null;
    }
}
