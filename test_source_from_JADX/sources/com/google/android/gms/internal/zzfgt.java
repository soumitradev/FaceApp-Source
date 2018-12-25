package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class zzfgt implements Iterator {
    private int zza = 0;
    private final int zzb = this.zzc.zza();
    private /* synthetic */ zzfgs zzc;

    zzfgt(zzfgs zzfgs) {
        this.zzc = zzfgs;
    }

    private final byte zza() {
        try {
            zzfgs zzfgs = this.zzc;
            int i = this.zza;
            this.zza = i + 1;
            return zzfgs.zza(i);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public final boolean hasNext() {
        return this.zza < this.zzb;
    }

    public final /* synthetic */ Object next() {
        return Byte.valueOf(zza());
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
