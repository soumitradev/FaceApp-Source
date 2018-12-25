package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzciw implements Creator<zzciu> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        Bundle bundle = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            if ((SupportMenu.USER_MASK & readInt) != 2) {
                zzbgm.zzb(parcel, readInt);
            } else {
                bundle = zzbgm.zzs(parcel, readInt);
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new zzciu(bundle);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzciu[i];
    }
}
