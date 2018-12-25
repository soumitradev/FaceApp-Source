package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;

public final class zzarq implements ServiceConnection {
    final /* synthetic */ zzaro zza;
    private volatile zzasz zzb;
    private volatile boolean zzc;

    protected zzarq(zzaro zzaro) {
        this.zza = zzaro;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzbq.zzb("AnalyticsServiceConnection.onServiceConnected");
        synchronized (this) {
            if (iBinder == null) {
                try {
                    this.zza.zzf("Service connected with null binder");
                    notifyAll();
                } catch (Throwable th) {
                    notifyAll();
                }
            } else {
                zzasz zzasz = null;
                try {
                    String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                    if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(interfaceDescriptor)) {
                        if (iBinder != null) {
                            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
                            zzasz = queryLocalInterface instanceof zzasz ? (zzasz) queryLocalInterface : new zzata(iBinder);
                        }
                        this.zza.zzb("Bound to IAnalyticsService interface");
                    } else {
                        this.zza.zze("Got binder with a wrong descriptor", interfaceDescriptor);
                    }
                } catch (RemoteException e) {
                    this.zza.zzf("Service connect failed to get IAnalyticsService");
                }
                if (zzasz == null) {
                    try {
                        zza.zza();
                        this.zza.zzk().unbindService(this.zza.zza);
                    } catch (IllegalArgumentException e2) {
                    }
                } else if (this.zzc) {
                    this.zzb = zzasz;
                } else {
                    this.zza.zze("onServiceConnected received after the timeout limit");
                    this.zza.zzn().zza(new zzarr(this, zzasz));
                }
                notifyAll();
            }
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        zzbq.zzb("AnalyticsServiceConnection.onServiceDisconnected");
        this.zza.zzn().zza(new zzars(this, componentName));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.zzasz zza() {
        /*
        r6 = this;
        com.google.android.gms.analytics.zzk.zzd();
        r0 = new android.content.Intent;
        r1 = "com.google.android.gms.analytics.service.START";
        r0.<init>(r1);
        r1 = new android.content.ComponentName;
        r2 = "com.google.android.gms";
        r3 = "com.google.android.gms.analytics.service.AnalyticsService";
        r1.<init>(r2, r3);
        r0.setComponent(r1);
        r1 = r6.zza;
        r1 = r1.zzk();
        r2 = "app_package_name";
        r3 = r1.getPackageName();
        r0.putExtra(r2, r3);
        r2 = com.google.android.gms.common.stats.zza.zza();
        monitor-enter(r6);
        r3 = 0;
        r6.zzb = r3;	 Catch:{ all -> 0x0077 }
        r4 = 1;
        r6.zzc = r4;	 Catch:{ all -> 0x0077 }
        r4 = r6.zza;	 Catch:{ all -> 0x0077 }
        r4 = r4.zza;	 Catch:{ all -> 0x0077 }
        r5 = 129; // 0x81 float:1.81E-43 double:6.37E-322;
        r0 = r2.zza(r1, r0, r4, r5);	 Catch:{ all -> 0x0077 }
        r1 = r6.zza;	 Catch:{ all -> 0x0077 }
        r2 = "Bind to service requested";
        r4 = java.lang.Boolean.valueOf(r0);	 Catch:{ all -> 0x0077 }
        r1.zza(r2, r4);	 Catch:{ all -> 0x0077 }
        r1 = 0;
        if (r0 != 0) goto L_0x004e;
    L_0x004a:
        r6.zzc = r1;	 Catch:{ all -> 0x0077 }
        monitor-exit(r6);	 Catch:{ all -> 0x0077 }
        return r3;
    L_0x004e:
        r0 = com.google.android.gms.internal.zzast.zzab;	 Catch:{ InterruptedException -> 0x005e }
        r0 = r0.zza();	 Catch:{ InterruptedException -> 0x005e }
        r0 = (java.lang.Long) r0;	 Catch:{ InterruptedException -> 0x005e }
        r4 = r0.longValue();	 Catch:{ InterruptedException -> 0x005e }
        r6.wait(r4);	 Catch:{ InterruptedException -> 0x005e }
        goto L_0x0066;
    L_0x005e:
        r0 = move-exception;
        r0 = r6.zza;	 Catch:{ all -> 0x0077 }
        r2 = "Wait for service connect was interrupted";
        r0.zze(r2);	 Catch:{ all -> 0x0077 }
    L_0x0066:
        r6.zzc = r1;	 Catch:{ all -> 0x0077 }
        r0 = r6.zzb;	 Catch:{ all -> 0x0077 }
        r6.zzb = r3;	 Catch:{ all -> 0x0077 }
        if (r0 != 0) goto L_0x0075;
    L_0x006e:
        r1 = r6.zza;	 Catch:{ all -> 0x0077 }
        r2 = "Successfully bound to service but never got onServiceConnected callback";
        r1.zzf(r2);	 Catch:{ all -> 0x0077 }
    L_0x0075:
        monitor-exit(r6);	 Catch:{ all -> 0x0077 }
        return r0;
    L_0x0077:
        r0 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0077 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzarq.zza():com.google.android.gms.internal.zzasz");
    }
}
