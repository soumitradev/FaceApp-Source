package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzbic;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class zzaa implements zzcc {
    private final Map<zzc<?>, zzz<?>> zza = new HashMap();
    private final Map<zzc<?>, zzz<?>> zzb = new HashMap();
    private final Map<Api<?>, Boolean> zzc;
    private final zzbm zzd;
    private final zzba zze;
    private final Lock zzf;
    private final Looper zzg;
    private final zzf zzh;
    private final Condition zzi;
    private final zzr zzj;
    private final boolean zzk;
    private final boolean zzl;
    private final Queue<zzm<?, ?>> zzm = new LinkedList();
    private boolean zzn;
    private Map<zzh<?>, ConnectionResult> zzo;
    private Map<zzh<?>, ConnectionResult> zzp;
    private zzad zzq;
    private ConnectionResult zzr;

    public zzaa(Context context, Lock lock, Looper looper, zzf zzf, Map<zzc<?>, zze> map, zzr zzr, Map<Api<?>, Boolean> map2, zza<? extends zzcyj, zzcyk> zza, ArrayList<zzt> arrayList, zzba zzba, boolean z) {
        this.zzf = lock;
        Looper looper2 = looper;
        this.zzg = looper2;
        this.zzi = lock.newCondition();
        this.zzh = zzf;
        this.zze = zzba;
        this.zzc = map2;
        zzr zzr2 = zzr;
        this.zzj = zzr2;
        this.zzk = z;
        Map hashMap = new HashMap();
        for (Api api : map2.keySet()) {
            hashMap.put(api.zzc(), api);
        }
        Map hashMap2 = new HashMap();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            zzt zzt = (zzt) obj;
            hashMap2.put(zzt.zza, zzt);
        }
        Object obj2 = null;
        Object obj3 = 1;
        Object obj4 = null;
        for (Entry entry : map.entrySet()) {
            Object obj5;
            Object obj6;
            Object obj7;
            Api api2 = (Api) hashMap.get(entry.getKey());
            zze zze = (zze) entry.getValue();
            if (zze.zzu()) {
                if (((Boolean) r0.zzc.get(api2)).booleanValue()) {
                    obj5 = obj3;
                    obj6 = obj4;
                } else {
                    obj5 = obj3;
                    obj6 = 1;
                }
                obj7 = 1;
            } else {
                obj7 = obj2;
                obj6 = obj4;
                obj5 = null;
            }
            zzz zzz = r1;
            zze zze2 = zze;
            Entry entry2 = entry;
            zzz zzz2 = new zzz(context, api2, looper2, zze, (zzt) hashMap2.get(api2), zzr2, zza);
            r0.zza.put((zzc) entry2.getKey(), zzz);
            if (zze2.l_()) {
                r0.zzb.put((zzc) entry2.getKey(), zzz);
            }
            obj4 = obj6;
            obj3 = obj5;
            obj2 = obj7;
            looper2 = looper;
        }
        boolean z2 = obj2 != null && obj3 == null && obj4 == null;
        r0.zzl = z2;
        r0.zzd = zzbm.zza();
    }

    @Nullable
    private final ConnectionResult zza(@NonNull zzc<?> zzc) {
        this.zzf.lock();
        try {
            zzz zzz = (zzz) this.zza.get(zzc);
            if (this.zzo == null || zzz == null) {
                this.zzf.unlock();
                return null;
            }
            ConnectionResult connectionResult = (ConnectionResult) this.zzo.get(zzz.zzc());
            return connectionResult;
        } finally {
            this.zzf.unlock();
        }
    }

    private final boolean zza(zzz<?> zzz, ConnectionResult connectionResult) {
        return !connectionResult.isSuccess() && !connectionResult.hasResolution() && ((Boolean) this.zzc.get(zzz.zza())).booleanValue() && zzz.zzh().zzu() && this.zzh.isUserResolvableError(connectionResult.getErrorCode());
    }

    private final <T extends zzm<? extends Result, ? extends zzb>> boolean zzc(@NonNull T t) {
        zzc zzc = t.zzc();
        ConnectionResult zza = zza(zzc);
        if (zza == null || zza.getErrorCode() != 4) {
            return false;
        }
        t.zzc(new Status(4, null, this.zzd.zza(((zzz) this.zza.get(zzc)).zzc(), System.identityHashCode(this.zze))));
        return true;
    }

    private final boolean zzh() {
        this.zzf.lock();
        try {
            if (this.zzn) {
                if (this.zzk) {
                    for (zzc zza : this.zzb.keySet()) {
                        ConnectionResult zza2 = zza(zza);
                        if (zza2 != null) {
                            if (!zza2.isSuccess()) {
                            }
                        }
                    }
                    this.zzf.unlock();
                    return true;
                }
            }
            this.zzf.unlock();
            return false;
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    private final void zzi() {
        if (this.zzj == null) {
            this.zze.zzc = Collections.emptySet();
            return;
        }
        Set hashSet = new HashSet(this.zzj.zze());
        Map zzg = this.zzj.zzg();
        for (Api api : zzg.keySet()) {
            ConnectionResult zza = zza(api);
            if (zza != null && zza.isSuccess()) {
                hashSet.addAll(((zzt) zzg.get(api)).zza);
            }
        }
        this.zze.zzc = hashSet;
    }

    private final void zzj() {
        while (!this.zzm.isEmpty()) {
            zzb((zzm) this.zzm.remove());
        }
        this.zze.zza(null);
    }

    @Nullable
    private final ConnectionResult zzk() {
        ConnectionResult connectionResult = null;
        ConnectionResult connectionResult2 = null;
        int i = 0;
        int i2 = 0;
        for (zzz zzz : this.zza.values()) {
            Api zza = zzz.zza();
            ConnectionResult connectionResult3 = (ConnectionResult) this.zzo.get(zzz.zzc());
            if (!connectionResult3.isSuccess() && (!((Boolean) this.zzc.get(zza)).booleanValue() || connectionResult3.hasResolution() || this.zzh.isUserResolvableError(connectionResult3.getErrorCode()))) {
                int zza2;
                if (connectionResult3.getErrorCode() == 4 && this.zzk) {
                    zza2 = zza.zza().zza();
                    if (connectionResult2 == null || i2 > zza2) {
                        connectionResult2 = connectionResult3;
                        i2 = zza2;
                    }
                } else {
                    zza2 = zza.zza().zza();
                    if (connectionResult == null || i > zza2) {
                        connectionResult = connectionResult3;
                        i = zza2;
                    }
                }
            }
        }
        return (connectionResult == null || connectionResult2 == null || i <= i2) ? connectionResult : connectionResult2;
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
            j = this.zzi.awaitNanos(j);
        }
        return zzd() ? ConnectionResult.zza : this.zzr != null ? this.zzr : new ConnectionResult(13, null);
    }

    @Nullable
    public final ConnectionResult zza(@NonNull Api<?> api) {
        return zza(api.zzc());
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(@NonNull T t) {
        if (this.zzk && zzc((zzm) t)) {
            return t;
        }
        if (zzd()) {
            this.zze.zze.zza(t);
            return ((zzz) this.zza.get(t.zzc())).zza(t);
        }
        this.zzm.add(t);
        return t;
    }

    public final void zza() {
        this.zzf.lock();
        try {
            if (!this.zzn) {
                this.zzn = true;
                this.zzo = null;
                this.zzp = null;
                this.zzq = null;
                this.zzr = null;
                this.zzd.zzd();
                this.zzd.zza(this.zza.values()).addOnCompleteListener(new zzbic(this.zzg), new zzac());
            }
            this.zzf.unlock();
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    public final void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final boolean zza(zzcu zzcu) {
        this.zzf.lock();
        try {
            if (!this.zzn || zzh()) {
                this.zzf.unlock();
                return false;
            }
            this.zzd.zzd();
            this.zzq = new zzad(this, zzcu);
            this.zzd.zza(this.zzb.values()).addOnCompleteListener(new zzbic(this.zzg), this.zzq);
            return true;
        } finally {
            this.zzf.unlock();
        }
    }

    public final ConnectionResult zzb() {
        zza();
        while (zze()) {
            try {
                this.zzi.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return zzd() ? ConnectionResult.zza : this.zzr != null ? this.zzr : new ConnectionResult(13, null);
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zzb(@NonNull T t) {
        zzc zzc = t.zzc();
        if (this.zzk && zzc((zzm) t)) {
            return t;
        }
        this.zze.zze.zza(t);
        return ((zzz) this.zza.get(zzc)).zzb(t);
    }

    public final void zzc() {
        this.zzf.lock();
        try {
            this.zzn = false;
            this.zzo = null;
            this.zzp = null;
            if (this.zzq != null) {
                this.zzq.zza();
                this.zzq = null;
            }
            this.zzr = null;
            while (!this.zzm.isEmpty()) {
                zzm zzm = (zzm) this.zzm.remove();
                zzm.zza(null);
                zzm.cancel();
            }
            this.zzi.signalAll();
        } finally {
            this.zzf.unlock();
        }
    }

    public final boolean zzd() {
        this.zzf.lock();
        try {
            boolean z = this.zzo != null && this.zzr == null;
            this.zzf.unlock();
            return z;
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    public final boolean zze() {
        this.zzf.lock();
        try {
            boolean z = this.zzo == null && this.zzn;
            this.zzf.unlock();
            return z;
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }

    public final void zzf() {
    }

    public final void zzg() {
        this.zzf.lock();
        try {
            this.zzd.zze();
            if (this.zzq != null) {
                this.zzq.zza();
                this.zzq = null;
            }
            if (this.zzp == null) {
                this.zzp = new ArrayMap(this.zzb.size());
            }
            ConnectionResult connectionResult = new ConnectionResult(4);
            for (zzz zzc : this.zzb.values()) {
                this.zzp.put(zzc.zzc(), connectionResult);
            }
            if (this.zzo != null) {
                this.zzo.putAll(this.zzp);
            }
            this.zzf.unlock();
        } catch (Throwable th) {
            this.zzf.unlock();
        }
    }
}
