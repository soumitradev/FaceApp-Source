package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zza implements Creator<AdBreakClipInfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zza = zzbgm.zza(parcel);
        long j = 0;
        long j2 = j;
        String str = null;
        String str2 = str;
        String str3 = str2;
        String str4 = str3;
        String str5 = str4;
        String str6 = str5;
        String str7 = str6;
        String str8 = str7;
        String str9 = str8;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str = zzbgm.zzq(parcel2, readInt);
                    break;
                case 3:
                    str2 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 4:
                    j = zzbgm.zzi(parcel2, readInt);
                    break;
                case 5:
                    str3 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 6:
                    str4 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 7:
                    str5 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 8:
                    str6 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 9:
                    str7 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 10:
                    str8 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 11:
                    j2 = zzbgm.zzi(parcel2, readInt);
                    break;
                case 12:
                    str9 = zzbgm.zzq(parcel2, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel2, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel2, zza);
        return new AdBreakClipInfo(str, str2, j, str3, str4, str5, str6, str7, str8, j2, str9);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new AdBreakClipInfo[i];
    }
}
