package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzdkj;
import com.google.android.gms.internal.zzdkn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzfj {
    private final Set<zzdkn> zza = new HashSet();
    private final Map<zzdkn, List<zzdkj>> zzb = new HashMap();
    private final Map<zzdkn, List<zzdkj>> zzc = new HashMap();
    private final Map<zzdkn, List<String>> zzd = new HashMap();
    private final Map<zzdkn, List<String>> zze = new HashMap();
    private zzdkj zzf;

    public final Set<zzdkn> zza() {
        return this.zza;
    }

    public final void zza(zzdkj zzdkj) {
        this.zzf = zzdkj;
    }

    public final void zza(zzdkn zzdkn) {
        this.zza.add(zzdkn);
    }

    public final void zza(zzdkn zzdkn, zzdkj zzdkj) {
        List list = (List) this.zzb.get(zzdkn);
        if (list == null) {
            list = new ArrayList();
            this.zzb.put(zzdkn, list);
        }
        list.add(zzdkj);
    }

    public final void zza(zzdkn zzdkn, String str) {
        List list = (List) this.zzd.get(zzdkn);
        if (list == null) {
            list = new ArrayList();
            this.zzd.put(zzdkn, list);
        }
        list.add(str);
    }

    public final Map<zzdkn, List<zzdkj>> zzb() {
        return this.zzb;
    }

    public final void zzb(zzdkn zzdkn, zzdkj zzdkj) {
        List list = (List) this.zzc.get(zzdkn);
        if (list == null) {
            list = new ArrayList();
            this.zzc.put(zzdkn, list);
        }
        list.add(zzdkj);
    }

    public final void zzb(zzdkn zzdkn, String str) {
        List list = (List) this.zze.get(zzdkn);
        if (list == null) {
            list = new ArrayList();
            this.zze.put(zzdkn, list);
        }
        list.add(str);
    }

    public final Map<zzdkn, List<String>> zzc() {
        return this.zzd;
    }

    public final Map<zzdkn, List<String>> zzd() {
        return this.zze;
    }

    public final Map<zzdkn, List<zzdkj>> zze() {
        return this.zzc;
    }

    public final zzdkj zzf() {
        return this.zzf;
    }
}
