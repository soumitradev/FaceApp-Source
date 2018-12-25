package com.google.android.gms.auth.api;

import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.proxy.ProxyApi;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zzd;
import com.google.android.gms.auth.api.signin.internal.zze;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api$ApiOptions$NoOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzawb;
import com.google.android.gms.internal.zzawc;
import com.google.android.gms.internal.zzawd;
import com.google.android.gms.internal.zzawz;
import com.google.android.gms.internal.zzaxi;
import com.google.android.gms.internal.zzayh;

public final class Auth {
    public static final Api<Auth$AuthCredentialsOptions> CREDENTIALS_API = new Api("Auth.CREDENTIALS_API", zzd, zza);
    public static final CredentialsApi CredentialsApi = new zzawz();
    public static final Api<GoogleSignInOptions> GOOGLE_SIGN_IN_API = new Api("Auth.GOOGLE_SIGN_IN_API", zzf, zzb);
    public static final GoogleSignInApi GoogleSignInApi = new zzd();
    @Hide
    @KeepForSdk
    public static final Api<zzf> PROXY_API = zzd.zza;
    @Hide
    @KeepForSdk
    public static final ProxyApi ProxyApi = new zzayh();
    @Hide
    public static final zzf<zzaxi> zza = new zzf();
    @Hide
    public static final zzf<zze> zzb = new zzf();
    @Hide
    private static zzf<zzawd> zzc = new zzf();
    private static final zza<zzaxi, Auth$AuthCredentialsOptions> zzd = new zza();
    private static final zza<zzawd, Api$ApiOptions$NoOptions> zze = new zzb();
    private static final zza<zze, GoogleSignInOptions> zzf = new zzc();
    @Hide
    private static Api<Api$ApiOptions$NoOptions> zzg = new Api("Auth.ACCOUNT_STATUS_API", zze, zzc);
    @Hide
    private static zzawb zzh = new zzawc();

    @Hide
    private Auth() {
    }
}
