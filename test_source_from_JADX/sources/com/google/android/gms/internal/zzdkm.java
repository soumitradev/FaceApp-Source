package com.google.android.gms.internal;

import com.google.android.gms.tagmanager.zzgk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzdkm {
    private final List<zzdkn> zza;
    private final Map<String, List<zzdkj>> zzb;
    private String zzc;
    private int zzd;

    private zzdkm() {
        this.zza = new ArrayList();
        this.zzb = new HashMap();
        this.zzc = "";
        this.zzd = 0;
    }

    public final zzdkl zza() {
        return new zzdkl(this.zza, this.zzb, this.zzc, this.zzd);
    }

    public final zzdkm zza(int i) {
        this.zzd = i;
        return this;
    }

    public final zzdkm zza(zzdkj zzdkj) {
        String zza = zzgk.zza((zzbt) zzdkj.zzb().get(zzbi.INSTANCE_NAME.toString()));
        List list = (List) this.zzb.get(zza);
        if (list == null) {
            list = new ArrayList();
            this.zzb.put(zza, list);
        }
        list.add(zzdkj);
        return this;
    }

    public final zzdkm zza(zzdkn zzdkn) {
        this.zza.add(zzdkn);
        return this;
    }

    public final zzdkm zza(String str) {
        this.zzc = str;
        return this;
    }
}
