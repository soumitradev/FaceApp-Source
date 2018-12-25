package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.parrot.freeflight.drone.DroneConfig;

public class zzark {
    private static volatile zzark zza;
    private final Context zzb;
    private final Context zzc;
    private final zze zzd = zzi.zzd();
    private final zzasl zze = new zzasl(this);
    private final zzatd zzf;
    private final zzk zzg;
    private final zzaqz zzh;
    private final zzasq zzi;
    private final zzatu zzj;
    private final zzath zzk;
    private final GoogleAnalytics zzl;
    private final zzasc zzm;
    private final zzaqy zzn;
    private final zzarv zzo;
    private final zzasp zzp;

    private zzark(zzarm zzarm) {
        Context zza = zzarm.zza();
        zzbq.zza(zza, "Application context can't be null");
        Context zzb = zzarm.zzb();
        zzbq.zza(zzb);
        this.zzb = zza;
        this.zzc = zzb;
        zzari zzatd = new zzatd(this);
        zzatd.zzaa();
        this.zzf = zzatd;
        zzarh zze = zze();
        String str = zzarj.zza;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + DroneConfig.H264_720P_SLRS_CODEC);
        stringBuilder.append("Google Analytics ");
        stringBuilder.append(str);
        stringBuilder.append(" is starting up. To enable debug logging on a device run:\n  adb shell setprop log.tag.GAv4 DEBUG\n  adb logcat -s GAv4");
        zze.zzd(stringBuilder.toString());
        zzatd = new zzath(this);
        zzatd.zzaa();
        this.zzk = zzatd;
        zzatd = new zzatu(this);
        zzatd.zzaa();
        this.zzj = zzatd;
        zzatd = new zzaqz(this, zzarm);
        zzari zzasc = new zzasc(this);
        zzari zzaqy = new zzaqy(this);
        zzari zzarv = new zzarv(this);
        zzari zzasp = new zzasp(this);
        zzk zza2 = zzk.zza(zza);
        zza2.zza(new zzarl(this));
        this.zzg = zza2;
        GoogleAnalytics googleAnalytics = new GoogleAnalytics(this);
        zzasc.zzaa();
        this.zzm = zzasc;
        zzaqy.zzaa();
        this.zzn = zzaqy;
        zzarv.zzaa();
        this.zzo = zzarv;
        zzasp.zzaa();
        this.zzp = zzasp;
        zzasc = new zzasq(this);
        zzasc.zzaa();
        this.zzi = zzasc;
        zzatd.zzaa();
        this.zzh = zzatd;
        googleAnalytics.zzc();
        this.zzl = googleAnalytics;
        zzatd.zzb();
    }

    public static zzark zza(Context context) {
        zzbq.zza(context);
        if (zza == null) {
            synchronized (zzark.class) {
                if (zza == null) {
                    zze zzd = zzi.zzd();
                    long zzb = zzd.zzb();
                    zzark zzark = new zzark(new zzarm(context));
                    zza = zzark;
                    GoogleAnalytics.zze();
                    long zzb2 = zzd.zzb() - zzb;
                    long longValue = ((Long) zzast.zzae.zza()).longValue();
                    if (zzb2 > longValue) {
                        zzark.zze().zzc("Slow initialization (ms)", Long.valueOf(zzb2), Long.valueOf(longValue));
                    }
                }
            }
        }
        return zza;
    }

    private static void zza(zzari zzari) {
        zzbq.zza(zzari, "Analytics service not created/initialized");
        zzbq.zzb(zzari.zzy(), "Analytics service not initialized");
    }

    public final Context zza() {
        return this.zzb;
    }

    public final Context zzb() {
        return this.zzc;
    }

    public final zze zzc() {
        return this.zzd;
    }

    public final zzasl zzd() {
        return this.zze;
    }

    public final zzatd zze() {
        zza(this.zzf);
        return this.zzf;
    }

    public final zzatd zzf() {
        return this.zzf;
    }

    public final zzk zzg() {
        zzbq.zza(this.zzg);
        return this.zzg;
    }

    public final zzaqz zzh() {
        zza(this.zzh);
        return this.zzh;
    }

    public final zzasq zzi() {
        zza(this.zzi);
        return this.zzi;
    }

    public final GoogleAnalytics zzj() {
        zzbq.zza(this.zzl);
        zzbq.zzb(this.zzl.zzd(), "Analytics instance not initialized");
        return this.zzl;
    }

    public final zzatu zzk() {
        zza(this.zzj);
        return this.zzj;
    }

    public final zzath zzl() {
        zza(this.zzk);
        return this.zzk;
    }

    public final zzath zzm() {
        if (this.zzk != null) {
            if (this.zzk.zzy()) {
                return this.zzk;
            }
        }
        return null;
    }

    public final zzaqy zzn() {
        zza(this.zzn);
        return this.zzn;
    }

    public final zzasc zzo() {
        zza(this.zzm);
        return this.zzm;
    }

    public final zzarv zzp() {
        zza(this.zzo);
        return this.zzo;
    }

    public final zzasp zzq() {
        return this.zzp;
    }
}
