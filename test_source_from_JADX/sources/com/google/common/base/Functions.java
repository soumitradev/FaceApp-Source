package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible
@CheckReturnValue
public final class Functions {

    private enum IdentityFunction implements Function<Object, Object> {
        INSTANCE;

        @Nullable
        public Object apply(@Nullable Object o) {
            return o;
        }

        public String toString() {
            return "Functions.identity()";
        }
    }

    private Functions() {
    }

    public static Function<Object, String> toStringFunction() {
        return Functions$ToStringFunction.INSTANCE;
    }

    public static <E> Function<E, E> identity() {
        return IdentityFunction.INSTANCE;
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new Functions$FunctionForMapNoDefault(map);
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @Nullable V defaultValue) {
        return new Functions$ForMapWithDefault(map, defaultValue);
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, ? extends B> f) {
        return new Functions$FunctionComposition(g, f);
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new Functions$PredicateFunction(predicate, null);
    }

    public static <E> Function<Object, E> constant(@Nullable E value) {
        return new Functions$ConstantFunction(value);
    }

    @Beta
    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new Functions$SupplierFunction(supplier, null);
    }
}
