package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.SoundManager;

public class PlaySoundAction extends TemporalAction {
    private SoundInfo sound;
    private Sprite sprite;

    protected void update(float percent) {
        if (this.sound != null && this.sprite.getSoundList().contains(this.sound) && this.sound.getFile() != null) {
            SoundManager.getInstance().playSoundFile(this.sound.getFile().getAbsolutePath());
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSound(SoundInfo sound) {
        this.sound = sound;
    }
}
