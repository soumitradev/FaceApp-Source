package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.support.v4.util.ArraySet;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzcyk;
import java.util.Collection;

public final class zzs {
    private Account zza;
    private ArraySet<Scope> zzb;
    private int zzc = 0;
    private String zzd;
    private String zze;
    private zzcyk zzf = zzcyk.zza;

    public final zzr zza() {
        return new zzr(this.zza, this.zzb, null, 0, null, this.zzd, this.zze, this.zzf);
    }

    public final zzs zza(Account account) {
        this.zza = account;
        return this;
    }

    public final zzs zza(String str) {
        this.zzd = str;
        return this;
    }

    public final zzs zza(Collection<Scope> collection) {
        if (this.zzb == null) {
            this.zzb = new ArraySet();
        }
        this.zzb.addAll((Collection) collection);
        return this;
    }

    public final zzs zzb(String str) {
        this.zze = str;
        return this;
    }
}
