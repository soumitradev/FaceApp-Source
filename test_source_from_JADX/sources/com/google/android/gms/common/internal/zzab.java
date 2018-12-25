package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import java.util.Set;

public abstract class zzab<T extends IInterface> extends zzd<T> implements zze, zzaf {
    private final zzr zzd;
    private final Set<Scope> zze;
    private final Account zzf;

    protected zzab(Context context, Looper looper, int i, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzag.zza(context), GoogleApiAvailability.getInstance(), i, zzr, (ConnectionCallbacks) zzbq.zza((Object) connectionCallbacks), (OnConnectionFailedListener) zzbq.zza((Object) onConnectionFailedListener));
    }

    private zzab(Context context, Looper looper, zzag zzag, GoogleApiAvailability googleApiAvailability, int i, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        zzd zzd = this;
        ConnectionCallbacks connectionCallbacks2 = connectionCallbacks;
        OnConnectionFailedListener onConnectionFailedListener2 = onConnectionFailedListener;
        super(context, looper, zzag, googleApiAvailability, i, connectionCallbacks2 == null ? null : new zzac(connectionCallbacks2), onConnectionFailedListener2 == null ? null : new zzad(onConnectionFailedListener2), zzr.zzi());
        zzd.zzd = zzr;
        zzd.zzf = zzr.zzb();
        Set zzf = zzr.zzf();
        Set<Scope> zza = zza(zzf);
        for (Scope contains : zza) {
            if (!zzf.contains(contains)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        zzd.zze = zza;
    }

    @Hide
    @NonNull
    protected Set<Scope> zza(@NonNull Set<Scope> set) {
        return set;
    }

    public final Account zzac() {
        return this.zzf;
    }

    public zzc[] zzad() {
        return new zzc[0];
    }

    protected final Set<Scope> zzah() {
        return this.zze;
    }

    protected final zzr zzai() {
        return this.zzd;
    }

    public final int zzx() {
        return -1;
    }
}
