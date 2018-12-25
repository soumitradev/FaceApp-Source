package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzari;
import com.google.android.gms.internal.zzark;
import com.google.android.gms.internal.zzats;
import java.util.HashMap;
import java.util.Map;

class Tracker$zza extends zzari implements GoogleAnalytics$zza {
    private boolean zza;
    private int zzb;
    private long zzc = -1;
    private boolean zzd;
    private long zze;
    private /* synthetic */ Tracker zzf;

    protected Tracker$zza(Tracker tracker, zzark zzark) {
        this.zzf = tracker;
        super(zzark);
    }

    private final void zzc() {
        if (this.zzc < 0) {
            if (!this.zza) {
                zzo().zzb(Tracker.zza(this.zzf));
                return;
            }
        }
        zzo().zza(Tracker.zza(this.zzf));
    }

    protected final void zza() {
    }

    public final void zza(long j) {
        this.zzc = j;
        zzc();
    }

    public final void zza(Activity activity) {
        if (this.zzb == 0) {
            if ((zzj().zzb() >= this.zze + Math.max(1000, this.zzc) ? 1 : null) != null) {
                this.zzd = true;
            }
        }
        this.zzb++;
        if (this.zza) {
            String canonicalName;
            Intent intent = activity.getIntent();
            if (intent != null) {
                this.zzf.setCampaignParamsOnNextHit(intent.getData());
            }
            Map hashMap = new HashMap();
            hashMap.put("&t", "screenview");
            Tracker tracker = this.zzf;
            String str = "&cd";
            if (Tracker.zzk(this.zzf) != null) {
                zzats zzk = Tracker.zzk(this.zzf);
                canonicalName = activity.getClass().getCanonicalName();
                String str2 = (String) zzk.zzg.get(canonicalName);
                if (str2 != null) {
                    canonicalName = str2;
                }
            } else {
                canonicalName = activity.getClass().getCanonicalName();
            }
            tracker.set(str, canonicalName);
            if (TextUtils.isEmpty((CharSequence) hashMap.get("&dr"))) {
                zzbq.zza(activity);
                Intent intent2 = activity.getIntent();
                CharSequence charSequence = null;
                if (intent2 != null) {
                    CharSequence stringExtra = intent2.getStringExtra("android.intent.extra.REFERRER_NAME");
                    if (!TextUtils.isEmpty(stringExtra)) {
                        charSequence = stringExtra;
                    }
                }
                if (!TextUtils.isEmpty(charSequence)) {
                    hashMap.put("&dr", charSequence);
                }
            }
            this.zzf.send(hashMap);
        }
    }

    public final void zza(boolean z) {
        this.zza = z;
        zzc();
    }

    public final void zzb(Activity activity) {
        this.zzb--;
        this.zzb = Math.max(0, this.zzb);
        if (this.zzb == 0) {
            this.zze = zzj().zzb();
        }
    }

    public final synchronized boolean zzb() {
        boolean z;
        z = this.zzd;
        this.zzd = false;
        return z;
    }
}
