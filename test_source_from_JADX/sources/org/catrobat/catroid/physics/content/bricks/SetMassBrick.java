package org.catrobat.catroid.physics.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetMassBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetMassBrick() {
        addAllowedBrickField(BrickField.PHYSICS_MASS, R.id.brick_set_mass_edit_text);
    }

    public SetMassBrick(double mass) {
        this(new Formula(Double.valueOf(mass)));
    }

    public SetMassBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.PHYSICS_MASS, formula);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(3));
        super.addRequiredResources(requiredResourcesSet);
    }

    public int getViewResource() {
        return R.layout.brick_physics_set_mass;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetMassAction(sprite, getFormulaWithBrickField(BrickField.PHYSICS_MASS)));
        return null;
    }
}
