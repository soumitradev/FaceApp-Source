package com.google.android.gms.auth;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.internal.zzez;
import java.io.IOException;

final class zzf implements zzj<Void> {
    private /* synthetic */ String zza;
    private /* synthetic */ Bundle zzb;

    zzf(String str, Bundle bundle) {
        this.zza = str;
        this.zzb = bundle;
    }

    public final /* synthetic */ Object zza(IBinder iBinder) throws RemoteException, IOException, GoogleAuthException {
        Bundle bundle = (Bundle) zzd.zza(zzez.zza(iBinder).zza(this.zza, this.zzb));
        String string = bundle.getString("Error");
        if (bundle.getBoolean("booleanResult")) {
            return null;
        }
        throw new GoogleAuthException(string);
    }
}
