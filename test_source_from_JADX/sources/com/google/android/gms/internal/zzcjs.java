package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

class zzcjs extends BroadcastReceiver {
    private static String zza = zzcjs.class.getName();
    private final zzckj zzb;
    private boolean zzc;
    private boolean zzd;

    zzcjs(zzckj zzckj) {
        zzbq.zza(zzckj);
        this.zzb = zzckj;
    }

    @MainThread
    public void onReceive(Context context, Intent intent) {
        this.zzb.zza();
        String action = intent.getAction();
        this.zzb.zzf().zzae().zza("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zzy = this.zzb.zzs().zzy();
            if (this.zzd != zzy) {
                this.zzd = zzy;
                this.zzb.zzh().zza(new zzcjt(this, zzy));
            }
            return;
        }
        this.zzb.zzf().zzaa().zza("NetworkBroadcastReceiver received unknown action", action);
    }

    @WorkerThread
    public final void zza() {
        this.zzb.zza();
        this.zzb.zzh().zzc();
        if (!this.zzc) {
            this.zzb.zzt().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.zzd = this.zzb.zzs().zzy();
            this.zzb.zzf().zzae().zza("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzd));
            this.zzc = true;
        }
    }

    @WorkerThread
    public final void zzb() {
        this.zzb.zza();
        this.zzb.zzh().zzc();
        this.zzb.zzh().zzc();
        if (this.zzc) {
            this.zzb.zzf().zzae().zza("Unregistering connectivity change receiver");
            this.zzc = false;
            this.zzd = false;
            try {
                this.zzb.zzt().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzb.zzf().zzy().zza("Failed to unregister the network broadcast receiver", e);
            }
        }
    }
}
