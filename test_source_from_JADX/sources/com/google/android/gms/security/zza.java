package com.google.android.gms.security;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.zzf;
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener;

final class zza extends AsyncTask<Void, Void, Integer> {
    private /* synthetic */ Context zza;
    private /* synthetic */ ProviderInstallListener zzb;

    zza(Context context, ProviderInstallListener providerInstallListener) {
        this.zza = context;
        this.zzb = providerInstallListener;
    }

    private final Integer zza(Void... voidArr) {
        int i;
        try {
            ProviderInstaller.installIfNeeded(this.zza);
            i = 0;
        } catch (GooglePlayServicesRepairableException e) {
            i = e.getConnectionStatusCode();
        } catch (GooglePlayServicesNotAvailableException e2) {
            i = e2.errorCode;
        }
        return Integer.valueOf(i);
    }

    protected final /* synthetic */ Object doInBackground(Object[] objArr) {
        return zza((Void[]) objArr);
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        Integer num = (Integer) obj;
        if (num.intValue() == 0) {
            this.zzb.onProviderInstalled();
            return;
        }
        ProviderInstaller.zza;
        this.zzb.onProviderInstallFailed(num.intValue(), zzf.zza(this.zza, num.intValue(), "pi"));
    }
}
