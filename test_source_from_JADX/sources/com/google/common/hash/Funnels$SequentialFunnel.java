package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import javax.annotation.Nullable;

class Funnels$SequentialFunnel<E> implements Funnel<Iterable<? extends E>>, Serializable {
    private final Funnel<E> elementFunnel;

    Funnels$SequentialFunnel(Funnel<E> elementFunnel) {
        this.elementFunnel = (Funnel) Preconditions.checkNotNull(elementFunnel);
    }

    public void funnel(Iterable<? extends E> from, PrimitiveSink into) {
        for (E e : from) {
            this.elementFunnel.funnel(e, into);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Funnels.sequentialFunnel(");
        stringBuilder.append(this.elementFunnel);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof Funnels$SequentialFunnel)) {
            return false;
        }
        return this.elementFunnel.equals(((Funnels$SequentialFunnel) o).elementFunnel);
    }

    public int hashCode() {
        return Funnels$SequentialFunnel.class.hashCode() ^ this.elementFunnel.hashCode();
    }
}
