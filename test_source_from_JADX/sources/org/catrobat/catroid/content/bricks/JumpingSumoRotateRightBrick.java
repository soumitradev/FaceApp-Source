package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class JumpingSumoRotateRightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public JumpingSumoRotateRightBrick() {
        addAllowedBrickField(BrickField.JUMPING_SUMO_ROTATE, R.id.brick_jumping_sumo_change_right_variable_edit_text);
    }

    public JumpingSumoRotateRightBrick(double degree) {
        this(new Formula(Double.valueOf(degree)));
    }

    public JumpingSumoRotateRightBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.JUMPING_SUMO_ROTATE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_jumping_sumo_rotate_right;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(23));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createJumpingSumoRotateRightAction(sprite, getFormulaWithBrickField(BrickField.JUMPING_SUMO_ROTATE)));
        return null;
    }
}
