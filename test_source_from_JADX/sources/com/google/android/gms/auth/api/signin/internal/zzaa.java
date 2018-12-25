package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public final class zzaa {
    private static final Lock zza = new ReentrantLock();
    private static zzaa zzb;
    private final Lock zzc = new ReentrantLock();
    private final SharedPreferences zzd;

    private zzaa(Context context) {
        this.zzd = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static zzaa zza(Context context) {
        zzbq.zza(context);
        zza.lock();
        try {
            if (zzb == null) {
                zzb = new zzaa(context.getApplicationContext());
            }
            zzaa zzaa = zzb;
            return zzaa;
        } finally {
            zza.unlock();
        }
    }

    private final GoogleSignInAccount zzb(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        str = zza(zzb("googleSignInAccount", str));
        if (str != null) {
            try {
                return GoogleSignInAccount.zza(str);
            } catch (JSONException e) {
            }
        }
        return null;
    }

    private static String zzb(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(str2).length());
        stringBuilder.append(str);
        stringBuilder.append(":");
        stringBuilder.append(str2);
        return stringBuilder.toString();
    }

    private final GoogleSignInOptions zzc(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        str = zza(zzb("googleSignInOptions", str));
        if (str != null) {
            try {
                return GoogleSignInOptions.zza(str);
            } catch (JSONException e) {
            }
        }
        return null;
    }

    private final void zzd(String str) {
        this.zzc.lock();
        try {
            this.zzd.edit().remove(str).apply();
        } finally {
            this.zzc.unlock();
        }
    }

    public final GoogleSignInAccount zza() {
        return zzb(zza("defaultGoogleSignInAccount"));
    }

    protected final String zza(String str) {
        this.zzc.lock();
        try {
            str = this.zzd.getString(str, null);
            return str;
        } finally {
            this.zzc.unlock();
        }
    }

    final void zza(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzbq.zza(googleSignInAccount);
        zzbq.zza(googleSignInOptions);
        String zzc = googleSignInAccount.zzc();
        zza(zzb("googleSignInAccount", zzc), googleSignInAccount.zze());
        zza(zzb("googleSignInOptions", zzc), googleSignInOptions.zzf());
    }

    protected final void zza(String str, String str2) {
        this.zzc.lock();
        try {
            this.zzd.edit().putString(str, str2).apply();
        } finally {
            this.zzc.unlock();
        }
    }

    public final GoogleSignInOptions zzb() {
        return zzc(zza("defaultGoogleSignInAccount"));
    }

    public final void zzc() {
        String zza = zza("defaultGoogleSignInAccount");
        zzd("defaultGoogleSignInAccount");
        if (!TextUtils.isEmpty(zza)) {
            zzd(zzb("googleSignInAccount", zza));
            zzd(zzb("googleSignInOptions", zza));
        }
    }

    public final void zzd() {
        this.zzc.lock();
        try {
            this.zzd.edit().clear().apply();
        } finally {
            this.zzc.unlock();
        }
    }
}
