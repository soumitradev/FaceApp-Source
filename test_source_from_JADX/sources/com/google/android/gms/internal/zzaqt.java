package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.measurement.AppMeasurement$Param;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzaqt extends zzi<zzaqt> {
    public String zza;
    public boolean zzb;

    public final String toString() {
        Map hashMap = new HashMap();
        hashMap.put("description", this.zza);
        hashMap.put(AppMeasurement$Param.FATAL, Boolean.valueOf(this.zzb));
        return zzi.zza((Object) hashMap);
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqt zzaqt = (zzaqt) zzi;
        if (!TextUtils.isEmpty(this.zza)) {
            zzaqt.zza = this.zza;
        }
        if (this.zzb) {
            zzaqt.zzb = this.zzb;
        }
    }
}
