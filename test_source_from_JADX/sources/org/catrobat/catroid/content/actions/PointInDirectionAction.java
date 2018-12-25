package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class PointInDirectionAction extends TemporalAction {
    private Formula degrees;
    private Sprite sprite;

    protected void update(float percent) {
        try {
            this.sprite.look.setDirectionInUserInterfaceDimensionUnit((this.degrees == null ? Float.valueOf(0.0f) : this.degrees.interpretFloat(this.sprite)).floatValue());
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setDegreesInUserInterfaceDimensionUnit(Formula degrees) {
        this.degrees = degrees;
    }
}
