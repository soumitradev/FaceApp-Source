package com.google.android.gms.analytics.ecommerce;

import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.zzd;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Product {
    private Map<String, String> zza = new HashMap();

    private final void zza(String str, String str2) {
        zzbq.zza(str, "Name should be non-null");
        this.zza.put(str, str2);
    }

    public Product setBrand(String str) {
        zza("br", str);
        return this;
    }

    public Product setCategory(String str) {
        zza("ca", str);
        return this;
    }

    public Product setCouponCode(String str) {
        zza("cc", str);
        return this;
    }

    public Product setCustomDimension(int i, String str) {
        zza(zzd.zzl(i), str);
        return this;
    }

    public Product setCustomMetric(int i, int i2) {
        zza(zzd.zzm(i), Integer.toString(i2));
        return this;
    }

    public Product setId(String str) {
        zza(ShareConstants.WEB_DIALOG_PARAM_ID, str);
        return this;
    }

    public Product setName(String str) {
        zza("nm", str);
        return this;
    }

    public Product setPosition(int i) {
        zza("ps", Integer.toString(i));
        return this;
    }

    public Product setPrice(double d) {
        zza("pr", Double.toString(d));
        return this;
    }

    public Product setQuantity(int i) {
        zza("qt", Integer.toString(i));
        return this;
    }

    public Product setVariant(String str) {
        zza("va", str);
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
