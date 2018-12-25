package com.google.android.gms.internal;

final class zzfhp {
    private static final zzfhn<?> zza = new zzfho();
    private static final zzfhn<?> zzb = zzc();

    static zzfhn<?> zza() {
        return zza;
    }

    static zzfhn<?> zzb() {
        if (zzb != null) {
            return zzb;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }

    private static zzfhn<?> zzc() {
        try {
            return (zzfhn) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }
}
