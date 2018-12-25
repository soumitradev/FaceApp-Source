package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenBrick;
import org.catrobat.catroid.content.eventids.EventId;

public class WhenScript extends Script {
    private static final long serialVersionUID = 1;

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenBrick(this);
        }
        return this.scriptBrick;
    }

    public EventId createEventId(Sprite sprite) {
        return new EventId(1);
    }
}
