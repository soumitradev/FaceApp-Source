package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.zzd;

final class zzc implements zzd {
    zzc() {
    }

    public final zzj zza(Context context, String str, zzi zzi) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        zzj zzj = new zzj();
        zzj.zza = zzi.zza(context, str);
        if (zzj.zza != 0) {
            zzj.zzc = -1;
            return zzj;
        }
        zzj.zzb = zzi.zza(context, str, true);
        if (zzj.zzb != 0) {
            zzj.zzc = 1;
        }
        return zzj;
    }
}
