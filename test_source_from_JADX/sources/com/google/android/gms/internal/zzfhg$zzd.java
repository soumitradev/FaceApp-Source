package com.google.android.gms.internal;

import java.io.IOException;
import java.io.OutputStream;

final class zzfhg$zzd extends zzfhg$zza {
    private final OutputStream zzf;

    zzfhg$zzd(OutputStream outputStream, int i) {
        super(i);
        if (outputStream == null) {
            throw new NullPointerException("out");
        }
        this.zzf = outputStream;
    }

    private final void zze() throws IOException {
        this.zzf.write(this.zzb, 0, this.zzd);
        this.zzd = 0;
    }

    private final void zzq(int i) throws IOException {
        if (this.zzc - this.zzd < i) {
            zze();
        }
    }

    public final void zza() throws IOException {
        if (this.zzd > 0) {
            zze();
        }
    }

    public final void zza(byte b) throws IOException {
        if (this.zzd == this.zzc) {
            zze();
        }
        zzb(b);
    }

    public final void zza(int i, int i2) throws IOException {
        zzc((i << 3) | i2);
    }

    public final void zza(int i, long j) throws IOException {
        zzq(20);
        zzi(i, 0);
        zzi(j);
    }

    public final void zza(int i, zzfgs zzfgs) throws IOException {
        zza(i, 2);
        zza(zzfgs);
    }

    public final void zza(int i, zzfjc zzfjc) throws IOException {
        zza(i, 2);
        zza(zzfjc);
    }

    public final void zza(int i, String str) throws IOException {
        zza(i, 2);
        zza(str);
    }

    public final void zza(int i, boolean z) throws IOException {
        zzq(11);
        zzi(i, 0);
        zzb((byte) z);
    }

    public final void zza(long j) throws IOException {
        zzq(10);
        zzi(j);
    }

    public final void zza(zzfgs zzfgs) throws IOException {
        zzc(zzfgs.zza());
        zzfgs.zza((zzfgr) this);
    }

    public final void zza(zzfjc zzfjc) throws IOException {
        zzc(zzfjc.zza());
        zzfjc.zza((zzfhg) this);
    }

    public final void zza(String str) throws IOException {
        int i;
        try {
            int length = str.length() * 3;
            int zzh = zzh(length);
            i = zzh + length;
            if (i > this.zzc) {
                byte[] bArr = new byte[length];
                length = zzfks.zza(str, bArr, 0, length);
                zzc(length);
                zza(bArr, 0, length);
                return;
            }
            int i2;
            if (i > this.zzc - this.zzd) {
                zze();
            }
            length = zzh(str.length());
            i = this.zzd;
            if (length == zzh) {
                this.zzd = i + length;
                zzh = zzfks.zza(str, this.zzb, this.zzd, this.zzc - this.zzd);
                this.zzd = i;
                i2 = (zzh - i) - length;
                zzo(i2);
                this.zzd = zzh;
            } else {
                i2 = zzfks.zza((CharSequence) str);
                zzo(i2);
                this.zzd = zzfks.zza(str, this.zzb, this.zzd, i2);
            }
            this.zze += i2;
        } catch (zzfkv e) {
            this.zze -= this.zzd - i;
            this.zzd = i;
            throw e;
        } catch (Throwable e2) {
            throw new zzfhg$zzc(e2);
        } catch (zzfkv e3) {
            zza(str, e3);
        }
    }

    public final void zza(byte[] bArr, int i, int i2) throws IOException {
        zzc(bArr, i, i2);
    }

    public final void zzb(int i) throws IOException {
        if (i >= 0) {
            zzc(i);
        } else {
            zza((long) i);
        }
    }

    public final void zzb(int i, int i2) throws IOException {
        zzq(20);
        zzi(i, 0);
        if (i2 >= 0) {
            zzo(i2);
        } else {
            zzi((long) i2);
        }
    }

    public final void zzb(int i, long j) throws IOException {
        zzq(18);
        zzi(i, 1);
        zzj(j);
    }

    public final void zzb(int i, zzfgs zzfgs) throws IOException {
        zza(1, 3);
        zzc(2, i);
        zza(3, zzfgs);
        zza(1, 4);
    }

    public final void zzb(int i, zzfjc zzfjc) throws IOException {
        zza(1, 3);
        zzc(2, i);
        zza(3, zzfjc);
        zza(1, 4);
    }

    public final void zzc(int i) throws IOException {
        zzq(10);
        zzo(i);
    }

    public final void zzc(int i, int i2) throws IOException {
        zzq(20);
        zzi(i, 0);
        zzo(i2);
    }

    public final void zzc(long j) throws IOException {
        zzq(8);
        zzj(j);
    }

    public final void zzc(byte[] bArr, int i, int i2) throws IOException {
        if (this.zzc - this.zzd >= i2) {
            System.arraycopy(bArr, i, this.zzb, this.zzd, i2);
            this.zzd += i2;
        } else {
            int i3 = this.zzc - this.zzd;
            System.arraycopy(bArr, i, this.zzb, this.zzd, i3);
            i += i3;
            i2 -= i3;
            this.zzd = this.zzc;
            this.zze += i3;
            zze();
            if (i2 <= this.zzc) {
                System.arraycopy(bArr, i, this.zzb, 0, i2);
                this.zzd = i2;
            } else {
                this.zzf.write(bArr, i, i2);
            }
        }
        this.zze += i2;
    }

    public final void zzd(int i, int i2) throws IOException {
        zzq(14);
        zzi(i, 5);
        zzp(i2);
    }

    public final void zzd(byte[] bArr, int i, int i2) throws IOException {
        zzc(i2);
        zzc(bArr, 0, i2);
    }

    public final void zze(int i) throws IOException {
        zzq(4);
        zzp(i);
    }
}
