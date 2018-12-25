package com.google.android.gms.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzavy implements Result {
    private final Status zza;

    public zzavy(Status status) {
        this.zza = status;
    }

    public final Status getStatus() {
        return this.zza;
    }
}
