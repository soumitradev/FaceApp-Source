package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class CredentialPickerConfig extends zzbgl implements ReflectedParcelable {
    public static final Creator<CredentialPickerConfig> CREATOR = new zzc();
    @Hide
    private int zza;
    private final boolean zzb;
    private final boolean zzc;
    @Deprecated
    private final boolean zzd;
    private final int zze;

    public static class Builder {
        private boolean zza = false;
        private boolean zzb = true;
        private int zzc = 1;

        public CredentialPickerConfig build() {
            return new CredentialPickerConfig();
        }

        @Deprecated
        public Builder setForNewAccount(boolean z) {
            this.zzc = z ? 3 : 1;
            return this;
        }

        public Builder setPrompt(int i) {
            this.zzc = i;
            return this;
        }

        public Builder setShowAddAccountButton(boolean z) {
            this.zza = z;
            return this;
        }

        public Builder setShowCancelButton(boolean z) {
            this.zzb = z;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Prompt {
        public static final int CONTINUE = 1;
        public static final int SIGN_IN = 2;
        public static final int SIGN_UP = 3;
    }

    CredentialPickerConfig(int i, boolean z, boolean z2, boolean z3, int i2) {
        this.zza = i;
        this.zzb = z;
        this.zzc = z2;
        z = true;
        if (i < 2) {
            int i3;
            this.zzd = z3;
            if (z3) {
                i3 = 3;
            }
            this.zze = i3;
            return;
        }
        if (i2 != 3) {
            z = false;
        }
        this.zzd = z;
        this.zze = i2;
    }

    private CredentialPickerConfig(Builder builder) {
        this(2, builder.zza, builder.zzb, false, builder.zzc);
    }

    @Deprecated
    public final boolean isForNewAccount() {
        return this.zze == 3;
    }

    public final boolean shouldShowAddAccountButton() {
        return this.zzb;
    }

    public final boolean shouldShowCancelButton() {
        return this.zzc;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, shouldShowAddAccountButton());
        zzbgo.zza(parcel, 2, shouldShowCancelButton());
        zzbgo.zza(parcel, 3, isForNewAccount());
        zzbgo.zza(parcel, 4, this.zze);
        zzbgo.zza(parcel, 1000, this.zza);
        zzbgo.zza(parcel, i);
    }
}
