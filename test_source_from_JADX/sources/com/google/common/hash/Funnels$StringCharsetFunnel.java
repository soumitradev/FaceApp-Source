package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

class Funnels$StringCharsetFunnel implements Funnel<CharSequence>, Serializable {
    private final Charset charset;

    /* renamed from: com.google.common.hash.Funnels$StringCharsetFunnel$SerializedForm */
    private static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final String charsetCanonicalName;

        SerializedForm(Charset charset) {
            this.charsetCanonicalName = charset.name();
        }

        private Object readResolve() {
            return Funnels.stringFunnel(Charset.forName(this.charsetCanonicalName));
        }
    }

    Funnels$StringCharsetFunnel(Charset charset) {
        this.charset = (Charset) Preconditions.checkNotNull(charset);
    }

    public void funnel(CharSequence from, PrimitiveSink into) {
        into.putString(from, this.charset);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Funnels.stringFunnel(");
        stringBuilder.append(this.charset.name());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof Funnels$StringCharsetFunnel)) {
            return false;
        }
        return this.charset.equals(((Funnels$StringCharsetFunnel) o).charset);
    }

    public int hashCode() {
        return Funnels$StringCharsetFunnel.class.hashCode() ^ this.charset.hashCode();
    }

    Object writeReplace() {
        return new SerializedForm(this.charset);
    }
}
