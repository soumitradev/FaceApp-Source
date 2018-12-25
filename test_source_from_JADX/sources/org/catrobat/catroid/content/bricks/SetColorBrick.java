package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetColorBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetColorBrick() {
        addAllowedBrickField(BrickField.COLOR, R.id.brick_set_color_edit_text);
    }

    public SetColorBrick(double color) {
        this(new Formula(Double.valueOf(color)));
    }

    public SetColorBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.COLOR, formula);
    }

    public int getViewResource() {
        return R.layout.brick_set_color_to;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetColorAction(sprite, getFormulaWithBrickField(BrickField.COLOR)));
        return null;
    }
}
