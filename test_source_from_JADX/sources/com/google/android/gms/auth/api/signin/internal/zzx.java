package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.os.Binder;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient$Builder;

public final class zzx extends zzs {
    private final Context zza;

    public zzx(Context context) {
        this.zza = context;
    }

    private final void zzc() {
        if (!GooglePlayServicesUtil.zzb(this.zza, Binder.getCallingUid())) {
            int callingUid = Binder.getCallingUid();
            StringBuilder stringBuilder = new StringBuilder(52);
            stringBuilder.append("Calling UID ");
            stringBuilder.append(callingUid);
            stringBuilder.append(" is not Google Play services.");
            throw new SecurityException(stringBuilder.toString());
        }
    }

    public final void zza() {
        zzc();
        zzaa zza = zzaa.zza(this.zza);
        GoogleSignInAccount zza2 = zza.zza();
        HasOptions hasOptions = GoogleSignInOptions.DEFAULT_SIGN_IN;
        if (zza2 != null) {
            hasOptions = zza.zzb();
        }
        GoogleApiClient build = new GoogleApiClient$Builder(this.zza).addApi(Auth.GOOGLE_SIGN_IN_API, hasOptions).build();
        try {
            if (build.blockingConnect().isSuccess()) {
                if (zza2 != null) {
                    Auth.GoogleSignInApi.revokeAccess(build);
                } else {
                    build.clearDefaultAccountAndReconnect();
                }
            }
            build.disconnect();
        } catch (Throwable th) {
            build.disconnect();
        }
    }

    public final void zzb() {
        zzc();
        zzp.zza(this.zza).zza();
    }
}
