package org.catrobat.catroid.content.eventids;

import com.google.common.base.Objects;

public class RaspiEventId extends EventId {
    private final String eventValue;
    private final String pin;

    public RaspiEventId(String pin, String eventValue) {
        this.pin = pin;
        this.eventValue = eventValue;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (!(o instanceof RaspiEventId) || !super.equals(o)) {
            return false;
        }
        RaspiEventId that = (RaspiEventId) o;
        if (!Objects.equal(this.pin, that.pin) || !Objects.equal(this.eventValue, that.eventValue)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i = 0;
        int result = (this.pin != null ? this.pin.hashCode() : 0) * 31;
        if (this.eventValue != null) {
            i = this.eventValue.hashCode();
        }
        return result + i;
    }
}
