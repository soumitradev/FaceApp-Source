package com.google.android.gms.internal;

import android.accounts.Account;
import com.google.android.gms.auth.account.WorkAccountApi.AddAccountResult;
import com.google.android.gms.common.api.Status;

final class zzavx implements AddAccountResult {
    private final Status zza;
    private final Account zzb;

    public zzavx(Status status, Account account) {
        this.zza = status;
        this.zzb = account;
    }

    public final Account getAccount() {
        return this.zzb;
    }

    public final Status getStatus() {
        return this.zza;
    }
}
