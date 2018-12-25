package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

public abstract class MultimapBuilder$MultimapBuilderWithKeys<K0> {
    private static final int DEFAULT_EXPECTED_VALUES_PER_KEY = 2;

    /* renamed from: com.google.common.collect.MultimapBuilder$MultimapBuilderWithKeys$2 */
    class C12832 extends MultimapBuilder$ListMultimapBuilder<K0, Object> {
        C12832() {
        }

        public <K extends K0, V> ListMultimap<K, V> build() {
            return Multimaps.newListMultimap(MultimapBuilder$MultimapBuilderWithKeys.this.createMap(), MultimapBuilder$LinkedListSupplier.instance());
        }
    }

    abstract <K extends K0, V> Map<K, Collection<V>> createMap();

    MultimapBuilder$MultimapBuilderWithKeys() {
    }

    public MultimapBuilder$ListMultimapBuilder<K0, Object> arrayListValues() {
        return arrayListValues(2);
    }

    public MultimapBuilder$ListMultimapBuilder<K0, Object> arrayListValues(final int expectedValuesPerKey) {
        CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
        return new MultimapBuilder$ListMultimapBuilder<K0, Object>() {
            public <K extends K0, V> ListMultimap<K, V> build() {
                return Multimaps.newListMultimap(MultimapBuilder$MultimapBuilderWithKeys.this.createMap(), new MultimapBuilder$ArrayListSupplier(expectedValuesPerKey));
            }
        };
    }

    public MultimapBuilder$ListMultimapBuilder<K0, Object> linkedListValues() {
        return new C12832();
    }

    public MultimapBuilder$SetMultimapBuilder<K0, Object> hashSetValues() {
        return hashSetValues(2);
    }

    public MultimapBuilder$SetMultimapBuilder<K0, Object> hashSetValues(final int expectedValuesPerKey) {
        CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
        return new MultimapBuilder$SetMultimapBuilder<K0, Object>() {
            public <K extends K0, V> SetMultimap<K, V> build() {
                return Multimaps.newSetMultimap(MultimapBuilder$MultimapBuilderWithKeys.this.createMap(), new MultimapBuilder$HashSetSupplier(expectedValuesPerKey));
            }
        };
    }

    public MultimapBuilder$SetMultimapBuilder<K0, Object> linkedHashSetValues() {
        return linkedHashSetValues(2);
    }

    public MultimapBuilder$SetMultimapBuilder<K0, Object> linkedHashSetValues(final int expectedValuesPerKey) {
        CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
        return new MultimapBuilder$SetMultimapBuilder<K0, Object>() {
            public <K extends K0, V> SetMultimap<K, V> build() {
                return Multimaps.newSetMultimap(MultimapBuilder$MultimapBuilderWithKeys.this.createMap(), new MultimapBuilder$LinkedHashSetSupplier(expectedValuesPerKey));
            }
        };
    }

    public MultimapBuilder$SortedSetMultimapBuilder<K0, Comparable> treeSetValues() {
        return treeSetValues(Ordering.natural());
    }

    public <V0> MultimapBuilder$SortedSetMultimapBuilder<K0, V0> treeSetValues(final Comparator<V0> comparator) {
        Preconditions.checkNotNull(comparator, "comparator");
        return new MultimapBuilder$SortedSetMultimapBuilder<K0, V0>() {
            public <K extends K0, V extends V0> SortedSetMultimap<K, V> build() {
                return Multimaps.newSortedSetMultimap(MultimapBuilder$MultimapBuilderWithKeys.this.createMap(), new MultimapBuilder$TreeSetSupplier(comparator));
            }
        };
    }

    public <V0 extends Enum<V0>> MultimapBuilder$SetMultimapBuilder<K0, V0> enumSetValues(final Class<V0> valueClass) {
        Preconditions.checkNotNull(valueClass, "valueClass");
        return new MultimapBuilder$SetMultimapBuilder<K0, V0>() {
            public <K extends K0, V extends V0> SetMultimap<K, V> build() {
                return Multimaps.newSetMultimap(MultimapBuilder$MultimapBuilderWithKeys.this.createMap(), new MultimapBuilder$EnumSetSupplier(valueClass));
            }
        };
    }
}
