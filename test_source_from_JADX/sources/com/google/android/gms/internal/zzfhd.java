package com.google.android.gms.internal;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.IOException;
import java.util.Arrays;

final class zzfhd extends zzfhb {
    private final byte[] zzd;
    private final boolean zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private int zzj;
    private int zzk;

    private zzfhd(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzk = Integer.MAX_VALUE;
        this.zzd = bArr;
        this.zzf = i2 + i;
        this.zzh = i;
        this.zzi = this.zzh;
        this.zze = z;
    }

    private final void zzaa() {
        this.zzf += this.zzg;
        int i = this.zzf - this.zzi;
        if (i > this.zzk) {
            this.zzg = i - this.zzk;
            this.zzf -= this.zzg;
            return;
        }
        this.zzg = 0;
    }

    private final byte zzab() throws IOException {
        if (this.zzh == this.zzf) {
            throw zzfie.zza();
        }
        byte[] bArr = this.zzd;
        int i = this.zzh;
        this.zzh = i + 1;
        return bArr[i];
    }

    private final long zzx() throws IOException {
        int i = this.zzh;
        if (this.zzf != i) {
            byte[] bArr = this.zzd;
            int i2 = i + 1;
            byte b = bArr[i];
            if (b >= (byte) 0) {
                this.zzh = i2;
                return (long) b;
            } else if (this.zzf - i2 >= 9) {
                long j;
                long j2;
                int i3 = i2 + 1;
                i = b ^ (bArr[i2] << 7);
                if (i < 0) {
                    i ^= -128;
                } else {
                    i2 = i3 + 1;
                    i ^= bArr[i3] << 14;
                    if (i >= 0) {
                        j = (long) (i ^ 16256);
                        i = i2;
                        j2 = j;
                        this.zzh = i;
                        return j2;
                    }
                    i3 = i2 + 1;
                    i ^= bArr[i2] << 21;
                    if (i < 0) {
                        i ^= -2080896;
                    } else {
                        long j3;
                        long j4 = (long) i;
                        i = i3 + 1;
                        long j5 = j4 ^ (((long) bArr[i3]) << 28);
                        if (j5 >= 0) {
                            j3 = 266354560;
                        } else {
                            long j6;
                            int i4 = i + 1;
                            long j7 = j5 ^ (((long) bArr[i]) << 35);
                            if (j7 < 0) {
                                j6 = -34093383808L;
                            } else {
                                i = i4 + 1;
                                j5 = j7 ^ (((long) bArr[i4]) << 42);
                                if (j5 >= 0) {
                                    j3 = 4363953127296L;
                                } else {
                                    i4 = i + 1;
                                    j7 = j5 ^ (((long) bArr[i]) << 49);
                                    if (j7 < 0) {
                                        j6 = -558586000294016L;
                                    } else {
                                        i = i4 + 1;
                                        long j8 = (j7 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                        if (j8 < 0) {
                                            i4 = i + 1;
                                            if (((long) bArr[i]) >= 0) {
                                                i = i4;
                                            }
                                        }
                                        j2 = j8;
                                        this.zzh = i;
                                        return j2;
                                    }
                                }
                            }
                            j2 = j7 ^ j6;
                            i = i4;
                            this.zzh = i;
                            return j2;
                        }
                        j2 = j5 ^ j3;
                        this.zzh = i;
                        return j2;
                    }
                }
                j = (long) i;
                i = i3;
                j2 = j;
                this.zzh = i;
                return j2;
            }
        }
        return zzt();
    }

    private final int zzy() throws IOException {
        int i = this.zzh;
        if (this.zzf - i < 4) {
            throw zzfie.zza();
        }
        byte[] bArr = this.zzd;
        this.zzh = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16));
    }

    private final long zzz() throws IOException {
        int i = this.zzh;
        if (this.zzf - i < 8) {
            throw zzfie.zza();
        }
        byte[] bArr = this.zzd;
        this.zzh = i + 8;
        return (((((((((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8)) | ((((long) bArr[i + 2]) & 255) << 16)) | ((((long) bArr[i + 3]) & 255) << 24)) | ((((long) bArr[i + 4]) & 255) << 32)) | ((((long) bArr[i + 5]) & 255) << 40)) | ((((long) bArr[i + 6]) & 255) << 48)) | ((((long) bArr[i + 7]) & 255) << 56);
    }

    public final int zza() throws IOException {
        if (zzv()) {
            this.zzj = 0;
            return 0;
        }
        this.zzj = zzs();
        if ((this.zzj >>> 3) != 0) {
            return this.zzj;
        }
        throw zzfie.zzd();
    }

    public final <T extends zzfhu<T, ?>> T zza(T t, zzfhm zzfhm) throws IOException {
        int zzs = zzs();
        if (this.zza >= this.zzb) {
            throw zzfie.zzg();
        }
        zzs = zzd(zzs);
        this.zza++;
        t = zzfhu.zza((zzfhu) t, (zzfhb) this, zzfhm);
        zza(0);
        this.zza--;
        zze(zzs);
        return t;
    }

    public final void zza(int i) throws zzfie {
        if (this.zzj != i) {
            throw zzfie.zze();
        }
    }

    public final void zza(zzfjd zzfjd, zzfhm zzfhm) throws IOException {
        int zzs = zzs();
        if (this.zza >= this.zzb) {
            throw zzfie.zzg();
        }
        zzs = zzd(zzs);
        this.zza++;
        zzfjd.zzb(this, zzfhm);
        zza(0);
        this.zza--;
        zze(zzs);
    }

    public final double zzb() throws IOException {
        return Double.longBitsToDouble(zzz());
    }

    public final boolean zzb(int i) throws IOException {
        int i2 = 0;
        switch (i & 7) {
            case 0:
                if (this.zzf - this.zzh >= 10) {
                    while (i2 < 10) {
                        byte[] bArr = this.zzd;
                        int i3 = this.zzh;
                        this.zzh = i3 + 1;
                        if (bArr[i3] < (byte) 0) {
                            i2++;
                        }
                    }
                    throw zzfie.zzc();
                }
                while (i2 < 10) {
                    if (zzab() < (byte) 0) {
                        i2++;
                    }
                }
                throw zzfie.zzc();
                return true;
            case 1:
                zzf(8);
                return true;
            case 2:
                zzf(zzs());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzf(4);
                return true;
            default:
                throw zzfie.zzf();
        }
        int zza;
        do {
            zza = zza();
            if (zza != 0) {
            }
            zza(((i >>> 3) << 3) | 4);
            return true;
        } while (zzb(zza));
        zza(((i >>> 3) << 3) | 4);
        return true;
    }

    public final float zzc() throws IOException {
        return Float.intBitsToFloat(zzy());
    }

    public final int zzd(int i) throws zzfie {
        if (i < 0) {
            throw zzfie.zzb();
        }
        i += zzw();
        int i2 = this.zzk;
        if (i > i2) {
            throw zzfie.zza();
        }
        this.zzk = i;
        zzaa();
        return i2;
    }

    public final long zzd() throws IOException {
        return zzx();
    }

    public final long zze() throws IOException {
        return zzx();
    }

    public final void zze(int i) {
        this.zzk = i;
        zzaa();
    }

    public final int zzf() throws IOException {
        return zzs();
    }

    public final void zzf(int i) throws IOException {
        if (i >= 0 && i <= this.zzf - this.zzh) {
            this.zzh += i;
        } else if (i < 0) {
            throw zzfie.zzb();
        } else {
            throw zzfie.zza();
        }
    }

    public final long zzg() throws IOException {
        return zzz();
    }

    public final int zzh() throws IOException {
        return zzy();
    }

    public final boolean zzi() throws IOException {
        return zzx() != 0;
    }

    public final String zzj() throws IOException {
        int zzs = zzs();
        if (zzs > 0 && zzs <= this.zzf - this.zzh) {
            String str = new String(this.zzd, this.zzh, zzs, zzfhz.zza);
            this.zzh += zzs;
            return str;
        } else if (zzs == 0) {
            return "";
        } else {
            if (zzs < 0) {
                throw zzfie.zzb();
            }
            throw zzfie.zza();
        }
    }

    public final String zzk() throws IOException {
        int zzs = zzs();
        if (zzs <= 0 || zzs > this.zzf - this.zzh) {
            if (zzs == 0) {
                return "";
            }
            if (zzs <= 0) {
                throw zzfie.zzb();
            }
            throw zzfie.zza();
        } else if (zzfks.zza(this.zzd, this.zzh, this.zzh + zzs)) {
            int i = this.zzh;
            this.zzh += zzs;
            return new String(this.zzd, i, zzs, zzfhz.zza);
        } else {
            throw zzfie.zzi();
        }
    }

    public final zzfgs zzl() throws IOException {
        int zzs = zzs();
        if (zzs > 0 && zzs <= this.zzf - this.zzh) {
            zzfgs zza = zzfgs.zza(this.zzd, this.zzh, zzs);
            this.zzh += zzs;
            return zza;
        } else if (zzs == 0) {
            return zzfgs.zza;
        } else {
            byte[] copyOfRange;
            if (zzs > 0 && zzs <= this.zzf - this.zzh) {
                int i = this.zzh;
                this.zzh += zzs;
                copyOfRange = Arrays.copyOfRange(this.zzd, i, this.zzh);
            } else if (zzs > 0) {
                throw zzfie.zza();
            } else if (zzs == 0) {
                copyOfRange = zzfhz.zzb;
            } else {
                throw zzfie.zzb();
            }
            return zzfgs.zzb(copyOfRange);
        }
    }

    public final int zzm() throws IOException {
        return zzs();
    }

    public final int zzn() throws IOException {
        return zzs();
    }

    public final int zzo() throws IOException {
        return zzy();
    }

    public final long zzp() throws IOException {
        return zzz();
    }

    public final int zzq() throws IOException {
        return zzfhb.zzg(zzs());
    }

    public final long zzr() throws IOException {
        return zzfhb.zza(zzx());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zzs() throws java.io.IOException {
        /*
        r5 = this;
        r0 = r5.zzh;
        r1 = r5.zzf;
        if (r1 == r0) goto L_0x006d;
    L_0x0006:
        r1 = r5.zzd;
        r2 = r0 + 1;
        r0 = r1[r0];
        if (r0 < 0) goto L_0x0011;
    L_0x000e:
        r5.zzh = r2;
        return r0;
    L_0x0011:
        r3 = r5.zzf;
        r3 = r3 - r2;
        r4 = 9;
        if (r3 < r4) goto L_0x006d;
    L_0x0018:
        r3 = r2 + 1;
        r2 = r1[r2];
        r2 = r2 << 7;
        r0 = r0 ^ r2;
        if (r0 >= 0) goto L_0x0024;
    L_0x0021:
        r0 = r0 ^ -128;
        goto L_0x006a;
    L_0x0024:
        r2 = r3 + 1;
        r3 = r1[r3];
        r3 = r3 << 14;
        r0 = r0 ^ r3;
        if (r0 < 0) goto L_0x0031;
    L_0x002d:
        r0 = r0 ^ 16256;
    L_0x002f:
        r3 = r2;
        goto L_0x006a;
    L_0x0031:
        r3 = r2 + 1;
        r2 = r1[r2];
        r2 = r2 << 21;
        r0 = r0 ^ r2;
        if (r0 >= 0) goto L_0x003f;
    L_0x003a:
        r1 = -2080896; // 0xffffffffffe03f80 float:NaN double:NaN;
        r0 = r0 ^ r1;
        goto L_0x006a;
    L_0x003f:
        r2 = r3 + 1;
        r3 = r1[r3];
        r4 = r3 << 28;
        r0 = r0 ^ r4;
        r4 = 266354560; // 0xfe03f80 float:2.2112565E-29 double:1.315966377E-315;
        r0 = r0 ^ r4;
        if (r3 >= 0) goto L_0x002f;
    L_0x004c:
        r3 = r2 + 1;
        r2 = r1[r2];
        if (r2 >= 0) goto L_0x006a;
    L_0x0052:
        r2 = r3 + 1;
        r3 = r1[r3];
        if (r3 >= 0) goto L_0x002f;
    L_0x0058:
        r3 = r2 + 1;
        r2 = r1[r2];
        if (r2 >= 0) goto L_0x006a;
    L_0x005e:
        r2 = r3 + 1;
        r3 = r1[r3];
        if (r3 >= 0) goto L_0x002f;
    L_0x0064:
        r3 = r2 + 1;
        r1 = r1[r2];
        if (r1 < 0) goto L_0x006d;
    L_0x006a:
        r5.zzh = r3;
        return r0;
    L_0x006d:
        r0 = r5.zzt();
        r0 = (int) r0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfhd.zzs():int");
    }

    final long zzt() throws IOException {
        long j = 0;
        int i = 0;
        while (i < 64) {
            byte zzab = zzab();
            long j2 = j | (((long) (zzab & MetaEvent.SEQUENCER_SPECIFIC)) << i);
            if ((zzab & 128) == 0) {
                return j2;
            }
            i += 7;
            j = j2;
        }
        throw zzfie.zzc();
    }

    public final int zzu() {
        return this.zzk == Integer.MAX_VALUE ? -1 : this.zzk - zzw();
    }

    public final boolean zzv() throws IOException {
        return this.zzh == this.zzf;
    }

    public final int zzw() {
        return this.zzh - this.zzi;
    }
}
