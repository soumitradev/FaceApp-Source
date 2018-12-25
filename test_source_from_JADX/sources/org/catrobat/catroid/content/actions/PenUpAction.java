package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;

public class PenUpAction extends TemporalAction {
    private Sprite sprite;

    protected void update(float delta) {
        this.sprite.penConfiguration.penDown = false;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
