package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzbde implements Creator<zzbdd> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        String str = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            if ((SupportMenu.USER_MASK & readInt) != 2) {
                zzbgm.zzb(parcel, readInt);
            } else {
                str = zzbgm.zzq(parcel, readInt);
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new zzbdd(str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbdd[i];
    }
}
