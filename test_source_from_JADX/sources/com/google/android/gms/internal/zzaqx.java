package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzaqx extends zzi<zzaqx> {
    public String zza;
    public long zzb;
    public String zzc;
    public String zzd;

    public final String toString() {
        Map hashMap = new HashMap();
        hashMap.put("variableName", this.zza);
        hashMap.put("timeInMillis", Long.valueOf(this.zzb));
        hashMap.put("category", this.zzc);
        hashMap.put("label", this.zzd);
        return zzi.zza((Object) hashMap);
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqx zzaqx = (zzaqx) zzi;
        if (!TextUtils.isEmpty(this.zza)) {
            zzaqx.zza = this.zza;
        }
        if (this.zzb != 0) {
            zzaqx.zzb = this.zzb;
        }
        if (!TextUtils.isEmpty(this.zzc)) {
            zzaqx.zzc = this.zzc;
        }
        if (!TextUtils.isEmpty(this.zzd)) {
            zzaqx.zzd = this.zzd;
        }
    }
}
