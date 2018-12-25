package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public abstract class zzari extends zzarh {
    private boolean zza;

    protected zzari(zzark zzark) {
        super(zzark);
    }

    @Hide
    protected abstract void zza();

    @Hide
    public final void zzaa() {
        zza();
        this.zza = true;
    }

    @Hide
    public final boolean zzy() {
        return this.zza;
    }

    @Hide
    protected final void zzz() {
        if (!zzy()) {
            throw new IllegalStateException("Not initialized");
        }
    }
}
