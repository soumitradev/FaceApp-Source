package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import java.util.List;

@Hide
public final class zzd implements Creator<ApplicationMetadata> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        String str = null;
        String str2 = str;
        List list = str2;
        List list2 = list;
        String str3 = list2;
        Uri uri = str3;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzbgm.zzq(parcel, readInt);
                    break;
                case 4:
                    list = zzbgm.zzc(parcel, readInt, WebImage.CREATOR);
                    break;
                case 5:
                    list2 = zzbgm.zzac(parcel, readInt);
                    break;
                case 6:
                    str3 = zzbgm.zzq(parcel, readInt);
                    break;
                case 7:
                    uri = (Uri) zzbgm.zza(parcel, readInt, Uri.CREATOR);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new ApplicationMetadata(str, str2, list, list2, str3, uri);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new ApplicationMetadata[i];
    }
}
