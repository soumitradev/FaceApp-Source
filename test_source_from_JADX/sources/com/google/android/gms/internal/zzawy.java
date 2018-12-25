package com.google.android.gms.internal;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.Status;

public final class zzawy implements CredentialRequestResult {
    private final Status zza;
    private final Credential zzb;

    public zzawy(Status status, Credential credential) {
        this.zza = status;
        this.zzb = credential;
    }

    public static zzawy zza(Status status) {
        return new zzawy(status, null);
    }

    public final Credential getCredential() {
        return this.zzb;
    }

    public final Status getStatus() {
        return this.zza;
    }
}
