package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzc implements Creator<AdBreakStatus> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        String str = null;
        String str2 = str;
        long j = 0;
        long j2 = j;
        long j3 = j2;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    j = zzbgm.zzi(parcel, readInt);
                    break;
                case 3:
                    j2 = zzbgm.zzi(parcel, readInt);
                    break;
                case 4:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 5:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 6:
                    j3 = zzbgm.zzi(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new AdBreakStatus(j, j2, str, str2, j3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new AdBreakStatus[i];
    }
}
