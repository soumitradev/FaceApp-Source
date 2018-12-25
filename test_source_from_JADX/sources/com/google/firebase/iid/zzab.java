package com.google.firebase.iid;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement$Param;
import java.util.concurrent.TimeUnit;
import org.catrobat.catroid.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

final class zzab {
    private static final long zzb = TimeUnit.DAYS.toMillis(7);
    final String zza;
    private String zzc;
    private long zzd;

    private zzab(String str, String str2, long j) {
        this.zza = str;
        this.zzc = str2;
        this.zzd = j;
    }

    static zzab zza(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!str.startsWith("{")) {
            return new zzab(str, null, 0);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new zzab(jSONObject.getString(Constants.TOKEN), jSONObject.getString("appVersion"), jSONObject.getLong(AppMeasurement$Param.TIMESTAMP));
        } catch (JSONException e) {
            str = String.valueOf(e);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 23);
            stringBuilder.append("Failed to parse token: ");
            stringBuilder.append(str);
            Log.w("FirebaseInstanceId", stringBuilder.toString());
            return null;
        }
    }

    static String zza(String str, String str2, long j) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(Constants.TOKEN, str);
            jSONObject.put("appVersion", str2);
            jSONObject.put(AppMeasurement$Param.TIMESTAMP, j);
            return jSONObject.toString();
        } catch (JSONException e) {
            str = String.valueOf(e);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 24);
            stringBuilder.append("Failed to encode token: ");
            stringBuilder.append(str);
            Log.w("FirebaseInstanceId", stringBuilder.toString());
            return null;
        }
    }

    final boolean zzb(String str) {
        if (System.currentTimeMillis() <= this.zzd + zzb) {
            if (str.equals(this.zzc)) {
                return false;
            }
        }
        return true;
    }
}
