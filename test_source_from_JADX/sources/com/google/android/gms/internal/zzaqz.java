package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Hide
public final class zzaqz extends zzari {
    private final zzarw zza;

    public zzaqz(zzark zzark, zzarm zzarm) {
        super(zzark);
        zzbq.zza(zzarm);
        this.zza = new zzarw(zzark, zzarm);
    }

    public final long zza(zzarn zzarn) {
        zzz();
        zzbq.zza(zzarn);
        zzk.zzd();
        long zza = this.zza.zza(zzarn, true);
        if (zza == 0) {
            this.zza.zza(zzarn);
        }
        return zza;
    }

    protected final void zza() {
        this.zza.zzaa();
    }

    public final void zza(int i) {
        zzz();
        zzb("setLocalDispatchPeriod (sec)", Integer.valueOf(i));
        zzn().zza(new zzara(this, i));
    }

    public final void zza(zzasr zzasr) {
        zzz();
        zzn().zza(new zzarf(this, zzasr));
    }

    public final void zza(zzasy zzasy) {
        zzbq.zza(zzasy);
        zzz();
        zzb("Hit delivery requested", zzasy);
        zzn().zza(new zzard(this, zzasy));
    }

    public final void zza(String str, Runnable runnable) {
        zzbq.zza(str, "campaign param can't be empty");
        zzn().zza(new zzarc(this, str, runnable));
    }

    public final void zzb() {
        this.zza.zzb();
    }

    public final void zzc() {
        zzz();
        zzn().zza(new zzare(this));
    }

    public final void zzd() {
        zzz();
        Context zzk = zzk();
        if (zzatk.zza(zzk) && zzatl.zza(zzk)) {
            Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent.setComponent(new ComponentName(zzk, "com.google.android.gms.analytics.AnalyticsService"));
            zzk.startService(intent);
            return;
        }
        zza(null);
    }

    public final boolean zze() {
        zzz();
        try {
            zzn().zza(new zzarg(this)).get(4, TimeUnit.SECONDS);
            return true;
        } catch (InterruptedException e) {
            zzd("syncDispatchLocalHits interrupted", e);
            return false;
        } catch (ExecutionException e2) {
            zze("syncDispatchLocalHits failed", e2);
            return false;
        } catch (TimeoutException e3) {
            zzd("syncDispatchLocalHits timed out", e3);
            return false;
        }
    }

    public final void zzf() {
        zzz();
        zzk.zzd();
        zzarh zzarh = this.zza;
        zzk.zzd();
        zzarh.zzz();
        zzarh.zzb("Service disconnected");
    }

    final void zzg() {
        zzk.zzd();
        this.zza.zze();
    }

    final void zzh() {
        zzk.zzd();
        this.zza.zzd();
    }
}
