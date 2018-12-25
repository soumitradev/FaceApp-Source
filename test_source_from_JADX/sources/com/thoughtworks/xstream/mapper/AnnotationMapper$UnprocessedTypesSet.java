package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.annotations.XStreamInclude;
import java.util.LinkedHashSet;

final class AnnotationMapper$UnprocessedTypesSet extends LinkedHashSet<Class<?>> {
    final /* synthetic */ AnnotationMapper this$0;

    private AnnotationMapper$UnprocessedTypesSet(AnnotationMapper annotationMapper) {
        this.this$0 = annotationMapper;
    }

    public boolean add(Class<?> type) {
        int i$ = 0;
        if (type == null) {
            return false;
        }
        while (type.isArray()) {
            type = type.getComponentType();
        }
        String name = type.getName();
        if (!name.startsWith("java.")) {
            if (!name.startsWith("javax.")) {
                boolean ret = AnnotationMapper.access$100(this.this$0).contains(type) ? false : super.add(type);
                if (ret) {
                    XStreamInclude inc = (XStreamInclude) type.getAnnotation(XStreamInclude.class);
                    if (inc != null) {
                        Class<?>[] incTypes = inc.value();
                        if (incTypes != null) {
                            Class<?>[] arr$ = incTypes;
                            int len$ = arr$.length;
                            while (i$ < len$) {
                                add(arr$[i$]);
                                i$++;
                            }
                        }
                    }
                }
                return ret;
            }
        }
        return false;
    }
}
