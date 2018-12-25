package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class PointInDirectionBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public PointInDirectionBrick() {
        addAllowedBrickField(BrickField.DEGREES, R.id.brick_point_in_direction_edit_text);
    }

    public PointInDirectionBrick(double direction) {
        this(new Formula(Double.valueOf(direction)));
    }

    public PointInDirectionBrick(Formula direction) {
        this();
        setFormulaWithBrickField(BrickField.DEGREES, direction);
    }

    public int getViewResource() {
        return R.layout.brick_point_in_direction;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPointInDirectionAction(sprite, getFormulaWithBrickField(BrickField.DEGREES)));
        return null;
    }
}
