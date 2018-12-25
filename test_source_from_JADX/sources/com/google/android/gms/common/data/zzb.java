package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Hide
public class zzb<T> implements Iterator<T> {
    protected final DataBuffer<T> zza;
    protected int zzb = -1;

    public zzb(DataBuffer<T> dataBuffer) {
        this.zza = (DataBuffer) zzbq.zza(dataBuffer);
    }

    public boolean hasNext() {
        return this.zzb < this.zza.getCount() - 1;
    }

    public T next() {
        if (hasNext()) {
            DataBuffer dataBuffer = this.zza;
            int i = this.zzb + 1;
            this.zzb = i;
            return dataBuffer.get(i);
        }
        i = this.zzb;
        StringBuilder stringBuilder = new StringBuilder(46);
        stringBuilder.append("Cannot advance the iterator beyond ");
        stringBuilder.append(i);
        throw new NoSuchElementException(stringBuilder.toString());
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
