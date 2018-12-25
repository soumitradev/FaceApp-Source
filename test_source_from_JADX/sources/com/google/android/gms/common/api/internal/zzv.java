package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.internal.zzcyk;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

final class zzv implements zzcc {
    private final Context zza;
    private final zzba zzb;
    private final Looper zzc;
    private final zzbi zzd;
    private final zzbi zze;
    private final Map<zzc<?>, zzbi> zzf;
    private final Set<zzcu> zzg = Collections.newSetFromMap(new WeakHashMap());
    private final zze zzh;
    private Bundle zzi;
    private ConnectionResult zzj = null;
    private ConnectionResult zzk = null;
    private boolean zzl = false;
    private final Lock zzm;
    private int zzn = 0;

    private zzv(Context context, zzba zzba, Lock lock, Looper looper, zzf zzf, Map<zzc<?>, zze> map, Map<zzc<?>, zze> map2, zzr zzr, zza<? extends zzcyj, zzcyk> zza, zze zze, ArrayList<zzt> arrayList, ArrayList<zzt> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        Context context2 = context;
        this.zza = context2;
        this.zzb = zzba;
        Lock lock2 = lock;
        this.zzm = lock2;
        Looper looper2 = looper;
        this.zzc = looper2;
        this.zzh = zze;
        Context context3 = context2;
        Lock lock3 = lock2;
        zzf zzf2 = zzf;
        zzbi zzbi = r3;
        zzbi zzbi2 = new zzbi(context3, this.zzb, lock3, looper2, zzf2, map2, null, map4, null, arrayList2, new zzx());
        this.zzd = zzbi;
        this.zze = new zzbi(context3, this.zzb, lock3, looper, zzf2, map, zzr, map3, zza, arrayList, new zzy());
        Map arrayMap = new ArrayMap();
        for (zzc put : map2.keySet()) {
            arrayMap.put(put, r0.zzd);
        }
        for (zzc put2 : map.keySet()) {
            arrayMap.put(put2, r0.zze);
        }
        r0.zzf = Collections.unmodifiableMap(arrayMap);
    }

