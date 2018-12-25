package com.google.android.gms.dynamic;

import java.util.Iterator;

final class zzb implements zzo<T> {
    private /* synthetic */ zza zza;

    zzb(zza zza) {
        this.zza = zza;
    }

    public final void zza(T t) {
        this.zza.zza = t;
        Iterator it = this.zza.zzc.iterator();
        while (it.hasNext()) {
            ((zzi) it.next()).zza(this.zza.zza);
        }
        this.zza.zzc.clear();
        this.zza.zzb = null;
    }
}
