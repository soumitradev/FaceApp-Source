package com.koushikdutta.async.future;

public abstract class ConvertFuture<T, F> extends TransformFuture<T, F> {
    protected abstract Future<T> convert(F f) throws Exception;

    protected final void transform(F result) throws Exception {
        setComplete(convert(result));
    }
}
