package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import java.util.List;

@Hide
public final class zzn implements Creator<CastDevice> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zza = zzbgm.zza(parcel);
        String str = null;
        String str2 = str;
        String str3 = str2;
        String str4 = str3;
        String str5 = str4;
        List list = str5;
        String str6 = list;
        String str7 = str6;
        String str8 = str7;
        byte[] bArr = str8;
        int i = 0;
        int i2 = 0;
        int i3 = -1;
        int i4 = 0;
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
                    str3 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 5:
                    str4 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 6:
                    str5 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 7:
                    i = zzbgm.zzg(parcel2, readInt);
                    break;
                case 8:
                    list = zzbgm.zzc(parcel2, readInt, WebImage.CREATOR);
                    break;
                case 9:
                    i2 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 10:
                    i3 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 11:
                    str6 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 12:
                    str7 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 13:
                    i4 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 14:
                    str8 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 15:
                    bArr = zzbgm.zzt(parcel2, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel2, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel2, zza);
        return new CastDevice(str, str2, str3, str4, str5, i, list, i2, i3, str6, str7, i4, str8, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CastDevice[i];
    }
}
