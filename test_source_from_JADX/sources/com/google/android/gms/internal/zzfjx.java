package com.google.android.gms.internal;

final class zzfjx {
    private static final Class<?> zza = zzd();
    private static final zzfkn<?, ?> zzb = zza(false);
    private static final zzfkn<?, ?> zzc = zza(true);
    private static final zzfkn<?, ?> zzd = new zzfkp();

    public static zzfkn<?, ?> zza() {
        return zzb;
    }

    private static zzfkn<?, ?> zza(boolean z) {
        try {
            Class zze = zze();
            if (zze == null) {
                return null;
            }
            return (zzfkn) zze.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable th) {
            return null;
        }
    }

    public static void zza(Class<?> cls) {
        if (!zzfhu.class.isAssignableFrom(cls) && zza != null && !zza.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static zzfkn<?, ?> zzb() {
        return zzc;
    }

    public static zzfkn<?, ?> zzc() {
        return zzd;
    }

    private static Class<?> zzd() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable th) {
            return null;
        }
    }

    private static Class<?> zze() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable th) {
            return null;
        }
    }
}
