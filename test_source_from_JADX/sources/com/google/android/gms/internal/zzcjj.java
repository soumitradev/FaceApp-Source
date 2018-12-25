package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.GuardedBy;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement;

public final class zzcjj extends zzcli {
    private char zza = '\u0000';
    private long zzb = -1;
    @GuardedBy("this")
    private String zzc;
    private final zzcjl zzd = new zzcjl(this, 6, false, false);
    private final zzcjl zze = new zzcjl(this, 6, true, false);
    private final zzcjl zzf = new zzcjl(this, 6, false, true);
    private final zzcjl zzg = new zzcjl(this, 5, false, false);
    private final zzcjl zzh = new zzcjl(this, 5, true, false);
    private final zzcjl zzi = new zzcjl(this, 5, false, true);
    private final zzcjl zzj = new zzcjl(this, 4, false, false);
    private final zzcjl zzk = new zzcjl(this, 3, false, false);
    private final zzcjl zzl = new zzcjl(this, 2, false, false);

    zzcjj(zzckj zzckj) {
        super(zzckj);
    }

    protected static Object zza(String str) {
        return str == null ? null : new zzcjm(str);
    }

    @Hide
    private static String zza(boolean z, Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf((long) ((Integer) obj).intValue());
        }
        int i = 0;
        String valueOf;
        if (obj instanceof Long) {
            if (!z) {
                return String.valueOf(obj);
            }
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return String.valueOf(obj);
            }
            String str = String.valueOf(obj).charAt(0) == '-' ? "-" : "";
            valueOf = String.valueOf(Math.abs(l.longValue()));
            long round = Math.round(Math.pow(10.0d, (double) (valueOf.length() - 1)));
            long round2 = Math.round(Math.pow(10.0d, (double) valueOf.length()) - 1.0d);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 43) + String.valueOf(str).length());
            stringBuilder.append(str);
            stringBuilder.append(round);
            stringBuilder.append("...");
            stringBuilder.append(str);
            stringBuilder.append(round2);
            return stringBuilder.toString();
        } else if (obj instanceof Boolean) {
            return String.valueOf(obj);
        } else {
            if (!(obj instanceof Throwable)) {
                return obj instanceof zzcjm ? ((zzcjm) obj).zza : z ? "-" : String.valueOf(obj);
            } else {
                Throwable th = (Throwable) obj;
                StringBuilder stringBuilder2 = new StringBuilder(z ? th.getClass().getName() : th.toString());
                valueOf = zzb(AppMeasurement.class.getCanonicalName());
                String zzb = zzb(zzckj.class.getCanonicalName());
                StackTraceElement[] stackTrace = th.getStackTrace();
                int length = stackTrace.length;
                while (i < length) {
                    StackTraceElement stackTraceElement = stackTrace[i];
                    if (!stackTraceElement.isNativeMethod()) {
                        String className = stackTraceElement.getClassName();
                        if (className != null) {
                            className = zzb(className);
                            if (className.equals(valueOf) || className.equals(zzb)) {
                                stringBuilder2.append(": ");
                                stringBuilder2.append(stackTraceElement);
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                    i++;
                }
                return stringBuilder2.toString();
            }
        }
    }

    @Hide
    static String zza(boolean z, String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            Object obj4 = "";
        }
        obj = zza(z, obj);
        obj2 = zza(z, obj2);
        Object zza = zza(z, obj3);
        StringBuilder stringBuilder = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(obj4)) {
            stringBuilder.append(obj4);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(obj)) {
            stringBuilder.append(str2);
            stringBuilder.append(obj);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(obj2)) {
            stringBuilder.append(str2);
            stringBuilder.append(obj2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zza)) {
            stringBuilder.append(str2);
            stringBuilder.append(zza);
        }
        return stringBuilder.toString();
    }

    private final String zzag() {
        String str;
        synchronized (this) {
            if (this.zzc == null) {
                this.zzc = (String) zzciz.zzg.zzb();
            }
            str = this.zzc;
        }
        return str;
    }

    private static String zzb(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @Hide
    protected final void zza(int i, String str) {
        Log.println(i, zzag(), str);
    }

    protected final void zza(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) {
        if (!z && zza(i)) {
            zza(i, zza(false, str, obj, obj2, obj3));
        }
        if (!z2 && i >= 5) {
            String str2;
            zzbq.zza(str);
            zzcli zzk = this.zzp.zzk();
            if (zzk == null) {
                str2 = "Scheduler not set. Not logging error/warn";
            } else if (zzk.zzap()) {
                if (i < 0) {
                    i = 0;
                }
                zzk.zza(new zzcjk(this, i >= 9 ? 8 : i, str, obj, obj2, obj3));
            } else {
                str2 = "Scheduler not initialized. Not logging error/warn";
            }
            zza(6, str2);
        }
    }

    protected final boolean zza(int i) {
        return Log.isLoggable(zzag(), i);
    }

    public final zzcjl zzaa() {
        return this.zzg;
    }

    public final zzcjl zzab() {
        return this.zzi;
    }

    public final zzcjl zzac() {
        return this.zzj;
    }

    public final zzcjl zzad() {
        return this.zzk;
    }

    public final zzcjl zzae() {
        return this.zzl;
    }

    public final String zzaf() {
        Pair zza = zzu().zzb.zza();
        if (zza != null) {
            if (zza != zzcju.zza) {
                String valueOf = String.valueOf(zza.second);
                String str = (String) zza.first;
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(str).length());
                stringBuilder.append(valueOf);
                stringBuilder.append(":");
                stringBuilder.append(str);
                return stringBuilder.toString();
            }
        }
        return null;
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
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

    protected final boolean zzw() {
        return false;
    }

    public final zzcjl zzy() {
        return this.zzd;
    }

    public final zzcjl zzz() {
        return this.zze;
    }
}
