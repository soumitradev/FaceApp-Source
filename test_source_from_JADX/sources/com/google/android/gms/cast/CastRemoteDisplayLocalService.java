package com.google.android.gms.cast;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.media.MediaRouter;
import android.support.v7.media.MediaRouter.Callback;
import android.text.TextUtils;
import android.view.Display;
import com.google.android.gms.R$drawable;
import com.google.android.gms.R$id;
import com.google.android.gms.R$string;
import com.google.android.gms.cast.CastRemoteDisplay.Configuration;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzbei;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(19)
public abstract class CastRemoteDisplayLocalService extends Service {
    private static final zzbei zza = new zzbei("CastRemoteDisplayLocalService");
    private static final int zzb = R$id.cast_notification_id;
    private static final Object zzc = new Object();
    private static AtomicBoolean zzd = new AtomicBoolean(false);
    private static CastRemoteDisplayLocalService zzu;
    private String zze;
    private WeakReference<Callbacks> zzf;
    private zzb zzg;
    private NotificationSettings zzh;
    private Notification zzi;
    private boolean zzj;
    private PendingIntent zzk;
    private CastDevice zzl;
    private Display zzm;
    private Context zzn;
    private ServiceConnection zzo;
    private Handler zzp;
    private MediaRouter zzq;
    private boolean zzr = false;
    private CastRemoteDisplayClient zzs;
    private final Callback zzt = new zzu(this);
    private final IBinder zzv = new zza(this);

    public interface Callbacks {
        void onRemoteDisplaySessionEnded(CastRemoteDisplayLocalService castRemoteDisplayLocalService);

        void onRemoteDisplaySessionError(Status status);

        void onRemoteDisplaySessionStarted(CastRemoteDisplayLocalService castRemoteDisplayLocalService);

        void onServiceCreated(CastRemoteDisplayLocalService castRemoteDisplayLocalService);
    }

    public static final class NotificationSettings {
        private Notification zza;
        private PendingIntent zzb;
        private String zzc;
        private String zzd;

        public static final class Builder {
            private NotificationSettings zza = new NotificationSettings();

            public final NotificationSettings build() {
                if (this.zza.zza != null) {
                    if (!TextUtils.isEmpty(this.zza.zzc)) {
                        throw new IllegalArgumentException("notificationTitle requires using the default notification");
                    } else if (!TextUtils.isEmpty(this.zza.zzd)) {
                        throw new IllegalArgumentException("notificationText requires using the default notification");
                    } else if (this.zza.zzb != null) {
                        throw new IllegalArgumentException("notificationPendingIntent requires using the default notification");
                    }
                } else if (TextUtils.isEmpty(this.zza.zzc) && TextUtils.isEmpty(this.zza.zzd) && this.zza.zzb == null) {
                    throw new IllegalArgumentException("At least an argument must be provided");
                }
                return this.zza;
            }

            public final Builder setNotification(Notification notification) {
                this.zza.zza = notification;
                return this;
            }

            public final Builder setNotificationPendingIntent(PendingIntent pendingIntent) {
                this.zza.zzb = pendingIntent;
                return this;
            }

            public final Builder setNotificationText(String str) {
                this.zza.zzd = str;
                return this;
            }

            public final Builder setNotificationTitle(String str) {
                this.zza.zzc = str;
                return this;
            }
        }

        private NotificationSettings() {
        }

        private NotificationSettings(NotificationSettings notificationSettings) {
            this.zza = notificationSettings.zza;
            this.zzb = notificationSettings.zzb;
            this.zzc = notificationSettings.zzc;
            this.zzd = notificationSettings.zzd;
        }
    }

    public static class Options {
        @Configuration
        private int zza = 2;

        @Configuration
        public int getConfigPreset() {
            return this.zza;
        }

        public void setConfigPreset(@Configuration int i) {
            this.zza = i;
        }
    }

