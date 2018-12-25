package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ChangeTransparencyByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ChangeTransparencyByNBrick() {
        addAllowedBrickField(BrickField.TRANSPARENCY_CHANGE, R.id.brick_change_transparency_edit_text);
    }

    public ChangeTransparencyByNBrick(double changeTransparencyValue) {
        this(new Formula(Double.valueOf(changeTransparencyValue)));
    }

    public ChangeTransparencyByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.TRANSPARENCY_CHANGE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_change_transparency;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeTransparencyByNAction(sprite, getFormulaWithBrickField(BrickField.TRANSPARENCY_CHANGE)));
        return null;
    }
}
