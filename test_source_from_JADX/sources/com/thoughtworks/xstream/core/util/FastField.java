package com.thoughtworks.xstream.core.util;

public final class FastField {
    private final String declaringClass;
    private final String name;

    public FastField(String definedIn, String name) {
        this.name = name;
        this.declaringClass = definedIn;
    }

    public FastField(Class definedIn, String name) {
        this(definedIn == null ? null : definedIn.getName(), name);
    }

    public String getName() {
        return this.name;
    }

    public String getDeclaringClass() {
        return this.declaringClass;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof FastField)) {
            return false;
        }
        FastField field = (FastField) obj;
        if ((this.declaringClass == null && field.declaringClass != null) || (this.declaringClass != null && field.declaringClass == null)) {
            return false;
        }
        if (!this.name.equals(field.getName()) || (this.declaringClass != null && !this.declaringClass.equals(field.getDeclaringClass()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.name.hashCode() ^ (this.declaringClass == null ? 0 : this.declaringClass.hashCode());
    }

    public String toString() {
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.declaringClass == null) {
            str = "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.declaringClass);
            stringBuilder2.append(".");
            str = stringBuilder2.toString();
        }
        stringBuilder.append(str);
        stringBuilder.append(this.name);
        return stringBuilder.toString();
    }
}
