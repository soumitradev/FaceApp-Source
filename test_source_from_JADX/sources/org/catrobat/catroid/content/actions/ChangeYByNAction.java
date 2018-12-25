package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class ChangeYByNAction extends TemporalAction {
    private Sprite sprite;
    private Formula yMovement;

    protected void update(float arg0) {
        try {
            this.sprite.look.changeYInUserInterfaceDimensionUnit((this.yMovement == null ? Float.valueOf(0.0f) : this.yMovement.interpretFloat(this.sprite)).floatValue());
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setyMovement(Formula yMovement) {
        this.yMovement = yMovement;
    }
}
