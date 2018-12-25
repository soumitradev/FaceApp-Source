package com.google.android.gms.analytics;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import java.util.ArrayList;
import java.util.List;

@Hide
public class zzj<T extends zzj> {
    @Hide
    protected final zzg zza;
    private final zzk zzb;
    private final List<zzh> zzc = new ArrayList();

    protected zzj(zzk zzk, zze zze) {
        zzbq.zza((Object) zzk);
        this.zzb = zzk;
        zzg zzg = new zzg(this, zze);
        zzg.zzj();
        this.zza = zzg;
    }

    @Hide
    protected void zza(zzg zzg) {
    }

    @Hide
    public zzg zzb() {
        zzg zza = this.zza.zza();
        zzb(zza);
        return zza;
    }

    @Hide
    protected final void zzb(zzg zzg) {
        for (zzh zza : this.zzc) {
            zza.zza(this, zzg);
        }
    }

    @Hide
    protected final zzk zzf() {
        return this.zzb;
    }
}
