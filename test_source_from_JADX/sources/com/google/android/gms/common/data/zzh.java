package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Hide;
import java.util.NoSuchElementException;

@Hide
public final class zzh<T> extends zzb<T> {
    private T zzc;

    public zzh(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    public final T next() {
        if (hasNext()) {
            this.zzb++;
            if (this.zzb == 0) {
                this.zzc = this.zza.get(0);
                if (!(this.zzc instanceof zzc)) {
                    String valueOf = String.valueOf(this.zzc.getClass());
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 44);
                    stringBuilder.append("DataBuffer reference of type ");
                    stringBuilder.append(valueOf);
                    stringBuilder.append(" is not movable");
                    throw new IllegalStateException(stringBuilder.toString());
                }
            }
            ((zzc) this.zzc).zza(this.zzb);
            return this.zzc;
        }
        int i = this.zzb;
        stringBuilder = new StringBuilder(46);
        stringBuilder.append("Cannot advance the iterator beyond ");
        stringBuilder.append(i);
        throw new NoSuchElementException(stringBuilder.toString());
    }
}
