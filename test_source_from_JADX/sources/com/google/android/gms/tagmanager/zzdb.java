package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import com.google.android.gms.internal.zzdkj;
import com.google.android.gms.internal.zzdkl;
import com.google.android.gms.internal.zzdkm;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class zzdb {
    public static zzdkl zza(String str) throws JSONException {
        zzbt zza = zzgk.zza(zza(new JSONObject(str)));
        zzdkm zza2 = zzdkl.zza();
        for (int i = 0; i < zza.zzd.length; i++) {
            zza2.zza(zzdkj.zza().zza(zzbi.INSTANCE_NAME.toString(), zza.zzd[i]).zza(zzbi.FUNCTION.toString(), zzgk.zza(zzt.zzb())).zza(zzt.zzc(), zza.zze[i]).zza());
        }
        return zza2.zza();
    }

    private static Object zza(Object obj) throws JSONException {
        if (obj instanceof JSONArray) {
            throw new RuntimeException("JSONArrays are not supported");
        } else if (JSONObject.NULL.equals(obj)) {
            throw new RuntimeException("JSON nulls are not supported");
        } else if (!(obj instanceof JSONObject)) {
            return obj;
        } else {
            JSONObject jSONObject = (JSONObject) obj;
            Map hashMap = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                hashMap.put(str, zza(jSONObject.get(str)));
            }
            return hashMap;
        }
    }
}
