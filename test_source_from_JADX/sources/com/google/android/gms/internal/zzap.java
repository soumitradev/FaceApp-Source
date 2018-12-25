package com.google.android.gms.internal;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;
import org.apache.commons.compress.utils.CharsetNames;

public final class zzap {
    private static long zza(String str) {
        try {
            return zza().parse(str).getTime();
        } catch (Throwable e) {
            zzaf.zza(e, "Unable to parse dateStr: %s, falling back to 0", str);
            return 0;
        }
    }

    public static zzc zza(zzp zzp) {
        long j;
        Object obj;
        long j2;
        Object obj2;
        zzp zzp2 = zzp;
        long currentTimeMillis = System.currentTimeMillis();
        Map map = zzp2.zzc;
        String str = (String) map.get("Date");
        long zza = str != null ? zza(str) : 0;
        str = (String) map.get("Cache-Control");
        int i = 0;
        if (str != null) {
            String[] split = str.split(",");
            j = 0;
            obj = null;
            j2 = 0;
            while (i < split.length) {
                String trim = split[i].trim();
                if (!trim.equals("no-cache")) {
                    if (!trim.equals("no-store")) {
                        if (trim.startsWith("max-age=")) {
                            try {
                                j = Long.parseLong(trim.substring(8));
                            } catch (Exception e) {
                            }
                        } else if (trim.startsWith("stale-while-revalidate=")) {
                            j2 = Long.parseLong(trim.substring(23));
                        } else if (trim.equals("must-revalidate") || trim.equals("proxy-revalidate")) {
                            obj = 1;
                        }
                        i++;
                    }
                }
                return null;
            }
            obj2 = 1;
        } else {
            j = 0;
            obj = null;
            j2 = 0;
            obj2 = null;
        }
        str = (String) map.get("Expires");
        long zza2 = str != null ? zza(str) : 0;
        str = (String) map.get("Last-Modified");
        long zza3 = str != null ? zza(str) : 0;
        str = (String) map.get("ETag");
        if (obj2 != null) {
            long j3 = currentTimeMillis + (j * 1000);
            currentTimeMillis = obj != null ? j3 : j3 + (j2 * 1000);
            zza2 = j3;
        } else if (zza <= 0 || zza2 < zza) {
            currentTimeMillis = 0;
            zza2 = currentTimeMillis;
        } else {
            zza2 = currentTimeMillis + (zza2 - zza);
            currentTimeMillis = zza2;
        }
        zzc zzc = new zzc();
        zzc.zza = zzp2.zzb;
        zzc.zzb = str;
        zzc.zzf = zza2;
        zzc.zze = currentTimeMillis;
        zzc.zzc = zza;
        zzc.zzd = zza3;
        zzc.zzg = map;
        zzc.zzh = zzp2.zzd;
        return zzc;
    }

    static String zza(long j) {
        return zza().format(new Date(j));
    }

    public static String zza(Map<String, String> map) {
        String str = CharsetNames.ISO_8859_1;
        String str2 = (String) map.get("Content-Type");
        if (str2 != null) {
            String[] split = str2.split(";");
            for (int i = 1; i < split.length; i++) {
                String[] split2 = split[i].trim().split("=");
                if (split2.length == 2 && split2[0].equals(HttpRequest.PARAM_CHARSET)) {
                    return split2[1];
                }
            }
        }
        return str;
    }

    private static SimpleDateFormat zza() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }

    static Map<String, String> zza(List<zzl> list) {
        Map<String, String> treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (zzl zzl : list) {
            treeMap.put(zzl.zza(), zzl.zzb());
        }
        return treeMap;
    }

    static List<zzl> zzb(Map<String, String> map) {
        List<zzl> arrayList = new ArrayList(map.size());
        for (Entry entry : map.entrySet()) {
            arrayList.add(new zzl((String) entry.getKey(), (String) entry.getValue()));
        }
        return arrayList;
    }
}
