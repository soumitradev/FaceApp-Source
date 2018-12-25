package com.google.android.gms.internal;

final class zzfhl {
    private static Class<?> zza = zzb();

    public static zzfhm zza() {
        if (zza != null) {
            try {
                return zza("getEmptyRegistry");
            } catch (Exception e) {
            }
        }
        return zzfhm.zza;
    }

    private static final zzfhm zza(String str) throws Exception {
        return (zzfhm) zza.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }

    private static Class<?> zzb() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
