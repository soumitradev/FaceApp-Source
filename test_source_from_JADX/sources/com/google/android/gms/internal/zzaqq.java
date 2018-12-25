package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzaqq extends zzi<zzaqq> {
    public int zza;
    public int zzb;
    public int zzc;
    public int zzd;
    public int zze;
    private String zzf;

    public final String toString() {
        Map hashMap = new HashMap();
        hashMap.put("language", this.zzf);
        hashMap.put("screenColors", Integer.valueOf(this.zza));
        hashMap.put("screenWidth", Integer.valueOf(this.zzb));
        hashMap.put("screenHeight", Integer.valueOf(this.zzc));
        hashMap.put("viewportWidth", Integer.valueOf(this.zzd));
        hashMap.put("viewportHeight", Integer.valueOf(this.zze));
        return zzi.zza((Object) hashMap);
    }

    public final String zza() {
        return this.zzf;
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqq zzaqq = (zzaqq) zzi;
        if (this.zza != 0) {
            zzaqq.zza = this.zza;
        }
        if (this.zzb != 0) {
            zzaqq.zzb = this.zzb;
        }
        if (this.zzc != 0) {
            zzaqq.zzc = this.zzc;
        }
        if (this.zzd != 0) {
            zzaqq.zzd = this.zzd;
        }
        if (this.zze != 0) {
            zzaqq.zze = this.zze;
        }
        if (!TextUtils.isEmpty(this.zzf)) {
            zzaqq.zzf = this.zzf;
        }
    }

    public final void zza(String str) {
        this.zzf = str;
    }
}
