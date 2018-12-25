package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzac implements Creator<zzab> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        int i = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            if ((SupportMenu.USER_MASK & readInt) != 2) {
                zzbgm.zzb(parcel, readInt);
            } else {
                i = zzbgm.zzg(parcel, readInt);
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new zzab(i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzab[i];
    }
}
