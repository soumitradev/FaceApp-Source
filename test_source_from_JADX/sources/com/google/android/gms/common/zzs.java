package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.R$string;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbf;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzj;
import com.google.android.gms.common.util.zzz;
import com.google.android.gms.internal.zzbih;
import com.parrot.freeflight.utils.ThumbnailUtils;
import java.util.concurrent.atomic.AtomicBoolean;

@Hide
public class zzs {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 12210000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    static final AtomicBoolean zza = new AtomicBoolean();
    @Hide
    private static boolean zzb = false;
    @Hide
    private static boolean zzc = false;
    private static boolean zzd = false;
    private static boolean zze = false;
    private static final AtomicBoolean zzf = new AtomicBoolean();

    zzs() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return zzf.zza().getErrorResolutionPendingIntent(context, i, i2);
    }

    @Deprecated
    public static String getErrorString(int i) {
        return ConnectionResult.zza(i);
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext("com.google.android.gms", 3);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return zza(context, -1);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        if (i != 9) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    @Deprecated
    public static int zza(Context context, int i) {
        try {
            context.getResources().getString(R$string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!("com.google.android.gms".equals(context.getPackageName()) || zzf.get())) {
            int zzb = zzbf.zzb(context);
            if (zzb == 0) {
                throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            } else if (zzb != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                i = GOOGLE_PLAY_SERVICES_VERSION_CODE;
                StringBuilder stringBuilder = new StringBuilder(ThumbnailUtils.TARGET_SIZE_MINI_THUMBNAIL);
                stringBuilder.append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ");
                stringBuilder.append(i);
                stringBuilder.append(" but found ");
                stringBuilder.append(zzb);
                stringBuilder.append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        boolean z = (zzj.zzb(context) || zzj.zzd(context)) ? false : true;
        return zza(context, z, GOOGLE_PLAY_SERVICES_VERSION_CODE, i);
    }

    private static int zza(Context context, boolean z, int i, int i2) {
        boolean z2;
        PackageManager packageManager;
        PackageInfo packageInfo;
        String str;
        String str2;
        PackageInfo packageInfo2;
        int i3;
        ApplicationInfo applicationInfo;
        if (i2 != -1) {
            if (i2 < 0) {
                z2 = false;
                zzbq.zzb(z2);
                packageManager = context.getPackageManager();
                packageInfo = null;
                if (z) {
                    try {
                        packageInfo = packageManager.getPackageInfo("com.android.vending", 8256);
                    } catch (NameNotFoundException e) {
                        str = "GooglePlayServicesUtil";
                        str2 = "Google Play Store is missing.";
                    }
                }
                packageInfo2 = packageManager.getPackageInfo("com.google.android.gms", 64);
                zzt.zza(context);
                if (zzt.zza(packageInfo2, true)) {
                    str = "GooglePlayServicesUtil";
                    str2 = "Google Play services signature invalid.";
                } else if (z || (zzt.zza(r4, true) && r4.signatures[0].equals(packageInfo2.signatures[0]))) {
                    i3 = packageInfo2.versionCode / 1000;
                    if (i3 < i / 1000 || (i2 != -1 && i3 >= i2 / 1000)) {
                        applicationInfo = packageInfo2.applicationInfo;
                        if (applicationInfo == null) {
                            try {
                                applicationInfo = packageManager.getApplicationInfo("com.google.android.gms", 0);
                            } catch (Throwable e2) {
                                Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", e2);
                                return 1;
                            }
                        }
                        return applicationInfo.enabled ? 3 : 0;
                    } else {
                        int i4 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
                        i = packageInfo2.versionCode;
                        StringBuilder stringBuilder = new StringBuilder(77);
                        stringBuilder.append("Google Play services out of date.  Requires ");
                        stringBuilder.append(i4);
                        stringBuilder.append(" but found ");
                        stringBuilder.append(i);
                        Log.w("GooglePlayServicesUtil", stringBuilder.toString());
                        return 2;
                    }
                } else {
                    str = "GooglePlayServicesUtil";
                    str2 = "Google Play Store signature invalid.";
                }
                Log.w(str, str2);
                return 9;
            }
        }
        z2 = true;
        zzbq.zzb(z2);
        packageManager = context.getPackageManager();
        packageInfo = null;
        if (z) {
            packageInfo = packageManager.getPackageInfo("com.android.vending", 8256);
        }
        try {
            packageInfo2 = packageManager.getPackageInfo("com.google.android.gms", 64);
            zzt.zza(context);
            if (zzt.zza(packageInfo2, true)) {
                if (z) {
                }
                i3 = packageInfo2.versionCode / 1000;
                if (i3 < i / 1000) {
                }
                applicationInfo = packageInfo2.applicationInfo;
                if (applicationInfo == null) {
                    applicationInfo = packageManager.getApplicationInfo("com.google.android.gms", 0);
                }
                if (applicationInfo.enabled) {
                }
            }
            str = "GooglePlayServicesUtil";
            str2 = "Google Play services signature invalid.";
            Log.w(str, str2);
            return 9;
        } catch (NameNotFoundException e3) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }

    @Hide
    @Deprecated
    public static void zza(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzf.zza();
        int zza = zzf.zza(context, -1);
        if (zza != 0) {
            zzf.zza();
            Intent zza2 = zzf.zza(context, zza, "e");
            StringBuilder stringBuilder = new StringBuilder(57);
            stringBuilder.append("GooglePlayServices not available due to error ");
            stringBuilder.append(zza);
            Log.e("GooglePlayServicesUtil", stringBuilder.toString());
            if (zza2 == null) {
                throw new GooglePlayServicesNotAvailableException(zza);
            }
            throw new GooglePlayServicesRepairableException(zza, "Google Play Services not available", zza2);
        }
    }

    @Hide
    @TargetApi(19)
    @Deprecated
    public static boolean zza(Context context, int i, String str) {
        return zzz.zza(context, i, str);
    }

    @TargetApi(21)
    static boolean zza(Context context, String str) {
        boolean equals = str.equals("com.google.android.gms");
        if (com.google.android.gms.common.util.zzs.zzg()) {
            try {
                for (SessionInfo appPackageName : context.getPackageManager().getPackageInstaller().getAllSessions()) {
                    if (str.equals(appPackageName.getAppPackageName())) {
                        return true;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 8192);
            if (equals) {
                return applicationInfo.enabled;
            }
            if (applicationInfo.enabled) {
                Object obj;
                if (com.google.android.gms.common.util.zzs.zzd()) {
                    Bundle applicationRestrictions = ((UserManager) context.getSystemService("user")).getApplicationRestrictions(context.getPackageName());
                    if (applicationRestrictions != null && ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(applicationRestrictions.getString("restricted_profile"))) {
                        obj = 1;
                        if (obj == null) {
                        }
                    }
                }
                obj = null;
                return obj == null;
            }
        } catch (NameNotFoundException e2) {
            return false;
        }
    }

    @Hide
    public static boolean zzb(Context context) {
        if (!zze) {
            try {
                PackageInfo zzb = zzbih.zza(context).zzb("com.google.android.gms", 64);
                zzt.zza(context);
                if (zzb == null || zzt.zza(zzb, false) || !zzt.zza(zzb, true)) {
                    zzd = false;
                    zze = true;
                } else {
                    zzd = true;
                    zze = true;
                }
            } catch (Throwable e) {
                Log.w("GooglePlayServicesUtil", "Cannot find Google Play services package name.", e);
            } catch (Throwable th) {
                zze = true;
            }
        }
        return zzd || !"user".equals(Build.TYPE);
    }

    @Hide
    @Deprecated
    public static boolean zzb(Context context, int i) {
        return zzz.zza(context, i);
    }

    @Hide
    @Deprecated
    public static void zzc(Context context) {
        if (!zza.getAndSet(true)) {
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                if (notificationManager != null) {
                    notificationManager.cancel(10436);
                }
            } catch (SecurityException e) {
            }
        }
    }

    @Hide
    @Deprecated
    public static boolean zzc(Context context, int i) {
        return i == 18 ? true : i == 1 ? zza(context, "com.google.android.gms") : false;
    }

    @Hide
    @Deprecated
    public static int zzd(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 0;
        }
    }
}
