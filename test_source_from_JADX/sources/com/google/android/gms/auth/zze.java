package com.google.android.gms.auth;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.zzayl;
import com.google.android.gms.internal.zzez;
import java.io.IOException;

final class zze implements zzj<TokenData> {
    private /* synthetic */ Account zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ Bundle zzc;

    zze(Account account, String str, Bundle bundle) {
        this.zza = account;
        this.zzb = str;
        this.zzc = bundle;
    }

    public final /* synthetic */ Object zza(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        Bundle bundle = (Bundle) zzd.zza(zzez.zza(iBinder).zza(this.zza, this.zzb, this.zzc));
        TokenData zza = TokenData.zza(bundle, "tokenDetails");
        if (zza != null) {
            return zza;
        }
        String string = bundle.getString("Error");
        Intent intent = (Intent) bundle.getParcelable("userRecoveryIntent");
        zzayl zza2 = zzayl.zza(string);
        int i = 0;
        if (zzayl.zza(zza2)) {
            Object[] objArr = new Object[1];
            String valueOf = String.valueOf(zza2);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 31);
            stringBuilder.append("isUserRecoverableError status: ");
            stringBuilder.append(valueOf);
            objArr[0] = stringBuilder.toString();
            zzd.zza().zzd("GoogleAuthUtil", objArr);
            throw new UserRecoverableAuthException(string, intent);
        }
        if (zzayl.NETWORK_ERROR.equals(zza2) || zzayl.SERVICE_UNAVAILABLE.equals(zza2)) {
            i = 1;
        }
        if (i != 0) {
            throw new IOException(string);
        }
        throw new GoogleAuthException(string);
    }
}
