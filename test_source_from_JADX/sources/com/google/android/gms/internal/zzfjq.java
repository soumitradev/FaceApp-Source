package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class zzfjq extends zzfgs {
    private static final int[] zzb;
    private final int zzc;
    private final zzfgs zzd;
    private final zzfgs zze;
    private final int zzf;
    private final int zzg;

    static {
        List arrayList = new ArrayList();
        int i = 1;
        int i2 = 1;
        while (i > 0) {
            arrayList.add(Integer.valueOf(i));
            int i3 = i2 + i;
            i2 = i;
            i = i3;
        }
        arrayList.add(Integer.valueOf(Integer.MAX_VALUE));
        zzb = new int[arrayList.size()];
        for (i = 0; i < zzb.length; i++) {
            zzb[i] = ((Integer) arrayList.get(i)).intValue();
        }
    }

    private zzfjq(zzfgs zzfgs, zzfgs zzfgs2) {
        this.zzd = zzfgs;
        this.zze = zzfgs2;
        this.zzf = zzfgs.zza();
        this.zzc = this.zzf + zzfgs2.zza();
        this.zzg = Math.max(zzfgs.zze(), zzfgs2.zze()) + 1;
    }

    static zzfgs zza(zzfgs zzfgs, zzfgs zzfgs2) {
        if (zzfgs2.zza() == 0) {
            return zzfgs;
        }
        if (zzfgs.zza() == 0) {
            return zzfgs2;
        }
        int zza = zzfgs.zza() + zzfgs2.zza();
        if (zza < 128) {
            return zzb(zzfgs, zzfgs2);
        }
        if (zzfgs instanceof zzfjq) {
            zzfjq zzfjq = (zzfjq) zzfgs;
            if (zzfjq.zze.zza() + zzfgs2.zza() < 128) {
                return new zzfjq(zzfjq.zzd, zzb(zzfjq.zze, zzfgs2));
            } else if (zzfjq.zzd.zze() > zzfjq.zze.zze() && zzfjq.zze() > zzfgs2.zze()) {
                return new zzfjq(zzfjq.zzd, new zzfjq(zzfjq.zze, zzfgs2));
            }
        }
        return zza >= zzb[Math.max(zzfgs.zze(), zzfgs2.zze()) + 1] ? new zzfjq(zzfgs, zzfgs2) : new zzfjs().zza(zzfgs, zzfgs2);
    }

    private static zzfgs zzb(zzfgs zzfgs, zzfgs zzfgs2) {
        int zza = zzfgs.zza();
        int zza2 = zzfgs2.zza();
        byte[] bArr = new byte[(zza + zza2)];
        zzfgs.zza(bArr, 0, 0, zza);
        zzfgs2.zza(bArr, 0, zza, zza2);
        return zzfgs.zzb(bArr);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfgs)) {
            return false;
        }
        zzfgs zzfgs = (zzfgs) obj;
        if (this.zzc != zzfgs.zza()) {
            return false;
        }
        if (this.zzc == 0) {
            return true;
        }
        int zzg = zzg();
        int zzg2 = zzfgs.zzg();
        if (zzg != 0 && zzg2 != 0 && zzg != zzg2) {
            return false;
        }
        Iterator zzfjt = new zzfjt(this);
        zzfgs zzfgs2 = (zzfgy) zzfjt.next();
        Iterator zzfjt2 = new zzfjt(zzfgs);
        zzfgs zzfgs3 = (zzfgy) zzfjt2.next();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int zza = zzfgs2.zza() - i;
            int zza2 = zzfgs3.zza() - i2;
            int min = Math.min(zza, zza2);
            if (!(i == 0 ? zzfgs2.zza(zzfgs3, i2, min) : zzfgs3.zza(zzfgs2, i, min))) {
                return false;
            }
            i3 += min;
            if (i3 >= this.zzc) {
                break;
            }
            if (min == zza) {
                zzfgs2 = (zzfgy) zzfjt.next();
                i = 0;
            } else {
                i += min;
            }
            if (min == zza2) {
                zzfgs3 = (zzfgy) zzfjt2.next();
                i2 = 0;
            } else {
                i2 += min;
            }
        }
        if (i3 == this.zzc) {
            return true;
        }
        throw new IllegalStateException();
    }

    public final byte zza(int i) {
        zzfgs zzfgs;
        zzfgs.zzb(i, this.zzc);
        if (i < this.zzf) {
            zzfgs = this.zzd;
        } else {
            zzfgs = this.zze;
            i -= this.zzf;
        }
        return zzfgs.zza(i);
    }

    public final int zza() {
        return this.zzc;
    }

    protected final int zza(int i, int i2, int i3) {
        if (i2 + i3 <= this.zzf) {
            return this.zzd.zza(i, i2, i3);
        }
        if (i2 >= this.zzf) {
            return this.zze.zza(i, i2 - this.zzf, i3);
        }
        int i4 = this.zzf - i2;
        return this.zze.zza(this.zzd.zza(i, i2, i4), 0, i3 - i4);
    }

    public final zzfgs zza(int i, int i2) {
        int zzb = zzfgs.zzb(i, i2, this.zzc);
        if (zzb == 0) {
            return zzfgs.zza;
        }
        if (zzb == this.zzc) {
            return this;
        }
        zzfgs zzfgs;
        if (i2 <= this.zzf) {
            zzfgs = this.zzd;
        } else if (i >= this.zzf) {
            zzfgs = this.zze;
            i -= this.zzf;
            i2 -= this.zzf;
        } else {
            zzfgs = this.zzd;
            return new zzfjq(zzfgs.zza(i, zzfgs.zza()), this.zze.zza(0, i2 - this.zzf));
        }
        return zzfgs.zza(i, i2);
    }

    final void zza(zzfgr zzfgr) throws IOException {
        this.zzd.zza(zzfgr);
        this.zze.zza(zzfgr);
    }

    protected final void zzb(byte[] bArr, int i, int i2, int i3) {
        if (i + i3 <= this.zzf) {
            this.zzd.zzb(bArr, i, i2, i3);
        } else if (i >= this.zzf) {
            this.zze.zzb(bArr, i - this.zzf, i2, i3);
        } else {
            int i4 = this.zzf - i;
            this.zzd.zzb(bArr, i, i2, i4);
            this.zze.zzb(bArr, 0, i2 + i4, i3 - i4);
        }
    }

    public final zzfhb zzd() {
        return zzfhb.zza(new zzfju(this));
    }

    protected final int zze() {
        return this.zzg;
    }

    protected final boolean zzf() {
        return this.zzc >= zzb[this.zzg];
    }
}
