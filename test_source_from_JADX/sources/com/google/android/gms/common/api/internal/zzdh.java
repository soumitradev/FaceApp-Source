package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzbq;
import java.lang.ref.WeakReference;

public final class zzdh<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    private ResultTransform<? super R, ? extends Result> zza = null;
    private zzdh<? extends Result> zzb = null;
    private volatile ResultCallbacks<? super R> zzc = null;
    private PendingResult<R> zzd = null;
    private final Object zze = new Object();
    private Status zzf = null;
    private final WeakReference<GoogleApiClient> zzg;
    private final zzdj zzh;
    private boolean zzi = false;

    public zzdh(WeakReference<GoogleApiClient> weakReference) {
        zzbq.zza(weakReference, "GoogleApiClient reference must not be null");
        this.zzg = weakReference;
        GoogleApiClient googleApiClient = (GoogleApiClient) this.zzg.get();
        this.zzh = new zzdj(this, googleApiClient != null ? googleApiClient.zzc() : Looper.getMainLooper());
    }

    private static void zza(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                String valueOf = String.valueOf(result);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 18);
                stringBuilder.append("Unable to release ");
                stringBuilder.append(valueOf);
                Log.w("TransformedResultImpl", stringBuilder.toString(), e);
            }
        }
    }

    private final void zza(Status status) {
        synchronized (this.zze) {
            this.zzf = status;
            zzb(this.zzf);
        }
    }

    private final void zzb() {
        if (this.zza != null || this.zzc != null) {
            GoogleApiClient googleApiClient = (GoogleApiClient) this.zzg.get();
            if (!(this.zzi || this.zza == null || googleApiClient == null)) {
                googleApiClient.zza(this);
                this.zzi = true;
            }
            if (this.zzf != null) {
                zzb(this.zzf);
                return;
            }
            if (this.zzd != null) {
                this.zzd.setResultCallback(this);
            }
        }
    }

    private final void zzb(Status status) {
        synchronized (this.zze) {
            if (this.zza != null) {
                status = this.zza.onFailure(status);
                zzbq.zza(status, "onFailure must not return null");
                this.zzb.zza(status);
            } else if (zzc()) {
                this.zzc.onFailure(status);
            }
        }
    }

    private final boolean zzc() {
        return (this.zzc == null || ((GoogleApiClient) this.zzg.get()) == null) ? false : true;
    }

    public final void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        synchronized (this.zze) {
            boolean z = false;
            zzbq.zza(this.zzc == null, "Cannot call andFinally() twice.");
            if (this.zza == null) {
                z = true;
            }
            zzbq.zza(z, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzc = resultCallbacks;
            zzb();
        }
    }

    public final void onResult(R r) {
        synchronized (this.zze) {
            if (!r.getStatus().isSuccess()) {
                zza(r.getStatus());
                zza((Result) r);
            } else if (this.zza != null) {
                zzcs.zza().submit(new zzdi(this, r));
            } else if (zzc()) {
                this.zzc.onSuccess(r);
            }
        }
    }

    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult zzdh;
        synchronized (this.zze) {
            boolean z = false;
            zzbq.zza(this.zza == null, "Cannot call then() twice.");
            if (this.zzc == null) {
                z = true;
            }
            zzbq.zza(z, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zza = resultTransform;
            zzdh = new zzdh(this.zzg);
            this.zzb = zzdh;
            zzb();
        }
        return zzdh;
    }

    final void zza() {
        this.zzc = null;
    }

    public final void zza(PendingResult<?> pendingResult) {
        synchronized (this.zze) {
            this.zzd = pendingResult;
            zzb();
        }
    }
}
