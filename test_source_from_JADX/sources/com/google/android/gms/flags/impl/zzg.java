package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzg implements Callable<Long> {
    private /* synthetic */ SharedPreferences zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ Long zzc;

    zzg(SharedPreferences sharedPreferences, String str, Long l) {
        this.zza = sharedPreferences;
        this.zzb = str;
        this.zzc = l;
    }

    public final /* synthetic */ Object call() throws Exception {
        return Long.valueOf(this.zza.getLong(this.zzb, this.zzc.longValue()));
    }
}
