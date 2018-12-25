package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzaqp extends zzi<zzaqp> {
    private final Map<String, Object> zza = new HashMap();

    public final String toString() {
        return zzi.zza((Object) this.zza);
    }

    public final Map<String, Object> zza() {
        return Collections.unmodifiableMap(this.zza);
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqp zzaqp = (zzaqp) zzi;
        zzbq.zza(zzaqp);
        zzaqp.zza.putAll(this.zza);
    }

    public final void zza(String str, String str2) {
        zzbq.zza(str);
        if (str != null && str.startsWith("&")) {
            str = str.substring(1);
        }
        zzbq.zza(str, "Name can not be empty or \"&\"");
        this.zza.put(str, str2);
    }
}
