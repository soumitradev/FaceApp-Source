package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;

@Hide
public final class zza extends zzao {
    public static Account zza(zzan zzan) {
        if (zzan != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Account zza;
            try {
                zza = zzan.zza();
                return zza;
            } catch (RemoteException e) {
                zza = "AccountAccessor";
                Log.w(zza, "Remote account accessor probably died");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return null;
    }

    public final boolean equals(Object obj) {
        throw new NoSuchMethodError();
    }

    public final Account zza() {
        throw new NoSuchMethodError();
    }
}
