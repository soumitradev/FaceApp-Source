package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;

public final class zzu {
    private static String zza = null;
    private static final int zzb = Process.myPid();

    public static String zza() {
        if (zza == null) {
            zza = zza(zzb);
        }
        return zza;
    }

    private static String zza(int i) {
        ThreadPolicy allowThreadDiskReads;
        Throwable th;
        Closeable closeable = null;
        if (i <= 0) {
            return null;
        }
        Closeable bufferedReader;
        try {
            allowThreadDiskReads = StrictMode.allowThreadDiskReads();
            StringBuilder stringBuilder = new StringBuilder(25);
            stringBuilder.append("/proc/");
            stringBuilder.append(i);
            stringBuilder.append("/cmdline");
            bufferedReader = new BufferedReader(new FileReader(stringBuilder.toString()));
            try {
                StrictMode.setThreadPolicy(allowThreadDiskReads);
                String trim = bufferedReader.readLine().trim();
                zzp.zza(bufferedReader);
                return trim;
            } catch (IOException e) {
                zzp.zza(bufferedReader);
                return null;
            } catch (Throwable th2) {
                th = th2;
                closeable = bufferedReader;
                zzp.zza(closeable);
                throw th;
            }
        } catch (IOException e2) {
            bufferedReader = null;
            zzp.zza(bufferedReader);
            return null;
        } catch (Throwable th3) {
            th = th3;
            zzp.zza(closeable);
            throw th;
        }
    }
}
