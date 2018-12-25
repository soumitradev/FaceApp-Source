package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.nio.charset.Charset;
import org.apache.commons.compress.utils.CharsetNames;

@GwtCompatible(emulated = true)
public final class Charsets {
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset ISO_8859_1 = Charset.forName(CharsetNames.ISO_8859_1);
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset US_ASCII = Charset.forName(CharsetNames.US_ASCII);
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset UTF_16 = Charset.forName(CharsetNames.UTF_16);
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset UTF_16BE = Charset.forName(CharsetNames.UTF_16BE);
    @GwtIncompatible("Non-UTF-8 Charset")
    public static final Charset UTF_16LE = Charset.forName(CharsetNames.UTF_16LE);
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private Charsets() {
    }
}
