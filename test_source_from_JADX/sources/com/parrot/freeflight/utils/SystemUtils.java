package com.parrot.freeflight.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;

public final class SystemUtils {
    private SystemUtils() {
    }

    public static long getFreeMemory(Context context) {
        MemoryInfo mi = new MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(mi);
        return mi.availMem;
    }

    public static boolean isGoogleTV(Context theContext) {
        return theContext.getPackageManager().hasSystemFeature("com.google.android.tv");
    }
}
