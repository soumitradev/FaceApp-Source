package com.google.android.gms.auth.api.signin;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public class SignInAccount extends zzbgl implements ReflectedParcelable {
    public static final Creator<SignInAccount> CREATOR = new zzf();
    @Deprecated
    private String zza;
    private GoogleSignInAccount zzb;
    @Deprecated
    private String zzc;

    SignInAccount(String str, GoogleSignInAccount googleSignInAccount, String str2) {
        this.zzb = googleSignInAccount;
        this.zza = zzbq.zza(str, (Object) "8.3 and 8.4 SDKs require non-null email");
        this.zzc = zzbq.zza(str2, (Object) "8.3 and 8.4 SDKs require non-null userId");
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 4, this.zza, false);
        zzbgo.zza(parcel, 7, this.zzb, i, false);
        zzbgo.zza(parcel, 8, this.zzc, false);
        zzbgo.zza(parcel, zza);
    }

    @Hide
    public final GoogleSignInAccount zza() {
        return this.zzb;
    }
}
