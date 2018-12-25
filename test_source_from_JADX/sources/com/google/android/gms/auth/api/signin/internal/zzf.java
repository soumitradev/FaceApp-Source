package com.google.android.gms.auth.api.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzco;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbhf;
import java.util.HashSet;

public final class zzf {
    private static zzbhf zza = new zzbhf("GoogleSignInCommon", new String[0]);

    public static Intent zza(Context context, GoogleSignInOptions googleSignInOptions) {
        zza.zzb("getSignInIntent()", new Object[0]);
        Parcelable signInConfiguration = new SignInConfiguration(context.getPackageName(), googleSignInOptions);
        Intent intent = new Intent("com.google.android.gms.auth.GOOGLE_SIGN_IN");
        intent.setPackage(context.getPackageName());
        intent.setClass(context, SignInHubActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("config", signInConfiguration);
        intent.putExtra("config", bundle);
        return intent;
    }

    @Nullable
    public static GoogleSignInResult zza(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("googleSignInStatus") || intent.hasExtra("googleSignInAccount")) {
                GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) intent.getParcelableExtra("googleSignInAccount");
                Status status = (Status) intent.getParcelableExtra("googleSignInStatus");
                if (googleSignInAccount != null) {
                    status = Status.zza;
                }
                return new GoogleSignInResult(googleSignInAccount, status);
            }
        }
        return null;
    }

    public static OptionalPendingResult<GoogleSignInResult> zza(GoogleApiClient googleApiClient, Context context, GoogleSignInOptions googleSignInOptions, boolean z) {
        Result googleSignInResult;
        zza.zzb("silentSignIn()", new Object[0]);
        zza.zzb("getEligibleSavedSignInResult()", new Object[0]);
        zzbq.zza(googleSignInOptions);
        GoogleSignInOptions zzc = zzp.zza(context).zzc();
        if (zzc != null) {
            Account zzb = zzc.zzb();
            Account zzb2 = googleSignInOptions.zzb();
            boolean equals = zzb == null ? zzb2 == null : zzb.equals(zzb2);
            if (equals && !googleSignInOptions.zzd() && ((!googleSignInOptions.zzc() || (zzc.zzc() && googleSignInOptions.zze().equals(zzc.zze()))) && new HashSet(zzc.zza()).containsAll(new HashSet(googleSignInOptions.zza())))) {
                GoogleSignInAccount zzb3 = zzp.zza(context).zzb();
                if (!(zzb3 == null || zzb3.zzb())) {
                    googleSignInResult = new GoogleSignInResult(zzb3, Status.zza);
                    if (googleSignInResult != null) {
                        zza.zzb("Eligible saved sign in result found", new Object[0]);
                        return PendingResults.zzb(googleSignInResult, googleApiClient);
                    } else if (z) {
                        return PendingResults.zzb(new GoogleSignInResult(null, new Status(4)), googleApiClient);
                    } else {
                        zza.zzb("trySilentSignIn()", new Object[0]);
                        return new zzco(googleApiClient.zza(new zzg(googleApiClient, context, googleSignInOptions)));
                    }
                }
            }
        }
        googleSignInResult = null;
        if (googleSignInResult != null) {
            zza.zzb("Eligible saved sign in result found", new Object[0]);
            return PendingResults.zzb(googleSignInResult, googleApiClient);
        } else if (z) {
            return PendingResults.zzb(new GoogleSignInResult(null, new Status(4)), googleApiClient);
        } else {
            zza.zzb("trySilentSignIn()", new Object[0]);
            return new zzco(googleApiClient.zza(new zzg(googleApiClient, context, googleSignInOptions)));
        }
    }

    public static PendingResult<Status> zza(GoogleApiClient googleApiClient, Context context, boolean z) {
        zza.zzb("Signing out", new Object[0]);
        zza(context);
        return z ? PendingResults.zza(Status.zza, googleApiClient) : googleApiClient.zzb(new zzi(googleApiClient));
    }

    private static void zza(Context context) {
        zzp.zza(context).zza();
        for (GoogleApiClient zzd : GoogleApiClient.zza()) {
            zzd.zzd();
        }
        zzbm.zzb();
    }

    public static Intent zzb(Context context, GoogleSignInOptions googleSignInOptions) {
        zza.zzb("getFallbackSignInIntent()", new Object[0]);
        Intent zza = zza(context, googleSignInOptions);
        zza.setAction("com.google.android.gms.auth.APPAUTH_SIGN_IN");
        return zza;
    }

    public static PendingResult<Status> zzb(GoogleApiClient googleApiClient, Context context, boolean z) {
        zza.zzb("Revoking access", new Object[0]);
        String zza = zzaa.zza(context).zza("refreshToken");
        zza(context);
        return z ? zzb.zza(zza) : googleApiClient.zzb(new zzk(googleApiClient));
    }

    public static Intent zzc(Context context, GoogleSignInOptions googleSignInOptions) {
        zza.zzb("getNoImplementationSignInIntent()", new Object[0]);
        Intent zza = zza(context, googleSignInOptions);
        zza.setAction("com.google.android.gms.auth.NO_IMPL");
        return zza;
    }
}
