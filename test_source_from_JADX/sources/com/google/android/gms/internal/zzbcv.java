package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.common.api.Status;
import java.util.Locale;

final class zzbcv implements zzben {
    private /* synthetic */ zzbcl zza;
    private /* synthetic */ zzbcu zzb;

    zzbcv(zzbcu zzbcu, zzbcl zzbcl) {
        this.zzb = zzbcu;
        this.zza = zzbcl;
    }

    public final void zza(long j) {
        this.zzb.zza(zzbcu.zzb(new Status(2103)));
    }

    public final void zza(long j, int i, Object obj) {
        if (obj == null) {
            try {
                this.zzb.zza(new zzbcw(new Status(i, null, null), this.zzb.zzd));
                return;
            } catch (ClassCastException e) {
                this.zzb.zza(zzbcu.zzb(new Status(13)));
                return;
            }
        }
        zzbcz zzbcz = (zzbcz) obj;
        zzbcy zzbcy = zzbcz.zzm;
        if (zzbcy == null || zzbdw.zza("1.0.0", zzbcy.zzc())) {
            this.zzb.zza(new zzbcw(new Status(i, zzbcz.zzc, null), this.zzb.zzd));
            return;
        }
        this.zzb.zza.zzl = null;
        this.zzb.zza(zzbcu.zzb(new Status(GameManagerClient.STATUS_INCORRECT_VERSION, String.format(Locale.ROOT, "Incorrect Game Manager SDK version. Receiver: %s Sender: %s", new Object[]{zzbcy.zzc(), "1.0.0"}))));
    }
}
