package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenClonedBrick;
import org.catrobat.catroid.content.eventids.EventId;

public class WhenClonedScript extends Script {
    private static final long serialVersionUID = 1;

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenClonedBrick(this);
        }
        return this.scriptBrick;
    }

    public EventId createEventId(Sprite sprite) {
        return new EventId(4);
    }
}
