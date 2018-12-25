package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.Map;
import org.catrobat.catroid.common.BrickValues;

final class zzej extends zzbr {
    private static final String zza = zzbh.RANDOM.toString();
    private static final String zzb = zzbi.MIN.toString();
    private static final String zzc = zzbi.MAX.toString();

    public zzej() {
        super(zza, new String[0]);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        double doubleValue;
        double doubleValue2;
        zzbt zzbt = (zzbt) map.get(zzb);
        zzbt zzbt2 = (zzbt) map.get(zzc);
        if (!(zzbt == null || zzbt == zzgk.zzg() || zzbt2 == null || zzbt2 == zzgk.zzg())) {
            zzgj zzb = zzgk.zzb(zzbt);
            zzgj zzb2 = zzgk.zzb(zzbt2);
            if (!(zzb == zzgk.zze() || zzb2 == zzgk.zze())) {
                doubleValue = zzb.doubleValue();
                doubleValue2 = zzb2.doubleValue();
                if (doubleValue <= doubleValue2) {
                    return zzgk.zza(Long.valueOf(Math.round((Math.random() * (doubleValue2 - doubleValue)) + doubleValue)));
                }
            }
        }
        doubleValue = BrickValues.SET_COLOR_TO;
        doubleValue2 = 2.147483647E9d;
        return zzgk.zza(Long.valueOf(Math.round((Math.random() * (doubleValue2 - doubleValue)) + doubleValue)));
    }

    public final boolean zza() {
        return false;
    }
}
