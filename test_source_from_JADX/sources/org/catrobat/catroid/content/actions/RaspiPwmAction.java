package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.devices.raspberrypi.RPiSocketConnection;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class RaspiPwmAction extends TemporalAction {
    private static final String TAG = RaspiPwmAction.class.getSimpleName();
    private double frequencyInterpretation;
    private double percentageInterpretation;
    private int pinInterpretation;
    private Formula pinNumberFormula;
    private Formula pwmFrequencyFormula;
    private Formula pwmPercentageFormula;
    private Sprite sprite;

    protected void begin() {
        Double valueOf;
        try {
            this.pinInterpretation = (this.pinNumberFormula == null ? Integer.valueOf(0) : this.pinNumberFormula.interpretInteger(this.sprite)).intValue();
        } catch (InterpretationException interpretationException) {
            this.pinInterpretation = 0;
            Log.d(TAG, "Formula interpretation for this specific Brick failed. (pin)", interpretationException);
        }
        try {
            if (this.pwmFrequencyFormula == null) {
                valueOf = Double.valueOf(BrickValues.SET_COLOR_TO);
            } else {
                valueOf = this.pwmFrequencyFormula.interpretDouble(this.sprite);
            }
            this.frequencyInterpretation = valueOf.doubleValue();
        } catch (InterpretationException interpretationException2) {
            this.frequencyInterpretation = BrickValues.SET_COLOR_TO;
            Log.d(TAG, "Formula interpretation for this specific Brick failed. (frequency)", interpretationException2);
        }
        try {
            if (this.pwmPercentageFormula == null) {
                valueOf = Double.valueOf(BrickValues.SET_COLOR_TO);
            } else {
                valueOf = this.pwmPercentageFormula.interpretDouble(this.sprite);
            }
            this.percentageInterpretation = valueOf.doubleValue();
        } catch (InterpretationException interpretationException22) {
            this.percentageInterpretation = BrickValues.SET_COLOR_TO;
            Log.d(TAG, "Formula interpretation for this specific Brick failed. (percentage)", interpretationException22);
        }
    }

    protected void update(float percent) {
        RPiSocketConnection connection = RaspberryPiService.getInstance().connection;
        try {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RPi pwm pin=");
            stringBuilder.append(this.pinInterpretation);
            stringBuilder.append(", ");
            stringBuilder.append(this.percentageInterpretation);
            stringBuilder.append("%, ");
            stringBuilder.append(this.frequencyInterpretation);
            stringBuilder.append("Hz");
            Log.d(str, stringBuilder.toString());
            connection.setPWM(this.pinInterpretation, this.frequencyInterpretation, this.percentageInterpretation);
        } catch (Exception e) {
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("RPi: exception during setPwm: ");
            stringBuilder2.append(e);
            Log.e(str2, stringBuilder2.toString());
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPinNumberFormula(Formula pinNumberFormula) {
        this.pinNumberFormula = pinNumberFormula;
    }

    public void setPwmFrequencyFormula(Formula pwmFrequencyFormula) {
        this.pwmFrequencyFormula = pwmFrequencyFormula;
    }

    public void setPwmPercentageFormula(Formula pwmPercentageFormula) {
        this.pwmPercentageFormula = pwmPercentageFormula;
    }
}
