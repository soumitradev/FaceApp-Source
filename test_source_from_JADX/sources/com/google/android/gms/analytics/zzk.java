package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzaql;
import com.google.android.gms.internal.zzaqq;
import com.google.android.gms.internal.zzatt;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Hide
public final class zzk {
    private static volatile zzk zza;
    private final Context zzb;
    private final List<zzn> zzc = new CopyOnWriteArrayList();
    private final zze zzd = new zze();
    private final zza zze = new zza(this);
    private volatile zzaql zzf;
    private UncaughtExceptionHandler zzg;

    class zza extends ThreadPoolExecutor {
        final /* synthetic */ zzk zza;

        public zza(zzk zzk) {
            this.zza = zzk;
            super(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue());
            setThreadFactory(new zzb());
            allowCoreThreadTimeOut(true);
        }

        protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
            return new zzm(this, runnable, t);
        }
    }

    static class zzb implements ThreadFactory {
        private static final AtomicInteger zza = new AtomicInteger();

        private zzb() {
        }

        public final Thread newThread(Runnable runnable) {
            int incrementAndGet = zza.incrementAndGet();
            StringBuilder stringBuilder = new StringBuilder(23);
            stringBuilder.append("measurement-");
            stringBuilder.append(incrementAndGet);
            return new zzc(runnable, stringBuilder.toString());
        }
    }

    static class zzc extends Thread {
        zzc(Runnable runnable, String str) {
            super(runnable, str);
        }

        public final void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }

    private zzk(Context context) {
        context = context.getApplicationContext();
        zzbq.zza(context);
        this.zzb = context;
    }

    public static zzk zza(Context context) {
        zzbq.zza(context);
        if (zza == null) {
            synchronized (zzk.class) {
                if (zza == null) {
                    zza = new zzk(context);
                }
            }
        }
        return zza;
    }

    private static void zzb(zzg zzg) {
        zzbq.zzc("deliver should be called from worker thread");
        zzbq.zzb(zzg.zzf(), "Measurement must be submitted");
        List<zzo> zzc = zzg.zzc();
        if (!zzc.isEmpty()) {
            Set hashSet = new HashSet();
            for (zzo zzo : zzc) {
                Uri zza = zzo.zza();
                if (!hashSet.contains(zza)) {
                    hashSet.add(zza);
                    zzo.zza(zzg);
                }
            }
        }
    }

    @Hide
    public static void zzd() {
        if (!(Thread.currentThread() instanceof zzc)) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final zzaql zza() {
        if (this.zzf == null) {
            synchronized (this) {
                if (this.zzf == null) {
                    zzaql zzaql = new zzaql();
                    PackageManager packageManager = this.zzb.getPackageManager();
                    String packageName = this.zzb.getPackageName();
                    zzaql.zzc(packageName);
                    zzaql.zzd(packageManager.getInstallerPackageName(packageName));
                    String str = null;
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(this.zzb.getPackageName(), 0);
                        if (packageInfo != null) {
                            CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                            if (!TextUtils.isEmpty(applicationLabel)) {
                                packageName = applicationLabel.toString();
                            }
                            str = packageInfo.versionName;
                        }
                    } catch (NameNotFoundException e) {
                        String str2 = "GAv4";
                        String str3 = "Error retrieving package info: appName set to ";
                        String valueOf = String.valueOf(packageName);
                        Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                    }
                    zzaql.zza(packageName);
                    zzaql.zzb(str);
                    this.zzf = zzaql;
                }
            }
        }
        return this.zzf;
    }

    @Hide
    public final <V> Future<V> zza(Callable<V> callable) {
        zzbq.zza(callable);
        if (!(Thread.currentThread() instanceof zzc)) {
            return this.zze.submit(callable);
        }
        Future futureTask = new FutureTask(callable);
        futureTask.run();
        return futureTask;
    }

    final void zza(zzg zzg) {
        if (zzg.zzi()) {
            throw new IllegalStateException("Measurement prototype can't be submitted");
        } else if (zzg.zzf()) {
            throw new IllegalStateException("Measurement can only be submitted once");
        } else {
            zzg = zzg.zza();
            zzg.zzg();
            this.zze.execute(new zzl(this, zzg));
        }
    }

    @Hide
    public final void zza(Runnable runnable) {
        zzbq.zza(runnable);
        this.zze.submit(runnable);
    }

    public final void zza(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzg = uncaughtExceptionHandler;
    }

    public final zzaqq zzb() {
        DisplayMetrics displayMetrics = this.zzb.getResources().getDisplayMetrics();
        zzaqq zzaqq = new zzaqq();
        zzaqq.zza(zzatt.zza(Locale.getDefault()));
        zzaqq.zzb = displayMetrics.widthPixels;
        zzaqq.zzc = displayMetrics.heightPixels;
        return zzaqq;
    }

    @Hide
    public final Context zzc() {
        return this.zzb;
    }
}
