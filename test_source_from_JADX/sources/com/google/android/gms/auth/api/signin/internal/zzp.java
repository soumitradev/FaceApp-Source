package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.zzbq;

public final class zzp {
    private static zzp zza = null;
    private zzaa zzb;
    private GoogleSignInAccount zzc = this.zzb.zza();
    private GoogleSignInOptions zzd = this.zzb.zzb();

    private zzp(Context context) {
        this.zzb = zzaa.zza(context);
    }

    public static synchronized zzp zza(@NonNull Context context) {
        zzp zzb;
        synchronized (zzp.class) {
            zzb = zzb(context.getApplicationContext());
        }
        return zzb;
    }

    private static synchronized zzp zzb(Context context) {
        zzp zzp;
        synchronized (zzp.class) {
            if (zza == null) {
                zza = new zzp(context);
            }
            zzp = zza;
        }
        return zzp;
    }

    public final synchronized void zza() {
        this.zzb.zzd();
        this.zzc = null;
        this.zzd = null;
    }

    public final synchronized void zza(GoogleSignInOptions googleSignInOptions, GoogleSignInAccount googleSignInAccount) {
        zzaa zzaa = this.zzb;
        zzbq.zza(googleSignInAccount);
        zzbq.zza(googleSignInOptions);
        zzaa.zza("defaultGoogleSignInAccount", googleSignInAccount.zzc());
        zzaa.zza(googleSignInAccount, googleSignInOptions);
        this.zzc = googleSignInAccount;
        this.zzd = googleSignInOptions;
    }

    public final synchronized GoogleSignInAccount zzb() {
        return this.zzc;
    }

    public final synchronized GoogleSignInOptions zzc() {
        return this.zzd;
    }
}
