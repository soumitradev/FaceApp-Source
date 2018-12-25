package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.zzaa;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzr;

public final class zzcyt extends zzab<zzcyr> implements zzcyj {
    private final boolean zzd;
    private final zzr zze;
    private final Bundle zzf;
    private Integer zzg;

    private zzcyt(Context context, Looper looper, boolean z, zzr zzr, Bundle bundle, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, zzr, connectionCallbacks, onConnectionFailedListener);
        this.zzd = true;
        this.zze = zzr;
        this.zzf = bundle;
        this.zzg = zzr.zzl();
    }

    public zzcyt(Context context, Looper looper, boolean z, zzr zzr, zzcyk zzcyk, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, true, zzr, zza(zzr), connectionCallbacks, onConnectionFailedListener);
    }

    public static Bundle zza(zzr zzr) {
        zzcyk zzk = zzr.zzk();
        Integer zzl = zzr.zzl();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", zzr.zzb());
        if (zzl != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", zzl.intValue());
        }
        if (zzk != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", zzk.zza());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", zzk.zzb());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", zzk.zzc());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", zzk.zzd());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", zzk.zze());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", zzk.zzf());
            if (zzk.zzg() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", zzk.zzg().longValue());
            }
            if (zzk.zzh() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", zzk.zzh().longValue());
            }
        }
        return bundle;
    }

    public final boolean l_() {
        return this.zzd;
    }

    protected final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
        return queryLocalInterface instanceof zzcyr ? (zzcyr) queryLocalInterface : new zzcys(iBinder);
    }

    protected final String zza() {
        return "com.google.android.gms.signin.service.START";
    }

    public final void zza(zzan zzan, boolean z) {
        try {
            ((zzcyr) zzaf()).zza(zzan, this.zzg.intValue(), z);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    public final void zza(zzcyp zzcyp) {
        zzbq.zza(zzcyp, "Expecting a valid ISignInCallbacks");
        try {
            Account zzc = this.zze.zzc();
            GoogleSignInAccount googleSignInAccount = null;
            if ("<<default account>>".equals(zzc.name)) {
                googleSignInAccount = zzaa.zza(zzaa()).zza();
            }
            ((zzcyr) zzaf()).zza(new zzcyu(new zzbr(zzc, this.zzg.intValue(), googleSignInAccount)), zzcyp);
        } catch (Throwable e) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                zzcyp.zza(new zzcyw(8));
            } catch (RemoteException e2) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", e);
            }
        }
    }

    protected final String zzb() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    protected final Bundle zzc() {
        if (!zzaa().getPackageName().equals(this.zze.zzh())) {
            this.zzf.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zze.zzh());
        }
        return this.zzf;
    }

    public final void zzh() {
        try {
            ((zzcyr) zzaf()).zza(this.zzg.intValue());
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    public final void zzi() {
        zza(new zzm(this));
    }
}
