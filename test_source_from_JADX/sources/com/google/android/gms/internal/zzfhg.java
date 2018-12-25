package com.google.android.gms.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class zzfhg extends zzfgr {
    private static final Logger zzb = Logger.getLogger(zzfhg.class.getName());
    private static final boolean zzc = zzfkq.zza();
    zzfhi zza;

    private zzfhg() {
    }

    static int zza(int i) {
        return i > 4096 ? 4096 : i;
    }

    public static int zza(int i, zzfik zzfik) {
        i = zzf(i);
        int zzb = zzfik.zzb();
        return i + (zzh(zzb) + zzb);
    }

    public static int zza(zzfik zzfik) {
        int zzb = zzfik.zzb();
        return zzh(zzb) + zzb;
    }

    public static zzfhg zza(OutputStream outputStream, int i) {
        return new zzfhg$zzd(outputStream, i);
    }

    public static zzfhg zza(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    public static int zzb(double d) {
        return 8;
    }

    public static int zzb(float f) {
        return 4;
    }

    public static int zzb(int i, double d) {
        return zzf(i) + 8;
    }

    public static int zzb(int i, zzfik zzfik) {
        return ((zzf(1) << 1) + zzf(2, i)) + zza(3, zzfik);
    }

    public static int zzb(int i, String str) {
        return zzf(i) + zzb(str);
    }

    public static int zzb(int i, boolean z) {
        return zzf(i) + 1;
    }

    public static int zzb(zzfgs zzfgs) {
        int zza = zzfgs.zza();
        return zzh(zza) + zza;
    }

    public static int zzb(zzfjc zzfjc) {
        int zza = zzfjc.zza();
        return zzh(zza) + zza;
    }

    public static int zzb(String str) {
        int zza;
        try {
            zza = zzfks.zza(str);
        } catch (zzfkv e) {
            zza = str.getBytes(zzfhz.zza).length;
        }
        return zzh(zza) + zza;
    }

    public static int zzb(boolean z) {
        return 1;
    }

    public static int zzb(byte[] bArr) {
        int length = bArr.length;
        return zzh(length) + length;
    }

    public static zzfhg zzb(byte[] bArr, int i, int i2) {
        return new zzfhg$zzb(bArr, i, i2);
    }

    public static int zzc(int i, long j) {
        return zzf(i) + zze(j);
    }

    public static int zzc(int i, zzfgs zzfgs) {
        i = zzf(i);
        int zza = zzfgs.zza();
        return i + (zzh(zza) + zza);
    }

    public static int zzc(int i, zzfjc zzfjc) {
        return zzf(i) + zzb(zzfjc);
    }

    @Deprecated
    public static int zzc(zzfjc zzfjc) {
        return zzfjc.zza();
    }

    public static int zzd(int i, long j) {
        return zzf(i) + zze(j);
    }

    public static int zzd(int i, zzfgs zzfgs) {
        return ((zzf(1) << 1) + zzf(2, i)) + zzc(3, zzfgs);
    }

    public static int zzd(int i, zzfjc zzfjc) {
        return ((zzf(1) << 1) + zzf(2, i)) + zzc(3, zzfjc);
    }

    public static int zzd(long j) {
        return zze(j);
    }

    public static int zze(int i, int i2) {
        return zzf(i) + zzg(i2);
    }

    public static int zze(int i, long j) {
        return zzf(i) + 8;
    }

    public static int zze(long j) {
        if ((j & -128) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        int i;
        if ((j & -34359738368L) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if ((j & -2097152) != 0) {
            i += 2;
            j >>>= 14;
        }
        if ((j & -16384) != 0) {
            i++;
        }
        return i;
    }

    public static int zzf(int i) {
        return zzh(i << 3);
    }

    public static int zzf(int i, int i2) {
        return zzf(i) + zzh(i2);
    }

    public static int zzf(long j) {
        return zze(zzi(j));
    }

    public static int zzg(int i) {
        return i >= 0 ? zzh(i) : 10;
    }

    public static int zzg(int i, int i2) {
        return zzf(i) + 4;
    }

    public static int zzg(long j) {
        return 8;
    }

    public static int zzh(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (i & -268435456) == 0 ? 4 : 5;
    }

    public static int zzh(int i, int i2) {
        return zzf(i) + zzg(i2);
    }

    public static int zzh(long j) {
        return 8;
    }

    public static int zzi(int i) {
        return zzh(zzo(i));
    }

    private static long zzi(long j) {
        return (j << 1) ^ (j >> 63);
    }

    public static int zzj(int i) {
        return 4;
    }

    public static int zzk(int i) {
        return 4;
    }

    public static int zzl(int i) {
        return zzg(i);
    }

    static int zzm(int i) {
        return zzh(i) + i;
    }

    @Deprecated
    public static int zzn(int i) {
        return zzh(i);
    }

    private static int zzo(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public abstract void zza() throws IOException;

    public abstract void zza(byte b) throws IOException;

    public final void zza(double d) throws IOException {
        zzc(Double.doubleToRawLongBits(d));
    }

    public final void zza(float f) throws IOException {
        zze(Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzb(i, Double.doubleToRawLongBits(d));
    }

    public abstract void zza(int i, int i2) throws IOException;

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzfgs zzfgs) throws IOException;

    public abstract void zza(int i, zzfjc zzfjc) throws IOException;

    public abstract void zza(int i, String str) throws IOException;

    public abstract void zza(int i, boolean z) throws IOException;

    public abstract void zza(long j) throws IOException;

    public abstract void zza(zzfgs zzfgs) throws IOException;

    public abstract void zza(zzfjc zzfjc) throws IOException;

    public abstract void zza(String str) throws IOException;

    final void zza(String str, zzfkv zzfkv) throws IOException {
        zzb.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zzfkv);
        byte[] bytes = str.getBytes(zzfhz.zza);
        try {
            zzc(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (Throwable e) {
            throw new zzfhg$zzc(e);
        } catch (zzfhg$zzc e2) {
            throw e2;
        }
    }

    public final void zza(boolean z) throws IOException {
        zza((byte) z);
    }

    public abstract int zzb();

    public abstract void zzb(int i) throws IOException;

    public abstract void zzb(int i, int i2) throws IOException;

    public abstract void zzb(int i, long j) throws IOException;

    public abstract void zzb(int i, zzfgs zzfgs) throws IOException;

    public abstract void zzb(int i, zzfjc zzfjc) throws IOException;

    public final void zzb(long j) throws IOException {
        zza(zzi(j));
    }

    public final void zzc() {
        if (zzb() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public abstract void zzc(int i) throws IOException;

    public abstract void zzc(int i, int i2) throws IOException;

    public abstract void zzc(long j) throws IOException;

    public abstract void zzc(byte[] bArr, int i, int i2) throws IOException;

    public final void zzd(int i) throws IOException {
        zzc(zzo(i));
    }

    public abstract void zzd(int i, int i2) throws IOException;

    abstract void zzd(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zze(int i) throws IOException;

    @Deprecated
    public final void zze(int i, zzfjc zzfjc) throws IOException {
        zza(i, 3);
        zzfjc.zza(this);
        zza(i, 4);
    }
}
