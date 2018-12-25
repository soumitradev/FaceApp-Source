package android.support.v4.os;

import android.os.Parcelable.Creator;

@Deprecated
public final class ParcelableCompat {
    @Deprecated
    public static <T> Creator<T> newCreator(ParcelableCompatCreatorCallbacks<T> callbacks) {
        return new ParcelableCompat$ParcelableCompatCreatorHoneycombMR2(callbacks);
    }

    private ParcelableCompat() {
    }
}
