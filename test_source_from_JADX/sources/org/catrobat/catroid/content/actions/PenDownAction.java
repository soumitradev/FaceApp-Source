package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;

public class PenDownAction extends TemporalAction {
    private Sprite sprite;

    protected void update(float delta) {
        this.sprite.penConfiguration.penDown = true;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
