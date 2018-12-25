package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.Process;
import com.google.android.gms.common.util.zzs;

public final class zzbig {
    private Context zza;

    public zzbig(Context context) {
        this.zza = context;
    }

    public final int zza(String str) {
        return this.zza.checkCallingOrSelfPermission(str);
    }

    public final int zza(String str, String str2) {
        return this.zza.getPackageManager().checkPermission(str, str2);
    }

    public final ApplicationInfo zza(String str, int i) throws NameNotFoundException {
        return this.zza.getPackageManager().getApplicationInfo(str, i);
    }

    public final boolean zza() {
        if (Binder.getCallingUid() == Process.myUid()) {
            return zzbif.zza(this.zza);
        }
        if (zzs.zzi()) {
            String nameForUid = this.zza.getPackageManager().getNameForUid(Binder.getCallingUid());
            if (nameForUid != null) {
                return this.zza.getPackageManager().isInstantApp(nameForUid);
            }
        }
        return false;
    }

    @TargetApi(19)
    public final boolean zza(int i, String str) {
        if (zzs.zze()) {
            try {
                ((AppOpsManager) this.zza.getSystemService("appops")).checkPackage(i, str);
                return true;
            } catch (SecurityException e) {
                return false;
            }
        }
        String[] packagesForUid = this.zza.getPackageManager().getPackagesForUid(i);
        if (!(str == null || packagesForUid == null)) {
            for (Object equals : packagesForUid) {
                if (str.equals(equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final String[] zza(int i) {
        return this.zza.getPackageManager().getPackagesForUid(i);
    }

    public final PackageInfo zzb(String str, int i) throws NameNotFoundException {
        return this.zza.getPackageManager().getPackageInfo(str, i);
    }

    public final CharSequence zzb(String str) throws NameNotFoundException {
        return this.zza.getPackageManager().getApplicationLabel(this.zza.getPackageManager().getApplicationInfo(str, 0));
    }
}
