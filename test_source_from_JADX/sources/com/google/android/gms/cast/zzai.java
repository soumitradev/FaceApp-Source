package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import org.catrobat.catroid.common.BrickValues;

@Hide
public final class zzai implements Creator<MediaQueueItem> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zza = zzbgm.zza(parcel);
        double d = BrickValues.SET_COLOR_TO;
        double d2 = d;
        double d3 = d2;
        MediaInfo mediaInfo = null;
        long[] jArr = mediaInfo;
        String str = jArr;
        int i = 0;
        boolean z = false;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    mediaInfo = (MediaInfo) zzbgm.zza(parcel2, readInt, MediaInfo.CREATOR);
                    break;
                case 3:
                    i = zzbgm.zzg(parcel2, readInt);
                    break;
                case 4:
                    z = zzbgm.zzc(parcel2, readInt);
                    break;
                case 5:
                    d = zzbgm.zzn(parcel2, readInt);
                    break;
                case 6:
                    d2 = zzbgm.zzn(parcel2, readInt);
                    break;
                case 7:
                    d3 = zzbgm.zzn(parcel2, readInt);
                    break;
                case 8:
                    jArr = zzbgm.zzx(parcel2, readInt);
                    break;
                case 9:
                    str = zzbgm.zzq(parcel2, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel2, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel2, zza);
        return new MediaQueueItem(mediaInfo, i, z, d, d2, d3, jArr, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MediaQueueItem[i];
    }
}
