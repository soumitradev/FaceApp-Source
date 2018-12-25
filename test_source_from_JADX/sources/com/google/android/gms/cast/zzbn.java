package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzbn implements Creator<TextTrackStyle> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zza = zzbgm.zza(parcel);
        String str = null;
        String str2 = str;
        float f = 0.0f;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    f = zzbgm.zzl(parcel2, readInt);
                    break;
                case 3:
                    i = zzbgm.zzg(parcel2, readInt);
                    break;
                case 4:
                    i2 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 5:
                    i3 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 6:
                    i4 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 7:
                    i5 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 8:
                    i6 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 9:
                    i7 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 10:
                    str = zzbgm.zzq(parcel2, readInt);
                    break;
                case 11:
                    i8 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 12:
                    i9 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 13:
                    str2 = zzbgm.zzq(parcel2, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel2, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel2, zza);
        return new TextTrackStyle(f, i, i2, i3, i4, i5, i6, i7, str, i8, i9, str2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new TextTrackStyle[i];
    }
}
