package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetYBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetYBrick() {
        addAllowedBrickField(BrickField.Y_POSITION, R.id.brick_set_y_edit_text);
    }

    public SetYBrick(int yPositionValue) {
        this(new Formula(Integer.valueOf(yPositionValue)));
    }

    public SetYBrick(Formula yPosition) {
        this();
        setFormulaWithBrickField(BrickField.Y_POSITION, yPosition);
    }

    public int getViewResource() {
        return R.layout.brick_set_y;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetYAction(sprite, getFormulaWithBrickField(BrickField.Y_POSITION)));
        return null;
    }
}
