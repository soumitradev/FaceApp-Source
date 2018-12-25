package com.google.android.gms.internal;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

final class zzfhe extends zzfhb {
    private final InputStream zzd;
    private final byte[] zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private int zzj;
    private int zzk;
    private zzfhf zzl;

    private zzfhe(InputStream inputStream, int i) {
        super();
        this.zzk = Integer.MAX_VALUE;
        this.zzl = null;
        zzfhz.zza(inputStream, "input");
        this.zzd = inputStream;
        this.zze = new byte[i];
        this.zzf = 0;
        this.zzh = 0;
        this.zzj = 0;
    }

    private final void zzaa() {
        this.zzf += this.zzg;
        int i = this.zzj + this.zzf;
        if (i > this.zzk) {
            this.zzg = i - this.zzk;
            this.zzf -= this.zzg;
            return;
        }
        this.zzg = 0;
    }

    private final byte zzab() throws IOException {
        if (this.zzh == this.zzf) {
            zzh(1);
        }
        byte[] bArr = this.zze;
        int i = this.zzh;
        this.zzh = i + 1;
        return bArr[i];
    }

    private final void zzh(int i) throws IOException {
        if (!zzi(i)) {
            if (i > (this.zzc - this.zzj) - this.zzh) {
                throw zzfie.zzh();
            }
            throw zzfie.zza();
        }
    }

    private final boolean zzi(int i) throws IOException {
        StringBuilder stringBuilder;
        while (this.zzh + i > this.zzf) {
            if (i > (this.zzc - this.zzj) - this.zzh || (this.zzj + this.zzh) + i > this.zzk) {
                return false;
            }
            int i2 = this.zzh;
            if (i2 > 0) {
                if (this.zzf > i2) {
                    System.arraycopy(this.zze, i2, this.zze, 0, this.zzf - i2);
                }
                this.zzj += i2;
                this.zzf -= i2;
                this.zzh = 0;
            }
            i2 = this.zzd.read(this.zze, this.zzf, Math.min(this.zze.length - this.zzf, (this.zzc - this.zzj) - this.zzf));
            if (i2 != 0 && i2 >= -1) {
                if (i2 <= this.zze.length) {
                    if (i2 <= 0) {
                        return false;
                    }
                    this.zzf += i2;
                    zzaa();
                    if (this.zzf >= i) {
                        return true;
                    }
                }
            }
            stringBuilder = new StringBuilder(102);
            stringBuilder.append("InputStream#read(byte[]) returned invalid result: ");
            stringBuilder.append(i2);
            stringBuilder.append("\nThe InputStream implementation is buggy.");
            throw new IllegalStateException(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder(77);
        stringBuilder.append("refillBuffer() called when ");
        stringBuilder.append(i);
        stringBuilder.append(" bytes were already available in buffer");
        throw new IllegalStateException(stringBuilder.toString());
    }

    private final byte[] zzj(int i) throws IOException {
        byte[] zzk = zzk(i);
        if (zzk != null) {
            return zzk;
        }
        int i2 = this.zzh;
        int i3 = this.zzf - this.zzh;
        this.zzj += this.zzf;
        this.zzh = 0;
        this.zzf = 0;
        List<byte[]> zzl = zzl(i - i3);
        Object obj = new byte[i];
        System.arraycopy(this.zze, i2, obj, 0, i3);
        for (byte[] bArr : zzl) {
            System.arraycopy(bArr, 0, obj, i3, bArr.length);
            i3 += bArr.length;
        }
        return obj;
    }

    private final byte[] zzk(int i) throws IOException {
        if (i == 0) {
            return zzfhz.zzb;
        }
        if (i < 0) {
            throw zzfie.zzb();
        }
        int i2 = (this.zzj + this.zzh) + i;
        if (i2 - this.zzc > 0) {
            throw zzfie.zzh();
        } else if (i2 > this.zzk) {
            zzf((this.zzk - this.zzj) - this.zzh);
            throw zzfie.zza();
        } else {
            i2 = this.zzf - this.zzh;
            int i3 = i - i2;
            if (i3 >= 4096) {
                if (i3 > this.zzd.available()) {
                    return null;
                }
            }
            Object obj = new byte[i];
            System.arraycopy(this.zze, this.zzh, obj, 0, i2);
            this.zzj += this.zzf;
            this.zzh = 0;
            this.zzf = 0;
            while (i2 < obj.length) {
                int read = this.zzd.read(obj, i2, i - i2);
                if (read == -1) {
                    throw zzfie.zza();
                }
                this.zzj += read;
                i2 += read;
            }
            return obj;
        }
    }

    private final List<byte[]> zzl(int i) throws IOException {
        List<byte[]> arrayList = new ArrayList();
        while (i > 0) {
            Object obj = new byte[Math.min(i, 4096)];
            int i2 = 0;
            while (i2 < obj.length) {
                int read = this.zzd.read(obj, i2, obj.length - i2);
                if (read == -1) {
                    throw zzfie.zza();
                }
                this.zzj += read;
                i2 += read;
            }
            i -= obj.length;
            arrayList.add(obj);
        }
        return arrayList;
    }

    private final long zzx() throws IOException {
        int i = this.zzh;
        if (this.zzf != i) {
            byte[] bArr = this.zze;
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
            zzh(4);
            i = this.zzh;
        }
        byte[] bArr = this.zze;
        this.zzh = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16));
    }

