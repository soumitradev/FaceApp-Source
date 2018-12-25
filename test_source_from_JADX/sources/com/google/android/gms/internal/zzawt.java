package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

@Hide
public final class zzawt extends zzbgl {
    public static final Creator<zzawt> CREATOR = new zzawu();
    @Hide
    private int zza;
    private String zzb;
    private byte[] zzc;

    zzawt(int i, String str, byte[] bArr) {
        this.zza = 1;
        this.zzb = (String) zzbq.zza(str);
        this.zzc = (byte[]) zzbq.zza(bArr);
    }

    public zzawt(String str, byte[] bArr) {
        this(1, str, bArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, false);
        zzbgo.zza(parcel, 3, this.zzc, false);
        zzbgo.zza(parcel, i);
    }
}
