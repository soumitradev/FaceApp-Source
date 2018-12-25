package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;

public class SetRotationStyleAction extends TemporalAction {
    private int mode;
    private Sprite sprite;

    protected void update(float delta) {
        this.sprite.look.setRotationMode(this.mode);
        if (this.mode != 0 && this.sprite.look.isFlipped()) {
            this.sprite.look.getLookData().getTextureRegion().flip(true, false);
        }
        boolean orientedLeft = this.sprite.look.getDirectionInUserInterfaceDimensionUnit() < 0.0f;
        if (this.mode == 0 && orientedLeft) {
            this.sprite.look.getLookData().getTextureRegion().flip(true, false);
        }
        this.sprite.look.setDirectionInUserInterfaceDimensionUnit(this.sprite.look.getDirectionInUserInterfaceDimensionUnit());
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setRotationStyle(int mode) {
        this.mode = mode;
    }
}
