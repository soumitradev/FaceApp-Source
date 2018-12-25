package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzaql extends zzi<zzaql> {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;

    public final String toString() {
        Map hashMap = new HashMap();
        hashMap.put("appName", this.zza);
        hashMap.put("appVersion", this.zzb);
        hashMap.put("appId", this.zzc);
        hashMap.put("appInstallerId", this.zzd);
        return zzi.zza((Object) hashMap);
    }

    public final String zza() {
        return this.zza;
    }

    public final void zza(zzaql zzaql) {
        if (!TextUtils.isEmpty(this.zza)) {
            zzaql.zza = this.zza;
        }
        if (!TextUtils.isEmpty(this.zzb)) {
            zzaql.zzb = this.zzb;
        }
        if (!TextUtils.isEmpty(this.zzc)) {
            zzaql.zzc = this.zzc;
        }
        if (!TextUtils.isEmpty(this.zzd)) {
            zzaql.zzd = this.zzd;
        }
    }

    public final void zza(String str) {
        this.zza = str;
    }

    public final String zzb() {
        return this.zzb;
    }

    public final void zzb(String str) {
        this.zzb = str;
    }

    public final String zzc() {
        return this.zzc;
    }

    public final void zzc(String str) {
        this.zzc = str;
    }

    public final String zzd() {
        return this.zzd;
    }

    public final void zzd(String str) {
        this.zzd = str;
    }
}
