package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Hide
public final class zzasy {
    private final Map<String, String> zza;
    private final List<zzasf> zzb;
    private final long zzc;
    private final long zzd;
    private final int zze;
    private final boolean zzf;
    private final String zzg;

    @Hide
    public zzasy(zzarh zzarh, Map<String, String> map, long j, boolean z) {
        this(zzarh, map, j, z, 0, 0, null);
    }

    @Hide
    public zzasy(zzarh zzarh, Map<String, String> map, long j, boolean z, long j2, int i) {
        this(zzarh, map, j, z, j2, i, null);
    }

    @Hide
    public zzasy(zzarh zzarh, Map<String, String> map, long j, boolean z, long j2, int i, List<zzasf> list) {
        zzbq.zza(zzarh);
        zzbq.zza(map);
        this.zzd = j;
        this.zzf = z;
        this.zzc = j2;
        this.zze = i;
        this.zzb = list != null ? list : Collections.emptyList();
        this.zzg = zza((List) list);
        Map hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            if (zza(entry.getKey())) {
                String zza = zza(zzarh, entry.getKey());
                if (zza != null) {
                    hashMap.put(zza, zzb(zzarh, entry.getValue()));
                }
            }
        }
        for (Entry entry2 : map.entrySet()) {
            if (!zza(entry2.getKey())) {
                String zza2 = zza(zzarh, entry2.getKey());
                if (zza2 != null) {
                    hashMap.put(zza2, zzb(zzarh, entry2.getValue()));
                }
            }
        }
        if (!TextUtils.isEmpty(this.zzg)) {
            zzatt.zza(hashMap, "_v", this.zzg);
            if (this.zzg.equals("ma4.0.0") || this.zzg.equals("ma4.0.1")) {
                hashMap.remove("adid");
            }
        }
        this.zza = Collections.unmodifiableMap(hashMap);
    }

    private static String zza(zzarh zzarh, Object obj) {
        if (obj == null) {
            return null;
        }
        obj = obj.toString();
        if (obj.startsWith("&")) {
            obj = obj.substring(1);
        }
        int length = obj.length();
        if (length > 256) {
            obj = obj.substring(0, 256);
            zzarh.zzc("Hit param name is too long and will be trimmed", Integer.valueOf(length), obj);
        }
        return TextUtils.isEmpty(obj) ? null : obj;
    }

    private final String zza(String str, String str2) {
        zzbq.zza(str);
        zzbq.zzb(str.startsWith("&") ^ 1, "Short param name required");
        str = (String) this.zza.get(str);
        return str != null ? str : str2;
    }

    private static String zza(List<zzasf> list) {
        Object zzb;
        if (list != null) {
            for (zzasf zzasf : list) {
                if ("appendVersion".equals(zzasf.zza())) {
                    zzb = zzasf.zzb();
                    break;
                }
            }
        }
        zzb = null;
        return TextUtils.isEmpty(zzb) ? null : zzb;
    }

    private static boolean zza(Object obj) {
        return obj == null ? false : obj.toString().startsWith("&");
    }

    private static String zzb(zzarh zzarh, Object obj) {
        String obj2 = obj == null ? "" : obj.toString();
        int length = obj2.length();
        if (length <= 8192) {
            return obj2;
        }
        obj2 = obj2.substring(0, 8192);
        zzarh.zzc("Hit param value is too long and will be trimmed", Integer.valueOf(length), obj2);
        return obj2;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ht=");
        stringBuilder.append(this.zzd);
        if (this.zzc != 0) {
            stringBuilder.append(", dbId=");
            stringBuilder.append(this.zzc);
        }
        if (this.zze != 0) {
            stringBuilder.append(", appUID=");
            stringBuilder.append(this.zze);
        }
        List arrayList = new ArrayList(this.zza.keySet());
        Collections.sort(arrayList);
        ArrayList arrayList2 = (ArrayList) arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            String str = (String) obj;
            stringBuilder.append(", ");
            stringBuilder.append(str);
            stringBuilder.append("=");
            stringBuilder.append((String) this.zza.get(str));
        }
        return stringBuilder.toString();
    }

    public final int zza() {
        return this.zze;
    }

    public final Map<String, String> zzb() {
        return this.zza;
    }

    public final long zzc() {
        return this.zzc;
    }

    public final long zzd() {
        return this.zzd;
    }

    public final List<zzasf> zze() {
        return this.zzb;
    }

    public final boolean zzf() {
        return this.zzf;
    }

    public final long zzg() {
        return zzatt.zzb(zza("_s", AppEventsConstants.EVENT_PARAM_VALUE_NO));
    }

    public final String zzh() {
        return zza("_m", "");
    }
}
