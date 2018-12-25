package com.google.common.base;

import javax.annotation.Nullable;

enum Predicates$ObjectPredicate implements Predicate<Object> {
    ALWAYS_TRUE {
        public boolean apply(@Nullable Object o) {
            return true;
        }

        public String toString() {
            return "Predicates.alwaysTrue()";
        }
    },
    ALWAYS_FALSE {
        public boolean apply(@Nullable Object o) {
            return false;
        }

        public String toString() {
            return "Predicates.alwaysFalse()";
        }
    },
    IS_NULL {
        public boolean apply(@Nullable Object o) {
            return o == null;
        }

        public String toString() {
            return "Predicates.isNull()";
        }
    },
    NOT_NULL {
        public boolean apply(@Nullable Object o) {
            return o != null;
        }

        public String toString() {
            return "Predicates.notNull()";
        }
    };

    <T> Predicate<T> withNarrowedType() {
        return this;
    }
}
