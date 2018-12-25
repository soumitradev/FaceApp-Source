package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerClient;
import java.io.IOException;

final class zzbcm extends zzbcu {
    final /* synthetic */ zzbcl zza;

    zzbcm(zzbcl zzbcl, GameManagerClient gameManagerClient) {
        this.zza = zzbcl;
        super(zzbcl, gameManagerClient);
    }

    public final void zza() {
        try {
            this.zza.zzj.setMessageReceivedCallbacks(this.zza.zzk, this.zza.zzg(), new zzbcn(this));
            this.zza.zzl();
            this.zza.zzk();
            this.zza.zza(null, 1100, null, this.zzb);
        } catch (IOException e) {
            this.zzb.zza(-1, 8, null);
        }
    }
}
