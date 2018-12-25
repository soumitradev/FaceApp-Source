package com.google.android.gms.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.UserRecoverableException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.zza;
import com.google.android.gms.common.zzs;
import com.google.android.gms.internal.zzbhf;
import java.io.IOException;
import java.util.List;

@Hide
public class zzd {
    public static final int CHANGE_TYPE_ACCOUNT_ADDED = 1;
    public static final int CHANGE_TYPE_ACCOUNT_REMOVED = 2;
    public static final int CHANGE_TYPE_ACCOUNT_RENAMED_FROM = 3;
    public static final int CHANGE_TYPE_ACCOUNT_RENAMED_TO = 4;
    public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
    public static final String KEY_SUPPRESS_PROGRESS_SCREEN = "suppressProgressScreen";
    @Hide
    public static final String WORK_ACCOUNT_TYPE = "com.google.work";
    @Hide
    @SuppressLint({"InlinedApi"})
    public static final String zza = "callerUid";
    @Hide
    @SuppressLint({"InlinedApi"})
    public static final String zzb = "androidPackageName";
    @Hide
    private static String[] zzc = new String[]{"com.google", "com.google.work", "cn.google"};
    private static final ComponentName zzd = new ComponentName("com.google.android.gms", "com.google.android.gms.auth.GetToken");
    private static final zzbhf zze = new zzbhf("Auth", new String[]{"GoogleAuthUtil"});

    zzd() {
    }

    public static void clearToken(Context context, String str) throws GooglePlayServicesAvailabilityException, GoogleAuthException, IOException {
        zzbq.zzc("Calling this from your main thread can lead to deadlock");
        zza(context);
        Bundle bundle = new Bundle();
        String str2 = context.getApplicationInfo().packageName;
        bundle.putString("clientPackageName", str2);
        if (!bundle.containsKey(zzb)) {
            bundle.putString(zzb, str2);
        }
        zza(context, zzd, new zzf(str, bundle));
    }

    public static List<AccountChangeEvent> getAccountChangeEvents(Context context, int i, String str) throws GoogleAuthException, IOException {
        zzbq.zza(str, (Object) "accountName must be provided");
        zzbq.zzc("Calling this from your main thread can lead to deadlock");
        zza(context);
        return (List) zza(context, zzd, new zzg(str, i));
    }

    public static String getAccountId(Context context, String str) throws GoogleAuthException, IOException {
        zzbq.zza(str, (Object) "accountName must be provided");
        zzbq.zzc("Calling this from your main thread can lead to deadlock");
        zza(context);
        return getToken(context, str, "^^_account_id_^^", new Bundle());
    }

    public static String getToken(Context context, Account account, String str) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return getToken(context, account, str, new Bundle());
    }

    public static String getToken(Context context, Account account, String str, Bundle bundle) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        zza(account);
        return zza(context, account, str, bundle).zza();
    }

    @Deprecated
    public static String getToken(Context context, String str, String str2) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return getToken(context, new Account(str, "com.google"), str2);
    }

    @Deprecated
    public static String getToken(Context context, String str, String str2, Bundle bundle) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return getToken(context, new Account(str, "com.google"), str2, bundle);
    }

    @RequiresPermission("android.permission.MANAGE_ACCOUNTS")
    @Deprecated
    public static void invalidateToken(Context context, String str) {
        AccountManager.get(context).invalidateAuthToken("com.google", str);
    }

    @Hide
    @TargetApi(23)
    public static Bundle removeAccount(Context context, Account account) throws GoogleAuthException, IOException {
        zzbq.zza((Object) context);
        zza(account);
        zza(context);
        return (Bundle) zza(context, zzd, new zzh(account));
    }

    @TargetApi(26)
    public static Boolean requestGoogleAccountsAccess(Context context) throws GoogleAuthException, IOException {
        zzbq.zza((Object) context);
        zza(context);
        return (Boolean) zza(context, zzd, new zzi(context.getApplicationInfo().packageName));
    }

    @Hide
    public static TokenData zza(Context context, Account account, String str, Bundle bundle) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        zzbq.zzc("Calling this from your main thread can lead to deadlock");
        zzbq.zza(str, (Object) "Scope cannot be empty or null.");
        zza(account);
        zza(context);
        bundle = bundle == null ? new Bundle() : new Bundle(bundle);
        String str2 = context.getApplicationInfo().packageName;
        bundle.putString("clientPackageName", str2);
        if (TextUtils.isEmpty(bundle.getString(zzb))) {
            bundle.putString(zzb, str2);
        }
        bundle.putLong("service_connection_start_time_millis", SystemClock.elapsedRealtime());
        return (TokenData) zza(context, zzd, new zze(account, str, bundle));
    }

    private static <T> T zza(Context context, ComponentName componentName, zzj<T> zzj) throws IOException, GoogleAuthException {
        ServiceConnection zza = new zza();
        zzag zza2 = zzag.zza(context);
        if (zza2.zza(componentName, zza, "GoogleAuthUtil")) {
            try {
                T zza3 = zzj.zza(zza.zza());
                zza2.zzb(componentName, zza, "GoogleAuthUtil");
                return zza3;
            } catch (Throwable e) {
                zze.zzc("GoogleAuthUtil", new Object[]{"Error on service connection.", e});
                throw new IOException("Error on service connection.", e);
            } catch (Throwable th) {
                zza2.zzb(componentName, zza, "GoogleAuthUtil");
            }
        } else {
            throw new IOException("Could not bind to service.");
        }
    }

    private static void zza(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        } else if (TextUtils.isEmpty(account.name)) {
            throw new IllegalArgumentException("Account name cannot be empty!");
        } else {
            String[] strArr = zzc;
            int length = strArr.length;
            int i = 0;
            while (i < length) {
                if (!strArr[i].equals(account.type)) {
                    i++;
                } else {
                    return;
                }
            }
            throw new IllegalArgumentException("Account type not supported");
        }
    }

    private static void zza(Context context) throws GoogleAuthException {
        try {
            zzs.zza(context.getApplicationContext());
        } catch (UserRecoverableException e) {
            throw new GooglePlayServicesAvailabilityException(e.getConnectionStatusCode(), e.getMessage(), e.getIntent());
        } catch (GooglePlayServicesNotAvailableException e2) {
            throw new GoogleAuthException(e2.getMessage());
        }
    }

    private static <T> T zzb(T t) throws IOException {
        if (t != null) {
            return t;
        }
        zze.zzd("GoogleAuthUtil", new Object[]{"Binder call returned null."});
        throw new IOException("Service unavailable.");
    }
}
