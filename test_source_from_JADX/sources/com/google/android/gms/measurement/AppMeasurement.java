package com.google.android.gms.measurement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzckj;
import com.google.android.gms.internal.zzclz;
import com.google.android.gms.internal.zzcnl;
import com.google.android.gms.internal.zzcno;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.List;
import java.util.Map;

@Keep
@Deprecated
public class AppMeasurement {
    @Hide
    @KeepForSdk
    public static final String CRASH_ORIGIN = "crash";
    @Hide
    @KeepForSdk
    public static final String FCM_ORIGIN = "fcm";
    private final zzckj zza;

    @Hide
    public AppMeasurement(zzckj zzckj) {
        zzbq.zza((Object) zzckj);
        this.zza = zzckj;
    }

    @Keep
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    @Deprecated
    public static AppMeasurement getInstance(Context context) {
        return zzckj.zza(context).zzm();
    }

    @Keep
    @Hide
    public void beginAdUnitExposure(@Size(min = 1) @NonNull String str) {
        this.zza.zzaa().zza(str);
    }

    @Keep
    @Hide
    protected void clearConditionalUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        this.zza.zzl().zzb(str, str2, bundle);
    }

    @Keep
    @Hide
    protected void clearConditionalUserPropertyAs(@Size(min = 1) @NonNull String str, @Size(max = 24, min = 1) @NonNull String str2, @Nullable String str3, @Nullable Bundle bundle) {
        this.zza.zzl().zza(str, str2, str3, bundle);
    }

    @Keep
    @Hide
    public void endAdUnitExposure(@Size(min = 1) @NonNull String str) {
        this.zza.zzaa().zzb(str);
    }

    @Keep
    @Hide
    public long generateEventId() {
        return this.zza.zzo().zzy();
    }

    @Keep
    @Nullable
    @Hide
    public String getAppInstanceId() {
        return this.zza.zzl().zzz();
    }

    @Keep
    @WorkerThread
    @Hide
    protected List<AppMeasurement$ConditionalUserProperty> getConditionalUserProperties(@Nullable String str, @Nullable @Size(max = 23, min = 1) String str2) {
        return this.zza.zzl().zza(str, str2);
    }

    @Keep
    @WorkerThread
    @Hide
    protected List<AppMeasurement$ConditionalUserProperty> getConditionalUserPropertiesAs(@Size(min = 1) @NonNull String str, @Nullable String str2, @Nullable @Size(max = 23, min = 1) String str3) {
        return this.zza.zzl().zza(str, str2, str3);
    }

    @Keep
    @Nullable
    @Hide
    public String getCurrentScreenClass() {
        zzclz zzz = this.zza.zzv().zzz();
        return zzz != null ? zzz.zzb : null;
    }

    @Keep
    @Nullable
    @Hide
    public String getCurrentScreenName() {
        zzclz zzz = this.zza.zzv().zzz();
        return zzz != null ? zzz.zza : null;
    }

    @Keep
    @Nullable
    @Hide
    public String getGmpAppId() {
        try {
            return zzbz.zza();
        } catch (IllegalStateException e) {
            this.zza.zzf().zzy().zza("getGoogleAppId failed with exception", e);
            return null;
        }
    }

    @Keep
    @WorkerThread
    @Hide
    protected int getMaxUserProperties(@Size(min = 1) @NonNull String str) {
        this.zza.zzl();
        zzbq.zza(str);
        return 25;
    }

    @Keep
    @WorkerThread
    @Hide
    protected Map<String, Object> getUserProperties(@Nullable String str, @Nullable @Size(max = 24, min = 1) String str2, boolean z) {
        return this.zza.zzl().zza(str, str2, z);
    }

    @WorkerThread
    @Hide
    @KeepForSdk
    public Map<String, Object> getUserProperties(boolean z) {
        List<zzcnl> zzb = this.zza.zzl().zzb(z);
        Map<String, Object> arrayMap = new ArrayMap(zzb.size());
        for (zzcnl zzcnl : zzb) {
            arrayMap.put(zzcnl.zza, zzcnl.zza());
        }
        return arrayMap;
    }

    @Keep
    @WorkerThread
    @Hide
    protected Map<String, Object> getUserPropertiesAs(@Size(min = 1) @NonNull String str, @Nullable String str2, @Nullable @Size(max = 23, min = 1) String str3, boolean z) {
        return this.zza.zzl().zza(str, str2, str3, z);
    }

    @Hide
    @KeepForSdk
    public void logEvent(@Size(max = 40, min = 1) @NonNull String str, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zza.zzl().zza(SettingsJsonConstants.APP_KEY, str, bundle, true);
    }

    @Keep
    @Hide
    public void logEventInternal(String str, String str2, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zza.zzl().zza(str, str2, bundle);
    }

    @Hide
    @KeepForSdk
    public void logEventInternalNoInterceptor(String str, String str2, Bundle bundle, long j) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zza.zzl().zza(str, str2, bundle, j);
    }

    @Hide
    @KeepForSdk
    public void registerOnMeasurementEventListener(AppMeasurement$OnEventListener appMeasurement$OnEventListener) {
        this.zza.zzl().zza(appMeasurement$OnEventListener);
    }

    @Keep
    @Hide
    public void registerOnScreenChangeCallback(@NonNull AppMeasurement$zza appMeasurement$zza) {
        this.zza.zzv().zza(appMeasurement$zza);
    }

    @Keep
    @Hide
    protected void setConditionalUserProperty(@NonNull AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty) {
        this.zza.zzl().zza(appMeasurement$ConditionalUserProperty);
    }

    @Keep
    @Hide
    protected void setConditionalUserPropertyAs(@NonNull AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty) {
        this.zza.zzl().zzb(appMeasurement$ConditionalUserProperty);
    }

    @WorkerThread
    @Hide
    @KeepForSdk
    public void setEventInterceptor(AppMeasurement$EventInterceptor appMeasurement$EventInterceptor) {
        this.zza.zzl().zza(appMeasurement$EventInterceptor);
    }

    @Deprecated
    public void setMeasurementEnabled(boolean z) {
        this.zza.zzl().zza(z);
    }

    @Hide
    @KeepForSdk
    public void setMinimumSessionDuration(long j) {
        this.zza.zzl().zza(j);
    }

    @Hide
    @KeepForSdk
    public void setSessionTimeoutDuration(long j) {
        this.zza.zzl().zzb(j);
    }

    @Hide
    @KeepForSdk
    public void setUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Nullable @Size(max = 36) String str2) {
        int zzc = this.zza.zzo().zzc(str);
        if (zzc != 0) {
            this.zza.zzo();
            this.zza.zzo().zza(zzc, "_ev", zzcno.zza(str, 24, true), str != null ? str.length() : 0);
            return;
        }
        setUserPropertyInternal(SettingsJsonConstants.APP_KEY, str, str2);
    }

    @Hide
    @KeepForSdk
    public void setUserPropertyInternal(String str, String str2, Object obj) {
        this.zza.zzl().zza(str, str2, obj);
    }

    @Hide
    @KeepForSdk
    public void unregisterOnMeasurementEventListener(AppMeasurement$OnEventListener appMeasurement$OnEventListener) {
        this.zza.zzl().zzb(appMeasurement$OnEventListener);
    }

    @Keep
    @Hide
    public void unregisterOnScreenChangeCallback(@NonNull AppMeasurement$zza appMeasurement$zza) {
        this.zza.zzv().zzb(appMeasurement$zza);
    }
}
