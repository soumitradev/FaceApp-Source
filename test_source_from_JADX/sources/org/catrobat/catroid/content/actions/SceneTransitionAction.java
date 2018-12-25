package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.stage.StageActivity;

public class SceneTransitionAction extends TemporalAction {
    private String sceneName;

    protected void update(float percent) {
        if (this.sceneName != null && !ProjectManager.getInstance().getCurrentlyPlayingScene().getName().equals(this.sceneName)) {
            StageActivity.stageListener.transitionToScene(this.sceneName);
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
