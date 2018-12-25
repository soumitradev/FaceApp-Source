package org.catrobat.catroid.content;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.eventids.CollisionEventId;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.physics.content.bricks.CollisionReceiverBrick;

public class CollisionScript extends Script {
    private static final long serialVersionUID = 1;
    private transient Sprite spriteToCollideWith;
    private String spriteToCollideWithName;

    public CollisionScript(String spriteToCollideWithName) {
        this.spriteToCollideWithName = spriteToCollideWithName;
    }

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new CollisionReceiverBrick(this);
        }
        return this.scriptBrick;
    }

    public String getSpriteToCollideWithName() {
        return this.spriteToCollideWithName;
    }

    public void setSpriteToCollideWithName(String spriteToCollideWithName) {
        this.spriteToCollideWithName = spriteToCollideWithName;
        updateSpriteToCollideWith(ProjectManager.getInstance().getCurrentlyEditedScene());
    }

    public Sprite getSpriteToCollideWith() {
        updateSpriteToCollideWith(ProjectManager.getInstance().getCurrentlyEditedScene());
        return this.spriteToCollideWith;
    }

    public void updateSpriteToCollideWith(Scene scene) {
        if (this.spriteToCollideWithName == null) {
            this.spriteToCollideWith = null;
            return;
        }
        this.spriteToCollideWith = scene.getSprite(this.spriteToCollideWithName);
        if (this.spriteToCollideWith == null) {
            this.spriteToCollideWithName = null;
        }
    }

    public EventId createEventId(Sprite sprite) {
        return new CollisionEventId(sprite, this.spriteToCollideWith);
    }
}
