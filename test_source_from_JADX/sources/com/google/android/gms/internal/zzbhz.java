package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import java.util.ArrayList;

@Hide
public final class zzbhz implements Creator<zzbhw> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        String str = null;
        int i = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 1:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 2:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    arrayList = zzbgm.zzc(parcel, readInt, zzbhx.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new zzbhw(i, str, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbhw[i];
    }
}
