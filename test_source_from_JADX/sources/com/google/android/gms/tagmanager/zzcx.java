package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.google.android.gms.common.internal.Hide;
import java.util.HashMap;
import java.util.Map;

@Hide
public class zzcx {
    static Map<String, String> zza = new HashMap();
    private static String zzb;

    public static String zza(String str, String str2) {
        if (str2 == null) {
            return str.length() > 0 ? str : null;
        } else {
            String str3 = "http://hostname/?";
            str = String.valueOf(str);
            return Uri.parse(str.length() != 0 ? str3.concat(str) : new String(str3)).getQueryParameter(str2);
        }
    }

    static void zza(Context context, String str) {
        zzfu.zza(context, "gtm_install_referrer", "referrer", str);
        zzc(context, str);
    }

    public static void zza(String str) {
        synchronized (zzcx.class) {
            zzb = str;
        }
    }

    public static String zzb(Context context, String str) {
        if (zzb == null) {
            synchronized (zzcx.class) {
                if (zzb == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    zzb = sharedPreferences != null ? sharedPreferences.getString("referrer", "") : "";
                }
            }
        }
        return zza(zzb, str);
    }

    public static void zzc(Context context, String str) {
        String zza = zza(str, "conv");
        if (zza != null && zza.length() > 0) {
            zza.put(zza, str);
            zzfu.zza(context, "gtm_click_referrers", zza, str);
        }
    }
}
