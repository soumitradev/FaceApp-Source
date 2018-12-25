package org.catrobat.catroid.content.eventids;

import android.support.annotation.NonNull;
import com.google.common.base.Objects;

public class NfcEventId extends EventId {
    final String tag;

    public NfcEventId(@NonNull String tag) {
        this.tag = tag;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NfcEventId) || !super.equals(o)) {
            return false;
        }
        return Objects.equal(this.tag, ((NfcEventId) o).tag);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{Integer.valueOf(super.hashCode()), this.tag});
    }
}
