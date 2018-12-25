package com.google.android.gms.internal;

final class zzfjk {
    private static final zzfji zza = zzc();
    private static final zzfji zzb = new zzfjj();

    static zzfji zza() {
        return zza;
    }

    static zzfji zzb() {
        return zzb;
    }

    private static zzfji zzc() {
        try {
            return (zzfji) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
