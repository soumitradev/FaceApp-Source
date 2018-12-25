package com.google.android.gms.common;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzba;
import com.google.android.gms.common.internal.zzbb;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.DynamiteModule.zzc;

@Hide
final class zzg {
    private static volatile zzba zza;
    private static final Object zzb = new Object();
    private static Context zzc;

    static zzp zza(String str, zzh zzh, boolean z) {
        Throwable e;
        String str2;
        try {
            if (zza == null) {
                zzbq.zza(zzc);
                synchronized (zzb) {
                    if (zza == null) {
                        zza = zzbb.zza(DynamiteModule.zza(zzc, DynamiteModule.zzc, "com.google.android.gms.googlecertificates").zza("com.google.android.gms.common.GoogleCertificatesImpl"));
                    }
                }
            }
            zzbq.zza(zzc);
            try {
                if (zza.zza(new zzn(str, zzh, z), zzn.zza(zzc.getPackageManager()))) {
                    return zzp.zza();
                }
                boolean z2 = true;
                if (z || !zza(str, zzh, true).zza) {
                    z2 = false;
                }
                return zzp.zza(str, zzh, z, z2);
            } catch (RemoteException e2) {
                e = e2;
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                str2 = "module call";
                return zzp.zza(str2, e);
            }
        } catch (zzc e3) {
            e = e3;
            str2 = "module init";
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized void zza(android.content.Context r2) {
        /*
        r0 = com.google.android.gms.common.zzg.class;
        monitor-enter(r0);
        r1 = zzc;	 Catch:{ all -> 0x001a }
        if (r1 != 0) goto L_0x0011;
    L_0x0007:
        if (r2 == 0) goto L_0x0018;
    L_0x0009:
        r2 = r2.getApplicationContext();	 Catch:{ all -> 0x001a }
        zzc = r2;	 Catch:{ all -> 0x001a }
        monitor-exit(r0);
        return;
    L_0x0011:
        r2 = "GoogleCertificates";
        r1 = "GoogleCertificates has been initialized already";
        android.util.Log.w(r2, r1);	 Catch:{ all -> 0x001a }
    L_0x0018:
        monitor-exit(r0);
        return;
    L_0x001a:
        r2 = move-exception;
        monitor-exit(r0);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zzg.zza(android.content.Context):void");
    }
}