    @VisibleForTesting
    class zza extends Binder {
        final /* synthetic */ CastRemoteDisplayLocalService zza;

        zza(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
            this.zza = castRemoteDisplayLocalService;
        }
    }

    static final class zzb extends BroadcastReceiver {
        private zzb() {
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT")) {
                CastRemoteDisplayLocalService.stopService();
                return;
            }
            if (intent.getAction().equals("com.google.android.gms.cast.remote_display.ACTION_SESSION_ENDED")) {
                CastRemoteDisplayLocalService.zzc(false);
            }
        }
    }

    public static CastRemoteDisplayLocalService getInstance() {
        CastRemoteDisplayLocalService castRemoteDisplayLocalService;
        synchronized (zzc) {
            castRemoteDisplayLocalService = zzu;
        }
        return castRemoteDisplayLocalService;
    }

    protected static void setDebugEnabled() {
        zza.zza(true);
    }

    public static void startService(Context context, Class<? extends CastRemoteDisplayLocalService> cls, String str, CastDevice castDevice, NotificationSettings notificationSettings, Callbacks callbacks) {
        startServiceWithOptions(context, cls, str, castDevice, new Options(), notificationSettings, callbacks);
    }

    public static void startServiceWithOptions(@NonNull Context context, @NonNull Class<? extends CastRemoteDisplayLocalService> cls, @NonNull String str, @NonNull CastDevice castDevice, @NonNull Options options, @NonNull NotificationSettings notificationSettings, @NonNull Callbacks callbacks) {
        zza.zza("Starting Service", new Object[0]);
        synchronized (zzc) {
            if (zzu != null) {
                zza.zzc("An existing service had not been stopped before starting one", new Object[0]);
                zzc(true);
            }
        }
        try {
            ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context, cls), 128);
            if (serviceInfo == null || !serviceInfo.exported) {
                zzbq.zza(context, "activityContext is required.");
                zzbq.zza(cls, "serviceClass is required.");
                zzbq.zza(str, "applicationId is required.");
                zzbq.zza(castDevice, "device is required.");
                zzbq.zza(options, "options is required.");
                zzbq.zza(notificationSettings, "notificationSettings is required.");
                zzbq.zza(callbacks, "callbacks is required.");
                if (notificationSettings.zza == null && notificationSettings.zzb == null) {
                    throw new IllegalArgumentException("notificationSettings: Either the notification or the notificationPendingIntent must be provided");
                } else if (zzd.getAndSet(true)) {
                    zza.zzd("Service is already being started, startService has been called twice", new Object[0]);
                    return;
                } else {
                    Intent intent = new Intent(context, cls);
                    context.startService(intent);
                    context.bindService(intent, new zzw(str, castDevice, options, notificationSettings, context, callbacks), 64);
                    return;
                }
            }
            throw new IllegalStateException("The service must not be exported, verify the manifest configuration");
        } catch (NameNotFoundException e) {
            throw new IllegalStateException("Service not found, did you forget to configure it in the manifest?");
        }
    }

    public static void stopService() {
        zzc(false);
    }

    private final void zza(Display display) {
        this.zzm = display;
        if (this.zzj) {
            this.zzi = zzd(true);
            startForeground(zzb, this.zzi);
        }
        if (this.zzf.get() != null) {
            ((Callbacks) this.zzf.get()).onRemoteDisplaySessionStarted(this);
        }
        onCreatePresentation(this.zzm);
    }

    private final void zza(NotificationSettings notificationSettings) {
        zzbq.zzb("updateNotificationSettingsInternal must be called on the main thread");
        if (this.zzh == null) {
            throw new IllegalStateException("No current notification settings to update");
        }
        if (!this.zzj) {
            zzbq.zza(notificationSettings.zza, "notification is required.");
            this.zzi = notificationSettings.zza;
            this.zzh.zza = this.zzi;
        } else if (notificationSettings.zza != null) {
            throw new IllegalStateException("Current mode is default notification, notification attribute must not be provided");
        } else {
            if (notificationSettings.zzb != null) {
                this.zzh.zzb = notificationSettings.zzb;
            }
            if (!TextUtils.isEmpty(notificationSettings.zzc)) {
                this.zzh.zzc = notificationSettings.zzc;
            }
            if (!TextUtils.isEmpty(notificationSettings.zzd)) {
                this.zzh.zzd = notificationSettings.zzd;
            }
            this.zzi = zzd(true);
        }
        startForeground(zzb, this.zzi);
    }

    private final void zza(String str) {
        zza.zza("[Instance: %s] %s", new Object[]{this, str});
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zza(java.lang.String r4, com.google.android.gms.cast.CastDevice r5, com.google.android.gms.cast.CastRemoteDisplayLocalService.Options r6, com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings r7, android.content.Context r8, android.content.ServiceConnection r9, com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks r10) {
        /*
        r3 = this;
        r0 = "startRemoteDisplaySession";
        r3.zza(r0);
        r0 = "Starting the Cast Remote Display must be done on the main thread";
        com.google.android.gms.common.internal.zzbq.zzb(r0);
        r0 = zzc;
        monitor-enter(r0);
        r1 = zzu;	 Catch:{ all -> 0x00e9 }
        r2 = 0;
        if (r1 == 0) goto L_0x001d;
    L_0x0012:
        r4 = zza;	 Catch:{ all -> 0x00e9 }
        r5 = "An existing service had not been stopped before starting one";
        r6 = new java.lang.Object[r2];	 Catch:{ all -> 0x00e9 }
        r4.zzc(r5, r6);	 Catch:{ all -> 0x00e9 }
        monitor-exit(r0);	 Catch:{ all -> 0x00e9 }
        return r2;
    L_0x001d:
        zzu = r3;	 Catch:{ all -> 0x00e9 }
        monitor-exit(r0);	 Catch:{ all -> 0x00e9 }
        r0 = new java.lang.ref.WeakReference;
        r0.<init>(r10);
        r3.zzf = r0;
        r3.zze = r4;
        r3.zzl = r5;
        r3.zzn = r8;
        r3.zzo = r9;
        r4 = r3.zzq;
        if (r4 != 0) goto L_0x003d;
    L_0x0033:
        r4 = r3.getApplicationContext();
        r4 = android.support.v7.media.MediaRouter.getInstance(r4);
        r3.zzq = r4;
    L_0x003d:
        r4 = new android.support.v7.media.MediaRouteSelector$Builder;
        r4.<init>();
        r8 = r3.zze;
        r8 = com.google.android.gms.cast.CastMediaControlIntent.categoryForCast(r8);
        r4 = r4.addControlCategory(r8);
        r4 = r4.build();
        r8 = "addMediaRouterCallback";
        r3.zza(r8);
        r8 = r3.zzq;
        r9 = r3.zzt;
        r10 = 4;
        r8.addCallback(r4, r9, r10);
        r4 = r7.zza;
        r3.zzi = r4;
        r4 = new com.google.android.gms.cast.CastRemoteDisplayLocalService$zzb;
        r8 = 0;
        r4.<init>();
        r3.zzg = r4;
        r4 = new android.content.IntentFilter;
        r4.<init>();
        r9 = "com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT";
        r4.addAction(r9);
        r9 = "com.google.android.gms.cast.remote_display.ACTION_SESSION_ENDED";
        r4.addAction(r9);
        r9 = r3.zzg;
        r3.registerReceiver(r9, r4);
        r4 = new com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings;
        r4.<init>(r7);
        r3.zzh = r4;
        r4 = r3.zzh;
        r4 = r4.zza;
        r7 = 1;
        if (r4 != 0) goto L_0x0098;
    L_0x008f:
        r3.zzj = r7;
        r4 = r3.zzd(r2);
    L_0x0095:
        r3.zzi = r4;
        goto L_0x00a1;
    L_0x0098:
        r3.zzj = r2;
        r4 = r3.zzh;
        r4 = r4.zza;
        goto L_0x0095;
    L_0x00a1:
        r4 = zzb;
        r8 = r3.zzi;
        r3.startForeground(r4, r8);
        r4 = "startRemoteDisplay";
        r3.zza(r4);
        r4 = new android.content.Intent;
        r8 = "com.google.android.gms.cast.remote_display.ACTION_SESSION_ENDED";
        r4.<init>(r8);
        r8 = r3.zzn;
        r8 = r8.getPackageName();
        r4.setPackage(r8);
        r4 = android.app.PendingIntent.getBroadcast(r3, r2, r4, r2);
        r8 = r3.zzs;
        r9 = r3.zze;
        r6 = r6.getConfigPreset();
        r4 = r8.startRemoteDisplay(r5, r9, r6, r4);
        r5 = new com.google.android.gms.cast.zzz;
        r5.<init>(r3);
        r4.addOnCompleteListener(r5);
        r4 = r3.zzf;
        r4 = r4.get();
        if (r4 == 0) goto L_0x00e8;
    L_0x00dd:
        r4 = r3.zzf;
        r4 = r4.get();
        r4 = (com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks) r4;
        r4.onServiceCreated(r3);
    L_0x00e8:
        return r7;
    L_0x00e9:
        r4 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x00e9 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastRemoteDisplayLocalService.zza(java.lang.String, com.google.android.gms.cast.CastDevice, com.google.android.gms.cast.CastRemoteDisplayLocalService$Options, com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings, android.content.Context, android.content.ServiceConnection, com.google.android.gms.cast.CastRemoteDisplayLocalService$Callbacks):boolean");
    }

    private final void zzb(String str) {
        zza.zzd("[Instance: %s] %s", new Object[]{this, str});
    }

    private final void zzb(boolean z) {
        zza("Stopping Service");
        zzbq.zzb("stopServiceInstanceInternal must be called on the main thread");
        if (!(z || this.zzq == null)) {
            zza("Setting default route");
            this.zzq.selectRoute(this.zzq.getDefaultRoute());
        }
        if (this.zzg != null) {
            zza("Unregistering notification receiver");
            unregisterReceiver(this.zzg);
        }
        zza("stopRemoteDisplaySession");
        zza("stopRemoteDisplay");
        this.zzs.stopRemoteDisplay().addOnCompleteListener(new zzaa(this));
        if (this.zzf.get() != null) {
            ((Callbacks) this.zzf.get()).onRemoteDisplaySessionEnded(this);
        }
        onDismissPresentation();
        zza("Stopping the remote display Service");
        stopForeground(true);
        stopSelf();
        if (this.zzq != null) {
            zzbq.zzb("CastRemoteDisplayLocalService calls must be done on the main thread");
            zza("removeMediaRouterCallback");
            this.zzq.removeCallback(this.zzt);
        }
        if (!(this.zzn == null || this.zzo == null)) {
            try {
                this.zzn.unbindService(this.zzo);
            } catch (IllegalArgumentException e) {
                zza("No need to unbind service, already unbound");
            }
            this.zzo = null;
            this.zzn = null;
        }
        this.zze = null;
        this.zzi = null;
        this.zzm = null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zzc(boolean r4) {
        /*
        r0 = zza;
        r1 = "Stopping Service";
        r2 = 0;
        r3 = new java.lang.Object[r2];
        r0.zza(r1, r3);
        r0 = zzd;
        r0.set(r2);
        r0 = zzc;
        monitor-enter(r0);
        r1 = zzu;	 Catch:{ all -> 0x0044 }
        if (r1 != 0) goto L_0x0021;
    L_0x0016:
        r4 = zza;	 Catch:{ all -> 0x0044 }
        r1 = "Service is already being stopped";
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x0044 }
        r4.zzd(r1, r2);	 Catch:{ all -> 0x0044 }
        monitor-exit(r0);	 Catch:{ all -> 0x0044 }
        return;
    L_0x0021:
        r1 = zzu;	 Catch:{ all -> 0x0044 }
        r2 = 0;
        zzu = r2;	 Catch:{ all -> 0x0044 }
        monitor-exit(r0);	 Catch:{ all -> 0x0044 }
        r0 = r1.zzp;
        if (r0 == 0) goto L_0x0043;
    L_0x002b:
        r0 = android.os.Looper.myLooper();
        r2 = android.os.Looper.getMainLooper();
        if (r0 == r2) goto L_0x0040;
    L_0x0035:
        r0 = r1.zzp;
        r2 = new com.google.android.gms.cast.zzx;
        r2.<init>(r1, r4);
        r0.post(r2);
        return;
    L_0x0040:
        r1.zzb(r4);
    L_0x0043:
        return;
    L_0x0044:
        r4 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0044 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastRemoteDisplayLocalService.zzc(boolean):void");
    }

    private final Notification zzd(boolean z) {
        int i;
        int i2;
        zza("createDefaultNotification");
        CharSequence zzb = this.zzh.zzc;
        CharSequence zzc = this.zzh.zzd;
        if (z) {
            i = R$string.cast_notification_connected_message;
            i2 = R$drawable.cast_ic_notification_on;
        } else {
            i = R$string.cast_notification_connecting_message;
            i2 = R$drawable.cast_ic_notification_connecting;
        }
        if (TextUtils.isEmpty(zzb)) {
            zzb = (String) getPackageManager().getApplicationLabel(getApplicationInfo());
        }
        if (TextUtils.isEmpty(zzc)) {
            zzc = getString(i, new Object[]{this.zzl.getFriendlyName()});
        }
        Builder ongoing = new Builder(this, "cast_remote_display_local_service").setContentTitle(zzb).setContentText(zzc).setContentIntent(this.zzh.zzb).setSmallIcon(i2).setOngoing(true);
        zzc = getString(R$string.cast_notification_disconnect);
        if (this.zzk == null) {
            Intent intent = new Intent("com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT");
            intent.setPackage(this.zzn.getPackageName());
            this.zzk = PendingIntent.getBroadcast(this, 0, intent, 134217728);
        }
        return ongoing.addAction(17301560, zzc, this.zzk).build();
    }

    private final void zze() {
        if (this.zzf.get() != null) {
            ((Callbacks) this.zzf.get()).onRemoteDisplaySessionError(new Status(CastStatusCodes.ERROR_SERVICE_CREATION_FAILED));
        }
        stopService();
    }

    protected Display getDisplay() {
        return this.zzm;
    }

    public IBinder onBind(Intent intent) {
        zza("onBind");
        return this.zzv;
    }

    public void onCreate() {
        zza("onCreate");
        super.onCreate();
        this.zzp = new Handler(getMainLooper());
        this.zzp.postDelayed(new zzv(this), 100);
        if (this.zzs == null) {
            this.zzs = CastRemoteDisplay.getClient(this);
        }
        if (zzs.zzi()) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            NotificationChannel notificationChannel = new NotificationChannel("cast_remote_display_local_service", getString(R$string.cast_notification_default_channel_name), 2);
            notificationChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public abstract void onCreatePresentation(Display display);

    public abstract void onDismissPresentation();

    public int onStartCommand(Intent intent, int i, int i2) {
        zza("onStartCommand");
        this.zzr = true;
        return 2;
    }

    public void updateNotificationSettings(NotificationSettings notificationSettings) {
        zzbq.zza(notificationSettings, "notificationSettings is required.");
        zzbq.zza(this.zzp, "Service is not ready yet.");
        this.zzp.post(new zzy(this, notificationSettings));
    }
}
