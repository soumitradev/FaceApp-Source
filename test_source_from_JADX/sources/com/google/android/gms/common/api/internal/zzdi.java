package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

final class zzdi implements Runnable {
    private /* synthetic */ Result zza;
    private /* synthetic */ zzdh zzb;

    zzdi(zzdh zzdh, Result result) {
        this.zzb = zzdh;
        this.zza = result;
    }

    @WorkerThread
    public final void run() {
        GoogleApiClient googleApiClient;
        try {
            BasePendingResult.zzc.set(Boolean.valueOf(true));
            this.zzb.zzh.sendMessage(this.zzb.zzh.obtainMessage(0, this.zzb.zza.onSuccess(this.zza)));
            BasePendingResult.zzc.set(Boolean.valueOf(false));
            zzdh.zza(this.zza);
            googleApiClient = (GoogleApiClient) this.zzb.zzg.get();
            if (googleApiClient != null) {
                googleApiClient.zzb(this.zzb);
            }
        } catch (RuntimeException e) {
            this.zzb.zzh.sendMessage(this.zzb.zzh.obtainMessage(1, e));
            BasePendingResult.zzc.set(Boolean.valueOf(false));
            zzdh.zza(this.zza);
            googleApiClient = (GoogleApiClient) this.zzb.zzg.get();
            if (googleApiClient != null) {
                googleApiClient.zzb(this.zzb);
            }
        } catch (Throwable th) {
            BasePendingResult.zzc.set(Boolean.valueOf(false));
            zzdh.zza(this.zza);
            GoogleApiClient googleApiClient2 = (GoogleApiClient) this.zzb.zzg.get();
            if (googleApiClient2 != null) {
                googleApiClient2.zzb(this.zzb);
            }
            throw th;
        }
    }
}
