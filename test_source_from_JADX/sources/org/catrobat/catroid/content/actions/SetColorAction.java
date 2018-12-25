package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class SetColorAction extends TemporalAction {
    private static final String TAG = SetColorAction.class.getSimpleName();
    private Formula color;
    private Sprite sprite;

    protected void update(float delta) {
        try {
            this.sprite.look.setColorInUserInterfaceDimensionUnit(this.color == null ? 0.0f : this.color.interpretFloat(this.sprite).floatValue());
        } catch (InterpretationException interpretationException) {
            Log.d(TAG, "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setColor(Formula color) {
        this.color = color;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
