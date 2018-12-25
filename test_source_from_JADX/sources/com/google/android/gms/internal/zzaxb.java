package com.google.android.gms.internal;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.Status;

final class zzaxb extends zzawx {
    private /* synthetic */ zzaxa zza;

    zzaxb(zzaxa zzaxa) {
        this.zza = zzaxa;
    }

    public final void zza(Status status) {
        this.zza.zza(zzawy.zza(status));
    }

    public final void zza(Status status, Credential credential) {
        this.zza.zza(new zzawy(status, credential));
    }
}
