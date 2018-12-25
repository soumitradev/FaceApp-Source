package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.zzez;
import java.io.IOException;

final class zzh implements zzj<Bundle> {
    private /* synthetic */ Account zza;

    zzh(Account account) {
        this.zza = account;
    }

    public final /* synthetic */ Object zza(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        return (Bundle) zzd.zza(zzez.zza(iBinder).zza(this.zza));
    }
}
