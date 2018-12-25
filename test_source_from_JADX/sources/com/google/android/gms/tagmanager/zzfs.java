package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Message;

final class zzfs implements zzfr {
    final /* synthetic */ zzfo zza;
    private Handler zzb;

    private zzfs(zzfo zzfo) {
        this.zza = zzfo;
        this.zzb = new Handler(this.zza.zzb.getMainLooper(), new zzft(this));
    }

    private final Message zzc() {
        return this.zzb.obtainMessage(1, zzfo.zza);
    }

    public final void zza() {
        this.zzb.removeMessages(1, zzfo.zza);
        this.zzb.sendMessage(zzc());
    }

    public final void zza(long j) {
        this.zzb.removeMessages(1, zzfo.zza);
        this.zzb.sendMessageDelayed(zzc(), j);
    }

    public final void zzb() {
        this.zzb.removeMessages(1, zzfo.zza);
    }
}
