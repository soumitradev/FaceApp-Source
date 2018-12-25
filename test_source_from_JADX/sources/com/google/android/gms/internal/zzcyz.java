package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzaa;
import com.google.android.gms.common.util.zzw;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Hide
public final class zzcyz {
    private static String zza = "WakeLock";
    private static String zzb = "*gcore*:";
    private static boolean zzc = false;
    private static ScheduledExecutorService zzo;
    private final WakeLock zzd;
    private WorkSource zze;
    private final int zzf;
    private final String zzg;
    private final String zzh;
    private final String zzi;
    private final Context zzj;
    private boolean zzk;
    private final Map<String, Integer[]> zzl;
    private int zzm;
    private AtomicInteger zzn;

    public zzcyz(Context context, int i, String str) {
        this(context, 1, str, null, context == null ? null : context.getPackageName());
    }

    @Hide
    @SuppressLint({"UnwrappedWakeLock"})
    private zzcyz(Context context, int i, String str, String str2, String str3) {
        this(context, 1, str, null, str3, null);
    }

    @Hide
    @SuppressLint({"UnwrappedWakeLock"})
    private zzcyz(Context context, int i, String str, String str2, String str3, String str4) {
        this.zzk = true;
        this.zzl = new HashMap();
        this.zzn = new AtomicInteger(0);
        zzbq.zza(str, "Wake lock name can NOT be empty");
        this.zzf = i;
        this.zzh = null;
        this.zzi = null;
        this.zzj = context.getApplicationContext();
        if ("com.google.android.gms".equals(context.getPackageName())) {
            this.zzg = str;
        } else {
            str2 = String.valueOf(zzb);
            str4 = String.valueOf(str);
            this.zzg = str4.length() != 0 ? str2.concat(str4) : new String(str2);
        }
        this.zzd = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (zzaa.zza(this.zzj)) {
            if (zzw.zza(str3)) {
                str3 = context.getPackageName();
            }
            this.zze = zzaa.zza(context, str3);
            WorkSource workSource = this.zze;
            if (workSource != null && zzaa.zza(this.zzj)) {
                if (this.zze != null) {
                    this.zze.add(workSource);
                } else {
                    this.zze = workSource;
                }
                try {
                    this.zzd.setWorkSource(this.zze);
                } catch (IllegalArgumentException e) {
                    Log.wtf(zza, e.toString());
                }
            }
        }
        if (zzo == null) {
            zzo = zzbhg.zza().zza();
        }
    }

    private final String zza(String str) {
        return this.zzk ? !TextUtils.isEmpty(str) ? str : this.zzh : this.zzh;
    }

