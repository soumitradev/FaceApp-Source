package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzarn {
    private final long zza = 0;
    private final String zzb;
    private final String zzc;
    private final boolean zzd;
    private long zze;
    private final Map<String, String> zzf;

    public zzarn(long j, String str, String str2, boolean z, long j2, Map<String, String> map) {
        zzbq.zza(str);
        zzbq.zza(str2);
        this.zzb = str;
        this.zzc = str2;
        this.zzd = z;
        this.zze = j2;
        this.zzf = map != null ? new HashMap(map) : Collections.emptyMap();
    }

    public final long zza() {
        return this.zza;
    }

    public final void zza(long j) {
        this.zze = j;
    }

    public final String zzb() {
        return this.zzb;
    }

    public final String zzc() {
        return this.zzc;
    }

    public final boolean zzd() {
        return this.zzd;
    }

    public final long zze() {
        return this.zze;
    }

    public final Map<String, String> zzf() {
        return this.zzf;
    }
}
