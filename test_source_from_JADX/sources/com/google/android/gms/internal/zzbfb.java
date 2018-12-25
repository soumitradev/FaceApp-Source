package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

final class zzbfb extends zzbfh {
    private /* synthetic */ zzbfg zza;
    private /* synthetic */ zzbfa zzb;

    zzbfb(zzbfa zzbfa, zzbfg zzbfg) {
        this.zzb = zzbfa;
        this.zza = zzbfg;
    }

    public final void zza(int i) throws RemoteException {
        zzbfa.zzi().zza("onRemoteDisplayEnded", new Object[0]);
        if (this.zza != null) {
            this.zza.zza(i);
        }
        if (zzbfa.zza(this.zzb) != null) {
            zzbfa.zza(this.zzb).onRemoteDisplayEnded(new Status(i));
        }
    }
}
