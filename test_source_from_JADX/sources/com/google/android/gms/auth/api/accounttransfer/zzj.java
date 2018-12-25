package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.zzawn;
import com.google.android.gms.internal.zzawp;

final class zzj extends zzc {
    private /* synthetic */ zzawp zzb;

    zzj(AccountTransferClient accountTransferClient, zzawp zzawp) {
        this.zzb = zzawp;
        super();
    }

    protected final void zza(zzawn zzawn) throws RemoteException {
        zzawn.zza(this.zza, this.zzb);
    }
}
