package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.util.zze;
import com.google.firebase.iid.FirebaseInstanceId;
import java.math.BigInteger;
import java.util.Locale;

public final class zzcje extends zzcli {
    private String zza;
    private String zzb;
    private int zzc;
    private String zzd;
    private String zze;
    private long zzf;
    private long zzg;
    private int zzh;
    private String zzi;

    zzcje(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final String zzad() {
        zzc();
        try {
            return FirebaseInstanceId.getInstance().getId();
        } catch (IllegalStateException e) {
            zzt().zzaa().zza("Failed to retrieve Firebase Instance Id");
            return null;
        }
    }

    protected final void p_() {
        String str;
        Status zza;
        Object obj;
        Object obj2;
        Boolean zzb;
        zzcjl zzac;
        String str2 = "unknown";
        String str3 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        String str4 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        String packageName = zzl().getPackageName();
        PackageManager packageManager = zzl().getPackageManager();
        int i = Integer.MIN_VALUE;
        if (packageManager == null) {
            zzt().zzy().zza("PackageManager is null, app identity information might be inaccurate. appId", zzcjj.zza(packageName));
        } else {
            try {
                str2 = packageManager.getInstallerPackageName(packageName);
            } catch (IllegalArgumentException e) {
                zzt().zzy().zza("Error retrieving app installer package name. appId", zzcjj.zza(packageName));
            }
            if (str2 == null) {
                str2 = "manual_install";
            } else if ("com.android.vending".equals(str2)) {
                str2 = "";
            }
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(zzl().getPackageName(), 0);
                if (packageInfo != null) {
                    CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                    if (!TextUtils.isEmpty(applicationLabel)) {
                        str4 = applicationLabel.toString();
                    }
                    str = packageInfo.versionName;
                    try {
                        i = packageInfo.versionCode;
                        str3 = str;
                    } catch (NameNotFoundException e2) {
                        str3 = str;
                        zzt().zzy().zza("Error retrieving package info. appId, appName", zzcjj.zza(packageName), str4);
                        this.zza = packageName;
                        this.zzd = str2;
                        this.zzb = str3;
                        this.zzc = i;
                        this.zze = str4;
                        this.zzf = 0;
                        zza = zzbz.zza(zzl());
                        obj = 1;
                        if (zza == null) {
                        }
                        if (obj2 == null) {
                            if (zza == null) {
                                zzt().zzy().zza("GoogleService failed to initialize (no status)");
                            } else {
                                zzt().zzy().zza("GoogleService failed to initialize, status", Integer.valueOf(zza.getStatusCode()), zza.getStatusMessage());
                            }
                        }
                        if (obj2 != null) {
                            zzb = zzv().zzb("firebase_analytics_collection_enabled");
                            if (zzv().zzx()) {
                                zzac = zzt().zzac();
                                str = "Collection disabled with firebase_analytics_collection_deactivated=1";
                            } else {
                                if (zzb == null) {
                                }
                                if (zzb == null) {
                                }
                                zzt().zzae().zza("Collection enabled");
                                this.zzi = "";
                                this.zzg = 0;
                                str2 = zzbz.zza();
                                if (TextUtils.isEmpty(str2)) {
                                    str2 = "";
                                }
                                this.zzi = str2;
                                if (obj != null) {
                                    zzt().zzae().zza("App package, google app id", this.zza, this.zzi);
                                }
                                if (VERSION.SDK_INT < 16) {
                                    this.zzh = zzbif.zza(zzl());
                                } else {
                                    this.zzh = 0;
                                }
                            }
                            zzac.zza(str);
                        }
                        obj = null;
                        this.zzi = "";
                        this.zzg = 0;
                        str2 = zzbz.zza();
                        if (TextUtils.isEmpty(str2)) {
                            str2 = "";
                        }
                        this.zzi = str2;
                        if (obj != null) {
                            zzt().zzae().zza("App package, google app id", this.zza, this.zzi);
                        }
                        if (VERSION.SDK_INT < 16) {
                            this.zzh = 0;
                        } else {
                            this.zzh = zzbif.zza(zzl());
                        }
                    }
                }
            } catch (NameNotFoundException e3) {
                zzt().zzy().zza("Error retrieving package info. appId, appName", zzcjj.zza(packageName), str4);
                this.zza = packageName;
                this.zzd = str2;
                this.zzb = str3;
                this.zzc = i;
                this.zze = str4;
                this.zzf = 0;
                zza = zzbz.zza(zzl());
                obj = 1;
                if (zza == null) {
                }
                if (obj2 == null) {
                    if (zza == null) {
                        zzt().zzy().zza("GoogleService failed to initialize, status", Integer.valueOf(zza.getStatusCode()), zza.getStatusMessage());
                    } else {
                        zzt().zzy().zza("GoogleService failed to initialize (no status)");
                    }
                }
                if (obj2 != null) {
                    zzb = zzv().zzb("firebase_analytics_collection_enabled");
                    if (zzv().zzx()) {
                        if (zzb == null) {
                        }
                        if (zzb == null) {
                        }
                        zzt().zzae().zza("Collection enabled");
                        this.zzi = "";
                        this.zzg = 0;
                        str2 = zzbz.zza();
                        if (TextUtils.isEmpty(str2)) {
                            str2 = "";
                        }
                        this.zzi = str2;
                        if (obj != null) {
                            zzt().zzae().zza("App package, google app id", this.zza, this.zzi);
                        }
                        if (VERSION.SDK_INT < 16) {
                            this.zzh = zzbif.zza(zzl());
                        } else {
                            this.zzh = 0;
                        }
                    }
                    zzac = zzt().zzac();
                    str = "Collection disabled with firebase_analytics_collection_deactivated=1";
                    zzac.zza(str);
                }
                obj = null;
                this.zzi = "";
                this.zzg = 0;
                str2 = zzbz.zza();
                if (TextUtils.isEmpty(str2)) {
                    str2 = "";
                }
                this.zzi = str2;
                if (obj != null) {
                    zzt().zzae().zza("App package, google app id", this.zza, this.zzi);
                }
                if (VERSION.SDK_INT < 16) {
                    this.zzh = 0;
                } else {
                    this.zzh = zzbif.zza(zzl());
                }
            }
        }
        this.zza = packageName;
        this.zzd = str2;
        this.zzb = str3;
        this.zzc = i;
        this.zze = str4;
        this.zzf = 0;
        zza = zzbz.zza(zzl());
        obj = 1;
        obj2 = (zza == null && zza.isSuccess()) ? 1 : null;
        if (obj2 == null) {
            if (zza == null) {
                zzt().zzy().zza("GoogleService failed to initialize (no status)");
            } else {
                zzt().zzy().zza("GoogleService failed to initialize, status", Integer.valueOf(zza.getStatusCode()), zza.getStatusMessage());
            }
        }
        if (obj2 != null) {
            zzb = zzv().zzb("firebase_analytics_collection_enabled");
            if (zzv().zzx()) {
                zzac = zzt().zzac();
                str = "Collection disabled with firebase_analytics_collection_deactivated=1";
            } else if (zzb == null && !zzb.booleanValue()) {
                zzac = zzt().zzac();
                str = "Collection disabled with firebase_analytics_collection_enabled=0";
            } else if (zzb == null || !zzbz.zzb()) {
                zzt().zzae().zza("Collection enabled");
                this.zzi = "";
                this.zzg = 0;
                str2 = zzbz.zza();
                if (TextUtils.isEmpty(str2)) {
                    str2 = "";
                }
                this.zzi = str2;
                if (obj != null) {
                    zzt().zzae().zza("App package, google app id", this.zza, this.zzi);
                }
                if (VERSION.SDK_INT < 16) {
                    this.zzh = zzbif.zza(zzl());
                } else {
                    this.zzh = 0;
                }
            } else {
                zzac = zzt().zzac();
                str = "Collection disabled with google_app_measurement_enable=0";
            }
            zzac.zza(str);
        }
        obj = null;
        this.zzi = "";
        this.zzg = 0;
        try {
            str2 = zzbz.zza();
            if (TextUtils.isEmpty(str2)) {
                str2 = "";
            }
            this.zzi = str2;
            if (obj != null) {
                zzt().zzae().zza("App package, google app id", this.zza, this.zzi);
            }
        } catch (IllegalStateException e4) {
            zzt().zzy().zza("getGoogleAppId or isMeasurementEnabled failed with exception. appId", zzcjj.zza(packageName), e4);
        }
        if (VERSION.SDK_INT < 16) {
            this.zzh = 0;
        } else {
            this.zzh = zzbif.zza(zzl());
        }
    }

