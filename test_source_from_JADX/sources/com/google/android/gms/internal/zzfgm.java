package com.google.android.gms.internal;

public abstract class zzfgm<MessageType extends zzfjc> implements zzfjl<MessageType> {
    private static final zzfhm zza = zzfhm.zza();

    public final /* synthetic */ Object zza(zzfhb zzfhb, zzfhm zzfhm) throws zzfie {
        zzfjc zzfjc = (zzfjc) zzb(zzfhb, zzfhm);
        if (zzfjc == null || zzfjc.zzs()) {
            return zzfjc;
        }
        zzfkm zzfkm = zzfjc instanceof zzfgj ? new zzfkm((zzfgj) zzfjc) : zzfjc instanceof zzfgl ? new zzfkm((zzfgl) zzfjc) : new zzfkm(zzfjc);
        throw zzfkm.zza().zza(zzfjc);
    }
}
