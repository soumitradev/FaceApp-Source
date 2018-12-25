package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzfp;
import com.google.android.gms.internal.zzfq;
import io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@KeepForSdkWithMembers
public class AdvertisingIdClient {
    @Nullable
    private com.google.android.gms.common.zza zza;
    @Nullable
    private zzfp zzb;
    private boolean zzc;
    private Object zzd;
    @Nullable
    private zza zze;
    private final Context zzf;
    private boolean zzg;
    private long zzh;

    public static final class Info {
        private final String zza;
        private final boolean zzb;

        public Info(String str, boolean z) {
            this.zza = str;
            this.zzb = z;
        }

        public final String getId() {
            return this.zza;
        }

        public final boolean isLimitAdTrackingEnabled() {
            return this.zzb;
        }

        public final String toString() {
            String str = this.zza;
            boolean z = this.zzb;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 7);
            stringBuilder.append("{");
            stringBuilder.append(str);
            stringBuilder.append("}");
            stringBuilder.append(z);
            return stringBuilder.toString();
        }
    }

    @Hide
    static class zza extends Thread {
        CountDownLatch zza = new CountDownLatch(1);
        boolean zzb = false;
        private WeakReference<AdvertisingIdClient> zzc;
        private long zzd;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzc = new WeakReference(advertisingIdClient);
            this.zzd = j;
            start();
        }

        private final void zza() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient) this.zzc.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzb = true;
            }
        }

        public final void run() {
            try {
                if (!this.zza.await(this.zzd, TimeUnit.MILLISECONDS)) {
                    zza();
                }
            } catch (InterruptedException e) {
                zza();
            }
        }
    }

    @Hide
    public AdvertisingIdClient(Context context) {
        this(context, 30000, false, false);
    }

    @Hide
    public AdvertisingIdClient(Context context, long j, boolean z, boolean z2) {
        this.zzd = new Object();
        zzbq.zza(context);
        if (z) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                context = applicationContext;
            }
        }
        this.zzf = context;
        this.zzc = false;
        this.zzh = j;
        this.zzg = z2;
    }

    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzb zzb = new zzb(context);
        boolean zza = zzb.zza("gads:ad_id_app_context:enabled", false);
        float zza2 = zzb.zza("gads:ad_id_app_context:ping_ratio", 0.0f);
        String zza3 = zzb.zza("gads:ad_id_use_shared_preference:experiment_id", "");
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1, zza, zzb.zza("gads:ad_id_use_persistent_service:enabled", false));
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            advertisingIdClient.zza(false);
            Info info = advertisingIdClient.getInfo();
            advertisingIdClient.zza(info, zza, zza2, SystemClock.elapsedRealtime() - elapsedRealtime, zza3, null);
            advertisingIdClient.finish();
            return info;
        } catch (Throwable th) {
            advertisingIdClient.finish();
        }
    }

    @Hide
    public static boolean getIsAdIdFakeForDebugLogging(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzb zzb = new zzb(context);
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1, zzb.zza("gads:ad_id_app_context:enabled", false), zzb.zza("com.google.android.gms.ads.identifier.service.PERSISTENT_START", false));
        try {
            advertisingIdClient.zza(false);
            boolean isAdIdFakeForDebugLogging = advertisingIdClient.getIsAdIdFakeForDebugLogging();
            return isAdIdFakeForDebugLogging;
        } finally {
            advertisingIdClient.finish();
        }
    }

    @Hide
    public static void setShouldSkipGmsCoreVersionCheck(boolean z) {
    }

    private static com.google.android.gms.common.zza zza(Context context, boolean z) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            int isGooglePlayServicesAvailable = zzf.zza().isGooglePlayServicesAvailable(context);
            if (isGooglePlayServicesAvailable == 0 || isGooglePlayServicesAvailable == 2) {
                String str = z ? "com.google.android.gms.ads.identifier.service.PERSISTENT_START" : AdvertisingInfoServiceStrategy.GOOGLE_PLAY_SERVICES_INTENT;
                Object zza = new com.google.android.gms.common.zza();
                Intent intent = new Intent(str);
                intent.setPackage("com.google.android.gms");
                try {
                    if (com.google.android.gms.common.stats.zza.zza().zza(context, intent, zza, 1)) {
                        return zza;
                    }
                    throw new IOException("Connection failure");
                } catch (Throwable th) {
                    IOException iOException = new IOException(th);
                }
            } else {
                throw new IOException("Google Play services not available");
            }
        } catch (NameNotFoundException e) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    @Hide
    private static zzfp zza(Context context, com.google.android.gms.common.zza zza) throws IOException {
        try {
            return zzfq.zza(zza.zza(10000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            IOException iOException = new IOException(th);
        }
    }

    private final void zza() {
        synchronized (this.zzd) {
            if (this.zze != null) {
                this.zze.zza.countDown();
                try {
                    this.zze.join();
                } catch (InterruptedException e) {
                }
            }
            if (this.zzh > 0) {
                this.zze = new zza(this, this.zzh);
            }
        }
    }

    @Hide
    private final void zza(boolean z) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzbq.zzc("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzc) {
                finish();
            }
            this.zza = zza(this.zzf, this.zzg);
            this.zzb = zza(this.zzf, this.zza);
            this.zzc = true;
            if (z) {
                zza();
            }
        }
    }

    private final boolean zza(Info info, boolean z, float f, long j, String str, Throwable th) {
        if (Math.random() > ((double) f)) {
            return false;
        }
        Map hashMap = new HashMap();
        hashMap.put("app_context", z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        if (info != null) {
            hashMap.put("limit_ad_tracking", info.isLimitAdTrackingEnabled() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        }
        if (!(info == null || info.getId() == null)) {
            hashMap.put("ad_id_size", Integer.toString(info.getId().length()));
        }
        if (th != null) {
            hashMap.put("error", th.getClass().getName());
        }
        if (!(str == null || str.isEmpty())) {
            hashMap.put("experiment_id", str);
        }
        hashMap.put("tag", "AdvertisingIdClient");
        hashMap.put("time_spent", Long.toString(j));
        new zza(this, hashMap).start();
        return true;
    }

    @Hide
    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @com.google.android.gms.common.internal.Hide
    public void finish() {
        /*
        r3 = this;
        r0 = "Calling this from your main thread can lead to deadlock";
        com.google.android.gms.common.internal.zzbq.zzc(r0);
        monitor-enter(r3);
        r0 = r3.zzf;	 Catch:{ all -> 0x0032 }
        if (r0 == 0) goto L_0x0030;
    L_0x000a:
        r0 = r3.zza;	 Catch:{ all -> 0x0032 }
        if (r0 != 0) goto L_0x000f;
    L_0x000e:
        goto L_0x0030;
    L_0x000f:
        r0 = r3.zzc;	 Catch:{ Throwable -> 0x001e }
        if (r0 == 0) goto L_0x0026;
    L_0x0013:
        com.google.android.gms.common.stats.zza.zza();	 Catch:{ Throwable -> 0x001e }
        r0 = r3.zzf;	 Catch:{ Throwable -> 0x001e }
        r1 = r3.zza;	 Catch:{ Throwable -> 0x001e }
        r0.unbindService(r1);	 Catch:{ Throwable -> 0x001e }
        goto L_0x0026;
    L_0x001e:
        r0 = move-exception;
        r1 = "AdvertisingIdClient";
        r2 = "AdvertisingIdClient unbindService failed.";
        android.util.Log.i(r1, r2, r0);	 Catch:{ all -> 0x0032 }
    L_0x0026:
        r0 = 0;
        r3.zzc = r0;	 Catch:{ all -> 0x0032 }
        r0 = 0;
        r3.zzb = r0;	 Catch:{ all -> 0x0032 }
        r3.zza = r0;	 Catch:{ all -> 0x0032 }
        monitor-exit(r3);	 Catch:{ all -> 0x0032 }
        return;
    L_0x0030:
        monitor-exit(r3);	 Catch:{ all -> 0x0032 }
        return;
    L_0x0032:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0032 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.AdvertisingIdClient.finish():void");
    }

    @Hide
    public Info getInfo() throws IOException {
        Info info;
        zzbq.zzc("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzc) {
                synchronized (this.zzd) {
                    if (this.zze != null) {
                        if (this.zze.zzb) {
                        }
                    }
                    throw new IOException("AdvertisingIdClient is not connected.");
                }
                try {
                    zza(false);
                    if (!this.zzc) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (Throwable e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Throwable e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzbq.zza(this.zza);
            zzbq.zza(this.zzb);
            info = new Info(this.zzb.zza(), this.zzb.zza(true));
        }
        zza();
        return info;
    }

    @Hide
    public boolean getIsAdIdFakeForDebugLogging() throws IOException {
        boolean zzb;
        zzbq.zzc("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzc) {
                synchronized (this.zzd) {
                    if (this.zze != null) {
                        if (this.zze.zzb) {
                        }
                    }
                    throw new IOException("AdvertisingIdClient is not connected.");
                }
                try {
                    zza(false);
                    if (!this.zzc) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (Throwable e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Throwable e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzbq.zza(this.zza);
            zzbq.zza(this.zzb);
            zzb = this.zzb.zzb();
        }
        zza();
        return zzb;
    }

    @Hide
    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zza(true);
    }
}
