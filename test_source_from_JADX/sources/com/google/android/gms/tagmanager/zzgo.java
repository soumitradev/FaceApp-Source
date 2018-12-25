package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbt;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

final class zzgo {
    private static zzea<zzbt> zza(zzea<zzbt> zzea) {
        try {
            return new zzea(zzgk.zza(zza(zzgk.zza((zzbt) zzea.zza()))), zzea.zzb());
        } catch (Throwable e) {
            zzdj.zza("Escape URI: unsupported encoding", e);
            return zzea;
        }
    }

    static zzea<zzbt> zza(zzea<zzbt> zzea, int... iArr) {
        for (int i : iArr) {
            String str;
            if (!(zzgk.zzf((zzbt) zzea.zza()) instanceof String)) {
                str = "Escaping can only be applied to strings.";
            } else if (i != 12) {
                StringBuilder stringBuilder = new StringBuilder(39);
                stringBuilder.append("Unsupported Value Escaping: ");
                stringBuilder.append(i);
                str = stringBuilder.toString();
            } else {
                zzea = zza((zzea) zzea);
            }
            zzdj.zza(str);
        }
        return zzea;
    }

    static String zza(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
    }
}
