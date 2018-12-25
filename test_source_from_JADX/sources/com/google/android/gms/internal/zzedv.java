package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class zzedv<T> implements Iterable<T> {
    private final zzedq<T, Void> zza;

    private zzedv(zzedq<T, Void> zzedq) {
        this.zza = zzedq;
    }

    public zzedv(List<T> list, Comparator<T> comparator) {
        this.zza = zzedr.zza(list, Collections.emptyMap(), zzedr.zza(), comparator);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzedv)) {
            return false;
        }
        return this.zza.equals(((zzedv) obj).zza);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator<T> iterator() {
        return new zzedw(this.zza.iterator());
    }

    public final T zza() {
        return this.zza.zza();
    }

    public final boolean zza(T t) {
        return this.zza.zza((Object) t);
    }

    public final zzedv<T> zzb(T t) {
        zzedq zzc = this.zza.zzc(t);
        return zzc == this.zza ? this : new zzedv(zzc);
    }

    public final T zzb() {
        return this.zza.zzb();
    }

    public final int zzc() {
        return this.zza.zzc();
    }

    public final zzedv<T> zzc(T t) {
        return new zzedv(this.zza.zza(t, null));
    }

    public final Iterator<T> zzd(T t) {
        return new zzedw(this.zza.zzd(t));
    }

    public final boolean zzd() {
        return this.zza.zzd();
    }

    public final T zze(T t) {
        return this.zza.zze(t);
    }

    public final Iterator<T> zze() {
        return new zzedw(this.zza.zze());
    }

    public final int zzf(T t) {
        return this.zza.zzf(t);
    }
}
