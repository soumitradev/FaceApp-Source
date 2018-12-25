package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.common.BrickValues;

@Hide
public final class zzaqu extends zzi<zzaqu> {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private boolean zze;
    private String zzf;
    private boolean zzg;
    private double zzh;

    public final String toString() {
        Map hashMap = new HashMap();
        hashMap.put("hitType", this.zza);
        hashMap.put("clientId", this.zzb);
        hashMap.put("userId", this.zzc);
        hashMap.put("androidAdId", this.zzd);
        hashMap.put("AdTargetingEnabled", Boolean.valueOf(this.zze));
        hashMap.put("sessionControl", this.zzf);
        hashMap.put("nonInteraction", Boolean.valueOf(this.zzg));
        hashMap.put("sampleRate", Double.valueOf(this.zzh));
        return zzi.zza((Object) hashMap);
    }

    public final String zza() {
        return this.zza;
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqu zzaqu = (zzaqu) zzi;
        if (!TextUtils.isEmpty(this.zza)) {
            zzaqu.zza = this.zza;
        }
        if (!TextUtils.isEmpty(this.zzb)) {
            zzaqu.zzb = this.zzb;
        }
        if (!TextUtils.isEmpty(this.zzc)) {
            zzaqu.zzc = this.zzc;
        }
        if (!TextUtils.isEmpty(this.zzd)) {
            zzaqu.zzd = this.zzd;
        }
        boolean z = true;
        if (this.zze) {
            zzaqu.zze = true;
        }
        if (!TextUtils.isEmpty(this.zzf)) {
            zzaqu.zzf = this.zzf;
        }
        if (this.zzg) {
            zzaqu.zzg = this.zzg;
        }
        if (this.zzh != BrickValues.SET_COLOR_TO) {
            double d = this.zzh;
            if (d < BrickValues.SET_COLOR_TO || d > 100.0d) {
                z = false;
            }
            zzbq.zzb(z, "Sample rate must be between 0% and 100%");
            zzaqu.zzh = d;
        }
    }

    public final void zza(String str) {
        this.zza = str;
    }

    public final void zza(boolean z) {
        this.zze = z;
    }

    public final String zzb() {
        return this.zzb;
    }

    public final void zzb(String str) {
        this.zzb = str;
    }

    public final void zzb(boolean z) {
        this.zzg = true;
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

    public final boolean zze() {
        return this.zze;
    }

    public final String zzf() {
        return this.zzf;
    }

    public final boolean zzg() {
        return this.zzg;
    }

    public final double zzh() {
        return this.zzh;
    }
}
