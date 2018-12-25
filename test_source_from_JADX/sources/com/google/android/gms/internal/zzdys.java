package com.google.android.gms.internal;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

final class zzdys {
    private final ConcurrentHashMap<zzdyt, List<Throwable>> zza = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzb = new ReferenceQueue();

    zzdys() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        while (true) {
            Reference poll = this.zzb.poll();
            if (poll != null) {
                this.zza.remove(poll);
            } else {
                return (List) this.zza.get(new zzdyt(th, null));
            }
        }
    }
}
