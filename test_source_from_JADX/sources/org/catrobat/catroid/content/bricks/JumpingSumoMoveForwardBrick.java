package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class JumpingSumoMoveForwardBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public JumpingSumoMoveForwardBrick() {
        addAllowedBrickField(BrickField.JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS, R.id.brick_jumping_sumo_move_forward_edit_text_second);
        addAllowedBrickField(BrickField.JUMPING_SUMO_SPEED, R.id.brick_jumping_sumo_move_forward_edit_text_power);
    }

    public JumpingSumoMoveForwardBrick(int durationInMilliseconds, int powerInPercent) {
        this(new Formula(Double.valueOf(((double) durationInMilliseconds) / 1000.0d)), new Formula(Integer.valueOf(powerInPercent)));
    }

    public JumpingSumoMoveForwardBrick(Formula durationInSeconds, Formula powerInPercent) {
        this();
        setFormulaWithBrickField(BrickField.JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS, durationInSeconds);
        setFormulaWithBrickField(BrickField.JUMPING_SUMO_SPEED, powerInPercent);
    }

    public int getViewResource() {
        return R.layout.brick_jumping_sumo_move_forward;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(23));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createJumpingSumoMoveForwardAction(sprite, getFormulaWithBrickField(BrickField.JUMPING_SUMO_TIME_TO_DRIVE_IN_SECONDS), getFormulaWithBrickField(BrickField.JUMPING_SUMO_SPEED)));
        return null;
    }
}
