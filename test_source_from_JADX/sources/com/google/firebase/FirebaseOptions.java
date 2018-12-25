package com.google.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzca;
import com.google.android.gms.common.util.zzw;
import java.util.Arrays;

public final class FirebaseOptions {
    private final String zza;
    private final String zzb;
    private final String zzc;
    private final String zzd;
    private final String zze;
    private final String zzf;
    private final String zzg;

    public static final class Builder {
        private String zza;
        private String zzb;
        private String zzc;
        private String zzd;
        private String zze;
        private String zzf;
        private String zzg;

        public Builder(FirebaseOptions firebaseOptions) {
            this.zzb = firebaseOptions.zzb;
            this.zza = firebaseOptions.zza;
            this.zzc = firebaseOptions.zzc;
            this.zzd = firebaseOptions.zzd;
            this.zze = firebaseOptions.zze;
            this.zzf = firebaseOptions.zzf;
            this.zzg = firebaseOptions.zzg;
        }

        public final FirebaseOptions build() {
            return new FirebaseOptions(this.zzb, this.zza, this.zzc, this.zzd, this.zze, this.zzf, this.zzg);
        }

        public final Builder setApiKey(@NonNull String str) {
            this.zza = zzbq.zza(str, "ApiKey must be set.");
            return this;
        }

        public final Builder setApplicationId(@NonNull String str) {
            this.zzb = zzbq.zza(str, "ApplicationId must be set.");
            return this;
        }

        public final Builder setDatabaseUrl(@Nullable String str) {
            this.zzc = str;
            return this;
        }

        public final Builder setGcmSenderId(@Nullable String str) {
            this.zze = str;
            return this;
        }

        public final Builder setProjectId(@Nullable String str) {
            this.zzg = str;
            return this;
        }

        public final Builder setStorageBucket(@Nullable String str) {
            this.zzf = str;
            return this;
        }
    }

    private FirebaseOptions(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7) {
        zzbq.zza(zzw.zza(str) ^ 1, "ApplicationId must be set.");
        this.zzb = str;
        this.zza = str2;
        this.zzc = str3;
        this.zzd = str4;
        this.zze = str5;
        this.zzf = str6;
        this.zzg = str7;
    }

    public static FirebaseOptions fromResource(Context context) {
        zzca zzca = new zzca(context);
        Object zza = zzca.zza("google_app_id");
        return TextUtils.isEmpty(zza) ? null : new FirebaseOptions(zza, zzca.zza("google_api_key"), zzca.zza("firebase_database_url"), zzca.zza("ga_trackingId"), zzca.zza("gcm_defaultSenderId"), zzca.zza("google_storage_bucket"), zzca.zza("project_id"));
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof FirebaseOptions)) {
            return false;
        }
        FirebaseOptions firebaseOptions = (FirebaseOptions) obj;
        return zzbg.zza(this.zzb, firebaseOptions.zzb) && zzbg.zza(this.zza, firebaseOptions.zza) && zzbg.zza(this.zzc, firebaseOptions.zzc) && zzbg.zza(this.zzd, firebaseOptions.zzd) && zzbg.zza(this.zze, firebaseOptions.zze) && zzbg.zza(this.zzf, firebaseOptions.zzf) && zzbg.zza(this.zzg, firebaseOptions.zzg);
    }

    public final String getApiKey() {
        return this.zza;
    }

    public final String getApplicationId() {
        return this.zzb;
    }

    public final String getDatabaseUrl() {
        return this.zzc;
    }

    public final String getGcmSenderId() {
        return this.zze;
    }

    public final String getProjectId() {
        return this.zzg;
    }

    public final String getStorageBucket() {
        return this.zzf;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzb, this.zza, this.zzc, this.zzd, this.zze, this.zzf, this.zzg});
    }

    public final String toString() {
        return zzbg.zza(this).zza("applicationId", this.zzb).zza("apiKey", this.zza).zza("databaseUrl", this.zzc).zza("gcmSenderId", this.zze).zza("storageBucket", this.zzf).zza("projectId", this.zzg).toString();
    }
}
