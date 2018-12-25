package com.google.android.gms.auth.api.signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.zzf;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.tasks.Task;

@Hide
public class GoogleSignInClient extends GoogleApi<GoogleSignInOptions> {
    private static final zza zzb = new zza();
    private static int zzc = zzb.zza;

    enum zzb {
        public static final int zza = 1;
        public static final int zzb = 2;
        public static final int zzc = 3;
        public static final int zzd = 4;
        private static final /* synthetic */ int[] zze = new int[]{zza, zzb, zzc, zzd};

        /* renamed from: values$50KLMJ33DTMIUPRFDTJMOP9FC5N68SJFD5I2UPRDECNM2TBKD0NM2S395TPMIPRED5N2UHRFDTJMOPAJD5JMSIBE8DM6IPBEEGI4IRBGDHIMQPBEEHGN8QBFDOTG____0 */
        public static int[] m9xd6228de2() {
            return (int[]) zze.clone();
        }
    }

    static class zza implements zzbo<GoogleSignInResult, GoogleSignInAccount> {
        private zza() {
        }

        public final /* synthetic */ Object zza(Result result) {
            return ((GoogleSignInResult) result).getSignInAccount();
        }
    }

    GoogleSignInClient(@NonNull Activity activity, GoogleSignInOptions googleSignInOptions) {
        super(activity, Auth.GOOGLE_SIGN_IN_API, googleSignInOptions, new zzg());
    }

    GoogleSignInClient(@NonNull Context context, GoogleSignInOptions googleSignInOptions) {
        super(context, Auth.GOOGLE_SIGN_IN_API, googleSignInOptions, new zzg());
    }

    private final synchronized int zzh() {
        if (zzc == zzb.zza) {
            Context zzg = zzg();
            int isGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(zzg);
            int i = isGooglePlayServicesAvailable == 0 ? zzb.zzd : (zzf.zza(zzg, isGooglePlayServicesAvailable, null) != null || DynamiteModule.zza(zzg, "com.google.android.gms.auth.api.fallback") == 0) ? zzb.zzb : zzb.zzc;
            zzc = i;
        }
        return zzc;
    }

    @NonNull
    public Intent getSignInIntent() {
        Context zzg = zzg();
        switch (zzc.zza[zzh() - 1]) {
            case 1:
                return com.google.android.gms.auth.api.signin.internal.zzf.zzb(zzg, (GoogleSignInOptions) zzb());
            case 2:
                return com.google.android.gms.auth.api.signin.internal.zzf.zza(zzg, (GoogleSignInOptions) zzb());
            default:
                return com.google.android.gms.auth.api.signin.internal.zzf.zzc(zzg, (GoogleSignInOptions) zzb());
        }
    }

    public Task<Void> revokeAccess() {
        return zzbj.zza(com.google.android.gms.auth.api.signin.internal.zzf.zzb(zze(), zzg(), zzh() == zzb.zzc));
    }

    public Task<Void> signOut() {
        return zzbj.zza(com.google.android.gms.auth.api.signin.internal.zzf.zza(zze(), zzg(), zzh() == zzb.zzc));
    }

    public Task<GoogleSignInAccount> silentSignIn() {
        return zzbj.zza(com.google.android.gms.auth.api.signin.internal.zzf.zza(zze(), zzg(), (GoogleSignInOptions) zzb(), zzh() == zzb.zzc), zzb);
    }
}
