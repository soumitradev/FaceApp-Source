package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.utils.VibratorUtil;

public class VibrateAction extends TemporalAction {
    private Formula duration;
    private Sprite sprite;

    protected void update(float percent) {
        try {
            VibratorUtil.setTimeToVibrate(Double.valueOf(this.duration == null ? Double.valueOf(BrickValues.SET_COLOR_TO).doubleValue() : (double) this.duration.interpretFloat(this.sprite).floatValue()).doubleValue() * 1000.0d);
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setDuration(Formula duration) {
        this.duration = duration;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
