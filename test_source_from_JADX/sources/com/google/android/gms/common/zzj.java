package com.google.android.gms.common;

import com.google.android.gms.common.internal.Hide;
import java.lang.ref.WeakReference;

@Hide
abstract class zzj extends zzh {
    private static final WeakReference<byte[]> zzb = new WeakReference(null);
    private WeakReference<byte[]> zza = zzb;

    zzj(byte[] bArr) {
        super(bArr);
    }

    final byte[] zza() {
        byte[] bArr;
        synchronized (this) {
            bArr = (byte[]) this.zza.get();
            if (bArr == null) {
                bArr = zzd();
                this.zza = new WeakReference(bArr);
            }
        }
        return bArr;
    }

    protected abstract byte[] zzd();
}
