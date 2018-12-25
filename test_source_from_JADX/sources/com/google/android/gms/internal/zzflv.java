package com.google.android.gms.internal;

import java.io.IOException;

public final class zzflv {
    public static final int[] zza = new int[0];
    public static final long[] zzb = new long[0];
    public static final float[] zzc = new float[0];
    public static final double[] zzd = new double[0];
    public static final boolean[] zze = new boolean[0];
    public static final String[] zzf = new String[0];
    public static final byte[][] zzg = new byte[0][];
    public static final byte[] zzh = new byte[0];
    private static int zzi = 11;
    private static int zzj = 12;
    private static int zzk = 16;
    private static int zzl = 26;

    public static final int zza(zzflj zzflj, int i) throws IOException {
        int zzm = zzflj.zzm();
        zzflj.zzb(i);
        int i2 = 1;
        while (zzflj.zza() == i) {
            zzflj.zzb(i);
            i2++;
        }
        zzflj.zzb(zzm, i);
        return i2;
    }
}
