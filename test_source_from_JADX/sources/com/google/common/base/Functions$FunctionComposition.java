package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.Constants;

class Functions$FunctionComposition<A, B, C> implements Function<A, C>, Serializable {
    private static final long serialVersionUID = 0;
    /* renamed from: f */
    private final Function<A, ? extends B> f126f;
    /* renamed from: g */
    private final Function<B, C> f127g;

    public Functions$FunctionComposition(Function<B, C> g, Function<A, ? extends B> f) {
        this.f127g = (Function) Preconditions.checkNotNull(g);
        this.f126f = (Function) Preconditions.checkNotNull(f);
    }

    public C apply(@Nullable A a) {
        return this.f127g.apply(this.f126f.apply(a));
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Functions$FunctionComposition)) {
            return false;
        }
        Functions$FunctionComposition<?, ?, ?> that = (Functions$FunctionComposition) obj;
        if (this.f126f.equals(that.f126f) && this.f127g.equals(that.f127g)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.f126f.hashCode() ^ this.f127g.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f127g);
        stringBuilder.append(Constants.OPENING_BRACE);
        stringBuilder.append(this.f126f);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
