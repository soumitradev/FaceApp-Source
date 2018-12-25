package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzaql;
import com.google.android.gms.internal.zzaqy;
import com.google.android.gms.internal.zzarj;
import com.google.android.gms.internal.zzarn;
import com.google.android.gms.internal.zzasy;
import com.google.android.gms.internal.zzatt;
import java.util.HashMap;
import java.util.Map;

final class zzp implements Runnable {
    private /* synthetic */ Map zza;
    private /* synthetic */ boolean zzb;
    private /* synthetic */ String zzc;
    private /* synthetic */ long zzd;
    private /* synthetic */ boolean zze;
    private /* synthetic */ boolean zzf;
    private /* synthetic */ String zzg;
    private /* synthetic */ Tracker zzh;

    zzp(Tracker tracker, Map map, boolean z, String str, long j, boolean z2, boolean z3, String str2) {
        this.zzh = tracker;
        this.zza = map;
        this.zzb = z;
        this.zzc = str;
        this.zzd = j;
        this.zze = z2;
        this.zzf = z3;
        this.zzg = str2;
    }

    public final void run() {
        Object obj;
        long zzb;
        long j;
        if (Tracker.zza(this.zzh).zzb()) {
            r0.zza.put("sc", "start");
        }
        zza zzo = r0.zzh.zzo();
        zzbq.zzc("getClientId can not be called from the main thread");
        zzatt.zzb(r0.zza, "cid", zzo.zza().zzo().zzb());
        String str = (String) r0.zza.get("sf");
        if (str != null) {
            double zza = zzatt.zza(str, 100.0d);
            if (zzatt.zza(zza, (String) r0.zza.get("cid"))) {
                r0.zzh.zzb("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(zza));
                return;
            }
        }
        zzaqy zzb2 = Tracker.zzb(r0.zzh);
        if (r0.zzb) {
            zzatt.zza(r0.zza, "ate", zzb2.zzb());
            zzatt.zza(r0.zza, "adid", zzb2.zzc());
        } else {
            r0.zza.remove("ate");
            r0.zza.remove("adid");
        }
        zzaql zzb3 = Tracker.zzc(r0.zzh).zzb();
        zzatt.zza(r0.zza, "an", zzb3.zza());
        zzatt.zza(r0.zza, "av", zzb3.zzb());
        zzatt.zza(r0.zza, "aid", zzb3.zzc());
        zzatt.zza(r0.zza, "aiid", zzb3.zzd());
        r0.zza.put("v", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        r0.zza.put("_v", zzarj.zzb);
        zzatt.zza(r0.zza, "ul", Tracker.zzd(r0.zzh).zzb().zza());
        zzatt.zza(r0.zza, "sr", Tracker.zze(r0.zzh).zzc());
        if (!r0.zzc.equals("transaction")) {
            if (!r0.zzc.equals("item")) {
                obj = null;
                if (obj == null || Tracker.zzf(r0.zzh).zza()) {
                    zzb = zzatt.zzb((String) r0.zza.get("ht"));
                    if (zzb == 0) {
                        zzb = r0.zzd;
                    }
                    j = zzb;
                    if (r0.zze) {
                        String str2 = (String) r0.zza.get("cid");
                        Map hashMap = new HashMap();
                        zzatt.zza(hashMap, "uid", r0.zza);
                        zzatt.zza(hashMap, "an", r0.zza);
                        zzatt.zza(hashMap, "aid", r0.zza);
                        zzatt.zza(hashMap, "av", r0.zza);
                        zzatt.zza(hashMap, "aiid", r0.zza);
                        r0.zza.put("_s", String.valueOf(Tracker.zzi(r0.zzh).zza(new zzarn(0, str2, r0.zzg, TextUtils.isEmpty((CharSequence) r0.zza.get("adid")) ^ 1, 0, hashMap))));
                        Tracker.zzj(r0.zzh).zza(new zzasy(r0.zzh, r0.zza, j, r0.zzf));
                        return;
                    }
                    Tracker.zzh(r0.zzh).zzc("Dry run enabled. Would have sent hit", new zzasy(r0.zzh, r0.zza, j, r0.zzf));
                }
                Tracker.zzg(r0.zzh).zza(r0.zza, "Too many hits sent too quickly, rate limiting invoked");
                return;
            }
        }
        obj = 1;
        if (obj == null) {
        }
        zzb = zzatt.zzb((String) r0.zza.get("ht"));
        if (zzb == 0) {
            zzb = r0.zzd;
        }
        j = zzb;
        if (r0.zze) {
            String str22 = (String) r0.zza.get("cid");
            Map hashMap2 = new HashMap();
            zzatt.zza(hashMap2, "uid", r0.zza);
            zzatt.zza(hashMap2, "an", r0.zza);
            zzatt.zza(hashMap2, "aid", r0.zza);
            zzatt.zza(hashMap2, "av", r0.zza);
            zzatt.zza(hashMap2, "aiid", r0.zza);
            r0.zza.put("_s", String.valueOf(Tracker.zzi(r0.zzh).zza(new zzarn(0, str22, r0.zzg, TextUtils.isEmpty((CharSequence) r0.zza.get("adid")) ^ 1, 0, hashMap2))));
            Tracker.zzj(r0.zzh).zza(new zzasy(r0.zzh, r0.zza, j, r0.zzf));
            return;
        }
        Tracker.zzh(r0.zzh).zzc("Dry run enabled. Would have sent hit", new zzasy(r0.zzh, r0.zza, j, r0.zzf));
    }
}
