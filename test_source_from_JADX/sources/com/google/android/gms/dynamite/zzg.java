package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.zzc;
import com.google.android.gms.dynamite.DynamiteModule.zzd;

final class zzg implements zzd {
    zzg() {
    }

    public final zzj zza(Context context, String str, zzi zzi) throws zzc {
        zzj zzj = new zzj();
        zzj.zza = zzi.zza(context, str);
        zzj.zzb = zzj.zza != 0 ? zzi.zza(context, str, false) : zzi.zza(context, str, true);
        if (zzj.zza == 0 && zzj.zzb == 0) {
            zzj.zzc = 0;
            return zzj;
        } else if (zzj.zzb >= zzj.zza) {
            zzj.zzc = 1;
            return zzj;
        } else {
            zzj.zzc = -1;
            return zzj;
        }
    }
}
