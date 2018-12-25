package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfmo extends zzflm<zzfmo> implements Cloneable {
    private String[] zza;
    private String[] zzb;
    private int[] zzc;
    private long[] zzd;
    private long[] zze;

    public zzfmo() {
        this.zza = zzflv.zzf;
        this.zzb = zzflv.zzf;
        this.zzc = zzflv.zza;
        this.zzd = zzflv.zzb;
        this.zze = zzflv.zzb;
        this.zzax = null;
        this.zzay = -1;
    }

    private zzfmo zzb() {
        try {
            zzfmo zzfmo = (zzfmo) super.zzc();
            if (this.zza != null && this.zza.length > 0) {
                zzfmo.zza = (String[]) this.zza.clone();
            }
            if (this.zzb != null && this.zzb.length > 0) {
                zzfmo.zzb = (String[]) this.zzb.clone();
            }
            if (this.zzc != null && this.zzc.length > 0) {
                zzfmo.zzc = (int[]) this.zzc.clone();
            }
            if (this.zzd != null && this.zzd.length > 0) {
                zzfmo.zzd = (long[]) this.zzd.clone();
            }
            if (this.zze != null && this.zze.length > 0) {
                zzfmo.zze = (long[]) this.zze.clone();
            }
            return zzfmo;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzb();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfmo)) {
            return false;
        }
        zzfmo zzfmo = (zzfmo) obj;
        if (!zzflq.zza(this.zza, zzfmo.zza) || !zzflq.zza(this.zzb, zzfmo.zzb) || !zzflq.zza(this.zzc, zzfmo.zzc) || !zzflq.zza(this.zzd, zzfmo.zzd) || !zzflq.zza(this.zze, zzfmo.zze)) {
            return false;
        }
        if (this.zzax != null) {
            if (!this.zzax.zzb()) {
                return this.zzax.equals(zzfmo.zzax);
            }
        }
        return zzfmo.zzax == null || zzfmo.zzax.zzb();
    }

    public final int hashCode() {
        int hashCode;
        int hashCode2 = (((((((((((getClass().getName().hashCode() + 527) * 31) + zzflq.zza(this.zza)) * 31) + zzflq.zza(this.zzb)) * 31) + zzflq.zza(this.zzc)) * 31) + zzflq.zza(this.zzd)) * 31) + zzflq.zza(this.zze)) * 31;
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
        int i3;
        int zza = super.zza();
        if (this.zza != null && this.zza.length > 0) {
            i = 0;
            i2 = 0;
            for (String str : this.zza) {
                if (str != null) {
                    i2++;
                    i += zzflk.zza(str);
                }
            }
            zza = (zza + i) + (i2 * 1);
        }
        if (this.zzb != null && this.zzb.length > 0) {
            i = 0;
            i2 = 0;
            for (String str2 : this.zzb) {
                if (str2 != null) {
                    i2++;
                    i += zzflk.zza(str2);
                }
            }
            zza = (zza + i) + (i2 * 1);
        }
        if (this.zzc != null && this.zzc.length > 0) {
            i = 0;
            for (int i22 : this.zzc) {
                i += zzflk.zza(i22);
            }
            zza = (zza + i) + (this.zzc.length * 1);
        }
        if (this.zzd != null && this.zzd.length > 0) {
            i = 0;
            for (long zza2 : this.zzd) {
                i += zzflk.zza(zza2);
            }
            zza = (zza + i) + (this.zzd.length * 1);
        }
        if (this.zze == null || this.zze.length <= 0) {
            return zza;
        }
        i3 = 0;
        for (long zza3 : this.zze) {
            i3 += zzflk.zza(zza3);
        }
        return (zza + i3) + (this.zze.length * 1);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            int length;
            Object obj;
            if (zza == 10) {
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
            } else if (zza == 18) {
                zza = zzflv.zza(zzflj, 18);
                length = this.zzb == null ? 0 : this.zzb.length;
                obj = new String[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zzb, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = zzflj.zze();
                    zzflj.zza();
                    length++;
                }
                obj[length] = zzflj.zze();
                this.zzb = obj;
            } else if (zza != 24) {
                int i;
                Object obj2;
                if (zza == 26) {
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzc();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzc == null ? 0 : this.zzc.length;
                    obj2 = new int[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzc, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzc();
                        length++;
                    }
                    this.zzc = obj2;
                } else if (zza == 32) {
                    zza = zzflv.zza(zzflj, 32);
                    length = this.zzd == null ? 0 : this.zzd.length;
                    obj = new long[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzd, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzb();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzb();
                    this.zzd = obj;
                } else if (zza == 34) {
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzb();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zzd == null ? 0 : this.zzd.length;
                    obj2 = new long[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzd, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzb();
                        length++;
                    }
                    this.zzd = obj2;
                } else if (zza == 40) {
                    zza = zzflv.zza(zzflj, 40);
                    length = this.zze == null ? 0 : this.zze.length;
                    obj = new long[(zza + length)];
                    if (length != 0) {
                        System.arraycopy(this.zze, 0, obj, 0, length);
                    }
                    while (length < obj.length - 1) {
                        obj[length] = zzflj.zzb();
                        zzflj.zza();
                        length++;
                    }
                    obj[length] = zzflj.zzb();
                    this.zze = obj;
                } else if (zza == 42) {
                    zza = zzflj.zzc(zzflj.zzh());
                    length = zzflj.zzm();
                    i = 0;
                    while (zzflj.zzl() > 0) {
                        zzflj.zzb();
                        i++;
                    }
                    zzflj.zze(length);
                    length = this.zze == null ? 0 : this.zze.length;
                    obj2 = new long[(i + length)];
                    if (length != 0) {
                        System.arraycopy(this.zze, 0, obj2, 0, length);
                    }
                    while (length < obj2.length) {
                        obj2[length] = zzflj.zzb();
                        length++;
                    }
                    this.zze = obj2;
                } else if (!super.zza(zzflj, zza)) {
                    return this;
                }
                zzflj.zzd(zza);
            } else {
                zza = zzflv.zza(zzflj, 24);
                length = this.zzc == null ? 0 : this.zzc.length;
                obj = new int[(zza + length)];
                if (length != 0) {
                    System.arraycopy(this.zzc, 0, obj, 0, length);
                }
                while (length < obj.length - 1) {
                    obj[length] = zzflj.zzc();
                    zzflj.zza();
                    length++;
                }
                obj[length] = zzflj.zzc();
                this.zzc = obj;
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
            for (String str2 : this.zzb) {
                if (str2 != null) {
                    zzflk.zza(2, str2);
                }
            }
        }
        if (this.zzc != null && this.zzc.length > 0) {
            for (int zza : this.zzc) {
                zzflk.zza(3, zza);
            }
        }
        if (this.zzd != null && this.zzd.length > 0) {
            for (long zzb : this.zzd) {
                zzflk.zzb(4, zzb);
            }
        }
        if (this.zze != null && this.zze.length > 0) {
            for (long zzb2 : this.zze) {
                zzflk.zzb(5, zzb2);
            }
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzc() throws CloneNotSupportedException {
        return (zzfmo) clone();
    }

    public final /* synthetic */ zzfls zzd() throws CloneNotSupportedException {
        return (zzfmo) clone();
    }
}
