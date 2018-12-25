package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible
@CheckReturnValue
public abstract class Equivalence<T> {

    public static final class Wrapper<T> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Equivalence<? super T> equivalence;
        @Nullable
        private final T reference;

        private Wrapper(Equivalence<? super T> equivalence, @Nullable T reference) {
            this.equivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
            this.reference = reference;
        }

        @Nullable
        public T get() {
            return this.reference;
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Wrapper) {
                Wrapper<?> that = (Wrapper) obj;
                if (this.equivalence.equals(that.equivalence)) {
                    return this.equivalence.equivalent(this.reference, that.reference);
                }
            }
            return false;
        }

        public int hashCode() {
            return this.equivalence.hash(this.reference);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.equivalence);
            stringBuilder.append(".wrap(");
            stringBuilder.append(this.reference);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class EquivalentToPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Equivalence<T> equivalence;
        @Nullable
        private final T target;

        EquivalentToPredicate(Equivalence<T> equivalence, @Nullable T target) {
            this.equivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
            this.target = target;
        }

        public boolean apply(@Nullable T input) {
            return this.equivalence.equivalent(input, this.target);
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EquivalentToPredicate)) {
                return false;
            }
            EquivalentToPredicate<?> that = (EquivalentToPredicate) obj;
            if (!this.equivalence.equals(that.equivalence) || !Objects.equal(this.target, that.target)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return Objects.hashCode(this.equivalence, this.target);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.equivalence);
            stringBuilder.append(".equivalentTo(");
            stringBuilder.append(this.target);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    protected abstract boolean doEquivalent(T t, T t2);

    protected abstract int doHash(T t);

    protected Equivalence() {
    }

    public final boolean equivalent(@Nullable T a, @Nullable T b) {
        if (a == b) {
            return true;
        }
        if (a != null) {
            if (b != null) {
                return doEquivalent(a, b);
            }
        }
        return false;
    }

    public final int hash(@Nullable T t) {
        if (t == null) {
            return 0;
        }
        return doHash(t);
    }

    public final <F> Equivalence<F> onResultOf(Function<F, ? extends T> function) {
        return new FunctionalEquivalence(function, this);
    }

    public final <S extends T> Wrapper<S> wrap(@Nullable S reference) {
        return new Wrapper(reference);
    }

    @GwtCompatible(serializable = true)
    public final <S extends T> Equivalence<Iterable<S>> pairwise() {
        return new PairwiseEquivalence(this);
    }

    @Beta
    public final Predicate<T> equivalentTo(@Nullable T target) {
        return new EquivalentToPredicate(this, target);
    }

    public static Equivalence<Object> equals() {
        return Equivalence$Equals.INSTANCE;
    }

    public static Equivalence<Object> identity() {
        return Equivalence$Identity.INSTANCE;
    }
}
