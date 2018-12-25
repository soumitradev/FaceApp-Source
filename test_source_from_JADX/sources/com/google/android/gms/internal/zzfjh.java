package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map.Entry;

final class zzfjh<T> implements zzfjv<T> {
    private final zzfjc zza;
    private final zzfkn<?, ?> zzb;
    private final boolean zzc;
    private final zzfhn<?> zzd;

    private zzfjh(Class<T> cls, zzfkn<?, ?> zzfkn, zzfhn<?> zzfhn, zzfjc zzfjc) {
        this.zzb = zzfkn;
        this.zzc = zzfhn.zza((Class) cls);
        this.zzd = zzfhn;
        this.zza = zzfjc;
    }

    static <T> zzfjh<T> zza(Class<T> cls, zzfkn<?, ?> zzfkn, zzfhn<?> zzfhn, zzfjc zzfjc) {
        return new zzfjh(cls, zzfkn, zzfhn, zzfjc);
    }

    public final int zza(T t) {
        zzfkn zzfkn = this.zzb;
        int zzb = zzfkn.zzb(zzfkn.zza(t)) + 0;
        return this.zzc ? zzb + this.zzd.zza((Object) t).zzc() : zzb;
    }

    public final void zza(T t, zzfli zzfli) {
        Iterator zzb = this.zzd.zza((Object) t).zzb();
        while (zzb.hasNext()) {
            Entry entry = (Entry) zzb.next();
            zzfhs zzfhs = (zzfhs) entry.getKey();
            if (zzfhs.zzc() == zzfld.MESSAGE && !zzfhs.zzd()) {
                if (!zzfhs.zze()) {
                    int zza;
                    Object zzc;
                    if (entry instanceof zzfii) {
                        zza = zzfhs.zza();
                        zzc = ((zzfii) entry).zza().zzc();
                    } else {
                        zza = zzfhs.zza();
                        zzc = entry.getValue();
                    }
                    zzfli.zza(zza, zzc);
                }
            }
            throw new IllegalStateException("Found invalid MessageSet item.");
        }
        zzfkn zzfkn = this.zzb;
        zzfkn.zza(zzfkn.zza(t), zzfli);
    }
}
