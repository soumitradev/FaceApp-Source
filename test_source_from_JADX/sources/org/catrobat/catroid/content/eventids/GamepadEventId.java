package org.catrobat.catroid.content.eventids;

import com.google.common.base.Objects;

public class GamepadEventId extends EventId {
    final String buttonPressed;

    public GamepadEventId(String buttonPressed) {
        this.buttonPressed = buttonPressed;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GamepadEventId) || !super.equals(o)) {
            return false;
        }
        return Objects.equal(this.buttonPressed, ((GamepadEventId) o).buttonPressed);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{Integer.valueOf(super.hashCode()), this.buttonPressed});
    }
}
