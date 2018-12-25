package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

public class BooleanResult implements Result {
    private final Status zza;
    private final boolean zzb;

    @Hide
    public BooleanResult(Status status, boolean z) {
        this.zza = (Status) zzbq.zza(status, "Status must not be null");
        this.zzb = z;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BooleanResult)) {
            return false;
        }
        BooleanResult booleanResult = (BooleanResult) obj;
        return this.zza.equals(booleanResult.zza) && this.zzb == booleanResult.zzb;
    }

    public Status getStatus() {
        return this.zza;
    }

    public boolean getValue() {
        return this.zzb;
    }

    public final int hashCode() {
        return ((this.zza.hashCode() + 527) * 31) + this.zzb;
    }
}
