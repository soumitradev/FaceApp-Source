package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class TurnLeftAction extends TemporalAction {
    private Formula degrees;
    private Sprite sprite;

    protected void update(float percent) {
        try {
            this.sprite.look.changeDirectionInUserInterfaceDimensionUnit(Float.valueOf(this.degrees == null ? Float.valueOf(0.0f).floatValue() : -this.degrees.interpretFloat(this.sprite).floatValue()).floatValue());
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setDegrees(Formula degrees) {
        this.degrees = degrees;
    }
}
