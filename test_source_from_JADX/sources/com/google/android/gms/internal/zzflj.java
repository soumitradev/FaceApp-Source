package com.google.android.gms.internal;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.IOException;

public final class zzflj {
    private final byte[] zza;
    private final int zzb;
    private final int zzc;
    private int zzd;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh = Integer.MAX_VALUE;
    private int zzi;
    private int zzj = 64;
    private int zzk = 67108864;

    private zzflj(byte[] bArr, int i, int i2) {
        this.zza = bArr;
        this.zzb = i;
        i2 += i;
        this.zzd = i2;
        this.zzc = i2;
        this.zzf = i;
    }

    public static zzflj zza(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    public static zzflj zza(byte[] bArr, int i, int i2) {
        return new zzflj(bArr, 0, i2);
    }

    private final void zzf(int i) throws IOException {
        if (i < 0) {
            throw zzflr.zzb();
        } else if (this.zzf + i > this.zzh) {
            zzf(this.zzh - this.zzf);
            throw zzflr.zza();
        } else if (i <= this.zzd - this.zzf) {
            this.zzf += i;
        } else {
            throw zzflr.zza();
        }
    }

    private final void zzn() {
        this.zzd += this.zze;
        int i = this.zzd;
        if (i > this.zzh) {
            this.zze = i - this.zzh;
            this.zzd -= this.zze;
            return;
        }
        this.zze = 0;
    }

    private final byte zzo() throws IOException {
        if (this.zzf == this.zzd) {
            throw zzflr.zza();
        }
        byte[] bArr = this.zza;
        int i = this.zzf;
        this.zzf = i + 1;
        return bArr[i];
    }

    public final int zza() throws IOException {
        if (this.zzf == this.zzd) {
            this.zzg = 0;
            return 0;
        }
        this.zzg = zzh();
        if (this.zzg != 0) {
            return this.zzg;
        }
        throw new zzflr("Protocol message contained an invalid tag (zero).");
    }

    public final void zza(int i) throws zzflr {
        if (this.zzg != i) {
            throw new zzflr("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final void zza(zzfls zzfls) throws IOException {
        int zzh = zzh();
        if (this.zzi >= this.zzj) {
            throw zzflr.zzd();
        }
        zzh = zzc(zzh);
        this.zzi++;
        zzfls.zza(this);
        zza(0);
        this.zzi--;
        zzd(zzh);
    }

    public final void zza(zzfls zzfls, int i) throws IOException {
        if (this.zzi >= this.zzj) {
            throw zzflr.zzd();
        }
        this.zzi++;
        zzfls.zza(this);
        zza((i << 3) | 4);
        this.zzi--;
    }

    public final byte[] zza(int i, int i2) {
        if (i2 == 0) {
            return zzflv.zzh;
        }
        Object obj = new byte[i2];
        System.arraycopy(this.zza, this.zzb + i, obj, 0, i2);
        return obj;
    }

    public final long zzb() throws IOException {
        return zzi();
    }

    final void zzb(int i, int i2) {
        if (i > this.zzf - this.zzb) {
            int i3 = this.zzf - this.zzb;
            StringBuilder stringBuilder = new StringBuilder(50);
            stringBuilder.append("Position ");
            stringBuilder.append(i);
            stringBuilder.append(" is beyond current ");
            stringBuilder.append(i3);
            throw new IllegalArgumentException(stringBuilder.toString());
        } else if (i < 0) {
            StringBuilder stringBuilder2 = new StringBuilder(24);
            stringBuilder2.append("Bad position ");
            stringBuilder2.append(i);
            throw new IllegalArgumentException(stringBuilder2.toString());
        } else {
            this.zzf = this.zzb + i;
            this.zzg = i2;
        }
    }

    public final boolean zzb(int i) throws IOException {
        switch (i & 7) {
            case 0:
                zzh();
                return true;
            case 1:
                zzk();
                return true;
            case 2:
                zzf(zzh());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzj();
                return true;
            default:
                throw new zzflr("Protocol message tag had invalid wire type.");
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

    public final int zzc() throws IOException {
        return zzh();
    }

    public final int zzc(int i) throws zzflr {
        if (i < 0) {
            throw zzflr.zzb();
        }
        i += this.zzf;
        int i2 = this.zzh;
        if (i > i2) {
            throw zzflr.zza();
        }
        this.zzh = i;
        zzn();
        return i2;
    }

    public final void zzd(int i) {
        this.zzh = i;
        zzn();
    }

    public final boolean zzd() throws IOException {
        return zzh() != 0;
    }

    public final String zze() throws IOException {
        int zzh = zzh();
        if (zzh < 0) {
            throw zzflr.zzb();
        } else if (zzh > this.zzd - this.zzf) {
            throw zzflr.zza();
        } else {
            String str = new String(this.zza, this.zzf, zzh, zzflq.zza);
            this.zzf += zzh;
            return str;
        }
    }

    public final void zze(int i) {
        zzb(i, this.zzg);
    }

    public final byte[] zzf() throws IOException {
        int zzh = zzh();
        if (zzh < 0) {
            throw zzflr.zzb();
        } else if (zzh == 0) {
            return zzflv.zzh;
        } else {
            if (zzh > this.zzd - this.zzf) {
                throw zzflr.zza();
            }
            Object obj = new byte[zzh];
            System.arraycopy(this.zza, this.zzf, obj, 0, zzh);
            this.zzf += zzh;
            return obj;
        }
    }

    public final long zzg() throws IOException {
        long zzi = zzi();
        return (zzi >>> 1) ^ (-(zzi & 1));
    }

    public final int zzh() throws IOException {
        byte zzo = zzo();
        if (zzo >= (byte) 0) {
            return zzo;
        }
        int i;
        int i2 = zzo & MetaEvent.SEQUENCER_SPECIFIC;
        byte zzo2 = zzo();
        if (zzo2 >= (byte) 0) {
            i = zzo2 << 7;
        } else {
            i2 |= (zzo2 & MetaEvent.SEQUENCER_SPECIFIC) << 7;
            zzo2 = zzo();
            if (zzo2 >= (byte) 0) {
                i = zzo2 << 14;
            } else {
                i2 |= (zzo2 & MetaEvent.SEQUENCER_SPECIFIC) << 14;
                zzo2 = zzo();
                if (zzo2 >= (byte) 0) {
                    i = zzo2 << 21;
                } else {
                    i2 |= (zzo2 & MetaEvent.SEQUENCER_SPECIFIC) << 21;
                    zzo2 = zzo();
                    i2 |= zzo2 << 28;
                    if (zzo2 >= (byte) 0) {
                        return i2;
                    }
                    for (i = 0; i < 5; i++) {
                        if (zzo() >= (byte) 0) {
                            return i2;
                        }
                    }
                    throw zzflr.zzc();
                }
            }
        }
        return i2 | i;
    }

    public final long zzi() throws IOException {
        int i = 0;
        long j = 0;
        while (i < 64) {
            byte zzo = zzo();
            long j2 = j | (((long) (zzo & MetaEvent.SEQUENCER_SPECIFIC)) << i);
            if ((zzo & 128) == 0) {
                return j2;
            }
            i += 7;
            j = j2;
        }
        throw zzflr.zzc();
    }

    public final int zzj() throws IOException {
        return (((zzo() & 255) | ((zzo() & 255) << 8)) | ((zzo() & 255) << 16)) | ((zzo() & 255) << 24);
    }

    public final long zzk() throws IOException {
        return (((((((((long) zzo()) & 255) | ((((long) zzo()) & 255) << 8)) | ((((long) zzo()) & 255) << 16)) | ((((long) zzo()) & 255) << 24)) | ((((long) zzo()) & 255) << 32)) | ((((long) zzo()) & 255) << 40)) | ((((long) zzo()) & 255) << 48)) | ((((long) zzo()) & 255) << 56);
    }

    public final int zzl() {
        if (this.zzh == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzh - this.zzf;
    }

    public final int zzm() {
        return this.zzf - this.zzb;
    }
}
