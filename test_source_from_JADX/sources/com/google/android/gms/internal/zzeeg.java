package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class zzeeg<A, B, C> {
    private final List<A> zza;
    private final Map<B, C> zzb;
    private final zzedt<A, B> zzc;
    private zzeed<A, C> zzd;
    private zzeed<A, C> zze;

    private zzeeg(List<A> list, Map<B, C> map, zzedt<A, B> zzedt) {
        this.zza = list;
        this.zzb = map;
        this.zzc = zzedt;
    }

    private final zzedz<A, C> zza(int i, int i2) {
        if (i2 == 0) {
            return zzedy.zza();
        }
        if (i2 == 1) {
            Object obj = this.zza.get(i);
            return new zzedx(obj, zza(obj), null, null);
        }
        i2 /= 2;
        int i3 = i + i2;
        zzedz zza = zza(i, i2);
        zzedz zza2 = zza(i3 + 1, i2);
        Object obj2 = this.zza.get(i3);
        return new zzedx(obj2, zza(obj2), zza, zza2);
    }

    public static <A, B, C> zzeee<A, C> zza(List<A> list, Map<B, C> map, zzedt<A, B> zzedt, Comparator<A> comparator) {
        zzeeg zzeeg = new zzeeg(list, map, zzedt);
        Collections.sort(list, comparator);
        Iterator it = new zzeeh(list.size()).iterator();
        int size = list.size();
        while (it.hasNext()) {
            int i;
            zzeej zzeej = (zzeej) it.next();
            size -= zzeej.zzb;
            if (zzeej.zza) {
                i = zzeea.zzb;
            } else {
                zzeeg.zza(zzeea.zzb, zzeej.zzb, size);
                size -= zzeej.zzb;
                i = zzeea.zza;
            }
            zzeeg.zza(i, zzeej.zzb, size);
        }
        return new zzeee(zzeeg.zzd == null ? zzedy.zza() : zzeeg.zzd, comparator);
    }

    private final C zza(A a) {
        return this.zzb.get(this.zzc.zza(a));
    }

    private final void zza(int i, int i2, int i3) {
        zzedz zza = zza(i3 + 1, i2 - 1);
        Object obj = this.zza.get(i3);
        zzedz zzeec = i == zzeea.zza ? new zzeec(obj, zza(obj), null, zza) : new zzedx(obj, zza(obj), null, zza);
        if (this.zzd == null) {
            this.zzd = zzeec;
        } else {
            this.zze.zza(zzeec);
        }
        this.zze = zzeec;
    }
}
