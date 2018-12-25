package com.google.android.gms.internal;

import com.pdrogfer.mididroid.event.meta.MetaEvent;

abstract class zzfhg$zza extends zzfhg {
    final byte[] zzb;
    final int zzc;
    int zzd;
    int zze;

    zzfhg$zza(int i) {
        super(null);
        if (i < 0) {
            throw new IllegalArgumentException("bufferSize must be >= 0");
        }
        this.zzb = new byte[Math.max(i, 20)];
        this.zzc = this.zzb.length;
    }

    public final int zzb() {
        throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
    }

    final void zzb(byte b) {
        byte[] bArr = this.zzb;
        int i = this.zzd;
        this.zzd = i + 1;
        bArr[i] = b;
        this.zze++;
    }

    final void zzi(int i, int i2) {
        zzo((i << 3) | i2);
    }

    final void zzi(long j) {
        byte[] bArr;
        if (zzfhg.zzd()) {
            long j2 = (long) this.zzd;
            while ((j & -128) != 0) {
                bArr = this.zzb;
                int i = this.zzd;
                this.zzd = i + 1;
                zzfkq.zza(bArr, (long) i, (byte) ((((int) j) & MetaEvent.SEQUENCER_SPECIFIC) | 128));
                j >>>= 7;
            }
            bArr = this.zzb;
            int i2 = this.zzd;
            this.zzd = i2 + 1;
            zzfkq.zza(bArr, (long) i2, (byte) ((int) j));
            this.zze += (int) (((long) this.zzd) - j2);
            return;
        }
        while ((j & -128) != 0) {
            bArr = this.zzb;
            int i3 = this.zzd;
            this.zzd = i3 + 1;
            bArr[i3] = (byte) ((((int) j) & MetaEvent.SEQUENCER_SPECIFIC) | 128);
            this.zze++;
            j >>>= 7;
        }
        bArr = this.zzb;
        i2 = this.zzd;
        this.zzd = i2 + 1;
        bArr[i2] = (byte) ((int) j);
        this.zze++;
    }

    final void zzj(long j) {
        byte[] bArr = this.zzb;
        int i = this.zzd;
        this.zzd = i + 1;
        bArr[i] = (byte) ((int) (j & 255));
        bArr = this.zzb;
        i = this.zzd;
        this.zzd = i + 1;
        bArr[i] = (byte) ((int) ((j >> 8) & 255));
        bArr = this.zzb;
        i = this.zzd;
        this.zzd = i + 1;
        bArr[i] = (byte) ((int) ((j >> 16) & 255));
        bArr = this.zzb;
        i = this.zzd;
        this.zzd = i + 1;
        bArr[i] = (byte) ((int) ((j >> 24) & 255));
        bArr = this.zzb;
        i = this.zzd;
        this.zzd = i + 1;
        bArr[i] = (byte) ((int) (j >> 32));
        bArr = this.zzb;
        i = this.zzd;
        this.zzd = i + 1;
        bArr[i] = (byte) ((int) (j >> 40));
        bArr = this.zzb;
        i = this.zzd;
        this.zzd = i + 1;
        bArr[i] = (byte) ((int) (j >> 48));
        bArr = this.zzb;
        i = this.zzd;
        this.zzd = i + 1;
        bArr[i] = (byte) ((int) (j >> 56));
        this.zze += 8;
    }

    final void zzo(int i) {
        if (zzfhg.zzd()) {
            byte[] bArr;
            int i2;
            long j = (long) this.zzd;
            while ((i & -128) != 0) {
                bArr = this.zzb;
                i2 = this.zzd;
                this.zzd = i2 + 1;
                zzfkq.zza(bArr, (long) i2, (byte) ((i & MetaEvent.SEQUENCER_SPECIFIC) | 128));
                i >>>= 7;
            }
            bArr = this.zzb;
            i2 = this.zzd;
            this.zzd = i2 + 1;
            zzfkq.zza(bArr, (long) i2, (byte) i);
            this.zze += (int) (((long) this.zzd) - j);
            return;
        }
        byte[] bArr2;
        int i3;
        while ((i & -128) != 0) {
            bArr2 = this.zzb;
            i3 = this.zzd;
            this.zzd = i3 + 1;
            bArr2[i3] = (byte) ((i & MetaEvent.SEQUENCER_SPECIFIC) | 128);
            this.zze++;
            i >>>= 7;
        }
        bArr2 = this.zzb;
        i3 = this.zzd;
        this.zzd = i3 + 1;
        bArr2[i3] = (byte) i;
        this.zze++;
    }

    final void zzp(int i) {
        byte[] bArr = this.zzb;
        int i2 = this.zzd;
        this.zzd = i2 + 1;
        bArr[i2] = (byte) i;
        bArr = this.zzb;
        i2 = this.zzd;
        this.zzd = i2 + 1;
        bArr[i2] = (byte) (i >> 8);
        bArr = this.zzb;
        i2 = this.zzd;
        this.zzd = i2 + 1;
        bArr[i2] = (byte) (i >> 16);
        bArr = this.zzb;
        i2 = this.zzd;
        this.zzd = i2 + 1;
        bArr[i2] = i >> 24;
        this.zze += 4;
    }
}
