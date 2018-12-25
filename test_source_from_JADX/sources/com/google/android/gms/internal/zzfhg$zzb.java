package com.google.android.gms.internal;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.IOException;

class zzfhg$zzb extends zzfhg {
    private final byte[] zzb;
    private final int zzc;
    private final int zzd;
    private int zze;

    zzfhg$zzb(byte[] bArr, int i, int i2) {
        super(null);
        if (bArr == null) {
            throw new NullPointerException("buffer");
        }
        int i3 = i + i2;
        if (((i | i2) | (bArr.length - i3)) < 0) {
            throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)}));
        }
        this.zzb = bArr;
        this.zzc = i;
        this.zze = i;
        this.zzd = i3;
    }

    public void zza() {
    }

    public final void zza(byte b) throws IOException {
        try {
            byte[] bArr = this.zzb;
            int i = this.zze;
            this.zze = i + 1;
            bArr[i] = b;
        } catch (Throwable e) {
            throw new zzfhg$zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(1)}), e);
        }
    }

    public final void zza(int i, int i2) throws IOException {
        zzc((i << 3) | i2);
    }

    public final void zza(int i, long j) throws IOException {
        zza(i, 0);
        zza(j);
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
        zza(i, 0);
        zza((byte) z);
    }

    public final void zza(long j) throws IOException {
        byte[] bArr;
        if (!zzfhg.zzd() || zzb() < 10) {
            while ((j & -128) != 0) {
                bArr = this.zzb;
                int i = this.zze;
                this.zze = i + 1;
                bArr[i] = (byte) ((((int) j) & MetaEvent.SEQUENCER_SPECIFIC) | 128);
                j >>>= 7;
            }
            try {
                bArr = this.zzb;
                int i2 = this.zze;
                this.zze = i2 + 1;
                bArr[i2] = (byte) ((int) j);
                return;
            } catch (Throwable e) {
                throw new zzfhg$zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(1)}), e);
            }
        }
        while ((j & -128) != 0) {
            bArr = this.zzb;
            i = this.zze;
            this.zze = i + 1;
            zzfkq.zza(bArr, (long) i, (byte) ((((int) j) & MetaEvent.SEQUENCER_SPECIFIC) | 128));
            j >>>= 7;
        }
        bArr = this.zzb;
        i2 = this.zze;
        this.zze = i2 + 1;
        zzfkq.zza(bArr, (long) i2, (byte) ((int) j));
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
        int i = this.zze;
        try {
            int zzh = zzh(str.length() * 3);
            int zzh2 = zzh(str.length());
            if (zzh2 == zzh) {
                this.zze = i + zzh2;
                zzh = zzfks.zza(str, this.zzb, this.zze, zzb());
                this.zze = i;
                zzc((zzh - i) - zzh2);
                this.zze = zzh;
                return;
            }
            zzc(zzfks.zza((CharSequence) str));
            this.zze = zzfks.zza(str, this.zzb, this.zze, zzb());
        } catch (zzfkv e) {
            this.zze = i;
            zza(str, e);
        } catch (Throwable e2) {
            throw new zzfhg$zzc(e2);
        }
    }

    public final void zza(byte[] bArr, int i, int i2) throws IOException {
        zzc(bArr, i, i2);
    }

    public final int zzb() {
        return this.zzd - this.zze;
    }

    public final void zzb(int i) throws IOException {
        if (i >= 0) {
            zzc(i);
        } else {
            zza((long) i);
        }
    }

    public final void zzb(int i, int i2) throws IOException {
        zza(i, 0);
        zzb(i2);
    }

    public final void zzb(int i, long j) throws IOException {
        zza(i, 1);
        zzc(j);
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
        byte[] bArr;
        int i2;
        if (!zzfhg.zzd() || zzb() < 10) {
            while ((i & -128) != 0) {
                bArr = this.zzb;
                i2 = this.zze;
                this.zze = i2 + 1;
                bArr[i2] = (byte) ((i & MetaEvent.SEQUENCER_SPECIFIC) | 128);
                i >>>= 7;
            }
            try {
                bArr = this.zzb;
                i2 = this.zze;
                this.zze = i2 + 1;
                bArr[i2] = (byte) i;
                return;
            } catch (Throwable e) {
                throw new zzfhg$zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(1)}), e);
            }
        }
        while ((i & -128) != 0) {
            bArr = this.zzb;
            i2 = this.zze;
            this.zze = i2 + 1;
            zzfkq.zza(bArr, (long) i2, (byte) ((i & MetaEvent.SEQUENCER_SPECIFIC) | 128));
            i >>>= 7;
        }
        bArr = this.zzb;
        i2 = this.zze;
        this.zze = i2 + 1;
        zzfkq.zza(bArr, (long) i2, (byte) i);
    }

    public final void zzc(int i, int i2) throws IOException {
        zza(i, 0);
        zzc(i2);
    }

    public final void zzc(long j) throws IOException {
        try {
            byte[] bArr = this.zzb;
            int i = this.zze;
            this.zze = i + 1;
            bArr[i] = (byte) ((int) j);
            bArr = this.zzb;
            i = this.zze;
            this.zze = i + 1;
            bArr[i] = (byte) ((int) (j >> 8));
            bArr = this.zzb;
            i = this.zze;
            this.zze = i + 1;
            bArr[i] = (byte) ((int) (j >> 16));
            bArr = this.zzb;
            i = this.zze;
            this.zze = i + 1;
            bArr[i] = (byte) ((int) (j >> 24));
            bArr = this.zzb;
            i = this.zze;
            this.zze = i + 1;
            bArr[i] = (byte) ((int) (j >> 32));
            bArr = this.zzb;
            i = this.zze;
            this.zze = i + 1;
            bArr[i] = (byte) ((int) (j >> 40));
            bArr = this.zzb;
            i = this.zze;
            this.zze = i + 1;
            bArr[i] = (byte) ((int) (j >> 48));
            bArr = this.zzb;
            i = this.zze;
            this.zze = i + 1;
            bArr[i] = (byte) ((int) (j >> 56));
        } catch (Throwable e) {
            throw new zzfhg$zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(1)}), e);
        }
    }

    public final void zzc(byte[] bArr, int i, int i2) throws IOException {
        try {
            System.arraycopy(bArr, i, this.zzb, this.zze, i2);
            this.zze += i2;
        } catch (Throwable e) {
            throw new zzfhg$zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(i2)}), e);
        }
    }

    public final void zzd(int i, int i2) throws IOException {
        zza(i, 5);
        zze(i2);
    }

    public final void zzd(byte[] bArr, int i, int i2) throws IOException {
        zzc(i2);
        zzc(bArr, 0, i2);
    }

    public final void zze(int i) throws IOException {
        try {
            byte[] bArr = this.zzb;
            int i2 = this.zze;
            this.zze = i2 + 1;
            bArr[i2] = (byte) i;
            bArr = this.zzb;
            i2 = this.zze;
            this.zze = i2 + 1;
            bArr[i2] = (byte) (i >> 8);
            bArr = this.zzb;
            i2 = this.zze;
            this.zze = i2 + 1;
            bArr[i2] = (byte) (i >> 16);
            bArr = this.zzb;
            i2 = this.zze;
            this.zze = i2 + 1;
            bArr[i2] = i >> 24;
        } catch (Throwable e) {
            throw new zzfhg$zzc(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.zze), Integer.valueOf(this.zzd), Integer.valueOf(1)}), e);
        }
    }
}
