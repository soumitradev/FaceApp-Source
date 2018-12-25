package com.google.android.gms.tagmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.Random;

@Hide
public final class zzai {
    private final Context zza;
    private final Random zzb;
    private final String zzc;

    public zzai(Context context, String str) {
        this(context, str, new Random());
    }

    @VisibleForTesting
    private zzai(Context context, String str, Random random) {
        this.zza = (Context) zzbq.zza(context);
        this.zzc = (String) zzbq.zza(str);
        this.zzb = random;
    }

    private final long zza(long j, long j2) {
        SharedPreferences zze = zze();
        long max = Math.max(0, zze.getLong("FORBIDDEN_COUNT", 0));
        return (long) (this.zzb.nextFloat() * ((float) (j + ((long) ((((float) max) / ((float) ((max + Math.max(0, zze.getLong("SUCCESSFUL_COUNT", 0))) + 1))) * ((float) (j2 - j)))))));
    }

    private final SharedPreferences zze() {
        Context context = this.zza;
        String valueOf = String.valueOf("_gtmContainerRefreshPolicy_");
        String valueOf2 = String.valueOf(this.zzc);
        return context.getSharedPreferences(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), 0);
    }

    public final long zza() {
        return zza(7200000, 259200000) + 43200000;
    }

    public final long zzb() {
        return zza(600000, 86400000) + 3600000;
    }

    @SuppressLint({"CommitPrefEdits"})
    public final void zzc() {
        SharedPreferences zze = zze();
        long j = zze.getLong("FORBIDDEN_COUNT", 0);
        long j2 = zze.getLong("SUCCESSFUL_COUNT", 0);
        Editor edit = zze.edit();
        j = j == 0 ? 3 : Math.min(10, j + 1);
        long max = Math.max(0, Math.min(j2, 10 - j));
        edit.putLong("FORBIDDEN_COUNT", j);
        edit.putLong("SUCCESSFUL_COUNT", max);
        edit.apply();
    }

    @SuppressLint({"CommitPrefEdits"})
    public final void zzd() {
        SharedPreferences zze = zze();
        long j = zze.getLong("SUCCESSFUL_COUNT", 0);
        long j2 = zze.getLong("FORBIDDEN_COUNT", 0);
        long min = Math.min(10, j + 1);
        long max = Math.max(0, Math.min(j2, 10 - min));
        Editor edit = zze.edit();
        edit.putLong("SUCCESSFUL_COUNT", min);
        edit.putLong("FORBIDDEN_COUNT", max);
        edit.apply();
    }
}
