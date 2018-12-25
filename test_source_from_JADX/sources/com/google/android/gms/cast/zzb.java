package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzb implements Creator<AdBreakInfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        String str = null;
        String[] strArr = str;
        long j = 0;
        long j2 = j;
        boolean z = false;
        boolean z2 = false;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    j = zzbgm.zzi(parcel, readInt);
                    break;
                case 3:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    j2 = zzbgm.zzi(parcel, readInt);
                    break;
                case 5:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 6:
                    strArr = zzbgm.zzaa(parcel, readInt);
                    break;
                case 7:
                    z2 = zzbgm.zzc(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new AdBreakInfo(j, str, j2, z, strArr, z2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new AdBreakInfo[i];
    }
}
