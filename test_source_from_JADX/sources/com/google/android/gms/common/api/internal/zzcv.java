package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbt;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzcyg;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import com.google.android.gms.internal.zzcyo;
import com.google.android.gms.internal.zzcyw;
import java.util.Set;

public final class zzcv extends zzcyo implements ConnectionCallbacks, OnConnectionFailedListener {
    private static zza<? extends zzcyj, zzcyk> zza = zzcyg.zza;
    private final Context zzb;
    private final Handler zzc;
    private final zza<? extends zzcyj, zzcyk> zzd;
    private Set<Scope> zze;
    private zzr zzf;
    private zzcyj zzg;
    private zzcy zzh;

    @WorkerThread
    public zzcv(Context context, Handler handler, @NonNull zzr zzr) {
        this(context, handler, zzr, zza);
    }

    @WorkerThread
    public zzcv(Context context, Handler handler, @NonNull zzr zzr, zza<? extends zzcyj, zzcyk> zza) {
        this.zzb = context;
        this.zzc = handler;
        this.zzf = (zzr) zzbq.zza(zzr, "ClientSettings must not be null");
        this.zze = zzr.zze();
        this.zzd = zza;
    }

    @WorkerThread
    private final void zzb(zzcyw zzcyw) {
        ConnectionResult zza = zzcyw.zza();
        if (zza.isSuccess()) {
            zzbt zzb = zzcyw.zzb();
            zza = zzb.zzb();
            if (zza.isSuccess()) {
                this.zzh.zza(zzb.zza(), this.zze);
                this.zzg.zzg();
            }
            String valueOf = String.valueOf(zza);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 48);
            stringBuilder.append("Sign-in succeeded with resolve account failure: ");
            stringBuilder.append(valueOf);
            Log.wtf("SignInCoordinator", stringBuilder.toString(), new Exception());
        }
        this.zzh.zzb(zza);
        this.zzg.zzg();
    }

    @WorkerThread
    public final void onConnected(@Nullable Bundle bundle) {
        this.zzg.zza(this);
    }

    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzh.zzb(connectionResult);
    }

    @WorkerThread
    public final void onConnectionSuspended(int i) {
        this.zzg.zzg();
    }

    public final zzcyj zza() {
        return this.zzg;
    }

    @WorkerThread
    public final void zza(zzcy zzcy) {
        if (this.zzg != null) {
            this.zzg.zzg();
        }
        this.zzf.zza(Integer.valueOf(System.identityHashCode(this)));
        this.zzg = (zzcyj) this.zzd.zza(this.zzb, this.zzc.getLooper(), this.zzf, this.zzf.zzk(), this, this);
        this.zzh = zzcy;
        if (this.zze != null) {
            if (!this.zze.isEmpty()) {
                this.zzg.zzi();
                return;
            }
        }
        this.zzc.post(new zzcw(this));
    }

    @BinderThread
    public final void zza(zzcyw zzcyw) {
        this.zzc.post(new zzcx(this, zzcyw));
    }

    public final void zzb() {
        if (this.zzg != null) {
            this.zzg.zzg();
        }
    }
}
