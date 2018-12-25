package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzu;
import java.util.HashSet;
import java.util.Set;

@Hide
public final class zzasl {
    private final zzark zza;
    private volatile Boolean zzb;
    private String zzc;
    private Set<Integer> zzd;

    protected zzasl(zzark zzark) {
        zzbq.zza(zzark);
        this.zza = zzark;
    }

    public static boolean zzb() {
        return ((Boolean) zzast.zza.zza()).booleanValue();
    }

    public static int zzc() {
        return ((Integer) zzast.zzr.zza()).intValue();
    }

    public static long zzd() {
        return ((Long) zzast.zzf.zza()).longValue();
    }

    public static long zze() {
        return ((Long) zzast.zzg.zza()).longValue();
    }

    public static int zzf() {
        return ((Integer) zzast.zzi.zza()).intValue();
    }

    public static int zzg() {
        return ((Integer) zzast.zzj.zza()).intValue();
    }

    public static String zzh() {
        return (String) zzast.zzl.zza();
    }

    public static String zzi() {
        return (String) zzast.zzk.zza();
    }

    public static String zzj() {
        return (String) zzast.zzm.zza();
    }

    public static long zzl() {
        return ((Long) zzast.zzy.zza()).longValue();
    }

    public final boolean zza() {
        if (this.zzb == null) {
            synchronized (this) {
                if (this.zzb == null) {
                    ApplicationInfo applicationInfo = this.zza.zza().getApplicationInfo();
                    String zza = zzu.zza();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        boolean z = str != null && str.equals(zza);
                        this.zzb = Boolean.valueOf(z);
                    }
                    if ((this.zzb == null || !this.zzb.booleanValue()) && "com.google.android.gms.analytics".equals(zza)) {
                        this.zzb = Boolean.TRUE;
                    }
                    if (this.zzb == null) {
                        this.zzb = Boolean.TRUE;
                        this.zza.zze().zzf("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzb.booleanValue();
    }

    public final Set<Integer> zzk() {
        String str = (String) zzast.zzu.zza();
        if (this.zzd == null || this.zzc == null || !this.zzc.equals(str)) {
            String[] split = TextUtils.split(str, ",");
            Set hashSet = new HashSet();
            for (String parseInt : split) {
                try {
                    hashSet.add(Integer.valueOf(Integer.parseInt(parseInt)));
                } catch (NumberFormatException e) {
                }
            }
            this.zzc = str;
            this.zzd = hashSet;
        }
        return this.zzd;
    }
}
