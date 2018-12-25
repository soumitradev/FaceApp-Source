package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;

public class PointToAction extends TemporalAction {
    private Sprite pointedSprite;
    private Sprite sprite;

    protected void update(float percent) {
        if (this.pointedSprite != null) {
            if (ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList().contains(this.pointedSprite)) {
                double rotationDegrees;
                float spriteXPosition = this.sprite.look.getXInUserInterfaceDimensionUnit();
                float spriteYPosition = this.sprite.look.getYInUserInterfaceDimensionUnit();
                float pointedSpriteXPosition = this.pointedSprite.look.getXInUserInterfaceDimensionUnit();
                float pointedSpriteYPosition = this.pointedSprite.look.getYInUserInterfaceDimensionUnit();
                if (spriteXPosition == pointedSpriteXPosition && spriteYPosition == pointedSpriteYPosition) {
                    rotationDegrees = 90.0d;
                } else if (spriteXPosition == pointedSpriteXPosition) {
                    if (spriteYPosition < pointedSpriteYPosition) {
                        rotationDegrees = BrickValues.SET_COLOR_TO;
                    } else {
                        rotationDegrees = 180.0d;
                    }
                } else if (spriteYPosition != pointedSpriteYPosition) {
                    rotationDegrees = 90.0d - Math.toDegrees(Math.atan2((double) (pointedSpriteYPosition - spriteYPosition), (double) (pointedSpriteXPosition - spriteXPosition)));
                    this.sprite.look.setDirectionInUserInterfaceDimensionUnit((float) rotationDegrees);
                } else if (spriteXPosition < pointedSpriteXPosition) {
                    rotationDegrees = 90.0d;
                } else {
                    rotationDegrees = -90.0d;
                }
                this.sprite.look.setDirectionInUserInterfaceDimensionUnit((float) rotationDegrees);
            }
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPointedSprite(Sprite pointedSprite) {
        this.pointedSprite = pointedSprite;
    }
}