    @WorkerThread
    final zzcif zza(String str) {
        zzc();
        String zzz = zzz();
        String zzaa = zzaa();
        zzaq();
        String str2 = this.zzb;
        long zzab = (long) zzab();
        zzaq();
        String str3 = this.zzd;
        zzaq();
        zzc();
        if (this.zzf == 0) {
            r0.zzf = r0.zzp.zzo().zzb(zzl(), zzl().getPackageName());
        }
        long j = r0.zzf;
        boolean zzab2 = r0.zzp.zzab();
        boolean z = true;
        boolean z2 = zzu().zzo ^ 1;
        String zzad = zzad();
        zzaq();
        long zzac = r0.zzp.zzac();
        int zzac2 = zzac();
        Boolean zzb = zzv().zzb("google_analytics_adid_collection_enabled");
        if (zzb != null) {
            if (!zzb.booleanValue()) {
                z = false;
            }
        }
        return new zzcif(zzz, zzaa, str2, zzab, str3, 12210, j, str, zzab2, z2, zzad, 0, zzac, zzac2, Boolean.valueOf(z).booleanValue());
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    final String zzaa() {
        zzaq();
        return this.zzi;
    }

    final int zzab() {
        zzaq();
        return this.zzc;
    }

    final int zzac() {
        zzaq();
        return this.zzh;
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
        return true;
    }

    @WorkerThread
    final String zzy() {
        zzp().zzz().nextBytes(new byte[16]);
        return String.format(Locale.US, "%032x", new Object[]{new BigInteger(1, r0)});
    }

    final String zzz() {
        zzaq();
        return this.zza;
    }
}
