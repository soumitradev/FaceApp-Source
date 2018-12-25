package com.google.android.gms.common.stats;

import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.text.TextUtils;

public final class zzc {
    public static String zza(WakeLock wakeLock, String str) {
        Object obj;
        String valueOf = String.valueOf(String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode(wakeLock))));
        if (TextUtils.isEmpty(str)) {
            obj = "";
        }
        str = String.valueOf(obj);
        return str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
    }
}
