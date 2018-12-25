package org.catrobat.catroid.content.actions;

import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.SetBackgroundEventId;

public class SetLookAction extends EventAction {
    protected LookData look;
    protected Sprite sprite;
    private int waitMode;

    public boolean act(float delta) {
        boolean z = true;
        if (this.firstStart) {
            if (!(this.look == null || this.sprite == null)) {
                if (this.sprite.getLookList().contains(this.look)) {
                    this.sprite.look.setLookData(this.look);
                    if (this.sprite.isBackgroundSprite()) {
                        setEvent(new EventWrapper(new SetBackgroundEventId(this.sprite, this.look), this.waitMode));
                    }
                }
            }
            return true;
        }
        if (this.sprite.isBackgroundSprite()) {
            if (!super.act(delta)) {
                z = false;
            }
        }
        return z;
    }

    public void setLookData(LookData look) {
        this.look = look;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setWaitMode(int waitMode) {
        this.waitMode = waitMode;
    }
}
