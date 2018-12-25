package com.google.android.gms.common.api.internal;

import android.support.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

final class zzq implements Runnable {
    final /* synthetic */ zzo zza;
    private final zzp zzb;

    zzq(zzo zzo, zzp zzp) {
        this.zza = zzo;
        this.zzb = zzp;
    }

    @MainThread
    public final void run() {
        if (this.zza.zza) {
            ConnectionResult zzb = this.zzb.zzb();
            if (zzb.hasResolution()) {
                this.zza.zzd.startActivityForResult(GoogleApiActivity.zza(this.zza.zzg(), zzb.getResolution(), this.zzb.zza(), false), 1);
            } else if (this.zza.zzc.isUserResolvableError(zzb.getErrorCode())) {
                this.zza.zzc.zza(this.zza.zzg(), this.zza.zzd, zzb.getErrorCode(), 2, this.zza);
            } else if (zzb.getErrorCode() == 18) {
                GoogleApiAvailability.zza(this.zza.zzg().getApplicationContext(), new zzr(this, GoogleApiAvailability.zza(this.zza.zzg(), this.zza)));
            } else {
                this.zza.zza(zzb, this.zzb.zza());
            }
        }
    }
}
