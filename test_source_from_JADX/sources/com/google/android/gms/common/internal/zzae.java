package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzae implements Callback {
    private final zzaf zza;
    private final ArrayList<ConnectionCallbacks> zzb = new ArrayList();
    private ArrayList<ConnectionCallbacks> zzc = new ArrayList();
    private final ArrayList<OnConnectionFailedListener> zzd = new ArrayList();
    private volatile boolean zze = false;
    private final AtomicInteger zzf = new AtomicInteger(0);
    private boolean zzg = false;
    private final Handler zzh;
    private final Object zzi = new Object();

    public zzae(Looper looper, zzaf zzaf) {
        this.zza = zzaf;
        this.zzh = new Handler(looper, this);
    }

    public final boolean handleMessage(Message message) {
        if (message.what == 1) {
            ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) message.obj;
            synchronized (this.zzi) {
                if (this.zze && this.zza.zzs() && this.zzb.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(this.zza.q_());
                }
            }
            return true;
        }
        int i = message.what;
        StringBuilder stringBuilder = new StringBuilder(45);
        stringBuilder.append("Don't know how to handle message: ");
        stringBuilder.append(i);
        Log.wtf("GmsClientEvents", stringBuilder.toString(), new Exception());
        return false;
    }

    public final void zza() {
        this.zze = false;
        this.zzf.incrementAndGet();
    }

    public final void zza(int i) {
        zzbq.zza(Looper.myLooper() == this.zzh.getLooper(), "onUnintentionalDisconnection must only be called on the Handler thread");
        this.zzh.removeMessages(1);
        synchronized (this.zzi) {
            this.zzg = true;
            ArrayList arrayList = new ArrayList(this.zzb);
            int i2 = this.zzf.get();
            arrayList = arrayList;
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) obj;
                if (!this.zze || this.zzf.get() != i2) {
                    break;
                } else if (this.zzb.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnectionSuspended(i);
                }
            }
            this.zzc.clear();
            this.zzg = false;
        }
    }

    public final void zza(Bundle bundle) {
        boolean z = true;
        zzbq.zza(Looper.myLooper() == this.zzh.getLooper(), "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.zzi) {
            zzbq.zza(this.zzg ^ true);
            this.zzh.removeMessages(1);
            this.zzg = true;
            if (this.zzc.size() != 0) {
                z = false;
            }
            zzbq.zza(z);
            ArrayList arrayList = new ArrayList(this.zzb);
            int i = this.zzf.get();
            arrayList = arrayList;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) obj;
                if (!this.zze || !this.zza.zzs() || this.zzf.get() != i) {
                    break;
                } else if (!this.zzc.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(bundle);
                }
            }
            this.zzc.clear();
            this.zzg = false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.common.ConnectionResult r8) {
        /*
        r7 = this;
        r0 = android.os.Looper.myLooper();
        r1 = r7.zzh;
        r1 = r1.getLooper();
        r2 = 0;
        r3 = 1;
        if (r0 != r1) goto L_0x0010;
    L_0x000e:
        r0 = 1;
        goto L_0x0011;
    L_0x0010:
        r0 = 0;
    L_0x0011:
        r1 = "onConnectionFailure must only be called on the Handler thread";
        com.google.android.gms.common.internal.zzbq.zza(r0, r1);
        r0 = r7.zzh;
        r0.removeMessages(r3);
        r0 = r7.zzi;
        monitor-enter(r0);
        r1 = new java.util.ArrayList;	 Catch:{ all -> 0x0058 }
        r3 = r7.zzd;	 Catch:{ all -> 0x0058 }
        r1.<init>(r3);	 Catch:{ all -> 0x0058 }
        r3 = r7.zzf;	 Catch:{ all -> 0x0058 }
        r3 = r3.get();	 Catch:{ all -> 0x0058 }
        r1 = (java.util.ArrayList) r1;	 Catch:{ all -> 0x0058 }
        r4 = r1.size();	 Catch:{ all -> 0x0058 }
    L_0x0031:
        if (r2 >= r4) goto L_0x0056;
    L_0x0033:
        r5 = r1.get(r2);	 Catch:{ all -> 0x0058 }
        r2 = r2 + 1;
        r5 = (com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener) r5;	 Catch:{ all -> 0x0058 }
        r6 = r7.zze;	 Catch:{ all -> 0x0058 }
        if (r6 == 0) goto L_0x0054;
    L_0x003f:
        r6 = r7.zzf;	 Catch:{ all -> 0x0058 }
        r6 = r6.get();	 Catch:{ all -> 0x0058 }
        if (r6 == r3) goto L_0x0048;
    L_0x0047:
        goto L_0x0054;
    L_0x0048:
        r6 = r7.zzd;	 Catch:{ all -> 0x0058 }
        r6 = r6.contains(r5);	 Catch:{ all -> 0x0058 }
        if (r6 == 0) goto L_0x0031;
    L_0x0050:
        r5.onConnectionFailed(r8);	 Catch:{ all -> 0x0058 }
        goto L_0x0031;
    L_0x0054:
        monitor-exit(r0);	 Catch:{ all -> 0x0058 }
        return;
    L_0x0056:
        monitor-exit(r0);	 Catch:{ all -> 0x0058 }
        return;
    L_0x0058:
        r8 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0058 }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzae.zza(com.google.android.gms.common.ConnectionResult):void");
    }

    public final void zza(ConnectionCallbacks connectionCallbacks) {
        zzbq.zza(connectionCallbacks);
        synchronized (this.zzi) {
            if (this.zzb.contains(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 62);
                stringBuilder.append("registerConnectionCallbacks(): listener ");
                stringBuilder.append(valueOf);
                stringBuilder.append(" is already registered");
                Log.w("GmsClientEvents", stringBuilder.toString());
            } else {
                this.zzb.add(connectionCallbacks);
            }
        }
        if (this.zza.zzs()) {
            this.zzh.sendMessage(this.zzh.obtainMessage(1, connectionCallbacks));
        }
    }

    public final void zza(OnConnectionFailedListener onConnectionFailedListener) {
        zzbq.zza(onConnectionFailedListener);
        synchronized (this.zzi) {
            if (this.zzd.contains(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 67);
                stringBuilder.append("registerConnectionFailedListener(): listener ");
                stringBuilder.append(valueOf);
                stringBuilder.append(" is already registered");
                Log.w("GmsClientEvents", stringBuilder.toString());
            } else {
                this.zzd.add(onConnectionFailedListener);
            }
        }
    }

    public final void zzb() {
        this.zze = true;
    }

    public final boolean zzb(ConnectionCallbacks connectionCallbacks) {
        boolean contains;
        zzbq.zza(connectionCallbacks);
        synchronized (this.zzi) {
            contains = this.zzb.contains(connectionCallbacks);
        }
        return contains;
    }

    public final boolean zzb(OnConnectionFailedListener onConnectionFailedListener) {
        boolean contains;
        zzbq.zza(onConnectionFailedListener);
        synchronized (this.zzi) {
            contains = this.zzd.contains(onConnectionFailedListener);
        }
        return contains;
    }

    public final void zzc(ConnectionCallbacks connectionCallbacks) {
        zzbq.zza(connectionCallbacks);
        synchronized (this.zzi) {
            if (!this.zzb.remove(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 52);
                stringBuilder.append("unregisterConnectionCallbacks(): listener ");
                stringBuilder.append(valueOf);
                stringBuilder.append(" not found");
                Log.w("GmsClientEvents", stringBuilder.toString());
            } else if (this.zzg) {
                this.zzc.add(connectionCallbacks);
            }
        }
    }

    public final void zzc(OnConnectionFailedListener onConnectionFailedListener) {
        zzbq.zza(onConnectionFailedListener);
        synchronized (this.zzi) {
            if (!this.zzd.remove(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 57);
                stringBuilder.append("unregisterConnectionFailedListener(): listener ");
                stringBuilder.append(valueOf);
                stringBuilder.append(" not found");
                Log.w("GmsClientEvents", stringBuilder.toString());
            }
        }
    }
}
