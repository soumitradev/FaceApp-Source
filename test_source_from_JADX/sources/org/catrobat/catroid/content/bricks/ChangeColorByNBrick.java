package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ChangeColorByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ChangeColorByNBrick() {
        addAllowedBrickField(BrickField.COLOR_CHANGE, R.id.brick_change_color_by_edit_text);
    }

    public ChangeColorByNBrick(double changeColorValue) {
        this(new Formula(Double.valueOf(changeColorValue)));
    }

    public ChangeColorByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.COLOR_CHANGE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_change_color_by;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeColorByNAction(sprite, getFormulaWithBrickField(BrickField.COLOR_CHANGE)));
        return null;
    }
}
