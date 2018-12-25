package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class SetPenSizeAction extends TemporalAction {
    private Formula penSize;
    private Sprite sprite;

    protected void update(float delta) {
        try {
            this.sprite.penConfiguration.penSize = (double) (this.penSize == null ? Float.valueOf(0.0f) : this.penSize.interpretFloat(this.sprite)).floatValue();
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPenSize(Formula size) {
        this.penSize = size;
    }
}
