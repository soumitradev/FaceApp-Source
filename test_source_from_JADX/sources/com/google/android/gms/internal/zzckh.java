package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

final class zzckh<V> extends FutureTask<V> implements Comparable<zzckh> {
    final boolean zza;
    private final long zzb = zzcke.zzk.getAndIncrement();
    private final String zzc;
    private /* synthetic */ zzcke zzd;

    zzckh(zzcke zzcke, Runnable runnable, boolean z, String str) {
        this.zzd = zzcke;
        super(runnable, null);
        zzbq.zza(str);
        this.zzc = str;
        this.zza = false;
        if (this.zzb == Long.MAX_VALUE) {
            zzcke.zzt().zzy().zza("Tasks index overflow");
        }
    }

    zzckh(zzcke zzcke, Callable<V> callable, boolean z, String str) {
        this.zzd = zzcke;
        super(callable);
        zzbq.zza(str);
        this.zzc = str;
        this.zza = z;
        if (this.zzb == Long.MAX_VALUE) {
            zzcke.zzt().zzy().zza("Tasks index overflow");
        }
    }

    public final /* synthetic */ int compareTo(@NonNull Object obj) {
        zzckh zzckh = (zzckh) obj;
        if (this.zza != zzckh.zza) {
            return this.zza ? -1 : 1;
        } else {
            if (this.zzb < zzckh.zzb) {
                return -1;
            }
            if (this.zzb > zzckh.zzb) {
                return 1;
            }
            this.zzd.zzt().zzz().zza("Two tasks share the same index. index", Long.valueOf(this.zzb));
            return 0;
        }
    }

    protected final void setException(Throwable th) {
        this.zzd.zzt().zzy().zza(this.zzc, th);
        if (th instanceof zzckf) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }
}
