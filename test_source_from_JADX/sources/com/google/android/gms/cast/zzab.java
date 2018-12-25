package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

@Hide
public final class zzab extends zzbgl {
    @Hide
    public static final Creator<zzab> CREATOR = new zzac();
    private int zza;

    public zzab() {
        this(0);
    }

    zzab(int i) {
        this.zza = i;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzab)) {
            return false;
        }
        return this.zza == ((zzab) obj).zza;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zza)});
    }

    public final String toString() {
        int i = this.zza;
        String str = i != 0 ? i != 2 ? "UNKNOWN" : "INVISIBLE" : "STRONG";
        return String.format("joinOptions(connectionType=%s)", new Object[]{str});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza);
        zzbgo.zza(parcel, i);
    }
}
