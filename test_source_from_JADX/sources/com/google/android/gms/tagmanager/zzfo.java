package com.google.android.gms.tagmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.google.android.gms.common.internal.Hide;
import javax.jmdns.impl.constants.DNSConstants;

@Hide
final class zzfo extends zzfn {
    private static final Object zza = new Object();
    private static zzfo zzn;
    private Context zzb;
    private zzcc zzc;
    private volatile zzbz zzd;
    private int zze = DNSConstants.ANNOUNCED_RENEWAL_TTL_INTERVAL;
    private boolean zzf = true;
    private boolean zzg = false;
    private boolean zzh = true;
    private boolean zzi = true;
    private zzcd zzj = new zzfp(this);
    private zzfr zzk;
    private zzdo zzl;
    private boolean zzm = false;

    private zzfo() {
    }

    public static zzfo zzc() {
        if (zzn == null) {
            zzn = new zzfo();
        }
        return zzn;
    }

    private final boolean zzf() {
        if (!this.zzm && this.zzh) {
            if (this.zze > 0) {
                return false;
            }
        }
        return true;
    }

    public final synchronized void zza() {
        if (this.zzg) {
            this.zzd.zza(new zzfq(this));
            return;
        }
        zzdj.zze("Dispatch call queued. Dispatch will run once initialization is complete.");
        this.zzf = true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final synchronized void zza(android.content.Context r2, com.google.android.gms.tagmanager.zzbz r3) {
        /*
        r1 = this;
        monitor-enter(r1);
        r0 = r1.zzb;	 Catch:{ all -> 0x0015 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r1);
        return;
    L_0x0007:
        r2 = r2.getApplicationContext();	 Catch:{ all -> 0x0015 }
        r1.zzb = r2;	 Catch:{ all -> 0x0015 }
        r2 = r1.zzd;	 Catch:{ all -> 0x0015 }
        if (r2 != 0) goto L_0x0013;
    L_0x0011:
        r1.zzd = r3;	 Catch:{ all -> 0x0015 }
    L_0x0013:
        monitor-exit(r1);
        return;
    L_0x0015:
        r2 = move-exception;
        monitor-exit(r1);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzfo.zza(android.content.Context, com.google.android.gms.tagmanager.zzbz):void");
    }

    public final synchronized void zza(boolean z) {
        zza(this.zzm, z);
    }

    final synchronized void zza(boolean z, boolean z2) {
        boolean zzf = zzf();
        this.zzm = z;
        this.zzh = z2;
        if (zzf() != zzf) {
            if (zzf()) {
                this.zzk.zzb();
                zzdj.zze("PowerSaveMode initiated.");
                return;
            }
            this.zzk.zza((long) this.zze);
            zzdj.zze("PowerSaveMode terminated.");
        }
    }

    public final synchronized void zzb() {
        if (!zzf()) {
            this.zzk.zza();
        }
    }

    final synchronized zzcc zzd() {
        if (this.zzc == null) {
            if (this.zzb == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.zzc = new zzec(this.zzj, this.zzb);
        }
        if (this.zzk == null) {
            this.zzk = new zzfs();
            if (this.zze > 0) {
                this.zzk.zza((long) this.zze);
            }
        }
        this.zzg = true;
        if (this.zzf) {
            zza();
            this.zzf = false;
        }
        if (this.zzl == null && this.zzi) {
            this.zzl = new zzdo(this);
            BroadcastReceiver broadcastReceiver = this.zzl;
            Context context = this.zzb;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context.registerReceiver(broadcastReceiver, intentFilter);
            intentFilter = new IntentFilter();
            intentFilter.addAction("com.google.analytics.RADIO_POWERED");
            intentFilter.addCategory(context.getPackageName());
            context.registerReceiver(broadcastReceiver, intentFilter);
        }
        return this.zzc;
    }
}
