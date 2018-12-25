package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

@Hide
public final class zzawp extends zzbgl {
    public static final Creator<zzawp> CREATOR = new zzawq();
    @Hide
    private int zza;
    private String zzb;
    private int zzc;

    zzawp(int i, String str, int i2) {
        this.zza = 1;
        this.zzb = (String) zzbq.zza(str);
        this.zzc = i2;
    }

    public zzawp(String str, int i) {
        this(1, str, i);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, false);
        zzbgo.zza(parcel, 3, this.zzc);
        zzbgo.zza(parcel, i);
    }
}
