package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import javax.annotation.CheckReturnValue;

@GwtCompatible
@CheckReturnValue
@Beta
public abstract class MultimapBuilder<K0, V0> {
    private static final int DEFAULT_EXPECTED_KEYS = 8;

    public abstract <K extends K0, V extends V0> Multimap<K, V> build();

    private MultimapBuilder() {
    }

    public static MultimapBuilder$MultimapBuilderWithKeys<Object> hashKeys() {
        return hashKeys(8);
    }

    public static MultimapBuilder$MultimapBuilderWithKeys<Object> hashKeys(int expectedKeys) {
        CollectPreconditions.checkNonnegative(expectedKeys, "expectedKeys");
        return new MultimapBuilder$1(expectedKeys);
    }

    public static MultimapBuilder$MultimapBuilderWithKeys<Object> linkedHashKeys() {
        return linkedHashKeys(8);
    }

    public static MultimapBuilder$MultimapBuilderWithKeys<Object> linkedHashKeys(int expectedKeys) {
        CollectPreconditions.checkNonnegative(expectedKeys, "expectedKeys");
        return new MultimapBuilder$2(expectedKeys);
    }

    public static MultimapBuilder$MultimapBuilderWithKeys<Comparable> treeKeys() {
        return treeKeys(Ordering.natural());
    }

    public static <K0> MultimapBuilder$MultimapBuilderWithKeys<K0> treeKeys(Comparator<K0> comparator) {
        Preconditions.checkNotNull(comparator);
        return new MultimapBuilder$3(comparator);
    }

    public static <K0 extends Enum<K0>> MultimapBuilder$MultimapBuilderWithKeys<K0> enumKeys(Class<K0> keyClass) {
        Preconditions.checkNotNull(keyClass);
        return new MultimapBuilder$4(keyClass);
    }

    public <K extends K0, V extends V0> Multimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
        Multimap<K, V> result = build();
        result.putAll(multimap);
        return result;
    }
}
