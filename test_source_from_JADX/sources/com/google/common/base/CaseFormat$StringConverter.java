package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

final class CaseFormat$StringConverter extends Converter<String, String> implements Serializable {
    private static final long serialVersionUID = 0;
    private final CaseFormat sourceFormat;
    private final CaseFormat targetFormat;

    CaseFormat$StringConverter(CaseFormat sourceFormat, CaseFormat targetFormat) {
        this.sourceFormat = (CaseFormat) Preconditions.checkNotNull(sourceFormat);
        this.targetFormat = (CaseFormat) Preconditions.checkNotNull(targetFormat);
    }

    protected String doForward(String s) {
        return this.sourceFormat.to(this.targetFormat, s);
    }

    protected String doBackward(String s) {
        return this.targetFormat.to(this.sourceFormat, s);
    }

    public boolean equals(@Nullable Object object) {
        boolean z = false;
        if (!(object instanceof CaseFormat$StringConverter)) {
            return false;
        }
        CaseFormat$StringConverter that = (CaseFormat$StringConverter) object;
        if (this.sourceFormat.equals(that.sourceFormat) && this.targetFormat.equals(that.targetFormat)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.sourceFormat);
        stringBuilder.append(".converterTo(");
        stringBuilder.append(this.targetFormat);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
