package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;

public class GoToOtherSpritePositionAction extends TemporalAction {
    private Sprite destinationSprite;
    private Sprite sprite;

    protected void update(float percent) {
        if (this.destinationSprite == null) {
            this.destinationSprite = this.sprite;
            return;
        }
        float destinationSpriteXPosition = this.destinationSprite.look.getXInUserInterfaceDimensionUnit();
        float destinationSpriteYPosition = this.destinationSprite.look.getYInUserInterfaceDimensionUnit();
        this.sprite.look.setXInUserInterfaceDimensionUnit(destinationSpriteXPosition);
        this.sprite.look.setYInUserInterfaceDimensionUnit(destinationSpriteYPosition);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setDestinationSprite(Sprite destinationSprite) {
        this.destinationSprite = destinationSprite;
    }
}
