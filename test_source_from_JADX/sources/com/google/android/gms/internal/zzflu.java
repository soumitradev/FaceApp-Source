package com.google.android.gms.internal;

import java.util.Arrays;

final class zzflu {
    final int zza;
    final byte[] zzb;

    zzflu(int i, byte[] bArr) {
        this.zza = i;
        this.zzb = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzflu)) {
            return false;
        }
        zzflu zzflu = (zzflu) obj;
        return this.zza == zzflu.zza && Arrays.equals(this.zzb, zzflu.zzb);
    }

    public final int hashCode() {
        return ((this.zza + 527) * 31) + Arrays.hashCode(this.zzb);
    }
}
