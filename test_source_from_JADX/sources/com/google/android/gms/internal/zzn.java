package com.google.android.gms.internal;

import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;

public final class zzn extends Thread {
    private final BlockingQueue<zzr<?>> zza;
    private final zzm zzb;
    private final zzb zzc;
    private final zzaa zzd;
    private volatile boolean zze = false;

    public zzn(BlockingQueue<zzr<?>> blockingQueue, zzm zzm, zzb zzb, zzaa zzaa) {
        this.zza = blockingQueue;
        this.zzb = zzm;
        this.zzc = zzb;
        this.zzd = zzaa;
    }

    private final void zzb() throws InterruptedException {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        zzr zzr = (zzr) this.zza.take();
        try {
            zzr.zza("network-queue-take");
            zzr.zze();
            TrafficStats.setThreadStatsTag(zzr.zzb());
            zzp zza = this.zzb.zza(zzr);
            zzr.zza("network-http-complete");
            if (zza.zze && zzr.zzl()) {
                zzr.zzb("not-modified");
                zzr.zzm();
                return;
            }
            zzx zza2 = zzr.zza(zza);
            zzr.zza("network-parse-complete");
            if (zzr.zzh() && zza2.zzb != null) {
                this.zzc.zza(zzr.zzc(), zza2.zzb);
                zzr.zza("network-cache-written");
            }
            zzr.zzk();
            this.zzd.zza(zzr, zza2);
            zzr.zza(zza2);
        } catch (zzae e) {
            e.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
            this.zzd.zza(zzr, e);
            zzr.zzm();
        } catch (Throwable e2) {
            zzaf.zza(e2, "Unhandled exception %s", e2.toString());
            zzae zzae = new zzae(e2);
            zzae.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
            this.zzd.zza(zzr, zzae);
            zzr.zzm();
        }
    }

    public final void run() {
        Process.setThreadPriority(10);
        while (true) {
            try {
                zzb();
            } catch (InterruptedException e) {
                if (this.zze) {
                    return;
                }
            }
        }
    }

    public final void zza() {
        this.zze = true;
        interrupt();
    }
}
