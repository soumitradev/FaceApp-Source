package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.List;

public final class zzbi {
    private final List<String> zza;
    private final Object zzb;

    private zzbi(Object obj) {
        this.zzb = zzbq.zza(obj);
        this.zza = new ArrayList();
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append(this.zzb.getClass().getSimpleName());
        stringBuilder.append('{');
        int size = this.zza.size();
        for (int i = 0; i < size; i++) {
            stringBuilder.append((String) this.zza.get(i));
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public final zzbi zza(String str, Object obj) {
        List list = this.zza;
        str = (String) zzbq.zza(str);
        String valueOf = String.valueOf(obj);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(valueOf).length());
        stringBuilder.append(str);
        stringBuilder.append("=");
        stringBuilder.append(valueOf);
        list.add(stringBuilder.toString());
        return this;
    }
}
