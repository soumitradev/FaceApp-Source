package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbz;

public final class zzal implements zzbh {
    private final zzbi zza;
    private boolean zzb = false;

    public zzal(zzbi zzbi) {
        this.zza = zzbi;
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(T t) {
        return zzb(t);
    }

    public final void zza() {
    }

    public final void zza(int i) {
        this.zza.zza(null);
        this.zza.zze.zza(i, this.zzb);
    }

    public final void zza(Bundle bundle) {
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zzb(T t) {
        try {
            this.zza.zzd.zze.zza(t);
            zzba zzba = this.zza.zzd;
            zzb zzb = (zze) zzba.zzb.get(t.zzc());
            zzbq.zza(zzb, "Appropriate Api was not requested.");
            if (zzb.zzs() || !this.zza.zzb.containsKey(t.zzc())) {
                if (zzb instanceof zzbz) {
                    zzb = zzbz.zzi();
                }
                t.zzb(zzb);
                return t;
            }
            t.zzc(new Status(17));
            return t;
        } catch (DeadObjectException e) {
            this.zza.zza(new zzam(this, this));
            return t;
        }
    }

    public final boolean zzb() {
        if (this.zzb) {
            return false;
        }
        if (this.zza.zzd.zzg()) {
            this.zzb = true;
            for (zzdh zza : this.zza.zzd.zzd) {
                zza.zza();
            }
            return false;
        }
        this.zza.zza(null);
        return true;
    }

    public final void zzc() {
        if (this.zzb) {
            this.zzb = false;
            this.zza.zza(new zzan(this, this));
        }
    }

    final void zzd() {
        if (this.zzb) {
            this.zzb = false;
            this.zza.zzd.zze.zza();
            zzb();
        }
    }
}
