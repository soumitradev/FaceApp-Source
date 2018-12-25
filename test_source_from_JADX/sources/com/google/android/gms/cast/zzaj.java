package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import java.util.List;
import org.catrobat.catroid.common.BrickValues;

@Hide
public final class zzaj implements Creator<MediaStatus> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zza = zzbgm.zza(parcel);
        double d = BrickValues.SET_COLOR_TO;
        double d2 = d;
        long j = 0;
        long j2 = j;
        long j3 = j2;
        MediaInfo mediaInfo = null;
        long[] jArr = mediaInfo;
        String str = jArr;
        List list = str;
        AdBreakStatus adBreakStatus = list;
        VideoInfo videoInfo = adBreakStatus;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        boolean z2 = false;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    mediaInfo = (MediaInfo) zzbgm.zza(parcel2, readInt, MediaInfo.CREATOR);
                    break;
                case 3:
                    j = zzbgm.zzi(parcel2, readInt);
                    break;
                case 4:
                    i = zzbgm.zzg(parcel2, readInt);
                    break;
                case 5:
                    d = zzbgm.zzn(parcel2, readInt);
                    break;
                case 6:
                    i2 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 7:
                    i3 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 8:
                    j2 = zzbgm.zzi(parcel2, readInt);
                    break;
                case 9:
                    j3 = zzbgm.zzi(parcel2, readInt);
                    break;
                case 10:
                    d2 = zzbgm.zzn(parcel2, readInt);
                    break;
                case 11:
                    z = zzbgm.zzc(parcel2, readInt);
                    break;
                case 12:
                    jArr = zzbgm.zzx(parcel2, readInt);
                    break;
                case 13:
                    i4 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 14:
                    i5 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 15:
                    str = zzbgm.zzq(parcel2, readInt);
                    break;
                case 16:
                    i6 = zzbgm.zzg(parcel2, readInt);
                    break;
                case 17:
                    list = zzbgm.zzc(parcel2, readInt, MediaQueueItem.CREATOR);
                    break;
                case 18:
                    z2 = zzbgm.zzc(parcel2, readInt);
                    break;
                case 19:
                    adBreakStatus = (AdBreakStatus) zzbgm.zza(parcel2, readInt, AdBreakStatus.CREATOR);
                    break;
                case 20:
                    videoInfo = (VideoInfo) zzbgm.zza(parcel2, readInt, VideoInfo.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel2, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel2, zza);
        return new MediaStatus(mediaInfo, j, i, d, i2, i3, j2, j3, d2, z, jArr, i4, i5, str, i6, list, z2, adBreakStatus, videoInfo);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MediaStatus[i];
    }
}
