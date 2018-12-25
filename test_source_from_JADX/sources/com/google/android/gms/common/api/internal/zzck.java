package com.google.android.gms.common.api.internal;

public final class zzck<L> {
    private final L zza;
    private final String zzb;

    zzck(L l, String str) {
        this.zza = l;
        this.zzb = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzck)) {
            return false;
        }
        zzck zzck = (zzck) obj;
        return this.zza == zzck.zza && this.zzb.equals(zzck.zzb);
    }

    public final int hashCode() {
        return (System.identityHashCode(this.zza) * 31) + this.zzb.hashCode();
    }
}
