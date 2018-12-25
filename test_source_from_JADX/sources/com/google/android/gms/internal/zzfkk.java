package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhu.zzb;
import com.google.android.gms.internal.zzfhu.zzg;
import com.google.android.gms.internal.zzfhu.zzh;
import java.io.IOException;

public final class zzfkk extends zzfhu<zzfkk, zza> implements zzfje {
    private static final zzfkk zzf;
    private static volatile zzfjl<zzfkk> zzg;
    private long zzd;
    private int zze;

    public static final class zza extends com.google.android.gms.internal.zzfhu.zza<zzfkk, zza> implements zzfje {
        private zza() {
            super(zzfkk.zzf);
        }

        public final zza zza(int i) {
            zzb();
            ((zzfkk) this.zza).zza(i);
            return this;
        }

        public final zza zza(long j) {
            zzb();
            ((zzfkk) this.zza).zza(j);
            return this;
        }
    }

    static {
        zzfhu zzfkk = new zzfkk();
        zzf = zzfkk;
        zzfkk.zza(zzg.zzf, null, null);
        zzfkk.zzb.zzc();
    }

    private zzfkk() {
    }

    private final void zza(int i) {
        this.zze = i;
    }

    private final void zza(long j) {
        this.zzd = j;
    }

    public static zza zzd() {
        return (zza) ((com.google.android.gms.internal.zzfhu.zza) zzf.zza(zzg.zzh, null, null));
    }

    public static zzfkk zze() {
        return zzf;
    }

    public final int zza() {
        int i = this.zzc;
        if (i != -1) {
            return i;
        }
        i = 0;
        if (this.zzd != 0) {
            i = 0 + zzfhg.zzc(1, this.zzd);
        }
        if (this.zze != 0) {
            i += zzfhg.zze(2, this.zze);
        }
        i += this.zzb.zze();
        this.zzc = i;
        return i;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        boolean z = true;
        switch (zzfkl.zza[i - 1]) {
            case 1:
                return new zzfkk();
            case 2:
                return zzf;
            case 3:
                return null;
            case 4:
                return new zza();
            case 5:
                zzh zzh = (zzh) obj;
                zzfkk zzfkk = (zzfkk) obj2;
                this.zzd = zzh.zza(this.zzd != 0, this.zzd, zzfkk.zzd != 0, zzfkk.zzd);
                boolean z2 = this.zze != 0;
                int i2 = this.zze;
                if (zzfkk.zze == 0) {
                    z = false;
                }
                this.zze = zzh.zza(z2, i2, z, zzfkk.zze);
                return this;
            case 6:
                zzfhb zzfhb = (zzfhb) obj;
                if (((zzfhm) obj2) != null) {
                    Object obj3 = null;
                    while (obj3 == null) {
                        try {
                            int zza = zzfhb.zza();
                            if (zza != 0) {
                                if (zza == 8) {
                                    this.zzd = zzfhb.zze();
                                } else if (zza != 16) {
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
                                    this.zze = zzfhb.zzf();
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
                    synchronized (zzfkk.class) {
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
        if (this.zzd != 0) {
            zzfhg.zza(1, this.zzd);
        }
        if (this.zze != 0) {
            zzfhg.zzb(2, this.zze);
        }
        this.zzb.zza(zzfhg);
    }

    public final long zzb() {
        return this.zzd;
    }

    public final int zzc() {
        return this.zze;
    }
}
