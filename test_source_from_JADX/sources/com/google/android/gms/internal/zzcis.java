package com.google.android.gms.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import java.util.Iterator;

public final class zzcis {
    final String zza;
    final String zzb;
    final long zzc;
    final long zzd;
    final zzciu zze;
    private String zzf;

    zzcis(zzckj zzckj, String str, String str2, String str3, long j, long j2, Bundle bundle) {
        zzbq.zza(str2);
        zzbq.zza(str3);
        this.zza = str2;
        this.zzb = str3;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzf = str;
        this.zzc = j;
        this.zzd = j2;
        if (this.zzd != 0 && this.zzd > this.zzc) {
            zzckj.zzf().zzaa().zza("Event created with reverse previous/current timestamps. appId", zzcjj.zza(str2));
        }
        this.zze = zza(zzckj, bundle);
    }

    private zzcis(zzckj zzckj, String str, String str2, String str3, long j, long j2, zzciu zzciu) {
        zzbq.zza(str2);
        zzbq.zza(str3);
        zzbq.zza(zzciu);
        this.zza = str2;
        this.zzb = str3;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzf = str;
        this.zzc = j;
        this.zzd = j2;
        if (this.zzd != 0 && this.zzd > this.zzc) {
            zzckj.zzf().zzaa().zza("Event created with reverse previous/current timestamps. appId", zzcjj.zza(str2));
        }
        this.zze = zzciu;
    }

    private static zzciu zza(zzckj zzckj, Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return new zzciu(new Bundle());
        }
        Bundle bundle2 = new Bundle(bundle);
        Iterator it = bundle2.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str == null) {
                zzckj.zzf().zzy().zza("Param name can't be null");
            } else {
                Object zza = zzckj.zzo().zza(str, bundle2.get(str));
                if (zza == null) {
                    zzckj.zzf().zzaa().zza("Param value can't be null", zzckj.zzp().zzb(str));
                } else {
                    zzckj.zzo().zza(bundle2, str, zza);
                }
            }
            it.remove();
        }
        return new zzciu(bundle2);
    }

    public final String toString() {
        String str = this.zza;
        String str2 = this.zzb;
        String valueOf = String.valueOf(this.zze);
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 33) + String.valueOf(str2).length()) + String.valueOf(valueOf).length());
        stringBuilder.append("Event{appId='");
        stringBuilder.append(str);
        stringBuilder.append("', name='");
        stringBuilder.append(str2);
        stringBuilder.append("', params=");
        stringBuilder.append(valueOf);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    final zzcis zza(zzckj zzckj, long j) {
        return new zzcis(zzckj, this.zzf, this.zza, this.zzb, this.zzc, j, this.zze);
    }
}
