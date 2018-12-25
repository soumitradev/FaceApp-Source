package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;
import java.util.List;

@Hide
public final class zzag implements Creator<MediaMetadata> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        List list = null;
        Bundle bundle = null;
        int i = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    list = zzbgm.zzc(parcel, readInt, WebImage.CREATOR);
                    break;
                case 3:
                    bundle = zzbgm.zzs(parcel, readInt);
                    break;
                case 4:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new MediaMetadata(list, bundle, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new MediaMetadata[i];
    }
}
