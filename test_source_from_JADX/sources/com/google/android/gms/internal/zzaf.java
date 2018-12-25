package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class zzaf {
    public static boolean zza;
    private static String zzb;

    static class zza {
        public static final boolean zza = zzaf.zza;
        private final List<zzag> zzb = new ArrayList();
        private boolean zzc = false;

        zza() {
        }

        protected final void finalize() throws Throwable {
            if (!this.zzc) {
                zza("Request on the loose");
                zzaf.zzc("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        public final synchronized void zza(String str) {
            long j;
            this.zzc = true;
            if (this.zzb.size() == 0) {
                j = 0;
            } else {
                j = ((zzag) this.zzb.get(this.zzb.size() - 1)).zzc - ((zzag) this.zzb.get(0)).zzc;
            }
            if (j > 0) {
                long j2 = ((zzag) this.zzb.get(0)).zzc;
                zzaf.zzb("(%-4d ms) %s", Long.valueOf(j), str);
                for (zzag zzag : this.zzb) {
                    zzaf.zzb("(+%-4d) [%2d] %s", Long.valueOf(zzag.zzc - j2), Long.valueOf(zzag.zzb), zzag.zza);
                    j2 = zzag.zzc;
                }
            }
        }

        public final synchronized void zza(String str, long j) {
            if (this.zzc) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.zzb.add(new zzag(str, j, SystemClock.elapsedRealtime()));
        }
    }

    static {
        String str = "Volley";
        zzb = str;
        zza = Log.isLoggable(str, 2);
    }

    public static void zza(String str, Object... objArr) {
        if (zza) {
            Log.v(zzb, zzd(str, objArr));
        }
    }

    public static void zza(Throwable th, String str, Object... objArr) {
        Log.e(zzb, zzd(str, objArr), th);
    }

    public static void zzb(String str, Object... objArr) {
        Log.d(zzb, zzd(str, objArr));
    }

    public static void zzc(String str, Object... objArr) {
        Log.e(zzb, zzd(str, objArr));
    }

    private static String zzd(String str, Object... objArr) {
        if (objArr != null) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        String str2 = "<unknown>";
        for (int i = 2; i < stackTrace.length; i++) {
            if (!stackTrace[i].getClass().equals(zzaf.class)) {
                str2 = stackTrace[i].getClassName();
                str2 = str2.substring(str2.lastIndexOf(46) + 1);
                str2 = str2.substring(str2.lastIndexOf(36) + 1);
                String methodName = stackTrace[i].getMethodName();
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str2).length() + 1) + String.valueOf(methodName).length());
                stringBuilder.append(str2);
                stringBuilder.append(".");
                stringBuilder.append(methodName);
                str2 = stringBuilder.toString();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), str2, str});
    }
}
