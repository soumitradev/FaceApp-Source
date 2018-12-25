package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.zzawn;
import com.google.android.gms.internal.zzawr;

final class zze extends zzb<byte[]> {
    private /* synthetic */ zzawr zza;

    zze(AccountTransferClient accountTransferClient, zzawr zzawr) {
        this.zza = zzawr;
        super();
    }

    protected final void zza(zzawn zzawn) throws RemoteException {
        zzawn.zza(new zzf(this, this), this.zza);
    }
}
