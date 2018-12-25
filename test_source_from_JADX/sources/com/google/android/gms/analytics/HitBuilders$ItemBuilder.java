package com.google.android.gms.analytics;

import com.google.android.gms.analytics.HitBuilders.HitBuilder;

@Deprecated
public class HitBuilders$ItemBuilder extends HitBuilder<HitBuilders$ItemBuilder> {
    public HitBuilders$ItemBuilder() {
        set("&t", "item");
    }

    public HitBuilders$ItemBuilder setCategory(String str) {
        set("&iv", str);
        return this;
    }

    public HitBuilders$ItemBuilder setCurrencyCode(String str) {
        set("&cu", str);
        return this;
    }

    public HitBuilders$ItemBuilder setName(String str) {
        set("&in", str);
        return this;
    }

    public HitBuilders$ItemBuilder setPrice(double d) {
        set("&ip", Double.toString(d));
        return this;
    }

    public HitBuilders$ItemBuilder setQuantity(long j) {
        set("&iq", Long.toString(j));
        return this;
    }

    public HitBuilders$ItemBuilder setSku(String str) {
        set("&ic", str);
        return this;
    }

    public HitBuilders$ItemBuilder setTransactionId(String str) {
        set("&ti", str);
        return this;
    }
}
