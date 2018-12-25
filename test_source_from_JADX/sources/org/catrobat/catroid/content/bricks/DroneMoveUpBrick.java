package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class DroneMoveUpBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public DroneMoveUpBrick() {
        addAllowedBrickField(BrickField.DRONE_TIME_TO_FLY_IN_SECONDS, R.id.brick_drone_move_up_edit_text_second);
        addAllowedBrickField(BrickField.DRONE_POWER_IN_PERCENT, R.id.brick_drone_move_up_edit_text_power);
    }

    public DroneMoveUpBrick(int durationInMilliseconds, int powerInPercent) {
        this(new Formula(Double.valueOf(((double) durationInMilliseconds) / 1000.0d)), new Formula(Integer.valueOf(powerInPercent)));
    }

    public DroneMoveUpBrick(Formula durationInSeconds, Formula powerInPercent) {
        this();
        setFormulaWithBrickField(BrickField.DRONE_TIME_TO_FLY_IN_SECONDS, durationInSeconds);
        setFormulaWithBrickField(BrickField.DRONE_POWER_IN_PERCENT, powerInPercent);
    }

    public int getViewResource() {
        return R.layout.brick_drone_move_up;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        setSecondsLabel(prototypeView, BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        return prototypeView;
    }

    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        return this.view;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(5));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDroneMoveUpAction(sprite, getFormulaWithBrickField(BrickField.DRONE_TIME_TO_FLY_IN_SECONDS), getFormulaWithBrickField(BrickField.DRONE_POWER_IN_PERCENT)));
        return null;
    }
}
