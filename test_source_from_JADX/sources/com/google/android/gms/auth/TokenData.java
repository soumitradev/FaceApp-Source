package com.google.android.gms.auth;

import android.os.Bundle;
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
import java.util.List;

@Hide
public class TokenData extends zzbgl implements ReflectedParcelable {
    public static final Creator<TokenData> CREATOR = new zzk();
    private int zza;
    private final String zzb;
    private final Long zzc;
    private final boolean zzd;
    private final boolean zze;
    private final List<String> zzf;

    TokenData(int i, String str, Long l, boolean z, boolean z2, List<String> list) {
        this.zza = i;
        this.zzb = zzbq.zza(str);
        this.zzc = l;
        this.zzd = z;
        this.zze = z2;
        this.zzf = list;
    }

    @Nullable
    public static TokenData zza(Bundle bundle, String str) {
        bundle.setClassLoader(TokenData.class.getClassLoader());
        bundle = bundle.getBundle(str);
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(TokenData.class.getClassLoader());
        return (TokenData) bundle.getParcelable("TokenData");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TokenData)) {
            return false;
        }
        TokenData tokenData = (TokenData) obj;
        return TextUtils.equals(this.zzb, tokenData.zzb) && zzbg.zza(this.zzc, tokenData.zzc) && this.zzd == tokenData.zzd && this.zze == tokenData.zze && zzbg.zza(this.zzf, tokenData.zzf);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzb, this.zzc, Boolean.valueOf(this.zzd), Boolean.valueOf(this.zze), this.zzf});
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, false);
        zzbgo.zza(parcel, 3, this.zzc, false);
        zzbgo.zza(parcel, 4, this.zzd);
        zzbgo.zza(parcel, 5, this.zze);
        zzbgo.zzb(parcel, 6, this.zzf, false);
        zzbgo.zza(parcel, i);
    }

    public final String zza() {
        return this.zzb;
    }
}
