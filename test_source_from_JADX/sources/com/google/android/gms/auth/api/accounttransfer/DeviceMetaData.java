package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public class DeviceMetaData extends zzbgl {
    @Hide
    public static final Creator<DeviceMetaData> CREATOR = new zzv();
    @Hide
    private int zza;
    @Hide
    private boolean zzb;
    @Hide
    private long zzc;
    @Hide
    private final boolean zzd;

    @Hide
    DeviceMetaData(int i, boolean z, long j, boolean z2) {
        this.zza = i;
        this.zzb = z;
        this.zzc = j;
        this.zzd = z2;
    }

    public long getMinAgeOfLockScreen() {
        return this.zzc;
    }

    public boolean isChallengeAllowed() {
        return this.zzd;
    }

    public boolean isLockScreenSolved() {
        return this.zzb;
    }

    @Hide
    public void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, isLockScreenSolved());
        zzbgo.zza(parcel, 3, getMinAgeOfLockScreen());
        zzbgo.zza(parcel, 4, isChallengeAllowed());
        zzbgo.zza(parcel, i);
    }
}
