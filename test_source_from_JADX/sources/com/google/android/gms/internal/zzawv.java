package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

@Hide
public final class zzawv extends zzbgl {
    public static final Creator<zzawv> CREATOR = new zzaww();
    @Hide
    private int zza;
    private String zzb;
    private PendingIntent zzc;

    zzawv(int i, String str, PendingIntent pendingIntent) {
        this.zza = 1;
        this.zzb = (String) zzbq.zza(str);
        this.zzc = (PendingIntent) zzbq.zza(pendingIntent);
    }

    public zzawv(String str, PendingIntent pendingIntent) {
        this(1, str, pendingIntent);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, false);
        zzbgo.zza(parcel, 3, this.zzc, i, false);
        zzbgo.zza(parcel, zza);
    }
}
