package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhu.zzg;
import java.io.IOException;
import java.util.Arrays;

public final class zzfko {
    private static final zzfko zza = new zzfko(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzfko() {
        this(0, new int[8], new Object[8], true);
    }

    private zzfko(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzfko zza() {
        return zza;
    }

    static zzfko zza(zzfko zzfko, zzfko zzfko2) {
        int i = zzfko.zzb + zzfko2.zzb;
        Object copyOf = Arrays.copyOf(zzfko.zzc, i);
        System.arraycopy(zzfko2.zzc, 0, copyOf, zzfko.zzb, zzfko2.zzb);
        Object copyOf2 = Arrays.copyOf(zzfko.zzd, i);
        System.arraycopy(zzfko2.zzd, 0, copyOf2, zzfko.zzb, zzfko2.zzb);
        return new zzfko(i, copyOf, copyOf2, true);
    }

    private void zza(int i, Object obj) {
        zzf();
        if (this.zzb == this.zzc.length) {
            int i2 = this.zzb + (this.zzb < 4 ? 8 : this.zzb >> 1);
            this.zzc = Arrays.copyOf(this.zzc, i2);
            this.zzd = Arrays.copyOf(this.zzd, i2);
        }
        this.zzc[this.zzb] = i;
        this.zzd[this.zzb] = obj;
        this.zzb++;
    }

    static zzfko zzb() {
        return new zzfko();
    }

    private final void zzf() {
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzfko)) {
            return false;
        }
        zzfko zzfko = (zzfko) obj;
        if (this.zzb == zzfko.zzb) {
            Object obj2;
            int[] iArr = this.zzc;
            int[] iArr2 = zzfko.zzc;
            int i = this.zzb;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] != iArr2[i2]) {
                    obj2 = null;
                    break;
                }
            }
            obj2 = 1;
            if (obj2 != null) {
                Object[] objArr = this.zzd;
                Object[] objArr2 = zzfko.zzd;
                int i3 = this.zzb;
                for (i = 0; i < i3; i++) {
                    if (!objArr[i].equals(objArr2[i])) {
                        obj = null;
                        break;
                    }
                }
                obj = 1;
                return obj != null;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((((this.zzb + 527) * 31) + Arrays.hashCode(this.zzc)) * 31) + Arrays.deepHashCode(this.zzd);
    }

    public final void zza(zzfhg zzfhg) throws IOException {
        for (int i = 0; i < this.zzb; i++) {
            int i2 = this.zzc[i];
            int i3 = i2 >>> 3;
            i2 &= 7;
            if (i2 != 5) {
                switch (i2) {
                    case 0:
                        zzfhg.zza(i3, ((Long) this.zzd[i]).longValue());
                        break;
                    case 1:
                        zzfhg.zzb(i3, ((Long) this.zzd[i]).longValue());
                        break;
                    case 2:
                        zzfhg.zza(i3, (zzfgs) this.zzd[i]);
                        break;
                    case 3:
                        zzfhg.zza(i3, 3);
                        ((zzfko) this.zzd[i]).zza(zzfhg);
                        zzfhg.zza(i3, 4);
                        break;
                    default:
                        throw zzfie.zzf();
                }
            }
            zzfhg.zzd(i3, ((Integer) this.zzd[i]).intValue());
        }
    }

    final void zza(zzfli zzfli) {
        int i;
        if (zzfli.zza() == zzg.zzm) {
            for (i = this.zzb - 1; i >= 0; i--) {
                zzfli.zza(this.zzc[i] >>> 3, this.zzd[i]);
            }
            return;
        }
        for (i = 0; i < this.zzb; i++) {
            zzfli.zza(this.zzc[i] >>> 3, this.zzd[i]);
        }
    }

    final void zza(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzfjf.zza(stringBuilder, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final boolean zza(int i, zzfhb zzfhb) throws IOException {
        zzf();
        int i2 = i >>> 3;
        switch (i & 7) {
            case 0:
                zza(i, Long.valueOf(zzfhb.zze()));
                return true;
            case 1:
                zza(i, Long.valueOf(zzfhb.zzg()));
                return true;
            case 2:
                zza(i, zzfhb.zzl());
                return true;
            case 3:
                Object zzfko = new zzfko();
                int zza;
                do {
                    zza = zzfhb.zza();
                    if (zza != 0) {
                    }
                    zzfhb.zza((i2 << 3) | 4);
                    zza(i, zzfko);
                    return true;
                } while (zzfko.zza(zza, zzfhb));
                zzfhb.zza((i2 << 3) | 4);
                zza(i, zzfko);
                return true;
            case 4:
                return false;
            case 5:
                zza(i, Integer.valueOf(zzfhb.zzh()));
                return true;
            default:
                throw zzfie.zzf();
        }
    }

    public final void zzc() {
        this.zzf = false;
    }

    public final int zzd() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.zzb; i++) {
            i2 += zzfhg.zzd(this.zzc[i] >>> 3, (zzfgs) this.zzd[i]);
        }
        this.zze = i2;
        return i2;
    }

    public final int zze() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.zzb; i++) {
            int i3 = this.zzc[i];
            int i4 = i3 >>> 3;
            i3 &= 7;
            if (i3 != 5) {
                switch (i3) {
                    case 0:
                        i3 = zzfhg.zzd(i4, ((Long) this.zzd[i]).longValue());
                        break;
                    case 1:
                        i3 = zzfhg.zze(i4, ((Long) this.zzd[i]).longValue());
                        break;
                    case 2:
                        i3 = zzfhg.zzc(i4, (zzfgs) this.zzd[i]);
                        break;
                    case 3:
                        i3 = (zzfhg.zzf(i4) << 1) + ((zzfko) this.zzd[i]).zze();
                        break;
                    default:
                        throw new IllegalStateException(zzfie.zzf());
                }
            }
            i3 = zzfhg.zzg(i4, ((Integer) this.zzd[i]).intValue());
            i2 += i3;
        }
        this.zze = i2;
        return i2;
    }
}
