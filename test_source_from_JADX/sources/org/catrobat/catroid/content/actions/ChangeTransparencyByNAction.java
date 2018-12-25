package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class ChangeTransparencyByNAction extends TemporalAction {
    private Formula changeTransparency;
    private Sprite sprite;

    protected void update(float delta) {
        try {
            Float newChangeTransparency;
            if (this.changeTransparency == null) {
                newChangeTransparency = Float.valueOf(0.0f);
            } else {
                newChangeTransparency = this.changeTransparency.interpretFloat(this.sprite);
            }
            this.sprite.look.changeTransparencyInUserInterfaceDimensionUnit(newChangeTransparency.floatValue());
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setTransparency(Formula value) {
        this.changeTransparency = value;
    }
}
