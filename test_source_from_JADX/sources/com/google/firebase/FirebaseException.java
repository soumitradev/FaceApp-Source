package com.google.firebase;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;

public class FirebaseException extends Exception {
    @Deprecated
    protected FirebaseException() {
    }

    public FirebaseException(@NonNull String str) {
        super(zzbq.zza(str, (Object) "Detail message must not be empty"));
    }

    public FirebaseException(@NonNull String str, Throwable th) {
        super(zzbq.zza(str, (Object) "Detail message must not be empty"), th);
    }
}
