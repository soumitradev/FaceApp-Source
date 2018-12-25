package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class JumpingSumoRotateLeftBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public JumpingSumoRotateLeftBrick() {
        addAllowedBrickField(BrickField.JUMPING_SUMO_ROTATE, R.id.brick_jumping_sumo_change_left_variable_edit_text);
    }

    public JumpingSumoRotateLeftBrick(double degree) {
        this(new Formula(Double.valueOf(degree)));
    }

    public JumpingSumoRotateLeftBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.JUMPING_SUMO_ROTATE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_jumping_sumo_rotate_left;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(23));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createJumpingSumoRotateLeftAction(sprite, getFormulaWithBrickField(BrickField.JUMPING_SUMO_ROTATE)));
        return null;
    }
}
