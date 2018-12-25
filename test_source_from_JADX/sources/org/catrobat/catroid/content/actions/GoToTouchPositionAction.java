package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.utils.TouchUtil;

public class GoToTouchPositionAction extends TemporalAction {
    private Sprite sprite;

    protected void update(float percent) {
        int touchIndex = TouchUtil.getLastTouchIndex();
        this.sprite.look.setXInUserInterfaceDimensionUnit(TouchUtil.getX(touchIndex));
        this.sprite.look.setYInUserInterfaceDimensionUnit(TouchUtil.getY(touchIndex));
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
