package com.google.android.gms.internal;

import com.badlogic.gdx.Input.Keys;
import java.io.IOException;
import java.util.Arrays;

public final class zzfmr extends zzflm<zzfmr> implements Cloneable {
    public long zza;
    public long zzb;
    public byte[] zzc;
    public long zzd;
    public byte[] zze;
    private long zzf;
    private String zzg;
    private int zzh;
    private int zzi;
    private boolean zzj;
    private zzfms[] zzk;
    private byte[] zzl;
    private zzfmp zzm;
    private String zzn;
    private String zzo;
    private zzfmo zzp;
    private String zzq;
    private zzfmq zzr;
    private String zzs;
    private int zzt;
    private int[] zzu;
    private long zzv;
    private zzfmt zzw;
    private boolean zzx;

    public zzfmr() {
        this.zza = 0;
        this.zzb = 0;
        this.zzf = 0;
        this.zzg = "";
        this.zzh = 0;
        this.zzi = 0;
        this.zzj = false;
        this.zzk = zzfms.zzb();
        this.zzl = zzflv.zzh;
        this.zzm = null;
        this.zzc = zzflv.zzh;
        this.zzn = "";
        this.zzo = "";
        this.zzp = null;
        this.zzq = "";
        this.zzd = 180000;
        this.zzr = null;
        this.zze = zzflv.zzh;
        this.zzs = "";
        this.zzt = 0;
        this.zzu = zzflv.zza;
        this.zzv = 0;
        this.zzw = null;
        this.zzx = false;
        this.zzax = null;
        this.zzay = -1;
    }

