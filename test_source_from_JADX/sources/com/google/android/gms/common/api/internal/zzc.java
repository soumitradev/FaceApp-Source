package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public final class zzc<A extends zzm<? extends Result, zzb>> extends zza {
    private A zza;

    public zzc(int i, A a) {
        super(i);
        this.zza = a;
    }

    public final void zza(@NonNull Status status) {
        this.zza.zzc(status);
    }

    public final void zza(@NonNull zzae zzae, boolean z) {
        zzae.zza(this.zza, z);
    }

    public final void zza(zzbo<?> zzbo) throws DeadObjectException {
        try {
            this.zza.zzb(zzbo.zzb());
        } catch (RuntimeException e) {
            String simpleName = e.getClass().getSimpleName();
            String localizedMessage = e.getLocalizedMessage();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(simpleName).length() + 2) + String.valueOf(localizedMessage).length());
            stringBuilder.append(simpleName);
            stringBuilder.append(": ");
            stringBuilder.append(localizedMessage);
            this.zza.zzc(new Status(10, stringBuilder.toString()));
        }
    }
}
