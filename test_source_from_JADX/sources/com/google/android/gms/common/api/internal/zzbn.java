package com.google.android.gms.common.api.internal;

final class zzbn implements zzl {
    private /* synthetic */ zzbm zza;

    zzbn(zzbm zzbm) {
        this.zza = zzbm;
    }

    public final void zza(boolean z) {
        this.zza.zzq.sendMessage(this.zza.zzq.obtainMessage(1, Boolean.valueOf(z)));
    }
}
