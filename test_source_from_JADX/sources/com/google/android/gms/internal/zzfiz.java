package com.google.android.gms.internal;

final class zzfiz {
    private static final zzfix zza = zzc();
    private static final zzfix zzb = new zzfiy();

    static zzfix zza() {
        return zza;
    }

    static zzfix zzb() {
        return zzb;
    }

    private static zzfix zzc() {
        try {
            return (zzfix) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
