package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient$Builder;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzcyk;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzr {
    private final Account zza;
    private final Set<Scope> zzb;
    private final Set<Scope> zzc;
    private final Map<Api<?>, zzt> zzd;
    private final int zze;
    private final View zzf;
    private final String zzg;
    private final String zzh;
    private final zzcyk zzi;
    private Integer zzj;

    @Hide
    public zzr(Account account, Set<Scope> set, Map<Api<?>, zzt> map, int i, View view, String str, String str2, zzcyk zzcyk) {
        Map map2;
        this.zza = account;
        this.zzb = set == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(set);
        if (map == null) {
            map2 = Collections.EMPTY_MAP;
        }
        this.zzd = map2;
        this.zzf = view;
        this.zze = i;
        this.zzg = str;
        this.zzh = str2;
        this.zzi = zzcyk;
        Set hashSet = new HashSet(this.zzb);
        for (zzt zzt : this.zzd.values()) {
            hashSet.addAll(zzt.zza);
        }
        this.zzc = Collections.unmodifiableSet(hashSet);
    }

    public static zzr zza(Context context) {
        return new GoogleApiClient$Builder(context).zza();
    }

    @Deprecated
    public final String zza() {
        return this.zza != null ? this.zza.name : null;
    }

    public final Set<Scope> zza(Api<?> api) {
        zzt zzt = (zzt) this.zzd.get(api);
        if (zzt != null) {
            if (!zzt.zza.isEmpty()) {
                Set<Scope> hashSet = new HashSet(this.zzb);
                hashSet.addAll(zzt.zza);
                return hashSet;
            }
        }
        return this.zzb;
    }

    public final void zza(Integer num) {
        this.zzj = num;
    }

    public final Account zzb() {
        return this.zza;
    }

    public final Account zzc() {
        return this.zza != null ? this.zza : new Account("<<default account>>", "com.google");
    }

    public final int zzd() {
        return this.zze;
    }

    public final Set<Scope> zze() {
        return this.zzb;
    }

    public final Set<Scope> zzf() {
        return this.zzc;
    }

    public final Map<Api<?>, zzt> zzg() {
        return this.zzd;
    }

    public final String zzh() {
        return this.zzg;
    }

    public final String zzi() {
        return this.zzh;
    }

    public final View zzj() {
        return this.zzf;
    }

    public final zzcyk zzk() {
        return this.zzi;
    }

    public final Integer zzl() {
        return this.zzj;
    }
}
