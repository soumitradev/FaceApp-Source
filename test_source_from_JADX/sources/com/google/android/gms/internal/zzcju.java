package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.math.BigInteger;
import java.util.Locale;

final class zzcju extends zzcli {
    static final Pair<String, Long> zza = new Pair("", Long.valueOf(0));
    public zzcjy zzb;
    public final zzcjx zzc = new zzcjx(this, "last_upload", 0);
    public final zzcjx zzd = new zzcjx(this, "last_upload_attempt", 0);
    public final zzcjx zze = new zzcjx(this, "backoff", 0);
    public final zzcjx zzf = new zzcjx(this, "last_delete_stale", 0);
    public final zzcjx zzg = new zzcjx(this, "midnight_offset", 0);
    public final zzcjx zzh = new zzcjx(this, "first_open_time", 0);
    public final zzcjz zzi = new zzcjz(this, "app_instance_id", null);
    public final zzcjx zzj = new zzcjx(this, "time_before_start", 10000);
    public final zzcjx zzk = new zzcjx(this, "session_timeout", 1800000);
    public final zzcjw zzl = new zzcjw(this, "start_new_session", true);
    public final zzcjx zzm = new zzcjx(this, "last_pause_time", 0);
    public final zzcjx zzn = new zzcjx(this, "time_active", 0);
    public boolean zzo;
    private SharedPreferences zzq;
    private String zzr;
    private boolean zzs;
    private long zzt;
    private String zzu;
    private long zzv;
    private final Object zzw = new Object();

    zzcju(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final SharedPreferences zzad() {
        zzc();
        zzaq();
        return this.zzq;
    }

    @WorkerThread
    protected final void p_() {
        this.zzq = zzl().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzo = this.zzq.getBoolean("has_been_opened", false);
        if (!this.zzo) {
            Editor edit = this.zzq.edit();
            edit.putBoolean("has_been_opened", true);
            edit.apply();
        }
        this.zzb = new zzcjy(this, "health_monitor", Math.max(0, ((Long) zzciz.zzi.zzb()).longValue()));
    }

    @WorkerThread
    @NonNull
    final Pair<String, Boolean> zza(String str) {
        zzc();
        long zzb = zzk().zzb();
        if (this.zzr != null && zzb < this.zzt) {
            return new Pair(this.zzr, Boolean.valueOf(this.zzs));
        }
        this.zzt = zzb + zzv().zza(str, zzciz.zzh);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(zzl());
            if (advertisingIdInfo != null) {
                this.zzr = advertisingIdInfo.getId();
                this.zzs = advertisingIdInfo.isLimitAdTrackingEnabled();
            }
            if (this.zzr == null) {
                this.zzr = "";
            }
        } catch (Throwable th) {
            zzt().zzad().zza("Unable to get advertising id", th);
            this.zzr = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair(this.zzr, Boolean.valueOf(this.zzs));
    }

    @WorkerThread
    final void zza(boolean z) {
        zzc();
        zzt().zzae().zza("Setting useService", Boolean.valueOf(z));
        Editor edit = zzad().edit();
        edit.putBoolean("use_service", z);
        edit.apply();
    }

    @WorkerThread
    final Boolean zzaa() {
        zzc();
        return !zzad().contains("use_service") ? null : Boolean.valueOf(zzad().getBoolean("use_service", false));
    }

    @WorkerThread
    final void zzab() {
        zzc();
        zzt().zzae().zza("Clearing collection preferences.");
        boolean contains = zzad().contains("measurement_enabled");
        boolean z = true;
        if (contains) {
            z = zzc(true);
        }
        Editor edit = zzad().edit();
        edit.clear();
        edit.apply();
        if (contains) {
            zzb(z);
        }
    }

    @WorkerThread
    protected final String zzac() {
        zzc();
        String string = zzad().getString("previous_os_version", null);
        zzh().zzaq();
        String str = VERSION.RELEASE;
        if (!(TextUtils.isEmpty(str) || str.equals(string))) {
            Editor edit = zzad().edit();
            edit.putString("previous_os_version", str);
            edit.apply();
        }
        return string;
    }

    @WorkerThread
    final String zzb(String str) {
        zzc();
        str = (String) zza(str).first;
        if (zzcno.zzf(CommonUtils.MD5_INSTANCE) == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzcno.zzf(CommonUtils.MD5_INSTANCE).digest(str.getBytes()))});
    }

    @WorkerThread
    final void zzb(boolean z) {
        zzc();
        zzt().zzae().zza("Setting measurementEnabled", Boolean.valueOf(z));
        Editor edit = zzad().edit();
        edit.putBoolean("measurement_enabled", z);
        edit.apply();
    }

    @WorkerThread
    final void zzc(String str) {
        zzc();
        Editor edit = zzad().edit();
        edit.putString("gmp_app_id", str);
        edit.apply();
    }

    @WorkerThread
    final boolean zzc(boolean z) {
        zzc();
        return zzad().getBoolean("measurement_enabled", z);
    }

    final void zzd(String str) {
        synchronized (this.zzw) {
            this.zzu = str;
            this.zzv = zzk().zzb();
        }
    }

    protected final boolean zzw() {
        return true;
    }

    @WorkerThread
    final String zzy() {
        zzc();
        return zzad().getString("gmp_app_id", null);
    }

    final String zzz() {
        synchronized (this.zzw) {
            if (Math.abs(zzk().zzb() - this.zzv) < 1000) {
                String str = this.zzu;
                return str;
            }
            return null;
        }
    }
}
