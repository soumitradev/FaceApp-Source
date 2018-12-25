package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Script;

public class StopThisScriptAction extends Action {
    private Script currentScript;

    public void setCurrentScript(Script currentScript) {
        this.currentScript = currentScript;
    }

    public boolean act(float delta) {
        if (this.actor instanceof Look) {
            ((Look) this.actor).stopThreadWithScript(this.currentScript);
        }
        return true;
    }
}
