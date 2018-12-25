package com.google.android.gms.internal;

import android.accounts.Account;
import com.google.android.gms.common.api.Status;

final class zzavt extends zzavw {
    private /* synthetic */ zzavs zza;

    zzavt(zzavs zzavs) {
        this.zza = zzavs;
    }

    public final void zza(Account account) {
        this.zza.zza(new zzavx(account != null ? Status.zza : zzavq.zza, account));
    }
}
