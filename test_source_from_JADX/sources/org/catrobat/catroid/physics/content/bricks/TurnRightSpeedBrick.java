package org.catrobat.catroid.physics.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class TurnRightSpeedBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public TurnRightSpeedBrick() {
        addAllowedBrickField(BrickField.PHYSICS_TURN_RIGHT_SPEED, R.id.brick_turn_right_speed_edit_text);
    }

    public TurnRightSpeedBrick(double degreesPerSecond) {
        this(new Formula(Double.valueOf(degreesPerSecond)));
    }

    public TurnRightSpeedBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.PHYSICS_TURN_RIGHT_SPEED, formula);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(3));
        super.addRequiredResources(requiredResourcesSet);
    }

    public int getViewResource() {
        return R.layout.brick_physics_turn_right_speed;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createTurnRightSpeedAction(sprite, getFormulaWithBrickField(BrickField.PHYSICS_TURN_RIGHT_SPEED)));
        return null;
    }
}
