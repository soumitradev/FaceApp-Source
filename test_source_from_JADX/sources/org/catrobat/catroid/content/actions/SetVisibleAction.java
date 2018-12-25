package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.ShowBubbleActor;
import org.catrobat.catroid.stage.StageActivity;

public class SetVisibleAction extends TemporalAction {
    private Sprite sprite;
    private boolean visible;

    protected void update(float delta) {
        this.sprite.look.setLookVisible(this.visible);
        if (StageActivity.stageListener != null) {
            ShowBubbleActor actor = StageActivity.stageListener.getBubbleActorForSprite(this.sprite);
            if (actor != null) {
                actor.setVisible(this.visible);
            }
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
