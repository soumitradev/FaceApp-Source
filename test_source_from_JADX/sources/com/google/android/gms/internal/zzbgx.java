package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzbgx extends zzbgr {
    private final zzn<Status> zza;

    public zzbgx(zzn<Status> zzn) {
        this.zza = zzn;
    }

    public final void zza(int i) throws RemoteException {
        this.zza.zza(new Status(i));
    }
}
