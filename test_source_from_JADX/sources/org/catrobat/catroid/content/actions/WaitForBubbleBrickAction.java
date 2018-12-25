package org.catrobat.catroid.content.actions;

import org.catrobat.catroid.stage.StageActivity;

public class WaitForBubbleBrickAction extends WaitAction {
    protected void end() {
        if (StageActivity.stageListener.getBubbleActorForSprite(this.sprite) != null) {
            StageActivity.stageListener.removeBubbleActorForSprite(this.sprite);
        }
    }
}
