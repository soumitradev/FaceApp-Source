package com.google.android.gms.cast;

import android.os.Bundle;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.internal.Hide;

public final class Cast$CastOptions implements HasOptions {
    final CastDevice zza;
    final Listener zzb;
    @Hide
    final Bundle zzc;
    private final int zzd;

    private Cast$CastOptions(Cast$CastOptions$Builder cast$CastOptions$Builder) {
        this.zza = cast$CastOptions$Builder.zza;
        this.zzb = cast$CastOptions$Builder.zzb;
        this.zzd = Cast$CastOptions$Builder.zza(cast$CastOptions$Builder);
        this.zzc = Cast$CastOptions$Builder.zzb(cast$CastOptions$Builder);
    }

    @Deprecated
    public static Cast$CastOptions$Builder builder(CastDevice castDevice, Listener listener) {
        return new Cast$CastOptions$Builder(castDevice, listener);
    }
}
