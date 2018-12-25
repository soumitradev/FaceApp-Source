package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.Map;
import java.util.Map.Entry;
import org.catrobat.catroid.common.Constants;

@Hide
public class zzatd extends zzari {
    private static zzatd zza;

    public zzatd(zzark zzark) {
        super(zzark);
    }

    private static String zza(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf((long) ((Integer) obj).intValue());
        }
        if (!(obj instanceof Long)) {
            return obj instanceof Boolean ? String.valueOf(obj) : obj instanceof Throwable ? obj.getClass().getCanonicalName() : "-";
        } else {
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return String.valueOf(obj);
            }
            String str = String.valueOf(obj).charAt(0) == '-' ? "-" : "";
            String valueOf = String.valueOf(Math.abs(l.longValue()));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(Math.round(Math.pow(10.0d, (double) (valueOf.length() - 1))));
            stringBuilder.append("...");
            stringBuilder.append(str);
            stringBuilder.append(Math.round(Math.pow(10.0d, (double) valueOf.length()) - 1.0d));
            return stringBuilder.toString();
        }
    }

    public static zzatd zzb() {
        return zza;
    }

    protected final void zza() {
        synchronized (zzatd.class) {
            zza = this;
        }
    }

    public final synchronized void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        zzbq.zza(str);
        if (i < 0) {
            i = 0;
        }
        if (i >= 9) {
            i = 8;
        }
        char c = zzm().zza() ? 'C' : 'c';
        char charAt = "01VDIWEA?".charAt(i);
        String str2 = zzarj.zza;
        str = zzc(str, zza(obj), zza(obj2), zza(obj3));
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str2).length() + 4) + String.valueOf(str).length());
        stringBuilder.append("3");
        stringBuilder.append(charAt);
        stringBuilder.append(c);
        stringBuilder.append(str2);
        stringBuilder.append(":");
        stringBuilder.append(str);
        String stringBuilder2 = stringBuilder.toString();
        if (stringBuilder2.length() > 1024) {
            stringBuilder2 = stringBuilder2.substring(0, 1024);
        }
        zzath zzm = zzi().zzm();
        if (zzm != null) {
            zzm.zzg().zza(stringBuilder2);
        }
    }

    public final void zza(zzasy zzasy, String str) {
        Object zzasy2 = zzasy != null ? zzasy.toString() : "no hit data";
        String str2 = "Discarding hit. ";
        str = String.valueOf(str);
        zzd(str.length() != 0 ? str2.concat(str) : new String(str2), zzasy2);
    }

    public final void zza(Map<String, String> map, String str) {
        Object stringBuilder;
        if (map != null) {
            StringBuilder stringBuilder2 = new StringBuilder();
            for (Entry entry : map.entrySet()) {
                if (stringBuilder2.length() > 0) {
                    stringBuilder2.append(Constants.REMIX_URL_SEPARATOR);
                }
                stringBuilder2.append((String) entry.getKey());
                stringBuilder2.append('=');
                stringBuilder2.append((String) entry.getValue());
            }
            stringBuilder = stringBuilder2.toString();
        } else {
            stringBuilder = "no hit data";
        }
        String str2 = "Discarding hit. ";
        str = String.valueOf(str);
        zzd(str.length() != 0 ? str2.concat(str) : new String(str2), stringBuilder);
    }
}
