package com.google.android.gms.internal;

public final class zzflo implements Cloneable {
    private static final zzflp zza = new zzflp();
    private boolean zzb;
    private int[] zzc;
    private zzflp[] zzd;
    private int zze;

    zzflo() {
        this(10);
    }

    private zzflo(int i) {
        this.zzb = false;
        i = zzc(i);
        this.zzc = new int[i];
        this.zzd = new zzflp[i];
        this.zze = 0;
    }

    private static int zzc(int i) {
        i <<= 2;
        for (int i2 = 4; i2 < 32; i2++) {
            int i3 = (1 << i2) - 12;
            if (i <= i3) {
                i = i3;
                break;
            }
        }
        return i / 4;
    }

    private final int zzd(int i) {
        int i2 = this.zze - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzc[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i2 = i4 - 1;
            }
        }
        return i3 ^ -1;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.zze;
        zzflo zzflo = new zzflo(i);
        int i2 = 0;
        System.arraycopy(this.zzc, 0, zzflo.zzc, 0, i);
        while (i2 < i) {
            if (this.zzd[i2] != null) {
                zzflo.zzd[i2] = (zzflp) this.zzd[i2].clone();
            }
            i2++;
        }
        zzflo.zze = i;
        return zzflo;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzflo)) {
            return false;
        }
        zzflo zzflo = (zzflo) obj;
        if (this.zze != zzflo.zze) {
            return false;
        }
        Object obj2;
        int[] iArr = this.zzc;
        int[] iArr2 = zzflo.zzc;
        int i = this.zze;
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                obj2 = null;
                break;
            }
        }
        obj2 = 1;
        if (obj2 != null) {
            zzflp[] zzflpArr = this.zzd;
            zzflp[] zzflpArr2 = zzflo.zzd;
            int i3 = this.zze;
            for (i = 0; i < i3; i++) {
                if (!zzflpArr[i].equals(zzflpArr2[i])) {
                    obj = null;
                    break;
                }
            }
            obj = 1;
            if (obj != null) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.zze; i2++) {
            i = (((i * 31) + this.zzc[i2]) * 31) + this.zzd[i2].hashCode();
        }
        return i;
    }

    final int zza() {
        return this.zze;
    }

    final zzflp zza(int i) {
        i = zzd(i);
        if (i >= 0) {
            if (this.zzd[i] != zza) {
                return this.zzd[i];
            }
        }
        return null;
    }

    final void zza(int i, zzflp zzflp) {
        int zzd = zzd(i);
        if (zzd >= 0) {
            this.zzd[zzd] = zzflp;
            return;
        }
        zzd ^= -1;
        if (zzd >= this.zze || this.zzd[zzd] != zza) {
            if (this.zze >= this.zzc.length) {
                int zzc = zzc(this.zze + 1);
                Object obj = new int[zzc];
                Object obj2 = new zzflp[zzc];
                System.arraycopy(this.zzc, 0, obj, 0, this.zzc.length);
                System.arraycopy(this.zzd, 0, obj2, 0, this.zzd.length);
                this.zzc = obj;
                this.zzd = obj2;
            }
            if (this.zze - zzd != 0) {
                int i2 = zzd + 1;
                System.arraycopy(this.zzc, zzd, this.zzc, i2, this.zze - zzd);
                System.arraycopy(this.zzd, zzd, this.zzd, i2, this.zze - zzd);
            }
            this.zzc[zzd] = i;
            this.zzd[zzd] = zzflp;
            this.zze++;
            return;
        }
        this.zzc[zzd] = i;
        this.zzd[zzd] = zzflp;
    }

    final zzflp zzb(int i) {
        return this.zzd[i];
    }

    public final boolean zzb() {
        return this.zze == 0;
    }
}
