package com.google.android.gms.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.Pair;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Hide
final class zzarw extends zzari {
    private boolean zza;
    private final zzart zzb;
    private final zzatf zzc;
    private final zzate zzd;
    private final zzaro zze;
    private long zzf = Long.MIN_VALUE;
    private final zzasn zzg;
    private final zzasn zzh;
    private final zzatp zzi;
    private long zzj;
    private boolean zzk;

    protected zzarw(zzark zzark, zzarm zzarm) {
        super(zzark);
        zzbq.zza(zzarm);
        this.zzd = new zzate(zzark);
        this.zzb = new zzart(zzark);
        this.zzc = new zzatf(zzark);
        this.zze = new zzaro(zzark);
        this.zzi = new zzatp(zzj());
        this.zzg = new zzarx(this, zzark);
        this.zzh = new zzary(this, zzark);
    }

    private final void zza(zzarn zzarn, zzaqm zzaqm) {
        zzbq.zza(zzarn);
        zzbq.zza(zzaqm);
        zzj zza = new zza(zzi());
        zza.zza(zzarn.zzc());
        zza.zza(zzarn.zzd());
        zzg zzb = zza.zzb();
        zzaqu zzaqu = (zzaqu) zzb.zzb(zzaqu.class);
        zzaqu.zza(ShareConstants.WEB_DIALOG_PARAM_DATA);
        zzaqu.zzb(true);
        zzb.zza((zzi) zzaqm);
        zzaqp zzaqp = (zzaqp) zzb.zzb(zzaqp.class);
        zzaql zzaql = (zzaql) zzb.zzb(zzaql.class);
        for (Entry entry : zzarn.zzf().entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if ("an".equals(str)) {
                zzaql.zza(str2);
            } else if ("av".equals(str)) {
                zzaql.zzb(str2);
            } else if ("aid".equals(str)) {
                zzaql.zzc(str2);
            } else if ("aiid".equals(str)) {
                zzaql.zzd(str2);
            } else if ("uid".equals(str)) {
                zzaqu.zzc(str2);
            } else {
                zzaqp.zza(str, str2);
            }
        }
        zzb("Sending installation campaign to", zzarn.zzc(), zzaqm);
        zzb.zza(zzs().zzb());
        zzb.zze();
    }

    private final void zzab() {
        zza(new zzasa(this));
    }

    private final void zzac() {
        try {
            this.zzb.zzf();
            zzh();
        } catch (SQLiteException e) {
            zzd("Failed to delete stale hits", e);
        }
        this.zzh.zza(86400000);
    }

    private final void zzad() {
        if (!this.zzk && zzasl.zzb() && !this.zze.zzb()) {
            if (this.zzi.zza(((Long) zzast.zzac.zza()).longValue())) {
                this.zzi.zza();
                zzb("Connecting to service");
                if (this.zze.zzd()) {
                    zzb("Connected to service");
                    this.zzi.zzb();
                    zze();
                }
            }
        }
    }

