package android.support.v7.graphics;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

public interface Palette$Filter {
    boolean isAllowed(@ColorInt int i, @NonNull float[] fArr);
}
