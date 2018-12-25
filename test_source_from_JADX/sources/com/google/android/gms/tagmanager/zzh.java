package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzh extends zzbr {
    private static final String zza = zzbh.ADWORDS_CLICK_REFERRER.toString();
    private static final String zzb = zzbi.COMPONENT.toString();
    private static final String zzc = zzbi.CONVERSION_ID.toString();
    private final Context zzd;

    public zzh(Context context) {
        super(zza, zzc);
        this.zzd = context;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        zzbt zzbt = (zzbt) map.get(zzc);
        if (zzbt == null) {
            return zzgk.zzg();
        }
        String zza = zzgk.zza(zzbt);
        zzbt zzbt2 = (zzbt) map.get(zzb);
        String zza2 = zzbt2 != null ? zzgk.zza(zzbt2) : null;
        Context context = this.zzd;
        String str = (String) zzcx.zza.get(zza);
        if (str == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            str = sharedPreferences != null ? sharedPreferences.getString(zza, "") : "";
            zzcx.zza.put(zza, str);
        }
        Object zza3 = zzcx.zza(str, zza2);
        return zza3 != null ? zzgk.zza(zza3) : zzgk.zzg();
    }

    public final boolean zza() {
        return true;
    }
}
