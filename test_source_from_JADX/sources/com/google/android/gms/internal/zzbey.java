package com.google.android.gms.internal;

import android.view.Display;
import com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplaySessionResult;
import com.google.android.gms.common.api.Status;

final class zzbey implements CastRemoteDisplaySessionResult {
    private final Status zza;
    private final Display zzb;

    public zzbey(Display display) {
        this.zza = Status.zza;
        this.zzb = display;
    }

    public zzbey(Status status) {
        this.zza = status;
        this.zzb = null;
    }

    public final Display getPresentationDisplay() {
        return this.zzb;
    }

    public final Status getStatus() {
        return this.zza;
    }
}
