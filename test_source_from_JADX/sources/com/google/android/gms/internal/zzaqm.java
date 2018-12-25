package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzaqm extends zzi<zzaqm> {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private String zzg;
    private String zzh;
    private String zzi;
    private String zzj;

    public final String toString() {
        Map hashMap = new HashMap();
        hashMap.put("name", this.zza);
        hashMap.put("source", this.zzb);
        hashMap.put(FirebaseAnalytics$Param.MEDIUM, this.zzc);
        hashMap.put("keyword", this.zzd);
        hashMap.put(FirebaseAnalytics$Param.CONTENT, this.zze);
        hashMap.put(ShareConstants.WEB_DIALOG_PARAM_ID, this.zzf);
        hashMap.put("adNetworkId", this.zzg);
        hashMap.put("gclid", this.zzh);
        hashMap.put("dclid", this.zzi);
        hashMap.put(FirebaseAnalytics$Param.ACLID, this.zzj);
        return zzi.zza((Object) hashMap);
    }

    public final String zza() {
        return this.zza;
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqm zzaqm = (zzaqm) zzi;
        if (!TextUtils.isEmpty(this.zza)) {
            zzaqm.zza = this.zza;
        }
        if (!TextUtils.isEmpty(this.zzb)) {
            zzaqm.zzb = this.zzb;
        }
        if (!TextUtils.isEmpty(this.zzc)) {
            zzaqm.zzc = this.zzc;
        }
        if (!TextUtils.isEmpty(this.zzd)) {
            zzaqm.zzd = this.zzd;
        }
        if (!TextUtils.isEmpty(this.zze)) {
            zzaqm.zze = this.zze;
        }
        if (!TextUtils.isEmpty(this.zzf)) {
            zzaqm.zzf = this.zzf;
        }
        if (!TextUtils.isEmpty(this.zzg)) {
            zzaqm.zzg = this.zzg;
        }
        if (!TextUtils.isEmpty(this.zzh)) {
            zzaqm.zzh = this.zzh;
        }
        if (!TextUtils.isEmpty(this.zzi)) {
            zzaqm.zzi = this.zzi;
        }
        if (!TextUtils.isEmpty(this.zzj)) {
            zzaqm.zzj = this.zzj;
        }
    }

    public final void zza(String str) {
        this.zza = str;
    }

    public final String zzb() {
        return this.zzb;
    }

    public final void zzb(String str) {
        this.zzb = str;
    }

    public final String zzc() {
        return this.zzc;
    }

    public final void zzc(String str) {
        this.zzc = str;
    }

    public final String zzd() {
        return this.zzd;
    }

    public final void zzd(String str) {
        this.zzd = str;
    }

    public final String zze() {
        return this.zze;
    }

    public final void zze(String str) {
        this.zze = str;
    }

    public final String zzf() {
        return this.zzf;
    }

    public final void zzf(String str) {
        this.zzf = str;
    }

    public final String zzg() {
        return this.zzg;
    }

    public final void zzg(String str) {
        this.zzg = str;
    }

    public final String zzh() {
        return this.zzh;
    }

    public final void zzh(String str) {
        this.zzh = str;
    }

    public final String zzi() {
        return this.zzi;
    }

    public final void zzi(String str) {
        this.zzi = str;
    }

    public final String zzj() {
        return this.zzj;
    }

    public final void zzj(String str) {
        this.zzj = str;
    }
}
