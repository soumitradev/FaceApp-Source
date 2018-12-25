package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.concurrent.TimeUnit;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible
@CheckReturnValue
public final class Suppliers {
    private Suppliers() {
    }

    public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(supplier);
        return new Suppliers$SupplierComposition(function, supplier);
    }

    public static <T> Supplier<T> memoize(Supplier<T> delegate) {
        return delegate instanceof Suppliers$MemoizingSupplier ? delegate : new Suppliers$MemoizingSupplier((Supplier) Preconditions.checkNotNull(delegate));
    }

    public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> delegate, long duration, TimeUnit unit) {
        return new Suppliers$ExpiringMemoizingSupplier(delegate, duration, unit);
    }

    public static <T> Supplier<T> ofInstance(@Nullable T instance) {
        return new Suppliers$SupplierOfInstance(instance);
    }

    public static <T> Supplier<T> synchronizedSupplier(Supplier<T> delegate) {
        return new Suppliers$ThreadSafeSupplier((Supplier) Preconditions.checkNotNull(delegate));
    }

    @Beta
    public static <T> Function<Supplier<T>, T> supplierFunction() {
        return Suppliers$SupplierFunctionImpl.INSTANCE;
    }
}
