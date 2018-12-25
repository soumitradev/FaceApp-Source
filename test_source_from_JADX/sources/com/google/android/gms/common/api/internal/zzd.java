package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzd extends zzb<Void> {
    private zzcq<zzb, ?> zzb;
    private zzdo<zzb, ?> zzc;

    public zzd(zzcr zzcr, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zzb = zzcr.zza;
        this.zzc = zzcr.zzb;
    }

    public final /* bridge */ /* synthetic */ void zza(@NonNull zzae zzae, boolean z) {
    }

    public final void zzb(zzbo<?> zzbo) throws RemoteException {
        this.zzb.zza(zzbo.zzb(), this.zza);
        if (this.zzb.zza() != null) {
            zzbo.zzc().put(this.zzb.zza(), new zzcr(this.zzb, this.zzc));
        }
    }
}
