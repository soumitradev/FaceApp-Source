package org.catrobat.catroid.content.actions.conditional;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;

public class IfOnEdgeBounceAction extends TemporalAction {
    private Sprite sprite;

    protected void update(float percent) {
        float width = this.sprite.look.getWidthInUserInterfaceDimensionUnit();
        float height = this.sprite.look.getHeightInUserInterfaceDimensionUnit();
        float xPosition = this.sprite.look.getXInUserInterfaceDimensionUnit();
        float yPosition = this.sprite.look.getYInUserInterfaceDimensionUnit();
        int halfVirtualScreenWidth = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth / 2;
        int halfVirtualScreenHeight = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenHeight / 2;
        float newDirection = this.sprite.look.getDirectionInUserInterfaceDimensionUnit();
        if (xPosition < ((float) (-halfVirtualScreenWidth)) + (width / 2.0f)) {
            if (isLookingLeft(newDirection)) {
                newDirection = -newDirection;
            }
            xPosition = ((float) (-halfVirtualScreenWidth)) + (width / 2.0f);
        } else if (xPosition > ((float) halfVirtualScreenWidth) - (width / 2.0f)) {
            if (isLookingRight(newDirection)) {
                newDirection = -newDirection;
            }
            xPosition = ((float) halfVirtualScreenWidth) - (width / 2.0f);
        }
        if (yPosition < ((float) (-halfVirtualScreenHeight)) + (height / 2.0f)) {
            if (isLookingDown(newDirection)) {
                newDirection = 180.0f - newDirection;
            }
            yPosition = ((float) (-halfVirtualScreenHeight)) + (height / 2.0f);
        } else if (yPosition > ((float) halfVirtualScreenHeight) - (height / 2.0f)) {
            if (isLookingUp(newDirection)) {
                newDirection = 180.0f - newDirection;
            }
            yPosition = ((float) halfVirtualScreenHeight) - (height / 2.0f);
        }
        this.sprite.look.setDirectionInUserInterfaceDimensionUnit(newDirection);
        this.sprite.look.setPositionInUserInterfaceDimensionUnit(xPosition, yPosition);
    }

    private boolean isLookingUp(float direction) {
        return direction > -90.0f && direction < 90.0f;
    }

    private boolean isLookingDown(float direction) {
        if (direction <= 90.0f) {
            if (direction >= -90.0f) {
                return false;
            }
        }
        return true;
    }

    private boolean isLookingLeft(float direction) {
        return direction > -180.0f && direction < 0.0f;
    }

    private boolean isLookingRight(float direction) {
        return direction > 0.0f && direction < 180.0f;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
