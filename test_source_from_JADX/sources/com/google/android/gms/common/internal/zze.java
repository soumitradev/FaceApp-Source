package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import com.google.android.gms.common.ConnectionResult;

@Hide
abstract class zze extends zzi<Boolean> {
    private int zza;
    private Bundle zzb;
    private /* synthetic */ zzd zzc;

    @BinderThread
    protected zze(zzd zzd, int i, Bundle bundle) {
        this.zzc = zzd;
        super(zzd, Boolean.valueOf(true));
        this.zza = i;
        this.zzb = bundle;
    }

    protected abstract void zza(ConnectionResult connectionResult);

    protected final /* synthetic */ void zza(Object obj) {
        PendingIntent pendingIntent = null;
        if (((Boolean) obj) == null) {
            zzd.zza(this.zzc, 1, null);
            return;
        }
        int i = this.zza;
        if (i == 0) {
            if (!zza()) {
                zzd.zza(this.zzc, 1, null);
                zza(new ConnectionResult(8, null));
            }
        } else if (i != 10) {
            zzd.zza(this.zzc, 1, null);
            if (this.zzb != null) {
                pendingIntent = (PendingIntent) this.zzb.getParcelable("pendingIntent");
            }
            zza(new ConnectionResult(this.zza, pendingIntent));
        } else {
            zzd.zza(this.zzc, 1, null);
            throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
        }
    }

    protected abstract boolean zza();

    protected final void zzb() {
    }
}
