package com.google.android.gms.internal;

import com.facebook.appevents.AppEventsConstants;
import java.io.IOException;

public final class zzbp extends zzflm<zzbp> {
    public String[] zza;
    public zzbt[] zzb;
    public zzbo[] zzc;
    public zzbl[] zzd;
    public zzbl[] zze;
    public zzbl[] zzf;
    public zzbq[] zzg;
    public String zzh;
    public int zzi;
    private String[] zzj;
    private String zzk;
    private String zzl;
    private String zzm;
    private zzbk zzn;
    private float zzo;
    private boolean zzp;
    private String[] zzq;

    public zzbp() {
        this.zzj = zzflv.zzf;
        this.zza = zzflv.zzf;
        this.zzb = zzbt.zzb();
        this.zzc = zzbo.zzb();
        this.zzd = zzbl.zzb();
        this.zze = zzbl.zzb();
        this.zzf = zzbl.zzb();
        this.zzg = zzbq.zzb();
        this.zzk = "";
        this.zzl = "";
        this.zzm = AppEventsConstants.EVENT_PARAM_VALUE_NO;
        this.zzh = "";
        this.zzn = null;
        this.zzo = 0.0f;
        this.zzp = false;
        this.zzq = zzflv.zzf;
        this.zzi = 0;
        this.zzax = null;
        this.zzay = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbp)) {
            return false;
        }
        zzbp zzbp = (zzbp) obj;
        if (!zzflq.zza(this.zzj, zzbp.zzj) || !zzflq.zza(this.zza, zzbp.zza) || !zzflq.zza(this.zzb, zzbp.zzb) || !zzflq.zza(this.zzc, zzbp.zzc) || !zzflq.zza(this.zzd, zzbp.zzd) || !zzflq.zza(this.zze, zzbp.zze) || !zzflq.zza(this.zzf, zzbp.zzf) || !zzflq.zza(this.zzg, zzbp.zzg)) {
            return false;
        }
        if (this.zzk == null) {
            if (zzbp.zzk != null) {
                return false;
            }
        } else if (!this.zzk.equals(zzbp.zzk)) {
            return false;
        }
        if (this.zzl == null) {
            if (zzbp.zzl != null) {
                return false;
            }
        } else if (!this.zzl.equals(zzbp.zzl)) {
            return false;
        }
        if (this.zzm == null) {
            if (zzbp.zzm != null) {
                return false;
            }
        } else if (!this.zzm.equals(zzbp.zzm)) {
            return false;
        }
        if (this.zzh == null) {
            if (zzbp.zzh != null) {
                return false;
            }
        } else if (!this.zzh.equals(zzbp.zzh)) {
            return false;
        }
        if (this.zzn == null) {
            if (zzbp.zzn != null) {
                return false;
            }
        } else if (!this.zzn.equals(zzbp.zzn)) {
            return false;
        }
        if (Float.floatToIntBits(this.zzo) != Float.floatToIntBits(zzbp.zzo) || this.zzp != zzbp.zzp || !zzflq.zza(this.zzq, zzbp.zzq) || this.zzi != zzbp.zzi) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzbp.zzax);
            }
        }
        return zzbp.zzax == null || zzbp.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zzj)) * 31) + zzflq.zza(this.zza)) * 31) + zzflq.zza(this.zzb)) * 31) + zzflq.zza(this.zzc)) * 31) + zzflq.zza(this.zzd)) * 31) + zzflq.zza(this.zze)) * 31) + zzflq.zza(this.zzf)) * 31) + zzflq.zza(this.zzg)) * 31) + (this.zzk == null ? 0 : this.zzk.hashCode())) * 31) + (this.zzl == null ? 0 : this.zzl.hashCode())) * 31) + (this.zzm == null ? 0 : this.zzm.hashCode())) * 31) + (this.zzh == null ? 0 : this.zzh.hashCode());
        zzbk zzbk = this.zzn;
        hashCode = ((((((((((hashCode * 31) + (zzbk == null ? 0 : zzbk.hashCode())) * 31) + Float.floatToIntBits(this.zzo)) * 31) + (this.zzp ? 1231 : 1237)) * 31) + zzflq.zza(this.zzq)) * 31) + this.zzi) * 31;
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                i = this.zzax.hashCode();
            }
        }
        return hashCode + i;
    }

    protected final int zza() {
        int i;
        int i2;
        int zza = super.zza();
        if (this.zza != null && this.zza.length > 0) {
            int i3 = 0;
            i = 0;
            for (String str : this.zza) {
                if (str != null) {
                    i++;
                    i3 += zzflk.zza(str);
                }
            }
            zza = (zza + i3) + (i * 1);
        }
        if (this.zzb != null && this.zzb.length > 0) {
            i2 = zza;
            for (zzfls zzfls : this.zzb) {
                if (zzfls != null) {
                    i2 += zzflk.zzb(2, zzfls);
                }
            }
            zza = i2;
        }
        if (this.zzc != null && this.zzc.length > 0) {
            i2 = zza;
            for (zzfls zzfls2 : this.zzc) {
                if (zzfls2 != null) {
                    i2 += zzflk.zzb(3, zzfls2);
                }
            }
            zza = i2;
        }
        if (this.zzd != null && this.zzd.length > 0) {
            i2 = zza;
            for (zzfls zzfls3 : this.zzd) {
                if (zzfls3 != null) {
                    i2 += zzflk.zzb(4, zzfls3);
                }
            }
            zza = i2;
        }
        if (this.zze != null && this.zze.length > 0) {
            i2 = zza;
            for (zzfls zzfls32 : this.zze) {
                if (zzfls32 != null) {
                    i2 += zzflk.zzb(5, zzfls32);
                }
            }
            zza = i2;
        }
        if (this.zzf != null && this.zzf.length > 0) {
            i2 = zza;
            for (zzfls zzfls322 : this.zzf) {
                if (zzfls322 != null) {
                    i2 += zzflk.zzb(6, zzfls322);
                }
            }
            zza = i2;
        }
        if (this.zzg != null && this.zzg.length > 0) {
            i2 = zza;
            for (zzfls zzfls3222 : this.zzg) {
                if (zzfls3222 != null) {
                    i2 += zzflk.zzb(7, zzfls3222);
                }
            }
            zza = i2;
        }
        if (!(this.zzk == null || this.zzk.equals(""))) {
            zza += zzflk.zzb(9, this.zzk);
        }
        if (!(this.zzl == null || this.zzl.equals(""))) {
            zza += zzflk.zzb(10, this.zzl);
        }
        if (!(this.zzm == null || this.zzm.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO))) {
            zza += zzflk.zzb(12, this.zzm);
        }
        if (!(this.zzh == null || this.zzh.equals(""))) {
            zza += zzflk.zzb(13, this.zzh);
        }
        if (this.zzn != null) {
            zza += zzflk.zzb(14, this.zzn);
        }
        if (Float.floatToIntBits(this.zzo) != Float.floatToIntBits(0.0f)) {
            zza += zzflk.zzb(15) + 4;
        }
        if (this.zzq != null && this.zzq.length > 0) {
            i = 0;
            int i4 = 0;
            for (String str2 : this.zzq) {
                if (str2 != null) {
                    i4++;
                    i += zzflk.zza(str2);
                }
            }
            zza = (zza + i) + (i4 * 2);
        }
        if (this.zzi != 0) {
            zza += zzflk.zzb(17, this.zzi);
        }
        if (this.zzp) {
            zza += zzflk.zzb(18) + 1;
        }
        if (this.zzj == null || this.zzj.length <= 0) {
            return zza;
        }
        i2 = 0;
        i = 0;
        for (String str3 : this.zzj) {
            if (str3 != null) {
                i++;
                i2 += zzflk.zza(str3);
            }
        }
        return (zza + i2) + (i * 2);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            int length;
            Object obj;
            switch (zza) {
                case 0:
                    return this;
                case 10:
                    zza = zzflv.zza(zzflj, 10);
                    length = this.zza == null ? 0 : this.zza.length;
                    obj = new String[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zza, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zze();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zze();
                    this.zza = obj;
                    break;
                case 18:
                    zza = zzflv.zza(zzflj, 18);
                    length = this.zzb == null ? 0 : this.zzb.length;
                    obj = new zzbt[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzb, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbt();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbt();
                    zzflj.zza(obj[length]);
                    this.zzb = obj;
                    break;
                case 26:
                    zza = zzflv.zza(zzflj, 26);
                    length = this.zzc == null ? 0 : this.zzc.length;
                    obj = new zzbo[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzc, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbo();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbo();
                    zzflj.zza(obj[length]);
                    this.zzc = obj;
                    break;
                case 34:
                    zza = zzflv.zza(zzflj, 34);
                    length = this.zzd == null ? 0 : this.zzd.length;
                    obj = new zzbl[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzd, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbl();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbl();
                    zzflj.zza(obj[length]);
                    this.zzd = obj;
                    break;
                case 42:
                    zza = zzflv.zza(zzflj, 42);
                    length = this.zze == null ? 0 : this.zze.length;
                    obj = new zzbl[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zze, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbl();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbl();
                    zzflj.zza(obj[length]);
                    this.zze = obj;
                    break;
                case 50:
                    zza = zzflv.zza(zzflj, 50);
                    length = this.zzf == null ? 0 : this.zzf.length;
                    obj = new zzbl[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzf, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbl();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbl();
                    zzflj.zza(obj[length]);
                    this.zzf = obj;
                    break;
                case 58:
                    zza = zzflv.zza(zzflj, 58);
                    length = this.zzg == null ? 0 : this.zzg.length;
                    obj = new zzbq[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzg, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzbq();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzbq();
                    zzflj.zza(obj[length]);
                    this.zzg = obj;
                    break;
                case 74:
                    this.zzk = zzflj.zze();
                    break;
                case 82:
                    this.zzl = zzflj.zze();
                    break;
                case 98:
                    this.zzm = zzflj.zze();
                    break;
                case 106:
                    this.zzh = zzflj.zze();
                    break;
                case 114:
                    if (this.zzn == null) {
                        this.zzn = new zzbk();
                    }
                    zzflj.zza(this.zzn);
                    break;
                case 125:
                    this.zzo = Float.intBitsToFloat(zzflj.zzj());
                    break;
                case 130:
                    zza = zzflv.zza(zzflj, 130);
                    length = this.zzq == null ? 0 : this.zzq.length;
                    obj = new String[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzq, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zze();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zze();
                    this.zzq = obj;
                    break;
                case 136:
                    this.zzi = zzflj.zzh();
                    break;
                case 144:
                    this.zzp = zzflj.zzd();
                    break;
                case 154:
                    zza = zzflv.zza(zzflj, 154);
                    length = this.zzj == null ? 0 : this.zzj.length;
                    obj = new String[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzj, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zze();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zze();
                    this.zzj = obj;
                    break;
                default:
                    if (super.zza(zzflj, zza)) {
                        break;
                    }
                    return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zza != null && this.zza.length > 0) {
            for (String str : this.zza) {
                if (str != null) {
                    zzflk.zza(1, str);
                }
            }
        }
        if (this.zzb != null && this.zzb.length > 0) {
            for (zzfls zzfls : this.zzb) {
                if (zzfls != null) {
                    zzflk.zza(2, zzfls);
                }
            }
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (zzfls zzfls2 : this.zzc) {
                if (zzfls2 != null) {
                    zzflk.zza(3, zzfls2);
                }
            }
        }
        if (this.zzd != null && this.zzd.length > 0) {
            for (zzfls zzfls22 : this.zzd) {
                if (zzfls22 != null) {
                    zzflk.zza(4, zzfls22);
                }
            }
        }
        if (this.zze != null && this.zze.length > 0) {
            for (zzfls zzfls222 : this.zze) {
                if (zzfls222 != null) {
                    zzflk.zza(5, zzfls222);
                }
            }
        }
        if (this.zzf != null && this.zzf.length > 0) {
            for (zzfls zzfls2222 : this.zzf) {
                if (zzfls2222 != null) {
                    zzflk.zza(6, zzfls2222);
                }
            }
        }
        if (this.zzg != null && this.zzg.length > 0) {
            for (zzfls zzfls22222 : this.zzg) {
                if (zzfls22222 != null) {
                    zzflk.zza(7, zzfls22222);
                }
            }
        }
        if (!(this.zzk == null || this.zzk.equals(""))) {
            zzflk.zza(9, this.zzk);
        }
        if (!(this.zzl == null || this.zzl.equals(""))) {
            zzflk.zza(10, this.zzl);
        }
        if (!(this.zzm == null || this.zzm.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO))) {
            zzflk.zza(12, this.zzm);
        }
        if (!(this.zzh == null || this.zzh.equals(""))) {
            zzflk.zza(13, this.zzh);
        }
        if (this.zzn != null) {
            zzflk.zza(14, this.zzn);
        }
        if (Float.floatToIntBits(this.zzo) != Float.floatToIntBits(0.0f)) {
            zzflk.zza(15, this.zzo);
        }
        if (this.zzq != null && this.zzq.length > 0) {
            for (String str2 : this.zzq) {
                if (str2 != null) {
                    zzflk.zza(16, str2);
                }
            }
        }
        if (this.zzi != 0) {
            zzflk.zza(17, this.zzi);
        }
        if (this.zzp) {
            zzflk.zza(18, this.zzp);
        }
        if (this.zzj != null && this.zzj.length > 0) {
            for (String str3 : this.zzj) {
                if (str3 != null) {
                    zzflk.zza(19, str3);
                }
            }
        }
        super.zza(zzflk);
    }
}
