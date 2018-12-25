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

public class LegoNxtPlayToneBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public LegoNxtPlayToneBrick() {
        addAllowedBrickField(BrickField.LEGO_NXT_FREQUENCY, R.id.nxt_tone_freq_edit_text);
        addAllowedBrickField(BrickField.LEGO_NXT_DURATION_IN_SECONDS, R.id.nxt_tone_duration_edit_text);
    }

    public LegoNxtPlayToneBrick(double frequencyValue, double durationValue) {
        this(new Formula(Double.valueOf(frequencyValue)), new Formula(Double.valueOf(durationValue)));
    }

    public LegoNxtPlayToneBrick(Formula frequencyFormula, Formula durationFormula) {
        this();
        setFormulaWithBrickField(BrickField.LEGO_NXT_FREQUENCY, frequencyFormula);
        setFormulaWithBrickField(BrickField.LEGO_NXT_DURATION_IN_SECONDS, durationFormula);
    }

    public int getViewResource() {
        return R.layout.brick_nxt_play_tone;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        setSecondsLabel(prototypeView, BrickField.LEGO_NXT_DURATION_IN_SECONDS);
        return prototypeView;
    }

    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, BrickField.LEGO_NXT_DURATION_IN_SECONDS);
        return this.view;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(2));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoNxtPlayToneAction(sprite, getFormulaWithBrickField(BrickField.LEGO_NXT_FREQUENCY), getFormulaWithBrickField(BrickField.LEGO_NXT_DURATION_IN_SECONDS)));
        return null;
    }
}
