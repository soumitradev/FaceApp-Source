package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzo;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.catrobat.catroid.common.BrickValues;

@Hide
public final class zzatt {
    private static final char[] zza = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static double zza(String str, double d) {
        if (str == null) {
            return 100.0d;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 100.0d;
        }
    }

    public static zzaqm zza(zzatd zzatd, String str) {
        zzbq.zza(zzatd);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        HashMap hashMap = new HashMap();
        try {
            String str2 = "?";
            str = String.valueOf(str);
            Map zza = zzo.zza(new URI(str.length() != 0 ? str2.concat(str) : new String(str2)), "UTF-8");
            zzaqm zzaqm = new zzaqm();
            zzaqm.zze((String) zza.get("utm_content"));
            zzaqm.zzc((String) zza.get("utm_medium"));
            zzaqm.zza((String) zza.get("utm_campaign"));
            zzaqm.zzb((String) zza.get("utm_source"));
            zzaqm.zzd((String) zza.get("utm_term"));
            zzaqm.zzf((String) zza.get("utm_id"));
            zzaqm.zzg((String) zza.get("anid"));
            zzaqm.zzh((String) zza.get("gclid"));
            zzaqm.zzi((String) zza.get("dclid"));
            zzaqm.zzj((String) zza.get(FirebaseAnalytics$Param.ACLID));
            return zzaqm;
        } catch (URISyntaxException e) {
            zzatd.zzd("No valid campaign data found", e);
            return null;
        }
    }

    public static String zza(Locale locale) {
        if (locale == null) {
            return null;
        }
        Object language = locale.getLanguage();
        if (TextUtils.isEmpty(language)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(language.toLowerCase());
        if (!TextUtils.isEmpty(locale.getCountry())) {
            stringBuilder.append("-");
            stringBuilder.append(locale.getCountry().toLowerCase());
        }
        return stringBuilder.toString();
    }

    public static String zza(boolean z) {
        return z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO;
    }

    public static Map<String, String> zza(String str) {
        Map<String, String> hashMap = new HashMap();
        for (String split : str.split("&")) {
            String[] split2 = split.split("=", 3);
            Object obj = null;
            if (split2.length > 1) {
                hashMap.put(split2[0], TextUtils.isEmpty(split2[1]) ? null : split2[1]);
                if (!(split2.length != 3 || TextUtils.isEmpty(split2[1]) || hashMap.containsKey(split2[1]))) {
                    Object obj2 = split2[1];
                    if (!TextUtils.isEmpty(split2[2])) {
                        obj = split2[2];
                    }
                    hashMap.put(obj2, obj);
                }
            } else if (split2.length == 1 && split2[0].length() != 0) {
                hashMap.put(split2[0], null);
            }
        }
        return hashMap;
    }

    public static void zza(Map<String, String> map, String str, String str2) {
        if (str2 != null && !map.containsKey(str)) {
            map.put(str, str2);
        }
    }

    public static void zza(Map<String, String> map, String str, Map<String, String> map2) {
        zza((Map) map, str, (String) map2.get(str));
    }

    public static void zza(Map<String, String> map, String str, boolean z) {
        if (!map.containsKey(str)) {
            map.put(str, z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        }
    }

    public static boolean zza(double d, String str) {
        if (d <= BrickValues.SET_COLOR_TO || d >= 100.0d) {
            return false;
        }
        int i;
        if (TextUtils.isEmpty(str)) {
            i = 1;
        } else {
            i = 0;
            for (int length = str.length() - 1; length >= 0; length--) {
                char charAt = str.charAt(length);
                i = (((i << 6) & 268435455) + charAt) + (charAt << 14);
                int i2 = 266338304 & i;
                if (i2 != 0) {
                    i ^= i2 >> 21;
                }
            }
        }
        return ((double) (i % 10000)) >= d * 100.0d;
    }

    public static boolean zza(Context context, String str) {
        try {
            ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context, str), 4);
            if (serviceInfo != null && serviceInfo.enabled) {
                return true;
            }
        } catch (NameNotFoundException e) {
        }
        return false;
    }

    public static boolean zza(Context context, String str, boolean z) {
        try {
            ActivityInfo receiverInfo = context.getPackageManager().getReceiverInfo(new ComponentName(context, str), 2);
            if (receiverInfo != null && receiverInfo.enabled && (!z || receiverInfo.exported)) {
                return true;
            }
        } catch (NameNotFoundException e) {
        }
        return false;
    }

    public static boolean zza(String str, boolean z) {
        if (str == null || str.equalsIgnoreCase(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE) || str.equalsIgnoreCase("yes") || str.equalsIgnoreCase(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
            return true;
        }
        if (str.equalsIgnoreCase("false") || str.equalsIgnoreCase("no") || str.equalsIgnoreCase(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
            return false;
        }
        return true;
    }

    public static long zzb(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void zzb(Map<String, String> map, String str, String str2) {
        if (str2 != null && TextUtils.isEmpty((CharSequence) map.get(str))) {
            map.put(str, str2);
        }
    }

    public static String zzc(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split;
        if (str.contains("?")) {
            split = str.split("[\\?]");
            if (split.length > 1) {
                str = split[1];
            }
        }
        if (str.contains("%3D")) {
            try {
                str = URLDecoder.decode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else if (!str.contains("=")) {
            return null;
        }
        Map zza = zza(str);
        split = new String[]{"dclid", "utm_source", "gclid", FirebaseAnalytics$Param.ACLID, "utm_campaign", "utm_medium", "utm_term", "utm_content", "utm_id", "anid", "gmob_t"};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            if (!TextUtils.isEmpty((CharSequence) zza.get(split[i]))) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("&");
                }
                stringBuilder.append(split[i]);
                stringBuilder.append("=");
                stringBuilder.append((String) zza.get(split[i]));
            }
        }
        return stringBuilder.toString();
    }

    public static MessageDigest zzd(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }

    public static boolean zze(String str) {
        return TextUtils.isEmpty(str) || !str.startsWith("http:");
    }
}
