package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzak implements Creator<MediaTrack> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zza = zzbgm.zza(parcel);
        String str = null;
        String str2 = str;
        String str3 = str2;
        String str4 = str3;
        String str5 = str4;
        long j = 0;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    j = zzbgm.zzi(parcel2, readInt);
                    break;
                case 3:
                    i = zzbgm.zzg(parcel2, readInt);
                    break;
                case 4:
                    str = zzbgm.zzq(parcel2, readInt);
                    break;
                case 5:
                    str2 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 6:
                    str3 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 7:
                    str4 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 8:
                    i2 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 9:
                    str5 = zzbgm.zzq(parcel2, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel2, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel2, zza);
        return new MediaTrack(j, i, str, str2, str3, str4, i2, str5);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MediaTrack[i];
    }
}
