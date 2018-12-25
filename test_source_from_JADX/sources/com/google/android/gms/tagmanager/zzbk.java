package com.google.android.gms.tagmanager;

import android.util.Base64;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzbk extends zzbr {
    private static final String zza = zzbh.ENCODE.toString();
    private static final String zzb = zzbi.ARG0.toString();
    private static final String zzc = zzbi.NO_PADDING.toString();
    private static final String zzd = zzbi.INPUT_FORMAT.toString();
    private static final String zze = zzbi.OUTPUT_FORMAT.toString();

    public zzbk() {
        super(zza, zzb);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        zzbt zzbt = (zzbt) map.get(zzb);
        if (zzbt != null) {
            if (zzbt != zzgk.zzg()) {
                String zza = zzgk.zza(zzbt);
                zzbt zzbt2 = (zzbt) map.get(zzd);
                Object zza2 = zzbt2 == null ? "text" : zzgk.zza(zzbt2);
                zzbt zzbt3 = (zzbt) map.get(zze);
                Object zza3 = zzbt3 == null ? "base16" : zzgk.zza(zzbt3);
                int i = 2;
                zzbt zzbt4 = (zzbt) map.get(zzc);
                if (zzbt4 != null && zzgk.zze(zzbt4).booleanValue()) {
                    i = 3;
                }
                String str;
                try {
                    byte[] bytes;
                    Object zza4;
                    if ("text".equals(zza2)) {
                        bytes = zza.getBytes();
                    } else if ("base16".equals(zza2)) {
                        bytes = zzo.zza(zza);
                    } else if ("base64".equals(zza2)) {
                        bytes = Base64.decode(zza, i);
                    } else if ("base64url".equals(zza2)) {
                        bytes = Base64.decode(zza, i | 8);
                    } else {
                        str = "Encode: unknown input format: ";
                        zza = String.valueOf(zza2);
                        zzdj.zza(zza.length() != 0 ? str.concat(zza) : new String(str));
                        return zzgk.zzg();
                    }
                    if ("base16".equals(zza3)) {
                        zza4 = zzo.zza(bytes);
                    } else if ("base64".equals(zza3)) {
                        zza4 = Base64.encodeToString(bytes, i);
                    } else if ("base64url".equals(zza3)) {
                        zza4 = Base64.encodeToString(bytes, i | 8);
                    } else {
                        str = "Encode: unknown output format: ";
                        zza = String.valueOf(zza3);
                        str = zza.length() != 0 ? str.concat(zza) : new String(str);
                        zzdj.zza(str);
                        return zzgk.zzg();
                    }
                    return zzgk.zza(zza4);
                } catch (IllegalArgumentException e) {
                    str = "Encode: invalid input:";
                }
            }
        }
        return zzgk.zzg();
    }

    public final boolean zza() {
        return true;
    }
}
