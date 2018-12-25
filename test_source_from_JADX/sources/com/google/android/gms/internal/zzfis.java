package com.google.android.gms.internal;

final class zzfis implements zzfjb {
    private zzfjb[] zza;

    zzfis(zzfjb... zzfjbArr) {
        this.zza = zzfjbArr;
    }

    public final boolean zza(Class<?> cls) {
        for (zzfjb zza : this.zza) {
            if (zza.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzfja zzb(Class<?> cls) {
        for (zzfjb zzfjb : this.zza) {
            if (zzfjb.zza(cls)) {
                return zzfjb.zzb(cls);
            }
        }
        String str = "No factory is available for message type: ";
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }
}
