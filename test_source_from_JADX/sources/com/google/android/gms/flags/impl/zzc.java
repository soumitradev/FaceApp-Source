package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzc implements Callable<Boolean> {
    private /* synthetic */ SharedPreferences zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ Boolean zzc;

    zzc(SharedPreferences sharedPreferences, String str, Boolean bool) {
        this.zza = sharedPreferences;
        this.zzb = str;
        this.zzc = bool;
    }

    public final /* synthetic */ Object call() throws Exception {
        return Boolean.valueOf(this.zza.getBoolean(this.zzb, this.zzc.booleanValue()));
    }
}
