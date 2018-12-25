package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Script;

public class StopOtherScriptsAction extends Action {
    private Script currentScript;

    public boolean act(float delta) {
        if (this.actor instanceof Look) {
            if (this.actor.getActions() != null) {
                Look look = this.actor;
                look.stopThreads(getOtherThreads(look));
                return true;
            }
        }
        return true;
    }

    private Array<Action> getOtherThreads(Look look) {
        Array<Action> otherThreads = new Array(look.getActions());
        Iterator<Action> it = otherThreads.iterator();
        while (it.hasNext()) {
            Action action = (Action) it.next();
            if ((action instanceof ScriptSequenceAction) && ((ScriptSequenceAction) action).getScript() == this.currentScript) {
                it.remove();
            }
        }
        return otherThreads;
    }

    public void setCurrentScript(Script currentScript) {
        this.currentScript = currentScript;
    }
}
