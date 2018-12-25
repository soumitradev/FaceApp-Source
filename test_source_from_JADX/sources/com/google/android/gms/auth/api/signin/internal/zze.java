package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;

public final class zze extends zzab<zzv> {
    private final GoogleSignInOptions zzd;

    public zze(Context context, Looper looper, zzr zzr, GoogleSignInOptions googleSignInOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 91, zzr, connectionCallbacks, onConnectionFailedListener);
        if (googleSignInOptions == null) {
            googleSignInOptions = new Builder().build();
        }
        if (!zzr.zzf().isEmpty()) {
            Builder builder = new Builder(googleSignInOptions);
            for (Scope requestScopes : zzr.zzf()) {
                builder.requestScopes(requestScopes, new Scope[0]);
            }
            googleSignInOptions = builder.build();
        }
        this.zzd = googleSignInOptions;
    }

    public final GoogleSignInOptions m_() {
        return this.zzd;
    }

    protected final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.signin.internal.ISignInService");
        return queryLocalInterface instanceof zzv ? (zzv) queryLocalInterface : new zzw(iBinder);
    }

    protected final String zza() {
        return "com.google.android.gms.auth.api.signin.service.START";
    }

    protected final String zzb() {
        return "com.google.android.gms.auth.api.signin.internal.ISignInService";
    }

    public final boolean zze() {
        return true;
    }

    public final Intent zzf() {
        return zzf.zza(zzaa(), this.zzd);
    }
}
