package com.google.android.gms.internal;

import java.io.UnsupportedEncodingException;

public class zzav extends zzr<String> {
    private final Object zza = new Object();
    private zzz<String> zzb;

    public zzav(int i, String str, zzz<String> zzz, zzy zzy) {
        super(i, str, zzy);
        this.zzb = zzz;
    }

    protected final zzx<String> zza(zzp zzp) {
        Object str;
        try {
            str = new String(zzp.zzb, zzap.zza(zzp.zzc));
        } catch (UnsupportedEncodingException e) {
            str = new String(zzp.zzb);
        }
        return zzx.zza(str, zzap.zza(zzp));
    }

    protected /* synthetic */ void zza(Object obj) {
        zzc((String) obj);
    }

    protected void zzc(String str) {
        synchronized (this.zza) {
            zzz zzz = this.zzb;
        }
        if (zzz != null) {
            zzz.zza(str);
        }
    }
}
