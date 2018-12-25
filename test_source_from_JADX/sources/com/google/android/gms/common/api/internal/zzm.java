package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbz;

public abstract class zzm<R extends Result, A extends zzb> extends BasePendingResult<R> implements zzn<R> {
    private final zzc<A> zza;
    private final Api<?> zzb;

    @Deprecated
    protected zzm(@NonNull zzc<A> zzc, @NonNull GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzbq.zza((Object) googleApiClient, (Object) "GoogleApiClient must not be null"));
        this.zza = (zzc) zzbq.zza((Object) zzc);
        this.zzb = null;
    }

    protected zzm(@NonNull Api<?> api, @NonNull GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzbq.zza((Object) googleApiClient, (Object) "GoogleApiClient must not be null"));
        zzbq.zza((Object) api, (Object) "Api must not be null");
        this.zza = api.zzc();
        this.zzb = api;
    }

    private final void zza(@NonNull RemoteException remoteException) {
        zzc(new Status(8, remoteException.getLocalizedMessage(), null));
    }

    @Hide
    protected abstract void zza(@NonNull A a) throws RemoteException;

    @Hide
    public final void zzb(@NonNull A a) throws DeadObjectException {
        if (a instanceof zzbz) {
            zzb zzi = zzbz.zzi();
        }
        try {
            zza(zzi);
        } catch (RemoteException e) {
            zza(e);
            throw e;
        } catch (RemoteException e2) {
            zza(e2);
        }
    }

    @Hide
    public final zzc<A> zzc() {
        return this.zza;
    }

    @Hide
    public final void zzc(@NonNull Status status) {
        zzbq.zzb(status.isSuccess() ^ 1, "Failed result must not be success");
        zza(zza(status));
    }

    @Hide
    public final Api<?> zzd() {
        return this.zzb;
    }
}
