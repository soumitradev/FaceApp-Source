package com.google.android.gms.internal;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzedp implements Iterator<Entry<K, V>> {
    private int zza = this.zzb;
    private /* synthetic */ int zzb;
    private /* synthetic */ boolean zzc;
    private /* synthetic */ zzedo zzd;

    zzedp(zzedo zzedo, int i, boolean z) {
        this.zzd = zzedo;
        this.zzb = i;
        this.zzc = z;
    }

    public final boolean hasNext() {
        return this.zzc ? this.zza >= 0 : this.zza < this.zzd.zza.length;
    }

    public final /* synthetic */ Object next() {
        Object obj = this.zzd.zza[this.zza];
        Object obj2 = this.zzd.zzb[this.zza];
        this.zza = this.zzc ? this.zza - 1 : this.zza + 1;
        return new SimpleImmutableEntry(obj, obj2);
    }

    public final void remove() {
        throw new UnsupportedOperationException("Can't remove elements from ImmutableSortedMap");
    }
}
