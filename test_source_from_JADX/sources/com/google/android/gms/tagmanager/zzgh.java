package com.google.android.gms.tagmanager;

import com.google.android.gms.analytics.Logger;

final class zzgh implements Logger {
    zzgh() {
    }

    public final void error(Exception exception) {
        zzdj.zza("", exception);
    }

    public final void error(String str) {
        zzdj.zza(str);
    }

    public final int getLogLevel() {
        switch (zzdj.zza) {
            case 2:
                return 0;
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            default:
                return 3;
        }
    }

    public final void info(String str) {
        zzdj.zzc(str);
    }

    public final void setLogLevel(int i) {
        zzdj.zzb("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
    }

    public final void verbose(String str) {
        zzdj.zze(str);
    }

    public final void warn(String str) {
        zzdj.zzb(str);
    }
}
