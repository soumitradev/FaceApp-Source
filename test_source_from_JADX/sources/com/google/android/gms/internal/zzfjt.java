package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

final class zzfjt implements Iterator<zzfgy> {
    private final Stack<zzfjq> zza;
    private zzfgy zzb;

    private zzfjt(zzfgs zzfgs) {
        this.zza = new Stack();
        this.zzb = zza(zzfgs);
    }

    private final zzfgy zza() {
        while (!this.zza.isEmpty()) {
            zzfgs zza = zza(((zzfjq) this.zza.pop()).zze);
            if (!zza.zzb()) {
                return zza;
            }
        }
        return null;
    }

    private final zzfgy zza(zzfgs zzfgs) {
        while (zzfgs instanceof zzfjq) {
            zzfjq zzfjq = (zzfjq) zzfgs;
            this.zza.push(zzfjq);
            zzfgs = zzfjq.zzd;
        }
        return (zzfgy) zzfgs;
    }

    public final boolean hasNext() {
        return this.zzb != null;
    }

    public final /* synthetic */ Object next() {
        if (this.zzb == null) {
            throw new NoSuchElementException();
        }
        zzfgy zzfgy = this.zzb;
        this.zzb = zza();
        return zzfgy;
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
