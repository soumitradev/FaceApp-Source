package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzbep {
    private static final zzbei zza = new zzbei("MetadataUtils");
    private static final String[] zzb = new String[]{"Z", "+hh", "+hhmm", "+hh:mm"};
    private static final String zzc;

    static {
        String valueOf = String.valueOf("yyyyMMdd'T'HHmmss");
        String valueOf2 = String.valueOf(zzb[0]);
        zzc = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public static String zza(Calendar calendar) {
        if (calendar == null) {
            zza.zza("Calendar object cannot be null", new Object[0]);
            return null;
        }
        String str = zzc;
        if (calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0) {
            str = "yyyyMMdd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        String format = simpleDateFormat.format(calendar.getTime());
        if (format.endsWith("+0000")) {
            format = format.replace("+0000", zzb[0]);
        }
        return format;
    }

    public static Calendar zza(String str) {
        if (TextUtils.isEmpty(str)) {
            zza.zza("Input string is empty or null", new Object[0]);
            return null;
        }
        String zzb = zzb(str);
        if (TextUtils.isEmpty(zzb)) {
            zza.zza("Invalid date format", new Object[0]);
            return null;
        }
        str = zzc(str);
        String str2 = "yyyyMMdd";
        if (!TextUtils.isEmpty(str)) {
            zzb = String.valueOf(zzb);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzb).length() + 1) + String.valueOf(str).length());
            stringBuilder.append(zzb);
            stringBuilder.append("T");
            stringBuilder.append(str);
            zzb = stringBuilder.toString();
            str2 = str.length() == 6 ? "yyyyMMdd'T'HHmmss" : zzc;
        }
        Calendar instance = GregorianCalendar.getInstance();
        try {
            instance.setTime(new SimpleDateFormat(str2).parse(zzb));
            return instance;
        } catch (ParseException e) {
            zza.zza("Error parsing string: %s", new Object[]{e.getMessage()});
            return null;
        }
    }

    public static void zza(List<WebImage> list, JSONObject jSONObject) {
        try {
            list.clear();
            JSONArray jSONArray = jSONObject.getJSONArray(Constants.IMAGE_DIRECTORY_NAME);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                try {
                    list.add(new WebImage(jSONArray.getJSONObject(i)));
                } catch (IllegalArgumentException e) {
                }
            }
        } catch (JSONException e2) {
        }
    }

    public static void zza(JSONObject jSONObject, List<WebImage> list) {
        if (!(list == null || list.isEmpty())) {
            JSONArray jSONArray = new JSONArray();
            for (WebImage zza : list) {
                jSONArray.put(zza.zza());
            }
            try {
                jSONObject.put(Constants.IMAGE_DIRECTORY_NAME, jSONArray);
            } catch (JSONException e) {
            }
        }
    }

    private static String zzb(String str) {
        if (TextUtils.isEmpty(str)) {
            zza.zza("Input string is empty or null", new Object[0]);
            return null;
        }
        try {
            return str.substring(0, 8);
        } catch (IndexOutOfBoundsException e) {
            zza.zzb("Error extracting the date: %s", new Object[]{e.getMessage()});
            return null;
        }
    }

    private static String zzc(String str) {
        if (TextUtils.isEmpty(str)) {
            zza.zza("string is empty or null", new Object[0]);
            return null;
        }
        int indexOf = str.indexOf(84);
        int i = indexOf + 1;
        if (indexOf != 8) {
            zza.zza("T delimeter is not found", new Object[0]);
            return null;
        }
        indexOf = 1;
        try {
            str = str.substring(i);
            if (str.length() == 6) {
                return str;
            }
            char charAt = str.charAt(6);
            if (charAt == '+' || charAt == '-') {
                i = str.length();
                if (!(i == zzb[1].length() + 6 || i == zzb[2].length() + 6)) {
                    if (i != zzb[3].length() + 6) {
                        indexOf = 0;
                    }
                }
                return indexOf != 0 ? str.replaceAll("([\\+\\-]\\d\\d):(\\d\\d)", "$1$2") : null;
            } else if (charAt != 'Z' || str.length() != zzb[0].length() + 6) {
                return null;
            } else {
                str = String.valueOf(str.substring(0, str.length() - 1));
                String valueOf = String.valueOf("+0000");
                return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
            }
        } catch (IndexOutOfBoundsException e) {
            zza.zza("Error extracting the time substring: %s", new Object[]{e.getMessage()});
            return null;
        }
    }
}
