package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.utils.FlashUtil;

public class FlashAction extends TemporalAction {
    private static final int OFF = 0;
    private static final int ON = 1;
    private int turnFlash = 0;

    protected void update(float percent) {
        if (this.turnFlash == 1) {
            FlashUtil.flashOn();
        } else {
            FlashUtil.flashOff();
        }
    }

    public void turnFlashOn() {
        this.turnFlash = 1;
    }

    public void turnFlashOff() {
        this.turnFlash = 0;
    }
}
