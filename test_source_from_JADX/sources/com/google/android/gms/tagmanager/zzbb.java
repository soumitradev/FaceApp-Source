package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzbb implements zzby {
    private static zzbb zza;
    private static final Object zzb = new Object();
    private zzek zzc;
    private zzbz zzd;

    private zzbb(Context context) {
        this(zzca.zza(context), new zzfm());
    }

    private zzbb(zzbz zzbz, zzek zzek) {
        this.zzd = zzbz;
        this.zzc = zzek;
    }

    public static zzby zza(Context context) {
        zzby zzby;
        synchronized (zzb) {
            if (zza == null) {
                zza = new zzbb(context);
            }
            zzby = zza;
        }
        return zzby;
    }

    public final boolean zza(String str) {
        if (this.zzc.zza()) {
            this.zzd.zza(str);
            return true;
        }
        zzdj.zzb("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
        return false;
    }
}
