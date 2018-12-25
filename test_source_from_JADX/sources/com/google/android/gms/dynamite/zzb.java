package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.zzc;
import com.google.android.gms.dynamite.DynamiteModule.zzd;

final class zzb implements zzd {
    zzb() {
    }

    public final zzj zza(Context context, String str, zzi zzi) throws zzc {
        zzj zzj = new zzj();
        zzj.zzb = zzi.zza(context, str, true);
        if (zzj.zzb != 0) {
            zzj.zzc = 1;
            return zzj;
        }
        zzj.zza = zzi.zza(context, str);
        if (zzj.zza != 0) {
            zzj.zzc = -1;
        }
        return zzj;
    }
}
