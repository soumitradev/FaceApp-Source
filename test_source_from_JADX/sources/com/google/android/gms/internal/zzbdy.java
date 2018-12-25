package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.common.internal.Hide;
import org.catrobat.catroid.common.BrickValues;

@Hide
public final class zzbdy implements Creator<zzbdx> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zza = zzbgm.zza(parcel);
        double d = BrickValues.SET_COLOR_TO;
        ApplicationMetadata applicationMetadata = null;
        boolean z = false;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zza) {
            int readInt = parcel.readInt();
            switch (SupportMenu.USER_MASK & readInt) {
                case 2:
                    d = zzbgm.zzn(parcel, readInt);
                    break;
                case 3:
                    z = zzbgm.zzc(parcel, readInt);
                    break;
                case 4:
                    i = zzbgm.zzg(parcel, readInt);
                    break;
                case 5:
                    applicationMetadata = (ApplicationMetadata) zzbgm.zza(parcel, readInt, ApplicationMetadata.CREATOR);
                    break;
                case 6:
                    i2 = zzbgm.zzg(parcel, readInt);
                    break;
                default:
                    zzbgm.zzb(parcel, readInt);
                    break;
            }
        }
        zzbgm.zzaf(parcel, zza);
        return new zzbdx(d, z, i, applicationMetadata, i2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbdx[i];
    }
}
