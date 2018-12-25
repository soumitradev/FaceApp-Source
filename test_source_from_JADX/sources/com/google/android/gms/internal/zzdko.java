package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;

public final class zzdko {
    private final List<zzdkj> zza;
    private final List<zzdkj> zzb;
    private final List<zzdkj> zzc;
    private final List<zzdkj> zzd;
    private final List<zzdkj> zze;
    private final List<zzdkj> zzf;
    private final List<String> zzg;
    private final List<String> zzh;
    private final List<String> zzi;
    private final List<String> zzj;

    private zzdko() {
        this.zza = new ArrayList();
        this.zzb = new ArrayList();
        this.zzc = new ArrayList();
        this.zzd = new ArrayList();
        this.zze = new ArrayList();
        this.zzf = new ArrayList();
        this.zzg = new ArrayList();
        this.zzh = new ArrayList();
        this.zzi = new ArrayList();
        this.zzj = new ArrayList();
    }

    public final zzdkn zza() {
        return new zzdkn(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, this.zzh, this.zzi, this.zzj);
    }

    public final zzdko zza(zzdkj zzdkj) {
        this.zza.add(zzdkj);
        return this;
    }

    public final zzdko zza(String str) {
        this.zzi.add(str);
        return this;
    }

    public final zzdko zzb(zzdkj zzdkj) {
        this.zzb.add(zzdkj);
        return this;
    }

    public final zzdko zzb(String str) {
        this.zzj.add(str);
        return this;
    }

    public final zzdko zzc(zzdkj zzdkj) {
        this.zzc.add(zzdkj);
        return this;
    }

    public final zzdko zzc(String str) {
        this.zzg.add(str);
        return this;
    }

    public final zzdko zzd(zzdkj zzdkj) {
        this.zzd.add(zzdkj);
        return this;
    }

    public final zzdko zzd(String str) {
        this.zzh.add(str);
        return this;
    }

    public final zzdko zze(zzdkj zzdkj) {
        this.zze.add(zzdkj);
        return this;
    }

    public final zzdko zzf(zzdkj zzdkj) {
        this.zzf.add(zzdkj);
        return this;
    }
}
