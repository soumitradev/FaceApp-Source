package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzf extends zzb<Boolean> {
    private zzck<?> zzb;

    public zzf(zzck<?> zzck, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zzb = zzck;
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull zzae zzae, boolean z) {
    }

    public final void zzb(zzbo<?> zzbo) throws RemoteException {
        zzcr zzcr = (zzcr) zzbo.zzc().remove(this.zzb);
        if (zzcr != null) {
            zzcr.zzb.zza(zzbo.zzb(), this.zza);
            zzcr.zza.zzb();
            return;
        }
        this.zza.trySetResult(Boolean.valueOf(false));
    }
}
