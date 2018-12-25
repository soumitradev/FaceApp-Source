package org.catrobat.catroid.physics;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.CollisionEventId;

public class PhysicsCollisionBroadcast {
    private int contactCounter = 0;
    private Sprite sprite1;
    private Sprite sprite2;

    PhysicsCollisionBroadcast(Sprite sprite1, Sprite sprite2) {
        this.sprite1 = sprite1;
        this.sprite2 = sprite2;
    }

    void increaseContactCounter() {
        this.contactCounter++;
    }

    void decreaseContactCounter() {
        if (this.contactCounter > 0) {
            this.contactCounter--;
        }
    }

    public int getContactCounter() {
        return this.contactCounter;
    }

    void sendBroadcast() {
        fireEvent(this.sprite1, this.sprite2);
        fireEvent(this.sprite1, null);
        fireEvent(this.sprite2, null);
    }

    static boolean fireEvent(Sprite sprite1, Sprite sprite2) {
        if (sprite1 == null && sprite2 == null) {
            return false;
        }
        ProjectManager.getInstance().getCurrentProject().fireToAllSprites(new EventWrapper(new CollisionEventId(sprite1, sprite2), 1));
        return true;
    }

    public String toString() {
        return String.format("PhysicsCollisionBroadcast:\n     sprite1: %s\n     sprite2: %s\n     contactCounter: %s\n", new Object[]{this.sprite1, this.sprite2, String.valueOf(this.contactCounter)});
    }
}
