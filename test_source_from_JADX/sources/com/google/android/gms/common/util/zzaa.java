package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.WorkSource;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.internal.zzbih;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzaa {
    private static final Method zza = zza();
    private static final Method zzb = zzb();
    private static final Method zzc = zzc();
    private static final Method zzd = zzd();
    private static final Method zze = zze();

    private static WorkSource zza(int i, String str) {
        WorkSource workSource = new WorkSource();
        if (zzb != null) {
            if (str == null) {
                str = "";
            }
            try {
                zzb.invoke(workSource, new Object[]{Integer.valueOf(i), str});
                return workSource;
            } catch (Throwable e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
                return workSource;
            }
        }
        if (zza != null) {
            zza.invoke(workSource, new Object[]{Integer.valueOf(i)});
        }
        return workSource;
    }

    @Nullable
    public static WorkSource zza(Context context, @Nullable String str) {
        if (context == null || context.getPackageManager() == null || str == null) {
            return null;
        }
        String str2;
        String str3;
        try {
            ApplicationInfo zza = zzbih.zza(context).zza(str, 0);
            if (zza != null) {
                return zza(zza.uid, str);
            }
            str2 = "WorkSourceUtil";
            str3 = "Could not get applicationInfo from package: ";
            str = String.valueOf(str);
            Log.e(str2, str.length() != 0 ? str3.concat(str) : new String(str3));
            return null;
        } catch (NameNotFoundException e) {
            str2 = "WorkSourceUtil";
            str3 = "Could not find package: ";
            str = String.valueOf(str);
            Log.e(str2, str.length() != 0 ? str3.concat(str) : new String(str3));
            return null;
        }
    }

    @Nullable
    private static String zza(WorkSource workSource, int i) {
        if (zze != null) {
            try {
                return (String) zze.invoke(workSource, new Object[]{Integer.valueOf(i)});
            } catch (Throwable e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return null;
    }

    private static Method zza() {
        try {
            return WorkSource.class.getMethod(ProductAction.ACTION_ADD, new Class[]{Integer.TYPE});
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> zza(@Nullable WorkSource workSource) {
        int zzb = workSource == null ? 0 : zzb(workSource);
        if (zzb == 0) {
            return Collections.emptyList();
        }
        List<String> arrayList = new ArrayList();
        for (int i = 0; i < zzb; i++) {
            String zza = zza(workSource, i);
            if (!zzw.zza(zza)) {
                arrayList.add(zza);
            }
        }
        return arrayList;
    }

    public static boolean zza(Context context) {
        return (context == null || context.getPackageManager() == null || zzbih.zza(context).zza("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) != 0) ? false : true;
    }

    private static int zzb(WorkSource workSource) {
        if (zzc != null) {
            try {
                return ((Integer) zzc.invoke(workSource, new Object[0])).intValue();
            } catch (Throwable e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return 0;
    }

    private static Method zzb() {
        if (zzs.zzd()) {
            try {
                return WorkSource.class.getMethod(ProductAction.ACTION_ADD, new Class[]{Integer.TYPE, String.class});
            } catch (Exception e) {
            }
        }
        return null;
    }

    private static Method zzc() {
        try {
            return WorkSource.class.getMethod("size", new Class[0]);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzd() {
        try {
            return WorkSource.class.getMethod("get", new Class[]{Integer.TYPE});
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zze() {
        if (zzs.zzd()) {
            try {
                return WorkSource.class.getMethod("getName", new Class[]{Integer.TYPE});
            } catch (Exception e) {
            }
        }
        return null;
    }
}
