package com.google.android.gms.common.api;

import android.accounts.Account;
import android.os.Looper;
import com.google.android.gms.common.api.internal.zzda;
import com.google.android.gms.common.internal.Hide;

@Hide
public class GoogleApi$zza {
    public static final GoogleApi$zza zza = new zzd().zza();
    public final zzda zzb;
    public final Looper zzc;

    private GoogleApi$zza(zzda zzda, Account account, Looper looper) {
        this.zzb = zzda;
        this.zzc = looper;
    }
}
