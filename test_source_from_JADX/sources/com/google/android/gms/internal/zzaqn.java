package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Hide
public final class zzaqn extends zzi<zzaqn> {
    private Map<Integer, String> zza = new HashMap(4);

    public final String toString() {
        Map hashMap = new HashMap();
        for (Entry entry : this.zza.entrySet()) {
            String valueOf = String.valueOf(entry.getKey());
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 9);
            stringBuilder.append("dimension");
            stringBuilder.append(valueOf);
            hashMap.put(stringBuilder.toString(), entry.getValue());
        }
        return zzi.zza((Object) hashMap);
    }

    public final Map<Integer, String> zza() {
        return Collections.unmodifiableMap(this.zza);
    }

    public final /* synthetic */ void zza(zzi zzi) {
        ((zzaqn) zzi).zza.putAll(this.zza);
    }
}
