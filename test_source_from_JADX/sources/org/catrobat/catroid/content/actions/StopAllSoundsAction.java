package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.io.SoundManager;

public class StopAllSoundsAction extends TemporalAction {
    protected void update(float percent) {
        SoundManager.getInstance().stopAllSounds();
    }
}
