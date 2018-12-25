package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import com.google.android.gms.internal.zzatc;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

public class ExceptionReporter implements UncaughtExceptionHandler {
    private final UncaughtExceptionHandler zza;
    private final Tracker zzb;
    private final Context zzc;
    private ExceptionParser zzd;
    private GoogleAnalytics zze;

    public ExceptionReporter(Tracker tracker, UncaughtExceptionHandler uncaughtExceptionHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        } else if (context == null) {
            throw new NullPointerException("context cannot be null");
        } else {
            this.zza = uncaughtExceptionHandler;
            this.zzb = tracker;
            this.zzd = new StandardExceptionParser(context, new ArrayList());
            this.zzc = context.getApplicationContext();
            String str = "ExceptionReporter created, original handler is ";
            String valueOf = String.valueOf(uncaughtExceptionHandler == null ? "null" : uncaughtExceptionHandler.getClass().getName());
            zzatc.zza(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    public ExceptionParser getExceptionParser() {
        return this.zzd;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.zzd = exceptionParser;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String str = "UncaughtException";
        if (this.zzd != null) {
            str = this.zzd.getDescription(thread != null ? thread.getName() : null, th);
        }
        String str2 = "Reporting uncaught exception: ";
        String valueOf = String.valueOf(str);
        zzatc.zza(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        this.zzb.send(new ExceptionBuilder().setDescription(str).setFatal(true).build());
        if (this.zze == null) {
            this.zze = GoogleAnalytics.getInstance(this.zzc);
        }
        zza zza = this.zze;
        zza.dispatchLocalHits();
        zza.zza().zzh().zze();
        if (this.zza != null) {
            zzatc.zza("Passing exception to the original handler");
            this.zza.uncaughtException(thread, th);
        }
    }

    final UncaughtExceptionHandler zza() {
        return this.zza;
    }
}
