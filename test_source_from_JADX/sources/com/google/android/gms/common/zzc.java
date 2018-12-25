package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public final class zzc extends zzbgl {
    public static final Creator<zzc> CREATOR = new zzd();
    private String zza;
    private int zzb;

    public zzc(String str, int i) {
        this.zza = str;
        this.zzb = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza, false);
        zzbgo.zza(parcel, 2, this.zzb);
        zzbgo.zza(parcel, i);
    }
}