    private final void zza(int i) {
        if (this.zzd.isHeld()) {
            try {
                this.zzd.release();
            } catch (RuntimeException e) {
                if (e.getClass().equals(RuntimeException.class)) {
                    Log.e(zza, String.valueOf(this.zzg).concat("was already released!"), new IllegalStateException());
                    return;
                }
                throw e;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza() {
        /*
        r11 = this;
        r0 = r11.zzn;
        r0 = r0.decrementAndGet();
        if (r0 >= 0) goto L_0x000f;
    L_0x0008:
        r0 = zza;
        r1 = "release without a matched acquire!";
        android.util.Log.e(r0, r1);
    L_0x000f:
        r0 = 0;
        r5 = r11.zza(r0);
        monitor-enter(r11);
        r0 = r11.zzk;	 Catch:{ all -> 0x0073 }
        r9 = 1;
        r10 = 0;
        if (r0 == 0) goto L_0x0046;
    L_0x001b:
        r0 = r11.zzl;	 Catch:{ all -> 0x0073 }
        r0 = r0.get(r5);	 Catch:{ all -> 0x0073 }
        r0 = (java.lang.Integer[]) r0;	 Catch:{ all -> 0x0073 }
        if (r0 != 0) goto L_0x0027;
    L_0x0025:
        r0 = 0;
        goto L_0x0044;
    L_0x0027:
        r1 = r0[r10];	 Catch:{ all -> 0x0073 }
        r1 = r1.intValue();	 Catch:{ all -> 0x0073 }
        if (r1 != r9) goto L_0x0036;
    L_0x002f:
        r0 = r11.zzl;	 Catch:{ all -> 0x0073 }
        r0.remove(r5);	 Catch:{ all -> 0x0073 }
        r0 = 1;
        goto L_0x0044;
    L_0x0036:
        r1 = r0[r10];	 Catch:{ all -> 0x0073 }
        r1 = r1.intValue();	 Catch:{ all -> 0x0073 }
        r1 = r1 - r9;
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ all -> 0x0073 }
        r0[r10] = r1;	 Catch:{ all -> 0x0073 }
        goto L_0x0025;
    L_0x0044:
        if (r0 != 0) goto L_0x004e;
    L_0x0046:
        r0 = r11.zzk;	 Catch:{ all -> 0x0073 }
        if (r0 != 0) goto L_0x006e;
    L_0x004a:
        r0 = r11.zzm;	 Catch:{ all -> 0x0073 }
        if (r0 != r9) goto L_0x006e;
    L_0x004e:
        com.google.android.gms.common.stats.zze.zza();	 Catch:{ all -> 0x0073 }
        r1 = r11.zzj;	 Catch:{ all -> 0x0073 }
        r0 = r11.zzd;	 Catch:{ all -> 0x0073 }
        r2 = com.google.android.gms.common.stats.zzc.zza(r0, r5);	 Catch:{ all -> 0x0073 }
        r3 = 8;
        r4 = r11.zzg;	 Catch:{ all -> 0x0073 }
        r6 = 0;
        r7 = r11.zzf;	 Catch:{ all -> 0x0073 }
        r0 = r11.zze;	 Catch:{ all -> 0x0073 }
        r8 = com.google.android.gms.common.util.zzaa.zza(r0);	 Catch:{ all -> 0x0073 }
        com.google.android.gms.common.stats.zze.zza(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ all -> 0x0073 }
        r0 = r11.zzm;	 Catch:{ all -> 0x0073 }
        r0 = r0 - r9;
        r11.zzm = r0;	 Catch:{ all -> 0x0073 }
    L_0x006e:
        monitor-exit(r11);	 Catch:{ all -> 0x0073 }
        r11.zza(r10);
        return;
    L_0x0073:
        r0 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x0073 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcyz.zza():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(long r12) {
        /*
        r11 = this;
        r12 = r11.zzn;
        r12.incrementAndGet();
        r12 = 0;
        r4 = r11.zza(r12);
        monitor-enter(r11);
        r12 = r11.zzl;	 Catch:{ all -> 0x0092 }
        r12 = r12.isEmpty();	 Catch:{ all -> 0x0092 }
        r13 = 0;
        if (r12 == 0) goto L_0x0018;
    L_0x0014:
        r12 = r11.zzm;	 Catch:{ all -> 0x0092 }
        if (r12 <= 0) goto L_0x0027;
    L_0x0018:
        r12 = r11.zzd;	 Catch:{ all -> 0x0092 }
        r12 = r12.isHeld();	 Catch:{ all -> 0x0092 }
        if (r12 != 0) goto L_0x0027;
    L_0x0020:
        r12 = r11.zzl;	 Catch:{ all -> 0x0092 }
        r12.clear();	 Catch:{ all -> 0x0092 }
        r11.zzm = r13;	 Catch:{ all -> 0x0092 }
    L_0x0027:
        r12 = r11.zzk;	 Catch:{ all -> 0x0092 }
        r10 = 1;
        if (r12 == 0) goto L_0x0054;
    L_0x002c:
        r12 = r11.zzl;	 Catch:{ all -> 0x0092 }
        r12 = r12.get(r4);	 Catch:{ all -> 0x0092 }
        r12 = (java.lang.Integer[]) r12;	 Catch:{ all -> 0x0092 }
        if (r12 != 0) goto L_0x0045;
    L_0x0036:
        r12 = r11.zzl;	 Catch:{ all -> 0x0092 }
        r0 = new java.lang.Integer[r10];	 Catch:{ all -> 0x0092 }
        r1 = java.lang.Integer.valueOf(r10);	 Catch:{ all -> 0x0092 }
        r0[r13] = r1;	 Catch:{ all -> 0x0092 }
        r12.put(r4, r0);	 Catch:{ all -> 0x0092 }
        r13 = 1;
        goto L_0x0052;
    L_0x0045:
        r0 = r12[r13];	 Catch:{ all -> 0x0092 }
        r0 = r0.intValue();	 Catch:{ all -> 0x0092 }
        r0 = r0 + r10;
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ all -> 0x0092 }
        r12[r13] = r0;	 Catch:{ all -> 0x0092 }
    L_0x0052:
        if (r13 != 0) goto L_0x005c;
    L_0x0054:
        r12 = r11.zzk;	 Catch:{ all -> 0x0092 }
        if (r12 != 0) goto L_0x007d;
    L_0x0058:
        r12 = r11.zzm;	 Catch:{ all -> 0x0092 }
        if (r12 != 0) goto L_0x007d;
    L_0x005c:
        com.google.android.gms.common.stats.zze.zza();	 Catch:{ all -> 0x0092 }
        r0 = r11.zzj;	 Catch:{ all -> 0x0092 }
        r12 = r11.zzd;	 Catch:{ all -> 0x0092 }
        r1 = com.google.android.gms.common.stats.zzc.zza(r12, r4);	 Catch:{ all -> 0x0092 }
        r2 = 7;
        r3 = r11.zzg;	 Catch:{ all -> 0x0092 }
        r5 = 0;
        r6 = r11.zzf;	 Catch:{ all -> 0x0092 }
        r12 = r11.zze;	 Catch:{ all -> 0x0092 }
        r7 = com.google.android.gms.common.util.zzaa.zza(r12);	 Catch:{ all -> 0x0092 }
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        com.google.android.gms.common.stats.zze.zza(r0, r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ all -> 0x0092 }
        r12 = r11.zzm;	 Catch:{ all -> 0x0092 }
        r12 = r12 + r10;
        r11.zzm = r12;	 Catch:{ all -> 0x0092 }
    L_0x007d:
        monitor-exit(r11);	 Catch:{ all -> 0x0092 }
        r12 = r11.zzd;
        r12.acquire();
        r12 = zzo;
        r13 = new com.google.android.gms.internal.zzcza;
        r13.<init>(r11);
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = java.util.concurrent.TimeUnit.MILLISECONDS;
        r12.schedule(r13, r0, r2);
        return;
    L_0x0092:
        r12 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x0092 }
        throw r12;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcyz.zza(long):void");
    }

    public final void zza(boolean z) {
        this.zzd.setReferenceCounted(false);
        this.zzk = false;
    }

    public final boolean zzb() {
        return this.zzd.isHeld();
    }
}
