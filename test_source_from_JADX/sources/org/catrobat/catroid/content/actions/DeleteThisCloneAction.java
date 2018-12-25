package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;

public class DeleteThisCloneAction extends TemporalAction {
    private Sprite sprite;

    protected void update(float percent) {
        if (this.sprite != null) {
            StageActivity.stageListener.removeClonedSpriteFromStage(this.sprite);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
