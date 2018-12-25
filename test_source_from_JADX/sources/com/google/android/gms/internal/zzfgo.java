package com.google.android.gms.internal;

final class zzfgo {
    private static final Class<?> zza = zza("libcore.io.Memory");
    private static final boolean zzb = (zza("org.robolectric.Robolectric") != null);

    private static <T> Class<T> zza(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable th) {
            return null;
        }
    }

    static boolean zza() {
        return (zza == null || zzb) ? false : true;
    }

    static Class<?> zzb() {
        return zza;
    }
}
