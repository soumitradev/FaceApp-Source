package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.internal.Hide;

public final class zzaxp extends zzbgl {
    @Hide
    public static final Creator<zzaxp> CREATOR = new zzaxq();
    private final Credential zza;

    public zzaxp(Credential credential) {
        this.zza = credential;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza, i, false);
        zzbgo.zza(parcel, zza);
    }
}
