package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetTransparencyBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetTransparencyBrick() {
        addAllowedBrickField(BrickField.TRANSPARENCY, R.id.brick_set_transparency_to_edit_text);
    }

    public SetTransparencyBrick(double transparency) {
        this(new Formula(Double.valueOf(transparency)));
    }

    public SetTransparencyBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.TRANSPARENCY, formula);
    }

    public int getViewResource() {
        return R.layout.brick_set_transparency;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetTransparencyAction(sprite, getFormulaWithBrickField(BrickField.TRANSPARENCY)));
        return null;
    }
}
