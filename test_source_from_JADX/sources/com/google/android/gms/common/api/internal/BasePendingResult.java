package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult$zza;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzaq;
import com.google.android.gms.common.internal.zzbq;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@KeepName
@Hide
public abstract class BasePendingResult<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zzc = new zzs();
    @KeepName
    private BasePendingResult$zzb mResultGuardian;
    private final Object zza;
    @Hide
    private BasePendingResult$zza<R> zzb;
    private WeakReference<GoogleApiClient> zzd;
    private final CountDownLatch zze;
    private final ArrayList<PendingResult$zza> zzf;
    private ResultCallback<? super R> zzg;
    private final AtomicReference<zzdn> zzh;
    private R zzi;
    private Status zzj;
    private volatile boolean zzk;
    private boolean zzl;
    private boolean zzm;
    private zzaq zzn;
    private volatile zzdh<R> zzo;
    private boolean zzp;

    @Deprecated
    BasePendingResult() {
        this.zza = new Object();
        this.zze = new CountDownLatch(1);
        this.zzf = new ArrayList();
        this.zzh = new AtomicReference();
        this.zzp = false;
        this.zzb = new BasePendingResult$zza(Looper.getMainLooper());
        this.zzd = new WeakReference(null);
    }

    @Deprecated
    protected BasePendingResult(Looper looper) {
        this.zza = new Object();
        this.zze = new CountDownLatch(1);
        this.zzf = new ArrayList();
        this.zzh = new AtomicReference();
        this.zzp = false;
        this.zzb = new BasePendingResult$zza(looper);
        this.zzd = new WeakReference(null);
    }

    protected BasePendingResult(GoogleApiClient googleApiClient) {
        this.zza = new Object();
        this.zze = new CountDownLatch(1);
        this.zzf = new ArrayList();
        this.zzh = new AtomicReference();
        this.zzp = false;
        this.zzb = new BasePendingResult$zza(googleApiClient != null ? googleApiClient.zzc() : Looper.getMainLooper());
        this.zzd = new WeakReference(googleApiClient);
    }

    private final R zza() {
        R r;
        synchronized (this.zza) {
            zzbq.zza(this.zzk ^ true, (Object) "Result has already been consumed.");
            zzbq.zza(zze(), (Object) "Result is not ready.");
            r = this.zzi;
            this.zzi = null;
            this.zzg = null;
            this.zzk = true;
        }
        zzdn zzdn = (zzdn) this.zzh.getAndSet(null);
        if (zzdn != null) {
            zzdn.zza(this);
        }
        return r;
    }

    @Hide
    public static void zzb(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                String valueOf = String.valueOf(result);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 18);
                stringBuilder.append("Unable to release ");
                stringBuilder.append(valueOf);
                Log.w("BasePendingResult", stringBuilder.toString(), e);
            }
        }
    }

    private final void zzc(R r) {
        this.zzi = r;
        this.zzn = null;
        this.zze.countDown();
        this.zzj = this.zzi.getStatus();
        if (this.zzl) {
            this.zzg = null;
        } else if (this.zzg != null) {
            this.zzb.removeMessages(2);
            this.zzb.zza(this.zzg, zza());
        } else if (this.zzi instanceof Releasable) {
            this.mResultGuardian = new BasePendingResult$zzb(this, null);
        }
        ArrayList arrayList = this.zzf;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PendingResult$zza) obj).zza(this.zzj);
        }
        this.zzf.clear();
    }

    public final R await() {
        zzbq.zzc("await must not be called on the UI thread");
        boolean z = true;
        zzbq.zza(this.zzk ^ true, (Object) "Result has already been consumed");
        if (this.zzo != null) {
            z = false;
        }
        zzbq.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            this.zze.await();
        } catch (InterruptedException e) {
            zzd(Status.zzb);
        }
        zzbq.zza(zze(), (Object) "Result is not ready.");
        return zza();
    }

    public final R await(long j, TimeUnit timeUnit) {
        if (j > 0) {
            zzbq.zzc("await must not be called on the UI thread when time is greater than zero.");
        }
        boolean z = true;
        zzbq.zza(this.zzk ^ true, (Object) "Result has already been consumed.");
        if (this.zzo != null) {
            z = false;
        }
        zzbq.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            if (!this.zze.await(j, timeUnit)) {
                zzd(Status.zzd);
            }
        } catch (InterruptedException e) {
            zzd(Status.zzb);
        }
        zzbq.zza(zze(), (Object) "Result is not ready.");
        return zza();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
        r2 = this;
        r0 = r2.zza;
        monitor-enter(r0);
        r1 = r2.zzl;	 Catch:{ all -> 0x002c }
        if (r1 != 0) goto L_0x002a;
    L_0x0007:
        r1 = r2.zzk;	 Catch:{ all -> 0x002c }
        if (r1 == 0) goto L_0x000c;
    L_0x000b:
        goto L_0x002a;
    L_0x000c:
        r1 = r2.zzn;	 Catch:{ all -> 0x002c }
        if (r1 == 0) goto L_0x0017;
    L_0x0010:
        r1 = r2.zzn;	 Catch:{ RemoteException -> 0x0016 }
        r1.zza();	 Catch:{ RemoteException -> 0x0016 }
        goto L_0x0017;
    L_0x0016:
        r1 = move-exception;
    L_0x0017:
        r1 = r2.zzi;	 Catch:{ all -> 0x002c }
        zzb(r1);	 Catch:{ all -> 0x002c }
        r1 = 1;
        r2.zzl = r1;	 Catch:{ all -> 0x002c }
        r1 = com.google.android.gms.common.api.Status.zze;	 Catch:{ all -> 0x002c }
        r1 = r2.zza(r1);	 Catch:{ all -> 0x002c }
        r2.zzc(r1);	 Catch:{ all -> 0x002c }
        monitor-exit(r0);	 Catch:{ all -> 0x002c }
        return;
    L_0x002a:
        monitor-exit(r0);	 Catch:{ all -> 0x002c }
        return;
    L_0x002c:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x002c }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.cancel():void");
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.zza) {
            z = this.zzl;
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r5) {
        /*
        r4 = this;
        r0 = r4.zza;
        monitor-enter(r0);
        if (r5 != 0) goto L_0x000c;
    L_0x0005:
        r5 = 0;
        r4.zzg = r5;	 Catch:{ all -> 0x000a }
        monitor-exit(r0);	 Catch:{ all -> 0x000a }
        return;
    L_0x000a:
        r5 = move-exception;
        goto L_0x003c;
    L_0x000c:
        r1 = r4.zzk;	 Catch:{ all -> 0x000a }
        r2 = 1;
        r1 = r1 ^ r2;
        r3 = "Result has already been consumed.";
        com.google.android.gms.common.internal.zzbq.zza(r1, r3);	 Catch:{ all -> 0x000a }
        r1 = r4.zzo;	 Catch:{ all -> 0x000a }
        if (r1 != 0) goto L_0x001a;
    L_0x0019:
        goto L_0x001b;
    L_0x001a:
        r2 = 0;
    L_0x001b:
        r1 = "Cannot set callbacks if then() has been called.";
        com.google.android.gms.common.internal.zzbq.zza(r2, r1);	 Catch:{ all -> 0x000a }
        r1 = r4.isCanceled();	 Catch:{ all -> 0x000a }
        if (r1 == 0) goto L_0x0028;
    L_0x0026:
        monitor-exit(r0);	 Catch:{ all -> 0x000a }
        return;
    L_0x0028:
        r1 = r4.zze();	 Catch:{ all -> 0x000a }
        if (r1 == 0) goto L_0x0038;
    L_0x002e:
        r1 = r4.zzb;	 Catch:{ all -> 0x000a }
        r2 = r4.zza();	 Catch:{ all -> 0x000a }
        r1.zza(r5, r2);	 Catch:{ all -> 0x000a }
        goto L_0x003a;
    L_0x0038:
        r4.zzg = r5;	 Catch:{ all -> 0x000a }
    L_0x003a:
        monitor-exit(r0);	 Catch:{ all -> 0x000a }
        return;
    L_0x003c:
        monitor-exit(r0);	 Catch:{ all -> 0x000a }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r5, long r6, java.util.concurrent.TimeUnit r8) {
        /*
        r4 = this;
        r0 = r4.zza;
        monitor-enter(r0);
        if (r5 != 0) goto L_0x000c;
    L_0x0005:
        r5 = 0;
        r4.zzg = r5;	 Catch:{ all -> 0x000a }
        monitor-exit(r0);	 Catch:{ all -> 0x000a }
        return;
    L_0x000a:
        r5 = move-exception;
        goto L_0x004a;
    L_0x000c:
        r1 = r4.zzk;	 Catch:{ all -> 0x000a }
        r2 = 1;
        r1 = r1 ^ r2;
        r3 = "Result has already been consumed.";
        com.google.android.gms.common.internal.zzbq.zza(r1, r3);	 Catch:{ all -> 0x000a }
        r1 = r4.zzo;	 Catch:{ all -> 0x000a }
        if (r1 != 0) goto L_0x001a;
    L_0x0019:
        goto L_0x001b;
    L_0x001a:
        r2 = 0;
    L_0x001b:
        r1 = "Cannot set callbacks if then() has been called.";
        com.google.android.gms.common.internal.zzbq.zza(r2, r1);	 Catch:{ all -> 0x000a }
        r1 = r4.isCanceled();	 Catch:{ all -> 0x000a }
        if (r1 == 0) goto L_0x0028;
    L_0x0026:
        monitor-exit(r0);	 Catch:{ all -> 0x000a }
        return;
    L_0x0028:
        r1 = r4.zze();	 Catch:{ all -> 0x000a }
        if (r1 == 0) goto L_0x0038;
    L_0x002e:
        r6 = r4.zzb;	 Catch:{ all -> 0x000a }
        r7 = r4.zza();	 Catch:{ all -> 0x000a }
        r6.zza(r5, r7);	 Catch:{ all -> 0x000a }
        goto L_0x0048;
    L_0x0038:
        r4.zzg = r5;	 Catch:{ all -> 0x000a }
        r5 = r4.zzb;	 Catch:{ all -> 0x000a }
        r6 = r8.toMillis(r6);	 Catch:{ all -> 0x000a }
        r8 = 2;
        r8 = r5.obtainMessage(r8, r4);	 Catch:{ all -> 0x000a }
        r5.sendMessageDelayed(r8, r6);	 Catch:{ all -> 0x000a }
    L_0x0048:
        monitor-exit(r0);	 Catch:{ all -> 0x000a }
        return;
    L_0x004a:
        monitor-exit(r0);	 Catch:{ all -> 0x000a }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback, long, java.util.concurrent.TimeUnit):void");
    }

    @Hide
    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        zzbq.zza(this.zzk ^ true, (Object) "Result has already been consumed.");
        synchronized (this.zza) {
            boolean z = false;
            zzbq.zza(this.zzo == null, (Object) "Cannot call then() twice.");
            if (this.zzg == null) {
                z = true;
            }
            zzbq.zza(z, (Object) "Cannot call then() if callbacks are set.");
            zzbq.zza(this.zzl ^ true, (Object) "Cannot call then() if result was canceled.");
            this.zzp = true;
            this.zzo = new zzdh(this.zzd);
            then = this.zzo.then(resultTransform);
            if (zze()) {
                this.zzb.zza(this.zzo, zza());
            } else {
                this.zzg = this.zzo;
            }
        }
        return then;
    }

    @Hide
    @NonNull
    protected abstract R zza(Status status);

    @Hide
    public final void zza(PendingResult$zza pendingResult$zza) {
        zzbq.zzb(pendingResult$zza != null, "Callback cannot be null.");
        synchronized (this.zza) {
            if (zze()) {
                pendingResult$zza.zza(this.zzj);
            } else {
                this.zzf.add(pendingResult$zza);
            }
        }
    }

    @Hide
    public final void zza(R r) {
        synchronized (this.zza) {
            if (this.zzm || this.zzl) {
                zzb(r);
                return;
            }
            zze();
            zzbq.zza(zze() ^ 1, (Object) "Results have already been set");
            zzbq.zza(this.zzk ^ 1, (Object) "Result has already been consumed");
            zzc(r);
        }
    }

    @Hide
    public final void zza(zzdn zzdn) {
        this.zzh.set(zzdn);
    }

    @Hide
    protected final void zza(zzaq zzaq) {
        synchronized (this.zza) {
            this.zzn = zzaq;
        }
    }

    @Hide
    public final Integer zzb() {
        return null;
    }

    @Hide
    public final void zzd(Status status) {
        synchronized (this.zza) {
            if (!zze()) {
                zza(zza(status));
                this.zzm = true;
            }
        }
    }

    @Hide
    public final boolean zze() {
        return this.zze.getCount() == 0;
    }

    @Hide
    public final boolean zzf() {
        boolean isCanceled;
        synchronized (this.zza) {
            if (((GoogleApiClient) this.zzd.get()) == null || !this.zzp) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    @Hide
    public final void zzg() {
        boolean z;
        if (!this.zzp) {
            if (!((Boolean) zzc.get()).booleanValue()) {
                z = false;
                this.zzp = z;
            }
        }
        z = true;
        this.zzp = z;
    }
}
