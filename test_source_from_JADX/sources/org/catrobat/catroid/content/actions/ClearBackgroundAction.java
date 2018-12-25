package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.stage.StageActivity;

public class ClearBackgroundAction extends TemporalAction {
    protected void update(float delta) {
        StageActivity.stageListener.clearBackground();
    }
}
