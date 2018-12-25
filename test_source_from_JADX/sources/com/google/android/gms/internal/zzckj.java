package com.google.android.gms.internal;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.badlogic.gdx.net.HttpStatus;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.catrobat.catroid.common.BrickValues;

public class zzckj {
    private static volatile zzckj zza;
    private boolean zzaa;
    private Boolean zzab;
    private long zzac;
    private FileLock zzad;
    private FileChannel zzae;
    private List<Long> zzaf;
    private List<Runnable> zzag;
    private int zzah;
    private int zzai;
    private long zzaj;
    private long zzak;
    private boolean zzal;
    private boolean zzam;
    private boolean zzan;
    private final long zzao;
    private final Context zzb;
    private final zzcik zzc;
    private final zzcju zzd;
    private final zzcjj zze;
    private final zzcke zzf;
    private final zzcnd zzg;
    private final zzckd zzh;
    private final AppMeasurement zzi;
    private final FirebaseAnalytics zzj;
    private final zzcno zzk;
    private final zzcjh zzl;
    private final zzcjn zzm;
    private final zze zzn;
    private final zzcma zzo;
    private final zzclk zzp;
    private final zzcia zzq;
    private zzcil zzr;
    private zzcjf zzs;
    private zzcme zzt;
    private zzcir zzu;
    private zzcje zzv;
    private zzcjs zzw;
    private zzcnj zzx;
    private zzcih zzy;
    private boolean zzz = false;

    class zza implements zzcin {
        zzcoe zza;
        List<Long> zzb;
        List<zzcob> zzc;
        private long zzd;
        private /* synthetic */ zzckj zze;

        private zza(zzckj zzckj) {
            this.zze = zzckj;
        }

        private static long zza(zzcob zzcob) {
            return ((zzcob.zzc.longValue() / 1000) / 60) / 60;
        }

        public final void zza(zzcoe zzcoe) {
            zzbq.zza(zzcoe);
            this.zza = zzcoe;
        }

        public final boolean zza(long j, zzcob zzcob) {
            zzbq.zza(zzcob);
            if (this.zzc == null) {
                this.zzc = new ArrayList();
            }
            if (this.zzb == null) {
                this.zzb = new ArrayList();
            }
            if (this.zzc.size() > 0 && zza((zzcob) this.zzc.get(0)) != zza(zzcob)) {
                return false;
            }
            long zzf = this.zzd + ((long) zzcob.zzf());
            if (zzf >= ((long) Math.max(0, ((Integer) zzciz.zzo.zzb()).intValue()))) {
                return false;
            }
            this.zzd = zzf;
            this.zzc.add(zzcob);
            this.zzb.add(Long.valueOf(j));
            return this.zzc.size() < Math.max(1, ((Integer) zzciz.zzp.zzb()).intValue());
        }
    }

    private zzckj(zzclj zzclj) {
        zzcjl zzae;
        String str;
        zzbq.zza(zzclj);
        this.zzb = zzclj.zza;
        this.zzaj = -1;
        this.zzn = zzi.zzd();
        this.zzao = this.zzn.zza();
        this.zzc = new zzcik(this);
        zzcli zzcju = new zzcju(this);
        zzcju.zzar();
        this.zzd = zzcju;
        zzcju = new zzcjj(this);
        zzcju.zzar();
        this.zze = zzcju;
        zzcju = new zzcno(this);
        zzcju.zzar();
        this.zzk = zzcju;
        zzcju = new zzcjh(this);
        zzcju.zzar();
        this.zzl = zzcju;
        this.zzq = new zzcia(this);
        zzcju = new zzcjn(this);
        zzcju.zzar();
        this.zzm = zzcju;
        zzcju = new zzcma(this);
        zzcju.zzar();
        this.zzo = zzcju;
        zzcju = new zzclk(this);
        zzcju.zzar();
        this.zzp = zzcju;
        this.zzi = new AppMeasurement(this);
        this.zzj = new FirebaseAnalytics(this);
        zzcju = new zzcnd(this);
        zzcju.zzar();
        this.zzg = zzcju;
        zzcju = new zzckd(this);
        zzcju.zzar();
        this.zzh = zzcju;
        zzcju = new zzcke(this);
        zzcju.zzar();
        this.zzf = zzcju;
        if (this.zzb.getApplicationContext() instanceof Application) {
            zzclh zzl = zzl();
            if (zzl.zzl().getApplicationContext() instanceof Application) {
                Application application = (Application) zzl.zzl().getApplicationContext();
                if (zzl.zza == null) {
                    zzl.zza = new zzcly(zzl);
                }
                application.unregisterActivityLifecycleCallbacks(zzl.zza);
                application.registerActivityLifecycleCallbacks(zzl.zza);
                zzae = zzl.zzt().zzae();
                str = "Registered activity lifecycle callback";
            }
            this.zzf.zza(new zzckk(this, zzclj));
        }
        zzae = zzf().zzaa();
        str = "Application context is not an Application";
        zzae.zza(str);
        this.zzf.zza(new zzckk(this, zzclj));
    }

    @WorkerThread
    private final int zza(FileChannel fileChannel) {
        zzh().zzc();
        if (fileChannel != null) {
            if (fileChannel.isOpen()) {
                ByteBuffer allocate = ByteBuffer.allocate(4);
                try {
                    fileChannel.position(0);
                    int read = fileChannel.read(allocate);
                    if (read != 4) {
                        if (read != -1) {
                            zzf().zzaa().zza("Unexpected data length. Bytes read", Integer.valueOf(read));
                        }
                        return 0;
                    }
                    allocate.flip();
                    return allocate.getInt();
                } catch (IOException e) {
                    zzf().zzy().zza("Failed to read from channel", e);
                    return 0;
                }
            }
        }
        zzf().zzy().zza("Bad channel to read from");
        return 0;
    }

