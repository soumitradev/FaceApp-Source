package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ChangeSizeByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ChangeSizeByNBrick() {
        addAllowedBrickField(BrickField.SIZE_CHANGE, R.id.brick_change_size_by_edit_text);
    }

    public ChangeSizeByNBrick(double sizeValue) {
        this(new Formula(Double.valueOf(sizeValue)));
    }

    public ChangeSizeByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.SIZE_CHANGE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_change_size_by_n;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeSizeByNAction(sprite, getFormulaWithBrickField(BrickField.SIZE_CHANGE)));
        return null;
    }
}
