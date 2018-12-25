package com.google.android.gms.clearcut;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

public final class zzc extends zzbgl {
    public static final Creator<zzc> CREATOR = new zzd();
    private boolean zza;
    private long zzb;
    private long zzc;

    public zzc(boolean z, long j, long j2) {
        this.zza = z;
        this.zzb = j;
        this.zzc = j2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzc) {
            zzc zzc = (zzc) obj;
            return this.zza == zzc.zza && this.zzb == zzc.zzb && this.zzc == zzc.zzc;
        }
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Boolean.valueOf(this.zza), Long.valueOf(this.zzb), Long.valueOf(this.zzc)});
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder("CollectForDebugParcelable[skipPersistentStorage: ");
        stringBuilder.append(this.zza);
        stringBuilder.append(",collectForDebugStartTimeMillis: ");
        stringBuilder.append(this.zzb);
        stringBuilder.append(",collectForDebugExpiryTimeMillis: ");
        stringBuilder.append(this.zzc);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzc);
        zzbgo.zza(parcel, 3, this.zzb);
        zzbgo.zza(parcel, i);
    }
}
