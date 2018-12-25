package com.google.android.gms.analytics;

import com.google.android.gms.analytics.HitBuilders.HitBuilder;

@Deprecated
public class HitBuilders$TransactionBuilder extends HitBuilder<HitBuilders$TransactionBuilder> {
    public HitBuilders$TransactionBuilder() {
        set("&t", "transaction");
    }

    public HitBuilders$TransactionBuilder setAffiliation(String str) {
        set("&ta", str);
        return this;
    }

    public HitBuilders$TransactionBuilder setCurrencyCode(String str) {
        set("&cu", str);
        return this;
    }

    public HitBuilders$TransactionBuilder setRevenue(double d) {
        set("&tr", Double.toString(d));
        return this;
    }

    public HitBuilders$TransactionBuilder setShipping(double d) {
        set("&ts", Double.toString(d));
        return this;
    }

    public HitBuilders$TransactionBuilder setTax(double d) {
        set("&tt", Double.toString(d));
        return this;
    }

    public HitBuilders$TransactionBuilder setTransactionId(String str) {
        set("&ti", str);
        return this;
    }
}