    public static zzv zza(Context context, zzba zzba, Lock lock, Looper looper, zzf zzf, Map<zzc<?>, zze> map, zzr zzr, Map<Api<?>, Boolean> map2, zza<? extends zzcyj, zzcyk> zza, ArrayList<zzt> arrayList) {
        Map<Api<?>, Boolean> map3 = map2;
        Map arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        zze zze = null;
        for (Entry entry : map.entrySet()) {
            zze zze2 = (zze) entry.getValue();
            if (zze2.zze()) {
                zze = zze2;
            }
            if (zze2.l_()) {
                arrayMap.put((zzc) entry.getKey(), zze2);
            } else {
                arrayMap2.put((zzc) entry.getKey(), zze2);
            }
        }
        zzbq.zza(arrayMap.isEmpty() ^ 1, "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        Map arrayMap3 = new ArrayMap();
        Map arrayMap4 = new ArrayMap();
        for (Api api : map2.keySet()) {
            zzc zzc = api.zzc();
            if (arrayMap.containsKey(zzc)) {
                arrayMap3.put(api, (Boolean) map3.get(api));
            } else if (arrayMap2.containsKey(zzc)) {
                arrayMap4.put(api, (Boolean) map3.get(api));
            } else {
                throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = arrayList;
        int size = arrayList4.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList4.get(i);
            i++;
            zzt zzt = (zzt) obj;
            if (arrayMap3.containsKey(zzt.zza)) {
                arrayList2.add(zzt);
            } else if (arrayMap4.containsKey(zzt.zza)) {
                arrayList3.add(zzt);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }
        }
        return new zzv(context, zzba, lock, looper, zzf, arrayMap, arrayMap2, zzr, zza, zze, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    private final void zza(int i, boolean z) {
        this.zzb.zza(i, z);
        this.zzk = null;
        this.zzj = null;
    }

    private final void zza(Bundle bundle) {
        if (this.zzi == null) {
            this.zzi = bundle;
            return;
        }
        if (bundle != null) {
            this.zzi.putAll(bundle);
        }
    }

    private final void zza(ConnectionResult connectionResult) {
        switch (this.zzn) {
            case 1:
                break;
            case 2:
                this.zzb.zza(connectionResult);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        zzi();
        this.zzn = 0;
    }

    private static boolean zzb(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    private final boolean zzc(zzm<? extends Result, ? extends zzb> zzm) {
        zzc zzc = zzm.zzc();
        zzbq.zzb(this.zzf.containsKey(zzc), "GoogleApiClient is not configured to use the API required for this call.");
        return ((zzbi) this.zzf.get(zzc)).equals(this.zze);
    }

    private final void zzh() {
        if (zzb(this.zzj)) {
            if (!zzb(this.zzk)) {
                if (!zzj()) {
                    if (this.zzk != null) {
                        if (this.zzn == 1) {
                            zzi();
                            return;
                        }
                        zza(this.zzk);
                        this.zzd.zzc();
                        return;
                    }
                }
            }
            switch (this.zzn) {
                case 1:
                    break;
                case 2:
                    this.zzb.zza(this.zzi);
                    break;
                default:
                    Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                    break;
            }
            zzi();
            this.zzn = 0;
        } else if (this.zzj != null && zzb(this.zzk)) {
            this.zze.zzc();
            zza(this.zzj);
        } else if (!(this.zzj == null || this.zzk == null)) {
            ConnectionResult connectionResult = this.zzj;
            if (this.zze.zzc < this.zzd.zzc) {
                connectionResult = this.zzk;
            }
            zza(connectionResult);
        }
    }

    private final void zzi() {
        for (zzcu zza : this.zzg) {
            zza.zza();
        }
        this.zzg.clear();
    }

    private final boolean zzj() {
        return this.zzk != null && this.zzk.getErrorCode() == 4;
    }

    @Nullable
    private final PendingIntent zzk() {
        return this.zzh == null ? null : PendingIntent.getActivity(this.zza, System.identityHashCode(this.zzb), this.zzh.zzf(), 134217728);
    }

    public final ConnectionResult zza(long j, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public final ConnectionResult zza(@NonNull Api<?> api) {
        return ((zzbi) this.zzf.get(api.zzc())).equals(this.zze) ? zzj() ? new ConnectionResult(4, zzk()) : this.zze.zza((Api) api) : this.zzd.zza((Api) api);
    }

    public final <A extends zzb, R extends Result, T extends zzm<R, A>> T zza(@NonNull T t) {
        if (!zzc((zzm) t)) {
            return this.zzd.zza((zzm) t);
        }
        if (!zzj()) {
            return this.zze.zza((zzm) t);
        }
        t.zzc(new Status(4, null, zzk()));
        return t;
    }

    public final void zza() {
        this.zzn = 2;
        this.zzl = false;
        this.zzk = null;
        this.zzj = null;
        this.zzd.zza();
        this.zze.zza();
    }

    public final void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("authClient").println(":");
        this.zze.zza(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(":");
        this.zzd.zza(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    public final boolean zza(zzcu zzcu) {
        boolean z;
        this.zzm.lock();
        try {
            if ((zze() || zzd()) && !this.zze.zzd()) {
                this.zzg.add(zzcu);
                z = true;
                if (this.zzn == 0) {
                    this.zzn = 1;
                }
                this.zzk = null;
                this.zze.zza();
                return z;
            }
            this.zzm.unlock();
            return false;
        } finally {
            z = this.zzm;
            z.unlock();
        }
    }

    public final ConnectionResult zzb() {
        throw new UnsupportedOperationException();
    }

    public final <A extends zzb, T extends zzm<? extends Result, A>> T zzb(@NonNull T t) {
        if (!zzc((zzm) t)) {
            return this.zzd.zzb((zzm) t);
        }
        if (!zzj()) {
            return this.zze.zzb((zzm) t);
        }
        t.zzc(new Status(4, null, zzk()));
        return t;
    }

    public final void zzc() {
        this.zzk = null;
        this.zzj = null;
        this.zzn = 0;
        this.zzd.zzc();
        this.zze.zzc();
        zzi();
    }

    public final boolean zzd() {
        this.zzm.lock();
        try {
            boolean z = true;
            if (this.zzd.zzd()) {
                if (!(this.zze.zzd() || zzj())) {
                    if (this.zzn == 1) {
                    }
                }
                this.zzm.unlock();
                return z;
            }
            z = false;
            this.zzm.unlock();
            return z;
        } catch (Throwable th) {
            this.zzm.unlock();
        }
    }

    public final boolean zze() {
        this.zzm.lock();
        try {
            boolean z = this.zzn == 2;
            this.zzm.unlock();
            return z;
        } catch (Throwable th) {
            this.zzm.unlock();
        }
    }

    public final void zzf() {
        this.zzd.zzf();
        this.zze.zzf();
    }

    public final void zzg() {
        this.zzm.lock();
        try {
            boolean zze = zze();
            this.zze.zzc();
            this.zzk = new ConnectionResult(4);
            if (zze) {
                new Handler(this.zzc).post(new zzw(this));
            } else {
                zzi();
            }
            this.zzm.unlock();
        } catch (Throwable th) {
            this.zzm.unlock();
        }
    }
}
