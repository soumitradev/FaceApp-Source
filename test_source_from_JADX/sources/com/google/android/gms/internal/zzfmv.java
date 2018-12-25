package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfmv extends zzflm<zzfmv> {
    public String zza;
    public String zzb;
    public long zzc;
    public String zzd;
    public long zze;
    public long zzf;
    public String zzg;
    public String zzh;
    public String zzi;
    public String zzj;
    public String zzk;
    public int zzl;
    public zzfmu[] zzm;

    public zzfmv() {
        this.zza = "";
        this.zzb = "";
        this.zzc = 0;
        this.zzd = "";
        this.zze = 0;
        this.zzf = 0;
        this.zzg = "";
        this.zzh = "";
        this.zzi = "";
        this.zzj = "";
        this.zzk = "";
        this.zzl = 0;
        this.zzm = zzfmu.zzb();
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzfmv zza(byte[] bArr) throws zzflr {
        return (zzfmv) zzfls.zza(new zzfmv(), bArr);
    }

    protected final int zza() {
        int zza = super.zza();
        if (!(this.zza == null || this.zza.equals(""))) {
            zza += zzflk.zzb(1, this.zza);
        }
        if (!(this.zzb == null || this.zzb.equals(""))) {
            zza += zzflk.zzb(2, this.zzb);
        }
        if (this.zzc != 0) {
            zza += zzflk.zze(3, this.zzc);
        }
        if (!(this.zzd == null || this.zzd.equals(""))) {
            zza += zzflk.zzb(4, this.zzd);
        }
        if (this.zze != 0) {
            zza += zzflk.zze(5, this.zze);
        }
        if (this.zzf != 0) {
            zza += zzflk.zze(6, this.zzf);
        }
        if (!(this.zzg == null || this.zzg.equals(""))) {
            zza += zzflk.zzb(7, this.zzg);
        }
        if (!(this.zzh == null || this.zzh.equals(""))) {
            zza += zzflk.zzb(8, this.zzh);
        }
        if (!(this.zzi == null || this.zzi.equals(""))) {
            zza += zzflk.zzb(9, this.zzi);
        }
        if (!(this.zzj == null || this.zzj.equals(""))) {
            zza += zzflk.zzb(10, this.zzj);
        }
        if (!(this.zzk == null || this.zzk.equals(""))) {
            zza += zzflk.zzb(11, this.zzk);
        }
        if (this.zzl != 0) {
            zza += zzflk.zzb(12, this.zzl);
        }
        if (this.zzm != null && this.zzm.length > 0) {
            for (zzfls zzfls : this.zzm) {
                if (zzfls != null) {
                    zza += zzflk.zzb(13, zzfls);
                }
            }
        }
        return zza;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            switch (zza) {
                case 0:
                    return this;
                case 10:
                    this.zza = zzflj.zze();
                    break;
                case 18:
                    this.zzb = zzflj.zze();
                    break;
                case 24:
                    this.zzc = zzflj.zzb();
                    break;
                case 34:
                    this.zzd = zzflj.zze();
                    break;
                case 40:
                    this.zze = zzflj.zzb();
                    break;
                case 48:
                    this.zzf = zzflj.zzb();
                    break;
                case 58:
                    this.zzg = zzflj.zze();
                    break;
                case 66:
                    this.zzh = zzflj.zze();
                    break;
                case 74:
                    this.zzi = zzflj.zze();
                    break;
                case 82:
                    this.zzj = zzflj.zze();
                    break;
                case 90:
                    this.zzk = zzflj.zze();
                    break;
                case 96:
                    this.zzl = zzflj.zzc();
                    break;
                case 106:
                    zza = zzflv.zza(zzflj, 106);
                    int length = this.zzm == null ? 0 : this.zzm.length;
                    Object obj = new zzfmu[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzm, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = new zzfmu();
                        zzflj.zza(obj[length]);
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = new zzfmu();
                    zzflj.zza(obj[length]);
                    this.zzm = obj;
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
        if (!(this.zza == null || this.zza.equals(""))) {
            zzflk.zza(1, this.zza);
        }
        if (!(this.zzb == null || this.zzb.equals(""))) {
            zzflk.zza(2, this.zzb);
        }
        if (this.zzc != 0) {
            zzflk.zzb(3, this.zzc);
        }
        if (!(this.zzd == null || this.zzd.equals(""))) {
            zzflk.zza(4, this.zzd);
        }
        if (this.zze != 0) {
            zzflk.zzb(5, this.zze);
        }
        if (this.zzf != 0) {
            zzflk.zzb(6, this.zzf);
        }
        if (!(this.zzg == null || this.zzg.equals(""))) {
            zzflk.zza(7, this.zzg);
        }
        if (!(this.zzh == null || this.zzh.equals(""))) {
            zzflk.zza(8, this.zzh);
        }
        if (!(this.zzi == null || this.zzi.equals(""))) {
            zzflk.zza(9, this.zzi);
        }
        if (!(this.zzj == null || this.zzj.equals(""))) {
            zzflk.zza(10, this.zzj);
        }
        if (!(this.zzk == null || this.zzk.equals(""))) {
            zzflk.zza(11, this.zzk);
        }
        if (this.zzl != 0) {
            zzflk.zza(12, this.zzl);
        }
        if (this.zzm != null && this.zzm.length > 0) {
            for (zzfls zzfls : this.zzm) {
                if (zzfls != null) {
                    zzflk.zza(13, zzfls);
                }
            }
        }
        super.zza(zzflk);
    }
}
