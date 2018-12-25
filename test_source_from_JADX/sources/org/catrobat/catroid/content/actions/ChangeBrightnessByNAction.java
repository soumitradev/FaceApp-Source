package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class ChangeBrightnessByNAction extends TemporalAction {
    private Formula changeBrightness;
    private Sprite sprite;

    protected void update(float percent) {
        try {
            Float newChangeBrightness;
            if (this.changeBrightness == null) {
                newChangeBrightness = Float.valueOf(0.0f);
            } else {
                newChangeBrightness = this.changeBrightness.interpretFloat(this.sprite);
            }
            this.sprite.look.changeBrightnessInUserInterfaceDimensionUnit(newChangeBrightness.floatValue());
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setBrightness(Formula changeBrightness) {
        this.changeBrightness = changeBrightness;
    }
}
