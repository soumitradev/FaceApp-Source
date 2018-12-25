package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzaqs extends zzi<zzaqs> {
    private String zza;
    private String zzb;
    private String zzc;
    private long zzd;

    public final String toString() {
        Map hashMap = new HashMap();
        hashMap.put("category", this.zza);
        hashMap.put(NativeProtocol.WEB_DIALOG_ACTION, this.zzb);
        hashMap.put("label", this.zzc);
        hashMap.put(FirebaseAnalytics$Param.VALUE, Long.valueOf(this.zzd));
        return zzi.zza((Object) hashMap);
    }

    public final String zza() {
        return this.zza;
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqs zzaqs = (zzaqs) zzi;
        if (!TextUtils.isEmpty(this.zza)) {
            zzaqs.zza = this.zza;
        }
        if (!TextUtils.isEmpty(this.zzb)) {
            zzaqs.zzb = this.zzb;
        }
        if (!TextUtils.isEmpty(this.zzc)) {
            zzaqs.zzc = this.zzc;
        }
        if (this.zzd != 0) {
            zzaqs.zzd = this.zzd;
        }
    }

    public final String zzb() {
        return this.zzb;
    }

    public final String zzc() {
        return this.zzc;
    }

    public final long zzd() {
        return this.zzd;
    }
}
