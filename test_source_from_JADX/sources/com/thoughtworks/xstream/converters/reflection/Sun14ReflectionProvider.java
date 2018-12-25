package com.thoughtworks.xstream.converters.reflection;

public class Sun14ReflectionProvider extends SunUnsafeReflectionProvider {
    public Sun14ReflectionProvider(FieldDictionary dic) {
        super(dic);
    }

    private Object readResolve() {
        init();
        return this;
    }
}
