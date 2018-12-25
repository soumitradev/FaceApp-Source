package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;

final class zzbgi extends ConstantState {
    int zza;
    int zzb;

    zzbgi(zzbgi zzbgi) {
        if (zzbgi != null) {
            this.zza = zzbgi.zza;
            this.zzb = zzbgi.zzb;
        }
    }

    public final int getChangingConfigurations() {
        return this.zza;
    }

    public final Drawable newDrawable() {
        return new zzbge(this);
    }
}
