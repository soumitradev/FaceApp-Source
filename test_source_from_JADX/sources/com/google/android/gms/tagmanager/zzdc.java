package com.google.android.gms.tagmanager;

import android.annotation.TargetApi;
import android.util.LruCache;

@TargetApi(12)
final class zzdc<K, V> implements zzp<K, V> {
    private LruCache<K, V> zza;

    zzdc(int i, zzs<K, V> zzs) {
        this.zza = new zzdd(this, 1048576, zzs);
    }

    public final V zza(K k) {
        return this.zza.get(k);
    }

    public final void zza(K k, V v) {
        this.zza.put(k, v);
    }
}
