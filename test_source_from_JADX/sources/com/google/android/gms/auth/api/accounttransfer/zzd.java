package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.zzawn;
import com.google.android.gms.internal.zzawt;

final class zzd extends zzc {
    private /* synthetic */ zzawt zzb;

    zzd(AccountTransferClient accountTransferClient, zzawt zzawt) {
        this.zzb = zzawt;
        super();
    }

    protected final void zza(zzawn zzawn) throws RemoteException {
        zzawn.zza(this.zza, this.zzb);
    }
}
