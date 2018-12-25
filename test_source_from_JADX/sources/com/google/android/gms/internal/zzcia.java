package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import java.util.Map;

public final class zzcia extends zzclh {
    private final Map<String, Long> zza = new ArrayMap();
    private final Map<String, Integer> zzb = new ArrayMap();
    private long zzc;

    public zzcia(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final void zza(long j, zzclz zzclz) {
        if (zzclz == null) {
            zzt().zzae().zza("Not logging ad exposure. No active activity");
        } else if (j < 1000) {
            zzt().zzae().zza("Not logging ad exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("_xt", j);
            zzcma.zza(zzclz, bundle, true);
            zzf().zza("am", "_xa", bundle);
        }
    }

    @WorkerThread
    private final void zza(String str, long j) {
        zzc();
        zzbq.zza(str);
        if (this.zzb.isEmpty()) {
            this.zzc = j;
        }
        Integer num = (Integer) this.zzb.get(str);
        if (num != null) {
            this.zzb.put(str, Integer.valueOf(num.intValue() + 1));
        } else if (this.zzb.size() >= 100) {
            zzt().zzaa().zza("Too many ads visible");
        } else {
            this.zzb.put(str, Integer.valueOf(1));
            this.zza.put(str, Long.valueOf(j));
        }
    }

    @WorkerThread
    private final void zza(String str, long j, zzclz zzclz) {
        if (zzclz == null) {
            zzt().zzae().zza("Not logging ad unit exposure. No active activity");
        } else if (j < 1000) {
            zzt().zzae().zza("Not logging ad unit exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("_ai", str);
            bundle.putLong("_xt", j);
            zzcma.zza(zzclz, bundle, true);
            zzf().zza("am", "_xu", bundle);
        }
    }

    @WorkerThread
    private final void zzb(long j) {
        for (String put : this.zza.keySet()) {
            this.zza.put(put, Long.valueOf(j));
        }
        if (!this.zza.isEmpty()) {
            this.zzc = j;
        }
    }

    @WorkerThread
    private final void zzb(String str, long j) {
        zzc();
        zzbq.zza(str);
        Integer num = (Integer) this.zzb.get(str);
        if (num != null) {
            zzclz zzy = zzj().zzy();
            int intValue = num.intValue() - 1;
            if (intValue == 0) {
                this.zzb.remove(str);
                Long l = (Long) this.zza.get(str);
                if (l == null) {
                    zzt().zzy().zza("First ad unit exposure time was never set");
                } else {
                    long longValue = j - l.longValue();
                    this.zza.remove(str);
                    zza(str, longValue, zzy);
                }
                if (this.zzb.isEmpty()) {
                    if (this.zzc == 0) {
                        zzt().zzy().zza("First ad exposure time was never set");
                        return;
                    } else {
                        zza(j - this.zzc, zzy);
                        this.zzc = 0;
                    }
                }
                return;
            }
            this.zzb.put(str, Integer.valueOf(intValue));
            return;
        }
        zzt().zzy().zza("Call to endAdUnitExposure for unknown ad unit id", str);
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @WorkerThread
    public final void zza(long j) {
        zzclz zzy = zzj().zzy();
        for (String str : this.zza.keySet()) {
            zza(str, j - ((Long) this.zza.get(str)).longValue(), zzy);
        }
        if (!this.zza.isEmpty()) {
            zza(j - this.zzc, zzy);
        }
        zzb(j);
    }

    public final void zza(String str) {
        if (str != null) {
            if (str.length() != 0) {
                zzs().zza(new zzcib(this, str, zzk().zzb()));
                return;
            }
        }
        zzt().zzy().zza("Ad unit id must be a non-empty string");
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final void zzb(String str) {
        if (str != null) {
            if (str.length() != 0) {
                zzs().zza(new zzcic(this, str, zzk().zzb()));
                return;
            }
        }
        zzt().zzy().zza("Ad unit id must be a non-empty string");
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ zzcih zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzclk zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzcje zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzcir zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzcme zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzcma zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zze zzk() {
        return super.zzk();
    }

    public final /* bridge */ /* synthetic */ Context zzl() {
        return super.zzl();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ zzcil zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzcno zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzckd zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzcke zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzcju zzu() {
        return super.zzu();
    }

    public final /* bridge */ /* synthetic */ zzcik zzv() {
        return super.zzv();
    }
}
