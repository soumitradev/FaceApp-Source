package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhu.zzb;
import com.google.android.gms.internal.zzfhu.zzf;
import com.google.android.gms.internal.zzfhu.zzg;
import com.google.android.gms.internal.zzfhu.zzh;
import java.io.IOException;

public final class zzfmg extends zzfhu<zzfmg, zza> implements zzfje {
    private static final zzfmg zzh;
    private static volatile zzfjl<zzfmg> zzi;
    private int zzd;
    private int zze;
    private String zzf = "";
    private zzfid<zzfgp> zzg = zzfhu.zzu();

    public static final class zza extends com.google.android.gms.internal.zzfhu.zza<zzfmg, zza> implements zzfje {
        private zza() {
            super(zzfmg.zzh);
        }
    }

    static {
        zzfhu zzfmg = new zzfmg();
        zzh = zzfmg;
        zzfmg.zza(zzg.zzf, null, null);
        zzfmg.zzb.zzc();
    }

    private zzfmg() {
    }

    public static zzfmg zzd() {
        return zzh;
    }

    public final int zza() {
        int i = this.zzc;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        i = this.zze != 0 ? zzfhg.zze(1, this.zze) + 0 : 0;
        if (!this.zzf.isEmpty()) {
            i += zzfhg.zzb(2, this.zzf);
        }
        while (i2 < this.zzg.size()) {
            i += zzfhg.zzc(3, (zzfjc) this.zzg.get(i2));
            i2++;
        }
        i += this.zzb.zze();
        this.zzc = i;
        return i;
    }

    protected final Object zza(int i, Object obj, Object obj2) {
        boolean z = false;
        int i2;
        switch (zzfmh.zza[i - 1]) {
            case 1:
                return new zzfmg();
            case 2:
                return zzh;
            case 3:
                this.zzg.zzb();
                return null;
            case 4:
                return new zza();
            case 5:
                zzh zzh = (zzh) obj;
                zzfmg zzfmg = (zzfmg) obj2;
                boolean z2 = this.zze != 0;
                i2 = this.zze;
                if (zzfmg.zze != 0) {
                    z = true;
                }
                this.zze = zzh.zza(z2, i2, z, zzfmg.zze);
                this.zzf = zzh.zza(this.zzf.isEmpty() ^ true, this.zzf, true ^ zzfmg.zzf.isEmpty(), zzfmg.zzf);
                this.zzg = zzh.zza(this.zzg, zzfmg.zzg);
                if (zzh == zzf.zza) {
                    this.zzd |= zzfmg.zzd;
                }
                return this;
            case 6:
                zzfhb zzfhb = (zzfhb) obj;
                zzfhm zzfhm = (zzfhm) obj2;
                if (zzfhm != null) {
                    while (!z) {
                        try {
                            i = zzfhb.zza();
                            if (i != 0) {
                                if (i == 8) {
                                    this.zze = zzfhb.zzf();
                                } else if (i == 18) {
                                    this.zzf = zzfhb.zzk();
                                } else if (i == 26) {
                                    if (!this.zzg.zza()) {
                                        zzfid zzfid = this.zzg;
                                        i2 = zzfid.size();
                                        this.zzg = zzfid.zzd(i2 == 0 ? 10 : i2 << 1);
                                    }
                                    this.zzg.add((zzfgp) zzfhb.zza(zzfgp.zzb(), zzfhm));
                                } else if (zza(i, zzfhb)) {
                                }
                            }
                            z = true;
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
                if (zzi == null) {
                    synchronized (zzfmg.class) {
                        if (zzi == null) {
                            zzi = new zzb(zzh);
                        }
                    }
                }
                return zzi;
            case 9:
                return Byte.valueOf((byte) 1);
            case 10:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
        return zzh;
    }

    public final void zza(zzfhg zzfhg) throws IOException {
        if (this.zze != 0) {
            zzfhg.zzb(1, this.zze);
        }
        if (!this.zzf.isEmpty()) {
            zzfhg.zza(2, this.zzf);
        }
        for (int i = 0; i < this.zzg.size(); i++) {
            zzfhg.zza(3, (zzfjc) this.zzg.get(i));
        }
        this.zzb.zza(zzfhg);
    }

    public final int zzb() {
        return this.zze;
    }

    public final String zzc() {
        return this.zzf;
    }
}
