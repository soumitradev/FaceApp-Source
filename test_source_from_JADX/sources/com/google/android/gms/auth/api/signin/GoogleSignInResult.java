package com.google.android.gms.auth.api.signin;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;

public class GoogleSignInResult implements Result {
    private Status zza;
    private GoogleSignInAccount zzb;

    @Hide
    public GoogleSignInResult(@Nullable GoogleSignInAccount googleSignInAccount, @NonNull Status status) {
        this.zzb = googleSignInAccount;
        this.zza = status;
    }

    @Nullable
    public GoogleSignInAccount getSignInAccount() {
        return this.zzb;
    }

    @NonNull
    public Status getStatus() {
        return this.zza;
    }

    public boolean isSuccess() {
        return this.zza.isSuccess();
    }
}
