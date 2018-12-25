package org.catrobat.catroid.content.eventids;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class EventId {
    public static final int ANY_NFC = 5;
    public static final int OTHER = 0;
    public static final int START = 3;
    public static final int START_AS_CLONE = 4;
    public static final int TAP = 1;
    public static final int TAP_BACKGROUND = 2;
    private final int type;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    public EventId(int type) {
        this.type = type;
    }

    protected EventId() {
        this.type = 0;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventId)) {
            return false;
        }
        if (this.type != ((EventId) o).type) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.type;
    }
}
