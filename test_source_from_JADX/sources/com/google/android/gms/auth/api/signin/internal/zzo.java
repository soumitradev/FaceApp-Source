package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public final class zzo extends zzbgl {
    public static final Creator<zzo> CREATOR = new zzn();
    private int zza;
    private int zzb;
    private Bundle zzc;

    zzo(int i, int i2, Bundle bundle) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = bundle;
    }

    public zzo(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
        this(1, googleSignInOptionsExtension.getExtensionType(), googleSignInOptionsExtension.toBundle());
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb);
        zzbgo.zza(parcel, 3, this.zzc, false);
        zzbgo.zza(parcel, i);
    }

    public final int zza() {
        return this.zzb;
    }
}
