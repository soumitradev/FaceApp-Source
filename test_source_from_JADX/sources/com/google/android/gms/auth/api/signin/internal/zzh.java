package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Status;

final class zzh extends zza {
    private /* synthetic */ zzg zza;

    zzh(zzg zzg) {
        this.zza = zzg;
    }

    public final void zza(GoogleSignInAccount googleSignInAccount, Status status) throws RemoteException {
        if (googleSignInAccount != null) {
            zzp.zza(this.zza.zza).zza(this.zza.zzb, googleSignInAccount);
        }
        this.zza.zza(new GoogleSignInResult(googleSignInAccount, status));
    }
}
