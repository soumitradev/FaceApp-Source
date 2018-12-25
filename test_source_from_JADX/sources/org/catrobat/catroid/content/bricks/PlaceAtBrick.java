package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class PlaceAtBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public PlaceAtBrick() {
        addAllowedBrickField(BrickField.X_POSITION, R.id.brick_place_at_edit_text_x);
        addAllowedBrickField(BrickField.Y_POSITION, R.id.brick_place_at_edit_text_y);
    }

    public PlaceAtBrick(int xPositionValue, int yPositionValue) {
        this(new Formula(Integer.valueOf(xPositionValue)), new Formula(Integer.valueOf(yPositionValue)));
    }

    public PlaceAtBrick(Formula xPosition, Formula yPosition) {
        this();
        setFormulaWithBrickField(BrickField.X_POSITION, xPosition);
        setFormulaWithBrickField(BrickField.Y_POSITION, yPosition);
    }

    public int getViewResource() {
        return R.layout.brick_place_at;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlaceAtAction(sprite, getFormulaWithBrickField(BrickField.X_POSITION), getFormulaWithBrickField(BrickField.Y_POSITION)));
        return null;
    }
}
