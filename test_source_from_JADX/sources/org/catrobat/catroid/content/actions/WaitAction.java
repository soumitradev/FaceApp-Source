package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class WaitAction extends TemporalAction {
    private Formula duration;
    protected Sprite sprite;

    protected void begin() {
        try {
            super.setDuration((this.duration == null ? Float.valueOf(0.0f) : this.duration.interpretFloat(this.sprite)).floatValue());
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setDelay(Formula delay) {
        this.duration = delay;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    protected void update(float percent) {
    }
}
