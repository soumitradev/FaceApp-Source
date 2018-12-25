package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class SignInConfiguration extends zzbgl implements ReflectedParcelable {
    public static final Creator<SignInConfiguration> CREATOR = new zzy();
    private final String zza;
    private GoogleSignInOptions zzb;

    public SignInConfiguration(String str, GoogleSignInOptions googleSignInOptions) {
        this.zza = zzbq.zza(str);
        this.zzb = googleSignInOptions;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r4) {
        /*
        r3 = this;
        r0 = 0;
        if (r4 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r4 = (com.google.android.gms.auth.api.signin.internal.SignInConfiguration) r4;	 Catch:{ ClassCastException -> 0x0026 }
        r1 = r3.zza;	 Catch:{ ClassCastException -> 0x0026 }
        r2 = r4.zza;	 Catch:{ ClassCastException -> 0x0026 }
        r1 = r1.equals(r2);	 Catch:{ ClassCastException -> 0x0026 }
        if (r1 == 0) goto L_0x0025;
    L_0x0010:
        r1 = r3.zzb;	 Catch:{ ClassCastException -> 0x0026 }
        if (r1 != 0) goto L_0x0019;
    L_0x0014:
        r4 = r4.zzb;	 Catch:{ ClassCastException -> 0x0026 }
        if (r4 != 0) goto L_0x0025;
    L_0x0018:
        goto L_0x0023;
    L_0x0019:
        r1 = r3.zzb;	 Catch:{ ClassCastException -> 0x0026 }
        r4 = r4.zzb;	 Catch:{ ClassCastException -> 0x0026 }
        r4 = r1.equals(r4);	 Catch:{ ClassCastException -> 0x0026 }
        if (r4 == 0) goto L_0x0025;
    L_0x0023:
        r4 = 1;
        return r4;
    L_0x0025:
        return r0;
    L_0x0026:
        r4 = move-exception;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.internal.SignInConfiguration.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        return new zzq().zza(this.zza).zza(this.zzb).zza();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 5, this.zzb, i, false);
        zzbgo.zza(parcel, zza);
    }

    public final GoogleSignInOptions zza() {
        return this.zzb;
    }
}
