package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenGamepadButtonBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.GamepadEventId;

public class WhenGamepadButtonScript extends Script {
    private static final long serialVersionUID = 1;
    private String action;

    public WhenGamepadButtonScript(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenGamepadButtonBrick(this);
        }
        return this.scriptBrick;
    }

    public void addRequiredResources(ResourcesSet resourcesSet) {
        resourcesSet.add(Integer.valueOf(22));
        super.addRequiredResources(resourcesSet);
    }

    public EventId createEventId(Sprite sprite) {
        return new GamepadEventId(this.action);
    }
}
