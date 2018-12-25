package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhu.zzb;
import com.google.android.gms.internal.zzfhu.zzg;
import com.google.android.gms.internal.zzfhu.zzh;
import java.io.IOException;

public final class zzfgp extends zzfhu<zzfgp, zza> implements zzfje {
    private static final zzfgp zzf;
    private static volatile zzfjl<zzfgp> zzg;
    private String zzd = "";
    private zzfgs zze = zzfgs.zza;

    public static final class zza extends com.google.android.gms.internal.zzfhu.zza<zzfgp, zza> implements zzfje {
        private zza() {
            super(zzfgp.zzf);
        }
    }

    static {
        zzfhu zzfgp = new zzfgp();
        zzf = zzfgp;
        zzfgp.zza(zzg.zzf, null, null);
        zzfgp.zzb.zzc();
    }

    private zzfgp() {
    }

    public static zzfgp zzb() {
        return zzf;
    }

    public final int zza() {
        int i = this.zzc;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        if (!this.zzd.isEmpty()) {
            i2 = 0 + zzfhg.zzb(1, this.zzd);
        }
        if (!this.zze.zzb()) {
            i2 += zzfhg.zzc(2, this.zze);
        }
        i2 += this.zzb.zze();
        this.zzc = i2;
        return i2;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        boolean z = true;
        switch (zzfgq.zza[i - 1]) {
            case 1:
                return new zzfgp();
            case 2:
                return zzf;
            case 3:
                return null;
            case 4:
                return new zza();
            case 5:
                zzh zzh = (zzh) obj;
                zzfgp zzfgp = (zzfgp) obj2;
                this.zzd = zzh.zza(this.zzd.isEmpty() ^ true, this.zzd, zzfgp.zzd.isEmpty() ^ true, zzfgp.zzd);
                boolean z2 = this.zze != zzfgs.zza;
                zzfgs zzfgs = this.zze;
                if (zzfgp.zze == zzfgs.zza) {
                    z = false;
                }
                this.zze = zzh.zza(z2, zzfgs, z, zzfgp.zze);
                return this;
            case 6:
                zzfhb zzfhb = (zzfhb) obj;
                if (((zzfhm) obj2) != null) {
                    Object obj3 = null;
                    while (obj3 == null) {
                        try {
                            int zza = zzfhb.zza();
                            if (zza != 0) {
                                if (zza == 10) {
                                    this.zzd = zzfhb.zzk();
                                } else if (zza != 18) {
                                    boolean z3;
                                    if ((zza & 7) == 4) {
                                        z3 = false;
                                    } else {
                                        if (this.zzb == zzfko.zza()) {
                                            this.zzb = zzfko.zzb();
                                        }
                                        z3 = this.zzb.zza(zza, zzfhb);
                                    }
                                    if (z3) {
                                    }
                                } else {
                                    this.zze = zzfhb.zzl();
                                }
                            }
                            obj3 = 1;
                        } catch (zzfie e) {
                            throw new RuntimeException(e.zza(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new zzfie(e2.getMessage()).zza(this));
                        }
                    }
                    break;
                }
                throw new NullPointerException();
            case 7:
                break;
            case 8:
                if (zzg == null) {
                    synchronized (zzfgp.class) {
                        if (zzg == null) {
                            zzg = new zzb(zzf);
                        }
                    }
                }
                return zzg;
            case 9:
                return Byte.valueOf((byte) 1);
            case 10:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
        return zzf;
    }

    public final void zza(zzfhg zzfhg) throws IOException {
        if (!this.zzd.isEmpty()) {
            zzfhg.zza(1, this.zzd);
        }
        if (!this.zze.zzb()) {
            zzfhg.zza(2, this.zze);
        }
        this.zzb.zza(zzfhg);
    }
}
