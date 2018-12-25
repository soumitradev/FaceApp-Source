package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzdyq;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

final class zzca extends Thread implements zzbz {
    private static zzca zzd;
    private final LinkedBlockingQueue<Runnable> zza = new LinkedBlockingQueue();
    private volatile boolean zzb = false;
    private volatile boolean zzc = false;
    private volatile zzcc zze;
    private final Context zzf;

    private zzca(Context context) {
        super("GAThread");
        if (context != null) {
            context = context.getApplicationContext();
        }
        this.zzf = context;
        start();
    }

    static zzca zza(Context context) {
        if (zzd == null) {
            zzd = new zzca(context);
        }
        return zzd;
    }

    public final void run() {
        while (true) {
            boolean z = this.zzc;
            try {
                Runnable runnable = (Runnable) this.zza.take();
                if (!this.zzb) {
                    runnable.run();
                }
            } catch (InterruptedException e) {
                zzdj.zzc(e.toString());
            } catch (Throwable th) {
                String str = "Error on Google TagManager Thread: ";
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream);
                zzdyq.zza(th, printStream);
                printStream.flush();
                String valueOf = String.valueOf(new String(byteArrayOutputStream.toByteArray()));
                zzdj.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                zzdj.zza("Google TagManager is shutting down.");
                this.zzb = true;
            }
        }
    }

    public final void zza(Runnable runnable) {
        this.zza.add(runnable);
    }

    public final void zza(String str) {
        zza(new zzcb(this, this, System.currentTimeMillis(), str));
    }
}
