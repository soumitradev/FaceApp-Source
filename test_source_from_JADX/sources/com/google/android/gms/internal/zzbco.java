package com.google.android.gms.internal;

import com.google.android.gms.cast.CastStatusCodes;
import org.json.JSONObject;

final class zzbco extends zzbcr {
    private /* synthetic */ int zzd;
    private /* synthetic */ String zze;
    private /* synthetic */ JSONObject zzf;
    private /* synthetic */ zzbcl zzg;

    zzbco(zzbcl zzbcl, int i, String str, JSONObject jSONObject) {
        this.zzg = zzbcl;
        this.zzd = i;
        this.zze = str;
        this.zzf = jSONObject;
        super(zzbcl);
    }

    public final void zza() {
        int i;
        switch (this.zzd) {
            case 2:
                i = 5;
                break;
            case 3:
                i = 1;
                break;
            case 4:
                i = 2;
                break;
            case 5:
                i = 3;
                break;
            case 6:
                i = 4;
                break;
            default:
                i = 0;
                break;
        }
        if (i == 0) {
            this.zzb.zza(-1, CastStatusCodes.INVALID_REQUEST, null);
            zzbcl.zze.zzc("sendPlayerRequest for unsupported playerState: %d", new Object[]{Integer.valueOf(this.zzd)});
            return;
        }
        this.zzg.zza(this.zze, i, this.zzf, this.zzb);
    }
}
