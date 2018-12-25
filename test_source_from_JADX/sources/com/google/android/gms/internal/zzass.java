package com.google.android.gms.internal;

import android.os.Build.VERSION;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzass {
    public static int zza() {
        try {
            return Integer.parseInt(VERSION.SDK);
        } catch (NumberFormatException e) {
            zzatc.zza("Invalid version number", VERSION.SDK);
            return 0;
        }
    }
}
