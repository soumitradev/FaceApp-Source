package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zze implements Callable<Integer> {
    private /* synthetic */ SharedPreferences zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ Integer zzc;

    zze(SharedPreferences sharedPreferences, String str, Integer num) {
        this.zza = sharedPreferences;
        this.zzb = str;
        this.zzc = num;
    }

    public final /* synthetic */ Object call() throws Exception {
        return Integer.valueOf(this.zza.getInt(this.zzb, this.zzc.intValue()));
    }
}
