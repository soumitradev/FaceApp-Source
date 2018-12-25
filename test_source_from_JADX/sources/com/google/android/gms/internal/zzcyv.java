package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbr;

@Hide
public final class zzcyv implements Creator<zzcyu> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        int i = 0;
        zzbr zzbr = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 2:
                    zzbr = (zzbr) zzbgm.zza(parcel, readInt, zzbr.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new zzcyu(i, zzbr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcyu[i];
    }
}
