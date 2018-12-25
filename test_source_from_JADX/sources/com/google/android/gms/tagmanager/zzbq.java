package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbm;
import com.google.android.gms.internal.zzbn;
import com.google.android.gms.internal.zzbr;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzbq {
    private static void zza(DataLayer dataLayer, zzbn zzbn) {
        for (zzbm zzbm : zzbn.zzc) {
            String str;
            if (zzbm.zza == null) {
                str = "GaExperimentRandom: No key";
            } else {
                Object obj = dataLayer.get(zzbm.zza);
                Long valueOf = !(obj instanceof Number) ? null : Long.valueOf(((Number) obj).longValue());
                long j = zzbm.zzb;
                long j2 = zzbm.zzc;
                if (!zzbm.zzd || valueOf == null || valueOf.longValue() < j || valueOf.longValue() > j2) {
                    if (j <= j2) {
                        obj = Long.valueOf(Math.round((Math.random() * ((double) (j2 - j))) + ((double) j)));
                    } else {
                        str = "GaExperimentRandom: random range invalid";
                    }
                }
                dataLayer.zza(zzbm.zza);
                Map zza = DataLayer.zza(zzbm.zza, obj);
                if (zzbm.zze > 0) {
                    if (zza.containsKey("gtm")) {
                        Object obj2 = zza.get("gtm");
                        if (obj2 instanceof Map) {
                            ((Map) obj2).put("lifetime", Long.valueOf(zzbm.zze));
                        } else {
                            zzdj.zzb("GaExperimentRandom: gtm not a map");
                        }
                    } else {
                        zza.put("gtm", DataLayer.mapOf("lifetime", Long.valueOf(zzbm.zze)));
                    }
                }
                dataLayer.push(zza);
            }
            zzdj.zzb(str);
        }
    }

    public static void zza(DataLayer dataLayer, zzbr zzbr) {
        if (zzbr.zzb == null) {
            zzdj.zzb("supplemental missing experimentSupplemental");
            return;
        }
        for (zzbt zza : zzbr.zzb.zzb) {
            dataLayer.zza(zzgk.zza(zza));
        }
        for (zzbt zzf : zzbr.zzb.zza) {
            Map map;
            Object zzf2 = zzgk.zzf(zzf);
            if (zzf2 instanceof Map) {
                map = (Map) zzf2;
            } else {
                String valueOf = String.valueOf(zzf2);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 36);
                stringBuilder.append("value: ");
                stringBuilder.append(valueOf);
                stringBuilder.append(" is not a map value, ignored.");
                zzdj.zzb(stringBuilder.toString());
                map = null;
            }
            if (map != null) {
                dataLayer.push(map);
            }
        }
        zza(dataLayer, zzbr.zzb);
    }
}
