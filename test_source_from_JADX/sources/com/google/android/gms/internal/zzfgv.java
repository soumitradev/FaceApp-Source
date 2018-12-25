package com.google.android.gms.internal;

final class zzfgv extends zzfgz {
    private final int zzc;
    private final int zzd;

    zzfgv(byte[] bArr, int i, int i2) {
        super(bArr);
        zzfgs.zzb(i, i + i2, bArr.length);
        this.zzc = i;
        this.zzd = i2;
    }

    public final byte zza(int i) {
        zzfgs.zzb(i, zza());
        return this.zzb[this.zzc + i];
    }

    public final int zza() {
        return this.zzd;
    }

    protected final void zzb(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzb, zzh() + i, bArr, i2, i3);
    }

    protected final int zzh() {
        return this.zzc;
    }
}
