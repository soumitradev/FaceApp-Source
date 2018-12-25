package com.google.android.gms.internal;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast.ApplicationConnectionResult;
import com.google.android.gms.common.api.Status;

final class zzbdq implements ApplicationConnectionResult {
    private final Status zza;
    private final ApplicationMetadata zzb;
    private final String zzc;
    private final String zzd;
    private final boolean zze;

    public zzbdq(Status status) {
        this(status, null, null, null, false);
    }

    public zzbdq(Status status, ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
        this.zza = status;
        this.zzb = applicationMetadata;
        this.zzc = str;
        this.zzd = str2;
        this.zze = z;
    }

    public final ApplicationMetadata getApplicationMetadata() {
        return this.zzb;
    }

    public final String getApplicationStatus() {
        return this.zzc;
    }

    public final String getSessionId() {
        return this.zzd;
    }

    public final Status getStatus() {
        return this.zza;
    }

    public final boolean getWasLaunched() {
        return this.zze;
    }
}