    private final zzfmr zzb() {
        try {
            zzfmr zzfmr = (zzfmr) super.zzc();
            if (this.zzk != null && this.zzk.length > 0) {
                zzfmr.zzk = new zzfms[this.zzk.length];
                for (int i = 0; i < this.zzk.length; i++) {
                    if (this.zzk[i] != null) {
                        zzfmr.zzk[i] = (zzfms) this.zzk[i].clone();
                    }
                }
            }
            if (this.zzm != null) {
                zzfmr.zzm = (zzfmp) this.zzm.clone();
            }
            if (this.zzp != null) {
                zzfmr.zzp = (zzfmo) this.zzp.clone();
            }
            if (this.zzr != null) {
                zzfmr.zzr = (zzfmq) this.zzr.clone();
            }
            if (this.zzu != null && this.zzu.length > 0) {
                zzfmr.zzu = (int[]) this.zzu.clone();
            }
            if (this.zzw != null) {
                zzfmr.zzw = (zzfmt) this.zzw.clone();
            }
            return zzfmr;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    private final zzfmr zzb(zzflj zzflj) throws IOException {
        while (true) {
            zzfls zzfls;
            int zza = zzflj.zza();
            int length;
            Object obj;
            switch (zza) {
                case 0:
                    return this;
                case 8:
                    this.zza = zzflj.zzb();
                    continue;
                case 18:
                    this.zzg = zzflj.zze();
                    continue;
                case 26:
                    zza = zzflv.zza(zzflj, 26);
                    length = this.zzk == null ? 0 : this.zzk.length;
                    obj = new zzfms[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzk, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzfms();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzfms();
                    zzflj.zza(obj[length]);
                    this.zzk = obj;
                    continue;
                case 34:
                    this.zzl = zzflj.zzf();
                    continue;
                case 50:
                    this.zzc = zzflj.zzf();
                    continue;
                case 58:
                    if (this.zzp == null) {
                        this.zzp = new zzfmo();
                    }
                    zzfls = this.zzp;
                    break;
                case 66:
                    this.zzn = zzflj.zze();
                    continue;
                case 74:
                    if (this.zzm == null) {
                        this.zzm = new zzfmp();
                    }
                    zzfls = this.zzm;
                    break;
                case 80:
                    this.zzj = zzflj.zzd();
                    continue;
                case 88:
                    this.zzh = zzflj.zzc();
                    continue;
                case 96:
                    this.zzi = zzflj.zzc();
                    continue;
                case 106:
                    this.zzo = zzflj.zze();
                    continue;
                case 114:
                    this.zzq = zzflj.zze();
                    continue;
                case 120:
                    this.zzd = zzflj.zzg();
                    continue;
                case 130:
                    if (this.zzr == null) {
                        this.zzr = new zzfmq();
                    }
                    zzfls = this.zzr;
                    break;
                case 136:
                    this.zzb = zzflj.zzb();
                    continue;
                case Keys.NUMPAD_2 /*146*/:
                    this.zze = zzflj.zzf();
                    continue;
                case Keys.NUMPAD_8 /*152*/:
                    try {
                        length = zzflj.zzc();
                        switch (length) {
                            case 0:
                            case 1:
                            case 2:
                                this.zzt = length;
                                continue;
                            default:
                                StringBuilder stringBuilder = new StringBuilder(45);
                                stringBuilder.append(length);
                                stringBuilder.append(" is not a valid enum InternalEvent");
                                throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    } catch (IllegalArgumentException e) {
                        zzflj.zze(zzflj.zzm());
                        zza(zzflj, zza);
                        break;
                    }
                case 160:
                    zza = zzflv.zza(zzflj, 160);
                    length = this.zzu == null ? 0 : this.zzu.length;
                    obj = new int[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzu, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzc();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzc();
                    this.zzu = obj;
                    continue;
                case 162:
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    int i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzc();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzu == null ? 0 : this.zzu.length;
                    Object obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzu, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzc();
                        length++;
                    }
                    this.zzu = obj2;
                    zzflj.zzd(zza);
                    continue;
                case 168:
                    this.zzf = zzflj.zzb();
                    continue;
                case 176:
                    this.zzv = zzflj.zzb();
                    continue;
                case 186:
                    if (this.zzw == null) {
                        this.zzw = new zzfmt();
                    }
                    zzfls = this.zzw;
                    break;
                case 194:
                    this.zzs = zzflj.zze();
                    continue;
                case 200:
                    this.zzx = zzflj.zzd();
                    continue;
                default:
                    if (!super.zza(zzflj, zza)) {
                        return this;
                    }
                    continue;
            }
            zzflj.zza(zzfls);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzb();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfmr)) {
            return false;
        }
        zzfmr zzfmr = (zzfmr) obj;
        if (this.zza != zzfmr.zza || this.zzb != zzfmr.zzb || this.zzf != zzfmr.zzf) {
            return false;
        }
        if (this.zzg == null) {
            if (zzfmr.zzg != null) {
                return false;
            }
        } else if (!this.zzg.equals(zzfmr.zzg)) {
            return false;
        }
        if (this.zzh != zzfmr.zzh || this.zzi != zzfmr.zzi || this.zzj != zzfmr.zzj || !zzflq.zza(this.zzk, zzfmr.zzk) || !Arrays.equals(this.zzl, zzfmr.zzl)) {
            return false;
        }
        if (this.zzm == null) {
            if (zzfmr.zzm != null) {
                return false;
            }
        } else if (!this.zzm.equals(zzfmr.zzm)) {
            return false;
        }
        if (!Arrays.equals(this.zzc, zzfmr.zzc)) {
            return false;
        }
        if (this.zzn == null) {
            if (zzfmr.zzn != null) {
                return false;
            }
        } else if (!this.zzn.equals(zzfmr.zzn)) {
            return false;
        }
        if (this.zzo == null) {
            if (zzfmr.zzo != null) {
                return false;
            }
        } else if (!this.zzo.equals(zzfmr.zzo)) {
            return false;
        }
        if (this.zzp == null) {
            if (zzfmr.zzp != null) {
                return false;
            }
        } else if (!this.zzp.equals(zzfmr.zzp)) {
            return false;
        }
        if (this.zzq == null) {
            if (zzfmr.zzq != null) {
                return false;
            }
        } else if (!this.zzq.equals(zzfmr.zzq)) {
            return false;
        }
        if (this.zzd != zzfmr.zzd) {
            return false;
        }
        if (this.zzr == null) {
            if (zzfmr.zzr != null) {
                return false;
            }
        } else if (!this.zzr.equals(zzfmr.zzr)) {
            return false;
        }
        if (!Arrays.equals(this.zze, zzfmr.zze)) {
            return false;
        }
        if (this.zzs == null) {
            if (zzfmr.zzs != null) {
                return false;
            }
        } else if (!this.zzs.equals(zzfmr.zzs)) {
            return false;
        }
        if (this.zzt != zzfmr.zzt || !zzflq.zza(this.zzu, zzfmr.zzu) || this.zzv != zzfmr.zzv) {
            return false;
        }
        if (this.zzw == null) {
            if (zzfmr.zzw != null) {
                return false;
            }
        } else if (!this.zzw.equals(zzfmr.zzw)) {
            return false;
        }
        if (this.zzx != zzfmr.zzx) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzfmr.zzax);
            }
        }
        return zzfmr.zzax == null || zzfmr.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int i2 = 1237;
        int hashCode = ((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.zza ^ (this.zza >>> 32)))) * 31) + ((int) (this.zzb ^ (this.zzb >>> 32)))) * 31) + ((int) (this.zzf ^ (this.zzf >>> 32)))) * 31) + (this.zzg == null ? 0 : this.zzg.hashCode())) * 31) + this.zzh) * 31) + this.zzi) * 31) + (this.zzj ? 1231 : 1237)) * 31) + zzflq.zza(this.zzk)) * 31) + Arrays.hashCode(this.zzl);
        zzfmp zzfmp = this.zzm;
        hashCode = (((((((hashCode * 31) + (zzfmp == null ? 0 : zzfmp.hashCode())) * 31) + Arrays.hashCode(this.zzc)) * 31) + (this.zzn == null ? 0 : this.zzn.hashCode())) * 31) + (this.zzo == null ? 0 : this.zzo.hashCode());
        zzfmo zzfmo = this.zzp;
        hashCode = (((((hashCode * 31) + (zzfmo == null ? 0 : zzfmo.hashCode())) * 31) + (this.zzq == null ? 0 : this.zzq.hashCode())) * 31) + ((int) (this.zzd ^ (this.zzd >>> 32)));
        zzfmq zzfmq = this.zzr;
        hashCode = (((((((((((hashCode * 31) + (zzfmq == null ? 0 : zzfmq.hashCode())) * 31) + Arrays.hashCode(this.zze)) * 31) + (this.zzs == null ? 0 : this.zzs.hashCode())) * 31) + this.zzt) * 31) + zzflq.zza(this.zzu)) * 31) + ((int) (this.zzv ^ (this.zzv >>> 32)));
        zzfmt zzfmt = this.zzw;
        hashCode = ((hashCode * 31) + (zzfmt == null ? 0 : zzfmt.hashCode())) * 31;
        if (this.zzx) {
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
        int zza = super.zza();
        if (this.zza != 0) {
            zza += zzflk.zze(1, this.zza);
        }
        if (!(this.zzg == null || this.zzg.equals(""))) {
            zza += zzflk.zzb(2, this.zzg);
        }
        if (this.zzk != null && this.zzk.length > 0) {
            i = zza;
            for (zzfls zzfls : this.zzk) {
                if (zzfls != null) {
                    i += zzflk.zzb(3, zzfls);
                }
            }
            zza = i;
        }
        if (!Arrays.equals(this.zzl, zzflv.zzh)) {
            zza += zzflk.zzb(4, this.zzl);
        }
        if (!Arrays.equals(this.zzc, zzflv.zzh)) {
            zza += zzflk.zzb(6, this.zzc);
        }
        if (this.zzp != null) {
            zza += zzflk.zzb(7, this.zzp);
        }
        if (!(this.zzn == null || this.zzn.equals(""))) {
            zza += zzflk.zzb(8, this.zzn);
        }
        if (this.zzm != null) {
            zza += zzflk.zzb(9, this.zzm);
        }
        if (this.zzj) {
            zza += zzflk.zzb(10) + 1;
        }
        if (this.zzh != 0) {
            zza += zzflk.zzb(11, this.zzh);
        }
        if (this.zzi != 0) {
            zza += zzflk.zzb(12, this.zzi);
        }
        if (!(this.zzo == null || this.zzo.equals(""))) {
            zza += zzflk.zzb(13, this.zzo);
        }
        if (!(this.zzq == null || this.zzq.equals(""))) {
            zza += zzflk.zzb(14, this.zzq);
        }
        if (this.zzd != 180000) {
            zza += zzflk.zzf(15, this.zzd);
        }
        if (this.zzr != null) {
            zza += zzflk.zzb(16, this.zzr);
        }
        if (this.zzb != 0) {
            zza += zzflk.zze(17, this.zzb);
        }
        if (!Arrays.equals(this.zze, zzflv.zzh)) {
            zza += zzflk.zzb(18, this.zze);
        }
        if (this.zzt != 0) {
            zza += zzflk.zzb(19, this.zzt);
        }
        if (this.zzu != null && this.zzu.length > 0) {
            i = 0;
            for (int zza2 : this.zzu) {
                i += zzflk.zza(zza2);
            }
            zza = (zza + i) + (this.zzu.length * 2);
        }
        if (this.zzf != 0) {
            zza += zzflk.zze(21, this.zzf);
        }
        if (this.zzv != 0) {
            zza += zzflk.zze(22, this.zzv);
        }
        if (this.zzw != null) {
            zza += zzflk.zzb(23, this.zzw);
        }
        if (!(this.zzs == null || this.zzs.equals(""))) {
            zza += zzflk.zzb(24, this.zzs);
        }
        return this.zzx ? zza + (zzflk.zzb(25) + 1) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        return zzb(zzflj);
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != 0) {
            zzflk.zzb(1, this.zza);
        }
        if (!(this.zzg == null || this.zzg.equals(""))) {
            zzflk.zza(2, this.zzg);
        }
        if (this.zzk != null && this.zzk.length > 0) {
            for (zzfls zzfls : this.zzk) {
                if (zzfls != null) {
                    zzflk.zza(3, zzfls);
                }
            }
        }
        if (!Arrays.equals(this.zzl, zzflv.zzh)) {
            zzflk.zza(4, this.zzl);
        }
        if (!Arrays.equals(this.zzc, zzflv.zzh)) {
            zzflk.zza(6, this.zzc);
        }
        if (this.zzp != null) {
            zzflk.zza(7, this.zzp);
        }
        if (!(this.zzn == null || this.zzn.equals(""))) {
            zzflk.zza(8, this.zzn);
        }
        if (this.zzm != null) {
            zzflk.zza(9, this.zzm);
        }
        if (this.zzj) {
            zzflk.zza(10, this.zzj);
        }
        if (this.zzh != 0) {
            zzflk.zza(11, this.zzh);
        }
        if (this.zzi != 0) {
            zzflk.zza(12, this.zzi);
        }
        if (!(this.zzo == null || this.zzo.equals(""))) {
            zzflk.zza(13, this.zzo);
        }
        if (!(this.zzq == null || this.zzq.equals(""))) {
            zzflk.zza(14, this.zzq);
        }
        if (this.zzd != 180000) {
            zzflk.zzd(15, this.zzd);
        }
        if (this.zzr != null) {
            zzflk.zza(16, this.zzr);
        }
        if (this.zzb != 0) {
            zzflk.zzb(17, this.zzb);
        }
        if (!Arrays.equals(this.zze, zzflv.zzh)) {
            zzflk.zza(18, this.zze);
        }
        if (this.zzt != 0) {
            zzflk.zza(19, this.zzt);
        }
        if (this.zzu != null && this.zzu.length > 0) {
            for (int zza : this.zzu) {
                zzflk.zza(20, zza);
            }
        }
        if (this.zzf != 0) {
            zzflk.zzb(21, this.zzf);
        }
        if (this.zzv != 0) {
            zzflk.zzb(22, this.zzv);
        }
        if (this.zzw != null) {
            zzflk.zza(23, this.zzw);
        }
        if (!(this.zzs == null || this.zzs.equals(""))) {
            zzflk.zza(24, this.zzs);
        }
        if (this.zzx) {
            zzflk.zza(25, this.zzx);
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzc() throws CloneNotSupportedException {
        return (zzfmr) clone();
    }

    public final /* synthetic */ zzfls zzd() throws CloneNotSupportedException {
        return (zzfmr) clone();
    }
}
