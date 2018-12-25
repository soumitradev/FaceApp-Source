package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Hide
public final class zzaqv extends zzi<zzaqv> {
    private String zza;
    private int zzb;
    private int zzc;
    private String zzd;
    private String zze;
    private boolean zzf;
    private boolean zzg;

    public zzaqv() {
        this(false);
    }

    private zzaqv(boolean z) {
        UUID randomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (randomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits == 0) {
            leastSignificantBits = (int) (randomUUID.getMostSignificantBits() & 2147483647L);
            if (leastSignificantBits == 0) {
                Log.e("GAv4", "UUID.randomUUID() returned 0.");
                leastSignificantBits = Integer.MAX_VALUE;
            }
        }
        this(false, leastSignificantBits);
    }

    private zzaqv(boolean z, int i) {
        if (i == 0) {
            throw new IllegalArgumentException("Given Integer is zero");
        }
        this.zzb = i;
        this.zzg = false;
    }

    public final String toString() {
        Map hashMap = new HashMap();
        hashMap.put("screenName", this.zza);
        hashMap.put("interstitial", Boolean.valueOf(this.zzf));
        hashMap.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC, Boolean.valueOf(this.zzg));
        hashMap.put("screenId", Integer.valueOf(this.zzb));
        hashMap.put("referrerScreenId", Integer.valueOf(this.zzc));
        hashMap.put("referrerScreenName", this.zzd);
        hashMap.put("referrerUri", this.zze);
        return zzi.zza((Object) hashMap);
    }

    public final String zza() {
        return this.zza;
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqv zzaqv = (zzaqv) zzi;
        if (!TextUtils.isEmpty(this.zza)) {
            zzaqv.zza = this.zza;
        }
        if (this.zzb != 0) {
            zzaqv.zzb = this.zzb;
        }
        if (this.zzc != 0) {
            zzaqv.zzc = this.zzc;
        }
        if (!TextUtils.isEmpty(this.zzd)) {
            zzaqv.zzd = this.zzd;
        }
        if (!TextUtils.isEmpty(this.zze)) {
            String str = this.zze;
            if (TextUtils.isEmpty(str)) {
                str = null;
            }
            zzaqv.zze = str;
        }
        if (this.zzf) {
            zzaqv.zzf = this.zzf;
        }
        if (this.zzg) {
            zzaqv.zzg = this.zzg;
        }
    }

    public final int zzb() {
        return this.zzb;
    }

    public final String zzc() {
        return this.zze;
    }
}
