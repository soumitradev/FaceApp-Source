package com.google.android.gms.auth.api.credentials;

import com.google.android.gms.auth.api.Auth$AuthCredentialsOptions;

public final class CredentialsOptions extends Auth$AuthCredentialsOptions {
    public static final CredentialsOptions DEFAULT = ((CredentialsOptions) new Builder().zza());

    public static final class Builder extends com.google.android.gms.auth.api.Auth$AuthCredentialsOptions.Builder {
        public final CredentialsOptions build() {
            return new CredentialsOptions();
        }

        public final Builder forceEnableSaveDialog() {
            this.zzb = Boolean.valueOf(true);
            return this;
        }

        public final /* synthetic */ Auth$AuthCredentialsOptions zza() {
            return build();
        }
    }

    private CredentialsOptions(Builder builder) {
        super(builder);
    }
}
