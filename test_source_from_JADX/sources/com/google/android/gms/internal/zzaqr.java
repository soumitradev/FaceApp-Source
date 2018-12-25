package com.google.android.gms.internal;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Hide
public final class zzaqr extends zzi<zzaqr> {
    private final List<Product> zza = new ArrayList();
    private final List<Promotion> zzb = new ArrayList();
    private final Map<String, List<Product>> zzc = new HashMap();
    private ProductAction zzd;

    public final String toString() {
        Map hashMap = new HashMap();
        if (!this.zza.isEmpty()) {
            hashMap.put("products", this.zza);
        }
        if (!this.zzb.isEmpty()) {
            hashMap.put("promotions", this.zzb);
        }
        if (!this.zzc.isEmpty()) {
            hashMap.put("impressions", this.zzc);
        }
        hashMap.put("productAction", this.zzd);
        return zzi.zza((Object) hashMap);
    }

    public final ProductAction zza() {
        return this.zzd;
    }

    public final /* synthetic */ void zza(zzi zzi) {
        zzaqr zzaqr = (zzaqr) zzi;
        zzaqr.zza.addAll(this.zza);
        zzaqr.zzb.addAll(this.zzb);
        for (Entry entry : this.zzc.entrySet()) {
            String str = (String) entry.getKey();
            for (Product product : (List) entry.getValue()) {
                if (product != null) {
                    Object obj = str == null ? "" : str;
                    if (!zzaqr.zzc.containsKey(obj)) {
                        zzaqr.zzc.put(obj, new ArrayList());
                    }
                    ((List) zzaqr.zzc.get(obj)).add(product);
                }
            }
        }
        if (this.zzd != null) {
            zzaqr.zzd = this.zzd;
        }
    }

    public final List<Product> zzb() {
        return Collections.unmodifiableList(this.zza);
    }

    public final Map<String, List<Product>> zzc() {
        return this.zzc;
    }

    public final List<Promotion> zzd() {
        return Collections.unmodifiableList(this.zzb);
    }
}
