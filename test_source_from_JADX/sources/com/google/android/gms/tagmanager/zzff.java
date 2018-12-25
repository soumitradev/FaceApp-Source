package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzdkj;
import com.google.android.gms.internal.zzdkn;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzff implements zzfh {
    private /* synthetic */ Map zza;
    private /* synthetic */ Map zzb;
    private /* synthetic */ Map zzc;
    private /* synthetic */ Map zzd;

    zzff(zzfc zzfc, Map map, Map map2, Map map3, Map map4) {
        this.zza = map;
        this.zzb = map2;
        this.zzc = map3;
        this.zzd = map4;
    }

    public final void zza(zzdkn zzdkn, Set<zzdkj> set, Set<zzdkj> set2, zzer zzer) {
        List list = (List) this.zza.get(zzdkn);
        this.zzb.get(zzdkn);
        if (list != null) {
            set.addAll(list);
            zzer.zzc();
        }
        List list2 = (List) this.zzc.get(zzdkn);
        this.zzd.get(zzdkn);
        if (list2 != null) {
            set2.addAll(list2);
            zzer.zzd();
        }
    }
}
