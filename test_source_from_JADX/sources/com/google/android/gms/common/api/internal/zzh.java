package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.internal.zzbg;
import java.util.Arrays;

public final class zzh<O extends ApiOptions> {
    private final boolean zza = true;
    private final int zzb;
    private final Api<O> zzc;
    private final O zzd;

    private zzh(Api<O> api) {
        this.zzc = api;
        this.zzd = null;
        this.zzb = System.identityHashCode(this);
    }

    private zzh(Api<O> api, O o) {
        this.zzc = api;
        this.zzd = o;
        this.zzb = Arrays.hashCode(new Object[]{this.zzc, this.zzd});
    }

    public static <O extends ApiOptions> zzh<O> zza(Api<O> api) {
        return new zzh(api);
    }

    public static <O extends ApiOptions> zzh<O> zza(Api<O> api, O o) {
        return new zzh(api, o);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzh)) {
            return false;
        }
        zzh zzh = (zzh) obj;
        return !this.zza && !zzh.zza && zzbg.zza(this.zzc, zzh.zzc) && zzbg.zza(this.zzd, zzh.zzd);
    }

    public final int hashCode() {
        return this.zzb;
    }

    public final String zza() {
        return this.zzc.zzd();
    }
}
