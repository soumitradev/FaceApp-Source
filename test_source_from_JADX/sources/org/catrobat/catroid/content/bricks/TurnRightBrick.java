package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class TurnRightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public TurnRightBrick() {
        addAllowedBrickField(BrickField.TURN_RIGHT_DEGREES, R.id.brick_turn_right_edit_text);
    }

    public TurnRightBrick(double degreesValue) {
        this(new Formula(Double.valueOf(degreesValue)));
    }

    public TurnRightBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.TURN_RIGHT_DEGREES, formula);
    }

    public int getViewResource() {
        return R.layout.brick_turn_right;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createTurnRightAction(sprite, getFormulaWithBrickField(BrickField.TURN_RIGHT_DEGREES)));
        return null;
    }
}
