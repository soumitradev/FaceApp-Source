package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;

public final class zzl implements ServiceConnection {
    private final int zza;
    private /* synthetic */ zzd zzb;

    public zzl(zzd zzd, int i) {
        this.zzb = zzd;
        this.zza = i;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            zzd.zza(this.zzb, 16);
            return;
        }
        synchronized (zzd.zza(this.zzb)) {
            zzay zzay;
            zzd zzd = this.zzb;
            if (iBinder == null) {
                zzay = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                zzay = (queryLocalInterface == null || !(queryLocalInterface instanceof zzay)) ? new zzaz(iBinder) : (zzay) queryLocalInterface;
            }
            zzd.zza(zzd, zzay);
        }
        this.zzb.zza(0, null, this.zza);
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (zzd.zza(this.zzb)) {
            zzd.zza(this.zzb, null);
        }
        this.zzb.zza.sendMessage(this.zzb.zza.obtainMessage(6, this.zza, 1));
    }
}
