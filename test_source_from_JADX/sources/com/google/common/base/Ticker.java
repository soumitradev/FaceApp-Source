package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import javax.annotation.CheckReturnValue;

@GwtCompatible
@Beta
public abstract class Ticker {
    private static final Ticker SYSTEM_TICKER = new Ticker$1();

    public abstract long read();

    protected Ticker() {
    }

    @CheckReturnValue
    public static Ticker systemTicker() {
        return SYSTEM_TICKER;
    }
}
