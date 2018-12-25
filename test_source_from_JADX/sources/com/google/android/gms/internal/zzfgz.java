package com.google.android.gms.internal;

import java.io.IOException;

class zzfgz extends zzfgy {
    protected final byte[] zzb;

    zzfgz(byte[] bArr) {
        this.zzb = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfgs) || zza() != ((zzfgs) obj).zza()) {
            return false;
        }
        if (zza() == 0) {
            return true;
        }
        if (!(obj instanceof zzfgz)) {
            return obj.equals(this);
        }
        zzfgz zzfgz = (zzfgz) obj;
        int zzg = zzg();
        int zzg2 = zzfgz.zzg();
        return (zzg == 0 || zzg2 == 0 || zzg == zzg2) ? zza(zzfgz, 0, zza()) : false;
    }

    public byte zza(int i) {
        return this.zzb[i];
    }

    public int zza() {
        return this.zzb.length;
    }

    protected final int zza(int i, int i2, int i3) {
        return zzfhz.zza(i, this.zzb, zzh() + i2, i3);
    }

    public final zzfgs zza(int i, int i2) {
        i2 = zzfgs.zzb(i, i2, zza());
        return i2 == 0 ? zzfgs.zza : new zzfgv(this.zzb, zzh() + i, i2);
    }

    final void zza(zzfgr zzfgr) throws IOException {
        zzfgr.zza(this.zzb, zzh(), zza());
    }

    final boolean zza(zzfgs zzfgs, int i, int i2) {
        if (i2 > zzfgs.zza()) {
            i = zza();
            StringBuilder stringBuilder = new StringBuilder(40);
            stringBuilder.append("Length too large: ");
            stringBuilder.append(i2);
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        int i3 = i + i2;
        int zza;
        if (i3 > zzfgs.zza()) {
            zza = zzfgs.zza();
            StringBuilder stringBuilder2 = new StringBuilder(59);
            stringBuilder2.append("Ran off end of other: ");
            stringBuilder2.append(i);
            stringBuilder2.append(", ");
            stringBuilder2.append(i2);
            stringBuilder2.append(", ");
            stringBuilder2.append(zza);
            throw new IllegalArgumentException(stringBuilder2.toString());
        } else if (!(zzfgs instanceof zzfgz)) {
            return zzfgs.zza(i, i3).equals(zza(0, i2));
        } else {
            zzfgz zzfgz = (zzfgz) zzfgs;
            byte[] bArr = this.zzb;
            byte[] bArr2 = zzfgz.zzb;
            int zzh = zzh() + i2;
            i2 = zzh();
            zza = zzfgz.zzh() + i;
            while (i2 < zzh) {
                if (bArr[i2] != bArr2[zza]) {
                    return false;
                }
                i2++;
                zza++;
            }
            return true;
        }
    }

    protected void zzb(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzb, i, bArr, i2, i3);
    }

    public final zzfhb zzd() {
        return zzfhb.zza(this.zzb, zzh(), zza(), true);
    }

    protected int zzh() {
        return 0;
    }
}
