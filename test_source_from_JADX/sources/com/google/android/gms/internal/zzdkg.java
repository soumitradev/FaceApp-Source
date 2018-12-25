package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzdkg {
    private final Context zza;
    private final zzdkq zzb;
    private final zze zzc;
    private String zzd;
    private Map<String, Object> zze;
    private final Map<String, Object> zzf;

    public zzdkg(Context context) {
        this(context, new HashMap(), new zzdkq(context), zzi.zzd());
    }

    private zzdkg(Context context, Map<String, Object> map, zzdkq zzdkq, zze zze) {
        this.zzd = null;
        this.zze = new HashMap();
        this.zza = context;
        this.zzc = zze;
        this.zzb = zzdkq;
        this.zzf = map;
    }

    public final void zza(String str) {
        this.zzd = str;
    }
}
