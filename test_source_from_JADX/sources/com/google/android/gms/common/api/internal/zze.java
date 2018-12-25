package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zze<TResult> extends zza {
    private final zzde<zzb, TResult> zza;
    private final TaskCompletionSource<TResult> zzb;
    private final zzda zzc;

    public zze(int i, zzde<zzb, TResult> zzde, TaskCompletionSource<TResult> taskCompletionSource, zzda zzda) {
        super(i);
        this.zzb = taskCompletionSource;
        this.zza = zzde;
        this.zzc = zzda;
    }

    public final void zza(@NonNull Status status) {
        this.zzb.trySetException(this.zzc.zza(status));
    }

    public final void zza(@NonNull zzae zzae, boolean z) {
        zzae.zza(this.zzb, z);
    }

    public final void zza(zzbo<?> zzbo) throws DeadObjectException {
        try {
            this.zza.zza(zzbo.zzb(), this.zzb);
        } catch (DeadObjectException e) {
            throw e;
        } catch (RemoteException e2) {
            zza(zza.zzb(e2));
        } catch (Exception e3) {
            this.zzb.trySetException(e3);
        }
    }
}
