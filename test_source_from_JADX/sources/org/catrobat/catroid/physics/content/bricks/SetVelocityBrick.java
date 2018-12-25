package org.catrobat.catroid.physics.content.bricks;

import com.badlogic.gdx.math.Vector2;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetVelocityBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetVelocityBrick() {
        addAllowedBrickField(BrickField.PHYSICS_VELOCITY_X, R.id.brick_set_velocity_edit_text_x);
        addAllowedBrickField(BrickField.PHYSICS_VELOCITY_Y, R.id.brick_set_velocity_edit_text_y);
    }

    public SetVelocityBrick(Vector2 velocity) {
        this(new Formula(Float.valueOf(velocity.f16x)), new Formula(Float.valueOf(velocity.f17y)));
    }

    public SetVelocityBrick(Formula velocityX, Formula velocityY) {
        this();
        setFormulaWithBrickField(BrickField.PHYSICS_VELOCITY_X, velocityX);
        setFormulaWithBrickField(BrickField.PHYSICS_VELOCITY_Y, velocityY);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(3));
        super.addRequiredResources(requiredResourcesSet);
    }

    public int getViewResource() {
        return R.layout.brick_physics_set_velocity;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetVelocityAction(sprite, getFormulaWithBrickField(BrickField.PHYSICS_VELOCITY_X), getFormulaWithBrickField(BrickField.PHYSICS_VELOCITY_Y)));
        return null;
    }
}
