package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

@Hide
public final class zzn extends zzbgl {
    public static final Creator<zzn> CREATOR = new zzo();
    private final String zza;
    private final zzh zzb;
    private final boolean zzc;

    zzn(String str, IBinder iBinder, boolean z) {
        this.zza = str;
        this.zzb = zza(iBinder);
        this.zzc = z;
    }

    zzn(String str, zzh zzh, boolean z) {
        this.zza = str;
        this.zzb = zzh;
        this.zzc = z;
    }

    private static zzh zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            IObjectWrapper zzb = zzau.zza(iBinder).zzb();
            byte[] bArr = zzb == null ? null : (byte[]) com.google.android.gms.dynamic.zzn.zza(zzb);
            if (bArr != null) {
                return new zzi(bArr);
            }
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
            return null;
        } catch (Throwable e) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", e);
            return null;
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder;
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza, false);
        if (this.zzb == null) {
            Log.w("GoogleCertificatesQuery", "certificate binder is null");
            iBinder = null;
        } else {
            iBinder = this.zzb.asBinder();
        }
        zzbgo.zza(parcel, 2, iBinder, false);
        zzbgo.zza(parcel, 3, this.zzc);
        zzbgo.zza(parcel, i);
    }
}