    private final zzcif zza(Context context, String str, String str2, boolean z, boolean z2) {
        String str3 = str;
        String str4 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        String str5 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        String str6 = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            zzf().zzy().zza("PackageManager is null, can not log app install information");
            return null;
        }
        try {
            str4 = packageManager.getInstallerPackageName(str3);
        } catch (IllegalArgumentException e) {
            zzf().zzy().zza("Error retrieving installer package name. appId", zzcjj.zza(str));
        }
        if (str4 == null) {
            str4 = "manual_install";
        } else if ("com.android.vending".equals(str4)) {
            str4 = "";
        }
        String str7 = str4;
        try {
            int i;
            PackageInfo zzb = zzbih.zza(context).zzb(str3, 0);
            if (zzb != null) {
                CharSequence zzb2 = zzbih.zza(context).zzb(str3);
                if (!TextUtils.isEmpty(zzb2)) {
                    str6 = zzb2.toString();
                }
                str5 = zzb.versionName;
                i = zzb.versionCode;
            } else {
                i = Integer.MIN_VALUE;
            }
            return new zzcif(str3, str2, str5, (long) i, str7, 12210, zzo().zzb(context, str3), null, z, false, "", 0, 0, 0, z2);
        } catch (NameNotFoundException e2) {
            zzf().zzy().zza("Error retrieving newly installed package info. appId, appName", zzcjj.zza(str), str6);
            return null;
        }
    }

    public static zzckj zza(Context context) {
        zzbq.zza(context);
        zzbq.zza(context.getApplicationContext());
        if (zza == null) {
            synchronized (zzckj.class) {
                if (zza == null) {
                    zza = new zzckj(new zzclj(context));
                }
            }
        }
        return zza;
    }

    @WorkerThread
    private final void zza(zzcie zzcie) {
        zzh().zzc();
        if (TextUtils.isEmpty(zzcie.zzd())) {
            zza(zzcie.zzb(), (int) HttpStatus.SC_NO_CONTENT, null, null, null);
            return;
        }
        String zzd = zzcie.zzd();
        String zzc = zzcie.zzc();
        Builder builder = new Builder();
        Builder encodedAuthority = builder.scheme((String) zzciz.zzk.zzb()).encodedAuthority((String) zzciz.zzl.zzb());
        String str = "config/app/";
        zzd = String.valueOf(zzd);
        encodedAuthority.path(zzd.length() != 0 ? str.concat(zzd) : new String(str)).appendQueryParameter("app_instance_id", zzc).appendQueryParameter("platform", "android").appendQueryParameter("gmp_version", "12210");
        zzd = builder.build().toString();
        try {
            Map map;
            URL url = new URL(zzd);
            zzf().zzae().zza("Fetching remote configuration", zzcie.zzb());
            zzcny zza = zzj().zza(zzcie.zzb());
            CharSequence zzb = zzj().zzb(zzcie.zzb());
            if (zza == null || TextUtils.isEmpty(zzb)) {
                map = null;
            } else {
                Map arrayMap = new ArrayMap();
                arrayMap.put("If-Modified-Since", zzb);
                map = arrayMap;
            }
            this.zzal = true;
            zzclh zzs = zzs();
            String zzb2 = zzcie.zzb();
            zzcjp zzckn = new zzckn(this);
            zzs.zzc();
            zzs.zzaq();
            zzbq.zza(url);
            zzbq.zza(zzckn);
            zzs.zzs().zzb(new zzcjr(zzs, zzb2, url, null, map, zzckn));
        } catch (MalformedURLException e) {
            zzf().zzy().zza("Failed to parse config URL. Not fetching. appId", zzcjj.zza(zzcie.zzb()), zzd);
        }
    }

    private static void zza(zzclh zzclh) {
        if (zzclh == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    @WorkerThread
    private final void zza(zzclj zzclj) {
        zzcjl zzac;
        zzh().zzc();
        zzcli zzcir = new zzcir(this);
        zzcir.zzar();
        this.zzu = zzcir;
        zzcir = new zzcje(this);
        zzcir.zzar();
        this.zzv = zzcir;
        zzcli zzcil = new zzcil(this);
        zzcil.zzar();
        this.zzr = zzcil;
        zzcil = new zzcjf(this);
        zzcil.zzar();
        this.zzs = zzcil;
        zzcil = new zzcih(this);
        zzcil.zzar();
        this.zzy = zzcil;
        zzcil = new zzcme(this);
        zzcil.zzar();
        this.zzt = zzcil;
        zzcil = new zzcnj(this);
        zzcil.zzar();
        this.zzx = zzcil;
        this.zzw = new zzcjs(this);
        this.zzk.zzas();
        this.zzd.zzas();
        this.zzv.zzas();
        zzf().zzac().zza("App measurement is starting up, version", Long.valueOf(12210));
        zzf().zzac().zza("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String zzz = zzcir.zzz();
        if (zzo().zzi(zzz)) {
            zzac = zzf().zzac();
            zzz = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
        } else {
            zzac = zzf().zzac();
            String str = "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ";
            zzz = String.valueOf(zzz);
            zzz = zzz.length() != 0 ? str.concat(zzz) : new String(str);
        }
        zzac.zza(zzz);
        zzf().zzad().zza("Debug-level message logging enabled");
        if (this.zzah != this.zzai) {
            zzf().zzy().zza("Not all components initialized", Integer.valueOf(this.zzah), Integer.valueOf(this.zzai));
        }
        this.zzz = true;
    }

    @WorkerThread
    private final boolean zza(int i, FileChannel fileChannel) {
        zzh().zzc();
        if (fileChannel != null) {
            if (fileChannel.isOpen()) {
                ByteBuffer allocate = ByteBuffer.allocate(4);
                allocate.putInt(i);
                allocate.flip();
                try {
                    fileChannel.truncate(0);
                    fileChannel.write(allocate);
                    fileChannel.force(true);
                    if (fileChannel.size() != 4) {
                        zzf().zzy().zza("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
                    }
                    return true;
                } catch (IOException e) {
                    zzf().zzy().zza("Failed to write to channel", e);
                    return false;
                }
            }
        }
        zzf().zzy().zza("Bad channel to read from");
        return false;
    }

    private static boolean zza(zzcob zzcob, String str, Object obj) {
        if (TextUtils.isEmpty(str) || obj == null) {
            return false;
        }
        zzcoc[] zzcocArr = zzcob.zza;
        int length = zzcocArr.length;
        int i = 0;
        while (i < length) {
            zzcoc zzcoc = zzcocArr[i];
            if (str.equals(zzcoc.zza)) {
                return ((obj instanceof Long) && obj.equals(zzcoc.zzc)) || (((obj instanceof String) && obj.equals(zzcoc.zzb)) || ((obj instanceof Double) && obj.equals(zzcoc.zzd)));
            } else {
                i++;
            }
        }
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.WorkerThread
    private final boolean zza(java.lang.String r61, long r62) {
        /*
        r60 = this;
        r1 = r60;
        r2 = r60.zzq();
        r2.zzx();
        r2 = new com.google.android.gms.internal.zzckj$zza;	 Catch:{ all -> 0x0a8b }
        r3 = 0;
        r2.<init>();	 Catch:{ all -> 0x0a8b }
        r4 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r5 = r1.zzaj;	 Catch:{ all -> 0x0a8b }
        com.google.android.gms.common.internal.zzbq.zza(r2);	 Catch:{ all -> 0x0a8b }
        r4.zzc();	 Catch:{ all -> 0x0a8b }
        r4.zzaq();	 Catch:{ all -> 0x0a8b }
        r7 = -1;
        r9 = 2;
        r10 = 0;
        r11 = 1;
        r15 = r4.zzaa();	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r12 = android.text.TextUtils.isEmpty(r3);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        if (r12 == 0) goto L_0x00a1;
    L_0x002d:
        r12 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        if (r12 == 0) goto L_0x004b;
    L_0x0031:
        r12 = new java.lang.String[r9];	 Catch:{ SQLiteException -> 0x0045, all -> 0x0040 }
        r13 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0045, all -> 0x0040 }
        r12[r10] = r13;	 Catch:{ SQLiteException -> 0x0045, all -> 0x0040 }
        r13 = java.lang.String.valueOf(r62);	 Catch:{ SQLiteException -> 0x0045, all -> 0x0040 }
        r12[r11] = r13;	 Catch:{ SQLiteException -> 0x0045, all -> 0x0040 }
        goto L_0x0053;
    L_0x0040:
        r0 = move-exception;
        r2 = r0;
        r9 = r3;
        goto L_0x0a85;
    L_0x0045:
        r0 = move-exception;
        r9 = r3;
        r12 = r9;
    L_0x0048:
        r3 = r0;
        goto L_0x026a;
    L_0x004b:
        r12 = new java.lang.String[r11];	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r13 = java.lang.String.valueOf(r62);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r12[r10] = r13;	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
    L_0x0053:
        r13 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        if (r13 == 0) goto L_0x005a;
    L_0x0057:
        r13 = "rowid <= ? and ";
        goto L_0x005c;
    L_0x005a:
        r13 = "";
    L_0x005c:
        r14 = java.lang.String.valueOf(r13);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r14 = r14.length();	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r14 = r14 + 148;
        r3 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r3.<init>(r14);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r14 = "select app_id, metadata_fingerprint from raw_events where ";
        r3.append(r14);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r3.append(r13);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r13 = "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;";
        r3.append(r13);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r3 = r3.toString();	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r3 = r15.rawQuery(r3, r12);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r12 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x025c, all -> 0x0040 }
        if (r12 != 0) goto L_0x008d;
    L_0x0086:
        if (r3 == 0) goto L_0x027e;
    L_0x0088:
        r3.close();	 Catch:{ all -> 0x0a8b }
        goto L_0x027e;
    L_0x008d:
        r12 = r3.getString(r10);	 Catch:{ SQLiteException -> 0x025c, all -> 0x0040 }
        r13 = r3.getString(r11);	 Catch:{ SQLiteException -> 0x009e, all -> 0x0040 }
        r3.close();	 Catch:{ SQLiteException -> 0x009e, all -> 0x0040 }
        r22 = r3;
        r3 = r12;
        r21 = r13;
        goto L_0x00f9;
    L_0x009e:
        r0 = move-exception;
        r9 = r3;
        goto L_0x0048;
    L_0x00a1:
        r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        if (r3 == 0) goto L_0x00b2;
    L_0x00a5:
        r3 = new java.lang.String[r9];	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r12 = 0;
        r3[r10] = r12;	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r12 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r3[r11] = r12;	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r12 = r3;
        goto L_0x00b7;
    L_0x00b2:
        r3 = 0;
        r12 = new java.lang.String[]{r3};	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
    L_0x00b7:
        r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        if (r3 == 0) goto L_0x00be;
    L_0x00bb:
        r3 = " and rowid <= ?";
        goto L_0x00c0;
    L_0x00be:
        r3 = "";
    L_0x00c0:
        r13 = java.lang.String.valueOf(r3);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r13 = r13.length();	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r13 = r13 + 84;
        r14 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r14.<init>(r13);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r13 = "select metadata_fingerprint from raw_events where app_id = ?";
        r14.append(r13);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r14.append(r3);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r3 = " order by rowid limit 1;";
        r14.append(r3);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r3 = r14.toString();	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r3 = r15.rawQuery(r3, r12);	 Catch:{ SQLiteException -> 0x0266, all -> 0x0261 }
        r12 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x025c, all -> 0x0040 }
        if (r12 != 0) goto L_0x00ed;
    L_0x00ea:
        if (r3 == 0) goto L_0x027e;
    L_0x00ec:
        goto L_0x0088;
    L_0x00ed:
        r13 = r3.getString(r10);	 Catch:{ SQLiteException -> 0x025c, all -> 0x0040 }
        r3.close();	 Catch:{ SQLiteException -> 0x025c, all -> 0x0040 }
        r22 = r3;
        r21 = r13;
        r3 = 0;
    L_0x00f9:
        r13 = "raw_events_metadata";
        r12 = "metadata";
        r14 = new java.lang.String[]{r12};	 Catch:{ SQLiteException -> 0x0256, all -> 0x0250 }
        r16 = "app_id = ? and metadata_fingerprint = ?";
        r12 = new java.lang.String[r9];	 Catch:{ SQLiteException -> 0x0256, all -> 0x0250 }
        r12[r10] = r3;	 Catch:{ SQLiteException -> 0x0256, all -> 0x0250 }
        r12[r11] = r21;	 Catch:{ SQLiteException -> 0x0256, all -> 0x0250 }
        r17 = 0;
        r18 = 0;
        r19 = "rowid";
        r20 = "2";
        r23 = r12;
        r12 = r15;
        r24 = r15;
        r15 = r16;
        r16 = r23;
        r15 = r12.query(r13, r14, r15, r16, r17, r18, r19, r20);	 Catch:{ SQLiteException -> 0x0256, all -> 0x0250 }
        r12 = r15.moveToFirst();	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        if (r12 != 0) goto L_0x0146;
    L_0x0124:
        r5 = r4.zzt();	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r5 = r5.zzy();	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r6 = "Raw event metadata record is missing. appId";
        r12 = com.google.android.gms.internal.zzcjj.zza(r3);	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r5.zza(r6, r12);	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        if (r15 == 0) goto L_0x027e;
    L_0x0137:
        r15.close();	 Catch:{ all -> 0x0a8b }
        goto L_0x027e;
    L_0x013c:
        r0 = move-exception;
        r2 = r0;
        r9 = r15;
        goto L_0x0a85;
    L_0x0141:
        r0 = move-exception;
        r12 = r3;
        r9 = r15;
        goto L_0x0048;
    L_0x0146:
        r12 = r15.getBlob(r10);	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r13 = r12.length;	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r12 = com.google.android.gms.internal.zzflj.zza(r12, r10, r13);	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r13 = new com.google.android.gms.internal.zzcoe;	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r13.<init>();	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r13.zza(r12);	 Catch:{ IOException -> 0x022c }
        r12 = r15.moveToNext();	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        if (r12 == 0) goto L_0x016e;
    L_0x015d:
        r12 = r4.zzt();	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r12 = r12.zzaa();	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r14 = "Get multiple raw event metadata records, expected one. appId";
        r9 = com.google.android.gms.internal.zzcjj.zza(r3);	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r12.zza(r14, r9);	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
    L_0x016e:
        r15.close();	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r2.zza(r13);	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        r14 = 3;
        if (r9 == 0) goto L_0x018c;
    L_0x0179:
        r9 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
        r12 = new java.lang.String[r14];	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r12[r10] = r3;	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r12[r11] = r21;	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r6 = 2;
        r12[r6] = r5;	 Catch:{ SQLiteException -> 0x0141, all -> 0x013c }
        r5 = r9;
        r16 = r12;
        goto L_0x0197;
    L_0x018c:
        r5 = "app_id = ? and metadata_fingerprint = ?";
        r6 = 2;
        r9 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r9[r10] = r3;	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r9[r11] = r21;	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r16 = r9;
    L_0x0197:
        r13 = "raw_events";
        r6 = "rowid";
        r9 = "name";
        r12 = "timestamp";
        r14 = "data";
        r14 = new java.lang.String[]{r6, r9, r12, r14};	 Catch:{ SQLiteException -> 0x024b, all -> 0x0247 }
        r17 = 0;
        r18 = 0;
        r19 = "rowid";
        r20 = 0;
        r12 = r24;
        r6 = 3;
        r9 = r15;
        r15 = r5;
        r5 = r12.query(r13, r14, r15, r16, r17, r18, r19, r20);	 Catch:{ SQLiteException -> 0x0245 }
        r9 = r5.moveToFirst();	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        if (r9 != 0) goto L_0x01d4;
    L_0x01bc:
        r6 = r4.zzt();	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r6 = r6.zzaa();	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r9 = "Raw event data disappeared while in transaction. appId";
        r12 = com.google.android.gms.internal.zzcjj.zza(r3);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r6.zza(r9, r12);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        if (r5 == 0) goto L_0x027e;
    L_0x01cf:
        r5.close();	 Catch:{ all -> 0x0a8b }
        goto L_0x027e;
    L_0x01d4:
        r12 = r5.getLong(r10);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r9 = r5.getBlob(r6);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r14 = r9.length;	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r9 = com.google.android.gms.internal.zzflj.zza(r9, r10, r14);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r14 = new com.google.android.gms.internal.zzcob;	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r14.<init>();	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r14.zza(r9);	 Catch:{ IOException -> 0x0203 }
        r9 = r5.getString(r11);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r14.zzb = r9;	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r9 = 2;
        r6 = r5.getLong(r9);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r14.zzc = r6;	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r6 = r2.zza(r12, r14);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        if (r6 != 0) goto L_0x0215;
    L_0x0200:
        if (r5 == 0) goto L_0x027e;
    L_0x0202:
        goto L_0x01cf;
    L_0x0203:
        r0 = move-exception;
        r6 = r4.zzt();	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r6 = r6.zzy();	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r7 = "Data loss. Failed to merge raw event. appId";
        r8 = com.google.android.gms.internal.zzcjj.zza(r3);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        r6.zza(r7, r8, r0);	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
    L_0x0215:
        r6 = r5.moveToNext();	 Catch:{ SQLiteException -> 0x0227, all -> 0x0222 }
        if (r6 != 0) goto L_0x021e;
    L_0x021b:
        if (r5 == 0) goto L_0x027e;
    L_0x021d:
        goto L_0x01cf;
    L_0x021e:
        r6 = 3;
        r7 = -1;
        goto L_0x01d4;
    L_0x0222:
        r0 = move-exception;
        r2 = r0;
        r9 = r5;
        goto L_0x0a85;
    L_0x0227:
        r0 = move-exception;
        r12 = r3;
        r9 = r5;
        goto L_0x0048;
    L_0x022c:
        r0 = move-exception;
        r9 = r15;
        r5 = r4.zzt();	 Catch:{ SQLiteException -> 0x0245 }
        r5 = r5.zzy();	 Catch:{ SQLiteException -> 0x0245 }
        r6 = "Data loss. Failed to merge raw event metadata. appId";
        r7 = com.google.android.gms.internal.zzcjj.zza(r3);	 Catch:{ SQLiteException -> 0x0245 }
        r5.zza(r6, r7, r0);	 Catch:{ SQLiteException -> 0x0245 }
        if (r9 == 0) goto L_0x027e;
    L_0x0241:
        r9.close();	 Catch:{ all -> 0x0a8b }
        goto L_0x027e;
    L_0x0245:
        r0 = move-exception;
        goto L_0x024d;
    L_0x0247:
        r0 = move-exception;
        r9 = r15;
        goto L_0x0a84;
    L_0x024b:
        r0 = move-exception;
        r9 = r15;
    L_0x024d:
        r12 = r3;
        goto L_0x0048;
    L_0x0250:
        r0 = move-exception;
        r2 = r0;
        r9 = r22;
        goto L_0x0a85;
    L_0x0256:
        r0 = move-exception;
        r12 = r3;
        r9 = r22;
        goto L_0x0048;
    L_0x025c:
        r0 = move-exception;
        r9 = r3;
        r12 = 0;
        goto L_0x0048;
    L_0x0261:
        r0 = move-exception;
        r2 = r0;
        r9 = 0;
        goto L_0x0a85;
    L_0x0266:
        r0 = move-exception;
        r3 = r0;
        r9 = 0;
        r12 = 0;
    L_0x026a:
        r4 = r4.zzt();	 Catch:{ all -> 0x0a83 }
        r4 = r4.zzy();	 Catch:{ all -> 0x0a83 }
        r5 = "Data loss. Error selecting raw event. appId";
        r6 = com.google.android.gms.internal.zzcjj.zza(r12);	 Catch:{ all -> 0x0a83 }
        r4.zza(r5, r6, r3);	 Catch:{ all -> 0x0a83 }
        if (r9 == 0) goto L_0x027e;
    L_0x027d:
        goto L_0x0241;
    L_0x027e:
        r3 = r2.zzc;	 Catch:{ all -> 0x0a8b }
        if (r3 == 0) goto L_0x028d;
    L_0x0282:
        r3 = r2.zzc;	 Catch:{ all -> 0x0a8b }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0a8b }
        if (r3 == 0) goto L_0x028b;
    L_0x028a:
        goto L_0x028d;
    L_0x028b:
        r3 = 0;
        goto L_0x028e;
    L_0x028d:
        r3 = 1;
    L_0x028e:
        if (r3 != 0) goto L_0x0a73;
    L_0x0290:
        r3 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r4 = r2.zzc;	 Catch:{ all -> 0x0a8b }
        r4 = r4.size();	 Catch:{ all -> 0x0a8b }
        r4 = new com.google.android.gms.internal.zzcob[r4];	 Catch:{ all -> 0x0a8b }
        r3.zzb = r4;	 Catch:{ all -> 0x0a8b }
        r4 = r1.zzc;	 Catch:{ all -> 0x0a8b }
        r5 = r3.zzo;	 Catch:{ all -> 0x0a8b }
        r4 = r4.zzd(r5);	 Catch:{ all -> 0x0a8b }
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r12 = 0;
    L_0x02a9:
        r14 = r2.zzc;	 Catch:{ all -> 0x0a8b }
        r14 = r14.size();	 Catch:{ all -> 0x0a8b }
        if (r7 >= r14) goto L_0x059e;
    L_0x02b1:
        r14 = r2.zzc;	 Catch:{ all -> 0x0a8b }
        r14 = r14.get(r7);	 Catch:{ all -> 0x0a8b }
        r14 = (com.google.android.gms.internal.zzcob) r14;	 Catch:{ all -> 0x0a8b }
        r15 = r60.zzj();	 Catch:{ all -> 0x0a8b }
        r11 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r11 = r11.zzo;	 Catch:{ all -> 0x0a8b }
        r5 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r5 = r15.zzb(r11, r5);	 Catch:{ all -> 0x0a8b }
        if (r5 == 0) goto L_0x032f;
    L_0x02c9:
        r5 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzaa();	 Catch:{ all -> 0x0a8b }
        r6 = "Dropping blacklisted raw event. appId";
        r11 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r11 = r11.zzo;	 Catch:{ all -> 0x0a8b }
        r11 = com.google.android.gms.internal.zzcjj.zza(r11);	 Catch:{ all -> 0x0a8b }
        r15 = r60.zzp();	 Catch:{ all -> 0x0a8b }
        r10 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r10 = r15.zza(r10);	 Catch:{ all -> 0x0a8b }
        r5.zza(r6, r11, r10);	 Catch:{ all -> 0x0a8b }
        r5 = r60.zzo();	 Catch:{ all -> 0x0a8b }
        r6 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r6 = r6.zzo;	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzk(r6);	 Catch:{ all -> 0x0a8b }
        if (r5 != 0) goto L_0x0307;
    L_0x02f6:
        r5 = r60.zzo();	 Catch:{ all -> 0x0a8b }
        r6 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r6 = r6.zzo;	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzl(r6);	 Catch:{ all -> 0x0a8b }
        if (r5 == 0) goto L_0x0305;
    L_0x0304:
        goto L_0x0307;
    L_0x0305:
        r5 = 0;
        goto L_0x0308;
    L_0x0307:
        r5 = 1;
    L_0x0308:
        if (r5 != 0) goto L_0x032b;
    L_0x030a:
        r5 = "_err";
        r6 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r5 = r5.equals(r6);	 Catch:{ all -> 0x0a8b }
        if (r5 != 0) goto L_0x032b;
    L_0x0314:
        r15 = r60.zzo();	 Catch:{ all -> 0x0a8b }
        r5 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzo;	 Catch:{ all -> 0x0a8b }
        r17 = 11;
        r18 = "_ev";
        r6 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r20 = 0;
        r16 = r5;
        r19 = r6;
        r15.zza(r16, r17, r18, r19, r20);	 Catch:{ all -> 0x0a8b }
    L_0x032b:
        r28 = r7;
        goto L_0x0598;
    L_0x032f:
        r5 = r60.zzj();	 Catch:{ all -> 0x0a8b }
        r6 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r6 = r6.zzo;	 Catch:{ all -> 0x0a8b }
        r10 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzc(r6, r10);	 Catch:{ all -> 0x0a8b }
        if (r5 != 0) goto L_0x034f;
    L_0x033f:
        r60.zzo();	 Catch:{ all -> 0x0a8b }
        r6 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r6 = com.google.android.gms.internal.zzcno.zzm(r6);	 Catch:{ all -> 0x0a8b }
        if (r6 == 0) goto L_0x034b;
    L_0x034a:
        goto L_0x034f;
    L_0x034b:
        r28 = r7;
        goto L_0x053b;
    L_0x034f:
        r6 = r14.zza;	 Catch:{ all -> 0x0a8b }
        if (r6 != 0) goto L_0x0358;
    L_0x0353:
        r6 = 0;
        r10 = new com.google.android.gms.internal.zzcoc[r6];	 Catch:{ all -> 0x0a8b }
        r14.zza = r10;	 Catch:{ all -> 0x0a8b }
    L_0x0358:
        r6 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r10 = r6.length;	 Catch:{ all -> 0x0a8b }
        r11 = 0;
        r15 = 0;
        r16 = 0;
    L_0x035f:
        if (r11 >= r10) goto L_0x03a0;
    L_0x0361:
        r25 = r10;
        r10 = r6[r11];	 Catch:{ all -> 0x0a8b }
        r26 = r6;
        r6 = "_c";
        r27 = r8;
        r8 = r10.zza;	 Catch:{ all -> 0x0a8b }
        r6 = r6.equals(r8);	 Catch:{ all -> 0x0a8b }
        if (r6 == 0) goto L_0x037f;
    L_0x0373:
        r28 = r7;
        r6 = 1;
        r8 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0a8b }
        r10.zzc = r8;	 Catch:{ all -> 0x0a8b }
        r15 = 1;
        goto L_0x0395;
    L_0x037f:
        r28 = r7;
        r6 = "_r";
        r7 = r10.zza;	 Catch:{ all -> 0x0a8b }
        r6 = r6.equals(r7);	 Catch:{ all -> 0x0a8b }
        if (r6 == 0) goto L_0x0395;
    L_0x038b:
        r6 = 1;
        r8 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0a8b }
        r10.zzc = r8;	 Catch:{ all -> 0x0a8b }
        r16 = 1;
    L_0x0395:
        r11 = r11 + 1;
        r10 = r25;
        r6 = r26;
        r8 = r27;
        r7 = r28;
        goto L_0x035f;
    L_0x03a0:
        r28 = r7;
        r27 = r8;
        if (r15 != 0) goto L_0x03e4;
    L_0x03a6:
        if (r5 == 0) goto L_0x03e4;
    L_0x03a8:
        r6 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r6 = r6.zzae();	 Catch:{ all -> 0x0a8b }
        r7 = "Marking event as conversion";
        r8 = r60.zzp();	 Catch:{ all -> 0x0a8b }
        r10 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r8 = r8.zza(r10);	 Catch:{ all -> 0x0a8b }
        r6.zza(r7, r8);	 Catch:{ all -> 0x0a8b }
        r6 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.length;	 Catch:{ all -> 0x0a8b }
        r8 = 1;
        r7 = r7 + r8;
        r6 = java.util.Arrays.copyOf(r6, r7);	 Catch:{ all -> 0x0a8b }
        r6 = (com.google.android.gms.internal.zzcoc[]) r6;	 Catch:{ all -> 0x0a8b }
        r7 = new com.google.android.gms.internal.zzcoc;	 Catch:{ all -> 0x0a8b }
        r7.<init>();	 Catch:{ all -> 0x0a8b }
        r8 = "_c";
        r7.zza = r8;	 Catch:{ all -> 0x0a8b }
        r10 = 1;
        r8 = java.lang.Long.valueOf(r10);	 Catch:{ all -> 0x0a8b }
        r7.zzc = r8;	 Catch:{ all -> 0x0a8b }
        r8 = r6.length;	 Catch:{ all -> 0x0a8b }
        r10 = 1;
        r8 = r8 - r10;
        r6[r8] = r7;	 Catch:{ all -> 0x0a8b }
        r14.zza = r6;	 Catch:{ all -> 0x0a8b }
    L_0x03e4:
        if (r16 != 0) goto L_0x0422;
    L_0x03e6:
        r6 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r6 = r6.zzae();	 Catch:{ all -> 0x0a8b }
        r7 = "Marking event as real-time";
        r8 = r60.zzp();	 Catch:{ all -> 0x0a8b }
        r10 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r8 = r8.zza(r10);	 Catch:{ all -> 0x0a8b }
        r6.zza(r7, r8);	 Catch:{ all -> 0x0a8b }
        r6 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.length;	 Catch:{ all -> 0x0a8b }
        r8 = 1;
        r7 = r7 + r8;
        r6 = java.util.Arrays.copyOf(r6, r7);	 Catch:{ all -> 0x0a8b }
        r6 = (com.google.android.gms.internal.zzcoc[]) r6;	 Catch:{ all -> 0x0a8b }
        r7 = new com.google.android.gms.internal.zzcoc;	 Catch:{ all -> 0x0a8b }
        r7.<init>();	 Catch:{ all -> 0x0a8b }
        r8 = "_r";
        r7.zza = r8;	 Catch:{ all -> 0x0a8b }
        r10 = 1;
        r8 = java.lang.Long.valueOf(r10);	 Catch:{ all -> 0x0a8b }
        r7.zzc = r8;	 Catch:{ all -> 0x0a8b }
        r8 = r6.length;	 Catch:{ all -> 0x0a8b }
        r10 = 1;
        r8 = r8 - r10;
        r6[r8] = r7;	 Catch:{ all -> 0x0a8b }
        r14.zza = r6;	 Catch:{ all -> 0x0a8b }
    L_0x0422:
        r29 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r30 = r60.zzak();	 Catch:{ all -> 0x0a8b }
        r6 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r6 = r6.zzo;	 Catch:{ all -> 0x0a8b }
        r33 = 0;
        r34 = 0;
        r35 = 0;
        r36 = 0;
        r37 = 1;
        r32 = r6;
        r6 = r29.zza(r30, r32, r33, r34, r35, r36, r37);	 Catch:{ all -> 0x0a8b }
        r6 = r6.zze;	 Catch:{ all -> 0x0a8b }
        r8 = r1.zzc;	 Catch:{ all -> 0x0a8b }
        r10 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r10 = r10.zzo;	 Catch:{ all -> 0x0a8b }
        r8 = r8.zza(r10);	 Catch:{ all -> 0x0a8b }
        r10 = (long) r8;	 Catch:{ all -> 0x0a8b }
        r8 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
        if (r8 <= 0) goto L_0x0487;
    L_0x044f:
        r6 = 0;
    L_0x0450:
        r7 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.length;	 Catch:{ all -> 0x0a8b }
        if (r6 >= r7) goto L_0x0484;
    L_0x0455:
        r7 = "_r";
        r8 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r8 = r8[r6];	 Catch:{ all -> 0x0a8b }
        r8 = r8.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.equals(r8);	 Catch:{ all -> 0x0a8b }
        if (r7 == 0) goto L_0x0481;
    L_0x0463:
        r7 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.length;	 Catch:{ all -> 0x0a8b }
        r8 = 1;
        r7 = r7 - r8;
        r7 = new com.google.android.gms.internal.zzcoc[r7];	 Catch:{ all -> 0x0a8b }
        if (r6 <= 0) goto L_0x0472;
    L_0x046c:
        r8 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r10 = 0;
        java.lang.System.arraycopy(r8, r10, r7, r10, r6);	 Catch:{ all -> 0x0a8b }
    L_0x0472:
        r8 = r7.length;	 Catch:{ all -> 0x0a8b }
        if (r6 >= r8) goto L_0x047e;
    L_0x0475:
        r8 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r10 = r6 + 1;
        r11 = r7.length;	 Catch:{ all -> 0x0a8b }
        r11 = r11 - r6;
        java.lang.System.arraycopy(r8, r10, r7, r6, r11);	 Catch:{ all -> 0x0a8b }
    L_0x047e:
        r14.zza = r7;	 Catch:{ all -> 0x0a8b }
        goto L_0x0484;
    L_0x0481:
        r6 = r6 + 1;
        goto L_0x0450;
    L_0x0484:
        r8 = r27;
        goto L_0x0488;
    L_0x0487:
        r8 = 1;
    L_0x0488:
        r6 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r6 = com.google.android.gms.internal.zzcno.zza(r6);	 Catch:{ all -> 0x0a8b }
        if (r6 == 0) goto L_0x053b;
    L_0x0490:
        if (r5 == 0) goto L_0x053b;
    L_0x0492:
        r29 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r30 = r60.zzak();	 Catch:{ all -> 0x0a8b }
        r5 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzo;	 Catch:{ all -> 0x0a8b }
        r33 = 0;
        r34 = 0;
        r35 = 1;
        r36 = 0;
        r37 = 0;
        r32 = r5;
        r5 = r29.zza(r30, r32, r33, r34, r35, r36, r37);	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzc;	 Catch:{ all -> 0x0a8b }
        r7 = r1.zzc;	 Catch:{ all -> 0x0a8b }
        r10 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r10 = r10.zzo;	 Catch:{ all -> 0x0a8b }
        r11 = com.google.android.gms.internal.zzciz.zzt;	 Catch:{ all -> 0x0a8b }
        r7 = r7.zzb(r10, r11);	 Catch:{ all -> 0x0a8b }
        r10 = (long) r7;	 Catch:{ all -> 0x0a8b }
        r7 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1));
        if (r7 <= 0) goto L_0x053b;
    L_0x04c1:
        r5 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzaa();	 Catch:{ all -> 0x0a8b }
        r6 = "Too many conversions. Not logging as conversion. appId";
        r7 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.zzo;	 Catch:{ all -> 0x0a8b }
        r7 = com.google.android.gms.internal.zzcjj.zza(r7);	 Catch:{ all -> 0x0a8b }
        r5.zza(r6, r7);	 Catch:{ all -> 0x0a8b }
        r5 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r6 = r5.length;	 Catch:{ all -> 0x0a8b }
        r7 = 0;
        r10 = 0;
        r11 = 0;
    L_0x04dc:
        if (r7 >= r6) goto L_0x0502;
    L_0x04de:
        r15 = r5[r7];	 Catch:{ all -> 0x0a8b }
        r38 = r5;
        r5 = "_c";
        r39 = r6;
        r6 = r15.zza;	 Catch:{ all -> 0x0a8b }
        r5 = r5.equals(r6);	 Catch:{ all -> 0x0a8b }
        if (r5 == 0) goto L_0x04f0;
    L_0x04ee:
        r11 = r15;
        goto L_0x04fb;
    L_0x04f0:
        r5 = "_err";
        r6 = r15.zza;	 Catch:{ all -> 0x0a8b }
        r5 = r5.equals(r6);	 Catch:{ all -> 0x0a8b }
        if (r5 == 0) goto L_0x04fb;
    L_0x04fa:
        r10 = 1;
    L_0x04fb:
        r7 = r7 + 1;
        r5 = r38;
        r6 = r39;
        goto L_0x04dc;
    L_0x0502:
        if (r10 == 0) goto L_0x0517;
    L_0x0504:
        if (r11 == 0) goto L_0x0517;
    L_0x0506:
        r5 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r6 = 1;
        r7 = new com.google.android.gms.internal.zzcoc[r6];	 Catch:{ all -> 0x0a8b }
        r6 = 0;
        r7[r6] = r11;	 Catch:{ all -> 0x0a8b }
        r5 = com.google.android.gms.common.util.zzb.zza(r5, r7);	 Catch:{ all -> 0x0a8b }
        r5 = (com.google.android.gms.internal.zzcoc[]) r5;	 Catch:{ all -> 0x0a8b }
        r14.zza = r5;	 Catch:{ all -> 0x0a8b }
        goto L_0x053b;
    L_0x0517:
        if (r11 == 0) goto L_0x0526;
    L_0x0519:
        r5 = "_err";
        r11.zza = r5;	 Catch:{ all -> 0x0a8b }
        r5 = 10;
        r5 = java.lang.Long.valueOf(r5);	 Catch:{ all -> 0x0a8b }
        r11.zzc = r5;	 Catch:{ all -> 0x0a8b }
        goto L_0x053b;
    L_0x0526:
        r5 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzy();	 Catch:{ all -> 0x0a8b }
        r6 = "Did not find conversion parameter. appId";
        r7 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.zzo;	 Catch:{ all -> 0x0a8b }
        r7 = com.google.android.gms.internal.zzcjj.zza(r7);	 Catch:{ all -> 0x0a8b }
        r5.zza(r6, r7);	 Catch:{ all -> 0x0a8b }
    L_0x053b:
        if (r4 == 0) goto L_0x058f;
    L_0x053d:
        r5 = "_e";
        r6 = r14.zzb;	 Catch:{ all -> 0x0a8b }
        r5 = r5.equals(r6);	 Catch:{ all -> 0x0a8b }
        if (r5 == 0) goto L_0x058f;
    L_0x0547:
        r5 = r14.zza;	 Catch:{ all -> 0x0a8b }
        if (r5 == 0) goto L_0x057c;
    L_0x054b:
        r5 = r14.zza;	 Catch:{ all -> 0x0a8b }
        r5 = r5.length;	 Catch:{ all -> 0x0a8b }
        if (r5 != 0) goto L_0x0551;
    L_0x0550:
        goto L_0x057c;
    L_0x0551:
        r60.zzo();	 Catch:{ all -> 0x0a8b }
        r5 = "_et";
        r5 = com.google.android.gms.internal.zzcno.zzb(r14, r5);	 Catch:{ all -> 0x0a8b }
        r5 = (java.lang.Long) r5;	 Catch:{ all -> 0x0a8b }
        if (r5 != 0) goto L_0x0574;
    L_0x055e:
        r5 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzaa();	 Catch:{ all -> 0x0a8b }
        r6 = "Engagement event does not include duration. appId";
        r7 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.zzo;	 Catch:{ all -> 0x0a8b }
        r7 = com.google.android.gms.internal.zzcjj.zza(r7);	 Catch:{ all -> 0x0a8b }
    L_0x0570:
        r5.zza(r6, r7);	 Catch:{ all -> 0x0a8b }
        goto L_0x058f;
    L_0x0574:
        r5 = r5.longValue();	 Catch:{ all -> 0x0a8b }
        r7 = 0;
        r10 = r12 + r5;
        goto L_0x0590;
    L_0x057c:
        r5 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzaa();	 Catch:{ all -> 0x0a8b }
        r6 = "Engagement event does not contain any parameters. appId";
        r7 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.zzo;	 Catch:{ all -> 0x0a8b }
        r7 = com.google.android.gms.internal.zzcjj.zza(r7);	 Catch:{ all -> 0x0a8b }
        goto L_0x0570;
    L_0x058f:
        r10 = r12;
    L_0x0590:
        r5 = r3.zzb;	 Catch:{ all -> 0x0a8b }
        r6 = r9 + 1;
        r5[r9] = r14;	 Catch:{ all -> 0x0a8b }
        r9 = r6;
        r12 = r10;
    L_0x0598:
        r7 = r28 + 1;
        r10 = 0;
        r11 = 1;
        goto L_0x02a9;
    L_0x059e:
        r27 = r8;
        r5 = r2.zzc;	 Catch:{ all -> 0x0a8b }
        r5 = r5.size();	 Catch:{ all -> 0x0a8b }
        if (r9 >= r5) goto L_0x05b2;
    L_0x05a8:
        r5 = r3.zzb;	 Catch:{ all -> 0x0a8b }
        r5 = java.util.Arrays.copyOf(r5, r9);	 Catch:{ all -> 0x0a8b }
        r5 = (com.google.android.gms.internal.zzcob[]) r5;	 Catch:{ all -> 0x0a8b }
        r3.zzb = r5;	 Catch:{ all -> 0x0a8b }
    L_0x05b2:
        if (r4 == 0) goto L_0x0674;
    L_0x05b4:
        r4 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r5 = r3.zzo;	 Catch:{ all -> 0x0a8b }
        r6 = "_lte";
        r4 = r4.zzc(r5, r6);	 Catch:{ all -> 0x0a8b }
        if (r4 == 0) goto L_0x05ea;
    L_0x05c2:
        r5 = r4.zze;	 Catch:{ all -> 0x0a8b }
        if (r5 != 0) goto L_0x05c7;
    L_0x05c6:
        goto L_0x05ea;
    L_0x05c7:
        r5 = new com.google.android.gms.internal.zzcnn;	 Catch:{ all -> 0x0a8b }
        r15 = r3.zzo;	 Catch:{ all -> 0x0a8b }
        r16 = "auto";
        r17 = "_lte";
        r6 = r1.zzn;	 Catch:{ all -> 0x0a8b }
        r18 = r6.zza();	 Catch:{ all -> 0x0a8b }
        r4 = r4.zze;	 Catch:{ all -> 0x0a8b }
        r4 = (java.lang.Long) r4;	 Catch:{ all -> 0x0a8b }
        r6 = r4.longValue();	 Catch:{ all -> 0x0a8b }
        r4 = 0;
        r8 = r6 + r12;
        r20 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x0a8b }
        r14 = r5;
        r14.<init>(r15, r16, r17, r18, r20);	 Catch:{ all -> 0x0a8b }
        r4 = r5;
        goto L_0x0603;
    L_0x05ea:
        r4 = new com.google.android.gms.internal.zzcnn;	 Catch:{ all -> 0x0a8b }
        r5 = r3.zzo;	 Catch:{ all -> 0x0a8b }
        r30 = "auto";
        r31 = "_lte";
        r6 = r1.zzn;	 Catch:{ all -> 0x0a8b }
        r32 = r6.zza();	 Catch:{ all -> 0x0a8b }
        r34 = java.lang.Long.valueOf(r12);	 Catch:{ all -> 0x0a8b }
        r28 = r4;
        r29 = r5;
        r28.<init>(r29, r30, r31, r32, r34);	 Catch:{ all -> 0x0a8b }
    L_0x0603:
        r5 = new com.google.android.gms.internal.zzcog;	 Catch:{ all -> 0x0a8b }
        r5.<init>();	 Catch:{ all -> 0x0a8b }
        r6 = "_lte";
        r5.zzb = r6;	 Catch:{ all -> 0x0a8b }
        r6 = r1.zzn;	 Catch:{ all -> 0x0a8b }
        r6 = r6.zza();	 Catch:{ all -> 0x0a8b }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0a8b }
        r5.zza = r6;	 Catch:{ all -> 0x0a8b }
        r6 = r4.zze;	 Catch:{ all -> 0x0a8b }
        r6 = (java.lang.Long) r6;	 Catch:{ all -> 0x0a8b }
        r5.zzd = r6;	 Catch:{ all -> 0x0a8b }
        r6 = 0;
    L_0x061f:
        r7 = r3.zzc;	 Catch:{ all -> 0x0a8b }
        r7 = r7.length;	 Catch:{ all -> 0x0a8b }
        if (r6 >= r7) goto L_0x063b;
    L_0x0624:
        r7 = "_lte";
        r8 = r3.zzc;	 Catch:{ all -> 0x0a8b }
        r8 = r8[r6];	 Catch:{ all -> 0x0a8b }
        r8 = r8.zzb;	 Catch:{ all -> 0x0a8b }
        r7 = r7.equals(r8);	 Catch:{ all -> 0x0a8b }
        if (r7 == 0) goto L_0x0638;
    L_0x0632:
        r7 = r3.zzc;	 Catch:{ all -> 0x0a8b }
        r7[r6] = r5;	 Catch:{ all -> 0x0a8b }
        r6 = 1;
        goto L_0x063c;
    L_0x0638:
        r6 = r6 + 1;
        goto L_0x061f;
    L_0x063b:
        r6 = 0;
    L_0x063c:
        if (r6 != 0) goto L_0x0658;
    L_0x063e:
        r6 = r3.zzc;	 Catch:{ all -> 0x0a8b }
        r7 = r3.zzc;	 Catch:{ all -> 0x0a8b }
        r7 = r7.length;	 Catch:{ all -> 0x0a8b }
        r8 = 1;
        r7 = r7 + r8;
        r6 = java.util.Arrays.copyOf(r6, r7);	 Catch:{ all -> 0x0a8b }
        r6 = (com.google.android.gms.internal.zzcog[]) r6;	 Catch:{ all -> 0x0a8b }
        r3.zzc = r6;	 Catch:{ all -> 0x0a8b }
        r6 = r3.zzc;	 Catch:{ all -> 0x0a8b }
        r7 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.zzc;	 Catch:{ all -> 0x0a8b }
        r7 = r7.length;	 Catch:{ all -> 0x0a8b }
        r8 = 1;
        r7 = r7 - r8;
        r6[r7] = r5;	 Catch:{ all -> 0x0a8b }
    L_0x0658:
        r5 = 0;
        r7 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1));
        if (r7 <= 0) goto L_0x0674;
    L_0x065e:
        r5 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r5.zza(r4);	 Catch:{ all -> 0x0a8b }
        r5 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzad();	 Catch:{ all -> 0x0a8b }
        r6 = "Updated lifetime engagement user property with value. Value";
        r4 = r4.zze;	 Catch:{ all -> 0x0a8b }
        r5.zza(r6, r4);	 Catch:{ all -> 0x0a8b }
    L_0x0674:
        r4 = r3.zzo;	 Catch:{ all -> 0x0a8b }
        r5 = r3.zzc;	 Catch:{ all -> 0x0a8b }
        r6 = r3.zzb;	 Catch:{ all -> 0x0a8b }
        r4 = r1.zza(r4, r5, r6);	 Catch:{ all -> 0x0a8b }
        r3.zzaa = r4;	 Catch:{ all -> 0x0a8b }
        r4 = com.google.android.gms.internal.zzciz.zzf;	 Catch:{ all -> 0x0a8b }
        r4 = r4.zzb();	 Catch:{ all -> 0x0a8b }
        r4 = (java.lang.Boolean) r4;	 Catch:{ all -> 0x0a8b }
        r4 = r4.booleanValue();	 Catch:{ all -> 0x0a8b }
        if (r4 == 0) goto L_0x0918;
    L_0x068e:
        r4 = r1.zzc;	 Catch:{ all -> 0x0a8b }
        r5 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzo;	 Catch:{ all -> 0x0a8b }
        r6 = "1";
        r4 = r4.zzq();	 Catch:{ all -> 0x0a8b }
        r7 = "measurement.event_sampling_enabled";
        r4 = r4.zza(r5, r7);	 Catch:{ all -> 0x0a8b }
        r4 = r6.equals(r4);	 Catch:{ all -> 0x0a8b }
        if (r4 == 0) goto L_0x0918;
    L_0x06a6:
        r4 = new java.util.HashMap;	 Catch:{ all -> 0x0a8b }
        r4.<init>();	 Catch:{ all -> 0x0a8b }
        r5 = r3.zzb;	 Catch:{ all -> 0x0a8b }
        r5 = r5.length;	 Catch:{ all -> 0x0a8b }
        r5 = new com.google.android.gms.internal.zzcob[r5];	 Catch:{ all -> 0x0a8b }
        r6 = r60.zzo();	 Catch:{ all -> 0x0a8b }
        r6 = r6.zzz();	 Catch:{ all -> 0x0a8b }
        r7 = r3.zzb;	 Catch:{ all -> 0x0a8b }
        r8 = r7.length;	 Catch:{ all -> 0x0a8b }
        r9 = 0;
        r10 = 0;
    L_0x06bd:
        if (r9 >= r8) goto L_0x08e5;
    L_0x06bf:
        r11 = r7[r9];	 Catch:{ all -> 0x0a8b }
        r12 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r13 = "_ep";
        r12 = r12.equals(r13);	 Catch:{ all -> 0x0a8b }
        if (r12 == 0) goto L_0x073e;
    L_0x06cb:
        r60.zzo();	 Catch:{ all -> 0x0a8b }
        r12 = "_en";
        r12 = com.google.android.gms.internal.zzcno.zzb(r11, r12);	 Catch:{ all -> 0x0a8b }
        r12 = (java.lang.String) r12;	 Catch:{ all -> 0x0a8b }
        r13 = r4.get(r12);	 Catch:{ all -> 0x0a8b }
        r13 = (com.google.android.gms.internal.zzcit) r13;	 Catch:{ all -> 0x0a8b }
        if (r13 != 0) goto L_0x06ed;
    L_0x06de:
        r13 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r14 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r14 = r14.zzo;	 Catch:{ all -> 0x0a8b }
        r13 = r13.zza(r14, r12);	 Catch:{ all -> 0x0a8b }
        r4.put(r12, r13);	 Catch:{ all -> 0x0a8b }
    L_0x06ed:
        r12 = r13.zzg;	 Catch:{ all -> 0x0a8b }
        if (r12 != 0) goto L_0x0735;
    L_0x06f1:
        r12 = r13.zzh;	 Catch:{ all -> 0x0a8b }
        r14 = r12.longValue();	 Catch:{ all -> 0x0a8b }
        r16 = 1;
        r12 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r12 <= 0) goto L_0x070c;
    L_0x06fd:
        r60.zzo();	 Catch:{ all -> 0x0a8b }
        r12 = r11.zza;	 Catch:{ all -> 0x0a8b }
        r14 = "_sr";
        r15 = r13.zzh;	 Catch:{ all -> 0x0a8b }
        r12 = com.google.android.gms.internal.zzcno.zza(r12, r14, r15);	 Catch:{ all -> 0x0a8b }
        r11.zza = r12;	 Catch:{ all -> 0x0a8b }
    L_0x070c:
        r12 = r13.zzi;	 Catch:{ all -> 0x0a8b }
        if (r12 == 0) goto L_0x072e;
    L_0x0710:
        r12 = r13.zzi;	 Catch:{ all -> 0x0a8b }
        r12 = r12.booleanValue();	 Catch:{ all -> 0x0a8b }
        if (r12 == 0) goto L_0x072e;
    L_0x0718:
        r60.zzo();	 Catch:{ all -> 0x0a8b }
        r12 = r11.zza;	 Catch:{ all -> 0x0a8b }
        r13 = "_efs";
        r40 = r7;
        r14 = 1;
        r7 = java.lang.Long.valueOf(r14);	 Catch:{ all -> 0x0a8b }
        r7 = com.google.android.gms.internal.zzcno.zza(r12, r13, r7);	 Catch:{ all -> 0x0a8b }
        r11.zza = r7;	 Catch:{ all -> 0x0a8b }
        goto L_0x0730;
    L_0x072e:
        r40 = r7;
    L_0x0730:
        r7 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0a8b }
        goto L_0x0777;
    L_0x0735:
        r40 = r7;
        r57 = r2;
        r58 = r3;
        r55 = r6;
        goto L_0x077e;
    L_0x073e:
        r40 = r7;
        r7 = "_dbg";
        r12 = 1;
        r14 = java.lang.Long.valueOf(r12);	 Catch:{ all -> 0x0a8b }
        r7 = zza(r11, r7, r14);	 Catch:{ all -> 0x0a8b }
        if (r7 != 0) goto L_0x075d;
    L_0x074e:
        r7 = r60.zzj();	 Catch:{ all -> 0x0a8b }
        r12 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r12 = r12.zzo;	 Catch:{ all -> 0x0a8b }
        r13 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r7 = r7.zzd(r12, r13);	 Catch:{ all -> 0x0a8b }
        goto L_0x075e;
    L_0x075d:
        r7 = 1;
    L_0x075e:
        if (r7 > 0) goto L_0x0787;
    L_0x0760:
        r12 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r12 = r12.zzaa();	 Catch:{ all -> 0x0a8b }
        r13 = "Sample rate must be positive. event, rate";
        r14 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r7 = java.lang.Integer.valueOf(r7);	 Catch:{ all -> 0x0a8b }
        r12.zza(r13, r14, r7);	 Catch:{ all -> 0x0a8b }
        r7 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0a8b }
    L_0x0777:
        r57 = r2;
        r58 = r3;
        r55 = r6;
        r10 = r7;
    L_0x077e:
        r56 = r8;
    L_0x0780:
        r59 = r9;
        r3 = 0;
        r8 = 1;
        goto L_0x08d6;
    L_0x0787:
        r12 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r12 = r4.get(r12);	 Catch:{ all -> 0x0a8b }
        r12 = (com.google.android.gms.internal.zzcit) r12;	 Catch:{ all -> 0x0a8b }
        if (r12 != 0) goto L_0x07d7;
    L_0x0791:
        r12 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r13 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r13 = r13.zzo;	 Catch:{ all -> 0x0a8b }
        r14 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r12 = r12.zza(r13, r14);	 Catch:{ all -> 0x0a8b }
        if (r12 != 0) goto L_0x07d7;
    L_0x07a1:
        r12 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r12 = r12.zzaa();	 Catch:{ all -> 0x0a8b }
        r13 = "Event being bundled has no eventAggregate. appId, eventName";
        r14 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r14 = r14.zzo;	 Catch:{ all -> 0x0a8b }
        r15 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r12.zza(r13, r14, r15);	 Catch:{ all -> 0x0a8b }
        r12 = new com.google.android.gms.internal.zzcit;	 Catch:{ all -> 0x0a8b }
        r13 = r2.zza;	 Catch:{ all -> 0x0a8b }
        r13 = r13.zzo;	 Catch:{ all -> 0x0a8b }
        r14 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r44 = 1;
        r46 = 1;
        r15 = r11.zzc;	 Catch:{ all -> 0x0a8b }
        r48 = r15.longValue();	 Catch:{ all -> 0x0a8b }
        r50 = 0;
        r52 = 0;
        r53 = 0;
        r54 = 0;
        r41 = r12;
        r42 = r13;
        r43 = r14;
        r41.<init>(r42, r43, r44, r46, r48, r50, r52, r53, r54);	 Catch:{ all -> 0x0a8b }
    L_0x07d7:
        r60.zzo();	 Catch:{ all -> 0x0a8b }
        r13 = "_eid";
        r13 = com.google.android.gms.internal.zzcno.zzb(r11, r13);	 Catch:{ all -> 0x0a8b }
        r13 = (java.lang.Long) r13;	 Catch:{ all -> 0x0a8b }
        if (r13 == 0) goto L_0x07e6;
    L_0x07e4:
        r14 = 1;
        goto L_0x07e7;
    L_0x07e6:
        r14 = 0;
    L_0x07e7:
        r14 = java.lang.Boolean.valueOf(r14);	 Catch:{ all -> 0x0a8b }
        r15 = 1;
        if (r7 != r15) goto L_0x0810;
    L_0x07ee:
        r7 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0a8b }
        r10 = r14.booleanValue();	 Catch:{ all -> 0x0a8b }
        if (r10 == 0) goto L_0x0777;
    L_0x07f8:
        r10 = r12.zzg;	 Catch:{ all -> 0x0a8b }
        if (r10 != 0) goto L_0x0804;
    L_0x07fc:
        r10 = r12.zzh;	 Catch:{ all -> 0x0a8b }
        if (r10 != 0) goto L_0x0804;
    L_0x0800:
        r10 = r12.zzi;	 Catch:{ all -> 0x0a8b }
        if (r10 == 0) goto L_0x0777;
    L_0x0804:
        r10 = 0;
        r12 = r12.zza(r10, r10, r10);	 Catch:{ all -> 0x0a8b }
        r10 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r4.put(r10, r12);	 Catch:{ all -> 0x0a8b }
        goto L_0x0777;
    L_0x0810:
        r15 = r6.nextInt(r7);	 Catch:{ all -> 0x0a8b }
        if (r15 != 0) goto L_0x0855;
    L_0x0816:
        r60.zzo();	 Catch:{ all -> 0x0a8b }
        r13 = r11.zza;	 Catch:{ all -> 0x0a8b }
        r15 = "_sr";
        r55 = r6;
        r6 = (long) r7;	 Catch:{ all -> 0x0a8b }
        r56 = r8;
        r8 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0a8b }
        r8 = com.google.android.gms.internal.zzcno.zza(r13, r15, r8);	 Catch:{ all -> 0x0a8b }
        r11.zza = r8;	 Catch:{ all -> 0x0a8b }
        r8 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0a8b }
        r10 = r14.booleanValue();	 Catch:{ all -> 0x0a8b }
        if (r10 == 0) goto L_0x083f;
    L_0x0836:
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0a8b }
        r7 = 0;
        r12 = r12.zza(r7, r6, r7);	 Catch:{ all -> 0x0a8b }
    L_0x083f:
        r6 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r7 = r11.zzc;	 Catch:{ all -> 0x0a8b }
        r10 = r7.longValue();	 Catch:{ all -> 0x0a8b }
        r7 = r12.zzb(r10);	 Catch:{ all -> 0x0a8b }
        r4.put(r6, r7);	 Catch:{ all -> 0x0a8b }
        r57 = r2;
        r58 = r3;
        r10 = r8;
        goto L_0x0780;
    L_0x0855:
        r57 = r2;
        r58 = r3;
        r55 = r6;
        r56 = r8;
        r2 = r12.zzf;	 Catch:{ all -> 0x0a8b }
        r6 = r11.zzc;	 Catch:{ all -> 0x0a8b }
        r15 = r6.longValue();	 Catch:{ all -> 0x0a8b }
        r6 = 0;
        r59 = r9;
        r8 = r15 - r2;
        r2 = java.lang.Math.abs(r8);	 Catch:{ all -> 0x0a8b }
        r8 = 86400000; // 0x5265c00 float:7.82218E-36 double:4.2687272E-316;
        r6 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r6 < 0) goto L_0x08c4;
    L_0x0875:
        r60.zzo();	 Catch:{ all -> 0x0a8b }
        r2 = r11.zza;	 Catch:{ all -> 0x0a8b }
        r3 = "_efs";
        r8 = 1;
        r6 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x0a8b }
        r2 = com.google.android.gms.internal.zzcno.zza(r2, r3, r6);	 Catch:{ all -> 0x0a8b }
        r11.zza = r2;	 Catch:{ all -> 0x0a8b }
        r60.zzo();	 Catch:{ all -> 0x0a8b }
        r2 = r11.zza;	 Catch:{ all -> 0x0a8b }
        r3 = "_sr";
        r6 = (long) r7;	 Catch:{ all -> 0x0a8b }
        r13 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0a8b }
        r2 = com.google.android.gms.internal.zzcno.zza(r2, r3, r13);	 Catch:{ all -> 0x0a8b }
        r11.zza = r2;	 Catch:{ all -> 0x0a8b }
        r2 = r10 + 1;
        r5[r10] = r11;	 Catch:{ all -> 0x0a8b }
        r3 = r14.booleanValue();	 Catch:{ all -> 0x0a8b }
        if (r3 == 0) goto L_0x08b2;
    L_0x08a4:
        r3 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0a8b }
        r6 = 1;
        r7 = java.lang.Boolean.valueOf(r6);	 Catch:{ all -> 0x0a8b }
        r6 = 0;
        r12 = r12.zza(r6, r3, r7);	 Catch:{ all -> 0x0a8b }
    L_0x08b2:
        r3 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r6 = r11.zzc;	 Catch:{ all -> 0x0a8b }
        r6 = r6.longValue();	 Catch:{ all -> 0x0a8b }
        r6 = r12.zzb(r6);	 Catch:{ all -> 0x0a8b }
        r4.put(r3, r6);	 Catch:{ all -> 0x0a8b }
        r10 = r2;
    L_0x08c2:
        r3 = 0;
        goto L_0x08d6;
    L_0x08c4:
        r8 = 1;
        r2 = r14.booleanValue();	 Catch:{ all -> 0x0a8b }
        if (r2 == 0) goto L_0x08c2;
    L_0x08cc:
        r2 = r11.zzb;	 Catch:{ all -> 0x0a8b }
        r3 = 0;
        r6 = r12.zza(r13, r3, r3);	 Catch:{ all -> 0x0a8b }
        r4.put(r2, r6);	 Catch:{ all -> 0x0a8b }
    L_0x08d6:
        r2 = r59 + 1;
        r9 = r2;
        r7 = r40;
        r6 = r55;
        r8 = r56;
        r2 = r57;
        r3 = r58;
        goto L_0x06bd;
    L_0x08e5:
        r57 = r2;
        r2 = r3;
        r3 = 0;
        r6 = r2.zzb;	 Catch:{ all -> 0x0a8b }
        r6 = r6.length;	 Catch:{ all -> 0x0a8b }
        if (r10 >= r6) goto L_0x08f6;
    L_0x08ee:
        r5 = java.util.Arrays.copyOf(r5, r10);	 Catch:{ all -> 0x0a8b }
        r5 = (com.google.android.gms.internal.zzcob[]) r5;	 Catch:{ all -> 0x0a8b }
        r2.zzb = r5;	 Catch:{ all -> 0x0a8b }
    L_0x08f6:
        r4 = r4.entrySet();	 Catch:{ all -> 0x0a8b }
        r4 = r4.iterator();	 Catch:{ all -> 0x0a8b }
    L_0x08fe:
        r5 = r4.hasNext();	 Catch:{ all -> 0x0a8b }
        if (r5 == 0) goto L_0x091c;
    L_0x0904:
        r5 = r4.next();	 Catch:{ all -> 0x0a8b }
        r5 = (java.util.Map.Entry) r5;	 Catch:{ all -> 0x0a8b }
        r6 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r5 = r5.getValue();	 Catch:{ all -> 0x0a8b }
        r5 = (com.google.android.gms.internal.zzcit) r5;	 Catch:{ all -> 0x0a8b }
        r6.zza(r5);	 Catch:{ all -> 0x0a8b }
        goto L_0x08fe;
    L_0x0918:
        r57 = r2;
        r2 = r3;
        r3 = 0;
    L_0x091c:
        r4 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x0a8b }
        r2.zze = r4;	 Catch:{ all -> 0x0a8b }
        r4 = -9223372036854775808;
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x0a8b }
        r2.zzf = r4;	 Catch:{ all -> 0x0a8b }
        r4 = 0;
    L_0x0930:
        r5 = r2.zzb;	 Catch:{ all -> 0x0a8b }
        r5 = r5.length;	 Catch:{ all -> 0x0a8b }
        if (r4 >= r5) goto L_0x0964;
    L_0x0935:
        r5 = r2.zzb;	 Catch:{ all -> 0x0a8b }
        r5 = r5[r4];	 Catch:{ all -> 0x0a8b }
        r6 = r5.zzc;	 Catch:{ all -> 0x0a8b }
        r6 = r6.longValue();	 Catch:{ all -> 0x0a8b }
        r8 = r2.zze;	 Catch:{ all -> 0x0a8b }
        r8 = r8.longValue();	 Catch:{ all -> 0x0a8b }
        r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r10 >= 0) goto L_0x094d;
    L_0x0949:
        r6 = r5.zzc;	 Catch:{ all -> 0x0a8b }
        r2.zze = r6;	 Catch:{ all -> 0x0a8b }
    L_0x094d:
        r6 = r5.zzc;	 Catch:{ all -> 0x0a8b }
        r6 = r6.longValue();	 Catch:{ all -> 0x0a8b }
        r8 = r2.zzf;	 Catch:{ all -> 0x0a8b }
        r8 = r8.longValue();	 Catch:{ all -> 0x0a8b }
        r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r10 <= 0) goto L_0x0961;
    L_0x095d:
        r5 = r5.zzc;	 Catch:{ all -> 0x0a8b }
        r2.zzf = r5;	 Catch:{ all -> 0x0a8b }
    L_0x0961:
        r4 = r4 + 1;
        goto L_0x0930;
    L_0x0964:
        r4 = r57;
        r5 = r4.zza;	 Catch:{ all -> 0x0a8b }
        r5 = r5.zzo;	 Catch:{ all -> 0x0a8b }
        r6 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r6 = r6.zzb(r5);	 Catch:{ all -> 0x0a8b }
        if (r6 != 0) goto L_0x098a;
    L_0x0974:
        r3 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r3 = r3.zzy();	 Catch:{ all -> 0x0a8b }
        r6 = "Bundling raw events w/o app info. appId";
        r7 = r4.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.zzo;	 Catch:{ all -> 0x0a8b }
        r7 = com.google.android.gms.internal.zzcjj.zza(r7);	 Catch:{ all -> 0x0a8b }
        r3.zza(r6, r7);	 Catch:{ all -> 0x0a8b }
        goto L_0x09e4;
    L_0x098a:
        r7 = r2.zzb;	 Catch:{ all -> 0x0a8b }
        r7 = r7.length;	 Catch:{ all -> 0x0a8b }
        if (r7 <= 0) goto L_0x09e4;
    L_0x098f:
        r7 = r6.zzh();	 Catch:{ all -> 0x0a8b }
        r9 = 0;
        r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));
        if (r11 == 0) goto L_0x099e;
    L_0x0999:
        r9 = java.lang.Long.valueOf(r7);	 Catch:{ all -> 0x0a8b }
        goto L_0x099f;
    L_0x099e:
        r9 = r3;
    L_0x099f:
        r2.zzh = r9;	 Catch:{ all -> 0x0a8b }
        r9 = r6.zzg();	 Catch:{ all -> 0x0a8b }
        r11 = 0;
        r13 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1));
        if (r13 != 0) goto L_0x09ac;
    L_0x09ab:
        goto L_0x09ad;
    L_0x09ac:
        r7 = r9;
    L_0x09ad:
        r9 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1));
        if (r9 == 0) goto L_0x09b5;
    L_0x09b1:
        r3 = java.lang.Long.valueOf(r7);	 Catch:{ all -> 0x0a8b }
    L_0x09b5:
        r2.zzg = r3;	 Catch:{ all -> 0x0a8b }
        r6.zzr();	 Catch:{ all -> 0x0a8b }
        r7 = r6.zzo();	 Catch:{ all -> 0x0a8b }
        r3 = (int) r7;	 Catch:{ all -> 0x0a8b }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x0a8b }
        r2.zzw = r3;	 Catch:{ all -> 0x0a8b }
        r3 = r2.zze;	 Catch:{ all -> 0x0a8b }
        r7 = r3.longValue();	 Catch:{ all -> 0x0a8b }
        r6.zza(r7);	 Catch:{ all -> 0x0a8b }
        r3 = r2.zzf;	 Catch:{ all -> 0x0a8b }
        r7 = r3.longValue();	 Catch:{ all -> 0x0a8b }
        r6.zzb(r7);	 Catch:{ all -> 0x0a8b }
        r3 = r6.zzz();	 Catch:{ all -> 0x0a8b }
        r2.zzx = r3;	 Catch:{ all -> 0x0a8b }
        r3 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r3.zza(r6);	 Catch:{ all -> 0x0a8b }
    L_0x09e4:
        r3 = r2.zzb;	 Catch:{ all -> 0x0a8b }
        r3 = r3.length;	 Catch:{ all -> 0x0a8b }
        if (r3 <= 0) goto L_0x0a30;
    L_0x09e9:
        r3 = r60.zzj();	 Catch:{ all -> 0x0a8b }
        r6 = r4.zza;	 Catch:{ all -> 0x0a8b }
        r6 = r6.zzo;	 Catch:{ all -> 0x0a8b }
        r3 = r3.zza(r6);	 Catch:{ all -> 0x0a8b }
        if (r3 == 0) goto L_0x0a01;
    L_0x09f7:
        r6 = r3.zza;	 Catch:{ all -> 0x0a8b }
        if (r6 != 0) goto L_0x09fc;
    L_0x09fb:
        goto L_0x0a01;
    L_0x09fc:
        r3 = r3.zza;	 Catch:{ all -> 0x0a8b }
    L_0x09fe:
        r2.zzae = r3;	 Catch:{ all -> 0x0a8b }
        goto L_0x0a27;
    L_0x0a01:
        r3 = r4.zza;	 Catch:{ all -> 0x0a8b }
        r3 = r3.zzy;	 Catch:{ all -> 0x0a8b }
        r3 = android.text.TextUtils.isEmpty(r3);	 Catch:{ all -> 0x0a8b }
        if (r3 == 0) goto L_0x0a12;
    L_0x0a0b:
        r6 = -1;
        r3 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x0a8b }
        goto L_0x09fe;
    L_0x0a12:
        r3 = r60.zzf();	 Catch:{ all -> 0x0a8b }
        r3 = r3.zzaa();	 Catch:{ all -> 0x0a8b }
        r6 = "Did not find measurement config or missing version info. appId";
        r7 = r4.zza;	 Catch:{ all -> 0x0a8b }
        r7 = r7.zzo;	 Catch:{ all -> 0x0a8b }
        r7 = com.google.android.gms.internal.zzcjj.zza(r7);	 Catch:{ all -> 0x0a8b }
        r3.zza(r6, r7);	 Catch:{ all -> 0x0a8b }
    L_0x0a27:
        r3 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r10 = r27;
        r3.zza(r2, r10);	 Catch:{ all -> 0x0a8b }
    L_0x0a30:
        r2 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r3 = r4.zzb;	 Catch:{ all -> 0x0a8b }
        r2.zza(r3);	 Catch:{ all -> 0x0a8b }
        r2 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r3 = r2.zzaa();	 Catch:{ all -> 0x0a8b }
        r4 = "delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)";
        r6 = 2;
        r6 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x0a50 }
        r7 = 0;
        r6[r7] = r5;	 Catch:{ SQLiteException -> 0x0a50 }
        r7 = 1;
        r6[r7] = r5;	 Catch:{ SQLiteException -> 0x0a50 }
        r3.execSQL(r4, r6);	 Catch:{ SQLiteException -> 0x0a50 }
        goto L_0x0a63;
    L_0x0a50:
        r0 = move-exception;
        r3 = r0;
        r2 = r2.zzt();	 Catch:{ all -> 0x0a8b }
        r2 = r2.zzy();	 Catch:{ all -> 0x0a8b }
        r4 = "Failed to remove unused event metadata. appId";
        r5 = com.google.android.gms.internal.zzcjj.zza(r5);	 Catch:{ all -> 0x0a8b }
        r2.zza(r4, r5, r3);	 Catch:{ all -> 0x0a8b }
    L_0x0a63:
        r2 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r2.zzy();	 Catch:{ all -> 0x0a8b }
        r2 = r60.zzq();
        r2.zzz();
        r2 = 1;
        return r2;
    L_0x0a73:
        r2 = r60.zzq();	 Catch:{ all -> 0x0a8b }
        r2.zzy();	 Catch:{ all -> 0x0a8b }
        r2 = r60.zzq();
        r2.zzz();
        r2 = 0;
        return r2;
    L_0x0a83:
        r0 = move-exception;
    L_0x0a84:
        r2 = r0;
    L_0x0a85:
        if (r9 == 0) goto L_0x0a8a;
    L_0x0a87:
        r9.close();	 Catch:{ all -> 0x0a8b }
    L_0x0a8a:
        throw r2;	 Catch:{ all -> 0x0a8b }
    L_0x0a8b:
        r0 = move-exception;
        r2 = r0;
        r3 = r60.zzq();
        r3.zzz();
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzckj.zza(java.lang.String, long):boolean");
    }

    private final boolean zza(String str, zzcix zzcix) {
        long round;
        Object zzd = zzcix.zzb.zzd(FirebaseAnalytics$Param.CURRENCY);
        if (Event.ECOMMERCE_PURCHASE.equals(zzcix.zza)) {
            double doubleValue = zzcix.zzb.zzc(FirebaseAnalytics$Param.VALUE).doubleValue() * 1000000.0d;
            if (doubleValue == BrickValues.SET_COLOR_TO) {
                doubleValue = ((double) zzcix.zzb.zzb(FirebaseAnalytics$Param.VALUE).longValue()) * 1000000.0d;
            }
            if (doubleValue > 9.223372036854776E18d || doubleValue < -9.223372036854776E18d) {
                zzf().zzaa().zza("Data lost. Currency value is too big. appId", zzcjj.zza(str), Double.valueOf(doubleValue));
                return false;
            }
            round = Math.round(doubleValue);
        } else {
            round = zzcix.zzb.zzb(FirebaseAnalytics$Param.VALUE).longValue();
        }
        if (!TextUtils.isEmpty(zzd)) {
            String toUpperCase = zzd.toUpperCase(Locale.US);
            if (toUpperCase.matches("[A-Z]{3}")) {
                String valueOf = String.valueOf("_ltv_");
                toUpperCase = String.valueOf(toUpperCase);
                String concat = toUpperCase.length() != 0 ? valueOf.concat(toUpperCase) : new String(valueOf);
                zzcnn zzc = zzq().zzc(str, concat);
                if (zzc != null) {
                    if (zzc.zze instanceof Long) {
                        zzc = new zzcnn(str, zzcix.zzc, concat, this.zzn.zza(), Long.valueOf(((Long) zzc.zze).longValue() + round));
                        if (!zzq().zza(zzc)) {
                            zzf().zzy().zza("Too many unique user properties are set. Ignoring user property. appId", zzcjj.zza(str), zzp().zzc(zzc.zzc), zzc.zze);
                            zzo().zza(str, 9, null, null, 0);
                        }
                    }
                }
                zzclh zzq = zzq();
                int zzb = this.zzc.zzb(str, zzciz.zzak) - 1;
                zzbq.zza(str);
                zzq.zzc();
                zzq.zzaq();
                try {
                    zzq.zzaa().execSQL("delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);", new String[]{str, str, String.valueOf(zzb)});
                } catch (SQLiteException e) {
                    zzq.zzt().zzy().zza("Error pruning currencies. appId", zzcjj.zza(str), e);
                }
                zzcnn zzcnn = new zzcnn(str, zzcix.zzc, concat, this.zzn.zza(), Long.valueOf(round));
                if (zzq().zza(zzc)) {
                    zzf().zzy().zza("Too many unique user properties are set. Ignoring user property. appId", zzcjj.zza(str), zzp().zzc(zzc.zzc), zzc.zze);
                    zzo().zza(str, 9, null, null, 0);
                }
            }
        }
        return true;
    }

    private final zzcoa[] zza(String str, zzcog[] zzcogArr, zzcob[] zzcobArr) {
        zzbq.zza(str);
        return zzz().zza(str, zzcobArr, zzcogArr);
    }

    static void zzad() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    private final zzcjs zzah() {
        if (this.zzw != null) {
            return this.zzw;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzcnj zzai() {
        zzb(this.zzx);
        return this.zzx;
    }

    @WorkerThread
    private final boolean zzaj() {
        Object e;
        zzcjl zzy;
        String str;
        zzh().zzc();
        try {
            this.zzae = new RandomAccessFile(new File(this.zzb.getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzad = this.zzae.tryLock();
            if (this.zzad != null) {
                zzf().zzae().zza("Storage concurrent access okay");
                return true;
            }
            zzf().zzy().zza("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e2) {
            e = e2;
            zzy = zzf().zzy();
            str = "Failed to acquire storage lock";
            zzy.zza(str, e);
            return false;
        } catch (IOException e3) {
            e = e3;
            zzy = zzf().zzy();
            str = "Failed to access storage lock file";
            zzy.zza(str, e);
            return false;
        }
    }

    private final long zzak() {
        long zza = this.zzn.zza();
        zzclh zze = zze();
        zze.zzaq();
        zze.zzc();
        long zza2 = zze.zzg.zza();
        if (zza2 == 0) {
            zza2 = (long) (zze.zzp().zzz().nextInt(86400000) + 1);
            zze.zzg.zza(zza2);
        }
        return ((((zza + zza2) / 1000) / 60) / 60) / 24;
    }

    private final boolean zzal() {
        zzh().zzc();
        zza();
        if (!zzq().zzag()) {
            if (TextUtils.isEmpty(zzq().zzab())) {
                return false;
            }
        }
        return true;
    }

    @WorkerThread
    private final void zzam() {
        zzckj zzckj = this;
        zzh().zzc();
        zza();
        if (zzan()) {
            long abs;
            if (zzckj.zzak > 0) {
                abs = 3600000 - Math.abs(zzckj.zzn.zzb() - zzckj.zzak);
                if (abs > 0) {
                    zzf().zzae().zza("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(abs));
                    zzah().zzb();
                    zzai().zzy();
                    return;
                }
                zzckj.zzak = 0;
            }
            if (zzb()) {
                if (zzal()) {
                    Object obj;
                    CharSequence zzaa;
                    zzcja zzcja;
                    long max;
                    long zza;
                    long zza2;
                    long j;
                    Object obj2;
                    long abs2;
                    long abs3;
                    long j2;
                    int i;
                    long zza3 = zzckj.zzn.zza();
                    long max2 = Math.max(0, ((Long) zzciz.zzag.zzb()).longValue());
                    if (!zzq().zzah()) {
                        if (!zzq().zzac()) {
                            obj = null;
                            if (obj == null) {
                                zzaa = zzckj.zzc.zzaa();
                                zzcja = (TextUtils.isEmpty(zzaa) || ".none.".equals(zzaa)) ? zzciz.zzaa : zzciz.zzab;
                            } else {
                                zzcja = zzciz.zzz;
                            }
                            max = Math.max(0, ((Long) zzcja.zzb()).longValue());
                            zza = zze().zzc.zza();
                            zza2 = zze().zzd.zza();
                            j = max;
                            obj2 = obj;
                            abs = Math.max(zzq().zzae(), zzq().zzaf());
                            if (abs != 0) {
                                abs2 = zza3 - Math.abs(abs - zza3);
                                abs3 = zza3 - Math.abs(zza2 - zza3);
                                zza3 = Math.max(zza3 - Math.abs(zza - zza3), abs3);
                                abs = abs2 + max2;
                                if (obj2 != null && zza3 > 0) {
                                    abs = Math.min(abs2, zza3) + j;
                                }
                                j2 = j;
                                max2 = zzo().zza(zza3, j2) ? zza3 + j2 : abs;
                                if (abs3 != 0 && abs3 >= abs2) {
                                    i = 0;
                                    while (i < Math.min(20, Math.max(0, ((Integer) zzciz.zzai.zzb()).intValue()))) {
                                        abs2 = max2 + (Math.max(0, ((Long) zzciz.zzah.zzb()).longValue()) * ((long) (1 << i)));
                                        if (abs2 > abs3) {
                                            max2 = abs2;
                                            break;
                                        } else {
                                            i++;
                                            max2 = abs2;
                                        }
                                    }
                                }
                                if (max2 != 0) {
                                    zzf().zzae().zza("Next upload time is 0");
                                    zzah().zzb();
                                    zzai().zzy();
                                    return;
                                } else if (zzs().zzy()) {
                                    zza3 = zze().zze.zza();
                                    abs = Math.max(0, ((Long) zzciz.zzx.zzb()).longValue());
                                    if (!zzo().zza(zza3, abs)) {
                                        max2 = Math.max(max2, zza3 + abs);
                                    }
                                    zzah().zzb();
                                    abs = max2 - zzckj.zzn.zza();
                                    if (abs <= 0) {
                                        abs = Math.max(0, ((Long) zzciz.zzac.zzb()).longValue());
                                        zze().zzc.zza(zzckj.zzn.zza());
                                    }
                                    zzf().zzae().zza("Upload scheduled in approximately ms", Long.valueOf(abs));
                                    zzai().zza(abs);
                                    return;
                                } else {
                                    zzf().zzae().zza("No network");
                                    zzah().zza();
                                    zzai().zzy();
                                    return;
                                }
                            }
                            max2 = 0;
                            if (max2 != 0) {
                                zzf().zzae().zza("Next upload time is 0");
                                zzah().zzb();
                                zzai().zzy();
                                return;
                            } else if (zzs().zzy()) {
                                zza3 = zze().zze.zza();
                                abs = Math.max(0, ((Long) zzciz.zzx.zzb()).longValue());
                                if (zzo().zza(zza3, abs)) {
                                    max2 = Math.max(max2, zza3 + abs);
                                }
                                zzah().zzb();
                                abs = max2 - zzckj.zzn.zza();
                                if (abs <= 0) {
                                    abs = Math.max(0, ((Long) zzciz.zzac.zzb()).longValue());
                                    zze().zzc.zza(zzckj.zzn.zza());
                                }
                                zzf().zzae().zza("Upload scheduled in approximately ms", Long.valueOf(abs));
                                zzai().zza(abs);
                                return;
                            } else {
                                zzf().zzae().zza("No network");
                                zzah().zza();
                                zzai().zzy();
                                return;
                            }
                        }
                    }
                    obj = 1;
                    if (obj == null) {
                        zzcja = zzciz.zzz;
                    } else {
                        zzaa = zzckj.zzc.zzaa();
                        if (!TextUtils.isEmpty(zzaa)) {
                        }
                    }
                    max = Math.max(0, ((Long) zzcja.zzb()).longValue());
                    zza = zze().zzc.zza();
                    zza2 = zze().zzd.zza();
                    j = max;
                    obj2 = obj;
                    abs = Math.max(zzq().zzae(), zzq().zzaf());
                    if (abs != 0) {
                        abs2 = zza3 - Math.abs(abs - zza3);
                        abs3 = zza3 - Math.abs(zza2 - zza3);
                        zza3 = Math.max(zza3 - Math.abs(zza - zza3), abs3);
                        abs = abs2 + max2;
                        abs = Math.min(abs2, zza3) + j;
                        j2 = j;
                        if (zzo().zza(zza3, j2)) {
                        }
                        i = 0;
                        while (i < Math.min(20, Math.max(0, ((Integer) zzciz.zzai.zzb()).intValue()))) {
                            abs2 = max2 + (Math.max(0, ((Long) zzciz.zzah.zzb()).longValue()) * ((long) (1 << i)));
                            if (abs2 > abs3) {
                                max2 = abs2;
                                break;
                            } else {
                                i++;
                                max2 = abs2;
                            }
                        }
                    }
                    max2 = 0;
                    if (max2 != 0) {
                        zzf().zzae().zza("Next upload time is 0");
                        zzah().zzb();
                        zzai().zzy();
                        return;
                    } else if (zzs().zzy()) {
                        zzf().zzae().zza("No network");
                        zzah().zza();
                        zzai().zzy();
                        return;
                    } else {
                        zza3 = zze().zze.zza();
                        abs = Math.max(0, ((Long) zzciz.zzx.zzb()).longValue());
                        if (zzo().zza(zza3, abs)) {
                            max2 = Math.max(max2, zza3 + abs);
                        }
                        zzah().zzb();
                        abs = max2 - zzckj.zzn.zza();
                        if (abs <= 0) {
                            abs = Math.max(0, ((Long) zzciz.zzac.zzb()).longValue());
                            zze().zzc.zza(zzckj.zzn.zza());
                        }
                        zzf().zzae().zza("Upload scheduled in approximately ms", Long.valueOf(abs));
                        zzai().zza(abs);
                        return;
                    }
                }
            }
            zzf().zzae().zza("Nothing to upload or uploading impossible");
            zzah().zzb();
            zzai().zzy();
        }
    }

    @WorkerThread
    private final boolean zzan() {
        zzh().zzc();
        zza();
        return this.zzaa;
    }

    @WorkerThread
    private final void zzao() {
        zzh().zzc();
        if (!(this.zzal || this.zzam)) {
            if (!this.zzan) {
                zzf().zzae().zza("Stopping uploading service(s)");
                if (this.zzag != null) {
                    for (Runnable run : this.zzag) {
                        run.run();
                    }
                    this.zzag.clear();
                    return;
                }
                return;
            }
        }
        zzf().zzae().zza("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzal), Boolean.valueOf(this.zzam), Boolean.valueOf(this.zzan));
    }

    @WorkerThread
    private final zzcif zzb(String str) {
        zzcjl zzy;
        String str2;
        Object zza;
        String str3 = str;
        zzcie zzb = zzq().zzb(str3);
        if (zzb != null) {
            if (!TextUtils.isEmpty(zzb.zzi())) {
                Boolean zzb2 = zzb(zzb);
                if (zzb2 == null || zzb2.booleanValue()) {
                    return new zzcif(str3, zzb.zzd(), zzb.zzi(), zzb.zzj(), zzb.zzk(), zzb.zzl(), zzb.zzm(), null, zzb.zzn(), false, zzb.zzf(), zzb.zzaa(), 0, 0, zzb.zzab());
                }
                zzy = zzf().zzy();
                str2 = "App version does not match; dropping. appId";
                zza = zzcjj.zza(str);
                zzy.zza(str2, zza);
                return null;
            }
        }
        zzy = zzf().zzad();
        str2 = "No app data available; dropping";
        zzy.zza(str2, zza);
        return null;
    }

    @WorkerThread
    private final Boolean zzb(zzcie zzcie) {
        try {
            if (zzcie.zzj() != -2147483648L) {
                if (zzcie.zzj() == ((long) zzbih.zza(this.zzb).zzb(zzcie.zzb(), 0).versionCode)) {
                    return Boolean.valueOf(true);
                }
            }
            String str = zzbih.zza(this.zzb).zzb(zzcie.zzb(), 0).versionName;
            if (zzcie.zzi() != null && zzcie.zzi().equals(str)) {
                return Boolean.valueOf(true);
            }
            return Boolean.valueOf(false);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    @WorkerThread
    private final void zzb(zzcix zzcix, zzcif zzcif) {
        zzckj zzckj = this;
        zzcix zzcix2 = zzcix;
        zzcif zzcif2 = zzcif;
        zzbq.zza(zzcif);
        zzbq.zza(zzcif2.zza);
        long nanoTime = System.nanoTime();
        zzh().zzc();
        zza();
        String str = zzcif2.zza;
        zzo();
        if (!zzcno.zza(zzcix, zzcif)) {
            return;
        }
        if (!zzcif2.zzh) {
            zzd(zzcif2);
        } else if (zzj().zzb(str, zzcix2.zza)) {
            Object obj;
            zzcie zzb;
            zzf().zzaa().zza("Dropping blacklisted event. appId", zzcjj.zza(str), zzp().zza(zzcix2.zza));
            if (!zzo().zzk(str)) {
                if (!zzo().zzl(str)) {
                    obj = null;
                    if (obj == null && !"_err".equals(zzcix2.zza)) {
                        zzo().zza(str, 11, "_ev", zzcix2.zza, 0);
                    }
                    if (obj != null) {
                        zzb = zzq().zzb(str);
                        if (zzb != null) {
                            if (Math.abs(zzckj.zzn.zza() - Math.max(zzb.zzq(), zzb.zzp())) > ((Long) zzciz.zzaf.zzb()).longValue()) {
                                zzf().zzad().zza("Fetching config for blacklisted app");
                                zza(zzb);
                            }
                        }
                    }
                }
            }
            obj = 1;
            zzo().zza(str, 11, "_ev", zzcix2.zza, 0);
            if (obj != null) {
                zzb = zzq().zzb(str);
                if (zzb != null) {
                    if (Math.abs(zzckj.zzn.zza() - Math.max(zzb.zzq(), zzb.zzp())) > ((Long) zzciz.zzaf.zzb()).longValue()) {
                        zzf().zzad().zza("Fetching config for blacklisted app");
                        zza(zzb);
                    }
                }
            }
        } else {
            if (zzf().zza(2)) {
                zzf().zzae().zza("Logging event", zzp().zza(zzcix2));
            }
            zzq().zzx();
            zzd(zzcif2);
            if (("_iap".equals(zzcix2.zza) || Event.ECOMMERCE_PURCHASE.equals(zzcix2.zza)) && !zza(str, zzcix2)) {
                zzq().zzy();
                zzq().zzz();
                return;
            }
            zzcoe zzcoe;
            try {
                boolean zza = zzcno.zza(zzcix2.zza);
                boolean equals = "_err".equals(zzcix2.zza);
                zzcim zza2 = zzq().zza(zzak(), str, true, zza, false, equals, false);
                long intValue = zza2.zzb - ((long) ((Integer) zzciz.zzq.zzb()).intValue());
                if (intValue > 0) {
                    if (intValue % 1000 == 1) {
                        zzf().zzy().zza("Data loss. Too many events logged. appId, count", zzcjj.zza(str), Long.valueOf(zza2.zzb));
                    }
                    zzq().zzy();
                    zzq().zzz();
                    return;
                }
                if (zza) {
                    intValue = zza2.zza - ((long) ((Integer) zzciz.zzs.zzb()).intValue());
                    if (intValue > 0) {
                        if (intValue % 1000 == 1) {
                            zzf().zzy().zza("Data loss. Too many public events logged. appId, count", zzcjj.zza(str), Long.valueOf(zza2.zza));
                        }
                        zzo().zza(str, 16, "_ev", zzcix2.zza, 0);
                        zzq().zzy();
                        zzq().zzz();
                        return;
                    }
                }
                if (equals) {
                    intValue = zza2.zzd - ((long) Math.max(0, Math.min(1000000, zzckj.zzc.zzb(zzcif2.zza, zzciz.zzr))));
                    if (intValue > 0) {
                        if (intValue == 1) {
                            zzf().zzy().zza("Too many error events logged. appId, count", zzcjj.zza(str), Long.valueOf(zza2.zzd));
                        }
                        zzq().zzy();
                        zzq().zzz();
                        return;
                    }
                }
                Bundle zzb2 = zzcix2.zzb.zzb();
                zzo().zza(zzb2, "_o", zzcix2.zzc);
                if (zzo().zzi(str)) {
                    zzo().zza(zzb2, "_dbg", Long.valueOf(1));
                    zzo().zza(zzb2, "_r", Long.valueOf(1));
                }
                long zzc = zzq().zzc(str);
                if (zzc > 0) {
                    zzf().zzaa().zza("Data lost. Too many events stored on disk, deleted. appId", zzcjj.zza(str), Long.valueOf(zzc));
                }
                long j = nanoTime;
                nanoTime = 0;
                zzcis zzcis = r1;
                String str2 = str;
                zzcis zzcis2 = new zzcis(zzckj, zzcix2.zzc, str, zzcix2.zza, zzcix2.zzd, 0, zzb2);
                zzcit zza3 = zzq().zza(str2, zzcis.zzb);
                if (zza3 != null) {
                    zzcis zza4 = zzcis.zza(zzckj, zza3.zze);
                    zza3 = zza3.zza(zza4.zzc);
                    zzcis = zza4;
                } else if (zzq().zzf(str2) < 500 || !zza) {
                    zzcit zzcit = new zzcit(str2, zzcis.zzb, 0, 0, zzcis.zzc, 0, null, null, null);
                } else {
                    zzf().zzy().zza("Too many event names used, ignoring event. appId, name, supported count", zzcjj.zza(str2), zzp().zza(zzcis.zzb), Integer.valueOf(500));
                    zzo().zza(str2, 8, null, null, 0);
                    zzq().zzz();
                    return;
                }
                zzq().zza(zza3);
                zzh().zzc();
                zza();
                zzbq.zza(zzcis);
                zzbq.zza(zzcif);
                zzbq.zza(zzcis.zza);
                zzbq.zzb(zzcis.zza.equals(zzcif2.zza));
                zzcoe = new zzcoe();
                equals = true;
                zzcoe.zza = Integer.valueOf(1);
                zzcoe.zzi = "android";
                zzcoe.zzo = zzcif2.zza;
                zzcoe.zzn = zzcif2.zzd;
                zzcoe.zzp = zzcif2.zzc;
                zzcoe.zzac = zzcif2.zzj == -2147483648L ? null : Integer.valueOf((int) zzcif2.zzj);
                zzcoe.zzq = Long.valueOf(zzcif2.zze);
                zzcoe.zzy = zzcif2.zzb;
                zzcoe.zzv = zzcif2.zzf == 0 ? null : Long.valueOf(zzcif2.zzf);
                Pair zza5 = zze().zza(zzcif2.zza);
                if (zza5 == null || TextUtils.isEmpty((CharSequence) zza5.first)) {
                    if (!zzx().zza(zzckj.zzb)) {
                        String string = Secure.getString(zzckj.zzb.getContentResolver(), "android_id");
                        if (string == null) {
                            zzf().zzaa().zza("null secure ID. appId", zzcjj.zza(zzcoe.zzo));
                            string = "null";
                        } else if (string.isEmpty()) {
                            zzf().zzaa().zza("empty secure ID. appId", zzcjj.zza(zzcoe.zzo));
                        }
                        zzcoe.zzad = string;
                    }
                } else if (zzcif2.zzo) {
                    zzcoe.zzs = (String) zza5.first;
                    zzcoe.zzt = (Boolean) zza5.second;
                }
                zzx().zzaq();
                zzcoe.zzk = Build.MODEL;
                zzx().zzaq();
                zzcoe.zzj = VERSION.RELEASE;
                zzcoe.zzm = Integer.valueOf((int) zzx().zzx());
                zzcoe.zzl = zzx().zzy();
                zzcoe.zzr = null;
                zzcoe.zzd = null;
                zzcoe.zze = null;
                zzcoe.zzf = null;
                zzcoe.zzaf = Long.valueOf(zzcif2.zzl);
                if (zzab() && zzcik.zzab()) {
                    zzcoe.zzag = null;
                }
                zzcie zzb3 = zzq().zzb(zzcif2.zza);
                if (zzb3 == null) {
                    zzb3 = new zzcie(zzckj, zzcif2.zza);
                    zzb3.zza(zzy().zzy());
                    zzb3.zzd(zzcif2.zzk);
                    zzb3.zzb(zzcif2.zzb);
                    zzb3.zzc(zze().zzb(zzcif2.zza));
                    zzb3.zzf(0);
                    zzb3.zza(0);
                    zzb3.zzb(0);
                    zzb3.zze(zzcif2.zzc);
                    zzb3.zzc(zzcif2.zzj);
                    zzb3.zzf(zzcif2.zzd);
                    zzb3.zzd(zzcif2.zze);
                    zzb3.zze(zzcif2.zzf);
                    zzb3.zza(zzcif2.zzh);
                    zzb3.zzo(zzcif2.zzl);
                    zzq().zza(zzb3);
                }
                zzcoe.zzu = zzb3.zzc();
                zzcoe.zzab = zzb3.zzf();
                List zza6 = zzq().zza(zzcif2.zza);
                zzcoe.zzc = new zzcog[zza6.size()];
                for (int i = 0; i < zza6.size(); i++) {
                    zzcog zzcog = new zzcog();
                    zzcoe.zzc[i] = zzcog;
                    zzcog.zzb = ((zzcnn) zza6.get(i)).zzc;
                    zzcog.zza = Long.valueOf(((zzcnn) zza6.get(i)).zzd);
                    zzo().zza(zzcog, ((zzcnn) zza6.get(i)).zze);
                }
                long zza7 = zzq().zza(zzcoe);
                zzcil zzq = zzq();
                if (zzcis.zze != null) {
                    Iterator it = zzcis.zze.iterator();
                    while (it.hasNext()) {
                        if ("_r".equals((String) it.next())) {
                            break;
                        }
                    }
                    boolean zzc2 = zzj().zzc(zzcis.zza, zzcis.zzb);
                    zzcim zza8 = zzq().zza(zzak(), zzcis.zza, false, false, false, false, false);
                    if (zzc2 && zza8.zze < ((long) zzckj.zzc.zza(zzcis.zza))) {
                        if (zzq.zza(zzcis, zza7, equals)) {
                            zzckj.zzak = 0;
                        }
                        zzq().zzy();
                        if (zzf().zza(2)) {
                            zzf().zzae().zza("Event recorded", zzp().zza(zzcis));
                        }
                        zzq().zzz();
                        zzam();
                        zzf().zzae().zza("Background event processing time, ms", Long.valueOf(((System.nanoTime() - j) + 500000) / 1000000));
                    }
                }
                equals = false;
                if (zzq.zza(zzcis, zza7, equals)) {
                    zzckj.zzak = 0;
                }
                zzq().zzy();
                if (zzf().zza(2)) {
                    zzf().zzae().zza("Event recorded", zzp().zza(zzcis));
                }
                zzq().zzz();
                zzam();
                zzf().zzae().zza("Background event processing time, ms", Long.valueOf(((System.nanoTime() - j) + 500000) / 1000000));
            } catch (IOException e) {
                zzf().zzy().zza("Data loss. Failed to insert raw event metadata. appId", zzcjj.zza(zzcoe.zzo), e);
            } catch (Throwable th) {
                Throwable th2 = th;
                zzq().zzz();
            }
        }
    }

    private static void zzb(zzcli zzcli) {
        if (zzcli == null) {
            throw new IllegalStateException("Component not created");
        } else if (!zzcli.zzap()) {
            String valueOf = String.valueOf(zzcli.getClass());
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 27);
            stringBuilder.append("Component not initialized: ");
            stringBuilder.append(valueOf);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    @WorkerThread
    private final void zzd(zzcif zzcif) {
        Object obj;
        zzh().zzc();
        zza();
        zzbq.zza(zzcif);
        zzbq.zza(zzcif.zza);
        zzcie zzb = zzq().zzb(zzcif.zza);
        String zzb2 = zze().zzb(zzcif.zza);
        if (zzb == null) {
            zzb = new zzcie(this, zzcif.zza);
            zzb.zza(zzy().zzy());
            zzb.zzc(zzb2);
        } else if (zzb2.equals(zzb.zze())) {
            obj = null;
            if (!(TextUtils.isEmpty(zzcif.zzb) || zzcif.zzb.equals(zzb.zzd()))) {
                zzb.zzb(zzcif.zzb);
                obj = 1;
            }
            if (!(TextUtils.isEmpty(zzcif.zzk) || zzcif.zzk.equals(zzb.zzf()))) {
                zzb.zzd(zzcif.zzk);
                obj = 1;
            }
            if (!(zzcif.zze == 0 || zzcif.zze == zzb.zzl())) {
                zzb.zzd(zzcif.zze);
                obj = 1;
            }
            if (!(TextUtils.isEmpty(zzcif.zzc) || zzcif.zzc.equals(zzb.zzi()))) {
                zzb.zze(zzcif.zzc);
                obj = 1;
            }
            if (zzcif.zzj != zzb.zzj()) {
                zzb.zzc(zzcif.zzj);
                obj = 1;
            }
            if (!(zzcif.zzd == null || zzcif.zzd.equals(zzb.zzk()))) {
                zzb.zzf(zzcif.zzd);
                obj = 1;
            }
            if (zzcif.zzf != zzb.zzm()) {
                zzb.zze(zzcif.zzf);
                obj = 1;
            }
            if (zzcif.zzh != zzb.zzn()) {
                zzb.zza(zzcif.zzh);
                obj = 1;
            }
            if (!(TextUtils.isEmpty(zzcif.zzg) || zzcif.zzg.equals(zzb.zzy()))) {
                zzb.zzg(zzcif.zzg);
                obj = 1;
            }
            if (zzcif.zzl != zzb.zzaa()) {
                zzb.zzo(zzcif.zzl);
                obj = 1;
            }
            if (zzcif.zzo != zzb.zzab()) {
                zzb.zzb(zzcif.zzo);
                obj = 1;
            }
            if (obj != null) {
                zzq().zza(zzb);
            }
        } else {
            zzb.zzc(zzb2);
            zzb.zza(zzy().zzy());
        }
        obj = 1;
        zzb.zzb(zzcif.zzb);
        obj = 1;
        zzb.zzd(zzcif.zzk);
        obj = 1;
        zzb.zzd(zzcif.zze);
        obj = 1;
        zzb.zze(zzcif.zzc);
        obj = 1;
        if (zzcif.zzj != zzb.zzj()) {
            zzb.zzc(zzcif.zzj);
            obj = 1;
        }
        zzb.zzf(zzcif.zzd);
        obj = 1;
        if (zzcif.zzf != zzb.zzm()) {
            zzb.zze(zzcif.zzf);
            obj = 1;
        }
        if (zzcif.zzh != zzb.zzn()) {
            zzb.zza(zzcif.zzh);
            obj = 1;
        }
        zzb.zzg(zzcif.zzg);
        obj = 1;
        if (zzcif.zzl != zzb.zzaa()) {
            zzb.zzo(zzcif.zzl);
            obj = 1;
        }
        if (zzcif.zzo != zzb.zzab()) {
            zzb.zzb(zzcif.zzo);
            obj = 1;
        }
        if (obj != null) {
            zzq().zza(zzb);
        }
    }

    public final String zza(String str) {
        try {
            return (String) zzh().zza(new zzckl(this, str)).get(30000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            zzf().zzy().zza("Failed to get app instance id. appId", zzcjj.zza(str), e);
            return null;
        }
    }

    final void zza() {
        if (!this.zzz) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }

    @WorkerThread
    protected final void zza(int i, Throwable th, byte[] bArr) {
        zzh().zzc();
        zza();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzam = false;
                zzao();
            }
        }
        List<Long> list = this.zzaf;
        this.zzaf = null;
        int i2 = 1;
        if ((i == 200 || i == HttpStatus.SC_NO_CONTENT) && th == null) {
            try {
                zze().zzc.zza(this.zzn.zza());
                zze().zzd.zza(0);
                zzam();
                zzf().zzae().zza("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzq().zzx();
                zzclh zzq;
                try {
                    for (Long l : list) {
                        zzq = zzq();
                        long longValue = l.longValue();
                        zzq.zzc();
                        zzq.zzaq();
                        if (zzq.zzaa().delete("queue", "rowid=?", new String[]{String.valueOf(longValue)}) != 1) {
                            throw new SQLiteException("Deleted fewer rows from queue than expected");
                        }
                    }
                    zzq().zzy();
                    zzq().zzz();
                    if (zzs().zzy() && zzal()) {
                        zzae();
                    } else {
                        this.zzaj = -1;
                        zzam();
                    }
                    this.zzak = 0;
                } catch (SQLiteException e) {
                    zzq.zzt().zzy().zza("Failed to delete a bundle in a queue table", e);
                    throw e;
                } catch (Throwable th3) {
                    zzq().zzz();
                }
            } catch (SQLiteException e2) {
                zzf().zzy().zza("Database error while trying to delete uploaded bundles", e2);
                this.zzak = this.zzn.zzb();
                zzf().zzae().zza("Disable upload, time", Long.valueOf(this.zzak));
            }
        } else {
            zzf().zzae().zza("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            zze().zzd.zza(this.zzn.zza());
            if (i != 503) {
                if (i != 429) {
                    i2 = 0;
                }
            }
            if (i2 != 0) {
                zze().zze.zza(this.zzn.zza());
            }
            zzam();
        }
        this.zzam = false;
        zzao();
    }

    final void zza(zzcif zzcif) {
        zzq().zzb(zzcif.zza);
        zzclh zzq = zzq();
        String str = zzcif.zza;
        zzbq.zza(str);
        zzq.zzc();
        zzq.zzaq();
        try {
            SQLiteDatabase zzaa = zzq.zzaa();
            String[] strArr = new String[]{str};
            int delete = ((((((((zzaa.delete("apps", "app_id=?", strArr) + 0) + zzaa.delete("events", "app_id=?", strArr)) + zzaa.delete("user_attributes", "app_id=?", strArr)) + zzaa.delete("conditional_properties", "app_id=?", strArr)) + zzaa.delete("raw_events", "app_id=?", strArr)) + zzaa.delete("raw_events_metadata", "app_id=?", strArr)) + zzaa.delete("queue", "app_id=?", strArr)) + zzaa.delete("audience_filter_values", "app_id=?", strArr)) + zzaa.delete("main_event_params", "app_id=?", strArr);
            if (delete > 0) {
                zzq.zzt().zzae().zza("Reset analytics data. app, records", str, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzq.zzt().zzy().zza("Error resetting analytics data. appId, error", zzcjj.zza(str), e);
        }
        zzc(zza(this.zzb, zzcif.zza, zzcif.zzb, zzcif.zzh, zzcif.zzo));
    }

    @WorkerThread
    final void zza(zzcii zzcii) {
        zzcif zzb = zzb(zzcii.zza);
        if (zzb != null) {
            zza(zzcii, zzb);
        }
    }

    @WorkerThread
    final void zza(zzcii zzcii, zzcif zzcif) {
        zzbq.zza(zzcii);
        zzbq.zza(zzcii.zza);
        zzbq.zza(zzcii.zzb);
        zzbq.zza(zzcii.zzc);
        zzbq.zza(zzcii.zzc.zza);
        zzh().zzc();
        zza();
        if (!TextUtils.isEmpty(zzcif.zzb)) {
            if (zzcif.zzh) {
                zzcii zzcii2 = new zzcii(zzcii);
                boolean z = false;
                zzcii2.zze = false;
                zzq().zzx();
                try {
                    zzcjl zzad;
                    String str;
                    Object obj;
                    Object zzc;
                    Object zza;
                    zzcii zzd = zzq().zzd(zzcii2.zza, zzcii2.zzc.zza);
                    if (!(zzd == null || zzd.zzb.equals(zzcii2.zzb))) {
                        zzf().zzaa().zza("Updating a conditional user property with different origin. name, origin, origin (from DB)", zzp().zzc(zzcii2.zzc.zza), zzcii2.zzb, zzd.zzb);
                    }
                    if (zzd != null && zzd.zze) {
                        zzcii2.zzb = zzd.zzb;
                        zzcii2.zzd = zzd.zzd;
                        zzcii2.zzh = zzd.zzh;
                        zzcii2.zzf = zzd.zzf;
                        zzcii2.zzi = zzd.zzi;
                        zzcii2.zze = zzd.zze;
                        zzcii2.zzc = new zzcnl(zzcii2.zzc.zza, zzd.zzc.zzb, zzcii2.zzc.zza(), zzd.zzc.zzc);
                    } else if (TextUtils.isEmpty(zzcii2.zzf)) {
                        zzcii2.zzc = new zzcnl(zzcii2.zzc.zza, zzcii2.zzd, zzcii2.zzc.zza(), zzcii2.zzc.zzc);
                        zzcii2.zze = true;
                        z = true;
                    }
                    if (zzcii2.zze) {
                        zzcjl zzad2;
                        String str2;
                        Object obj2;
                        Object zzc2;
                        Object obj3;
                        zzcnl zzcnl = zzcii2.zzc;
                        zzcnn zzcnn = new zzcnn(zzcii2.zza, zzcii2.zzb, zzcnl.zza, zzcnl.zzb, zzcnl.zza());
                        if (zzq().zza(zzcnn)) {
                            zzad2 = zzf().zzad();
                            str2 = "User property updated immediately";
                            obj2 = zzcii2.zza;
                            zzc2 = zzp().zzc(zzcnn.zzc);
                            obj3 = zzcnn.zze;
                        } else {
                            zzad2 = zzf().zzy();
                            str2 = "(2)Too many active user properties, ignoring";
                            obj2 = zzcjj.zza(zzcii2.zza);
                            zzc2 = zzp().zzc(zzcnn.zzc);
                            obj3 = zzcnn.zze;
                        }
                        zzad2.zza(str2, obj2, zzc2, obj3);
                        if (z && zzcii2.zzi != null) {
                            zzb(new zzcix(zzcii2.zzi, zzcii2.zzd), zzcif);
                        }
                    }
                    if (zzq().zza(zzcii2)) {
                        zzad = zzf().zzad();
                        str = "Conditional property added";
                        obj = zzcii2.zza;
                        zzc = zzp().zzc(zzcii2.zzc.zza);
                        zza = zzcii2.zzc.zza();
                    } else {
                        zzad = zzf().zzy();
                        str = "Too many conditional properties, ignoring";
                        obj = zzcjj.zza(zzcii2.zza);
                        zzc = zzp().zzc(zzcii2.zzc.zza);
                        zza = zzcii2.zzc.zza();
                    }
                    zzad.zza(str, obj, zzc, zza);
                    zzq().zzy();
                } finally {
                    zzq().zzz();
                }
            } else {
                zzd(zzcif);
            }
        }
    }

    @WorkerThread
    final void zza(zzcix zzcix, zzcif zzcif) {
        zzckj zzckj = this;
        zzcix zzcix2 = zzcix;
        zzcif zzcif2 = zzcif;
        zzbq.zza(zzcif);
        zzbq.zza(zzcif2.zza);
        zzh().zzc();
        zza();
        String str = zzcif2.zza;
        long j = zzcix2.zzd;
        zzo();
        if (!zzcno.zza(zzcix, zzcif)) {
            return;
        }
        if (zzcif2.zzh) {
            zzq().zzx();
            try {
                List emptyList;
                Object obj;
                List emptyList2;
                zzclh zzq = zzq();
                zzbq.zza(str);
                zzq.zzc();
                zzq.zzaq();
                if (j < 0) {
                    zzq.zzt().zzaa().zza("Invalid time querying timed out conditional properties", zzcjj.zza(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzq.zza("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzcii zzcii : r5) {
                    if (zzcii != null) {
                        zzf().zzad().zza("User property timed out", zzcii.zza, zzp().zzc(zzcii.zzc.zza), zzcii.zzc.zza());
                        if (zzcii.zzg != null) {
                            zzb(new zzcix(zzcii.zzg, j), zzcif2);
                        }
                        zzq().zze(str, zzcii.zzc.zza);
                    }
                }
                zzq = zzq();
                zzbq.zza(str);
                zzq.zzc();
                zzq.zzaq();
                if (j < 0) {
                    zzq.zzt().zzaa().zza("Invalid time querying expired conditional properties", zzcjj.zza(str), Long.valueOf(j));
                    emptyList = Collections.emptyList();
                } else {
                    emptyList = zzq.zza("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                List arrayList = new ArrayList(r5.size());
                for (zzcii zzcii2 : r5) {
                    if (zzcii2 != null) {
                        zzf().zzad().zza("User property expired", zzcii2.zza, zzp().zzc(zzcii2.zzc.zza), zzcii2.zzc.zza());
                        zzq().zzb(str, zzcii2.zzc.zza);
                        if (zzcii2.zzk != null) {
                            arrayList.add(zzcii2.zzk);
                        }
                        zzq().zze(str, zzcii2.zzc.zza);
                    }
                }
                ArrayList arrayList2 = (ArrayList) arrayList;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    obj = arrayList2.get(i);
                    i++;
                    zzb(new zzcix((zzcix) obj, j), zzcif2);
                }
                zzq = zzq();
                String str2 = zzcix2.zza;
                zzbq.zza(str);
                zzbq.zza(str2);
                zzq.zzc();
                zzq.zzaq();
                if (j < 0) {
                    zzq.zzt().zzaa().zza("Invalid time querying triggered conditional properties", zzcjj.zza(str), zzq.zzo().zza(str2), Long.valueOf(j));
                    emptyList2 = Collections.emptyList();
                } else {
                    emptyList2 = zzq.zza("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                List arrayList3 = new ArrayList(emptyList2.size());
                Iterator it = emptyList2.iterator();
                while (it.hasNext()) {
                    zzcii zzcii3 = (zzcii) it.next();
                    if (zzcii3 != null) {
                        zzcjl zzad;
                        Object zzc;
                        Object obj2;
                        zzcnl zzcnl = zzcii3.zzc;
                        zzcnn zzcnn = r5;
                        Iterator it2 = it;
                        zzcii zzcii4 = zzcii3;
                        zzcnn zzcnn2 = new zzcnn(zzcii3.zza, zzcii3.zzb, zzcnl.zza, j, zzcnl.zza());
                        if (zzq().zza(zzcnn)) {
                            zzad = zzf().zzad();
                            str2 = "User property triggered";
                            obj = zzcii4.zza;
                            zzc = zzp().zzc(zzcnn.zzc);
                            obj2 = zzcnn.zze;
                        } else {
                            zzad = zzf().zzy();
                            str2 = "Too many active user properties, ignoring";
                            obj = zzcjj.zza(zzcii4.zza);
                            zzc = zzp().zzc(zzcnn.zzc);
                            obj2 = zzcnn.zze;
                        }
                        zzad.zza(str2, obj, zzc, obj2);
                        if (zzcii4.zzi != null) {
                            arrayList3.add(zzcii4.zzi);
                        }
                        zzcii4.zzc = new zzcnl(zzcnn);
                        zzcii4.zze = true;
                        zzq().zza(zzcii4);
                        it = it2;
                    }
                }
                zzb(zzcix, zzcif);
                ArrayList arrayList4 = (ArrayList) arrayList3;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj3 = arrayList4.get(i2);
                    i2++;
                    zzb(new zzcix((zzcix) obj3, j), zzcif2);
                }
                zzq().zzy();
                zzq().zzz();
            } catch (Throwable th) {
                Throwable th2 = th;
                zzq().zzz();
            }
        } else {
            zzd(zzcif2);
        }
    }

    @WorkerThread
    final void zza(zzcix zzcix, String str) {
        zzckj zzckj = this;
        zzcix zzcix2 = zzcix;
        String str2 = str;
        zzcie zzb = zzq().zzb(str2);
        if (zzb != null) {
            if (!TextUtils.isEmpty(zzb.zzi())) {
                Boolean zzb2 = zzb(zzb);
                if (zzb2 == null) {
                    if (!"_ui".equals(zzcix2.zza)) {
                        zzf().zzaa().zza("Could not find package. appId", zzcjj.zza(str));
                    }
                } else if (!zzb2.booleanValue()) {
                    zzf().zzy().zza("App version does not match; dropping event. appId", zzcjj.zza(str));
                    return;
                }
                zzcif zzcif = r2;
                zzcif zzcif2 = new zzcif(str2, zzb.zzd(), zzb.zzi(), zzb.zzj(), zzb.zzk(), zzb.zzl(), zzb.zzm(), null, zzb.zzn(), false, zzb.zzf(), zzb.zzaa(), 0, 0, zzb.zzab());
                zza(zzcix2, zzcif);
                return;
            }
        }
        zzf().zzad().zza("No app data available; dropping event", str2);
    }

    final void zza(zzcli zzcli) {
        this.zzah++;
    }

    @WorkerThread
    final void zza(zzcnl zzcnl, zzcif zzcif) {
        zzh().zzc();
        zza();
        if (!TextUtils.isEmpty(zzcif.zzb)) {
            if (zzcif.zzh) {
                int zzd = zzo().zzd(zzcnl.zza);
                if (zzd != 0) {
                    zzo();
                    zzo().zza(zzcif.zza, zzd, "_ev", zzcno.zza(zzcnl.zza, 24, true), zzcnl.zza != null ? zzcnl.zza.length() : 0);
                    return;
                }
                int zzb = zzo().zzb(zzcnl.zza, zzcnl.zza());
                if (zzb != 0) {
                    zzo();
                    String zza = zzcno.zza(zzcnl.zza, 24, true);
                    Object zza2 = zzcnl.zza();
                    int length = (zza2 == null || !((zza2 instanceof String) || (zza2 instanceof CharSequence))) ? 0 : String.valueOf(zza2).length();
                    zzo().zza(zzcif.zza, zzb, "_ev", zza, length);
                    return;
                }
                Object zzc = zzo().zzc(zzcnl.zza, zzcnl.zza());
                if (zzc != null) {
                    zzcnn zzcnn = new zzcnn(zzcif.zza, zzcnl.zzc, zzcnl.zza, zzcnl.zzb, zzc);
                    zzf().zzad().zza("Setting user property", zzp().zzc(zzcnn.zzc), zzc);
                    zzq().zzx();
                    try {
                        zzd(zzcif);
                        boolean zza3 = zzq().zza(zzcnn);
                        zzq().zzy();
                        if (zza3) {
                            zzf().zzad().zza("User property set", zzp().zzc(zzcnn.zzc), zzcnn.zze);
                        } else {
                            zzf().zzy().zza("Too many unique user properties are set. Ignoring user property", zzp().zzc(zzcnn.zzc), zzcnn.zze);
                            zzo().zza(zzcif.zza, 9, null, null, 0);
                        }
                        zzq().zzz();
                        return;
                    } catch (Throwable th) {
                        zzq().zzz();
                    }
                } else {
                    return;
                }
            }
            zzd(zzcif);
        }
    }

    @WorkerThread
    final void zza(Runnable runnable) {
        zzh().zzc();
        if (this.zzag == null) {
            this.zzag = new ArrayList();
        }
        this.zzag.add(runnable);
    }

    @WorkerThread
    final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        zzcil zzq;
        zzh().zzc();
        zza();
        zzbq.zza(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzal = false;
                zzao();
            }
        }
        zzf().zzae().zza("onConfigFetched. Response size", Integer.valueOf(bArr.length));
        zzq().zzx();
        zzcie zzb = zzq().zzb(str);
        Object obj = 1;
        Object obj2 = ((i == 200 || i == HttpStatus.SC_NO_CONTENT || i == HttpStatus.SC_NOT_MODIFIED) && th == null) ? 1 : null;
        if (zzb == null) {
            zzf().zzaa().zza("App does not exist in onConfigFetched. appId", zzcjj.zza(str));
            zzq().zzy();
            zzq = zzq();
        } else {
            if (obj2 == null) {
                if (i != HttpStatus.SC_NOT_FOUND) {
                    zzb.zzh(this.zzn.zza());
                    zzq().zza(zzb);
                    zzf().zzae().zza("Fetching config failed. code, error", Integer.valueOf(i), th);
                    zzj().zzc(str);
                    zze().zzd.zza(this.zzn.zza());
                    if (i != 503) {
                        if (i != 429) {
                            obj = null;
                        }
                    }
                    if (obj != null) {
                        zze().zze.zza(this.zzn.zza());
                    }
                    zzam();
                    zzq().zzy();
                    zzq = zzq();
                }
            }
            List list = map != null ? (List) map.get("Last-Modified") : null;
            String str2 = (list == null || list.size() <= 0) ? null : (String) list.get(0);
            if (i != HttpStatus.SC_NOT_FOUND) {
                if (i != HttpStatus.SC_NOT_MODIFIED) {
                    if (!zzj().zza(str, bArr, str2)) {
                        zzq = zzq();
                    }
                    zzb.zzg(this.zzn.zza());
                    zzq().zza(zzb);
                    if (i != HttpStatus.SC_NOT_FOUND) {
                        zzf().zzab().zza("Config not found. Using empty config. appId", str);
                    } else {
                        zzf().zzae().zza("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                    }
                    if (zzs().zzy() && zzal()) {
                        zzae();
                        zzq().zzy();
                        zzq = zzq();
                    }
                    zzam();
                    zzq().zzy();
                    zzq = zzq();
                }
            }
            if (zzj().zza(str) == null && !zzj().zza(str, null, null)) {
                zzq = zzq();
            }
            zzb.zzg(this.zzn.zza());
            zzq().zza(zzb);
            if (i != HttpStatus.SC_NOT_FOUND) {
                zzf().zzae().zza("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
            } else {
                zzf().zzab().zza("Config not found. Using empty config. appId", str);
            }
            zzae();
            zzq().zzy();
            zzq = zzq();
        }
        zzq.zzz();
        this.zzal = false;
        zzao();
    }

    public final void zza(boolean z) {
        zzam();
    }

    public final zzcia zzaa() {
        zza(this.zzq);
        return this.zzq;
    }

    @WorkerThread
    public final boolean zzab() {
        zzh().zzc();
        zza();
        boolean z = false;
        if (this.zzc.zzx()) {
            return false;
        }
        Boolean zzb = this.zzc.zzb("firebase_analytics_collection_enabled");
        if (zzb != null) {
            z = zzb.booleanValue();
        } else if (!zzbz.zzb()) {
            z = true;
        }
        return zze().zzc(z);
    }

    final long zzac() {
        Long valueOf = Long.valueOf(zze().zzh.zza());
        return valueOf.longValue() == 0 ? this.zzao : Math.min(this.zzao, valueOf.longValue());
    }

    @WorkerThread
    public final void zzae() {
        zzh().zzc();
        zza();
        this.zzan = true;
        String zzab;
        String str;
        try {
            zzcjl zzaa;
            String str2;
            Boolean zzad = zzw().zzad();
            if (zzad == null) {
                zzaa = zzf().zzaa();
                str2 = "Upload data called on the client side before use of service was decided";
            } else if (zzad.booleanValue()) {
                zzaa = zzf().zzy();
                str2 = "Upload called in the client side when service should be used";
            } else {
                if (this.zzak <= 0) {
                    zzh().zzc();
                    if ((this.zzaf != null ? 1 : null) != null) {
                        zzaa = zzf().zzae();
                        str2 = "Uploading requested multiple times";
                    } else if (zzs().zzy()) {
                        long zza = this.zzn.zza();
                        Object obj = null;
                        zza(null, zza - zzcik.zzz());
                        long zza2 = zze().zzc.zza();
                        if (zza2 != 0) {
                            zzf().zzad().zza("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(zza - zza2)));
                        }
                        zzab = zzq().zzab();
                        if (TextUtils.isEmpty(zzab)) {
                            this.zzaj = -1;
                            Object zza3 = zzq().zza(zza - zzcik.zzz());
                            if (!TextUtils.isEmpty(zza3)) {
                                zzcie zzb = zzq().zzb(zza3);
                                if (zzb != null) {
                                    zza(zzb);
                                }
                            }
                        } else {
                            if (this.zzaj == -1) {
                                this.zzaj = zzq().zzai();
                            }
                            List<Pair> zza4 = zzq().zza(zzab, this.zzc.zzb(zzab, zzciz.zzm), Math.max(0, this.zzc.zzb(zzab, zzciz.zzn)));
                            if (!zza4.isEmpty()) {
                                Object obj2;
                                List subList;
                                for (Pair pair : zza4) {
                                    zzcoe zzcoe = (zzcoe) pair.first;
                                    if (!TextUtils.isEmpty(zzcoe.zzs)) {
                                        obj2 = zzcoe.zzs;
                                        break;
                                    }
                                }
                                obj2 = null;
                                if (obj2 != null) {
                                    for (int i = 0; i < zza4.size(); i++) {
                                        zzcoe zzcoe2 = (zzcoe) ((Pair) zza4.get(i)).first;
                                        if (!TextUtils.isEmpty(zzcoe2.zzs) && !zzcoe2.zzs.equals(obj2)) {
                                            subList = zza4.subList(0, i);
                                            break;
                                        }
                                    }
                                }
                                zzcod zzcod = new zzcod();
                                zzcod.zza = new zzcoe[subList.size()];
                                Collection arrayList = new ArrayList(subList.size());
                                Object obj3 = (zzcik.zzab() && this.zzc.zzc(zzab)) ? 1 : null;
                                for (int i2 = 0; i2 < zzcod.zza.length; i2++) {
                                    zzcod.zza[i2] = (zzcoe) ((Pair) subList.get(i2)).first;
                                    arrayList.add((Long) ((Pair) subList.get(i2)).second);
                                    zzcod.zza[i2].zzr = Long.valueOf(12210);
                                    zzcod.zza[i2].zzd = Long.valueOf(zza);
                                    zzcod.zza[i2].zzz = Boolean.valueOf(false);
                                    if (obj3 == null) {
                                        zzcod.zza[i2].zzag = null;
                                    }
                                }
                                if (zzf().zza(2)) {
                                    obj = zzp().zza(zzcod);
                                }
                                Object zza5 = zzo().zza(zzcod);
                                str = (String) zzciz.zzw.zzb();
                                URL url = new URL(str);
                                zzbq.zzb(arrayList.isEmpty() ^ true);
                                if (this.zzaf != null) {
                                    zzf().zzy().zza("Set uploading progress before finishing the previous upload");
                                } else {
                                    this.zzaf = new ArrayList(arrayList);
                                }
                                zze().zzd.zza(zza);
                                Object obj4 = "?";
                                if (zzcod.zza.length > 0) {
                                    obj4 = zzcod.zza[0].zzo;
                                }
                                zzf().zzae().zza("Uploading data. app, uncompressed size, data", obj4, Integer.valueOf(zza5.length), obj);
                                this.zzam = true;
                                zzclh zzs = zzs();
                                zzcjp zzckm = new zzckm(this);
                                zzs.zzc();
                                zzs.zzaq();
                                zzbq.zza(url);
                                zzbq.zza(zza5);
                                zzbq.zza(zzckm);
                                zzs.zzs().zzb(new zzcjr(zzs, zzab, url, zza5, null, zzckm));
                            }
                        }
                        this.zzan = false;
                        zzao();
                    } else {
                        zzf().zzae().zza("Network not connected, ignoring upload request");
                    }
                }
                zzam();
                this.zzan = false;
                zzao();
            }
            zzaa.zza(str2);
        } catch (MalformedURLException e) {
            zzf().zzy().zza("Failed to parse upload URL. Not uploading. appId", zzcjj.zza(zzab), str);
        } catch (Throwable th) {
            this.zzan = false;
            zzao();
        }
        this.zzan = false;
        zzao();
    }

    final void zzaf() {
        this.zzai++;
    }

    @WorkerThread
    final void zzag() {
        zzh().zzc();
        zza();
        if (!this.zzaa) {
            zzf().zzac().zza("This instance being marked as an uploader");
            zzh().zzc();
            zza();
            if (zzan() && zzaj()) {
                zzcjl zzy;
                String str;
                int zza = zza(this.zzae);
                int zzab = zzy().zzab();
                zzh().zzc();
                if (zza > zzab) {
                    zzy = zzf().zzy();
                    str = "Panic: can't downgrade version. Previous, current version";
                } else if (zza < zzab) {
                    if (zza(zzab, this.zzae)) {
                        zzy = zzf().zzae();
                        str = "Storage version upgraded. Previous, current version";
                    } else {
                        zzy = zzf().zzy();
                        str = "Storage version upgrade failed. Previous, current version";
                    }
                }
                zzy.zza(str, Integer.valueOf(zza), Integer.valueOf(zzab));
            }
            this.zzaa = true;
            zzam();
        }
    }

    final void zzb(zzcif zzcif) {
        zzh().zzc();
        zza();
        zzbq.zza(zzcif.zza);
        zzd(zzcif);
    }

    @WorkerThread
    final void zzb(zzcii zzcii) {
        zzcif zzb = zzb(zzcii.zza);
        if (zzb != null) {
            zzb(zzcii, zzb);
        }
    }

    @WorkerThread
    final void zzb(zzcii zzcii, zzcif zzcif) {
        zzbq.zza(zzcii);
        zzbq.zza(zzcii.zza);
        zzbq.zza(zzcii.zzc);
        zzbq.zza(zzcii.zzc.zza);
        zzh().zzc();
        zza();
        if (!TextUtils.isEmpty(zzcif.zzb)) {
            if (zzcif.zzh) {
                zzq().zzx();
                try {
                    zzd(zzcif);
                    zzcii zzd = zzq().zzd(zzcii.zza, zzcii.zzc.zza);
                    if (zzd != null) {
                        zzf().zzad().zza("Removing conditional user property", zzcii.zza, zzp().zzc(zzcii.zzc.zza));
                        zzq().zze(zzcii.zza, zzcii.zzc.zza);
                        if (zzd.zze) {
                            zzq().zzb(zzcii.zza, zzcii.zzc.zza);
                        }
                        if (zzcii.zzk != null) {
                            Bundle bundle = null;
                            if (zzcii.zzk.zzb != null) {
                                bundle = zzcii.zzk.zzb.zzb();
                            }
                            Bundle bundle2 = bundle;
                            zzb(zzo().zza(zzcii.zzk.zza, bundle2, zzd.zzb, zzcii.zzk.zzd, true, false), zzcif);
                        }
                    } else {
                        zzf().zzaa().zza("Conditional user property doesn't exist", zzcjj.zza(zzcii.zza), zzp().zzc(zzcii.zzc.zza));
                    }
                    zzq().zzy();
                } finally {
                    zzq().zzz();
                }
            } else {
                zzd(zzcif);
            }
        }
    }

    @WorkerThread
    final void zzb(zzcnl zzcnl, zzcif zzcif) {
        zzh().zzc();
        zza();
        if (!TextUtils.isEmpty(zzcif.zzb)) {
            if (zzcif.zzh) {
                zzf().zzad().zza("Removing user property", zzp().zzc(zzcnl.zza));
                zzq().zzx();
                try {
                    zzd(zzcif);
                    zzq().zzb(zzcif.zza, zzcnl.zza);
                    zzq().zzy();
                    zzf().zzad().zza("User property removed", zzp().zzc(zzcnl.zza));
                } finally {
                    zzq().zzz();
                }
            } else {
                zzd(zzcif);
            }
        }
    }

    @WorkerThread
    protected final boolean zzb() {
        zza();
        zzh().zzc();
        if (this.zzab == null || this.zzac == 0 || !(this.zzab == null || this.zzab.booleanValue() || Math.abs(this.zzn.zzb() - this.zzac) <= 1000)) {
            this.zzac = this.zzn.zzb();
            boolean z = false;
            if (zzo().zzg("android.permission.INTERNET") && zzo().zzg("android.permission.ACCESS_NETWORK_STATE") && (zzbih.zza(this.zzb).zza() || (zzcka.zza(this.zzb) && zzcmy.zza(this.zzb, false)))) {
                z = true;
            }
            this.zzab = Boolean.valueOf(z);
            if (this.zzab.booleanValue()) {
                this.zzab = Boolean.valueOf(zzo().zze(zzy().zzaa()));
            }
        }
        return this.zzab.booleanValue();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.WorkerThread
    public final byte[] zzb(@android.support.annotation.NonNull com.google.android.gms.internal.zzcix r33, @android.support.annotation.Size(min = 1) java.lang.String r34) {
        /*
        r32 = this;
        r11 = r32;
        r1 = r33;
        r10 = r34;
        r32.zza();
        r2 = r32.zzh();
        r2.zzc();
        zzad();
        com.google.android.gms.common.internal.zzbq.zza(r33);
        com.google.android.gms.common.internal.zzbq.zza(r34);
        r8 = new com.google.android.gms.internal.zzcod;
        r8.<init>();
        r2 = r32.zzq();
        r2.zzx();
        r2 = r32.zzq();	 Catch:{ all -> 0x04bd }
        r9 = r2.zzb(r10);	 Catch:{ all -> 0x04bd }
        r6 = 0;
        if (r9 != 0) goto L_0x004c;
    L_0x0030:
        r1 = r32.zzf();	 Catch:{ all -> 0x0047 }
        r1 = r1.zzad();	 Catch:{ all -> 0x0047 }
        r2 = "Log and bundle not available. package_name";
        r1.zza(r2, r10);	 Catch:{ all -> 0x0047 }
    L_0x003d:
        r1 = new byte[r6];	 Catch:{ all -> 0x0047 }
        r2 = r32.zzq();
        r2.zzz();
        return r1;
    L_0x0047:
        r0 = move-exception;
        r1 = r0;
        r5 = r11;
        goto L_0x04c0;
    L_0x004c:
        r2 = r9.zzn();	 Catch:{ all -> 0x04bd }
        if (r2 != 0) goto L_0x0060;
    L_0x0052:
        r1 = r32.zzf();	 Catch:{ all -> 0x0047 }
        r1 = r1.zzad();	 Catch:{ all -> 0x0047 }
        r2 = "Log and bundle disabled. package_name";
        r1.zza(r2, r10);	 Catch:{ all -> 0x0047 }
        goto L_0x003d;
    L_0x0060:
        r2 = "_iap";
        r3 = r1.zza;	 Catch:{ all -> 0x04bd }
        r2 = r2.equals(r3);	 Catch:{ all -> 0x04bd }
        if (r2 != 0) goto L_0x0074;
    L_0x006a:
        r2 = "ecommerce_purchase";
        r3 = r1.zza;	 Catch:{ all -> 0x0047 }
        r2 = r2.equals(r3);	 Catch:{ all -> 0x0047 }
        if (r2 == 0) goto L_0x008b;
    L_0x0074:
        r2 = r11.zza(r10, r1);	 Catch:{ all -> 0x04bd }
        if (r2 != 0) goto L_0x008b;
    L_0x007a:
        r2 = r32.zzf();	 Catch:{ all -> 0x0047 }
        r2 = r2.zzaa();	 Catch:{ all -> 0x0047 }
        r3 = "Failed to handle purchase event at single event bundle creation. appId";
        r4 = com.google.android.gms.internal.zzcjj.zza(r34);	 Catch:{ all -> 0x0047 }
        r2.zza(r3, r4);	 Catch:{ all -> 0x0047 }
    L_0x008b:
        r2 = r11.zzc;	 Catch:{ all -> 0x04bd }
        r2 = r2.zzd(r10);	 Catch:{ all -> 0x04bd }
        r4 = 0;
        r3 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x04bd }
        if (r2 == 0) goto L_0x00e4;
    L_0x0099:
        r7 = "_e";
        r12 = r1.zza;	 Catch:{ all -> 0x0047 }
        r7 = r7.equals(r12);	 Catch:{ all -> 0x0047 }
        if (r7 == 0) goto L_0x00e4;
    L_0x00a3:
        r7 = r1.zzb;	 Catch:{ all -> 0x0047 }
        if (r7 == 0) goto L_0x00d5;
    L_0x00a7:
        r7 = r1.zzb;	 Catch:{ all -> 0x0047 }
        r7 = r7.zza();	 Catch:{ all -> 0x0047 }
        if (r7 != 0) goto L_0x00b0;
    L_0x00af:
        goto L_0x00d5;
    L_0x00b0:
        r7 = r1.zzb;	 Catch:{ all -> 0x0047 }
        r12 = "_et";
        r7 = r7.zzb(r12);	 Catch:{ all -> 0x0047 }
        if (r7 != 0) goto L_0x00cc;
    L_0x00ba:
        r7 = r32.zzf();	 Catch:{ all -> 0x0047 }
        r7 = r7.zzaa();	 Catch:{ all -> 0x0047 }
        r12 = "The engagement event does not include duration. appId";
        r13 = com.google.android.gms.internal.zzcjj.zza(r34);	 Catch:{ all -> 0x0047 }
    L_0x00c8:
        r7.zza(r12, r13);	 Catch:{ all -> 0x0047 }
        goto L_0x00e4;
    L_0x00cc:
        r3 = r1.zzb;	 Catch:{ all -> 0x0047 }
        r7 = "_et";
        r3 = r3.zzb(r7);	 Catch:{ all -> 0x0047 }
        goto L_0x00e4;
    L_0x00d5:
        r7 = r32.zzf();	 Catch:{ all -> 0x0047 }
        r7 = r7.zzaa();	 Catch:{ all -> 0x0047 }
        r12 = "The engagement event does not contain any parameters. appId";
        r13 = com.google.android.gms.internal.zzcjj.zza(r34);	 Catch:{ all -> 0x0047 }
        goto L_0x00c8;
    L_0x00e4:
        r7 = new com.google.android.gms.internal.zzcoe;	 Catch:{ all -> 0x04bd }
        r7.<init>();	 Catch:{ all -> 0x04bd }
        r13 = 1;
        r12 = new com.google.android.gms.internal.zzcoe[r13];	 Catch:{ all -> 0x04bd }
        r12[r6] = r7;	 Catch:{ all -> 0x04bd }
        r8.zza = r12;	 Catch:{ all -> 0x04bd }
        r12 = java.lang.Integer.valueOf(r13);	 Catch:{ all -> 0x04bd }
        r7.zza = r12;	 Catch:{ all -> 0x04bd }
        r12 = "android";
        r7.zzi = r12;	 Catch:{ all -> 0x04bd }
        r12 = r9.zzb();	 Catch:{ all -> 0x04bd }
        r7.zzo = r12;	 Catch:{ all -> 0x04bd }
        r12 = r9.zzk();	 Catch:{ all -> 0x04bd }
        r7.zzn = r12;	 Catch:{ all -> 0x04bd }
        r12 = r9.zzi();	 Catch:{ all -> 0x04bd }
        r7.zzp = r12;	 Catch:{ all -> 0x04bd }
        r14 = r9.zzj();	 Catch:{ all -> 0x04bd }
        r16 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r12 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        r13 = 0;
        if (r12 != 0) goto L_0x011a;
    L_0x0118:
        r12 = r13;
        goto L_0x011f;
    L_0x011a:
        r12 = (int) r14;	 Catch:{ all -> 0x04bd }
        r12 = java.lang.Integer.valueOf(r12);	 Catch:{ all -> 0x04bd }
    L_0x011f:
        r7.zzac = r12;	 Catch:{ all -> 0x04bd }
        r14 = r9.zzl();	 Catch:{ all -> 0x04bd }
        r12 = java.lang.Long.valueOf(r14);	 Catch:{ all -> 0x04bd }
        r7.zzq = r12;	 Catch:{ all -> 0x04bd }
        r12 = r9.zzd();	 Catch:{ all -> 0x04bd }
        r7.zzy = r12;	 Catch:{ all -> 0x04bd }
        r14 = r9.zzm();	 Catch:{ all -> 0x04bd }
        r12 = java.lang.Long.valueOf(r14);	 Catch:{ all -> 0x04bd }
        r7.zzv = r12;	 Catch:{ all -> 0x04bd }
        r12 = r32.zzab();	 Catch:{ all -> 0x04bd }
        if (r12 == 0) goto L_0x0153;
    L_0x0141:
        r12 = com.google.android.gms.internal.zzcik.zzab();	 Catch:{ all -> 0x0047 }
        if (r12 == 0) goto L_0x0153;
    L_0x0147:
        r12 = r11.zzc;	 Catch:{ all -> 0x0047 }
        r14 = r7.zzo;	 Catch:{ all -> 0x0047 }
        r12 = r12.zzc(r14);	 Catch:{ all -> 0x0047 }
        if (r12 == 0) goto L_0x0153;
    L_0x0151:
        r7.zzag = r13;	 Catch:{ all -> 0x0047 }
    L_0x0153:
        r12 = r32.zze();	 Catch:{ all -> 0x04bd }
        r14 = r9.zzb();	 Catch:{ all -> 0x04bd }
        r12 = r12.zza(r14);	 Catch:{ all -> 0x04bd }
        r14 = r9.zzab();	 Catch:{ all -> 0x04bd }
        if (r14 == 0) goto L_0x017d;
    L_0x0165:
        if (r12 == 0) goto L_0x017d;
    L_0x0167:
        r14 = r12.first;	 Catch:{ all -> 0x0047 }
        r14 = (java.lang.CharSequence) r14;	 Catch:{ all -> 0x0047 }
        r14 = android.text.TextUtils.isEmpty(r14);	 Catch:{ all -> 0x0047 }
        if (r14 != 0) goto L_0x017d;
    L_0x0171:
        r14 = r12.first;	 Catch:{ all -> 0x0047 }
        r14 = (java.lang.String) r14;	 Catch:{ all -> 0x0047 }
        r7.zzs = r14;	 Catch:{ all -> 0x0047 }
        r12 = r12.second;	 Catch:{ all -> 0x0047 }
        r12 = (java.lang.Boolean) r12;	 Catch:{ all -> 0x0047 }
        r7.zzt = r12;	 Catch:{ all -> 0x0047 }
    L_0x017d:
        r12 = r32.zzx();	 Catch:{ all -> 0x04bd }
        r12.zzaq();	 Catch:{ all -> 0x04bd }
        r12 = android.os.Build.MODEL;	 Catch:{ all -> 0x04bd }
        r7.zzk = r12;	 Catch:{ all -> 0x04bd }
        r12 = r32.zzx();	 Catch:{ all -> 0x04bd }
        r12.zzaq();	 Catch:{ all -> 0x04bd }
        r12 = android.os.Build.VERSION.RELEASE;	 Catch:{ all -> 0x04bd }
        r7.zzj = r12;	 Catch:{ all -> 0x04bd }
        r12 = r32.zzx();	 Catch:{ all -> 0x04bd }
        r14 = r12.zzx();	 Catch:{ all -> 0x04bd }
        r12 = (int) r14;	 Catch:{ all -> 0x04bd }
        r12 = java.lang.Integer.valueOf(r12);	 Catch:{ all -> 0x04bd }
        r7.zzm = r12;	 Catch:{ all -> 0x04bd }
        r12 = r32.zzx();	 Catch:{ all -> 0x04bd }
        r12 = r12.zzy();	 Catch:{ all -> 0x04bd }
        r7.zzl = r12;	 Catch:{ all -> 0x04bd }
        r12 = r9.zzc();	 Catch:{ all -> 0x04bd }
        r7.zzu = r12;	 Catch:{ all -> 0x04bd }
        r12 = r9.zzf();	 Catch:{ all -> 0x04bd }
        r7.zzab = r12;	 Catch:{ all -> 0x04bd }
        r12 = r32.zzq();	 Catch:{ all -> 0x04bd }
        r14 = r9.zzb();	 Catch:{ all -> 0x04bd }
        r12 = r12.zza(r14);	 Catch:{ all -> 0x04bd }
        r14 = r12.size();	 Catch:{ all -> 0x04bd }
        r14 = new com.google.android.gms.internal.zzcog[r14];	 Catch:{ all -> 0x04bd }
        r7.zzc = r14;	 Catch:{ all -> 0x04bd }
        if (r2 == 0) goto L_0x022a;
    L_0x01ce:
        r14 = r32.zzq();	 Catch:{ all -> 0x0047 }
        r15 = r7.zzo;	 Catch:{ all -> 0x0047 }
        r6 = "_lte";
        r6 = r14.zzc(r15, r6);	 Catch:{ all -> 0x0047 }
        if (r6 == 0) goto L_0x0214;
    L_0x01dc:
        r14 = r6.zze;	 Catch:{ all -> 0x0047 }
        if (r14 != 0) goto L_0x01e1;
    L_0x01e0:
        goto L_0x0214;
    L_0x01e1:
        r14 = r3.longValue();	 Catch:{ all -> 0x0047 }
        r16 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1));
        if (r16 <= 0) goto L_0x0212;
    L_0x01e9:
        r14 = new com.google.android.gms.internal.zzcnn;	 Catch:{ all -> 0x0047 }
        r15 = r7.zzo;	 Catch:{ all -> 0x0047 }
        r19 = "auto";
        r20 = "_lte";
        r13 = r11.zzn;	 Catch:{ all -> 0x0047 }
        r21 = r13.zza();	 Catch:{ all -> 0x0047 }
        r6 = r6.zze;	 Catch:{ all -> 0x0047 }
        r6 = (java.lang.Long) r6;	 Catch:{ all -> 0x0047 }
        r16 = r6.longValue();	 Catch:{ all -> 0x0047 }
        r23 = r3.longValue();	 Catch:{ all -> 0x0047 }
        r6 = 0;
        r4 = r16 + r23;
        r23 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x0047 }
        r17 = r14;
        r18 = r15;
        r17.<init>(r18, r19, r20, r21, r23);	 Catch:{ all -> 0x0047 }
        goto L_0x022b;
    L_0x0212:
        r14 = r6;
        goto L_0x022b;
    L_0x0214:
        r4 = new com.google.android.gms.internal.zzcnn;	 Catch:{ all -> 0x0047 }
        r15 = r7.zzo;	 Catch:{ all -> 0x0047 }
        r16 = "auto";
        r17 = "_lte";
        r5 = r11.zzn;	 Catch:{ all -> 0x0047 }
        r18 = r5.zza();	 Catch:{ all -> 0x0047 }
        r14 = r4;
        r20 = r3;
        r14.<init>(r15, r16, r17, r18, r20);	 Catch:{ all -> 0x0047 }
        r14 = r4;
        goto L_0x022b;
    L_0x022a:
        r14 = 0;
    L_0x022b:
        r4 = 0;
        r5 = 0;
    L_0x022d:
        r6 = r12.size();	 Catch:{ all -> 0x04bd }
        if (r4 >= r6) goto L_0x028d;
    L_0x0233:
        r6 = new com.google.android.gms.internal.zzcog;	 Catch:{ all -> 0x0047 }
        r6.<init>();	 Catch:{ all -> 0x0047 }
        r13 = r7.zzc;	 Catch:{ all -> 0x0047 }
        r13[r4] = r6;	 Catch:{ all -> 0x0047 }
        r13 = r12.get(r4);	 Catch:{ all -> 0x0047 }
        r13 = (com.google.android.gms.internal.zzcnn) r13;	 Catch:{ all -> 0x0047 }
        r13 = r13.zzc;	 Catch:{ all -> 0x0047 }
        r6.zzb = r13;	 Catch:{ all -> 0x0047 }
        r13 = r12.get(r4);	 Catch:{ all -> 0x0047 }
        r13 = (com.google.android.gms.internal.zzcnn) r13;	 Catch:{ all -> 0x0047 }
        r27 = r8;
        r28 = r9;
        r8 = r13.zzd;	 Catch:{ all -> 0x0047 }
        r8 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x0047 }
        r6.zza = r8;	 Catch:{ all -> 0x0047 }
        r8 = r32.zzo();	 Catch:{ all -> 0x0047 }
        r9 = r12.get(r4);	 Catch:{ all -> 0x0047 }
        r9 = (com.google.android.gms.internal.zzcnn) r9;	 Catch:{ all -> 0x0047 }
        r9 = r9.zze;	 Catch:{ all -> 0x0047 }
        r8.zza(r6, r9);	 Catch:{ all -> 0x0047 }
        if (r2 == 0) goto L_0x0286;
    L_0x0269:
        r8 = "_lte";
        r9 = r6.zzb;	 Catch:{ all -> 0x0047 }
        r8 = r8.equals(r9);	 Catch:{ all -> 0x0047 }
        if (r8 == 0) goto L_0x0286;
    L_0x0273:
        r5 = r14.zze;	 Catch:{ all -> 0x0047 }
        r5 = (java.lang.Long) r5;	 Catch:{ all -> 0x0047 }
        r6.zzd = r5;	 Catch:{ all -> 0x0047 }
        r5 = r11.zzn;	 Catch:{ all -> 0x0047 }
        r8 = r5.zza();	 Catch:{ all -> 0x0047 }
        r5 = java.lang.Long.valueOf(r8);	 Catch:{ all -> 0x0047 }
        r6.zza = r5;	 Catch:{ all -> 0x0047 }
        r5 = r6;
    L_0x0286:
        r4 = r4 + 1;
        r8 = r27;
        r9 = r28;
        goto L_0x022d;
    L_0x028d:
        r27 = r8;
        r28 = r9;
        if (r2 == 0) goto L_0x02c9;
    L_0x0293:
        if (r5 != 0) goto L_0x02c9;
    L_0x0295:
        r2 = new com.google.android.gms.internal.zzcog;	 Catch:{ all -> 0x0047 }
        r2.<init>();	 Catch:{ all -> 0x0047 }
        r4 = "_lte";
        r2.zzb = r4;	 Catch:{ all -> 0x0047 }
        r4 = r11.zzn;	 Catch:{ all -> 0x0047 }
        r4 = r4.zza();	 Catch:{ all -> 0x0047 }
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x0047 }
        r2.zza = r4;	 Catch:{ all -> 0x0047 }
        r4 = r14.zze;	 Catch:{ all -> 0x0047 }
        r4 = (java.lang.Long) r4;	 Catch:{ all -> 0x0047 }
        r2.zzd = r4;	 Catch:{ all -> 0x0047 }
        r4 = r7.zzc;	 Catch:{ all -> 0x0047 }
        r5 = r7.zzc;	 Catch:{ all -> 0x0047 }
        r5 = r5.length;	 Catch:{ all -> 0x0047 }
        r6 = 1;
        r5 = r5 + r6;
        r4 = java.util.Arrays.copyOf(r4, r5);	 Catch:{ all -> 0x0047 }
        r4 = (com.google.android.gms.internal.zzcog[]) r4;	 Catch:{ all -> 0x0047 }
        r7.zzc = r4;	 Catch:{ all -> 0x0047 }
        r4 = r7.zzc;	 Catch:{ all -> 0x0047 }
        r5 = r7.zzc;	 Catch:{ all -> 0x0047 }
        r5 = r5.length;	 Catch:{ all -> 0x0047 }
        r6 = 1;
        r5 = r5 - r6;
        r4[r5] = r2;	 Catch:{ all -> 0x0047 }
        goto L_0x02ca;
    L_0x02c9:
        r6 = 1;
    L_0x02ca:
        r2 = r3.longValue();	 Catch:{ all -> 0x04bd }
        r4 = 0;
        r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r8 <= 0) goto L_0x02db;
    L_0x02d4:
        r2 = r32.zzq();	 Catch:{ all -> 0x0047 }
        r2.zza(r14);	 Catch:{ all -> 0x0047 }
    L_0x02db:
        r2 = r1.zzb;	 Catch:{ all -> 0x04bd }
        r8 = r2.zzb();	 Catch:{ all -> 0x04bd }
        r2 = "_iap";
        r3 = r1.zza;	 Catch:{ all -> 0x04bd }
        r2 = r2.equals(r3);	 Catch:{ all -> 0x04bd }
        r12 = 1;
        if (r2 == 0) goto L_0x0304;
    L_0x02ed:
        r2 = "_c";
        r8.putLong(r2, r12);	 Catch:{ all -> 0x0047 }
        r2 = r32.zzf();	 Catch:{ all -> 0x0047 }
        r2 = r2.zzad();	 Catch:{ all -> 0x0047 }
        r3 = "Marking in-app purchase as real-time";
        r2.zza(r3);	 Catch:{ all -> 0x0047 }
        r2 = "_r";
        r8.putLong(r2, r12);	 Catch:{ all -> 0x0047 }
    L_0x0304:
        r2 = "_o";
        r3 = r1.zzc;	 Catch:{ all -> 0x04bd }
        r8.putString(r2, r3);	 Catch:{ all -> 0x04bd }
        r2 = r32.zzo();	 Catch:{ all -> 0x04bd }
        r3 = r7.zzo;	 Catch:{ all -> 0x04bd }
        r2 = r2.zzi(r3);	 Catch:{ all -> 0x04bd }
        if (r2 == 0) goto L_0x0331;
    L_0x0317:
        r2 = r32.zzo();	 Catch:{ all -> 0x0047 }
        r3 = "_dbg";
        r9 = java.lang.Long.valueOf(r12);	 Catch:{ all -> 0x0047 }
        r2.zza(r8, r3, r9);	 Catch:{ all -> 0x0047 }
        r2 = r32.zzo();	 Catch:{ all -> 0x0047 }
        r3 = "_r";
        r9 = java.lang.Long.valueOf(r12);	 Catch:{ all -> 0x0047 }
        r2.zza(r8, r3, r9);	 Catch:{ all -> 0x0047 }
    L_0x0331:
        r2 = r32.zzq();	 Catch:{ all -> 0x04bd }
        r3 = r1.zza;	 Catch:{ all -> 0x04bd }
        r2 = r2.zza(r10, r3);	 Catch:{ all -> 0x04bd }
        if (r2 != 0) goto L_0x0362;
    L_0x033d:
        r2 = new com.google.android.gms.internal.zzcit;	 Catch:{ all -> 0x0047 }
        r14 = r1.zza;	 Catch:{ all -> 0x0047 }
        r15 = 1;
        r17 = 0;
        r12 = r1.zzd;	 Catch:{ all -> 0x0047 }
        r21 = 0;
        r23 = 0;
        r24 = 0;
        r25 = 0;
        r19 = r12;
        r12 = r2;
        r9 = 1;
        r26 = 0;
        r13 = r10;
        r12.<init>(r13, r14, r15, r17, r19, r21, r23, r24, r25);	 Catch:{ all -> 0x0047 }
        r3 = r32.zzq();	 Catch:{ all -> 0x0047 }
        r3.zza(r2);	 Catch:{ all -> 0x0047 }
        r12 = r4;
        goto L_0x0378;
    L_0x0362:
        r9 = 1;
        r26 = 0;
        r12 = r2.zze;	 Catch:{ all -> 0x04bd }
        r14 = r1.zzd;	 Catch:{ all -> 0x04bd }
        r2 = r2.zza(r14);	 Catch:{ all -> 0x04bd }
        r2 = r2.zza();	 Catch:{ all -> 0x04bd }
        r3 = r32.zzq();	 Catch:{ all -> 0x04bd }
        r3.zza(r2);	 Catch:{ all -> 0x04bd }
    L_0x0378:
        r14 = new com.google.android.gms.internal.zzcis;	 Catch:{ all -> 0x04bd }
        r3 = r1.zzc;	 Catch:{ all -> 0x04bd }
        r6 = r1.zza;	 Catch:{ all -> 0x04bd }
        r1 = r1.zzd;	 Catch:{ all -> 0x04bd }
        r15 = r1;
        r1 = r14;
        r2 = r11;
        r17 = r4;
        r4 = r10;
        r5 = r6;
        r11 = r7;
        r29 = r14;
        r14 = 0;
        r6 = r15;
        r16 = r8;
        r15 = r27;
        r30 = r28;
        r14 = 1;
        r8 = r12;
        r10 = r16;
        r1.<init>(r2, r3, r4, r5, r6, r8, r10);	 Catch:{ all -> 0x04b9 }
        r1 = new com.google.android.gms.internal.zzcob;	 Catch:{ all -> 0x04b9 }
        r1.<init>();	 Catch:{ all -> 0x04b9 }
        r2 = new com.google.android.gms.internal.zzcob[r14];	 Catch:{ all -> 0x04b9 }
        r3 = 0;
        r2[r3] = r1;	 Catch:{ all -> 0x04b9 }
        r11.zzb = r2;	 Catch:{ all -> 0x04b9 }
        r2 = r29;
        r3 = r2.zzc;	 Catch:{ all -> 0x04b9 }
        r3 = java.lang.Long.valueOf(r3);	 Catch:{ all -> 0x04b9 }
        r1.zzc = r3;	 Catch:{ all -> 0x04b9 }
        r3 = r2.zzb;	 Catch:{ all -> 0x04b9 }
        r1.zzb = r3;	 Catch:{ all -> 0x04b9 }
        r3 = r2.zzd;	 Catch:{ all -> 0x04b9 }
        r3 = java.lang.Long.valueOf(r3);	 Catch:{ all -> 0x04b9 }
        r1.zzd = r3;	 Catch:{ all -> 0x04b9 }
        r3 = r2.zze;	 Catch:{ all -> 0x04b9 }
        r3 = r3.zza();	 Catch:{ all -> 0x04b9 }
        r3 = new com.google.android.gms.internal.zzcoc[r3];	 Catch:{ all -> 0x04b9 }
        r1.zza = r3;	 Catch:{ all -> 0x04b9 }
        r3 = r2.zze;	 Catch:{ all -> 0x04b9 }
        r3 = r3.iterator();	 Catch:{ all -> 0x04b9 }
        r4 = 0;
    L_0x03cc:
        r5 = r3.hasNext();	 Catch:{ all -> 0x04b9 }
        if (r5 == 0) goto L_0x03f8;
    L_0x03d2:
        r5 = r3.next();	 Catch:{ all -> 0x04b9 }
        r5 = (java.lang.String) r5;	 Catch:{ all -> 0x04b9 }
        r6 = new com.google.android.gms.internal.zzcoc;	 Catch:{ all -> 0x04b9 }
        r6.<init>();	 Catch:{ all -> 0x04b9 }
        r7 = r1.zza;	 Catch:{ all -> 0x04b9 }
        r8 = r4 + 1;
        r7[r4] = r6;	 Catch:{ all -> 0x04b9 }
        r6.zza = r5;	 Catch:{ all -> 0x04b9 }
        r4 = r2.zze;	 Catch:{ all -> 0x04b9 }
        r4 = r4.zza(r5);	 Catch:{ all -> 0x04b9 }
        r7 = r11;
        r5 = r32;
        r9 = r32.zzo();	 Catch:{ all -> 0x04b7 }
        r9.zza(r6, r4);	 Catch:{ all -> 0x04b7 }
        r11 = r7;
        r4 = r8;
        goto L_0x03cc;
    L_0x03f8:
        r7 = r11;
        r2 = r30;
        r5 = r32;
        r3 = r2.zzb();	 Catch:{ all -> 0x04b7 }
        r4 = r7.zzc;	 Catch:{ all -> 0x04b7 }
        r6 = r7.zzb;	 Catch:{ all -> 0x04b7 }
        r3 = r5.zza(r3, r4, r6);	 Catch:{ all -> 0x04b7 }
        r7.zzaa = r3;	 Catch:{ all -> 0x04b7 }
        r3 = r1.zzc;	 Catch:{ all -> 0x04b7 }
        r7.zze = r3;	 Catch:{ all -> 0x04b7 }
        r1 = r1.zzc;	 Catch:{ all -> 0x04b7 }
        r7.zzf = r1;	 Catch:{ all -> 0x04b7 }
        r3 = r2.zzh();	 Catch:{ all -> 0x04b7 }
        r1 = (r3 > r17 ? 1 : (r3 == r17 ? 0 : -1));
        if (r1 == 0) goto L_0x0420;
    L_0x041b:
        r13 = java.lang.Long.valueOf(r3);	 Catch:{ all -> 0x04b7 }
        goto L_0x0422;
    L_0x0420:
        r13 = r26;
    L_0x0422:
        r7.zzh = r13;	 Catch:{ all -> 0x04b7 }
        r8 = r2.zzg();	 Catch:{ all -> 0x04b7 }
        r1 = (r8 > r17 ? 1 : (r8 == r17 ? 0 : -1));
        if (r1 != 0) goto L_0x042d;
    L_0x042c:
        goto L_0x042e;
    L_0x042d:
        r3 = r8;
    L_0x042e:
        r1 = (r3 > r17 ? 1 : (r3 == r17 ? 0 : -1));
        if (r1 == 0) goto L_0x0437;
    L_0x0432:
        r13 = java.lang.Long.valueOf(r3);	 Catch:{ all -> 0x04b7 }
        goto L_0x0439;
    L_0x0437:
        r13 = r26;
    L_0x0439:
        r7.zzg = r13;	 Catch:{ all -> 0x04b7 }
        r2.zzr();	 Catch:{ all -> 0x04b7 }
        r3 = r2.zzo();	 Catch:{ all -> 0x04b7 }
        r1 = (int) r3;	 Catch:{ all -> 0x04b7 }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ all -> 0x04b7 }
        r7.zzw = r1;	 Catch:{ all -> 0x04b7 }
        r3 = 12210; // 0x2fb2 float:1.711E-41 double:6.0325E-320;
        r1 = java.lang.Long.valueOf(r3);	 Catch:{ all -> 0x04b7 }
        r7.zzr = r1;	 Catch:{ all -> 0x04b7 }
        r1 = r5.zzn;	 Catch:{ all -> 0x04b7 }
        r3 = r1.zza();	 Catch:{ all -> 0x04b7 }
        r1 = java.lang.Long.valueOf(r3);	 Catch:{ all -> 0x04b7 }
        r7.zzd = r1;	 Catch:{ all -> 0x04b7 }
        r1 = java.lang.Boolean.TRUE;	 Catch:{ all -> 0x04b7 }
        r7.zzz = r1;	 Catch:{ all -> 0x04b7 }
        r1 = r7.zze;	 Catch:{ all -> 0x04b7 }
        r3 = r1.longValue();	 Catch:{ all -> 0x04b7 }
        r2.zza(r3);	 Catch:{ all -> 0x04b7 }
        r1 = r7.zzf;	 Catch:{ all -> 0x04b7 }
        r3 = r1.longValue();	 Catch:{ all -> 0x04b7 }
        r2.zzb(r3);	 Catch:{ all -> 0x04b7 }
        r1 = r32.zzq();	 Catch:{ all -> 0x04b7 }
        r1.zza(r2);	 Catch:{ all -> 0x04b7 }
        r1 = r32.zzq();	 Catch:{ all -> 0x04b7 }
        r1.zzy();	 Catch:{ all -> 0x04b7 }
        r1 = r32.zzq();
        r1.zzz();
        r1 = r15.zzf();	 Catch:{ IOException -> 0x04a3 }
        r1 = new byte[r1];	 Catch:{ IOException -> 0x04a3 }
        r2 = r1.length;	 Catch:{ IOException -> 0x04a3 }
        r3 = 0;
        r2 = com.google.android.gms.internal.zzflk.zza(r1, r3, r2);	 Catch:{ IOException -> 0x04a3 }
        r15.zza(r2);	 Catch:{ IOException -> 0x04a3 }
        r2.zza();	 Catch:{ IOException -> 0x04a3 }
        r2 = r32.zzo();	 Catch:{ IOException -> 0x04a3 }
        r1 = r2.zza(r1);	 Catch:{ IOException -> 0x04a3 }
        return r1;
    L_0x04a3:
        r0 = move-exception;
        r1 = r0;
        r2 = r32.zzf();
        r2 = r2.zzy();
        r3 = "Data loss. Failed to bundle and serialize. appId";
        r4 = com.google.android.gms.internal.zzcjj.zza(r34);
        r2.zza(r3, r4, r1);
        return r26;
    L_0x04b7:
        r0 = move-exception;
        goto L_0x04bf;
    L_0x04b9:
        r0 = move-exception;
        r5 = r32;
        goto L_0x04bf;
    L_0x04bd:
        r0 = move-exception;
        r5 = r11;
    L_0x04bf:
        r1 = r0;
    L_0x04c0:
        r2 = r32.zzq();
        r2.zzz();
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzckj.zzb(com.google.android.gms.internal.zzcix, java.lang.String):byte[]");
    }

    @WorkerThread
    protected final void zzc() {
        zzh().zzc();
        zzq().zzad();
        if (zze().zzc.zza() == 0) {
            zze().zzc.zza(this.zzn.zza());
        }
        if (Long.valueOf(zze().zzh.zza()).longValue() == 0) {
            zzf().zzae().zza("Persisting first open", Long.valueOf(this.zzao));
            zze().zzh.zza(this.zzao);
        }
        if (zzb()) {
            if (!TextUtils.isEmpty(zzy().zzaa())) {
                String zzy = zze().zzy();
                if (zzy == null) {
                    zze().zzc(zzy().zzaa());
                } else if (!zzy.equals(zzy().zzaa())) {
                    zzf().zzac().zza("Rechecking which service to use due to a GMP App Id change");
                    zze().zzab();
                    this.zzt.zzae();
                    this.zzt.zzac();
                    zze().zzc(zzy().zzaa());
                    zze().zzh.zza(this.zzao);
                    zze().zzi.zza(null);
                }
            }
            zzl().zza(zze().zzi.zza());
            if (!TextUtils.isEmpty(zzy().zzaa())) {
                zzclh zzl = zzl();
                zzl.zzc();
                zzl.zzaq();
                if (zzl.zzp.zzb()) {
                    zzl.zzi().zzab();
                    String zzac = zzl.zzu().zzac();
                    if (!TextUtils.isEmpty(zzac)) {
                        zzl.zzh().zzaq();
                        if (!zzac.equals(VERSION.RELEASE)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("_po", zzac);
                            zzl.zza("auto", "_ou", bundle);
                        }
                    }
                }
                zzw().zza(new AtomicReference());
            }
        } else if (zzab()) {
            if (!zzo().zzg("android.permission.INTERNET")) {
                zzf().zzy().zza("App is missing INTERNET permission");
            }
            if (!zzo().zzg("android.permission.ACCESS_NETWORK_STATE")) {
                zzf().zzy().zza("App is missing ACCESS_NETWORK_STATE permission");
            }
            if (!zzbih.zza(this.zzb).zza()) {
                if (!zzcka.zza(this.zzb)) {
                    zzf().zzy().zza("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzcmy.zza(this.zzb, false)) {
                    zzf().zzy().zza("AppMeasurementService not registered/enabled");
                }
            }
            zzf().zzy().zza("Uploading is not possible. App measurement disabled");
        }
        zzam();
    }

    @WorkerThread
    public final void zzc(zzcif zzcif) {
        zzclh zzq;
        String zzb;
        zzckj zzckj = this;
        zzcif zzcif2 = zzcif;
        zzh().zzc();
        zza();
        zzbq.zza(zzcif);
        zzbq.zza(zzcif2.zza);
        if (!TextUtils.isEmpty(zzcif2.zzb)) {
            zzcie zzb2 = zzq().zzb(zzcif2.zza);
            if (!(zzb2 == null || !TextUtils.isEmpty(zzb2.zzd()) || TextUtils.isEmpty(zzcif2.zzb))) {
                zzb2.zzg(0);
                zzq().zza(zzb2);
                zzj().zzd(zzcif2.zza);
            }
            if (zzcif2.zzh) {
                zzcie zzb3;
                zzcix zzcix;
                zzcil zzq2;
                String str;
                String str2;
                zzcit zzcit;
                long j;
                long j2;
                Bundle bundle;
                PackageInfo zzb4;
                Object obj;
                ApplicationInfo zza;
                zzclh zzq3;
                long j3 = zzcif2.zzm;
                if (j3 == 0) {
                    j3 = zzckj.zzn.zza();
                }
                int i = zzcif2.zzn;
                if (!(i == 0 || i == 1)) {
                    zzf().zzaa().zza("Incorrect app type, assuming installed app. appId, appType", zzcjj.zza(zzcif2.zza), Integer.valueOf(i));
                    i = 0;
                }
                zzq().zzx();
                try {
                    zzb3 = zzq().zzb(zzcif2.zza);
                    if (!(zzb3 == null || zzb3.zzd() == null || zzb3.zzd().equals(zzcif2.zzb))) {
                        zzf().zzaa().zza("New GMP App Id passed in. Removing cached database data. appId", zzcjj.zza(zzb3.zzb()));
                        zzq = zzq();
                        zzb = zzb3.zzb();
                        zzq.zzaq();
                        zzq.zzc();
                        zzbq.zza(zzb);
                        SQLiteDatabase zzaa = zzq.zzaa();
                        String[] strArr = new String[]{zzb};
                        int delete = ((((((((zzaa.delete("events", "app_id=?", strArr) + 0) + zzaa.delete("user_attributes", "app_id=?", strArr)) + zzaa.delete("conditional_properties", "app_id=?", strArr)) + zzaa.delete("apps", "app_id=?", strArr)) + zzaa.delete("raw_events", "app_id=?", strArr)) + zzaa.delete("raw_events_metadata", "app_id=?", strArr)) + zzaa.delete("event_filters", "app_id=?", strArr)) + zzaa.delete("property_filters", "app_id=?", strArr)) + zzaa.delete("audience_filter_values", "app_id=?", strArr);
                        if (delete > 0) {
                            zzq.zzt().zzae().zza("Deleted application data. app, records", zzb, Integer.valueOf(delete));
                        }
                        zzb3 = null;
                    }
                } catch (SQLiteException e) {
                    zzq.zzt().zzy().zza("Error deleting application data. appId, error", zzcjj.zza(zzb), e);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    zzq().zzz();
                }
                if (zzb3 != null) {
                    Bundle bundle2;
                    if (zzb3.zzj() != -2147483648L) {
                        if (zzb3.zzj() != zzcif2.zzj) {
                            bundle2 = new Bundle();
                            bundle2.putString("_pv", zzb3.zzi());
                            zzcix = new zzcix("_au", new zzciu(bundle2), "auto", j3);
                        }
                    } else if (!(zzb3.zzi() == null || zzb3.zzi().equals(zzcif2.zzc))) {
                        bundle2 = new Bundle();
                        bundle2.putString("_pv", zzb3.zzi());
                        zzcix = new zzcix("_au", new zzciu(bundle2), "auto", j3);
                    }
                    zza(r14, zzcif2);
                }
                zzd(zzcif);
                if (i == 0) {
                    zzq2 = zzq();
                    str = zzcif2.zza;
                    str2 = "_f";
                } else if (i == 1) {
                    zzq2 = zzq();
                    str = zzcif2.zza;
                    str2 = "_v";
                } else {
                    zzcit = null;
                    if (zzcit != null) {
                        j = 3600000 * ((j3 / 3600000) + 1);
                        if (i != 0) {
                            j2 = 1;
                            zza(new zzcnl("_fot", j3, Long.valueOf(j), "auto"), zzcif2);
                            zzh().zzc();
                            zza();
                            bundle = new Bundle();
                            bundle.putLong("_c", j2);
                            bundle.putLong("_r", j2);
                            bundle.putLong("_uwa", 0);
                            bundle.putLong("_pfo", 0);
                            bundle.putLong("_sys", 0);
                            bundle.putLong("_sysu", 0);
                            if (zzckj.zzb.getPackageManager() != null) {
                                zzf().zzy().zza("PackageManager is null, first open report might be inaccurate. appId", zzcjj.zza(zzcif2.zza));
                            } else {
                                try {
                                    zzb4 = zzbih.zza(zzckj.zzb).zzb(zzcif2.zza, 0);
                                } catch (NameNotFoundException e2) {
                                    zzf().zzy().zza("Package info is null, first open report might be inaccurate. appId", zzcjj.zza(zzcif2.zza), e2);
                                    zzb4 = null;
                                }
                                if (!(zzb4 == null || zzb4.firstInstallTime == 0)) {
                                    if (zzb4.firstInstallTime == zzb4.lastUpdateTime) {
                                        bundle.putLong("_uwa", j2);
                                        obj = null;
                                    } else {
                                        obj = 1;
                                    }
                                    zza(new zzcnl("_fi", j3, Long.valueOf(obj == null ? j2 : 0), "auto"), zzcif2);
                                }
                                try {
                                    zza = zzbih.zza(zzckj.zzb).zza(zzcif2.zza, 0);
                                } catch (NameNotFoundException e22) {
                                    zzf().zzy().zza("Application info is null, first open report might be inaccurate. appId", zzcjj.zza(zzcif2.zza), e22);
                                    zza = null;
                                }
                                if (zza != null) {
                                    if ((zza.flags & 1) != 0) {
                                        bundle.putLong("_sys", j2);
                                    }
                                    if ((zza.flags & 128) != 0) {
                                        bundle.putLong("_sysu", j2);
                                    }
                                }
                            }
                            zzq3 = zzq();
                            str = zzcif2.zza;
                            zzbq.zza(str);
                            zzq3.zzc();
                            zzq3.zzaq();
                            j = zzq3.zzh(str, "first_open_count");
                            if (j >= 0) {
                                bundle.putLong("_pfo", j);
                            }
                            zzcix = new zzcix("_f", new zzciu(bundle), "auto", j3);
                        } else {
                            j2 = 1;
                            if (i == 1) {
                                zza(new zzcnl("_fvt", j3, Long.valueOf(j), "auto"), zzcif2);
                                zzh().zzc();
                                zza();
                                bundle = new Bundle();
                                bundle.putLong("_c", j2);
                                bundle.putLong("_r", j2);
                                zzcix = new zzcix("_v", new zzciu(bundle), "auto", j3);
                            }
                            bundle = new Bundle();
                            bundle.putLong("_et", j2);
                            zzcix = new zzcix("_e", new zzciu(bundle), "auto", j3);
                        }
                        zza(r14, zzcif2);
                        bundle = new Bundle();
                        bundle.putLong("_et", j2);
                        zzcix = new zzcix("_e", new zzciu(bundle), "auto", j3);
                    } else {
                        if (zzcif2.zzi) {
                            zzcix = new zzcix("_cd", new zzciu(new Bundle()), "auto", j3);
                        }
                        zzq().zzy();
                        zzq().zzz();
                        return;
                    }
                    zza(r4, zzcif2);
                    zzq().zzy();
                    zzq().zzz();
                    return;
                }
                zzcit = zzq2.zza(str, str2);
                if (zzcit != null) {
                    if (zzcif2.zzi) {
                        zzcix = new zzcix("_cd", new zzciu(new Bundle()), "auto", j3);
                    }
                    zzq().zzy();
                    zzq().zzz();
                    return;
                }
                j = 3600000 * ((j3 / 3600000) + 1);
                if (i != 0) {
                    j2 = 1;
                    if (i == 1) {
                        zza(new zzcnl("_fvt", j3, Long.valueOf(j), "auto"), zzcif2);
                        zzh().zzc();
                        zza();
                        bundle = new Bundle();
                        bundle.putLong("_c", j2);
                        bundle.putLong("_r", j2);
                        zzcix = new zzcix("_v", new zzciu(bundle), "auto", j3);
                    }
                    bundle = new Bundle();
                    bundle.putLong("_et", j2);
                    zzcix = new zzcix("_e", new zzciu(bundle), "auto", j3);
                } else {
                    j2 = 1;
                    zza(new zzcnl("_fot", j3, Long.valueOf(j), "auto"), zzcif2);
                    zzh().zzc();
                    zza();
                    bundle = new Bundle();
                    bundle.putLong("_c", j2);
                    bundle.putLong("_r", j2);
                    bundle.putLong("_uwa", 0);
                    bundle.putLong("_pfo", 0);
                    bundle.putLong("_sys", 0);
                    bundle.putLong("_sysu", 0);
                    if (zzckj.zzb.getPackageManager() != null) {
                        zzb4 = zzbih.zza(zzckj.zzb).zzb(zzcif2.zza, 0);
                        if (zzb4.firstInstallTime == zzb4.lastUpdateTime) {
                            obj = 1;
                        } else {
                            bundle.putLong("_uwa", j2);
                            obj = null;
                        }
                        if (obj == null) {
                        }
                        zza(new zzcnl("_fi", j3, Long.valueOf(obj == null ? j2 : 0), "auto"), zzcif2);
                        zza = zzbih.zza(zzckj.zzb).zza(zzcif2.zza, 0);
                        if (zza != null) {
                            if ((zza.flags & 1) != 0) {
                                bundle.putLong("_sys", j2);
                            }
                            if ((zza.flags & 128) != 0) {
                                bundle.putLong("_sysu", j2);
                            }
                        }
                    } else {
                        zzf().zzy().zza("PackageManager is null, first open report might be inaccurate. appId", zzcjj.zza(zzcif2.zza));
                    }
                    zzq3 = zzq();
                    str = zzcif2.zza;
                    zzbq.zza(str);
                    zzq3.zzc();
                    zzq3.zzaq();
                    j = zzq3.zzh(str, "first_open_count");
                    if (j >= 0) {
                        bundle.putLong("_pfo", j);
                    }
                    zzcix = new zzcix("_f", new zzciu(bundle), "auto", j3);
                }
                zza(r14, zzcif2);
                bundle = new Bundle();
                bundle.putLong("_et", j2);
                zzcix = new zzcix("_e", new zzciu(bundle), "auto", j3);
                zza(r4, zzcif2);
                zzq().zzy();
                zzq().zzz();
                return;
            }
            zzd(zzcif);
        }
    }

    public final zzcik zzd() {
        return this.zzc;
    }

    public final zzcju zze() {
        zza(this.zzd);
        return this.zzd;
    }

    public final zzcjj zzf() {
        zzb(this.zze);
        return this.zze;
    }

    public final zzcjj zzg() {
        return (this.zze == null || !this.zze.zzap()) ? null : this.zze;
    }

    public final zzcke zzh() {
        zzb(this.zzf);
        return this.zzf;
    }

    public final zzcnd zzi() {
        zzb(this.zzg);
        return this.zzg;
    }

    public final zzckd zzj() {
        zzb(this.zzh);
        return this.zzh;
    }

    final zzcke zzk() {
        return this.zzf;
    }

    public final zzclk zzl() {
        zzb(this.zzp);
        return this.zzp;
    }

    public final AppMeasurement zzm() {
        return this.zzi;
    }

    public final FirebaseAnalytics zzn() {
        return this.zzj;
    }

    public final zzcno zzo() {
        zza(this.zzk);
        return this.zzk;
    }

    public final zzcjh zzp() {
        zza(this.zzl);
        return this.zzl;
    }

    public final zzcil zzq() {
        zzb(this.zzr);
        return this.zzr;
    }

    public final zzcjf zzr() {
        zzb(this.zzs);
        return this.zzs;
    }

    public final zzcjn zzs() {
        zzb(this.zzm);
        return this.zzm;
    }

    public final Context zzt() {
        return this.zzb;
    }

    public final zze zzu() {
        return this.zzn;
    }

    public final zzcma zzv() {
        zzb(this.zzo);
        return this.zzo;
    }

    public final zzcme zzw() {
        zzb(this.zzt);
        return this.zzt;
    }

    public final zzcir zzx() {
        zzb(this.zzu);
        return this.zzu;
    }

    public final zzcje zzy() {
        zzb(this.zzv);
        return this.zzv;
    }

    public final zzcih zzz() {
        zzb(this.zzy);
        return this.zzy;
    }
}
