package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class zzel extends zzbr {
    private static final String zza = zzbh.REGEX_GROUP.toString();
    private static final String zzb = zzbi.ARG0.toString();
    private static final String zzc = zzbi.ARG1.toString();
    private static final String zzd = zzbi.IGNORE_CASE.toString();
    private static final String zze = zzbi.GROUP.toString();

    public zzel() {
        super(zza, zzb, zzc);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        zzbt zzbt = (zzbt) map.get(zzb);
        zzbt zzbt2 = (zzbt) map.get(zzc);
        if (!(zzbt == null || zzbt == zzgk.zzg() || zzbt2 == null)) {
            if (zzbt2 != zzgk.zzg()) {
                int i = 64;
                if (zzgk.zze((zzbt) map.get(zzd)).booleanValue()) {
                    i = 66;
                }
                int i2 = 1;
                zzbt zzbt3 = (zzbt) map.get(zze);
                if (zzbt3 != null) {
                    Long zzc = zzgk.zzc(zzbt3);
                    if (zzc == zzgk.zzb()) {
                        return zzgk.zzg();
                    }
                    i2 = zzc.intValue();
                    if (i2 < 0) {
                        return zzgk.zzg();
                    }
                }
                try {
                    CharSequence zza = zzgk.zza(zzbt);
                    String zza2 = zzgk.zza(zzbt2);
                    Object obj = null;
                    Matcher matcher = Pattern.compile(zza2, i).matcher(zza);
                    if (matcher.find() && matcher.groupCount() >= i2) {
                        obj = matcher.group(i2);
                    }
                    return obj == null ? zzgk.zzg() : zzgk.zza(obj);
                } catch (PatternSyntaxException e) {
                    return zzgk.zzg();
                }
            }
        }
        return zzgk.zzg();
    }

    public final boolean zza() {
        return true;
    }
}
