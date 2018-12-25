package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class SetBrightnessAction extends TemporalAction {
    private Formula brightness;
    private Sprite sprite;

    protected void update(float percent) {
        try {
            this.sprite.look.setBrightnessInUserInterfaceDimensionUnit((this.brightness == null ? Float.valueOf(0.0f) : this.brightness.interpretFloat(this.sprite)).floatValue());
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setBrightness(Formula brightness) {
        this.brightness = brightness;
    }
}
