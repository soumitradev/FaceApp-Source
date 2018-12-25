package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

final class zzam extends zzbr {
    private static final String zza = zzbh.FUNCTION_CALL.toString();
    private static final String zzb = zzbi.FUNCTION_CALL_NAME.toString();
    private static final String zzc = zzbi.ADDITIONAL_PARAMS.toString();
    private final zzan zzd;

    public zzam(zzan zzan) {
        super(zza, zzb);
        this.zzd = zzan;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        String zza = zzgk.zza((zzbt) map.get(zzb));
        Map hashMap = new HashMap();
        zzbt zzbt = (zzbt) map.get(zzc);
        if (zzbt != null) {
            Object zzf = zzgk.zzf(zzbt);
            if (zzf instanceof Map) {
                for (Entry entry : ((Map) zzf).entrySet()) {
                    hashMap.put(entry.getKey().toString(), entry.getValue());
                }
            } else {
                String str = "FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.";
                zzdj.zzb(str);
                return zzgk.zzg();
            }
        }
        try {
            return zzgk.zza(this.zzd.zza(zza, hashMap));
        } catch (Exception e) {
            str = e.getMessage();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zza).length() + 34) + String.valueOf(str).length());
            stringBuilder.append("Custom macro/tag ");
            stringBuilder.append(zza);
            stringBuilder.append(" threw exception ");
            stringBuilder.append(str);
            str = stringBuilder.toString();
        }
    }

    public final boolean zza() {
        return false;
    }
}
