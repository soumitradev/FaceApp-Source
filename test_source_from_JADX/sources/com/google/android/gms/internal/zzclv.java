package com.google.android.gms.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

final class zzclv implements Callable<String> {
    private /* synthetic */ zzclk zza;

    zzclv(zzclk zzclk) {
        this.zza = zzclk;
    }

    public final /* synthetic */ Object call() throws Exception {
        String zzz = this.zza.zzu().zzz();
        if (zzz != null) {
            return zzz;
        }
        zzcjl zzy;
        String str;
        zzclh zzf = this.zza.zzf();
        String str2 = null;
        if (zzf.zzs().zzz()) {
            zzy = zzf.zzt().zzy();
            str = "Cannot retrieve app instance id from analytics worker thread";
        } else {
            zzf.zzs();
            if (zzcke.zzy()) {
                zzy = zzf.zzt().zzy();
                str = "Cannot retrieve app instance id from main thread";
            } else {
                long zzb = zzf.zzk().zzb();
                String zzc = zzf.zzc(120000);
                long zzb2 = zzf.zzk().zzb() - zzb;
                str2 = (zzc != null || zzb2 >= 120000) ? zzc : zzf.zzc(120000 - zzb2);
                if (str2 != null) {
                    throw new TimeoutException();
                }
                this.zza.zzu().zzd(str2);
                return str2;
            }
        }
        zzy.zza(str);
        if (str2 != null) {
            this.zza.zzu().zzd(str2);
            return str2;
        }
        throw new TimeoutException();
    }
}
