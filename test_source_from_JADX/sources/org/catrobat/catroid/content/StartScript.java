package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenStartedBrick;
import org.catrobat.catroid.content.eventids.EventId;

public class StartScript extends Script {
    private static final long serialVersionUID = 1;

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenStartedBrick(this);
        }
        return this.scriptBrick;
    }

    public EventId createEventId(Sprite sprite) {
        return new EventId(3);
    }
}
