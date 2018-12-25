package com.google.android.gms.auth.api.accounttransfer;

import android.os.RemoteException;
import com.google.android.gms.internal.zzawj;
import com.google.android.gms.internal.zzawn;

final class zzg extends zzb<DeviceMetaData> {
    private /* synthetic */ zzawj zza;

    zzg(AccountTransferClient accountTransferClient, zzawj zzawj) {
        this.zza = zzawj;
        super();
    }

    protected final void zza(zzawn zzawn) throws RemoteException {
        zzawn.zza(new zzh(this, this), this.zza);
    }
}
