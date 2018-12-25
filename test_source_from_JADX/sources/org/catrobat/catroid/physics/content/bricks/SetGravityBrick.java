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

public class SetGravityBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetGravityBrick() {
        addAllowedBrickField(BrickField.PHYSICS_GRAVITY_X, R.id.brick_set_gravity_edit_text_x);
        addAllowedBrickField(BrickField.PHYSICS_GRAVITY_Y, R.id.brick_set_gravity_edit_text_y);
    }

    public SetGravityBrick(Vector2 gravity) {
        this(new Formula(Float.valueOf(gravity.f16x)), new Formula(Float.valueOf(gravity.f17y)));
    }

    public SetGravityBrick(Formula gravityX, Formula gravityY) {
        this();
        setFormulaWithBrickField(BrickField.PHYSICS_GRAVITY_X, gravityX);
        setFormulaWithBrickField(BrickField.PHYSICS_GRAVITY_Y, gravityY);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(3));
        super.addRequiredResources(requiredResourcesSet);
    }

    public int getViewResource() {
        return R.layout.brick_physics_set_gravity;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetGravityAction(sprite, getFormulaWithBrickField(BrickField.PHYSICS_GRAVITY_X), getFormulaWithBrickField(BrickField.PHYSICS_GRAVITY_Y)));
        return null;
    }
}
