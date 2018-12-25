package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgm;

@Hide
public final class zzad implements Creator<LaunchOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        boolean z = false;
        String str = null;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 3:
                    str = zzbgm.zzq(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new LaunchOptions(z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new LaunchOptions[i];
    }
}
