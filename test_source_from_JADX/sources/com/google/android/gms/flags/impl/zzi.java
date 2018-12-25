package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzi implements Callable<String> {
    private /* synthetic */ SharedPreferences zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ String zzc;

    zzi(SharedPreferences sharedPreferences, String str, String str2) {
        this.zza = sharedPreferences;
        this.zzb = str;
        this.zzc = str2;
    }

    public final /* synthetic */ Object call() throws Exception {
        return this.zza.getString(this.zzb, this.zzc);
    }
}
