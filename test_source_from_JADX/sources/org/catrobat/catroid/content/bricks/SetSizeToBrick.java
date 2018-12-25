package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetSizeToBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetSizeToBrick() {
        addAllowedBrickField(BrickField.SIZE, R.id.brick_set_size_to_edit_text);
    }

    public SetSizeToBrick(double size) {
        this(new Formula(Double.valueOf(size)));
    }

    public SetSizeToBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.SIZE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_set_size_to;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetSizeToAction(sprite, getFormulaWithBrickField(BrickField.SIZE)));
        return null;
    }
}
