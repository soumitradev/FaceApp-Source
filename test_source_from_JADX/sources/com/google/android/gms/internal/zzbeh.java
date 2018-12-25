package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api.zzf;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;

public final class zzbeh {
    public static final zzf<zzbdp> zza = new zzf();
    public static final zzf<zzbfa> zzb = new zzf();
    public static final zzf<zzbez> zzc = new zzf();
    private static zzf<Object> zzd = new zzf();
    private static zzf<Object> zze = new zzf();
    private static Charset zzf;
    private static String zzg = zzbdw.zzb("com.google.cast.multizone");

    static {
        Charset forName;
        try {
            forName = Charset.forName("UTF-8");
        } catch (IllegalCharsetNameException e) {
            forName = null;
        }
        zzf = forName;
    }
}
