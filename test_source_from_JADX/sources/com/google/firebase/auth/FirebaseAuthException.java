package com.google.firebase.auth;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;
import com.google.firebase.FirebaseException;

public class FirebaseAuthException extends FirebaseException {
    private final String zza;

    public FirebaseAuthException(@NonNull String str, @NonNull String str2) {
        super(str2);
        this.zza = zzbq.zza(str);
    }

    @NonNull
    public String getErrorCode() {
        return this.zza;
    }
}
