package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.Constants;

class Predicates$CompositionPredicate<A, B> implements Predicate<A>, Serializable {
    private static final long serialVersionUID = 0;
    /* renamed from: f */
    final Function<A, ? extends B> f128f;
    /* renamed from: p */
    final Predicate<B> f129p;

    private Predicates$CompositionPredicate(Predicate<B> p, Function<A, ? extends B> f) {
        this.f129p = (Predicate) Preconditions.checkNotNull(p);
        this.f128f = (Function) Preconditions.checkNotNull(f);
    }

    public boolean apply(@Nullable A a) {
        return this.f129p.apply(this.f128f.apply(a));
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Predicates$CompositionPredicate)) {
            return false;
        }
        Predicates$CompositionPredicate<?, ?> that = (Predicates$CompositionPredicate) obj;
        if (this.f128f.equals(that.f128f) && this.f129p.equals(that.f129p)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.f128f.hashCode() ^ this.f129p.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f129p);
        stringBuilder.append(Constants.OPENING_BRACE);
        stringBuilder.append(this.f128f);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
