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

public class LegoEv3PlayToneBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public LegoEv3PlayToneBrick() {
        addAllowedBrickField(BrickField.LEGO_EV3_FREQUENCY, R.id.brick_ev3_tone_freq_edit_text);
        addAllowedBrickField(BrickField.LEGO_EV3_DURATION_IN_SECONDS, R.id.brick_ev3_tone_duration_edit_text);
        addAllowedBrickField(BrickField.LEGO_EV3_VOLUME, R.id.brick_ev3_tone_volume_edit_text);
    }

    public LegoEv3PlayToneBrick(double frequencyValue, double durationValue, double volumeValue) {
        this(new Formula(Double.valueOf(frequencyValue)), new Formula(Double.valueOf(durationValue)), new Formula(Double.valueOf(volumeValue)));
    }

    public LegoEv3PlayToneBrick(Formula frequencyFormula, Formula durationFormula, Formula volumeFormula) {
        this();
        setFormulaWithBrickField(BrickField.LEGO_EV3_FREQUENCY, frequencyFormula);
        setFormulaWithBrickField(BrickField.LEGO_EV3_DURATION_IN_SECONDS, durationFormula);
        setFormulaWithBrickField(BrickField.LEGO_EV3_VOLUME, volumeFormula);
    }

    public int getViewResource() {
        return R.layout.brick_ev3_play_tone;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(20));
        super.addRequiredResources(requiredResourcesSet);
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        setSecondsLabel(prototypeView, BrickField.LEGO_EV3_DURATION_IN_SECONDS);
        return prototypeView;
    }

    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, BrickField.LEGO_EV3_DURATION_IN_SECONDS);
        return this.view;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoEv3PlayToneAction(sprite, getFormulaWithBrickField(BrickField.LEGO_EV3_FREQUENCY), getFormulaWithBrickField(BrickField.LEGO_EV3_DURATION_IN_SECONDS), getFormulaWithBrickField(BrickField.LEGO_EV3_VOLUME)));
        return null;
    }
}
