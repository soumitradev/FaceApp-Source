package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import java.util.Arrays;

public final class zzbdd extends zzbgl {
    @Hide
    public static final Creator<zzbdd> CREATOR = new zzbde();
    private String zza;

    public zzbdd() {
        this(null);
    }

    zzbdd(String str) {
        this.zza = str;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbdd)) {
            return false;
        }
        return zzbdw.zza(this.zza, ((zzbdd) obj).zza);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, i);
    }

    public final String zza() {
        return this.zza;
    }
}
