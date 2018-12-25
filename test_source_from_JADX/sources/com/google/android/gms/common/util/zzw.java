package com.google.android.gms.common.util;

import android.support.annotation.Nullable;
import java.util.regex.Pattern;

public final class zzw {
    private static final Pattern zza = Pattern.compile("\\$\\{(.*?)\\}");

    public static boolean zza(@Nullable String str) {
        if (str != null) {
            if (!str.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
