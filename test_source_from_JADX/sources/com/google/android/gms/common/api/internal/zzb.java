package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzb<T> extends zza {
    protected final TaskCompletionSource<T> zza;

    public zzb(int i, TaskCompletionSource<T> taskCompletionSource) {
        super(i);
        this.zza = taskCompletionSource;
    }

    public void zza(@NonNull Status status) {
        this.zza.trySetException(new ApiException(status));
    }

    public void zza(@NonNull zzae zzae, boolean z) {
    }

    public final void zza(zzbo<?> zzbo) throws DeadObjectException {
        try {
            zzb(zzbo);
        } catch (RemoteException e) {
            zza(zza.zzb(e));
            throw e;
        } catch (RemoteException e2) {
            zza(zza.zzb(e2));
        } catch (RuntimeException e3) {
            zza(e3);
        }
    }

    public void zza(@NonNull RuntimeException runtimeException) {
        this.zza.trySetException(runtimeException);
    }

    protected abstract void zzb(zzbo<?> zzbo) throws RemoteException;
}
