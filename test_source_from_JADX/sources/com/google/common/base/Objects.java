package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.util.Arrays;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible
public final class Objects {

    @Deprecated
    public static final class ToStringHelper {
        private final String className;
        private Objects$ToStringHelper$ValueHolder holderHead;
        private Objects$ToStringHelper$ValueHolder holderTail;
        private boolean omitNullValues;

        private ToStringHelper(String className) {
            this.holderHead = new Objects$ToStringHelper$ValueHolder(null);
            this.holderTail = this.holderHead;
            this.omitNullValues = false;
            this.className = (String) Preconditions.checkNotNull(className);
        }

        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        public ToStringHelper add(String name, @Nullable Object value) {
            return addHolder(name, value);
        }

        public ToStringHelper add(String name, boolean value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, char value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, double value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, float value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, int value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, long value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper addValue(@Nullable Object value) {
            return addHolder(value);
        }

        public ToStringHelper addValue(boolean value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(char value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(double value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(float value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(int value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(long value) {
            return addHolder(String.valueOf(value));
        }

        public String toString() {
            boolean omitNullValuesSnapshot = this.omitNullValues;
            String nextSeparator = "";
            StringBuilder builder = new StringBuilder(32);
            builder.append(this.className);
            builder = builder.append('{');
            Objects$ToStringHelper$ValueHolder valueHolder = this.holderHead.next;
            while (valueHolder != null) {
                if (!omitNullValuesSnapshot || valueHolder.value != null) {
                    builder.append(nextSeparator);
                    nextSeparator = ", ";
                    if (valueHolder.name != null) {
                        builder.append(valueHolder.name);
                        builder.append('=');
                    }
                    builder.append(valueHolder.value);
                }
                valueHolder = valueHolder.next;
            }
            builder.append('}');
            return builder.toString();
        }

        private Objects$ToStringHelper$ValueHolder addHolder() {
            Objects$ToStringHelper$ValueHolder valueHolder = new Objects$ToStringHelper$ValueHolder(null);
            this.holderTail.next = valueHolder;
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private ToStringHelper addHolder(@Nullable Object value) {
            addHolder().value = value;
            return this;
        }

        private ToStringHelper addHolder(String name, @Nullable Object value) {
            Objects$ToStringHelper$ValueHolder valueHolder = addHolder();
            valueHolder.value = value;
            valueHolder.name = (String) Preconditions.checkNotNull(name);
            return this;
        }
    }

    private Objects() {
    }

    @CheckReturnValue
    public static boolean equal(@Nullable Object a, @Nullable Object b) {
        if (a != b) {
            if (a == null || !a.equals(b)) {
                return false;
            }
        }
        return true;
    }

    @CheckReturnValue
    public static int hashCode(@Nullable Object... objects) {
        return Arrays.hashCode(objects);
    }

    @CheckReturnValue
    @Deprecated
    public static ToStringHelper toStringHelper(Object self) {
        return new ToStringHelper(self.getClass().getSimpleName());
    }

    @CheckReturnValue
    @Deprecated
    public static ToStringHelper toStringHelper(Class<?> clazz) {
        return new ToStringHelper(clazz.getSimpleName());
    }

    @CheckReturnValue
    @Deprecated
    public static ToStringHelper toStringHelper(String className) {
        return new ToStringHelper(className);
    }

    @CheckReturnValue
    @Deprecated
    public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
        return MoreObjects.firstNonNull(first, second);
    }
}
