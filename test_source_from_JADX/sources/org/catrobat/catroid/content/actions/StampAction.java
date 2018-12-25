package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;

public class StampAction extends TemporalAction {
    private Sprite sprite;

    protected void update(float delta) {
        this.sprite.penConfiguration.stamp = true;
        StageActivity.stageListener.getPenActor().stampToFrameBuffer();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
