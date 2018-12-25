package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public final class zzp {
    public final int zza;
    public final byte[] zzb;
    public final Map<String, String> zzc;
    public final List<zzl> zzd;
    public final boolean zze;
    private long zzf;

    private zzp(int i, byte[] bArr, Map<String, String> map, List<zzl> list, boolean z, long j) {
        this.zza = i;
        this.zzb = bArr;
        this.zzc = map;
        this.zzd = list == null ? null : Collections.unmodifiableList(list);
        this.zze = z;
        this.zzf = j;
    }

    @Deprecated
    public zzp(int i, byte[] bArr, Map<String, String> map, boolean z, long j) {
        this(i, bArr, map, zza((Map) map), z, j);
    }

    public zzp(int i, byte[] bArr, boolean z, long j, List<zzl> list) {
        this(i, bArr, zza((List) list), list, z, j);
    }

    @Deprecated
    public zzp(byte[] bArr, Map<String, String> map) {
        this(200, bArr, (Map) map, false, 0);
    }

    private static List<zzl> zza(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return Collections.emptyList();
        }
        List<zzl> arrayList = new ArrayList(map.size());
        for (Entry entry : map.entrySet()) {
            arrayList.add(new zzl((String) entry.getKey(), (String) entry.getValue()));
        }
        return arrayList;
    }

    private static Map<String, String> zza(List<zzl> list) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (zzl zzl : list) {
            treeMap.put(zzl.zza(), zzl.zzb());
        }
        return treeMap;
    }
}
