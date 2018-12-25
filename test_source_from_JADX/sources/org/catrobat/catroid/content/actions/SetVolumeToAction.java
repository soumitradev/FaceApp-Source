package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.io.SoundManager;

public class SetVolumeToAction extends TemporalAction {
    private Sprite sprite;
    private Formula volume;

    protected void update(float percent) {
        try {
            SoundManager.getInstance().setVolume((this.volume == null ? Float.valueOf(0.0f) : this.volume.interpretFloat(this.sprite)).floatValue());
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setVolume(Formula volume) {
        this.volume = volume;
    }
}
