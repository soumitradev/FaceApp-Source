package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class HintRequest extends zzbgl implements ReflectedParcelable {
    public static final Creator<HintRequest> CREATOR = new zzh();
    private int zza;
    private final CredentialPickerConfig zzb;
    private final boolean zzc;
    private final boolean zzd;
    private final String[] zze;
    private final boolean zzf;
    private final String zzg;
    private final String zzh;

    public static final class Builder {
        private boolean zza;
        private boolean zzb;
        private String[] zzc;
        private CredentialPickerConfig zzd = new com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Builder().build();
        private boolean zze = false;
        private String zzf;
        private String zzg;

        public final HintRequest build() {
            if (this.zzc == null) {
                this.zzc = new String[0];
            }
            if (this.zza || this.zzb || this.zzc.length != 0) {
                return new HintRequest();
            }
            throw new IllegalStateException("At least one authentication method must be specified");
        }

        public final Builder setAccountTypes(String... strArr) {
            if (strArr == null) {
                strArr = new String[0];
            }
            this.zzc = strArr;
            return this;
        }

        public final Builder setEmailAddressIdentifierSupported(boolean z) {
            this.zza = z;
            return this;
        }

        public final Builder setHintPickerConfig(@NonNull CredentialPickerConfig credentialPickerConfig) {
            this.zzd = (CredentialPickerConfig) zzbq.zza(credentialPickerConfig);
            return this;
        }

        public final Builder setIdTokenNonce(@Nullable String str) {
            this.zzg = str;
            return this;
        }

        public final Builder setIdTokenRequested(boolean z) {
            this.zze = z;
            return this;
        }

        public final Builder setPhoneNumberIdentifierSupported(boolean z) {
            this.zzb = z;
            return this;
        }

        public final Builder setServerClientId(@Nullable String str) {
            this.zzf = str;
            return this;
        }
    }

    HintRequest(int i, CredentialPickerConfig credentialPickerConfig, boolean z, boolean z2, String[] strArr, boolean z3, String str, String str2) {
        this.zza = i;
        this.zzb = (CredentialPickerConfig) zzbq.zza(credentialPickerConfig);
        this.zzc = z;
        this.zzd = z2;
        this.zze = (String[]) zzbq.zza(strArr);
        if (this.zza < 2) {
            this.zzf = true;
            this.zzg = null;
            this.zzh = null;
            return;
        }
        this.zzf = z3;
        this.zzg = str;
        this.zzh = str2;
    }

    private HintRequest(Builder builder) {
        this(2, builder.zzd, builder.zza, builder.zzb, builder.zzc, builder.zze, builder.zzf, builder.zzg);
    }

    @NonNull
    public final String[] getAccountTypes() {
        return this.zze;
    }

    @NonNull
    public final CredentialPickerConfig getHintPickerConfig() {
        return this.zzb;
    }

    @Nullable
    public final String getIdTokenNonce() {
        return this.zzh;
    }

    @Nullable
    public final String getServerClientId() {
        return this.zzg;
    }

    public final boolean isEmailAddressIdentifierSupported() {
        return this.zzc;
    }

    public final boolean isIdTokenRequested() {
        return this.zzf;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, getHintPickerConfig(), i, false);
        zzbgo.zza(parcel, 2, isEmailAddressIdentifierSupported());
        zzbgo.zza(parcel, 3, this.zzd);
        zzbgo.zza(parcel, 4, getAccountTypes(), false);
        zzbgo.zza(parcel, 5, isIdTokenRequested());
        zzbgo.zza(parcel, 6, getServerClientId(), false);
        zzbgo.zza(parcel, 7, getIdTokenNonce(), false);
        zzbgo.zza(parcel, 1000, this.zza);
        zzbgo.zza(parcel, zza);
    }
}