    private final boolean zzae() {
        zzk.zzd();
        zzz();
        zzb("Dispatching a batch of local hits");
        int zzb = this.zzc.zzb() ^ 1;
        if ((this.zze.zzb() ^ 1) == 0 || zzb == 0) {
            List<zzasy> zza;
            long max = (long) Math.max(zzasl.zzf(), zzasl.zzg());
            List arrayList = new ArrayList();
            long j = 0;
            while (true) {
                this.zzb.zzb();
                arrayList.clear();
                try {
                    zza = this.zzb.zza(max);
                    if (zza.isEmpty()) {
                        zzb("Store is empty, nothing to dispatch");
                        zzah();
                        try {
                            this.zzb.zzc();
                            this.zzb.zzd();
                            return false;
                        } catch (SQLiteException e) {
                            zze("Failed to commit local dispatch transaction", e);
                            zzah();
                            return false;
                        }
                    }
                    zza("Hits loaded from store. count", Integer.valueOf(zza.size()));
                    try {
                        for (zzasy zzc : zza) {
                            if (zzc.zzc() == j) {
                                break;
                            }
                        }
                        if (this.zze.zzb()) {
                            zzb("Service connected, sending hits to the service");
                            while (!zza.isEmpty()) {
                                zzasy zzasy = (zzasy) zza.get(0);
                                if (this.zze.zza(zzasy)) {
                                    j = Math.max(j, zzasy.zzc());
                                    zza.remove(zzasy);
                                    zzb("Hit sent do device AnalyticsService for delivery", zzasy);
                                    this.zzb.zzb(zzasy.zzc());
                                    arrayList.add(Long.valueOf(zzasy.zzc()));
                                }
                            }
                        }
                        if (this.zzc.zzb()) {
                            List<Long> zza2 = this.zzc.zza((List) zza);
                            for (Long longValue : zza2) {
                                j = Math.max(j, longValue.longValue());
                            }
                            try {
                                this.zzb.zza((List) zza2);
                                arrayList.addAll(zza2);
                            } catch (SQLiteException e2) {
                                zze("Failed to remove successfully uploaded hits", e2);
                                zzah();
                                try {
                                    this.zzb.zzc();
                                    this.zzb.zzd();
                                    return false;
                                } catch (SQLiteException e22) {
                                    zze("Failed to commit local dispatch transaction", e22);
                                    zzah();
                                    return false;
                                }
                            }
                        }
                        if (arrayList.isEmpty()) {
                            try {
                                this.zzb.zzc();
                                this.zzb.zzd();
                                return false;
                            } catch (SQLiteException e222) {
                                zze("Failed to commit local dispatch transaction", e222);
                                zzah();
                                return false;
                            }
                        }
                        try {
                            this.zzb.zzc();
                            this.zzb.zzd();
                        } catch (SQLiteException e2222) {
                            zze("Failed to commit local dispatch transaction", e2222);
                            zzah();
                            return false;
                        }
                    } catch (SQLiteException e22222) {
                        zze("Failed to remove hit that was send for delivery", e22222);
                        zzah();
                        return false;
                    } finally {
                        try {
                            this.zzb.zzc();
                            this.zzb.zzd();
                        } catch (SQLiteException e222222) {
                            zze("Failed to commit local dispatch transaction", e222222);
                            zzah();
                            return false;
                        }
                    }
                } catch (SQLiteException e2222222) {
                    zzd("Failed to read hits from persisted store", e2222222);
                    zzah();
                    try {
                        this.zzb.zzc();
                        this.zzb.zzd();
                        return false;
                    } catch (SQLiteException e22222222) {
                        zze("Failed to commit local dispatch transaction", e22222222);
                        zzah();
                        return false;
                    }
                }
            }
            zzd("Database contains successfully uploaded hit", Long.valueOf(j), Integer.valueOf(zza.size()));
            zzah();
            return false;
        }
        zzb("No network or service available. Will retry later");
        return false;
    }

    private final long zzaf() {
        zzk.zzd();
        zzz();
        try {
            return this.zzb.zzg();
        } catch (SQLiteException e) {
            zze("Failed to get min/max hit times from local store", e);
            return 0;
        }
    }

