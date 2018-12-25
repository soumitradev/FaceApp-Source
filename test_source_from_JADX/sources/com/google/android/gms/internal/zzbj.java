package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbj {

    public static final class zza extends zzflm<zza> {
        public static final zzfln<zzbt, zza> zza = zzfln.zza(11, zza.class, 810);
        private static final zza[] zzg = new zza[0];
        public int[] zzb;
        public int[] zzc;
        public int[] zzd;
        public int[] zze;
        public int zzf;
        private int zzh;
        private int zzi;

        public zza() {
            this.zzb = zzflv.zza;
            this.zzc = zzflv.zza;
            this.zzd = zzflv.zza;
            this.zzh = 0;
            this.zze = zzflv.zza;
            this.zzf = 0;
            this.zzi = 0;
            this.zzax = null;
            this.zzay = -1;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            if (!zzflq.zza(this.zzb, zza.zzb) || !zzflq.zza(this.zzc, zza.zzc) || !zzflq.zza(this.zzd, zza.zzd) || this.zzh != zza.zzh || !zzflq.zza(this.zze, zza.zze) || this.zzf != zza.zzf || this.zzi != zza.zzi) {
                return false;
            }
            if (this.zzax != null) {
                if (!this.zzax.zzb()) {
                    return this.zzax.equals(zza.zzax);
                }
            }
            return zza.zzax == null || zza.zzax.zzb();
        }

        public final int hashCode() {
            int hashCode;
            int hashCode2 = (((((((((((((((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zzb)) * 31) + zzflq.zza(this.zzc)) * 31) + zzflq.zza(this.zzd)) * 31) + this.zzh) * 31) + zzflq.zza(this.zze)) * 31) + this.zzf) * 31) + this.zzi) * 31;
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
            if (this.zzb != null && this.zzb.length > 0) {
                i = 0;
                for (int zza2 : this.zzb) {
                    i += zzflk.zza(zza2);
                }
                zza = (zza + i) + (this.zzb.length * 1);
            }
            if (this.zzc != null && this.zzc.length > 0) {
                i = 0;
                for (int zza22 : this.zzc) {
                    i += zzflk.zza(zza22);
                }
                zza = (zza + i) + (this.zzc.length * 1);
            }
            if (this.zzd != null && this.zzd.length > 0) {
                i = 0;
                for (int zza222 : this.zzd) {
                    i += zzflk.zza(zza222);
                }
                zza = (zza + i) + (this.zzd.length * 1);
            }
            if (this.zzh != 0) {
                zza += zzflk.zzb(4, this.zzh);
            }
            if (this.zze != null && this.zze.length > 0) {
                i2 = 0;
                for (int i3 : this.zze) {
                    i2 += zzflk.zza(i3);
                }
                zza = (zza + i2) + (this.zze.length * 1);
            }
            if (this.zzf != 0) {
                zza += zzflk.zzb(6, this.zzf);
            }
            return this.zzi != 0 ? zza + zzflk.zzb(7, this.zzi) : zza;
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
                    case 10:
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
                    case 16:
                        zza = zzflv.zza(zzflj, 16);
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
                    case 18:
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
                    case 24:
                        zza = zzflv.zza(zzflj, 24);
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
                    case 26:
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
                    case 32:
                        this.zzh = zzflj.zzh();
                        continue;
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
                        this.zzf = zzflj.zzh();
                        continue;
                    case 56:
                        this.zzi = zzflj.zzh();
                        continue;
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
            if (this.zzb != null && this.zzb.length > 0) {
                for (int zza : this.zzb) {
                    zzflk.zza(1, zza);
                }
            }
            if (this.zzc != null && this.zzc.length > 0) {
                for (int zza2 : this.zzc) {
                    zzflk.zza(2, zza2);
                }
            }
            if (this.zzd != null && this.zzd.length > 0) {
                for (int zza22 : this.zzd) {
                    zzflk.zza(3, zza22);
                }
            }
            if (this.zzh != 0) {
                zzflk.zza(4, this.zzh);
            }
            if (this.zze != null && this.zze.length > 0) {
                for (int zza3 : this.zze) {
                    zzflk.zza(5, zza3);
                }
            }
            if (this.zzf != 0) {
                zzflk.zza(6, this.zzf);
            }
            if (this.zzi != 0) {
                zzflk.zza(7, this.zzi);
            }
            super.zza(zzflk);
        }
    }
}
