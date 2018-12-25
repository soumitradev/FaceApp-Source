package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbq extends zzflm<zzbq> {
    private static volatile zzbq[] zzk;
    public int[] zza;
    public int[] zzb;
    public int[] zzc;
    public int[] zzd;
    public int[] zze;
    public int[] zzf;
    public int[] zzg;
    public int[] zzh;
    public int[] zzi;
    public int[] zzj;

    public zzbq() {
        this.zza = zzflv.zza;
        this.zzb = zzflv.zza;
        this.zzc = zzflv.zza;
        this.zzd = zzflv.zza;
        this.zze = zzflv.zza;
        this.zzf = zzflv.zza;
        this.zzg = zzflv.zza;
        this.zzh = zzflv.zza;
        this.zzi = zzflv.zza;
        this.zzj = zzflv.zza;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzbq[] zzb() {
        if (zzk == null) {
            synchronized (zzflq.zzb) {
                if (zzk == null) {
                    zzk = new zzbq[0];
                }
            }
        }
        return zzk;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbq)) {
            return false;
        }
        zzbq zzbq = (zzbq) obj;
        if (!zzflq.zza(this.zza, zzbq.zza) || !zzflq.zza(this.zzb, zzbq.zzb) || !zzflq.zza(this.zzc, zzbq.zzc) || !zzflq.zza(this.zzd, zzbq.zzd) || !zzflq.zza(this.zze, zzbq.zze) || !zzflq.zza(this.zzf, zzbq.zzf) || !zzflq.zza(this.zzg, zzbq.zzg) || !zzflq.zza(this.zzh, zzbq.zzh) || !zzflq.zza(this.zzi, zzbq.zzi) || !zzflq.zza(this.zzj, zzbq.zzj)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbq.zzax);
            }
        }
        return zzbq.zzax == null || zzbq.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zza)) * 31) + zzflq.zza(this.zzb)) * 31) + zzflq.zza(this.zzc)) * 31) + zzflq.zza(this.zzd)) * 31) + zzflq.zza(this.zze)) * 31) + zzflq.zza(this.zzf)) * 31) + zzflq.zza(this.zzg)) * 31) + zzflq.zza(this.zzh)) * 31) + zzflq.zza(this.zzi)) * 31) + zzflq.zza(this.zzj)) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                hashCode = this.zzax.hashCode();
                return hashCode2 + hashCode;
            }
        }
        hashCode = 0;
        return hashCode2 + hashCode;
    }

    protected final int zza() {
        int i;
        int i2;
        int zza = super.zza();
        if (this.zza != null && this.zza.length > 0) {
            i = 0;
            for (int zza2 : this.zza) {
                i += zzflk.zza(zza2);
            }
            zza = (zza + i) + (this.zza.length * 1);
        }
        if (this.zzb != null && this.zzb.length > 0) {
            i = 0;
            for (int zza22 : this.zzb) {
                i += zzflk.zza(zza22);
            }
            zza = (zza + i) + (this.zzb.length * 1);
        }
        if (this.zzc != null && this.zzc.length > 0) {
            i = 0;
            for (int zza222 : this.zzc) {
                i += zzflk.zza(zza222);
            }
            zza = (zza + i) + (this.zzc.length * 1);
        }
        if (this.zzd != null && this.zzd.length > 0) {
            i = 0;
            for (int zza2222 : this.zzd) {
                i += zzflk.zza(zza2222);
            }
            zza = (zza + i) + (this.zzd.length * 1);
        }
        if (this.zze != null && this.zze.length > 0) {
            i = 0;
            for (int zza22222 : this.zze) {
                i += zzflk.zza(zza22222);
            }
            zza = (zza + i) + (this.zze.length * 1);
        }
        if (this.zzf != null && this.zzf.length > 0) {
            i = 0;
            for (int zza222222 : this.zzf) {
                i += zzflk.zza(zza222222);
            }
            zza = (zza + i) + (this.zzf.length * 1);
        }
        if (this.zzg != null && this.zzg.length > 0) {
            i = 0;
            for (int zza2222222 : this.zzg) {
                i += zzflk.zza(zza2222222);
            }
            zza = (zza + i) + (this.zzg.length * 1);
        }
        if (this.zzh != null && this.zzh.length > 0) {
            i = 0;
            for (int zza22222222 : this.zzh) {
                i += zzflk.zza(zza22222222);
            }
            zza = (zza + i) + (this.zzh.length * 1);
        }
        if (this.zzi != null && this.zzi.length > 0) {
            i = 0;
            for (int zza222222222 : this.zzi) {
                i += zzflk.zza(zza222222222);
            }
            zza = (zza + i) + (this.zzi.length * 1);
        }
        if (this.zzj == null || this.zzj.length <= 0) {
            return zza;
        }
        i2 = 0;
        for (int i3 : this.zzj) {
            i2 += zzflk.zza(i3);
        }
        return (zza + i2) + (this.zzj.length * 1);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            int length;
            Object obj;
            int i;
            Object obj2;
            switch (zza) {
                case 0:
                    return this;
                case 8:
                    zza = zzflv.zza(zzflj, 8);
                    length = this.zza == null ? 0 : this.zza.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zza, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zza = obj;
                    continue;
                case 10:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zza == null ? 0 : this.zza.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zza, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zza = obj2;
                    break;
                case 16:
                    zza = zzflv.zza(zzflj, 16);
                    length = this.zzb == null ? 0 : this.zzb.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzb, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zzb = obj;
                    continue;
                case 18:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzb == null ? 0 : this.zzb.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzb, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zzb = obj2;
                    break;
                case 24:
                    zza = zzflv.zza(zzflj, 24);
                    length = this.zzc == null ? 0 : this.zzc.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzc, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zzc = obj;
                    continue;
                case 26:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzc == null ? 0 : this.zzc.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzc, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zzc = obj2;
                    break;
                case 32:
                    zza = zzflv.zza(zzflj, 32);
                    length = this.zzd == null ? 0 : this.zzd.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzd, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zzd = obj;
                    continue;
                case 34:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzd == null ? 0 : this.zzd.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzd, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zzd = obj2;
                    break;
                case 40:
                    zza = zzflv.zza(zzflj, 40);
                    length = this.zze == null ? 0 : this.zze.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zze, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zze = obj;
                    continue;
                case 42:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zze == null ? 0 : this.zze.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zze, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zze = obj2;
                    break;
                case 48:
                    zza = zzflv.zza(zzflj, 48);
                    length = this.zzf == null ? 0 : this.zzf.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzf, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zzf = obj;
                    continue;
                case 50:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzf == null ? 0 : this.zzf.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzf, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zzf = obj2;
                    break;
                case 56:
                    zza = zzflv.zza(zzflj, 56);
                    length = this.zzg == null ? 0 : this.zzg.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzg, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zzg = obj;
                    continue;
                case 58:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzg == null ? 0 : this.zzg.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzg, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zzg = obj2;
                    break;
                case 64:
                    zza = zzflv.zza(zzflj, 64);
                    length = this.zzh == null ? 0 : this.zzh.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzh, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zzh = obj;
                    continue;
                case 66:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzh == null ? 0 : this.zzh.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzh, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zzh = obj2;
                    break;
                case 72:
                    zza = zzflv.zza(zzflj, 72);
                    length = this.zzi == null ? 0 : this.zzi.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzi, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zzi = obj;
                    continue;
                case 74:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzi == null ? 0 : this.zzi.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzi, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zzi = obj2;
                    break;
                case 80:
                    zza = zzflv.zza(zzflj, 80);
                    length = this.zzj == null ? 0 : this.zzj.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzj, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzh();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzh();
                    this.zzj = obj;
                    continue;
                case 82:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzh();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzj == null ? 0 : this.zzj.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzj, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzh();
                        length++;
                    }
                    this.zzj = obj2;
                    break;
                default:
                    if (!super.zza(zzflj, zza)) {
                        return this;
                    }
                    continue;
            }
            zzflj.zzd(zza);
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null && this.zza.length > 0) {
            for (int zza : this.zza) {
                zzflk.zza(1, zza);
            }
        }
        if (this.zzb != null && this.zzb.length > 0) {
            for (int zza2 : this.zzb) {
                zzflk.zza(2, zza2);
            }
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (int zza22 : this.zzc) {
                zzflk.zza(3, zza22);
            }
        }
        if (this.zzd != null && this.zzd.length > 0) {
            for (int zza222 : this.zzd) {
                zzflk.zza(4, zza222);
            }
        }
        if (this.zze != null && this.zze.length > 0) {
            for (int zza2222 : this.zze) {
                zzflk.zza(5, zza2222);
            }
        }
        if (this.zzf != null && this.zzf.length > 0) {
            for (int zza22222 : this.zzf) {
                zzflk.zza(6, zza22222);
            }
        }
        if (this.zzg != null && this.zzg.length > 0) {
            for (int zza222222 : this.zzg) {
                zzflk.zza(7, zza222222);
            }
        }
        if (this.zzh != null && this.zzh.length > 0) {
            for (int zza2222222 : this.zzh) {
                zzflk.zza(8, zza2222222);
            }
        }
        if (this.zzi != null && this.zzi.length > 0) {
            for (int zza22222222 : this.zzi) {
                zzflk.zza(9, zza22222222);
            }
        }
        if (this.zzj != null && this.zzj.length > 0) {
            for (int zza3 : this.zzj) {
                zzflk.zza(10, zza3);
            }
        }
        super.zza(zzflk);
    }
}
