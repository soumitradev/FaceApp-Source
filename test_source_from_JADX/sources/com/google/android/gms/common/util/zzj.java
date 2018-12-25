package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;

public final class zzj {
    private static Boolean zza;
    private static Boolean zzb;
    private static Boolean zzc;
    private static Boolean zzd;
    private static Boolean zze;

    @TargetApi(20)
    public static boolean zza(Context context) {
        if (zzc == null) {
            boolean z = zzs.zzf() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
            zzc = Boolean.valueOf(z);
        }
        return zzc.booleanValue();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean zza(android.content.res.Resources r4) {
        /*
        r0 = 0;
        if (r4 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r1 = zza;
        if (r1 != 0) goto L_0x0045;
    L_0x0008:
        r1 = r4.getConfiguration();
        r1 = r1.screenLayout;
        r1 = r1 & 15;
        r2 = 3;
        r3 = 1;
        if (r1 <= r2) goto L_0x0016;
    L_0x0014:
        r1 = 1;
        goto L_0x0017;
    L_0x0016:
        r1 = 0;
    L_0x0017:
        if (r1 != 0) goto L_0x003e;
    L_0x0019:
        r1 = zzb;
        if (r1 != 0) goto L_0x0036;
    L_0x001d:
        r4 = r4.getConfiguration();
        r1 = r4.screenLayout;
        r1 = r1 & 15;
        if (r1 > r2) goto L_0x002f;
    L_0x0027:
        r4 = r4.smallestScreenWidthDp;
        r1 = 600; // 0x258 float:8.41E-43 double:2.964E-321;
        if (r4 < r1) goto L_0x002f;
    L_0x002d:
        r4 = 1;
        goto L_0x0030;
    L_0x002f:
        r4 = 0;
    L_0x0030:
        r4 = java.lang.Boolean.valueOf(r4);
        zzb = r4;
    L_0x0036:
        r4 = zzb;
        r4 = r4.booleanValue();
        if (r4 == 0) goto L_0x003f;
    L_0x003e:
        r0 = 1;
    L_0x003f:
        r4 = java.lang.Boolean.valueOf(r0);
        zza = r4;
    L_0x0045:
        r4 = zza;
        r4 = r4.booleanValue();
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.zzj.zza(android.content.res.Resources):boolean");
    }

    @TargetApi(24)
    public static boolean zzb(Context context) {
        return (!zzs.zzh() || zzc(context)) && zza(context);
    }

    @TargetApi(21)
    public static boolean zzc(Context context) {
        if (zzd == null) {
            boolean z = zzs.zzg() && context.getPackageManager().hasSystemFeature("cn.google");
            zzd = Boolean.valueOf(z);
        }
        return zzd.booleanValue();
    }

    public static boolean zzd(Context context) {
        if (zze == null) {
            boolean z;
            if (!context.getPackageManager().hasSystemFeature("android.hardware.type.iot")) {
                if (!context.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
                    z = false;
                    zze = Boolean.valueOf(z);
                }
            }
            z = true;
            zze = Boolean.valueOf(z);
        }
        return zze.booleanValue();
    }
}
