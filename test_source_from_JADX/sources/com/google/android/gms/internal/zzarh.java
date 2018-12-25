package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zze;

@Hide
public class zzarh {
    private final zzark zza;

    protected zzarh(zzark zzark) {
        zzbq.zza((Object) zzark);
        this.zza = zzark;
    }

    private static String zza(Object obj) {
        return obj == null ? "" : obj instanceof String ? (String) obj : obj instanceof Boolean ? obj == Boolean.TRUE ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false" : obj instanceof Throwable ? ((Throwable) obj).toString() : obj.toString();
    }

    private final void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        zzatd zzf = this.zza != null ? this.zza.zzf() : null;
        String str2;
        if (zzf != null) {
            str2 = (String) zzast.zzb.zza();
            if (Log.isLoggable(str2, i)) {
                Log.println(i, str2, zzatd.zzc(str, obj, obj2, obj3));
            }
            if (i >= 5) {
                zzf.zza(i, str, obj, obj2, obj3);
            }
            return;
        }
        str2 = (String) zzast.zzb.zza();
        if (Log.isLoggable(str2, i)) {
            Log.println(i, str2, zzc(str, obj, obj2, obj3));
        }
    }

    @Hide
    protected static String zzc(String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            Object obj4 = "";
        }
        obj = zza(obj);
        obj2 = zza(obj2);
        obj3 = zza(obj3);
        StringBuilder stringBuilder = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(obj4)) {
            stringBuilder.append(obj4);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(obj)) {
            stringBuilder.append(str2);
            stringBuilder.append(obj);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(obj2)) {
            stringBuilder.append(str2);
            stringBuilder.append(obj2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(obj3)) {
            stringBuilder.append(str2);
            stringBuilder.append(obj3);
        }
        return stringBuilder.toString();
    }

    @Hide
    public static boolean zzx() {
        return Log.isLoggable((String) zzast.zzb.zza(), 2);
    }

    @Hide
    public final void zza(String str, Object obj) {
        zza(2, str, obj, null, null);
    }

    @Hide
    public final void zza(String str, Object obj, Object obj2) {
        zza(2, str, obj, obj2, null);
    }

    @Hide
    public final void zza(String str, Object obj, Object obj2, Object obj3) {
        zza(3, str, obj, obj2, obj3);
    }

    @Hide
    public final void zzb(String str) {
        zza(2, str, null, null, null);
    }

    @Hide
    public final void zzb(String str, Object obj) {
        zza(3, str, obj, null, null);
    }

    @Hide
    public final void zzb(String str, Object obj, Object obj2) {
        zza(3, str, obj, obj2, null);
    }

    @Hide
    public final void zzb(String str, Object obj, Object obj2, Object obj3) {
        zza(5, str, obj, obj2, obj3);
    }

    @Hide
    public final void zzc(String str) {
        zza(3, str, null, null, null);
    }

    @Hide
    public final void zzc(String str, Object obj) {
        zza(4, str, obj, null, null);
    }

    @Hide
    public final void zzc(String str, Object obj, Object obj2) {
        zza(5, str, obj, obj2, null);
    }

    @Hide
    public final void zzd(String str) {
        zza(4, str, null, null, null);
    }

    @Hide
    public final void zzd(String str, Object obj) {
        zza(5, str, obj, null, null);
    }

    @Hide
    public final void zzd(String str, Object obj, Object obj2) {
        zza(6, str, obj, obj2, null);
    }

    @Hide
    public final void zze(String str) {
        zza(5, str, null, null, null);
    }

    @Hide
    public final void zze(String str, Object obj) {
        zza(6, str, obj, null, null);
    }

    @Hide
    public final void zzf(String str) {
        zza(6, str, null, null, null);
    }

    @Hide
    public final zzark zzi() {
        return this.zza;
    }

    @Hide
    protected final zze zzj() {
        return this.zza.zzc();
    }

    @Hide
    protected final Context zzk() {
        return this.zza.zza();
    }

    @Hide
    protected final zzatd zzl() {
        return this.zza.zze();
    }

    @Hide
    protected final zzasl zzm() {
        return this.zza.zzd();
    }

    @Hide
    protected final zzk zzn() {
        return this.zza.zzg();
    }

    @Hide
    public final GoogleAnalytics zzo() {
        return this.zza.zzj();
    }

    @Hide
    protected final zzaqz zzp() {
        return this.zza.zzh();
    }

    @Hide
    protected final zzasq zzq() {
        return this.zza.zzi();
    }

    @Hide
    protected final zzatu zzr() {
        return this.zza.zzk();
    }

    @Hide
    protected final zzath zzs() {
        return this.zza.zzl();
    }

    @Hide
    protected final zzasc zzt() {
        return this.zza.zzo();
    }

    @Hide
    protected final zzaqy zzu() {
        return this.zza.zzn();
    }

    @Hide
    protected final zzarv zzv() {
        return this.zza.zzp();
    }

    @Hide
    protected final zzasp zzw() {
        return this.zza.zzq();
    }
}
