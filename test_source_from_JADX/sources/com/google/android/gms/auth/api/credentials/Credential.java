package com.google.android.gms.auth.api.credentials;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Credential extends zzbgl implements ReflectedParcelable {
    public static final Creator<Credential> CREATOR = new zza();
    public static final String EXTRA_KEY = "com.google.android.gms.credentials.Credential";
    private final String zza;
    @Nullable
    private final String zzb;
    @Nullable
    private final Uri zzc;
    private final List<IdToken> zzd;
    @Nullable
    private final String zze;
    @Nullable
    private final String zzf;
    @Nullable
    private final String zzg;
    @Nullable
    private final String zzh;
    @Nullable
    private final String zzi;
    @Nullable
    private final String zzj;

    public static class Builder {
        private final String zza;
        private String zzb;
        private Uri zzc;
        private List<IdToken> zzd;
        private String zze;
        private String zzf;
        private String zzg;
        private String zzh;
        private String zzi;
        private String zzj;

        public Builder(Credential credential) {
            this.zza = credential.zza;
            this.zzb = credential.zzb;
            this.zzc = credential.zzc;
            this.zzd = credential.zzd;
            this.zze = credential.zze;
            this.zzf = credential.zzf;
            this.zzg = credential.zzg;
            this.zzh = credential.zzh;
            this.zzi = credential.zzi;
            this.zzj = credential.zzj;
        }

        public Builder(String str) {
            this.zza = str;
        }

        public Credential build() {
            return new Credential(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, this.zzh, this.zzi, this.zzj);
        }

        public Builder setAccountType(String str) {
            this.zzf = str;
            return this;
        }

        public Builder setName(String str) {
            this.zzb = str;
            return this;
        }

        public Builder setPassword(String str) {
            this.zze = str;
            return this;
        }

        public Builder setProfilePictureUri(Uri uri) {
            this.zzc = uri;
            return this;
        }
    }

    @Hide
    Credential(String str, String str2, Uri uri, List<IdToken> list, String str3, String str4, String str5, String str6, String str7, String str8) {
        str = ((String) zzbq.zza(str, "credential identifier cannot be null")).trim();
        zzbq.zza(str, "credential identifier cannot be empty");
        if (str3 == null || !TextUtils.isEmpty(str3)) {
            if (str4 != null) {
                boolean z = false;
                if (!TextUtils.isEmpty(str4)) {
                    Uri parse = Uri.parse(str4);
                    if (parse.isAbsolute() && parse.isHierarchical() && !TextUtils.isEmpty(parse.getScheme())) {
                        if (!TextUtils.isEmpty(parse.getAuthority())) {
                            if ("http".equalsIgnoreCase(parse.getScheme()) || "https".equalsIgnoreCase(parse.getScheme())) {
                                z = true;
                            }
                        }
                    }
                }
                if (!Boolean.valueOf(z).booleanValue()) {
                    throw new IllegalArgumentException("Account type must be a valid Http/Https URI");
                }
            }
            if (TextUtils.isEmpty(str4) || TextUtils.isEmpty(str3)) {
                if (str2 != null && TextUtils.isEmpty(str2.trim())) {
                    str2 = null;
                }
                this.zzb = str2;
                this.zzc = uri;
                this.zzd = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
                this.zza = str;
                this.zze = str3;
                this.zzf = str4;
                this.zzg = str5;
                this.zzh = str6;
                this.zzi = str7;
                this.zzj = str8;
                return;
            }
            throw new IllegalArgumentException("Password and AccountType are mutually exclusive");
        }
        throw new IllegalArgumentException("Password must not be empty if set");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Credential)) {
            return false;
        }
        Credential credential = (Credential) obj;
        return TextUtils.equals(this.zza, credential.zza) && TextUtils.equals(this.zzb, credential.zzb) && zzbg.zza(this.zzc, credential.zzc) && TextUtils.equals(this.zze, credential.zze) && TextUtils.equals(this.zzf, credential.zzf) && TextUtils.equals(this.zzg, credential.zzg);
    }

    @Nullable
    public String getAccountType() {
        return this.zzf;
    }

    @Nullable
    public String getFamilyName() {
        return this.zzj;
    }

    @Nullable
    public String getGeneratedPassword() {
        return this.zzg;
    }

    @Nullable
    public String getGivenName() {
        return this.zzi;
    }

    public String getId() {
        return this.zza;
    }

    public List<IdToken> getIdTokens() {
        return this.zzd;
    }

    @Nullable
    public String getName() {
        return this.zzb;
    }

    @Nullable
    public String getPassword() {
        return this.zze;
    }

    @Nullable
    public Uri getProfilePictureUri() {
        return this.zzc;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza, this.zzb, this.zzc, this.zze, this.zzf, this.zzg});
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, getId(), false);
        zzbgo.zza(parcel, 2, getName(), false);
        zzbgo.zza(parcel, 3, getProfilePictureUri(), i, false);
        zzbgo.zzc(parcel, 4, getIdTokens(), false);
        zzbgo.zza(parcel, 5, getPassword(), false);
        zzbgo.zza(parcel, 6, getAccountType(), false);
        zzbgo.zza(parcel, 7, getGeneratedPassword(), false);
        zzbgo.zza(parcel, 8, this.zzh, false);
        zzbgo.zza(parcel, 9, getGivenName(), false);
        zzbgo.zza(parcel, 10, getFamilyName(), false);
        zzbgo.zza(parcel, zza);
    }
}
