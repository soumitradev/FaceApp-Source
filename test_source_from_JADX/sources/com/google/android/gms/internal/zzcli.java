package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

abstract class zzcli extends zzclh {
    private boolean zza;

    zzcli(zzckj zzckj) {
        super(zzckj);
        this.zzp.zza(this);
    }

    @Hide
    protected void p_() {
    }

    @Hide
    final boolean zzap() {
        return this.zza;
    }

    @Hide
    protected final void zzaq() {
        if (!zzap()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    @Hide
    public final void zzar() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        } else if (!zzw()) {
            this.zzp.zzaf();
            this.zza = true;
        }
    }

    @Hide
    public final void zzas() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        p_();
        this.zzp.zzaf();
        this.zza = true;
    }

    @Hide
    protected abstract boolean zzw();
}
