package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class zzbhp {
    protected static <O, I> I zza(zzbhq<I, O> zzbhq, Object obj) {
        return zzbhq.zzk != null ? zzbhq.zza(obj) : obj;
    }

    private static void zza(StringBuilder stringBuilder, zzbhq zzbhq, Object obj) {
        String zzbhp;
        if (zzbhq.zza == 11) {
            zzbhp = ((zzbhp) zzbhq.zzg.cast(obj)).toString();
        } else if (zzbhq.zza == 7) {
            stringBuilder.append("\"");
            stringBuilder.append(zzq.zza((String) obj));
            zzbhp = "\"";
        } else {
            stringBuilder.append(obj);
            return;
        }
        stringBuilder.append(zzbhp);
    }

    private static void zza(StringBuilder stringBuilder, zzbhq zzbhq, ArrayList<Object> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                zza(stringBuilder, zzbhq, obj);
            }
        }
        stringBuilder.append("]");
    }

    public String toString() {
        Map zza = zza();
        StringBuilder stringBuilder = new StringBuilder(100);
        for (String str : zza.keySet()) {
            String str2;
            zzbhq zzbhq = (zzbhq) zza.get(str2);
            if (zza(zzbhq)) {
                Object zza2 = zza(zzbhq, zzb(zzbhq));
                stringBuilder.append(stringBuilder.length() == 0 ? "{" : ",");
                stringBuilder.append("\"");
                stringBuilder.append(str2);
                stringBuilder.append("\":");
                if (zza2 == null) {
                    str2 = "null";
                } else {
                    switch (zzbhq.zzc) {
                        case 8:
                            stringBuilder.append("\"");
                            str2 = zzc.zza((byte[]) zza2);
                            break;
                        case 9:
                            stringBuilder.append("\"");
                            str2 = zzc.zzb((byte[]) zza2);
                            break;
                        case 10:
                            zzr.zza(stringBuilder, (HashMap) zza2);
                            continue;
                        default:
                            if (!zzbhq.zzb) {
                                zza(stringBuilder, zzbhq, zza2);
                                break;
                            }
                            zza(stringBuilder, zzbhq, (ArrayList) zza2);
                            continue;
                    }
                    stringBuilder.append(str2);
                    str2 = "\"";
                }
                stringBuilder.append(str2);
            }
        }
        stringBuilder.append(stringBuilder.length() > 0 ? "}" : "{}");
        return stringBuilder.toString();
    }

    protected abstract Object zza(String str);

    public abstract Map<String, zzbhq<?, ?>> zza();

    protected boolean zza(zzbhq zzbhq) {
        if (zzbhq.zzc != 11) {
            return zzb(zzbhq.zze);
        }
        if (zzbhq.zzd) {
            String str = zzbhq.zze;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        str = zzbhq.zze;
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected Object zzb(zzbhq zzbhq) {
        String str = zzbhq.zze;
        if (zzbhq.zzg == null) {
            return zza(zzbhq.zze);
        }
        zza(zzbhq.zze);
        zzbq.zza(true, "Concrete field shouldn't be value object: %s", new Object[]{zzbhq.zze});
        boolean z = zzbhq.zzd;
        try {
            char toUpperCase = Character.toUpperCase(str.charAt(0));
            str = str.substring(1);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 4);
            stringBuilder.append("get");
            stringBuilder.append(toUpperCase);
            stringBuilder.append(str);
            return getClass().getMethod(stringBuilder.toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract boolean zzb(String str);
}
