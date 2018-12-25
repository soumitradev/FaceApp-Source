package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.stage.StageActivity;

public class SceneStartAction extends TemporalAction {
    private String sceneName;

    protected void update(float percent) {
        if (this.sceneName != null) {
            StageActivity.stageListener.startScene(this.sceneName);
        }
    }

    public void reset() {
        super.reset();
        this.sceneName = null;
    }

    public void setScene(String sceneName) {
        this.sceneName = sceneName;
    }
}
