package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.Iterator;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.stage.StageActivity;

public class StopAllScriptsAction extends Action {
    public boolean act(float delta) {
        Iterator it = StageActivity.stageListener.getStage().getActors().iterator();
        while (it.hasNext()) {
            Actor actor = (Actor) it.next();
            if (actor instanceof Look) {
                Look look = (Look) actor;
                look.stopThreads(look.getActions());
            }
        }
        return true;
    }
}
