package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfhu.zzg;

final class zzfiq implements zzfjw {
    private static final zzfjb zzb = new zzfir();
    private final zzfjb zza;

    public zzfiq() {
        this(new zzfis(zzfht.zza(), zza()));
    }

    private zzfiq(zzfjb zzfjb) {
        this.zza = (zzfjb) zzfhz.zza(zzfjb, "messageInfoFactory");
    }

    private static zzfjb zza() {
        try {
            return (zzfjb) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            return zzb;
        }
    }

    private static boolean zza(zzfja zzfja) {
        return zzfja.zza() == zzg.zzk;
    }

    public final <T> zzfjv<T> zza(Class<T> cls) {
        zzfjx.zza((Class) cls);
        zzfja zzb = this.zza.zzb(cls);
        if (zzb.zzb()) {
            return zzfhu.class.isAssignableFrom(cls) ? zzfjh.zza(cls, zzfjx.zzc(), zzfhp.zza(), zzb.zzc()) : zzfjh.zza(cls, zzfjx.zza(), zzfhp.zzb(), zzb.zzc());
        } else {
            if (zzfhu.class.isAssignableFrom(cls)) {
                if (zza(zzb)) {
                    return zzfjg.zza(cls, zzb, zzfjk.zzb(), zzfim.zzb(), zzfjx.zzc(), zzfhp.zza(), zzfiz.zzb());
                }
                return zzfjg.zza(cls, zzb, zzfjk.zzb(), zzfim.zzb(), zzfjx.zzc(), null, zzfiz.zzb());
            } else if (zza(zzb)) {
                return zzfjg.zza(cls, zzb, zzfjk.zza(), zzfim.zza(), zzfjx.zza(), zzfhp.zzb(), zzfiz.zza());
            } else {
                return zzfjg.zza(cls, zzb, zzfjk.zza(), zzfim.zza(), zzfjx.zzb(), null, zzfiz.zza());
            }
        }
    }
}
