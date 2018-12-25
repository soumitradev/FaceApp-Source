package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzaqw extends zzi<zzaqw> {
    public String zza;
    public String zzb;
    public String zzc;

    public final String toString() {
        Map hashMap = new HashMap();
        hashMap.put("network", this.zza);
        hashMap.put(NativeProtocol.WEB_DIALOG_ACTION, this.zzb);
        hashMap.put("target", this.zzc);
        return zzi.zza((Object) hashMap);
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqw zzaqw = (zzaqw) zzi;
        if (!TextUtils.isEmpty(this.zza)) {
            zzaqw.zza = this.zza;
        }
        if (!TextUtils.isEmpty(this.zzb)) {
            zzaqw.zzb = this.zzb;
        }
        if (!TextUtils.isEmpty(this.zzc)) {
            zzaqw.zzc = this.zzc;
        }
    }
}
