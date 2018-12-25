package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbz;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class zzbo<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener, zzu {
    final /* synthetic */ zzbm zza;
    private final Queue<zza> zzb = new LinkedList();
    private final zze zzc;
    private final zzb zzd;
    private final zzh<O> zze;
    private final zzae zzf;
    private final Set<zzj> zzg = new HashSet();
    private final Map<zzck<?>, zzcr> zzh = new HashMap();
    private final int zzi;
    private final zzcv zzj;
    private boolean zzk;
    private int zzl = -1;
    private ConnectionResult zzm = null;

    @WorkerThread
    public zzbo(zzbm zzbm, GoogleApi<O> googleApi) {
        this.zza = zzbm;
        this.zzc = googleApi.zza(zzbm.zzq.getLooper(), this);
        this.zzd = this.zzc instanceof zzbz ? zzbz.zzi() : this.zzc;
        this.zze = googleApi.zzc();
        this.zzf = new zzae();
        this.zzi = googleApi.zzd();
        if (this.zzc.l_()) {
            this.zzj = googleApi.zza(zzbm.zzh, zzbm.zzq);
        } else {
            this.zzj = null;
        }
    }

    @WorkerThread
    private final void zzb(ConnectionResult connectionResult) {
        for (zzj zzj : this.zzg) {
            String str = null;
            if (connectionResult == ConnectionResult.zza) {
                str = this.zzc.zzw();
            }
            zzj.zza(this.zze, connectionResult, str);
        }
        this.zzg.clear();
    }

    @WorkerThread
    private final void zzb(zza zza) {
        zza.zza(this.zzf, zzk());
        try {
            zza.zza(this);
        } catch (DeadObjectException e) {
            onConnectionSuspended(1);
            this.zzc.zzg();
        }
    }

    private final void zzn() {
        this.zzl = -1;
        this.zza.zzj = -1;
    }

    @WorkerThread
    private final void zzo() {
        zzd();
        zzb(ConnectionResult.zza);
        zzq();
        for (zzcr zzcr : this.zzh.values()) {
            try {
                zzcr.zza.zza(this.zzd, new TaskCompletionSource());
            } catch (DeadObjectException e) {
                onConnectionSuspended(1);
                this.zzc.zzg();
            } catch (RemoteException e2) {
            }
        }
        while (this.zzc.zzs() && !this.zzb.isEmpty()) {
            zzb((zza) this.zzb.remove());
        }
        zzr();
    }

    @WorkerThread
    private final void zzp() {
        zzd();
        this.zzk = true;
        this.zzf.zzc();
        this.zza.zzq.sendMessageDelayed(Message.obtain(this.zza.zzq, 9, this.zze), this.zza.zzc);
        this.zza.zzq.sendMessageDelayed(Message.obtain(this.zza.zzq, 11, this.zze), this.zza.zzd);
        zzn();
    }

    @WorkerThread
    private final void zzq() {
        if (this.zzk) {
            this.zza.zzq.removeMessages(11, this.zze);
            this.zza.zzq.removeMessages(9, this.zze);
            this.zzk = false;
        }
    }

    private final void zzr() {
        this.zza.zzq.removeMessages(12, this.zze);
        this.zza.zzq.sendMessageDelayed(this.zza.zzq.obtainMessage(12, this.zze), this.zza.zze);
    }

    public final void onConnected(@Nullable Bundle bundle) {
        if (Looper.myLooper() == this.zza.zzq.getLooper()) {
            zzo();
        } else {
            this.zza.zzq.post(new zzbp(this));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.WorkerThread
    public final void onConnectionFailed(@android.support.annotation.NonNull com.google.android.gms.common.ConnectionResult r5) {
        /*
        r4 = this;
        r0 = r4.zza;
        r0 = r0.zzq;
        com.google.android.gms.common.internal.zzbq.zza(r0);
        r0 = r4.zzj;
        if (r0 == 0) goto L_0x0012;
    L_0x000d:
        r0 = r4.zzj;
        r0.zzb();
    L_0x0012:
        r4.zzd();
        r4.zzn();
        r4.zzb(r5);
        r0 = r5.getErrorCode();
        r1 = 4;
        if (r0 != r1) goto L_0x002a;
    L_0x0022:
        r5 = com.google.android.gms.common.api.internal.zzbm.zzb;
        r4.zza(r5);
        return;
    L_0x002a:
        r0 = r4.zzb;
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0035;
    L_0x0032:
        r4.zzm = r5;
        return;
    L_0x0035:
        r0 = com.google.android.gms.common.api.internal.zzbm.zzf;
        monitor-enter(r0);
        r1 = r4.zza;	 Catch:{ all -> 0x00c6 }
        r1 = r1.zzn;	 Catch:{ all -> 0x00c6 }
        if (r1 == 0) goto L_0x005d;
    L_0x0042:
        r1 = r4.zza;	 Catch:{ all -> 0x00c6 }
        r1 = r1.zzo;	 Catch:{ all -> 0x00c6 }
        r2 = r4.zze;	 Catch:{ all -> 0x00c6 }
        r1 = r1.contains(r2);	 Catch:{ all -> 0x00c6 }
        if (r1 == 0) goto L_0x005d;
    L_0x0050:
        r1 = r4.zza;	 Catch:{ all -> 0x00c6 }
        r1 = r1.zzn;	 Catch:{ all -> 0x00c6 }
        r2 = r4.zzi;	 Catch:{ all -> 0x00c6 }
        r1.zzb(r5, r2);	 Catch:{ all -> 0x00c6 }
        monitor-exit(r0);	 Catch:{ all -> 0x00c6 }
        return;
    L_0x005d:
        monitor-exit(r0);	 Catch:{ all -> 0x00c6 }
        r0 = r4.zza;
        r1 = r4.zzi;
        r0 = r0.zza(r5, r1);
        if (r0 != 0) goto L_0x00c5;
    L_0x0068:
        r5 = r5.getErrorCode();
        r0 = 18;
        if (r5 != r0) goto L_0x0073;
    L_0x0070:
        r5 = 1;
        r4.zzk = r5;
    L_0x0073:
        r5 = r4.zzk;
        if (r5 == 0) goto L_0x0095;
    L_0x0077:
        r5 = r4.zza;
        r5 = r5.zzq;
        r0 = r4.zza;
        r0 = r0.zzq;
        r1 = 9;
        r2 = r4.zze;
        r0 = android.os.Message.obtain(r0, r1, r2);
        r1 = r4.zza;
        r1 = r1.zzc;
        r5.sendMessageDelayed(r0, r1);
        return;
    L_0x0095:
        r5 = new com.google.android.gms.common.api.Status;
        r0 = 17;
        r1 = r4.zze;
        r1 = r1.zza();
        r2 = java.lang.String.valueOf(r1);
        r2 = r2.length();
        r2 = r2 + 38;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "API: ";
        r3.append(r2);
        r3.append(r1);
        r1 = " is not available on this device.";
        r3.append(r1);
        r1 = r3.toString();
        r5.<init>(r0, r1);
        r4.zza(r5);
    L_0x00c5:
        return;
    L_0x00c6:
        r5 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x00c6 }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzbo.onConnectionFailed(com.google.android.gms.common.ConnectionResult):void");
    }

    public final void onConnectionSuspended(int i) {
        if (Looper.myLooper() == this.zza.zzq.getLooper()) {
            zzp();
        } else {
            this.zza.zzq.post(new zzbq(this));
        }
    }

    @WorkerThread
    public final void zza() {
        zzbq.zza(this.zza.zzq);
        zza(zzbm.zza);
        this.zzf.zzb();
        for (zzck zzf : (zzck[]) this.zzh.keySet().toArray(new zzck[this.zzh.size()])) {
            zza(new zzf(zzf, new TaskCompletionSource()));
        }
        zzb(new ConnectionResult(4));
        if (this.zzc.zzs()) {
            this.zzc.zza(new zzbs(this));
        }
    }

    @WorkerThread
    public final void zza(@NonNull ConnectionResult connectionResult) {
        zzbq.zza(this.zza.zzq);
        this.zzc.zzg();
        onConnectionFailed(connectionResult);
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (Looper.myLooper() == this.zza.zzq.getLooper()) {
            onConnectionFailed(connectionResult);
        } else {
            this.zza.zzq.post(new zzbr(this, connectionResult));
        }
    }

    @WorkerThread
    public final void zza(Status status) {
        zzbq.zza(this.zza.zzq);
        for (zza zza : this.zzb) {
            zza.zza(status);
        }
        this.zzb.clear();
    }

    @WorkerThread
    public final void zza(zza zza) {
        zzbq.zza(this.zza.zzq);
        if (this.zzc.zzs()) {
            zzb(zza);
            zzr();
            return;
        }
        this.zzb.add(zza);
        if (this.zzm == null || !this.zzm.hasResolution()) {
            zzi();
        } else {
            onConnectionFailed(this.zzm);
        }
    }

    @WorkerThread
    public final void zza(zzj zzj) {
        zzbq.zza(this.zza.zzq);
        this.zzg.add(zzj);
    }

    public final zze zzb() {
        return this.zzc;
    }

    public final Map<zzck<?>, zzcr> zzc() {
        return this.zzh;
    }

    @WorkerThread
    public final void zzd() {
        zzbq.zza(this.zza.zzq);
        this.zzm = null;
    }

    @WorkerThread
    public final ConnectionResult zze() {
        zzbq.zza(this.zza.zzq);
        return this.zzm;
    }

    @WorkerThread
    public final void zzf() {
        zzbq.zza(this.zza.zzq);
        if (this.zzk) {
            zzi();
        }
    }

    @WorkerThread
    public final void zzg() {
        zzbq.zza(this.zza.zzq);
        if (this.zzk) {
            zzq();
            zza(this.zza.zzi.isGooglePlayServicesAvailable(this.zza.zzh) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
            this.zzc.zzg();
        }
    }

    @WorkerThread
    public final void zzh() {
        zzbq.zza(this.zza.zzq);
        if (this.zzc.zzs() && this.zzh.size() == 0) {
            if (this.zzf.zza()) {
                zzr();
                return;
            }
            this.zzc.zzg();
        }
    }

    @WorkerThread
    public final void zzi() {
        zzbq.zza(this.zza.zzq);
        if (!this.zzc.zzs() && !this.zzc.zzt()) {
            if (this.zzc.zzu()) {
                this.zzc.zzx();
                if (this.zza.zzj != 0) {
                    this.zza.zzi;
                    int zza = GoogleApiAvailability.zza(this.zza.zzh, this.zzc.zzx());
                    this.zzc.zzx();
                    this.zza.zzj = zza;
                    if (zza != 0) {
                        onConnectionFailed(new ConnectionResult(zza, null));
                        return;
                    }
                }
            }
            zzcy zzbu = new zzbu(this.zza, this.zzc, this.zze);
            if (this.zzc.l_()) {
                this.zzj.zza(zzbu);
            }
            this.zzc.zza(zzbu);
        }
    }

    final boolean zzj() {
        return this.zzc.zzs();
    }

    public final boolean zzk() {
        return this.zzc.l_();
    }

    public final int zzl() {
        return this.zzi;
    }

    final zzcyj zzm() {
        return this.zzj == null ? null : this.zzj.zza();
    }
}
