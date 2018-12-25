package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzbr extends zzbgl {
    public static final Creator<zzbr> CREATOR = new zzbs();
    private int zza;
    private final Account zzb;
    private final int zzc;
    private final GoogleSignInAccount zzd;

    zzbr(int i, Account account, int i2, GoogleSignInAccount googleSignInAccount) {
        this.zza = i;
        this.zzb = account;
        this.zzc = i2;
        this.zzd = googleSignInAccount;
    }

    public zzbr(Account account, int i, GoogleSignInAccount googleSignInAccount) {
        this(2, account, i, googleSignInAccount);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, i, false);
        zzbgo.zza(parcel, 3, this.zzc);
        zzbgo.zza(parcel, 4, this.zzd, i, false);
        zzbgo.zza(parcel, zza);
    }
}
