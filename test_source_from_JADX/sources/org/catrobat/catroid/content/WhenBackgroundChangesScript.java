package org.catrobat.catroid.content;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenBackgroundChangesBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.SetBackgroundEventId;

public class WhenBackgroundChangesScript extends Script {
    private static final long serialVersionUID = 1;
    private LookData look;

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenBackgroundChangesBrick(this);
        }
        return this.scriptBrick;
    }

    public LookData getLook() {
        return this.look;
    }

    public void setLook(LookData look) {
        this.look = look;
    }

    public EventId createEventId(Sprite sprite) {
        return new SetBackgroundEventId(ProjectManager.getInstance().getCurrentlyPlayingScene().getBackgroundSprite(), this.look);
    }
}
