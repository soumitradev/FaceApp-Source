package com.google.android.gms.internal;

import java.util.Iterator;

final class zzciv implements Iterator<String> {
    private Iterator<String> zza = this.zzb.zza.keySet().iterator();
    private /* synthetic */ zzciu zzb;

    zzciv(zzciu zzciu) {
        this.zzb = zzciu;
    }

    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    public final /* synthetic */ Object next() {
        return (String) this.zza.next();
    }

    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }
}
