package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class zzcke extends zzcli {
    private static final AtomicLong zzk = new AtomicLong(Long.MIN_VALUE);
    private ExecutorService zza;
    private zzcki zzb;
    private zzcki zzc;
    private final PriorityBlockingQueue<zzckh<?>> zzd = new PriorityBlockingQueue();
    private final BlockingQueue<zzckh<?>> zze = new LinkedBlockingQueue();
    private final UncaughtExceptionHandler zzf = new zzckg(this, "Thread death: Uncaught exception on worker thread");
    private final UncaughtExceptionHandler zzg = new zzckg(this, "Thread death: Uncaught exception on network thread");
    private final Object zzh = new Object();
    private final Semaphore zzi = new Semaphore(2);
    private volatile boolean zzj;

    zzcke(zzckj zzckj) {
        super(zzckj);
    }

    private final void zza(zzckh<?> zzckh) {
        synchronized (this.zzh) {
            this.zzd.add(zzckh);
            if (this.zzb == null) {
                this.zzb = new zzcki(this, "Measurement Worker", this.zzd);
                this.zzb.setUncaughtExceptionHandler(this.zzf);
                this.zzb.start();
            } else {
                this.zzb.zza();
            }
        }
    }

    public static boolean zzy() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public final <V> Future<V> zza(Callable<V> callable) throws IllegalStateException {
        zzaq();
        zzbq.zza(callable);
        zzckh zzckh = new zzckh(this, (Callable) callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            if (!this.zzd.isEmpty()) {
                zzt().zzaa().zza("Callable skipped the worker queue.");
            }
            zzckh.run();
            return zzckh;
        }
        zza(zzckh);
        return zzckh;
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    public final void zza(Runnable runnable) throws IllegalStateException {
        zzaq();
        zzbq.zza(runnable);
        zza(new zzckh(this, runnable, false, "Task exception on worker thread"));
    }

    final ExecutorService zzaa() {
        ExecutorService executorService;
        synchronized (this.zzh) {
            if (this.zza == null) {
                this.zza = new ThreadPoolExecutor(0, 1, 30, TimeUnit.SECONDS, new ArrayBlockingQueue(100));
            }
            executorService = this.zza;
        }
        return executorService;
    }

    public final <V> Future<V> zzb(Callable<V> callable) throws IllegalStateException {
        zzaq();
        zzbq.zza(callable);
        zzckh zzckh = new zzckh(this, (Callable) callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            zzckh.run();
            return zzckh;
        }
        zza(zzckh);
        return zzckh;
    }

    public final void zzb() {
        if (Thread.currentThread() != this.zzc) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    public final void zzb(Runnable runnable) throws IllegalStateException {
        zzaq();
        zzbq.zza(runnable);
        zzckh zzckh = new zzckh(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzh) {
            this.zze.add(zzckh);
            if (this.zzc == null) {
                this.zzc = new zzcki(this, "Measurement Network", this.zze);
                this.zzc.setUncaughtExceptionHandler(this.zzg);
                this.zzc.start();
            } else {
                this.zzc.zza();
            }
        }
    }

    public final void zzc() {
        if (Thread.currentThread() != this.zzb) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ zzcih zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzclk zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzcje zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzcir zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzcme zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzcma zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zze zzk() {
        return super.zzk();
    }

    public final /* bridge */ /* synthetic */ Context zzl() {
        return super.zzl();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ zzcil zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzcno zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzckd zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzcke zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzcju zzu() {
        return super.zzu();
    }

    public final /* bridge */ /* synthetic */ zzcik zzv() {
        return super.zzv();
    }

    protected final boolean zzw() {
        return false;
    }

    public final boolean zzz() {
        return Thread.currentThread() == this.zzb;
    }
}
