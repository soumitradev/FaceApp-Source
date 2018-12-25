package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.Hide;
import com.google.firebase.FirebaseApp;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FirebaseInstanceId {
    private static final long zza = TimeUnit.HOURS.toSeconds(8);
    private static Map<String, FirebaseInstanceId> zzb = new ArrayMap();
    private static zzaa zzc;
    private static ScheduledThreadPoolExecutor zzd;
    private final FirebaseApp zze;
    private final zzw zzf;
    private final zzx zzg;
    private KeyPair zzh;
    private boolean zzi = false;
    private boolean zzj;

    private FirebaseInstanceId(FirebaseApp firebaseApp, zzw zzw) {
        if (zzw.zza(firebaseApp) == null) {
            throw new IllegalStateException("FirebaseInstanceId failed to initialize, FirebaseApp is missing project ID");
        }
        this.zze = firebaseApp;
        this.zzf = zzw;
        this.zzg = new zzx(firebaseApp.getApplicationContext(), zzw);
        this.zzj = zzl();
        if (zzh()) {
            zzi();
        }
    }

    public static FirebaseInstanceId getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @Keep
    public static synchronized FirebaseInstanceId getInstance(@NonNull FirebaseApp firebaseApp) {
        FirebaseInstanceId firebaseInstanceId;
        synchronized (FirebaseInstanceId.class) {
            firebaseInstanceId = (FirebaseInstanceId) zzb.get(firebaseApp.getOptions().getApplicationId());
            if (firebaseInstanceId == null) {
                if (zzc == null) {
                    zzc = new zzaa(firebaseApp.getApplicationContext());
                }
                firebaseInstanceId = new FirebaseInstanceId(firebaseApp, new zzw(firebaseApp.getApplicationContext()));
                zzb.put(firebaseApp.getOptions().getApplicationId(), firebaseInstanceId);
            }
        }
        return firebaseInstanceId;
    }

    private final String zza(String str, String str2, Bundle bundle) throws IOException {
        bundle.putString("scope", str2);
        bundle.putString("sender", str);
        bundle.putString("subtype", str);
        bundle.putString("appid", getId());
        bundle.putString("gmp_app_id", this.zze.getOptions().getApplicationId());
        bundle.putString("gmsv", Integer.toString(this.zzf.zzd()));
        bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
        bundle.putString("app_ver", this.zzf.zzb());
        bundle.putString("app_ver_name", this.zzf.zzc());
        bundle.putString("cliv", "fiid-12210000");
        Bundle zza = this.zzg.zza(bundle);
        if (zza == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        str2 = zza.getString("registration_id");
        if (str2 != null) {
            return str2;
        }
        str2 = zza.getString("unregistered");
        if (str2 != null) {
            return str2;
        }
        str2 = zza.getString("error");
        if ("RST".equals(str2)) {
            zzf();
            throw new IOException("INSTANCE_ID_RESET");
        } else if (str2 != null) {
            throw new IOException(str2);
        } else {
            str = String.valueOf(zza);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 21);
            stringBuilder.append("Unexpected response: ");
            stringBuilder.append(str);
            Log.w("FirebaseInstanceId", stringBuilder.toString(), new Throwable());
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
    }

    static void zza(Runnable runnable, long j) {
        synchronized (FirebaseInstanceId.class) {
            if (zzd == null) {
                zzd = new ScheduledThreadPoolExecutor(1);
            }
            zzd.schedule(runnable, j, TimeUnit.SECONDS);
        }
    }

    static zzaa zzd() {
        return zzc;
    }

    static boolean zze() {
        if (!Log.isLoggable("FirebaseInstanceId", 3)) {
            if (VERSION.SDK_INT != 23 || !Log.isLoggable("FirebaseInstanceId", 3)) {
                return false;
            }
        }
        return true;
    }

    private final void zzi() {
        zzab zzb = zzb();
        if (zzb == null || zzb.zzb(this.zzf.zzb()) || zzc.zza() != null) {
            zzj();
        }
    }

    private final synchronized void zzj() {
        if (!this.zzi) {
            zza(0);
        }
    }

    private final synchronized KeyPair zzk() {
        if (this.zzh == null) {
            this.zzh = zzc.zzf("");
        }
        if (this.zzh == null) {
            this.zzh = zzc.zzd("");
        }
        return this.zzh;
    }

    private final boolean zzl() {
        Context applicationContext = this.zze.getApplicationContext();
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("com.google.firebase.messaging", 0);
        if (sharedPreferences.contains("auto_init")) {
            return sharedPreferences.getBoolean("auto_init", true);
        }
        try {
            PackageManager packageManager = applicationContext.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128);
                if (!(applicationInfo == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey("firebase_messaging_auto_init_enabled"))) {
                    return applicationInfo.metaData.getBoolean("firebase_messaging_auto_init_enabled");
                }
            }
        } catch (NameNotFoundException e) {
        }
        return zzm();
    }

    private final boolean zzm() {
        try {
            Class.forName("com.google.firebase.messaging.FirebaseMessaging");
            return true;
        } catch (ClassNotFoundException e) {
            Context applicationContext = this.zze.getApplicationContext();
            Intent intent = new Intent("com.google.firebase.MESSAGING_EVENT");
            intent.setPackage(applicationContext.getPackageName());
            ResolveInfo resolveService = applicationContext.getPackageManager().resolveService(intent, 0);
            return (resolveService == null || resolveService.serviceInfo == null) ? false : true;
        }
    }

    @WorkerThread
    public void deleteInstanceId() throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Bundle bundle = new Bundle();
        bundle.putString("iid-operation", "delete");
        bundle.putString("delete", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        zza("*", "*", bundle);
        zzf();
    }

    @WorkerThread
    public void deleteToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        Bundle bundle = new Bundle();
        bundle.putString("delete", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        zza(str, str2, bundle);
        zzc.zzb("", str, str2);
    }

    public long getCreationTime() {
        return zzc.zzc("");
    }

    @WorkerThread
    public String getId() {
        zzi();
        return zzw.zza(zzk());
    }

    @Nullable
    public String getToken() {
        zzab zzb = zzb();
        if (zzb == null || zzb.zzb(this.zzf.zzb())) {
            zzj();
        }
        return zzb != null ? zzb.zza : null;
    }

    @WorkerThread
    public String getToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        zzab zza = zzc.zza("", str, str2);
        if (zza != null && !zza.zzb(this.zzf.zzb())) {
            return zza.zza;
        }
        String zza2 = zza(str, str2, new Bundle());
        if (zza2 != null) {
            zzc.zza("", str, str2, zza2, this.zzf.zzb());
        }
        return zza2;
    }

    final FirebaseApp zza() {
        return this.zze;
    }

    final synchronized void zza(long j) {
        zza(new zzac(this, this.zzf, Math.min(Math.max(30, j << 1), zza)), j);
        this.zzi = true;
    }

    @Hide
    public final synchronized void zza(String str) {
        zzc.zza(str);
        zzj();
    }

    final synchronized void zza(boolean z) {
        this.zzi = z;
    }

    @Nullable
    final zzab zzb() {
        return zzc.zza("", zzw.zza(this.zze), "*");
    }

    final void zzb(String str) throws IOException {
        zzab zzb = zzb();
        if (zzb != null) {
            if (!zzb.zzb(this.zzf.zzb())) {
                Bundle bundle = new Bundle();
                String str2 = "gcm.topic";
                String valueOf = String.valueOf("/topics/");
                String valueOf2 = String.valueOf(str);
                bundle.putString(str2, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
                String str3 = zzb.zza;
                str2 = String.valueOf("/topics/");
                str = String.valueOf(str);
                zza(str3, str.length() != 0 ? str2.concat(str) : new String(str2), bundle);
                return;
            }
        }
        throw new IOException("token not available");
    }

    @Hide
    public final synchronized void zzb(boolean z) {
        Editor edit = this.zze.getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit();
        edit.putBoolean("auto_init", z);
        edit.apply();
        if (!this.zzj && z) {
            zzi();
        }
        this.zzj = z;
    }

    @Hide
    final String zzc() throws IOException {
        return getToken(zzw.zza(this.zze), "*");
    }

    final void zzc(String str) throws IOException {
        zzab zzb = zzb();
        if (zzb != null) {
            if (!zzb.zzb(this.zzf.zzb())) {
                Bundle bundle = new Bundle();
                String str2 = "gcm.topic";
                String valueOf = String.valueOf("/topics/");
                String valueOf2 = String.valueOf(str);
                bundle.putString(str2, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
                bundle.putString("delete", AppEventsConstants.EVENT_PARAM_VALUE_YES);
                String str3 = zzb.zza;
                str2 = String.valueOf("/topics/");
                str = String.valueOf(str);
                zza(str3, str.length() != 0 ? str2.concat(str) : new String(str2), bundle);
                return;
            }
        }
        throw new IOException("token not available");
    }

    final synchronized void zzf() {
        zzc.zzb();
        this.zzh = null;
        if (zzh()) {
            zzj();
        }
    }

    final void zzg() {
        zzc.zze("");
        zzj();
    }

    @Hide
    public final synchronized boolean zzh() {
        return this.zzj;
    }
}
