package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.hardware.display.VirtualDisplay;
import com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplaySessionResult;
import com.google.android.gms.cast.CastRemoteDisplayApi;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.Hide;

@Hide
@Deprecated
public final class zzbeq implements CastRemoteDisplayApi {
    private static final zzbei zza = new zzbei("CastRemoteDisplayApiImpl");
    private Api<?> zzb;
    private VirtualDisplay zzc;
    private final zzbfg zzd = new zzber(this);

    public zzbeq(Api api) {
        this.zzb = api;
    }

    @TargetApi(19)
    private final void zzb() {
        if (this.zzc != null) {
            if (this.zzc.getDisplay() != null) {
                zzbei zzbei = zza;
                int displayId = this.zzc.getDisplay().getDisplayId();
                StringBuilder stringBuilder = new StringBuilder(38);
                stringBuilder.append("releasing virtual display: ");
                stringBuilder.append(displayId);
                zzbei.zza(stringBuilder.toString(), new Object[0]);
            }
            this.zzc.release();
            this.zzc = null;
        }
    }

    public final PendingResult<CastRemoteDisplaySessionResult> startRemoteDisplay(GoogleApiClient googleApiClient, String str) {
        zza.zza("startRemoteDisplay", new Object[0]);
        return googleApiClient.zzb(new zzbes(this, googleApiClient, str));
    }

    public final PendingResult<CastRemoteDisplaySessionResult> stopRemoteDisplay(GoogleApiClient googleApiClient) {
        zza.zza("stopRemoteDisplay", new Object[0]);
        return googleApiClient.zzb(new zzbet(this, googleApiClient));
    }
}
