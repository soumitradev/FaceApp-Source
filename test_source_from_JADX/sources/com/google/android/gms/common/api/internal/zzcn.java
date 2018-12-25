package com.google.android.gms.common.api.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zzcn extends zzo {
    private TaskCompletionSource<Void> zze = new TaskCompletionSource();

    private zzcn(zzcf zzcf) {
        super(zzcf);
        this.zzd.zza("GmsAvailabilityHelper", (LifecycleCallback) this);
    }

    public static zzcn zzb(Activity activity) {
        zzcf zza = LifecycleCallback.zza(activity);
        zzcn zzcn = (zzcn) zza.zza("GmsAvailabilityHelper", zzcn.class);
        if (zzcn == null) {
            return new zzcn(zza);
        }
        if (zzcn.zze.getTask().isComplete()) {
            zzcn.zze = new TaskCompletionSource();
        }
        return zzcn;
    }

    protected final void zza(ConnectionResult connectionResult, int i) {
        this.zze.setException(zzb.zza(new Status(connectionResult.getErrorCode(), connectionResult.getErrorMessage(), connectionResult.getResolution())));
    }

    protected final void zzc() {
        int isGooglePlayServicesAvailable = this.zzc.isGooglePlayServicesAvailable(this.zzd.zza());
        if (isGooglePlayServicesAvailable == 0) {
            this.zze.setResult(null);
            return;
        }
        if (!this.zze.getTask().isComplete()) {
            zzb(new ConnectionResult(isGooglePlayServicesAvailable, null), 0);
        }
    }

    public final Task<Void> zzf() {
        return this.zze.getTask();
    }

    public final void zzh() {
        super.zzh();
        this.zze.trySetException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }
}
