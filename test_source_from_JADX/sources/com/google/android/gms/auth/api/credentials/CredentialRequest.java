package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class CredentialRequest extends zzbgl {
    public static final Creator<CredentialRequest> CREATOR = new zze();
    @Hide
    private int zza;
    private final boolean zzb;
    private final String[] zzc;
    private final CredentialPickerConfig zzd;
    private final CredentialPickerConfig zze;
    private final boolean zzf;
    private final String zzg;
    private final String zzh;
    private final boolean zzi;

    public static final class Builder {
        private boolean zza;
        private String[] zzb;
        private CredentialPickerConfig zzc;
        private CredentialPickerConfig zzd;
        private boolean zze = false;
        private boolean zzf = false;
        @Nullable
        private String zzg = null;
        @Nullable
        private String zzh;

        public final CredentialRequest build() {
            if (this.zzb == null) {
                this.zzb = new String[0];
            }
            if (this.zza || this.zzb.length != 0) {
                return new CredentialRequest();
            }
            throw new IllegalStateException("At least one authentication method must be specified");
        }

        public final Builder setAccountTypes(String... strArr) {
            if (strArr == null) {
                strArr = new String[0];
            }
            this.zzb = strArr;
            return this;
        }

        public final Builder setCredentialHintPickerConfig(CredentialPickerConfig credentialPickerConfig) {
            this.zzd = credentialPickerConfig;
            return this;
        }

        public final Builder setCredentialPickerConfig(CredentialPickerConfig credentialPickerConfig) {
            this.zzc = credentialPickerConfig;
            return this;
        }

        public final Builder setIdTokenNonce(@Nullable String str) {
            this.zzh = str;
            return this;
        }

        public final Builder setIdTokenRequested(boolean z) {
            this.zze = z;
            return this;
        }

        public final Builder setPasswordLoginSupported(boolean z) {
            this.zza = z;
            return this;
        }

        public final Builder setServerClientId(@Nullable String str) {
            this.zzg = str;
            return this;
        }

        @Deprecated
        public final Builder setSupportsPasswordLogin(boolean z) {
            return setPasswordLoginSupported(z);
        }
    }

    CredentialRequest(int i, boolean z, String[] strArr, CredentialPickerConfig credentialPickerConfig, CredentialPickerConfig credentialPickerConfig2, boolean z2, String str, String str2, boolean z3) {
        this.zza = i;
        this.zzb = z;
        this.zzc = (String[]) zzbq.zza((Object) strArr);
        if (credentialPickerConfig == null) {
            credentialPickerConfig = new com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Builder().build();
        }
        this.zzd = credentialPickerConfig;
        if (credentialPickerConfig2 == null) {
            credentialPickerConfig2 = new com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Builder().build();
        }
        this.zze = credentialPickerConfig2;
        if (i < 3) {
            this.zzf = true;
            this.zzg = null;
            this.zzh = null;
        } else {
            this.zzf = z2;
            this.zzg = str;
            this.zzh = str2;
        }
        this.zzi = z3;
    }

    private CredentialRequest(Builder builder) {
        this(4, builder.zza, builder.zzb, builder.zzc, builder.zzd, builder.zze, builder.zzg, builder.zzh, false);
    }

    @NonNull
    public final String[] getAccountTypes() {
        return this.zzc;
    }

    @NonNull
    public final Set<String> getAccountTypesSet() {
        return new HashSet(Arrays.asList(this.zzc));
    }

    @NonNull
    public final CredentialPickerConfig getCredentialHintPickerConfig() {
        return this.zze;
    }

    @NonNull
    public final CredentialPickerConfig getCredentialPickerConfig() {
        return this.zzd;
    }

    @Nullable
    public final String getIdTokenNonce() {
        return this.zzh;
    }

    @Nullable
    public final String getServerClientId() {
        return this.zzg;
    }

    @Deprecated
    public final boolean getSupportsPasswordLogin() {
        return isPasswordLoginSupported();
    }

    public final boolean isIdTokenRequested() {
        return this.zzf;
    }

    public final boolean isPasswordLoginSupported() {
        return this.zzb;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, isPasswordLoginSupported());
        zzbgo.zza(parcel, 2, getAccountTypes(), false);
        zzbgo.zza(parcel, 3, getCredentialPickerConfig(), i, false);
        zzbgo.zza(parcel, 4, getCredentialHintPickerConfig(), i, false);
        zzbgo.zza(parcel, 5, isIdTokenRequested());
        zzbgo.zza(parcel, 6, getServerClientId(), false);
        zzbgo.zza(parcel, 7, getIdTokenNonce(), false);
        zzbgo.zza(parcel, 1000, this.zza);
        zzbgo.zza(parcel, 8, this.zzi);
        zzbgo.zza(parcel, zza);
    }
}
