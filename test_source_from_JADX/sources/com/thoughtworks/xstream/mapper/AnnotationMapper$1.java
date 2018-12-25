package com.thoughtworks.xstream.mapper;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

class AnnotationMapper$1 extends LinkedHashSet<Type> {
    final /* synthetic */ AnnotationMapper this$0;
    final /* synthetic */ Set val$processedTypes;
    final /* synthetic */ Set val$types;

    AnnotationMapper$1(AnnotationMapper annotationMapper, Set set, Set set2) {
        this.this$0 = annotationMapper;
        this.val$types = set;
        this.val$processedTypes = set2;
    }

    public boolean add(Type o) {
        if (o instanceof Class) {
            return this.val$types.add((Class) o);
        }
        boolean add;
        if (o != null) {
            if (!this.val$processedTypes.contains(o)) {
                add = super.add(o);
                return add;
            }
        }
        add = false;
        return add;
    }
}
