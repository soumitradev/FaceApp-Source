package com.google.android.gms.internal;

import java.io.PrintStream;
import java.io.PrintWriter;

public final class zzdyq {
    private static zzdyr zza;
    private static int zzb;

    static final class zza extends zzdyr {
        zza() {
        }

        public final void zza(Throwable th, PrintStream printStream) {
            th.printStackTrace(printStream);
        }

        public final void zza(Throwable th, PrintWriter printWriter) {
            th.printStackTrace(printWriter);
        }
    }

    static {
        Integer zza;
        zzdyr zzdyv;
        Throwable th;
        PrintStream printStream;
        String name;
        StringBuilder stringBuilder;
        int i = 1;
        try {
            zza = zza();
            if (zza != null) {
                try {
                    if (zza.intValue() >= 19) {
                        zzdyv = new zzdyv();
                        zza = zzdyv;
                        if (zza != null) {
                            i = zza.intValue();
                        }
                        zzb = i;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = System.err;
                    name = zza.class.getName();
                    stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
                    stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
                    stringBuilder.append(name);
                    stringBuilder.append("will be used. The error is: ");
                    printStream.println(stringBuilder.toString());
                    th.printStackTrace(System.err);
                    zzdyv = new zza();
                    zza = zzdyv;
                    if (zza != null) {
                        i = zza.intValue();
                    }
                    zzb = i;
                }
            }
            zzdyv = (Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ^ 1) != 0 ? new zzdyu() : new zza();
        } catch (Throwable th3) {
            th = th3;
            zza = null;
            printStream = System.err;
            name = zza.class.getName();
            stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
            stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
            stringBuilder.append(name);
            stringBuilder.append("will be used. The error is: ");
            printStream.println(stringBuilder.toString());
            th.printStackTrace(System.err);
            zzdyv = new zza();
            zza = zzdyv;
            if (zza != null) {
                i = zza.intValue();
            }
            zzb = i;
        }
        zza = zzdyv;
        if (zza != null) {
            i = zza.intValue();
        }
        zzb = i;
    }

    private static Integer zza() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    public static void zza(Throwable th, PrintStream printStream) {
        zza.zza(th, printStream);
    }

    public static void zza(Throwable th, PrintWriter printWriter) {
        zza.zza(th, printWriter);
    }
}
