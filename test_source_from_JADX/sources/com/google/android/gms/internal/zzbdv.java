package com.google.android.gms.internal;

import com.google.android.gms.cast.Cast.MessageReceivedCallback;

final class zzbdv implements Runnable {
    private /* synthetic */ zzbdp zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ String zzc;

    zzbdv(zzbdr zzbdr, zzbdp zzbdp, String str, String str2) {
        this.zza = zzbdp;
        this.zzb = str;
        this.zzc = str2;
    }

    public final void run() {
        synchronized (this.zza.zzh) {
            MessageReceivedCallback messageReceivedCallback = (MessageReceivedCallback) this.zza.zzh.get(this.zzb);
        }
        if (messageReceivedCallback != null) {
            messageReceivedCallback.onMessageReceived(this.zza.zzf, this.zzb, this.zzc);
            return;
        }
        zzbdp.zzd.zza("Discarded message for unknown namespace '%s'", new Object[]{this.zzb});
    }
}
