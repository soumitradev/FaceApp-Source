package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzu;
import java.lang.reflect.InvocationTargetException;

@Hide
public final class zzcik extends zzclh {
    private Boolean zza;

    zzcik(zzckj zzckj) {
        super(zzckj);
    }

    public static boolean zzab() {
        return ((Boolean) zzciz.zze.zzb()).booleanValue();
    }

    public static long zzy() {
        return ((Long) zzciz.zzaj.zzb()).longValue();
    }

    public static long zzz() {
        return ((Long) zzciz.zzj.zzb()).longValue();
    }

    public final int zza(@Size(min = 1) String str) {
        return zzb(str, zzciz.zzu);
    }

    public final long zza(String str, zzcja<Long> zzcja) {
        if (str != null) {
            Object zza = zzq().zza(str, zzcja.zza());
            if (!TextUtils.isEmpty(zza)) {
                try {
                    return ((Long) zzcja.zza(Long.valueOf(Long.valueOf(zza).longValue()))).longValue();
                } catch (NumberFormatException e) {
                }
            }
        }
        return ((Long) zzcja.zzb()).longValue();
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    public final String zzaa() {
        Object e;
        zzcjl zzy;
        String str;
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{"debug.firebase.analytics.app", ""});
        } catch (ClassNotFoundException e2) {
            e = e2;
            zzy = zzt().zzy();
            str = "Could not find SystemProperties class";
            zzy.zza(str, e);
            return "";
        } catch (NoSuchMethodException e3) {
            e = e3;
            zzy = zzt().zzy();
            str = "Could not find SystemProperties.get() method";
            zzy.zza(str, e);
            return "";
        } catch (IllegalAccessException e4) {
            e = e4;
            zzy = zzt().zzy();
            str = "Could not access SystemProperties.get()";
            zzy.zza(str, e);
            return "";
        } catch (InvocationTargetException e5) {
            e = e5;
            zzy = zzt().zzy();
            str = "SystemProperties.get() threw an exception";
            zzy.zza(str, e);
            return "";
        }
    }

    public final int zzb(String str, zzcja<Integer> zzcja) {
        if (str != null) {
            Object zza = zzq().zza(str, zzcja.zza());
            if (!TextUtils.isEmpty(zza)) {
                try {
                    return ((Integer) zzcja.zza(Integer.valueOf(Integer.valueOf(zza).intValue()))).intValue();
                } catch (NumberFormatException e) {
                }
            }
        }
        return ((Integer) zzcja.zzb()).intValue();
    }

    @Nullable
    final Boolean zzb(@Size(min = 1) String str) {
        zzbq.zza(str);
        try {
            if (zzl().getPackageManager() == null) {
                zzt().zzy().zza("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo zza = zzbih.zza(zzl()).zza(zzl().getPackageName(), 128);
            if (zza == null) {
                zzt().zzy().zza("Failed to load metadata: ApplicationInfo is null");
                return null;
            } else if (zza.metaData != null) {
                return !zza.metaData.containsKey(str) ? null : Boolean.valueOf(zza.metaData.getBoolean(str));
            } else {
                zzt().zzy().zza("Failed to load metadata: Metadata bundle is null");
                return null;
            }
        } catch (NameNotFoundException e) {
            zzt().zzy().zza("Failed to load metadata: Package name not found", e);
            return null;
        }
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final boolean zzc(String str) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(zzq().zza(str, "gaia_collection_enabled"));
    }

    public final boolean zzc(String str, zzcja<Boolean> zzcja) {
        Object zza;
        if (str != null) {
            zza = zzq().zza(str, zzcja.zza());
            if (!TextUtils.isEmpty(zza)) {
                zza = zzcja.zza(Boolean.valueOf(Boolean.parseBoolean(zza)));
                return ((Boolean) zza).booleanValue();
            }
        }
        zza = zzcja.zzb();
        return ((Boolean) zza).booleanValue();
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    final boolean zzd(String str) {
        return zzc(str, zzciz.zzan);
    }

    public final /* bridge */ /* synthetic */ zzcih zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzclk zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzcje zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzcir zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzcme zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzcma zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zze zzk() {
        return super.zzk();
    }

    public final /* bridge */ /* synthetic */ Context zzl() {
        return super.zzl();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ zzcil zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzcno zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzckd zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzcke zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzcju zzu() {
        return super.zzu();
    }

    public final /* bridge */ /* synthetic */ zzcik zzv() {
        return super.zzv();
    }

    public final boolean zzw() {
        if (this.zza == null) {
            synchronized (this) {
                if (this.zza == null) {
                    ApplicationInfo applicationInfo = zzl().getApplicationInfo();
                    String zza = zzu.zza();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        boolean z = str != null && str.equals(zza);
                        this.zza = Boolean.valueOf(z);
                    }
                    if (this.zza == null) {
                        this.zza = Boolean.TRUE;
                        zzt().zzy().zza("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zza.booleanValue();
    }

    public final boolean zzx() {
        Boolean zzb = zzb("firebase_analytics_collection_deactivated");
        return zzb != null && zzb.booleanValue();
    }
}
