package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ProductAction {
    public static final String ACTION_ADD = "add";
    public static final String ACTION_CHECKOUT = "checkout";
    public static final String ACTION_CHECKOUT_OPTION = "checkout_option";
    @Deprecated
    public static final String ACTION_CHECKOUT_OPTIONS = "checkout_options";
    public static final String ACTION_CLICK = "click";
    public static final String ACTION_DETAIL = "detail";
    public static final String ACTION_PURCHASE = "purchase";
    public static final String ACTION_REFUND = "refund";
    public static final String ACTION_REMOVE = "remove";
    private Map<String, String> zza = new HashMap();

    public ProductAction(String str) {
        zza("&pa", str);
    }

    private final void zza(String str, String str2) {
        zzbq.zza((Object) str, (Object) "Name should be non-null");
        this.zza.put(str, str2);
    }

    public ProductAction setCheckoutOptions(String str) {
        zza("&col", str);
        return this;
    }

    public ProductAction setCheckoutStep(int i) {
        zza("&cos", Integer.toString(i));
        return this;
    }

    public ProductAction setProductActionList(String str) {
        zza("&pal", str);
        return this;
    }

    public ProductAction setProductListSource(String str) {
        zza("&pls", str);
        return this;
    }

    public ProductAction setTransactionAffiliation(String str) {
        zza("&ta", str);
        return this;
    }

    public ProductAction setTransactionCouponCode(String str) {
        zza("&tcc", str);
        return this;
    }

    public ProductAction setTransactionId(String str) {
        zza("&ti", str);
        return this;
    }

    public ProductAction setTransactionRevenue(double d) {
        zza("&tr", Double.toString(d));
        return this;
    }

    public ProductAction setTransactionShipping(double d) {
        zza("&ts", Double.toString(d));
        return this;
    }

    public ProductAction setTransactionTax(double d) {
        zza("&tt", Double.toString(d));
        return this;
    }

    public String toString() {
        Map hashMap = new HashMap();
        for (Entry entry : this.zza.entrySet()) {
            Object substring;
            if (((String) entry.getKey()).startsWith("&")) {
                substring = ((String) entry.getKey()).substring(1);
            } else {
                String str = (String) entry.getKey();
            }
            hashMap.put(substring, (String) entry.getValue());
        }
        return zzi.zza(hashMap);
    }

    @Hide
    public final Map<String, String> zza() {
        return new HashMap(this.zza);
    }
}
