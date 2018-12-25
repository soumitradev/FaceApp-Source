package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zzbi implements zzcc, zzu {
    final Map<zzc<?>, zze> zza;
    final Map<zzc<?>, ConnectionResult> zzb = new HashMap();
    int zzc;
    final zzba zzd;
    final zzcd zze;
    private final Lock zzf;
    private final Condition zzg;
    private final Context zzh;
    private final zzf zzi;
    private final zzbk zzj;
    private zzr zzk;
    private Map<Api<?>, Boolean> zzl;
    private zza<? extends zzcyj, zzcyk> zzm;
    private volatile zzbh zzn;
    private ConnectionResult zzo = null;

    public zzbi(Context context, zzba zzba, Lock lock, Looper looper, zzf zzf, Map<zzc<?>, zze> map, zzr zzr, Map<Api<?>, Boolean> map2, zza<? extends zzcyj, zzcyk> zza, ArrayList<zzt> arrayList, zzcd zzcd) {
        this.zzh = context;
        this.zzf = lock;
        this.zzi = zzf;
        this.zza = map;
        this.zzk = zzr;
        this.zzl = map2;
        this.zzm = zza;
        this.zzd = zzba;
        this.zze = zzcd;
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((zzt) obj).zza(this);
        }
        this.zzj = new zzbk(this, looper);
        this.zzg = lock.newCondition();
        this.zzn = new zzaz(this);
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zzf.lock();
        try {
            this.zzn.zza(bundle);
        } finally {
            this.zzf.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
        this.zzf.lock();
        try {
            this.zzn.zza(i);
        } finally {
            this.zzf.unlock();
        }
    }

    public final ConnectionResult zza(long j, TimeUnit timeUnit) {
        zza();
        j = timeUnit.toNanos(j);
        while (zze()) {
            if (j <= 0) {
                try {
                    zzc();
                    return new ConnectionResult(14, null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, null);
                }
            }
            j = this.zzg.awaitNanos(j);
        }
        return zzd() ? ConnectionResult.zza : this.zzo != null ? this.zzo : new ConnectionResult(13, null);
    }

    @Nullable
    public final ConnectionResult zza(@NonNull Api<?> api) {
        zzc zzc = api.zzc();
        if (this.zza.containsKey(zzc)) {
            if (((zze) this.zza.get(zzc)).zzs()) {
                return ConnectionResult.zza;
            }
            if (this.zzb.containsKey(zzc)) {
                return (ConnectionResult) this.zzb.get(zzc);
            }
        }
        return null;
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(@NonNull T t) {
        t.zzg();
        return this.zzn.zza((zzm) t);
    }

    public final void zza() {
        this.zzn.zzc();
    }

    final void zza(ConnectionResult connectionResult) {
        this.zzf.lock();
        try {
            this.zzo = connectionResult;
            this.zzn = new zzaz(this);
            this.zzn.zza();
            this.zzg.signalAll();
        } finally {
            this.zzf.unlock();
        }
    }

    public final void zza(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, boolean z) {
        this.zzf.lock();
        try {
            this.zzn.zza(connectionResult, api, z);
        } finally {
            this.zzf.unlock();
        }
    }

    final void zza(zzbj zzbj) {
        this.zzj.sendMessage(this.zzj.obtainMessage(1, zzbj));
    }

    final void zza(RuntimeException runtimeException) {
        this.zzj.sendMessage(this.zzj.obtainMessage(2, runtimeException));
    }

    public final void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String concat = String.valueOf(str).concat("  ");
        printWriter.append(str).append("mState=").println(this.zzn);
        for (Api api : this.zzl.keySet()) {
            printWriter.append(str).append(api.zzd()).println(":");
            ((zze) this.zza.get(api.zzc())).zza(concat, fileDescriptor, printWriter, strArr);
        }
    }

    public final boolean zza(zzcu zzcu) {
        return false;
    }

    public final ConnectionResult zzb() {
        zza();
        while (zze()) {
            try {
                this.zzg.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return zzd() ? ConnectionResult.zza : this.zzo != null ? this.zzo : new ConnectionResult(13, null);
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zzb(@NonNull T t) {
        t.zzg();
        return this.zzn.zzb(t);
    }

    public final void zzc() {
        if (this.zzn.zzb()) {
            this.zzb.clear();
        }
    }

    public final boolean zzd() {
        return this.zzn instanceof zzal;
    }

    public final boolean zze() {
        return this.zzn instanceof zzao;
    }

    public final void zzf() {
        if (zzd()) {
            ((zzal) this.zzn).zzd();
        }
    }

    public final void zzg() {
    }

    final void zzh() {
        this.zzf.lock();
        try {
            this.zzn = new zzao(this, this.zzk, this.zzl, this.zzi, this.zzm, this.zzf, this.zzh);
            this.zzn.zza();
            this.zzg.signalAll();
        } finally {
            this.zzf.unlock();
        }
    }

    final void zzi() {
        this.zzf.lock();
        try {
            this.zzd.zzf();
            this.zzn = new zzal(this);
            this.zzn.zza();
            this.zzg.signalAll();
        } finally {
            this.zzf.unlock();
        }
    }
}
