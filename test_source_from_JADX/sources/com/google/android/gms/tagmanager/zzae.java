package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.internal.zzbs;

final class zzae implements zzdi<zzbs> {
    private /* synthetic */ zzy zza;

    private zzae(zzy zzy) {
        this.zza = zzy;
    }

    public final void zza() {
    }

    public final void zza(int i) {
        if (i == zzda.zzg) {
            this.zza.zzj.zzc();
        }
        synchronized (this.zza) {
            if (!this.zza.zze()) {
                BasePendingResult basePendingResult;
                Result zzb;
                if (this.zza.zzm != null) {
                    basePendingResult = this.zza;
                    zzb = this.zza.zzm;
                } else {
                    basePendingResult = this.zza;
                    zzb = this.zza.zzb(Status.zzd);
                }
                basePendingResult.zza(zzb);
            }
        }
        this.zza.zza(this.zza.zzj.zzb());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void zza(java.lang.Object r6) {
        /*
        r5 = this;
        r6 = (com.google.android.gms.internal.zzbs) r6;
        r0 = r5.zza;
        r0 = r0.zzj;
        r0.zzd();
        r0 = r5.zza;
        monitor-enter(r0);
        r1 = r6.zzb;	 Catch:{ all -> 0x0077 }
        if (r1 != 0) goto L_0x003c;
    L_0x0012:
        r1 = r5.zza;	 Catch:{ all -> 0x0077 }
        r1 = r1.zzo;	 Catch:{ all -> 0x0077 }
        r1 = r1.zzb;	 Catch:{ all -> 0x0077 }
        if (r1 != 0) goto L_0x0032;
    L_0x001c:
        r6 = "Current resource is null; network resource is also null";
        com.google.android.gms.tagmanager.zzdj.zza(r6);	 Catch:{ all -> 0x0077 }
        r6 = r5.zza;	 Catch:{ all -> 0x0077 }
        r6 = r6.zzj;	 Catch:{ all -> 0x0077 }
        r1 = r6.zzb();	 Catch:{ all -> 0x0077 }
        r6 = r5.zza;	 Catch:{ all -> 0x0077 }
        r6.zza(r1);	 Catch:{ all -> 0x0077 }
        monitor-exit(r0);	 Catch:{ all -> 0x0077 }
        return;
    L_0x0032:
        r1 = r5.zza;	 Catch:{ all -> 0x0077 }
        r1 = r1.zzo;	 Catch:{ all -> 0x0077 }
        r1 = r1.zzb;	 Catch:{ all -> 0x0077 }
        r6.zzb = r1;	 Catch:{ all -> 0x0077 }
    L_0x003c:
        r1 = r5.zza;	 Catch:{ all -> 0x0077 }
        r2 = r5.zza;	 Catch:{ all -> 0x0077 }
        r2 = r2.zza;	 Catch:{ all -> 0x0077 }
        r2 = r2.zza();	 Catch:{ all -> 0x0077 }
        r4 = 0;
        r1.zza(r6, r2, r4);	 Catch:{ all -> 0x0077 }
        r1 = r5.zza;	 Catch:{ all -> 0x0077 }
        r1 = r1.zzp;	 Catch:{ all -> 0x0077 }
        r3 = 58;
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0077 }
        r4.<init>(r3);	 Catch:{ all -> 0x0077 }
        r3 = "setting refresh time to current time: ";
        r4.append(r3);	 Catch:{ all -> 0x0077 }
        r4.append(r1);	 Catch:{ all -> 0x0077 }
        r1 = r4.toString();	 Catch:{ all -> 0x0077 }
        com.google.android.gms.tagmanager.zzdj.zze(r1);	 Catch:{ all -> 0x0077 }
        r1 = r5.zza;	 Catch:{ all -> 0x0077 }
        r1 = r1.zzi();	 Catch:{ all -> 0x0077 }
        if (r1 != 0) goto L_0x0075;
    L_0x0070:
        r1 = r5.zza;	 Catch:{ all -> 0x0077 }
        r1.zza(r6);	 Catch:{ all -> 0x0077 }
    L_0x0075:
        monitor-exit(r0);	 Catch:{ all -> 0x0077 }
        return;
    L_0x0077:
        r6 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0077 }
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzae.zza(java.lang.Object):void");
    }
}
