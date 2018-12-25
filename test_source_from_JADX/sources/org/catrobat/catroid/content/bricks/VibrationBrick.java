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

public class VibrationBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public VibrationBrick() {
        addAllowedBrickField(BrickField.VIBRATE_DURATION_IN_SECONDS, R.id.brick_vibration_edit_text);
    }

    public VibrationBrick(double vibrateDurationInSeconds) {
        this(new Formula(Double.valueOf(vibrateDurationInSeconds)));
    }

    public VibrationBrick(Formula vibrateDurationInSecondsFormula) {
        this();
        setFormulaWithBrickField(BrickField.VIBRATE_DURATION_IN_SECONDS, vibrateDurationInSecondsFormula);
    }

    public int getViewResource() {
        return R.layout.brick_vibration;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(9));
        super.addRequiredResources(requiredResourcesSet);
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        setSecondsLabel(prototypeView, BrickField.VIBRATE_DURATION_IN_SECONDS);
        return prototypeView;
    }

    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, BrickField.VIBRATE_DURATION_IN_SECONDS);
        return this.view;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVibrateAction(sprite, getFormulaWithBrickField(BrickField.VIBRATE_DURATION_IN_SECONDS)));
        return null;
    }
}
