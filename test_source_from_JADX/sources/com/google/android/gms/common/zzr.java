package com.google.android.gms.common;

import com.google.android.gms.common.util.zza;
import com.google.android.gms.common.util.zzm;
import io.fabric.sdk.android.services.common.CommonUtils;

final class zzr extends zzp {
    private final String zzc;
    private final zzh zzd;
    private final boolean zze;
    private final boolean zzf;

    private zzr(String str, zzh zzh, boolean z, boolean z2) {
        super(false, null, null);
        this.zzc = str;
        this.zzd = zzh;
        this.zze = z;
        this.zzf = z2;
    }

    final String zzb() {
        String str = this.zzf ? "debug cert rejected" : "not whitelisted";
        String str2 = this.zzc;
        String zzb = zzm.zzb(zza.zza(CommonUtils.SHA1_INSTANCE).digest(this.zzd.zza()));
        boolean z = this.zze;
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 44) + String.valueOf(str2).length()) + String.valueOf(zzb).length());
        stringBuilder.append(str);
        stringBuilder.append(": pkg=");
        stringBuilder.append(str2);
        stringBuilder.append(", sha1=");
        stringBuilder.append(zzb);
        stringBuilder.append(", atk=");
        stringBuilder.append(z);
        stringBuilder.append(", ver=12210278.false");
        return stringBuilder.toString();
    }
}
