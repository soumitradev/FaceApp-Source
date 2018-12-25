package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetPenSizeBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetPenSizeBrick() {
        addAllowedBrickField(BrickField.PEN_SIZE, R.id.brick_set_pen_size_edit_text);
    }

    public SetPenSizeBrick(double penSize) {
        this(new Formula(Double.valueOf(penSize)));
    }

    public SetPenSizeBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.PEN_SIZE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_set_pen_size;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetPenSizeAction(sprite, getFormulaWithBrickField(BrickField.PEN_SIZE)));
        return null;
    }
}
