package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzz extends zzbgl {
    public static final Creator<zzz> CREATOR = new zzaa();
    String zza;
    IBinder zzb;
    Scope[] zzc;
    Bundle zzd;
    Account zze;
    zzc[] zzf;
    private int zzg;
    private int zzh;
    private int zzi;

    public zzz(int i) {
        this.zzg = 3;
        this.zzi = zzf.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzh = i;
    }

    zzz(int i, int i2, int i3, String str, IBinder iBinder, Scope[] scopeArr, Bundle bundle, Account account, zzc[] zzcArr) {
        this.zzg = i;
        this.zzh = i2;
        this.zzi = i3;
        if ("com.google.android.gms".equals(str)) {
            this.zza = "com.google.android.gms";
        } else {
            this.zza = str;
        }
        if (i < 2) {
            Account account2 = null;
            if (iBinder != null) {
                zzan zzap;
                if (iBinder != null) {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
                    zzap = queryLocalInterface instanceof zzan ? (zzan) queryLocalInterface : new zzap(iBinder);
                }
                account2 = zza.zza(zzap);
            }
            this.zze = account2;
        } else {
            this.zzb = iBinder;
            this.zze = account;
        }
        this.zzc = scopeArr;
        this.zzd = bundle;
        this.zzf = zzcArr;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzg);
        zzbgo.zza(parcel, 2, this.zzh);
        zzbgo.zza(parcel, 3, this.zzi);
        zzbgo.zza(parcel, 4, this.zza, false);
        zzbgo.zza(parcel, 5, this.zzb, false);
        zzbgo.zza(parcel, 6, this.zzc, i, false);
        zzbgo.zza(parcel, 7, this.zzd, false);
        zzbgo.zza(parcel, 8, this.zze, i, false);
        zzbgo.zza(parcel, 10, this.zzf, i, false);
        zzbgo.zza(parcel, zza);
    }
}
