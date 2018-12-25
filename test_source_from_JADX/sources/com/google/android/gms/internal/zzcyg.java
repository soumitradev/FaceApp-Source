package com.google.android.gms.internal;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Hide;

public final class zzcyg {
    @Hide
    public static final zza<zzcyt, zzcyk> zza = new zzcyh();
    @Hide
    public static final Api<zzcyk> zzb = new Api("SignIn.API", zza, zzc);
    @Hide
    private static zzf<zzcyt> zzc = new zzf();
    @Hide
    private static zzf<zzcyt> zzd = new zzf();
    @Hide
    private static zza<zzcyt, Object> zze = new zzcyi();
    private static Scope zzf = new Scope(Scopes.PROFILE);
    private static Scope zzg = new Scope("email");
    @Hide
    private static Api<Object> zzh = new Api("SignIn.INTERNAL_API", zze, zzd);
}
