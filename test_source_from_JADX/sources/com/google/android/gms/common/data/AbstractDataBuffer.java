package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.internal.Hide;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
    @Hide
    protected final DataHolder zza;

    @Hide
    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.zza = dataHolder;
    }

    @Deprecated
    public final void close() {
        release();
    }

    public abstract T get(int i);

    public int getCount() {
        return this.zza == null ? 0 : this.zza.zza;
    }

    @Deprecated
    public boolean isClosed() {
        if (this.zza != null) {
            if (!this.zza.zze()) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new zzb(this);
    }

    public void release() {
        if (this.zza != null) {
            this.zza.close();
        }
    }

    public Iterator<T> singleRefIterator() {
        return new zzh(this);
    }

    @Hide
    public final Bundle zza() {
        return this.zza.zzc();
    }
}
