package com.google.common.reflect;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken.TypeCollector;
import java.util.Set;

final class TypeToken$InterfaceSet extends TypeToken$TypeSet {
    private static final long serialVersionUID = 0;
    private final transient TypeToken$TypeSet allTypes;
    private transient ImmutableSet<TypeToken<? super T>> interfaces;
    final /* synthetic */ TypeToken this$0;

    /* renamed from: com.google.common.reflect.TypeToken$InterfaceSet$1 */
    class C10041 implements Predicate<Class<?>> {
        C10041() {
        }

        public boolean apply(Class<?> type) {
            return type.isInterface();
        }
    }

    TypeToken$InterfaceSet(TypeToken typeToken, TypeToken$TypeSet allTypes) {
        this.this$0 = typeToken;
        super(typeToken);
        this.allTypes = allTypes;
    }

    protected Set<TypeToken<? super T>> delegate() {
        ImmutableSet<TypeToken<? super T>> result = this.interfaces;
        if (result != null) {
            return result;
        }
        Set toSet = FluentIterable.from(this.allTypes).filter(TypeToken$TypeFilter.INTERFACE_ONLY).toSet();
        this.interfaces = toSet;
        return toSet;
    }

    public TypeToken$TypeSet interfaces() {
        return this;
    }

    public Set<Class<? super T>> rawTypes() {
        return FluentIterable.from(TypeCollector.FOR_RAW_TYPE.collectTypes(TypeToken.access$400(this.this$0))).filter(new C10041()).toSet();
    }

    public TypeToken$TypeSet classes() {
        throw new UnsupportedOperationException("interfaces().classes() not supported.");
    }

    private Object readResolve() {
        return this.this$0.getTypes().interfaces();
    }
}
