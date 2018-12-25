package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;

public abstract class zzfhb {
    private static volatile boolean zze;
    int zza;
    int zzb;
    int zzc;
    private boolean zzd;

    static {
        zze = false;
        zze = true;
    }

    private zzfhb() {
        this.zzb = 100;
        this.zzc = Integer.MAX_VALUE;
        this.zzd = false;
    }

    public static long zza(long j) {
        return (j >>> 1) ^ (-(j & 1));
    }

    public static zzfhb zza(InputStream inputStream) {
        if (inputStream != null) {
            return new zzfhe(inputStream, 4096, null);
        }
        byte[] bArr = zzfhz.zzb;
        return zza(bArr, 0, bArr.length, false);
    }

    public static zzfhb zza(byte[] bArr) {
        return zza(bArr, 0, bArr.length, false);
    }

    public static zzfhb zza(byte[] bArr, int i, int i2) {
        return zza(bArr, i, i2, false);
    }

    static zzfhb zza(byte[] bArr, int i, int i2, boolean z) {
        zzfhb zzfhd = new zzfhd(bArr, i, i2, z, null);
        try {
            zzfhd.zzd(i2);
            return zzfhd;
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int zzg(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public abstract int zza() throws IOException;

    public abstract <T extends zzfhu<T, ?>> T zza(T t, zzfhm zzfhm) throws IOException;

    public abstract void zza(int i) throws zzfie;

    public abstract void zza(zzfjd zzfjd, zzfhm zzfhm) throws IOException;

    public abstract double zzb() throws IOException;

    public abstract boolean zzb(int i) throws IOException;

    public abstract float zzc() throws IOException;

    public final int zzc(int i) {
        i = this.zzc;
        this.zzc = Integer.MAX_VALUE;
        return i;
    }

    public abstract int zzd(int i) throws zzfie;

    public abstract long zzd() throws IOException;

    public abstract long zze() throws IOException;

    public abstract void zze(int i);

    public abstract int zzf() throws IOException;

    public abstract void zzf(int i) throws IOException;

    public abstract long zzg() throws IOException;

    public abstract int zzh() throws IOException;

    public abstract boolean zzi() throws IOException;

    public abstract String zzj() throws IOException;

    public abstract String zzk() throws IOException;

    public abstract zzfgs zzl() throws IOException;

    public abstract int zzm() throws IOException;

    public abstract int zzn() throws IOException;

    public abstract int zzo() throws IOException;

    public abstract long zzp() throws IOException;

    public abstract int zzq() throws IOException;

    public abstract long zzr() throws IOException;

    public abstract int zzs() throws IOException;

    abstract long zzt() throws IOException;

    public abstract int zzu();

    public abstract boolean zzv() throws IOException;

    public abstract int zzw();
}
