package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

public class zzaj implements Releasable, Result {
    protected final DataHolder zza;
    private Status zzb;

    protected zzaj(DataHolder dataHolder, Status status) {
        this.zzb = status;
        this.zza = dataHolder;
    }

    public Status getStatus() {
        return this.zzb;
    }

    public void release() {
        if (this.zza != null) {
            this.zza.close();
        }
    }
}
