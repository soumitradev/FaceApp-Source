package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;

final class zzfjo<E> extends zzfgn<E> {
    private static final zzfjo<Object> zza;
    private final List<E> zzb;

    static {
        zzfgn zzfjo = new zzfjo();
        zza = zzfjo;
        zzfjo.zzb();
    }

    zzfjo() {
        this(new ArrayList(10));
    }

    private zzfjo(List<E> list) {
        this.zzb = list;
    }

    public static <E> zzfjo<E> zzd() {
        return zza;
    }

    public final void add(int i, E e) {
        zzc();
        this.zzb.add(i, e);
        this.modCount++;
    }

    public final E get(int i) {
        return this.zzb.get(i);
    }

    public final E remove(int i) {
        zzc();
        E remove = this.zzb.remove(i);
        this.modCount++;
        return remove;
    }

    public final E set(int i, E e) {
        zzc();
        E e2 = this.zzb.set(i, e);
        this.modCount++;
        return e2;
    }

    public final int size() {
        return this.zzb.size();
    }

    public final /* synthetic */ zzfid zzd(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        List arrayList = new ArrayList(i);
        arrayList.addAll(this.zzb);
        return new zzfjo(arrayList);
    }
}
