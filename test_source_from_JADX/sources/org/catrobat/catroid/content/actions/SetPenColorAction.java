package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class SetPenColorAction extends TemporalAction {
    private Formula blue;
    private Formula green;
    private Formula red;
    private Sprite sprite;

    protected void update(float delta) {
        try {
            int newBlue = 0;
            int newRed = this.red == null ? 0 : this.red.interpretInteger(this.sprite).intValue();
            int newGreen = this.green == null ? 0 : this.green.interpretInteger(this.sprite).intValue();
            if (this.blue != null) {
                newBlue = this.blue.interpretInteger(this.sprite).intValue();
            }
            Color color = new Color();
            Color.argb8888ToColor(color, android.graphics.Color.argb(255, newRed, newGreen, newBlue));
            this.sprite.penConfiguration.penColor = color;
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setRed(Formula red) {
        this.red = red;
    }

    public void setGreen(Formula green) {
        this.green = green;
    }

    public void setBlue(Formula blue) {
        this.blue = blue;
    }
}
