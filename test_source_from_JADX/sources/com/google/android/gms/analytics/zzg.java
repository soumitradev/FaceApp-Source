package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Hide
public final class zzg {
    private final zzj zza;
    private final zze zzb;
    private boolean zzc;
    private long zzd;
    private long zze;
    private long zzf;
    private long zzg;
    private long zzh;
    private boolean zzi;
    private final Map<Class<? extends zzi>, zzi> zzj;
    private final List<zzo> zzk;

    private zzg(zzg zzg) {
        this.zza = zzg.zza;
        this.zzb = zzg.zzb;
        this.zzd = zzg.zzd;
        this.zze = zzg.zze;
        this.zzf = zzg.zzf;
        this.zzg = zzg.zzg;
        this.zzh = zzg.zzh;
        this.zzk = new ArrayList(zzg.zzk);
        this.zzj = new HashMap(zzg.zzj.size());
        for (Entry entry : zzg.zzj.entrySet()) {
            zzi zzc = zzc((Class) entry.getKey());
            ((zzi) entry.getValue()).zza(zzc);
            this.zzj.put((Class) entry.getKey(), zzc);
        }
    }

    zzg(zzj zzj, zze zze) {
        zzbq.zza(zzj);
        zzbq.zza(zze);
        this.zza = zzj;
        this.zzb = zze;
        this.zzg = 1800000;
        this.zzh = 3024000000L;
        this.zzj = new HashMap();
        this.zzk = new ArrayList();
    }

    @Hide
    @TargetApi(19)
    private static <T extends zzi> T zzc(Class<T> cls) {
        try {
            return (zzi) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable e) {
            if (e instanceof InstantiationException) {
                throw new IllegalArgumentException("dataType doesn't have default constructor", e);
            } else if (e instanceof IllegalAccessException) {
                throw new IllegalArgumentException("dataType default constructor is not accessible", e);
            } else if (VERSION.SDK_INT < 19 || !(e instanceof ReflectiveOperationException)) {
                throw new RuntimeException(e);
            } else {
                throw new IllegalArgumentException("Linkage exception", e);
            }
        }
    }

    public final zzg zza() {
        return new zzg(this);
    }

    @Hide
    public final <T extends zzi> T zza(Class<T> cls) {
        return (zzi) this.zzj.get(cls);
    }

    @Hide
    public final void zza(long j) {
        this.zze = j;
    }

    @Hide
    public final void zza(zzi zzi) {
        zzbq.zza(zzi);
        Class cls = zzi.getClass();
        if (cls.getSuperclass() != zzi.class) {
            throw new IllegalArgumentException();
        }
        zzi.zza(zzb(cls));
    }

    @Hide
    public final <T extends zzi> T zzb(Class<T> cls) {
        zzi zzi = (zzi) this.zzj.get(cls);
        if (zzi != null) {
            return zzi;
        }
        T zzc = zzc(cls);
        this.zzj.put(cls, zzc);
        return zzc;
    }

    @Hide
    public final Collection<zzi> zzb() {
        return this.zzj.values();
    }

    public final List<zzo> zzc() {
        return this.zzk;
    }

    public final long zzd() {
        return this.zzd;
    }

    public final void zze() {
        this.zza.zzf().zza(this);
    }

    public final boolean zzf() {
        return this.zzc;
    }

    final void zzg() {
        this.zzf = this.zzb.zzb();
        this.zzd = this.zze != 0 ? this.zze : this.zzb.zza();
        this.zzc = true;
    }

    final zzj zzh() {
        return this.zza;
    }

    final boolean zzi() {
        return this.zzi;
    }

    final void zzj() {
        this.zzi = true;
    }
}
