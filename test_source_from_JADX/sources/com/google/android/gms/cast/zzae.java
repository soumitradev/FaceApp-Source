package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import java.util.List;

@Hide
public final class zzae implements Creator<MediaInfo> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zza = zzbgm.zza(parcel);
        String str = null;
        String str2 = str;
        MediaMetadata mediaMetadata = str2;
        List list = mediaMetadata;
        TextTrackStyle textTrackStyle = list;
        String str3 = textTrackStyle;
        List list2 = str3;
        List list3 = list2;
        String str4 = list3;
        long j = 0;
        int i = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str = zzbgm.zzq(parcel2, readInt);
                    break;
                case 3:
                    i = zzbgm.zzg(parcel2, readInt);
                    break;
                case 4:
                    str2 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 5:
                    mediaMetadata = (MediaMetadata) zzbgm.zza(parcel2, readInt, MediaMetadata.CREATOR);
                    break;
                case 6:
                    j = zzbgm.zzi(parcel2, readInt);
                    break;
                case 7:
                    list = zzbgm.zzc(parcel2, readInt, MediaTrack.CREATOR);
                    break;
                case 8:
                    textTrackStyle = (TextTrackStyle) zzbgm.zza(parcel2, readInt, TextTrackStyle.CREATOR);
                    break;
                case 9:
                    str3 = zzbgm.zzq(parcel2, readInt);
                    break;
                case 10:
                    list2 = zzbgm.zzc(parcel2, readInt, AdBreakInfo.CREATOR);
                    break;
                case 11:
                    list3 = zzbgm.zzc(parcel2, readInt, AdBreakClipInfo.CREATOR);
                    break;
                case 12:
                    str4 = zzbgm.zzq(parcel2, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel2, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel2, zza);
        return new MediaInfo(str, i, str2, mediaMetadata, j, list, textTrackStyle, str3, list2, list3, str4);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MediaInfo[i];
    }
}
