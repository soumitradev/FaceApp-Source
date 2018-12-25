package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

final class zzasm implements Logger {
    private int zza = 2;
    private boolean zzb;

    zzasm() {
    }

    public final void error(Exception exception) {
    }

    public final void error(String str) {
    }

    public final int getLogLevel() {
        return this.zza;
    }

    public final void info(String str) {
    }

    public final void setLogLevel(int i) {
        this.zza = i;
        if (!this.zzb) {
            String str = (String) zzast.zzb.zza();
            String str2 = (String) zzast.zzb.zza();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 91);
            stringBuilder.append("Logger is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.");
            stringBuilder.append(str2);
            stringBuilder.append(" DEBUG");
            Log.i(str, stringBuilder.toString());
            this.zzb = true;
        }
    }

    public final void verbose(String str) {
    }

    public final void warn(String str) {
    }
}
