package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

final class zzbw extends zzbr {
    private static final String zza = zzbh.HASH.toString();
    private static final String zzb = zzbi.ARG0.toString();
    private static final String zzc = zzbi.ALGORITHM.toString();
    private static final String zzd = zzbi.INPUT_FORMAT.toString();

    public zzbw() {
        super(zza, zzb);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        String valueOf;
        zzbt zzbt = (zzbt) map.get(zzb);
        if (zzbt != null) {
            if (zzbt != zzgk.zzg()) {
                byte[] bytes;
                String zza = zzgk.zza(zzbt);
                zzbt zzbt2 = (zzbt) map.get(zzc);
                String zza2 = zzbt2 == null ? CommonUtils.MD5_INSTANCE : zzgk.zza(zzbt2);
                zzbt zzbt3 = (zzbt) map.get(zzd);
                Object zza3 = zzbt3 == null ? "text" : zzgk.zza(zzbt3);
                if ("text".equals(zza3)) {
                    bytes = zza.getBytes();
                } else if ("base16".equals(zza3)) {
                    bytes = zzo.zza(zza);
                } else {
                    zza = "Hash: unknown input format: ";
                    valueOf = String.valueOf(zza3);
                    valueOf = valueOf.length() != 0 ? zza.concat(valueOf) : new String(zza);
                    zzdj.zza(valueOf);
                    return zzgk.zzg();
                }
                try {
                    MessageDigest instance = MessageDigest.getInstance(zza2);
                    instance.update(bytes);
                    return zzgk.zza(zzo.zza(instance.digest()));
                } catch (NoSuchAlgorithmException e) {
                    valueOf = "Hash: unknown algorithm: ";
                    zza = String.valueOf(zza2);
                    valueOf = zza.length() != 0 ? valueOf.concat(zza) : new String(valueOf);
                }
            }
        }
        return zzgk.zzg();
    }

    public final boolean zza() {
        return true;
    }
}
