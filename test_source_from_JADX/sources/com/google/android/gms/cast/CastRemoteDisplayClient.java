package com.google.android.gms.cast;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import com.google.android.gms.cast.CastRemoteDisplay.Configuration;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api$ApiOptions$NoOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi$zza;
import com.google.android.gms.internal.zzbeh;
import com.google.android.gms.internal.zzbei;
import com.google.android.gms.internal.zzbez;
import com.google.android.gms.tasks.Task;
import com.parrot.freeflight.utils.ThumbnailUtils;

@TargetApi(19)
public class CastRemoteDisplayClient extends GoogleApi<Api$ApiOptions$NoOptions> {
    private static final zza<zzbez, Api$ApiOptions$NoOptions> zzd = new zzp();
    private static final Api<Api$ApiOptions$NoOptions> zze = new Api("CastRemoteDisplay.API", zzd, zzbeh.zzc);
    private final zzbei zzb = new zzbei("CastRemoteDisplay");
    private VirtualDisplay zzc;

    CastRemoteDisplayClient(@NonNull Context context) {
        super(context, zze, null, GoogleApi$zza.zza);
    }

    private static int zza(int i, int i2) {
        return (Math.min(i, i2) * ThumbnailUtils.TARGET_SIZE_MINI_THUMBNAIL) / 1080;
    }

    @TargetApi(19)
    private final void zzh() {
        if (this.zzc != null) {
            if (this.zzc.getDisplay() != null) {
                zzbei zzbei = this.zzb;
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

    public Task<Display> startRemoteDisplay(@NonNull CastDevice castDevice, @NonNull String str, @Configuration int i, @Nullable PendingIntent pendingIntent) {
        return zzb(new zzq(this, i, pendingIntent, castDevice, str));
    }

    public Task<Void> stopRemoteDisplay() {
        return zzb(new zzs(this));
    }
}
