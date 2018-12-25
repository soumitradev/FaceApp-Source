package com.google.android.gms.internal;

import java.util.Iterator;

final class zzeeh implements Iterable<zzeej> {
    private long zza;
    private final int zzb;

    public zzeeh(int i) {
        i++;
        this.zzb = (int) Math.floor(Math.log((double) i) / Math.log(2.0d));
        this.zza = ((long) i) & (((long) Math.pow(2.0d, (double) this.zzb)) - 1);
    }

    public final Iterator<zzeej> iterator() {
        return new zzeei(this);
    }
}
