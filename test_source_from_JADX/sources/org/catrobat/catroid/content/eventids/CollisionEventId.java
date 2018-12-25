package org.catrobat.catroid.content.eventids;

import com.google.common.base.Objects;
import org.catrobat.catroid.content.Sprite;

public class CollisionEventId extends EventId {
    public final Sprite sprite1;
    public final Sprite sprite2;

    public CollisionEventId(Sprite sprite1, Sprite sprite2) {
        this.sprite1 = sprite1;
        this.sprite2 = sprite2;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollisionEventId)) {
            return false;
        }
        CollisionEventId collisionEventId = (CollisionEventId) o;
        if (Objects.equal(this.sprite1, collisionEventId.sprite1) || Objects.equal(this.sprite1, collisionEventId.sprite2)) {
            if (!Objects.equal(this.sprite2, collisionEventId.sprite1)) {
                if (Objects.equal(this.sprite2, collisionEventId.sprite2)) {
                }
            }
            return z;
        }
        z = false;
        return z;
    }

    public int hashCode() {
        int i = 0;
        int result = this.sprite1 != null ? this.sprite1.hashCode() : 0;
        if (this.sprite2 != null) {
            i = this.sprite2.hashCode();
        }
        return result + i;
    }
}
