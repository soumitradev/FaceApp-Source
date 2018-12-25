package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public final class zzbex extends zzbeu {
    private /* synthetic */ zzbev zza;

    protected zzbex(zzbev zzbev) {
        this.zza = zzbev;
    }

    public final void zza(int i) throws RemoteException {
        zzbeq.zza().zza("onError: %d", new Object[]{Integer.valueOf(i)});
        zzbeq.zza(this.zza.zza);
        this.zza.zza(new zzbey(Status.zzc));
    }

    public final void zzb() throws RemoteException {
        zzbeq.zza().zza("onDisconnected", new Object[0]);
        zzbeq.zza(this.zza.zza);
        this.zza.zza(new zzbey(Status.zza));
    }
}
