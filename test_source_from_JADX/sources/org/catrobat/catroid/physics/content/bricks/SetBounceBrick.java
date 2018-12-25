package org.catrobat.catroid.physics.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetBounceBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetBounceBrick() {
        addAllowedBrickField(BrickField.PHYSICS_BOUNCE_FACTOR, R.id.brick_set_bounce_factor_edit_text);
    }

    public SetBounceBrick(double bounceFactor) {
        this(new Formula(Double.valueOf(bounceFactor)));
    }

    public SetBounceBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.PHYSICS_BOUNCE_FACTOR, formula);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(3));
        super.addRequiredResources(requiredResourcesSet);
    }

    public int getViewResource() {
        return R.layout.brick_physics_set_bounce_factor;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBounceFactorAction(sprite, getFormulaWithBrickField(BrickField.PHYSICS_BOUNCE_FACTOR)));
        return null;
    }
}
