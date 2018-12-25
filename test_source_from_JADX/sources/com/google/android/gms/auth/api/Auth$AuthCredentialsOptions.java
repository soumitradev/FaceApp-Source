package com.google.android.gms.auth.api;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;
import com.google.android.gms.common.api.Api$ApiOptions$Optional;
import com.google.android.gms.common.internal.Hide;

@Deprecated
public class Auth$AuthCredentialsOptions implements Api$ApiOptions$Optional {
    @Hide
    private static Auth$AuthCredentialsOptions zza = new Builder().zza();
    private final String zzb = null;
    private final PasswordSpecification zzc;
    private final boolean zzd;

    @Deprecated
    /* renamed from: com.google.android.gms.auth.api.Auth$AuthCredentialsOptions$Builder */
    public static class Builder {
        @Hide
        @NonNull
        protected PasswordSpecification zza = PasswordSpecification.zza;
        @Hide
        protected Boolean zzb = Boolean.valueOf(false);

        public Builder forceEnableSaveDialog() {
            this.zzb = Boolean.valueOf(true);
            return this;
        }

        @Hide
        public Auth$AuthCredentialsOptions zza() {
            return new Auth$AuthCredentialsOptions(this);
        }
    }

    @Hide
    public Auth$AuthCredentialsOptions(Builder builder) {
        this.zzc = builder.zza;
        this.zzd = builder.zzb.booleanValue();
    }

    @Hide
    public final PasswordSpecification zza() {
        return this.zzc;
    }

    @Hide
    public final Bundle zzb() {
        Bundle bundle = new Bundle();
        bundle.putString("consumer_package", null);
        bundle.putParcelable("password_specification", this.zzc);
        bundle.putBoolean("force_save_dialog", this.zzd);
        return bundle;
    }
}
