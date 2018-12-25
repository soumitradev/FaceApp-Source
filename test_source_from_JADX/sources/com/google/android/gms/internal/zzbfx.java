package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public class zzbfx<T> {
    private static final Object zza = new Object();
    private static zzbgd zzb = null;
    private static int zzc = 0;
    private static String zzd = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    private String zze;
    private T zzf;
    private T zzg = null;

    protected zzbfx(String str, T t) {
        this.zze = str;
        this.zzf = t;
    }

    public static zzbfx<Float> zza(String str, Float f) {
        return new zzbgb(str, f);
    }

    public static zzbfx<Integer> zza(String str, Integer num) {
        return new zzbga(str, num);
    }

    public static zzbfx<Long> zza(String str, Long l) {
        return new zzbfz(str, l);
    }

    public static zzbfx<String> zza(String str, String str2) {
        return new zzbgc(str, str2);
    }

    public static zzbfx<Boolean> zza(String str, boolean z) {
        return new zzbfy(str, Boolean.valueOf(z));
    }
}
