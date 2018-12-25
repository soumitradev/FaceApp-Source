package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.measurement.AppMeasurement$zza;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class zzcma extends zzcli {
    protected zzcmd zza;
    private volatile zzclz zzb;
    private zzclz zzc;
    private long zzd;
    private final Map<Activity, zzcmd> zze = new ArrayMap();
    private final CopyOnWriteArrayList<AppMeasurement$zza> zzf = new CopyOnWriteArrayList();
    private boolean zzg;
    private zzclz zzh;
    private String zzi;

    public zzcma(zzckj zzckj) {
        super(zzckj);
    }

    private static String zza(String str) {
        String[] split = str.split("\\.");
        str = split.length > 0 ? split[split.length - 1] : "";
        return str.length() > 100 ? str.substring(0, 100) : str;
    }

    @MainThread
    private final void zza(Activity activity, zzcmd zzcmd, boolean z) {
        zzclz zzclz = null;
        zzclz zzclz2 = this.zzb != null ? this.zzb : (this.zzc == null || Math.abs(zzk().zzb() - this.zzd) >= 1000) ? null : this.zzc;
        if (zzclz2 != null) {
            zzclz = new zzclz(zzclz2);
        }
        int i = 1;
        this.zzg = true;
        try {
            Iterator it = this.zzf.iterator();
            while (it.hasNext()) {
                try {
                    i &= ((AppMeasurement$zza) it.next()).zza(zzclz, zzcmd);
                } catch (Exception e) {
                    zzt().zzy().zza("onScreenChangeCallback threw exception", e);
                }
            }
        } catch (Exception e2) {
            zzt().zzy().zza("onScreenChangeCallback loop threw exception", e2);
        } catch (Throwable th) {
            this.zzg = false;
        }
        this.zzg = false;
        zzclz = this.zzb == null ? this.zzc : this.zzb;
        if (i != 0) {
            if (zzcmd.zzb == null) {
                zzcmd.zzb = zza(activity.getClass().getCanonicalName());
            }
            zzclz zzcmd2 = new zzcmd(zzcmd);
            this.zzc = this.zzb;
            this.zzd = zzk().zzb();
            this.zzb = zzcmd2;
            zzs().zza(new zzcmb(this, z, zzclz, zzcmd2));
        }
    }

    public static void zza(zzclz zzclz, Bundle bundle, boolean z) {
        if (bundle == null || zzclz == null || (bundle.containsKey("_sc") && !z)) {
            if (bundle != null && zzclz == null && z) {
                bundle.remove("_sn");
                bundle.remove("_sc");
                bundle.remove("_si");
            }
            return;
        }
        if (zzclz.zza != null) {
            bundle.putString("_sn", zzclz.zza);
        } else {
            bundle.remove("_sn");
        }
        bundle.putString("_sc", zzclz.zzb);
        bundle.putLong("_si", zzclz.zzc);
    }

    @WorkerThread
    private final void zza(@NonNull zzcmd zzcmd) {
        zzd().zza(zzk().zzb());
        if (zzr().zza(zzcmd.zzd)) {
            zzcmd.zzd = false;
        }
    }

    @MainThread
    final zzcmd zza(@NonNull Activity activity) {
        zzbq.zza(activity);
        zzcmd zzcmd = (zzcmd) this.zze.get(activity);
        if (zzcmd != null) {
            return zzcmd;
        }
        zzcmd zzcmd2 = new zzcmd(null, zza(activity.getClass().getCanonicalName()), zzp().zzy());
        this.zze.put(activity, zzcmd2);
        return zzcmd2;
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @MainThread
    public final void zza(Activity activity, Bundle bundle) {
        if (bundle != null) {
            zzcmd zzcmd = (zzcmd) this.zze.get(activity);
            if (zzcmd != null) {
                Bundle bundle2 = new Bundle();
                bundle2.putLong(ShareConstants.WEB_DIALOG_PARAM_ID, zzcmd.zzc);
                bundle2.putString("name", zzcmd.zza);
                bundle2.putString("referrer_name", zzcmd.zzb);
                bundle.putBundle("com.google.firebase.analytics.screen_service", bundle2);
            }
        }
    }

    @MainThread
    public final void zza(@NonNull Activity activity, @Nullable @Size(max = 36, min = 1) String str, @Nullable @Size(max = 36, min = 1) String str2) {
        zzs();
        if (!zzcke.zzy()) {
            zzt().zzaa().zza("setCurrentScreen must be called from the main thread");
        } else if (this.zzg) {
            zzt().zzaa().zza("Cannot call setCurrentScreen from onScreenChangeCallback");
        } else if (this.zzb == null) {
            zzt().zzaa().zza("setCurrentScreen cannot be called while no activity active");
        } else if (this.zze.get(activity) == null) {
            zzt().zzaa().zza("setCurrentScreen must be called with an activity in the activity lifecycle");
        } else {
            if (str2 == null) {
                str2 = zza(activity.getClass().getCanonicalName());
            }
            boolean equals = this.zzb.zzb.equals(str2);
            boolean zzb = zzcno.zzb(this.zzb.zza, str);
            if (equals && zzb) {
                zzt().zzab().zza("setCurrentScreen cannot be called with the same class and name");
            } else if (str != null && (str.length() <= 0 || str.length() > 100)) {
                zzt().zzaa().zza("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            } else if (str2 == null || (str2.length() > 0 && str2.length() <= 100)) {
                zzt().zzae().zza("Setting current screen to name, class", str == null ? "null" : str, str2);
                zzcmd zzcmd = new zzcmd(str, str2, zzp().zzy());
                this.zze.put(activity, zzcmd);
                zza(activity, zzcmd, true);
            } else {
                zzt().zzaa().zza("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            }
        }
    }

    @MainThread
    public final void zza(@NonNull AppMeasurement$zza appMeasurement$zza) {
        if (appMeasurement$zza == null) {
            zzt().zzaa().zza("Attempting to register null OnScreenChangeCallback");
            return;
        }
        this.zzf.remove(appMeasurement$zza);
        this.zzf.add(appMeasurement$zza);
    }

    @WorkerThread
    public final void zza(String str, zzclz zzclz) {
        zzc();
        synchronized (this) {
            if (this.zzi == null || this.zzi.equals(str) || zzclz != null) {
                this.zzi = str;
                this.zzh = zzclz;
            }
        }
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    @MainThread
    public final void zzb(Activity activity) {
        zza(activity, zza(activity), false);
        zzclh zzd = zzd();
        zzd.zzs().zza(new zzcid(zzd, zzd.zzk().zzb()));
    }

    @MainThread
    public final void zzb(@NonNull AppMeasurement$zza appMeasurement$zza) {
        this.zzf.remove(appMeasurement$zza);
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    @MainThread
    public final void zzc(Activity activity) {
        zzcmd zza = zza(activity);
        this.zzc = this.zzb;
        this.zzd = zzk().zzb();
        this.zzb = null;
        zzs().zza(new zzcmc(this, zza));
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    @MainThread
    public final void zzd(Activity activity) {
        this.zze.remove(activity);
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

    @WorkerThread
    public final zzcmd zzy() {
        zzaq();
        zzc();
        return this.zza;
    }

    public final zzclz zzz() {
        zzclz zzclz = this.zzb;
        return zzclz == null ? null : new zzclz(zzclz);
    }
}
