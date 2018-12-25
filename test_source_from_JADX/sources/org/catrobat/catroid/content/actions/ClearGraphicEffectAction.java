package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;

public class ClearGraphicEffectAction extends TemporalAction {
    private Sprite sprite;

    protected void update(float percent) {
        this.sprite.look.setBrightnessInUserInterfaceDimensionUnit(100.0f);
        this.sprite.look.setTransparencyInUserInterfaceDimensionUnit(0.0f);
        this.sprite.look.setColorInUserInterfaceDimensionUnit(0.0f);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
