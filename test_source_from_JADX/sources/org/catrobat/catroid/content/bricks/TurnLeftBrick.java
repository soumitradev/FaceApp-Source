package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class TurnLeftBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public TurnLeftBrick() {
        addAllowedBrickField(BrickField.TURN_LEFT_DEGREES, R.id.brick_turn_left_edit_text);
    }

    public TurnLeftBrick(double degreesValue) {
        this(new Formula(Double.valueOf(degreesValue)));
    }

    public TurnLeftBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.TURN_LEFT_DEGREES, formula);
    }

    public int getViewResource() {
        return R.layout.brick_turn_left;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createTurnLeftAction(sprite, getFormulaWithBrickField(BrickField.TURN_LEFT_DEGREES)));
        return null;
    }
}
