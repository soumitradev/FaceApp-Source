package com.google.android.gms.internal;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.net.HttpStatus;
import java.io.IOException;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.ReportDigitalPortMessageWriter;

public final class zzcoe extends zzflm<zzcoe> {
    private static volatile zzcoe[] zzah;
    public Integer zza;
    public zzcoa[] zzaa;
    public String zzab;
    public Integer zzac;
    public String zzad;
    public Long zzae;
    public Long zzaf;
    public String zzag;
    private Integer zzai;
    private Integer zzaj;
    public zzcob[] zzb;
    public zzcog[] zzc;
    public Long zzd;
    public Long zze;
    public Long zzf;
    public Long zzg;
    public Long zzh;
    public String zzi;
    public String zzj;
    public String zzk;
    public String zzl;
    public Integer zzm;
    public String zzn;
    public String zzo;
    public String zzp;
    public Long zzq;
    public Long zzr;
    public String zzs;
    public Boolean zzt;
    public String zzu;
    public Long zzv;
    public Integer zzw;
    public String zzx;
    public String zzy;
    public Boolean zzz;

    public zzcoe() {
        this.zza = null;
        this.zzb = zzcob.zzb();
        this.zzc = zzcog.zzb();
        this.zzd = null;
        this.zze = null;
        this.zzf = null;
        this.zzg = null;
        this.zzh = null;
        this.zzi = null;
        this.zzj = null;
        this.zzk = null;
        this.zzl = null;
        this.zzm = null;
        this.zzn = null;
        this.zzo = null;
        this.zzp = null;
        this.zzq = null;
        this.zzr = null;
        this.zzs = null;
        this.zzt = null;
        this.zzu = null;
        this.zzv = null;
        this.zzw = null;
        this.zzx = null;
        this.zzy = null;
        this.zzz = null;
        this.zzaa = zzcoa.zzb();
        this.zzab = null;
        this.zzac = null;
        this.zzai = null;
        this.zzaj = null;
        this.zzad = null;
        this.zzae = null;
        this.zzaf = null;
        this.zzag = null;
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzcoe[] zzb() {
        if (zzah == null) {
            synchronized (zzflq.zzb) {
                if (zzah == null) {
                    zzah = new zzcoe[0];
                }
            }
        }
        return zzah;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcoe)) {
            return false;
        }
        zzcoe zzcoe = (zzcoe) obj;
        if (this.zza == null) {
            if (zzcoe.zza != null) {
                return false;
            }
        } else if (!this.zza.equals(zzcoe.zza)) {
            return false;
        }
        if (!zzflq.zza(this.zzb, zzcoe.zzb) || !zzflq.zza(this.zzc, zzcoe.zzc)) {
            return false;
        }
        if (this.zzd == null) {
            if (zzcoe.zzd != null) {
                return false;
            }
        } else if (!this.zzd.equals(zzcoe.zzd)) {
            return false;
        }
        if (this.zze == null) {
            if (zzcoe.zze != null) {
                return false;
            }
        } else if (!this.zze.equals(zzcoe.zze)) {
            return false;
        }
        if (this.zzf == null) {
            if (zzcoe.zzf != null) {
                return false;
            }
        } else if (!this.zzf.equals(zzcoe.zzf)) {
            return false;
        }
        if (this.zzg == null) {
            if (zzcoe.zzg != null) {
                return false;
            }
        } else if (!this.zzg.equals(zzcoe.zzg)) {
            return false;
        }
        if (this.zzh == null) {
            if (zzcoe.zzh != null) {
                return false;
            }
        } else if (!this.zzh.equals(zzcoe.zzh)) {
            return false;
        }
        if (this.zzi == null) {
            if (zzcoe.zzi != null) {
                return false;
            }
        } else if (!this.zzi.equals(zzcoe.zzi)) {
            return false;
        }
        if (this.zzj == null) {
            if (zzcoe.zzj != null) {
                return false;
            }
        } else if (!this.zzj.equals(zzcoe.zzj)) {
            return false;
        }
        if (this.zzk == null) {
            if (zzcoe.zzk != null) {
                return false;
            }
        } else if (!this.zzk.equals(zzcoe.zzk)) {
            return false;
        }
        if (this.zzl == null) {
            if (zzcoe.zzl != null) {
                return false;
            }
        } else if (!this.zzl.equals(zzcoe.zzl)) {
            return false;
        }
        if (this.zzm == null) {
            if (zzcoe.zzm != null) {
                return false;
            }
        } else if (!this.zzm.equals(zzcoe.zzm)) {
            return false;
        }
        if (this.zzn == null) {
            if (zzcoe.zzn != null) {
                return false;
            }
        } else if (!this.zzn.equals(zzcoe.zzn)) {
            return false;
        }
        if (this.zzo == null) {
            if (zzcoe.zzo != null) {
                return false;
            }
        } else if (!this.zzo.equals(zzcoe.zzo)) {
            return false;
        }
        if (this.zzp == null) {
            if (zzcoe.zzp != null) {
                return false;
            }
        } else if (!this.zzp.equals(zzcoe.zzp)) {
            return false;
        }
        if (this.zzq == null) {
            if (zzcoe.zzq != null) {
                return false;
            }
        } else if (!this.zzq.equals(zzcoe.zzq)) {
            return false;
        }
        if (this.zzr == null) {
            if (zzcoe.zzr != null) {
                return false;
            }
        } else if (!this.zzr.equals(zzcoe.zzr)) {
            return false;
        }
        if (this.zzs == null) {
            if (zzcoe.zzs != null) {
                return false;
            }
        } else if (!this.zzs.equals(zzcoe.zzs)) {
            return false;
        }
        if (this.zzt == null) {
            if (zzcoe.zzt != null) {
                return false;
            }
        } else if (!this.zzt.equals(zzcoe.zzt)) {
            return false;
        }
        if (this.zzu == null) {
            if (zzcoe.zzu != null) {
                return false;
            }
        } else if (!this.zzu.equals(zzcoe.zzu)) {
            return false;
        }
        if (this.zzv == null) {
            if (zzcoe.zzv != null) {
                return false;
            }
        } else if (!this.zzv.equals(zzcoe.zzv)) {
            return false;
        }
        if (this.zzw == null) {
            if (zzcoe.zzw != null) {
                return false;
            }
        } else if (!this.zzw.equals(zzcoe.zzw)) {
            return false;
        }
        if (this.zzx == null) {
            if (zzcoe.zzx != null) {
                return false;
            }
        } else if (!this.zzx.equals(zzcoe.zzx)) {
            return false;
        }
        if (this.zzy == null) {
            if (zzcoe.zzy != null) {
                return false;
            }
        } else if (!this.zzy.equals(zzcoe.zzy)) {
            return false;
        }
        if (this.zzz == null) {
            if (zzcoe.zzz != null) {
                return false;
            }
        } else if (!this.zzz.equals(zzcoe.zzz)) {
            return false;
        }
        if (!zzflq.zza(this.zzaa, zzcoe.zzaa)) {
            return false;
        }
        if (this.zzab == null) {
            if (zzcoe.zzab != null) {
                return false;
            }
        } else if (!this.zzab.equals(zzcoe.zzab)) {
            return false;
        }
        if (this.zzac == null) {
            if (zzcoe.zzac != null) {
                return false;
            }
        } else if (!this.zzac.equals(zzcoe.zzac)) {
            return false;
        }
        if (this.zzai == null) {
            if (zzcoe.zzai != null) {
                return false;
            }
        } else if (!this.zzai.equals(zzcoe.zzai)) {
            return false;
        }
        if (this.zzaj == null) {
            if (zzcoe.zzaj != null) {
                return false;
            }
        } else if (!this.zzaj.equals(zzcoe.zzaj)) {
            return false;
        }
        if (this.zzad == null) {
            if (zzcoe.zzad != null) {
                return false;
            }
        } else if (!this.zzad.equals(zzcoe.zzad)) {
            return false;
        }
        if (this.zzae == null) {
            if (zzcoe.zzae != null) {
                return false;
            }
        } else if (!this.zzae.equals(zzcoe.zzae)) {
            return false;
        }
        if (this.zzaf == null) {
            if (zzcoe.zzaf != null) {
                return false;
            }
        } else if (!this.zzaf.equals(zzcoe.zzaf)) {
            return false;
        }
        if (this.zzag == null) {
            if (zzcoe.zzag != null) {
                return false;
            }
        } else if (!this.zzag.equals(zzcoe.zzag)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzcoe.zzax);
            }
        }
        return zzcoe.zzax == null || zzcoe.zzax.zzb();
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + (this.zza == null ? 0 : this.zza.hashCode())) * 31) + zzflq.zza(this.zzb)) * 31) + zzflq.zza(this.zzc)) * 31) + (this.zzd == null ? 0 : this.zzd.hashCode())) * 31) + (this.zze == null ? 0 : this.zze.hashCode())) * 31) + (this.zzf == null ? 0 : this.zzf.hashCode())) * 31) + (this.zzg == null ? 0 : this.zzg.hashCode())) * 31) + (this.zzh == null ? 0 : this.zzh.hashCode())) * 31) + (this.zzi == null ? 0 : this.zzi.hashCode())) * 31) + (this.zzj == null ? 0 : this.zzj.hashCode())) * 31) + (this.zzk == null ? 0 : this.zzk.hashCode())) * 31) + (this.zzl == null ? 0 : this.zzl.hashCode())) * 31) + (this.zzm == null ? 0 : this.zzm.hashCode())) * 31) + (this.zzn == null ? 0 : this.zzn.hashCode())) * 31) + (this.zzo == null ? 0 : this.zzo.hashCode())) * 31) + (this.zzp == null ? 0 : this.zzp.hashCode())) * 31) + (this.zzq == null ? 0 : this.zzq.hashCode())) * 31) + (this.zzr == null ? 0 : this.zzr.hashCode())) * 31) + (this.zzs == null ? 0 : this.zzs.hashCode())) * 31) + (this.zzt == null ? 0 : this.zzt.hashCode())) * 31) + (this.zzu == null ? 0 : this.zzu.hashCode())) * 31) + (this.zzv == null ? 0 : this.zzv.hashCode())) * 31) + (this.zzw == null ? 0 : this.zzw.hashCode())) * 31) + (this.zzx == null ? 0 : this.zzx.hashCode())) * 31) + (this.zzy == null ? 0 : this.zzy.hashCode())) * 31) + (this.zzz == null ? 0 : this.zzz.hashCode())) * 31) + zzflq.zza(this.zzaa)) * 31) + (this.zzab == null ? 0 : this.zzab.hashCode())) * 31) + (this.zzac == null ? 0 : this.zzac.hashCode())) * 31) + (this.zzai == null ? 0 : this.zzai.hashCode())) * 31) + (this.zzaj == null ? 0 : this.zzaj.hashCode())) * 31) + (this.zzad == null ? 0 : this.zzad.hashCode())) * 31) + (this.zzae == null ? 0 : this.zzae.hashCode())) * 31) + (this.zzaf == null ? 0 : this.zzaf.hashCode())) * 31) + (this.zzag == null ? 0 : this.zzag.hashCode())) * 31;
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
        if (this.zza != null) {
            zza += zzflk.zzb(1, this.zza.intValue());
        }
        if (this.zzb != null && this.zzb.length > 0) {
            i = zza;
            for (zzfls zzfls : this.zzb) {
                if (zzfls != null) {
                    i += zzflk.zzb(2, zzfls);
                }
            }
            zza = i;
        }
        if (this.zzc != null && this.zzc.length > 0) {
            i = zza;
            for (zzfls zzfls2 : this.zzc) {
                if (zzfls2 != null) {
                    i += zzflk.zzb(3, zzfls2);
                }
            }
            zza = i;
        }
        if (this.zzd != null) {
            zza += zzflk.zze(4, this.zzd.longValue());
        }
        if (this.zze != null) {
            zza += zzflk.zze(5, this.zze.longValue());
        }
        if (this.zzf != null) {
            zza += zzflk.zze(6, this.zzf.longValue());
        }
        if (this.zzh != null) {
            zza += zzflk.zze(7, this.zzh.longValue());
        }
        if (this.zzi != null) {
            zza += zzflk.zzb(8, this.zzi);
        }
        if (this.zzj != null) {
            zza += zzflk.zzb(9, this.zzj);
        }
        if (this.zzk != null) {
            zza += zzflk.zzb(10, this.zzk);
        }
        if (this.zzl != null) {
            zza += zzflk.zzb(11, this.zzl);
        }
        if (this.zzm != null) {
            zza += zzflk.zzb(12, this.zzm.intValue());
        }
        if (this.zzn != null) {
            zza += zzflk.zzb(13, this.zzn);
        }
        if (this.zzo != null) {
            zza += zzflk.zzb(14, this.zzo);
        }
        if (this.zzp != null) {
            zza += zzflk.zzb(16, this.zzp);
        }
        if (this.zzq != null) {
            zza += zzflk.zze(17, this.zzq.longValue());
        }
        if (this.zzr != null) {
            zza += zzflk.zze(18, this.zzr.longValue());
        }
        if (this.zzs != null) {
            zza += zzflk.zzb(19, this.zzs);
        }
        if (this.zzt != null) {
            this.zzt.booleanValue();
            zza += zzflk.zzb(20) + 1;
        }
        if (this.zzu != null) {
            zza += zzflk.zzb(21, this.zzu);
        }
        if (this.zzv != null) {
            zza += zzflk.zze(22, this.zzv.longValue());
        }
        if (this.zzw != null) {
            zza += zzflk.zzb(23, this.zzw.intValue());
        }
        if (this.zzx != null) {
            zza += zzflk.zzb(24, this.zzx);
        }
        if (this.zzy != null) {
            zza += zzflk.zzb(25, this.zzy);
        }
        if (this.zzg != null) {
            zza += zzflk.zze(26, this.zzg.longValue());
        }
        if (this.zzz != null) {
            this.zzz.booleanValue();
            zza += zzflk.zzb(28) + 1;
        }
        if (this.zzaa != null && this.zzaa.length > 0) {
            for (zzfls zzfls3 : this.zzaa) {
                if (zzfls3 != null) {
                    zza += zzflk.zzb(29, zzfls3);
                }
            }
        }
        if (this.zzab != null) {
            zza += zzflk.zzb(30, this.zzab);
        }
        if (this.zzac != null) {
            zza += zzflk.zzb(31, this.zzac.intValue());
        }
        if (this.zzai != null) {
            zza += zzflk.zzb(32, this.zzai.intValue());
        }
        if (this.zzaj != null) {
            zza += zzflk.zzb(33, this.zzaj.intValue());
        }
        if (this.zzad != null) {
            zza += zzflk.zzb(34, this.zzad);
        }
        if (this.zzae != null) {
            zza += zzflk.zze(35, this.zzae.longValue());
        }
        if (this.zzaf != null) {
            zza += zzflk.zze(36, this.zzaf.longValue());
        }
        return this.zzag != null ? zza + zzflk.zzb(37, this.zzag) : zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            int length;
            Object obj;
            switch (zza) {
                case 0:
                    return this;
                case 8:
                    this.zza = Integer.valueOf(zzflj.zzh());
                    break;
                case 18:
                    zza = zzflv.zza(zzflj, 18);
                    length = this.zzb == null ? 0 : this.zzb.length;
                    obj = new zzcob[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzb, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzcob();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzcob();
                    zzflj.zza(obj[length]);
                    this.zzb = obj;
                    break;
                case 26:
                    zza = zzflv.zza(zzflj, 26);
                    length = this.zzc == null ? 0 : this.zzc.length;
                    obj = new zzcog[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzc, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzcog();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzcog();
                    zzflj.zza(obj[length]);
                    this.zzc = obj;
                    break;
                case 32:
                    this.zzd = Long.valueOf(zzflj.zzi());
                    break;
                case 40:
                    this.zze = Long.valueOf(zzflj.zzi());
                    break;
                case 48:
                    this.zzf = Long.valueOf(zzflj.zzi());
                    break;
                case 56:
                    this.zzh = Long.valueOf(zzflj.zzi());
                    break;
                case 66:
                    this.zzi = zzflj.zze();
                    break;
                case 74:
                    this.zzj = zzflj.zze();
                    break;
                case 82:
                    this.zzk = zzflj.zze();
                    break;
                case 90:
                    this.zzl = zzflj.zze();
                    break;
                case 96:
                    this.zzm = Integer.valueOf(zzflj.zzh());
                    break;
                case 106:
                    this.zzn = zzflj.zze();
                    break;
                case 114:
                    this.zzo = zzflj.zze();
                    break;
                case 130:
                    this.zzp = zzflj.zze();
                    break;
                case 136:
                    this.zzq = Long.valueOf(zzflj.zzi());
                    break;
                case 144:
                    this.zzr = Long.valueOf(zzflj.zzi());
                    break;
                case 154:
                    this.zzs = zzflj.zze();
                    break;
                case 160:
                    this.zzt = Boolean.valueOf(zzflj.zzd());
                    break;
                case 170:
                    this.zzu = zzflj.zze();
                    break;
                case 176:
                    this.zzv = Long.valueOf(zzflj.zzi());
                    break;
                case 184:
                    this.zzw = Integer.valueOf(zzflj.zzh());
                    break;
                case 194:
                    this.zzx = zzflj.zze();
                    break;
                case HttpStatus.SC_ACCEPTED /*202*/:
                    this.zzy = zzflj.zze();
                    break;
                case ReportDigitalPortMessageWriter.COMMAND /*208*/:
                    this.zzg = Long.valueOf(zzflj.zzi());
                    break;
                case AnalogMessageWriter.COMMAND /*224*/:
                    this.zzz = Boolean.valueOf(zzflj.zzd());
                    break;
                case 234:
                    zza = zzflv.zza(zzflj, 234);
                    length = this.zzaa == null ? 0 : this.zzaa.length;
                    obj = new zzcoa[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzaa, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzcoa();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzcoa();
                    zzflj.zza(obj[length]);
                    this.zzaa = obj;
                    break;
                case 242:
                    this.zzab = zzflj.zze();
                    break;
                case Keys.F5 /*248*/:
                    this.zzac = Integer.valueOf(zzflj.zzh());
                    break;
                case 256:
                    this.zzai = Integer.valueOf(zzflj.zzh());
                    break;
                case 264:
                    this.zzaj = Integer.valueOf(zzflj.zzh());
                    break;
                case 274:
                    this.zzad = zzflj.zze();
                    break;
                case 280:
                    this.zzae = Long.valueOf(zzflj.zzi());
                    break;
                case 288:
                    this.zzaf = Long.valueOf(zzflj.zzi());
                    break;
                case 298:
                    this.zzag = zzflj.zze();
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
        if (this.zza != null) {
            zzflk.zza(1, this.zza.intValue());
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
        if (this.zzd != null) {
            zzflk.zzb(4, this.zzd.longValue());
        }
        if (this.zze != null) {
            zzflk.zzb(5, this.zze.longValue());
        }
        if (this.zzf != null) {
            zzflk.zzb(6, this.zzf.longValue());
        }
        if (this.zzh != null) {
            zzflk.zzb(7, this.zzh.longValue());
        }
        if (this.zzi != null) {
            zzflk.zza(8, this.zzi);
        }
        if (this.zzj != null) {
            zzflk.zza(9, this.zzj);
        }
        if (this.zzk != null) {
            zzflk.zza(10, this.zzk);
        }
        if (this.zzl != null) {
            zzflk.zza(11, this.zzl);
        }
        if (this.zzm != null) {
            zzflk.zza(12, this.zzm.intValue());
        }
        if (this.zzn != null) {
            zzflk.zza(13, this.zzn);
        }
        if (this.zzo != null) {
            zzflk.zza(14, this.zzo);
        }
        if (this.zzp != null) {
            zzflk.zza(16, this.zzp);
        }
        if (this.zzq != null) {
            zzflk.zzb(17, this.zzq.longValue());
        }
        if (this.zzr != null) {
            zzflk.zzb(18, this.zzr.longValue());
        }
        if (this.zzs != null) {
            zzflk.zza(19, this.zzs);
        }
        if (this.zzt != null) {
            zzflk.zza(20, this.zzt.booleanValue());
        }
        if (this.zzu != null) {
            zzflk.zza(21, this.zzu);
        }
        if (this.zzv != null) {
            zzflk.zzb(22, this.zzv.longValue());
        }
        if (this.zzw != null) {
            zzflk.zza(23, this.zzw.intValue());
        }
        if (this.zzx != null) {
            zzflk.zza(24, this.zzx);
        }
        if (this.zzy != null) {
            zzflk.zza(25, this.zzy);
        }
        if (this.zzg != null) {
            zzflk.zzb(26, this.zzg.longValue());
        }
        if (this.zzz != null) {
            zzflk.zza(28, this.zzz.booleanValue());
        }
        if (this.zzaa != null && this.zzaa.length > 0) {
            for (zzfls zzfls3 : this.zzaa) {
                if (zzfls3 != null) {
                    zzflk.zza(29, zzfls3);
                }
            }
        }
        if (this.zzab != null) {
            zzflk.zza(30, this.zzab);
        }
        if (this.zzac != null) {
            zzflk.zza(31, this.zzac.intValue());
        }
        if (this.zzai != null) {
            zzflk.zza(32, this.zzai.intValue());
        }
        if (this.zzaj != null) {
            zzflk.zza(33, this.zzaj.intValue());
        }
        if (this.zzad != null) {
            zzflk.zza(34, this.zzad);
        }
        if (this.zzae != null) {
            zzflk.zzb(35, this.zzae.longValue());
        }
        if (this.zzaf != null) {
            zzflk.zzb(36, this.zzaf.longValue());
        }
        if (this.zzag != null) {
            zzflk.zza(37, this.zzag);
        }
        super.zza(zzflk);
    }
}
