package com.google.android.gms.tagmanager;

import android.util.LruCache;

final class zzdd extends LruCache<K, V> {
    private /* synthetic */ zzs zza;

    zzdd(zzdc zzdc, int i, zzs zzs) {
        this.zza = zzs;
        super(i);
    }

    protected final int sizeOf(K k, V v) {
        return this.zza.zza(k, v);
    }
}
