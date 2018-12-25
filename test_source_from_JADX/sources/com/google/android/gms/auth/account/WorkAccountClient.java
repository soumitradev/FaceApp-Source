package com.google.android.gms.auth.account;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api$ApiOptions$NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi$zza;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.internal.zzavq;
import com.google.android.gms.tasks.Task;

public class WorkAccountClient extends GoogleApi<Api$ApiOptions$NoOptions> {
    private final WorkAccountApi zzb = new zzavq();

    WorkAccountClient(@NonNull Activity activity) {
        super(activity, WorkAccount.API, null, GoogleApi$zza.zza);
    }

    WorkAccountClient(@NonNull Context context) {
        super(context, WorkAccount.API, null, GoogleApi$zza.zza);
    }

    public Task<Account> addWorkAccount(String str) {
        return zzbj.zza(this.zzb.addWorkAccount(zze(), str), new zzg(this));
    }

    public Task<Void> removeWorkAccount(Account account) {
        return zzbj.zza(this.zzb.removeWorkAccount(zze(), account));
    }

    public Task<Void> setWorkAuthenticatorEnabled(boolean z) {
        return zzbj.zza(this.zzb.setWorkAuthenticatorEnabledWithResult(zze(), z));
    }
}
