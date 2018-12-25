package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.data.DataHolder;

public abstract class zzai<L> implements zzcl<L> {
    private final DataHolder zza;

    protected zzai(DataHolder dataHolder) {
        this.zza = dataHolder;
    }

    public final void zza() {
        if (this.zza != null) {
            this.zza.close();
        }
    }

    public final void zza(L l) {
        zza(l, this.zza);
    }

    protected abstract void zza(L l, DataHolder dataHolder);
}
