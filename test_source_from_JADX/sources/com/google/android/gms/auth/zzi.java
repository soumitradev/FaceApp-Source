package com.google.android.gms.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.zzayl;
import com.google.android.gms.internal.zzez;
import java.io.IOException;

final class zzi implements zzj<Boolean> {
    private /* synthetic */ String zza;

    zzi(String str) {
        this.zza = str;
    }

    public final /* synthetic */ Object zza(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        Bundle bundle = (Bundle) zzd.zza(zzez.zza(iBinder).zza(this.zza));
        String string = bundle.getString("Error");
        Intent intent = (Intent) bundle.getParcelable("userRecoveryIntent");
        zzayl zza = zzayl.zza(string);
        if (zzayl.SUCCESS.equals(zza)) {
            return Boolean.valueOf(true);
        }
        if (zzayl.zza(zza)) {
            Object[] objArr = new Object[1];
            String valueOf = String.valueOf(zza);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 31);
            stringBuilder.append("isUserRecoverableError status: ");
            stringBuilder.append(valueOf);
            objArr[0] = stringBuilder.toString();
            zzd.zza().zzd("GoogleAuthUtil", objArr);
            throw new UserRecoverableAuthException(string, intent);
        }
        throw new GoogleAuthException(string);
    }
}
