package com.google.common.hash;

import com.google.common.annotations.Beta;
import java.io.OutputStream;
import java.nio.charset.Charset;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
@Beta
public final class Funnels {
    private Funnels() {
    }

    public static Funnel<byte[]> byteArrayFunnel() {
        return Funnels$ByteArrayFunnel.INSTANCE;
    }

    public static Funnel<CharSequence> unencodedCharsFunnel() {
        return Funnels$UnencodedCharsFunnel.INSTANCE;
    }

    public static Funnel<CharSequence> stringFunnel(Charset charset) {
        return new Funnels$StringCharsetFunnel(charset);
    }

    public static Funnel<Integer> integerFunnel() {
        return Funnels$IntegerFunnel.INSTANCE;
    }

    public static <E> Funnel<Iterable<? extends E>> sequentialFunnel(Funnel<E> elementFunnel) {
        return new Funnels$SequentialFunnel(elementFunnel);
    }

    public static Funnel<Long> longFunnel() {
        return Funnels$LongFunnel.INSTANCE;
    }

    public static OutputStream asOutputStream(PrimitiveSink sink) {
        return new Funnels$SinkAsStream(sink);
    }
}
