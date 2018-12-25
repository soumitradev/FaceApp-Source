package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.Sprite;

public class GoToRandomPositionAction extends TemporalAction {
    private float randomXPosition;
    private float randomYPosition;
    private Sprite sprite;

    protected void update(float percent) {
        this.randomXPosition = (((float) Math.random()) * ((float) (ScreenValues.SCREEN_WIDTH + 1))) - ((float) (ScreenValues.SCREEN_WIDTH / 2));
        this.randomYPosition = (((float) Math.random()) * ((float) (ScreenValues.SCREEN_HEIGHT + 1))) - ((float) (ScreenValues.SCREEN_HEIGHT / 2));
        this.sprite.look.setXInUserInterfaceDimensionUnit(this.randomXPosition);
        this.sprite.look.setYInUserInterfaceDimensionUnit(this.randomYPosition);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getRandomXPosition() {
        return this.randomXPosition;
    }

    public float getRandomYPosition() {
        return this.randomYPosition;
    }
}