    private final void zzag() {
        zzasq zzq = zzq();
        if (zzq.zzb() && !zzq.zzc()) {
            long zzaf = zzaf();
            if (zzaf != 0 && Math.abs(zzj().zza() - zzaf) <= ((Long) zzast.zzh.zza()).longValue()) {
                zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzasl.zze()));
                zzq.zzd();
            }
        }
    }

    private final void zzah() {
        if (this.zzg.zzc()) {
            zzb("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzg.zzd();
        zzasq zzq = zzq();
        if (zzq.zzc()) {
            zzq.zze();
        }
    }

    private final long zzai() {
        if (this.zzf != Long.MIN_VALUE) {
            return this.zzf;
        }
        long longValue = ((Long) zzast.zze.zza()).longValue();
        zzari zzr = zzr();
        zzr.zzz();
        if (zzr.zza) {
            zzari zzr2 = zzr();
            zzr2.zzz();
            longValue = ((long) zzr2.zzb) * 1000;
        }
        return longValue;
    }

    private final void zzaj() {
        zzz();
        zzk.zzd();
        this.zzk = true;
        this.zze.zze();
        zzh();
    }

    private final boolean zzg(String str) {
        return zzbih.zza(zzk()).zza(str) == 0;
    }

    public final long zza(zzarn zzarn, boolean z) {
        zzbq.zza(zzarn);
        zzz();
        zzk.zzd();
        try {
            this.zzb.zzb();
            zzarh zzarh = this.zzb;
            long zza = zzarn.zza();
            zzbq.zza(zzarn.zzb());
            zzarh.zzz();
            zzk.zzd();
            int delete = zzarh.zzh().delete("properties", "app_uid=? AND cid<>?", new String[]{String.valueOf(zza), r4});
            if (delete > 0) {
                zzarh.zza("Deleted property records", Integer.valueOf(delete));
            }
            zza = this.zzb.zza(zzarn.zza(), zzarn.zzb(), zzarn.zzc());
            zzarn.zza(zza + 1);
            zzarh = this.zzb;
            zzbq.zza(zzarn);
            zzarh.zzz();
            zzk.zzd();
            SQLiteDatabase zzh = zzarh.zzh();
            Map zzf = zzarn.zzf();
            zzbq.zza(zzf);
            Builder builder = new Builder();
            for (Entry entry : zzf.entrySet()) {
                builder.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
            String encodedQuery = builder.build().getEncodedQuery();
            if (encodedQuery == null) {
                encodedQuery = "";
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_uid", Long.valueOf(zzarn.zza()));
            contentValues.put("cid", zzarn.zzb());
            contentValues.put("tid", zzarn.zzc());
            contentValues.put("adid", Integer.valueOf(zzarn.zzd()));
            contentValues.put("hits_count", Long.valueOf(zzarn.zze()));
            contentValues.put(NativeProtocol.WEB_DIALOG_PARAMS, encodedQuery);
            try {
                if (zzh.insertWithOnConflict("properties", null, contentValues, 5) == -1) {
                    zzarh.zzf("Failed to insert/update a property (got -1)");
                }
            } catch (SQLiteException e) {
                zzarh.zze("Error storing a property", e);
            }
            this.zzb.zzc();
            return zza;
        } catch (SQLiteException e2) {
            zze("Failed to update Analytics property", e2);
            return -1;
        } finally {
            try {
                this.zzb.zzd();
            } catch (SQLiteException e3) {
                zze("Failed to end transaction", e3);
            }
        }
    }

    protected final void zza() {
        this.zzb.zzaa();
        this.zzc.zzaa();
        this.zze.zzaa();
    }

    public final void zza(long j) {
        zzk.zzd();
        zzz();
        if (j < 0) {
            j = 0;
        }
        this.zzf = j;
        zzh();
    }

    protected final void zza(zzarn zzarn) {
        zzk.zzd();
        zzb("Sending first hit to property", zzarn.zzc());
        if (!zzs().zzc().zza(zzasl.zzl())) {
            String zzf = zzs().zzf();
            if (!TextUtils.isEmpty(zzf)) {
                zzaqm zza = zzatt.zza(zzl(), zzf);
                zzb("Found relevant installation campaign", zza);
                zza(zzarn, zza);
            }
        }
    }

    public final void zza(zzasr zzasr) {
        long j = this.zzj;
        zzk.zzd();
        zzz();
        long zzd = zzs().zzd();
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(zzd != 0 ? Math.abs(zzj().zza() - zzd) : -1));
        zzad();
        try {
            zzae();
            zzs().zze();
            zzh();
            if (zzasr != null) {
                zzasr.zza(null);
            }
            if (this.zzj != j) {
                this.zzd.zzc();
            }
        } catch (Throwable th) {
            zze("Local dispatch failed", th);
            zzs().zze();
            zzh();
            if (zzasr != null) {
                zzasr.zza(th);
            }
        }
    }

    public final void zza(zzasy zzasy) {
        zzbq.zza(zzasy);
        zzk.zzd();
        zzz();
        if (this.zzk) {
            zzc("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", zzasy);
        }
        if (TextUtils.isEmpty(zzasy.zzh())) {
            Pair zza = zzs().zzg().zza();
            if (zza != null) {
                Long l = (Long) zza.second;
                String str = (String) zza.first;
                String valueOf = String.valueOf(l);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(str).length());
                stringBuilder.append(valueOf);
                stringBuilder.append(":");
                stringBuilder.append(str);
                str = stringBuilder.toString();
                Map hashMap = new HashMap(zzasy.zzb());
                hashMap.put("_m", str);
                zzasy = new zzasy(this, hashMap, zzasy.zzd(), zzasy.zzf(), zzasy.zzc(), zzasy.zza(), zzasy.zze());
            }
        }
        zzad();
        if (this.zze.zza(zzasy)) {
            zzc("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        try {
            this.zzb.zza(zzasy);
            zzh();
        } catch (SQLiteException e) {
            zze("Delivery failed to save hit to a database", e);
            zzl().zza(zzasy, "deliver: failed to insert hit to database");
        }
    }

    public final void zza(String str) {
        zzbq.zza(str);
        zzk.zzd();
        zzaqm zza = zzatt.zza(zzl(), str);
        if (zza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        CharSequence zzf = zzs().zzf();
        if (str.equals(zzf)) {
            zze("Ignoring duplicate install campaign");
        } else if (TextUtils.isEmpty(zzf)) {
            zzs().zza(str);
            if (zzs().zzc().zza(zzasl.zzl())) {
                zzd("Campaign received too late, ignoring", zza);
                return;
            }
            zzb("Received installation campaign", zza);
            for (zzarn zza2 : this.zzb.zzc(0)) {
                zza(zza2, zza);
            }
        } else {
            zzd("Ignoring multiple install campaigns. original, new", zzf, str);
        }
    }

    final void zzb() {
        zzz();
        zzbq.zza(this.zza ^ true, "Analytics backend already started");
        this.zza = true;
        zzn().zza(new zzarz(this));
    }

    protected final void zzc() {
        zzz();
        zzk.zzd();
        Context zza = zzi().zza();
        if (!zzatk.zza(zza)) {
            zze("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzatl.zza(zza)) {
            zzf("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zza(zza)) {
            zze("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzs().zzb();
        if (!zzg("android.permission.ACCESS_NETWORK_STATE")) {
            zzf("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzaj();
        }
        if (!zzg("android.permission.INTERNET")) {
            zzf("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzaj();
        }
        if (zzatl.zza(zzk())) {
            zzb("AnalyticsService registered in the app manifest and enabled");
        } else {
            zze("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!(this.zzk || this.zzb.zze())) {
            zzad();
        }
        zzh();
    }

    final void zzd() {
        zzk.zzd();
        this.zzj = zzj().zza();
    }

    protected final void zze() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r5 = this;
        com.google.android.gms.analytics.zzk.zzd();
        com.google.android.gms.analytics.zzk.zzd();
        r5.zzz();
        r0 = com.google.android.gms.internal.zzasl.zzb();
        if (r0 != 0) goto L_0x0014;
    L_0x000f:
        r0 = "Service client disabled. Can't dispatch local hits to device AnalyticsService";
        r5.zze(r0);
    L_0x0014:
        r0 = r5.zze;
        r0 = r0.zzb();
        if (r0 != 0) goto L_0x0022;
    L_0x001c:
        r0 = "Service not connected";
        r5.zzb(r0);
        return;
    L_0x0022:
        r0 = r5.zzb;
        r0 = r0.zze();
        if (r0 != 0) goto L_0x007d;
    L_0x002a:
        r0 = "Dispatching local hits to device AnalyticsService";
        r5.zzb(r0);
    L_0x002f:
        r0 = r5.zzb;	 Catch:{ SQLiteException -> 0x0074 }
        r1 = com.google.android.gms.internal.zzasl.zzf();	 Catch:{ SQLiteException -> 0x0074 }
        r1 = (long) r1;	 Catch:{ SQLiteException -> 0x0074 }
        r0 = r0.zza(r1);	 Catch:{ SQLiteException -> 0x0074 }
        r1 = r0.isEmpty();	 Catch:{ SQLiteException -> 0x0074 }
        if (r1 == 0) goto L_0x0044;	 Catch:{ SQLiteException -> 0x0074 }
    L_0x0040:
        r5.zzh();	 Catch:{ SQLiteException -> 0x0074 }
        return;
    L_0x0044:
        r1 = r0.isEmpty();
        if (r1 != 0) goto L_0x002f;
    L_0x004a:
        r1 = 0;
        r1 = r0.get(r1);
        r1 = (com.google.android.gms.internal.zzasy) r1;
        r2 = r5.zze;
        r2 = r2.zza(r1);
        if (r2 != 0) goto L_0x005d;
    L_0x0059:
        r5.zzh();
        return;
    L_0x005d:
        r0.remove(r1);
        r2 = r5.zzb;	 Catch:{ SQLiteException -> 0x006a }
        r3 = r1.zzc();	 Catch:{ SQLiteException -> 0x006a }
        r2.zzb(r3);	 Catch:{ SQLiteException -> 0x006a }
        goto L_0x0044;
    L_0x006a:
        r0 = move-exception;
        r1 = "Failed to remove hit that was send for delivery";
        r5.zze(r1, r0);
        r5.zzah();
        return;
    L_0x0074:
        r0 = move-exception;
        r1 = "Failed to read hits from store";
        r5.zze(r1, r0);
        r5.zzah();
    L_0x007d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzarw.zze():void");
    }

    public final void zzf() {
        zzk.zzd();
        zzz();
        zzb("Delete all hits from local store");
        try {
            zzari zzari = this.zzb;
            zzk.zzd();
            zzari.zzz();
            zzari.zzh().delete("hits2", null, null);
            zzari = this.zzb;
            zzk.zzd();
            zzari.zzz();
            zzari.zzh().delete("properties", null, null);
            zzh();
        } catch (SQLiteException e) {
            zzd("Failed to delete hits from store", e);
        }
        zzad();
        if (this.zze.zzc()) {
            zzb("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    public final void zzg() {
        zzk.zzd();
        zzz();
        zzc("Sync dispatching local hits");
        long j = this.zzj;
        zzad();
        try {
            zzae();
            zzs().zze();
            zzh();
            if (this.zzj != j) {
                this.zzd.zzc();
            }
        } catch (Throwable th) {
            zze("Sync local dispatch failed", th);
            zzh();
        }
    }

    public final void zzh() {
        zzk.zzd();
        zzz();
        boolean z = true;
        Object obj = (this.zzk || zzai() <= 0) ? null : 1;
        if (obj == null) {
            this.zzd.zzb();
            zzah();
        } else if (this.zzb.zze()) {
            this.zzd.zzb();
            zzah();
        } else {
            if (!((Boolean) zzast.zzz.zza()).booleanValue()) {
                this.zzd.zza();
                z = this.zzd.zzd();
            }
            if (z) {
                long abs;
                zzag();
                long zzai = zzai();
                long zzd = zzs().zzd();
                if (zzd != 0) {
                    abs = zzai - Math.abs(zzj().zza() - zzd);
                    if (abs > 0) {
                        zza("Dispatch scheduled (ms)", Long.valueOf(abs));
                        if (this.zzg.zzc()) {
                            this.zzg.zza(abs);
                            return;
                        } else {
                            this.zzg.zzb(Math.max(1, abs + this.zzg.zzb()));
                            return;
                        }
                    }
                }
                abs = Math.min(zzasl.zzd(), zzai);
                zza("Dispatch scheduled (ms)", Long.valueOf(abs));
                if (this.zzg.zzc()) {
                    this.zzg.zza(abs);
                    return;
                } else {
                    this.zzg.zzb(Math.max(1, abs + this.zzg.zzb()));
                    return;
                }
            }
            zzah();
            zzag();
        }
    }
}
