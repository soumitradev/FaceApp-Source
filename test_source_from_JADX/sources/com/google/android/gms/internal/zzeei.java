package com.google.android.gms.internal;

import java.util.Iterator;

final class zzeei implements Iterator<zzeej> {
    private int zza = (this.zzb.zzb - 1);
    private /* synthetic */ zzeeh zzb;

    zzeei(zzeeh zzeeh) {
        this.zzb = zzeeh;
    }

    public final boolean hasNext() {
        return this.zza >= 0;
    }

    public final /* synthetic */ Object next() {
        long zzb = this.zzb.zza & ((long) (1 << this.zza));
        zzeej zzeej = new zzeej();
        zzeej.zza = zzb == 0;
        zzeej.zzb = (int) Math.pow(2.0d, (double) this.zza);
        this.zza--;
        return zzeej;
    }

    public final void remove() {
    }
}
