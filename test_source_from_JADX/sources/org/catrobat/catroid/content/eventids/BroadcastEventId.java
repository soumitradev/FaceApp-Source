package org.catrobat.catroid.content.eventids;

public class BroadcastEventId extends EventId {
    public final String message;

    public BroadcastEventId(String message) {
        this.message = message;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BroadcastEventId)) {
            return false;
        }
        return this.message.equals(((BroadcastEventId) o).message);
    }

    public int hashCode() {
        return this.message != null ? this.message.hashCode() : 0;
    }
}
