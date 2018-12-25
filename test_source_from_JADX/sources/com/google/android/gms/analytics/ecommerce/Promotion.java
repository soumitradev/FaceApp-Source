package com.google.android.gms.analytics.ecommerce;

import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Promotion {
    public static final String ACTION_CLICK = "click";
    public static final String ACTION_VIEW = "view";
    private Map<String, String> zza = new HashMap();

    private final void zza(String str, String str2) {
        zzbq.zza(str, "Name should be non-null");
        this.zza.put(str, str2);
    }

    public Promotion setCreative(String str) {
        zza("cr", str);
        return this;
    }

    public Promotion setId(String str) {
        zza(ShareConstants.WEB_DIALOG_PARAM_ID, str);
        return this;
    }

    public Promotion setName(String str) {
        zza("nm", str);
        return this;
    }

    public Promotion setPosition(String str) {
        zza("ps", str);
        return this;
    }

    public String toString() {
        return zzi.zza(this.zza);
    }

    @Hide
    public final Map<String, String> zza(String str) {
        Map<String, String> hashMap = new HashMap();
        for (Entry entry : this.zza.entrySet()) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf((String) entry.getKey());
            hashMap.put(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), (String) entry.getValue());
        }
        return hashMap;
    }
}
