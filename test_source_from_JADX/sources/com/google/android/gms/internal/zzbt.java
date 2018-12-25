package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbt extends zzflm<zzbt> {
    private static volatile zzbt[] zzm;
    public int zza;
    public String zzb;
    public zzbt[] zzc;
    public zzbt[] zzd;
    public zzbt[] zze;
    public String zzf;
    public String zzg;
    public long zzh;
    public boolean zzi;
    public zzbt[] zzj;
    public int[] zzk;
    public boolean zzl;

    public zzbt() {
        this.zza = 1;
        this.zzb = "";
        this.zzc = zzb();
        this.zzd = zzb();
        this.zze = zzb();
        this.zzf = "";
        this.zzg = "";
        this.zzh = 0;
        this.zzi = false;
        this.zzj = zzb();
        this.zzk = zzflv.zza;
        this.zzl = false;
        this.zzax = null;
        this.zzay = -1;
    }

    private static int zza(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                return i;
            default:
                StringBuilder stringBuilder = new StringBuilder(40);
                stringBuilder.append(i);
                stringBuilder.append(" is not a valid enum Escaping");
                throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    private final zzbt zzb(zzflj zzflj) throws IOException {
        int zzh;
        while (true) {
            int zza = zzflj.zza();
            int length;
            Object obj;
            int i;
            switch (zza) {
                case 0:
                    return this;
                case 8:
                    try {
                        zzh = zzflj.zzh();
                        switch (zzh) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                                this.zza = zzh;
                                break;
                            default:
                                StringBuilder stringBuilder = new StringBuilder(36);
                                stringBuilder.append(zzh);
                                stringBuilder.append(" is not a valid enum Type");
                                throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    } catch (IllegalArgumentException e) {
                        zzflj.zze(zzflj.zzm());
                        zza(zzflj, zza);
                        break;
                    }
                case 18:
                    this.zzb = zzflj.zze();
                    break;
                case 26:
                    zza = zzflv.zza(zzflj, 26);
                    length = this.zzc == null ? 0 : this.zzc.length;
                    obj = new zzbt[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzc, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbt();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbt();
                    zzflj.zza(obj[length]);
                    this.zzc = obj;
                    break;
                case 34:
                    zza = zzflv.zza(zzflj, 34);
                    length = this.zzd == null ? 0 : this.zzd.length;
                    obj = new zzbt[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzd, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbt();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbt();
                    zzflj.zza(obj[length]);
                    this.zzd = obj;
                    break;
                case 42:
                    zza = zzflv.zza(zzflj, 42);
                    length = this.zze == null ? 0 : this.zze.length;
                    obj = new zzbt[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zze, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbt();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbt();
                    zzflj.zza(obj[length]);
                    this.zze = obj;
                    break;
                case 50:
                    this.zzf = zzflj.zze();
                    break;
                case 58:
                    this.zzg = zzflj.zze();
                    break;
                case 64:
                    this.zzh = zzflj.zzi();
                    break;
                case 72:
                    this.zzl = zzflj.zzd();
                    break;
                case 80:
                    length = zzflv.zza(zzflj, 80);
                    Object obj2 = new int[length];
                    int i2 = 0;
                    for (i = 0; i < length; i++) {
                        if (i != 0) {
                            zzflj.zza();
                        }
                        int zzm = zzflj.zzm();
                        try {
                            obj2[i2] = zza(zzflj.zzh());
                            i2++;
                        } catch (IllegalArgumentException e2) {
                            zzflj.zze(zzm);
                            zza(zzflj, zza);
                        }
                    }
                    if (i2 != 0) {
                        zza = this.zzk == null ? 0 : this.zzk.length;
                        if (zza != 0 || i2 != obj2.length) {
                            Object obj3 = new int[(zza + i2)];
                            if (zza != 0) {
                                System.arraycopy(this.zzk, 0, obj3, 0, zza);
                            }
                            System.arraycopy(obj2, 0, obj3, zza, i2);
                            this.zzk = obj3;
                            break;
                        }
                        this.zzk = obj2;
                        break;
                    }
                    break;
                case 82:
                    zza = zzflj.zzc(zzflj.zzh());
                    int zzm2 = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        try {
                            zza(zzflj.zzh());
                            i++;
                        } catch (IllegalArgumentException e3) {
                        }
                    }
                    if (i != 0) {
                        zzflj.zze(zzm2);
                        zzm2 = this.zzk == null ? 0 : this.zzk.length;
                        Object obj4 = new int[(i + zzm2)];
                        if (zzm2 != 0) {
                            System.arraycopy(this.zzk, 0, obj4, 0, zzm2);
                        }
                        while (zzflj.zzl() > 0) {
                            zzh = zzflj.zzm();
                            try {
                                obj4[zzm2] = zza(zzflj.zzh());
                                zzm2++;
                            } catch (IllegalArgumentException e4) {
                                zzflj.zze(zzh);
                                zza(zzflj, 80);
                            }
                        }
                        this.zzk = obj4;
                    }
                    zzflj.zzd(zza);
                    break;
                case 90:
                    zza = zzflv.zza(zzflj, 90);
                    length = this.zzj == null ? 0 : this.zzj.length;
                    obj = new zzbt[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzj, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbt();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbt();
                    zzflj.zza(obj[length]);
                    this.zzj = obj;
                    break;
                case 96:
                    this.zzi = zzflj.zzd();
                    break;
                default:
                    if (super.zza(zzflj, zza)) {
                        break;
                    }
                    return this;
            }
        }
    }

    public static zzbt[] zzb() {
        if (zzm == null) {
            synchronized (zzflq.zzb) {
                if (zzm == null) {
                    zzm = new zzbt[0];
                }
            }
        }
        return zzm;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbt)) {
            return false;
        }
        zzbt zzbt = (zzbt) obj;
        if (this.zza != zzbt.zza) {
            return false;
        }
        if (this.zzb == null) {
            if (zzbt.zzb != null) {
                return false;
            }
        } else if (!this.zzb.equals(zzbt.zzb)) {
            return false;
        }
        if (!zzflq.zza(this.zzc, zzbt.zzc) || !zzflq.zza(this.zzd, zzbt.zzd) || !zzflq.zza(this.zze, zzbt.zze)) {
            return false;
        }
        if (this.zzf == null) {
            if (zzbt.zzf != null) {
                return false;
            }
        } else if (!this.zzf.equals(zzbt.zzf)) {
            return false;
        }
        if (this.zzg == null) {
            if (zzbt.zzg != null) {
                return false;
            }
        } else if (!this.zzg.equals(zzbt.zzg)) {
            return false;
        }
        if (this.zzh != zzbt.zzh || this.zzi != zzbt.zzi || !zzflq.zza(this.zzj, zzbt.zzj) || !zzflq.zza(this.zzk, zzbt.zzk) || this.zzl != zzbt.zzl) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbt.zzax);
            }
        }
        return zzbt.zzax == null || zzbt.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int i2 = 1237;
        int hashCode = (((((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + this.zza) * 31) + (this.zzb == null ? 0 : this.zzb.hashCode())) * 31) + zzflq.zza(this.zzc)) * 31) + zzflq.zza(this.zzd)) * 31) + zzflq.zza(this.zze)) * 31) + (this.zzf == null ? 0 : this.zzf.hashCode())) * 31) + (this.zzg == null ? 0 : this.zzg.hashCode())) * 31) + ((int) (this.zzh ^ (this.zzh >>> 32)))) * 31) + (this.zzi ? 1231 : 1237)) * 31) + zzflq.zza(this.zzj)) * 31) + zzflq.zza(this.zzk)) * 31;
        if (this.zzl) {
            i2 = 1231;
        }
        hashCode = (hashCode + i2) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int i;
        int zza = super.zza() + zzflk.zzb(1, this.zza);
        if (!(this.zzb == null || this.zzb.equals(""))) {
            zza += zzflk.zzb(2, this.zzb);
        }
        if (this.zzc != null && this.zzc.length > 0) {
            i = zza;
            for (zzfls zzfls : this.zzc) {
                if (zzfls != null) {
                    i += zzflk.zzb(3, zzfls);
                }
            }
            zza = i;
        }
        if (this.zzd != null && this.zzd.length > 0) {
            i = zza;
            for (zzfls zzfls2 : this.zzd) {
                if (zzfls2 != null) {
                    i += zzflk.zzb(4, zzfls2);
                }
            }
            zza = i;
        }
        if (this.zze != null && this.zze.length > 0) {
            i = zza;
            for (zzfls zzfls22 : this.zze) {
                if (zzfls22 != null) {
                    i += zzflk.zzb(5, zzfls22);
                }
            }
            zza = i;
        }
        if (!(this.zzf == null || this.zzf.equals(""))) {
            zza += zzflk.zzb(6, this.zzf);
        }
        if (!(this.zzg == null || this.zzg.equals(""))) {
            zza += zzflk.zzb(7, this.zzg);
        }
        if (this.zzh != 0) {
            zza += zzflk.zze(8, this.zzh);
        }
        if (this.zzl) {
            zza += zzflk.zzb(9) + 1;
        }
        if (this.zzk != null && this.zzk.length > 0) {
            int i2 = 0;
            for (int zza2 : this.zzk) {
                i2 += zzflk.zza(zza2);
            }
            zza = (zza + i2) + (this.zzk.length * 1);
        }
        if (this.zzj != null && this.zzj.length > 0) {
            for (zzfls zzfls3 : this.zzj) {
                if (zzfls3 != null) {
                    zza += zzflk.zzb(11, zzfls3);
                }
            }
        }
        return this.zzi ? zza + (zzflk.zzb(12) + 1) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzb(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        zzflk.zza(1, this.zza);
        if (!(this.zzb == null || this.zzb.equals(""))) {
            zzflk.zza(2, this.zzb);
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (zzfls zzfls : this.zzc) {
                if (zzfls != null) {
                    zzflk.zza(3, zzfls);
                }
            }
        }
        if (this.zzd != null && this.zzd.length > 0) {
            for (zzfls zzfls2 : this.zzd) {
                if (zzfls2 != null) {
                    zzflk.zza(4, zzfls2);
                }
            }
        }
        if (this.zze != null && this.zze.length > 0) {
            for (zzfls zzfls22 : this.zze) {
                if (zzfls22 != null) {
                    zzflk.zza(5, zzfls22);
                }
            }
        }
        if (!(this.zzf == null || this.zzf.equals(""))) {
            zzflk.zza(6, this.zzf);
        }
        if (!(this.zzg == null || this.zzg.equals(""))) {
            zzflk.zza(7, this.zzg);
        }
        if (this.zzh != 0) {
            zzflk.zzb(8, this.zzh);
        }
        if (this.zzl) {
            zzflk.zza(9, this.zzl);
        }
        if (this.zzk != null && this.zzk.length > 0) {
            for (int zza : this.zzk) {
                zzflk.zza(10, zza);
            }
        }
        if (this.zzj != null && this.zzj.length > 0) {
            for (zzfls zzfls3 : this.zzj) {
                if (zzfls3 != null) {
                    zzflk.zza(11, zzfls3);
                }
            }
        }
        if (this.zzi) {
            zzflk.zza(12, this.zzi);
        }
        super.zza(zzflk);
    }
}
