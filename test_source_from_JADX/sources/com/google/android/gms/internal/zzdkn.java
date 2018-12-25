package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;

public final class zzdkn {
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

    private zzdkn(List<zzdkj> list, List<zzdkj> list2, List<zzdkj> list3, List<zzdkj> list4, List<zzdkj> list5, List<zzdkj> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
        this.zza = Collections.unmodifiableList(list);
        this.zzb = Collections.unmodifiableList(list2);
        this.zzc = Collections.unmodifiableList(list3);
        this.zzd = Collections.unmodifiableList(list4);
        this.zze = Collections.unmodifiableList(list5);
        this.zzf = Collections.unmodifiableList(list6);
        this.zzg = Collections.unmodifiableList(list7);
        this.zzh = Collections.unmodifiableList(list8);
        this.zzi = Collections.unmodifiableList(list9);
        this.zzj = Collections.unmodifiableList(list10);
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zza);
        String valueOf2 = String.valueOf(this.zzb);
        String valueOf3 = String.valueOf(this.zzc);
        String valueOf4 = String.valueOf(this.zzd);
        String valueOf5 = String.valueOf(this.zze);
        String valueOf6 = String.valueOf(this.zzf);
        StringBuilder stringBuilder = new StringBuilder((((((String.valueOf(valueOf).length() + 102) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()) + String.valueOf(valueOf4).length()) + String.valueOf(valueOf5).length()) + String.valueOf(valueOf6).length());
        stringBuilder.append("Positive predicates: ");
        stringBuilder.append(valueOf);
        stringBuilder.append("  Negative predicates: ");
        stringBuilder.append(valueOf2);
        stringBuilder.append("  Add tags: ");
        stringBuilder.append(valueOf3);
        stringBuilder.append("  Remove tags: ");
        stringBuilder.append(valueOf4);
        stringBuilder.append("  Add macros: ");
        stringBuilder.append(valueOf5);
        stringBuilder.append("  Remove macros: ");
        stringBuilder.append(valueOf6);
        return stringBuilder.toString();
    }

    public final List<zzdkj> zza() {
        return this.zza;
    }

    public final List<zzdkj> zzb() {
        return this.zzb;
    }

    public final List<zzdkj> zzc() {
        return this.zzc;
    }

    public final List<zzdkj> zzd() {
        return this.zzd;
    }

    public final List<zzdkj> zze() {
        return this.zze;
    }

    public final List<zzdkj> zzf() {
        return this.zzf;
    }
}
