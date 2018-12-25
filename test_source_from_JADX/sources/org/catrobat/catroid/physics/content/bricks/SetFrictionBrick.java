package org.catrobat.catroid.physics.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetFrictionBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetFrictionBrick() {
        addAllowedBrickField(BrickField.PHYSICS_FRICTION, R.id.brick_set_friction_edit_text);
    }

    public SetFrictionBrick(double friction) {
        this(new Formula(Double.valueOf(friction)));
    }

    public SetFrictionBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.PHYSICS_FRICTION, formula);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(3));
        super.addRequiredResources(requiredResourcesSet);
    }

    public int getViewResource() {
        return R.layout.brick_physics_set_friction;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetFrictionAction(sprite, getFormulaWithBrickField(BrickField.PHYSICS_FRICTION)));
        return null;
    }
}
