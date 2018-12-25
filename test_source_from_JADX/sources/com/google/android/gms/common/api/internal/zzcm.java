package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzcm {
    private final Set<zzci<?>> zza = Collections.newSetFromMap(new WeakHashMap());

    public static <L> zzck<L> zza(@NonNull L l, @NonNull String str) {
        zzbq.zza(l, "Listener must not be null");
        zzbq.zza(str, "Listener type must not be null");
        zzbq.zza(str, "Listener type must not be empty");
        return new zzck(l, str);
    }

    public static <L> zzci<L> zzb(@NonNull L l, @NonNull Looper looper, @NonNull String str) {
        zzbq.zza(l, "Listener must not be null");
        zzbq.zza(looper, "Looper must not be null");
        zzbq.zza(str, "Listener type must not be null");
        return new zzci(looper, l, str);
    }

    public final <L> zzci<L> zza(@NonNull L l, @NonNull Looper looper, @NonNull String str) {
        zzci<L> zzb = zzb(l, looper, str);
        this.zza.add(zzb);
        return zzb;
    }

    public final void zza() {
        for (zzci zzb : this.zza) {
            zzb.zzb();
        }
        this.zza.clear();
    }
}
