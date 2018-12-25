package org.catrobat.catroid.content.eventids;

import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Sprite;

public class SetBackgroundEventId extends EventId {
    public final LookData lookData;
    public final Sprite sprite;

    public SetBackgroundEventId(Sprite sprite, LookData lookData) {
        this.sprite = sprite;
        this.lookData = lookData;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetBackgroundEventId) || !super.equals(o)) {
            return false;
        }
        SetBackgroundEventId that = (SetBackgroundEventId) o;
        if (!this.sprite.equals(that.sprite) || !this.lookData.equals(that.lookData)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((super.hashCode() * 31) + this.sprite.hashCode()) * 31) + this.lookData.hashCode();
    }
}