    private final long zzz() throws IOException {
        int i = this.zzh;
        if (this.zzf - i < 8) {
            zzh(8);
            i = this.zzh;
        }
        byte[] bArr = this.zze;
        this.zzh = i + 8;
        return (((((((((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8)) | ((((long) bArr[i + 2]) & 255) << 16)) | ((((long) bArr[i + 3]) & 255) << 24)) | ((((long) bArr[i + 4]) & 255) << 32)) | ((((long) bArr[i + 5]) & 255) << 40)) | ((((long) bArr[i + 6]) & 255) << 48)) | ((((long) bArr[i + 7]) & 255) << 56);
    }

    public final int zza() throws IOException {
        if (zzv()) {
            this.zzi = 0;
            return 0;
        }
        this.zzi = zzs();
        if ((this.zzi >>> 3) != 0) {
            return this.zzi;
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
        if (this.zzi != i) {
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
                        byte[] bArr = this.zze;
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
        i += this.zzj + this.zzh;
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
        if (i <= this.zzf - this.zzh && i >= 0) {
            this.zzh += i;
        } else if (i < 0) {
            throw zzfie.zzb();
        } else if ((this.zzj + this.zzh) + i > this.zzk) {
            zzf((this.zzk - this.zzj) - this.zzh);
            throw zzfie.zza();
        } else {
            int i2 = this.zzf - this.zzh;
            this.zzh = this.zzf;
            while (true) {
                zzh(1);
                int i3 = i - i2;
                if (i3 > this.zzf) {
                    i2 += this.zzf;
                    this.zzh = this.zzf;
                } else {
                    this.zzh = i3;
                    return;
                }
            }
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
        String str;
        if (zzs > 0 && zzs <= this.zzf - this.zzh) {
            str = new String(this.zze, this.zzh, zzs, zzfhz.zza);
            this.zzh += zzs;
            return str;
        } else if (zzs == 0) {
            return "";
        } else {
            if (zzs > this.zzf) {
                return new String(zzj(zzs), zzfhz.zza);
            }
            zzh(zzs);
            str = new String(this.zze, this.zzh, zzs, zzfhz.zza);
            this.zzh += zzs;
            return str;
        }
    }

    public final String zzk() throws IOException {
        byte[] bArr;
        int zzs = zzs();
        int i = this.zzh;
        int i2 = 0;
        if (zzs <= this.zzf - i && zzs > 0) {
            bArr = this.zze;
            this.zzh = i + zzs;
            i2 = i;
        } else if (zzs == 0) {
            return "";
        } else {
            if (zzs <= this.zzf) {
                zzh(zzs);
                bArr = this.zze;
                this.zzh = zzs;
            } else {
                bArr = zzj(zzs);
            }
        }
        if (zzfks.zza(bArr, i2, i2 + zzs)) {
            return new String(bArr, i2, zzs, zzfhz.zza);
        }
        throw zzfie.zzi();
    }

    public final zzfgs zzl() throws IOException {
        int zzs = zzs();
        if (zzs <= this.zzf - this.zzh && zzs > 0) {
            zzfgs zza = zzfgs.zza(this.zze, this.zzh, zzs);
            this.zzh += zzs;
            return zza;
        } else if (zzs == 0) {
            return zzfgs.zza;
        } else {
            byte[] zzk = zzk(zzs);
            if (zzk != null) {
                return zzfgs.zzb(zzk);
            }
            int i = this.zzh;
            int i2 = this.zzf - this.zzh;
            this.zzj += this.zzf;
            this.zzh = 0;
            this.zzf = 0;
            List<byte[]> zzl = zzl(zzs - i2);
            Iterable arrayList = new ArrayList(zzl.size() + 1);
            arrayList.add(zzfgs.zza(this.zze, i, i2));
            for (byte[] zzk2 : zzl) {
                arrayList.add(zzfgs.zzb(zzk2));
            }
            return zzfgs.zza(arrayList);
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
        r1 = r5.zze;
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfhe.zzs():int");
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
        if (this.zzk == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzk - (this.zzj + this.zzh);
    }

    public final boolean zzv() throws IOException {
        return this.zzh == this.zzf && !zzi(1);
    }

    public final int zzw() {
        return this.zzj + this.zzh;
    }
}
