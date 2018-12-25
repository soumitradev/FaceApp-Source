package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

@Hide
public final class zzawj extends zzbgl {
    public static final Creator<zzawj> CREATOR = new zzawk();
    @Hide
    private int zza;
    private String zzb;

    zzawj(int i, String str) {
        this.zza = 1;
        this.zzb = (String) zzbq.zza(str);
    }

    public zzawj(String str) {
        this(1, str);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, false);
        zzbgo.zza(parcel, i);
    }
}
