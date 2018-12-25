package com.google.android.gms.tagmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    private static String[] zza = "gtm.lifetime".toString().split("\\.");
    private static final Pattern zzb = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final ConcurrentHashMap<zzb, Integer> zzc;
    private final Map<String, Object> zzd;
    private final ReentrantLock zze;
    private final LinkedList<Map<String, Object>> zzf;
    private final zzc zzg;
    private final CountDownLatch zzh;

    static final class zza {
        public final String zza;
        public final Object zzb;

        zza(String str, Object obj) {
            this.zza = str;
            this.zzb = obj;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            return this.zza.equals(zza.zza) && this.zzb.equals(zza.zzb);
        }

        public final int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.zza.hashCode()), Integer.valueOf(this.zzb.hashCode())});
        }

        public final String toString() {
            String str = this.zza;
            String obj = this.zzb.toString();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 13) + String.valueOf(obj).length());
            stringBuilder.append("Key: ");
            stringBuilder.append(str);
            stringBuilder.append(" value: ");
            stringBuilder.append(obj);
            return stringBuilder.toString();
        }
    }

    interface zzb {
        void zza(Map<String, Object> map);
    }

    interface zzc {
        void zza(zzaq zzaq);

        void zza(String str);

        void zza(List<zza> list, long j);
    }

    DataLayer() {
        this(new zzao());
    }

    DataLayer(zzc zzc) {
        this.zzg = zzc;
        this.zzc = new ConcurrentHashMap();
        this.zzd = new HashMap();
        this.zze = new ReentrantLock();
        this.zzf = new LinkedList();
        this.zzh = new CountDownLatch(1);
        this.zzg.zza(new zzap(this));
    }

    public static List<Object> listOf(Object... objArr) {
        List<Object> arrayList = new ArrayList();
        for (Object add : objArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static Map<String, Object> mapOf(Object... objArr) {
        if (objArr.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        Map<String, Object> hashMap = new HashMap();
        int i = 0;
        while (i < objArr.length) {
            if (objArr[i] instanceof String) {
                hashMap.put((String) objArr[i], objArr[i + 1]);
                i += 2;
            } else {
                String valueOf = String.valueOf(objArr[i]);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 21);
                stringBuilder.append("key is not a string: ");
                stringBuilder.append(valueOf);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        return hashMap;
    }

    static Map<String, Object> zza(String str, Object obj) {
        Map hashMap = new HashMap();
        String[] split = str.toString().split("\\.");
        int i = 0;
        Map map = hashMap;
        while (i < split.length - 1) {
            HashMap hashMap2 = new HashMap();
            map.put(split[i], hashMap2);
            i++;
            Object obj2 = hashMap2;
        }
        map.put(split[split.length - 1], obj);
        return hashMap;
    }

    private final void zza(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof List) {
                if (!(list2.get(i) instanceof List)) {
                    list2.set(i, new ArrayList());
                }
                zza((List) obj, (List) list2.get(i));
            } else if (obj instanceof Map) {
                if (!(list2.get(i) instanceof Map)) {
                    list2.set(i, new HashMap());
                }
                zza((Map) obj, (Map) list2.get(i));
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i, obj);
            }
        }
    }

    private final void zza(Map<String, Object> map) {
        this.zze.lock();
        try {
            this.zzf.offer(map);
            if (this.zze.getHoldCount() == 1) {
                int i = 0;
                do {
                    Map map2 = (Map) this.zzf.poll();
                    if (map2 != null) {
                        synchronized (this.zzd) {
                            for (String str : map2.keySet()) {
                                zza(zza(str, map2.get(str)), this.zzd);
                            }
                        }
                        for (zzb zza : this.zzc.keySet()) {
                            zza.zza(map2);
                        }
                        i++;
                    }
                } while (i <= 500);
                this.zzf.clear();
                throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
            }
            Object zzb = zzb((Map) map);
            Long zzb2 = zzb == null ? null : zzb(zzb.toString());
            if (zzb2 != null) {
                Object arrayList = new ArrayList();
                zza(map, "", arrayList);
                this.zzg.zza(arrayList, zzb2.longValue());
            }
            this.zze.unlock();
        } catch (Throwable th) {
            this.zze.unlock();
        }
    }

    private final void zza(Map<String, Object> map, String str, Collection<zza> collection) {
        for (Entry entry : map.entrySet()) {
            String str2 = str.length() == 0 ? "" : ".";
            String str3 = (String) entry.getKey();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + String.valueOf(str2).length()) + String.valueOf(str3).length());
            stringBuilder.append(str);
            stringBuilder.append(str2);
            stringBuilder.append(str3);
            str2 = stringBuilder.toString();
            if (entry.getValue() instanceof Map) {
                zza((Map) entry.getValue(), str2, collection);
            } else if (!str2.equals("gtm.lifetime")) {
                collection.add(new zza(str2, entry.getValue()));
            }
        }
    }

    private final void zza(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                zza((List) obj, (List) map2.get(str));
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                zza((Map) obj, (Map) map2.get(str));
            } else {
                map2.put(str, obj);
            }
        }
    }

    private static Long zzb(String str) {
        Matcher matcher = zzb.matcher(str);
        String str2;
        if (matcher.matches()) {
            long parseLong;
            try {
                parseLong = Long.parseLong(matcher.group(1));
            } catch (NumberFormatException e) {
                String str3 = "illegal number in _lifetime value: ";
                String valueOf = String.valueOf(str);
                zzdj.zzb(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                parseLong = 0;
            }
            if (parseLong <= 0) {
                str2 = "non-positive _lifetime: ";
                str = String.valueOf(str);
                zzdj.zzc(str.length() != 0 ? str2.concat(str) : new String(str2));
                return null;
            }
            str2 = matcher.group(2);
            if (str2.length() == 0) {
                return Long.valueOf(parseLong);
            }
            char charAt = str2.charAt(0);
            if (charAt != 'd') {
                if (charAt == 'h') {
                    parseLong = (parseLong * 1000) * 60;
                } else if (charAt == 'm') {
                    parseLong *= 1000;
                } else if (charAt != 's') {
                    str2 = "unknown units in _lifetime: ";
                    str = String.valueOf(str);
                    zzdj.zzb(str.length() != 0 ? str2.concat(str) : new String(str2));
                    return null;
                } else {
                    parseLong *= 1000;
                }
                parseLong *= 60;
            } else {
                parseLong = (((parseLong * 1000) * 60) * 60) * 24;
            }
            return Long.valueOf(parseLong);
        }
        str2 = "unknown _lifetime: ";
        str = String.valueOf(str);
        zzdj.zzc(str.length() != 0 ? str2.concat(str) : new String(str2));
        return null;
    }

    private static Object zzb(Map<String, Object> map) {
        Object obj;
        for (Object obj2 : zza) {
            if (!(obj instanceof Map)) {
                return null;
            }
            obj = ((Map) obj).get(obj2);
        }
        return obj;
    }

    public Object get(String str) {
        synchronized (this.zzd) {
            Object obj = this.zzd;
            String[] split = str.split("\\.");
            int length = split.length;
            int i = 0;
            while (i < length) {
                Object obj2 = split[i];
                if (obj instanceof Map) {
                    obj = ((Map) obj).get(obj2);
                    if (obj == null) {
                        return null;
                    }
                    i++;
                } else {
                    return null;
                }
            }
            return obj;
        }
    }

    public void push(String str, Object obj) {
        push(zza(str, obj));
    }

    public void push(Map<String, Object> map) {
        try {
            this.zzh.await();
        } catch (InterruptedException e) {
            zzdj.zzb("DataLayer.push: unexpected InterruptedException");
        }
        zza((Map) map);
    }

    public void pushEvent(String str, Map<String, Object> map) {
        Map hashMap = new HashMap(map);
        hashMap.put("event", str);
        push(hashMap);
    }

    public String toString() {
        String stringBuilder;
        synchronized (this.zzd) {
            StringBuilder stringBuilder2 = new StringBuilder();
            for (Entry entry : this.zzd.entrySet()) {
                stringBuilder2.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", new Object[]{entry.getKey(), entry.getValue()}));
            }
            stringBuilder = stringBuilder2.toString();
        }
        return stringBuilder;
    }

    final void zza(zzb zzb) {
        this.zzc.put(zzb, Integer.valueOf(0));
    }

    final void zza(String str) {
        push(str, null);
        this.zzg.zza(str);
    }
}
