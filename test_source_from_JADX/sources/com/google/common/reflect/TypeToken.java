package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList$Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.TypeResolver.TypeVariableKey;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

@Beta
public abstract class TypeToken<T> extends TypeCapture<T> implements Serializable {
    private final Type runtimeType;
    private transient TypeResolver typeResolver;

    private static abstract class TypeCollector<K> {
        static final TypeCollector<TypeToken<?>> FOR_GENERIC_TYPE = new C00681();
        static final TypeCollector<Class<?>> FOR_RAW_TYPE = new C00692();

        /* renamed from: com.google.common.reflect.TypeToken$TypeCollector$1 */
        static class C00681 extends TypeCollector<TypeToken<?>> {
            C00681() {
                super();
            }

            Class<?> getRawType(TypeToken<?> type) {
                return type.getRawType();
            }

            Iterable<? extends TypeToken<?>> getInterfaces(TypeToken<?> type) {
                return type.getGenericInterfaces();
            }

            @Nullable
            TypeToken<?> getSuperclass(TypeToken<?> type) {
                return type.getGenericSuperclass();
            }
        }

        /* renamed from: com.google.common.reflect.TypeToken$TypeCollector$2 */
        static class C00692 extends TypeCollector<Class<?>> {
            C00692() {
                super();
            }

            Class<?> getRawType(Class<?> type) {
                return type;
            }

            Iterable<? extends Class<?>> getInterfaces(Class<?> type) {
                return Arrays.asList(type.getInterfaces());
            }

            @Nullable
            Class<?> getSuperclass(Class<?> type) {
                return type.getSuperclass();
            }
        }

        abstract Iterable<? extends K> getInterfaces(K k);

        abstract Class<?> getRawType(K k);

        @Nullable
        abstract K getSuperclass(K k);

        private TypeCollector() {
        }

        final TypeCollector<K> classesOnly() {
            return new TypeToken$TypeCollector$3(this, this);
        }

        final ImmutableList<K> collectTypes(K type) {
            return collectTypes(ImmutableList.of(type));
        }

        ImmutableList<K> collectTypes(Iterable<? extends K> types) {
            Map<K, Integer> map = Maps.newHashMap();
            for (K type : types) {
                collectTypes(type, map);
            }
            return sortKeysByValue(map, Ordering.natural().reverse());
        }

        private int collectTypes(K type, Map<? super K, Integer> map) {
            Integer existing = (Integer) map.get(this);
            if (existing != null) {
                return existing.intValue();
            }
            int aboveMe = getRawType(type).isInterface();
            for (K interfaceType : getInterfaces(type)) {
                aboveMe = Math.max(aboveMe, collectTypes(interfaceType, map));
            }
            K superclass = getSuperclass(type);
            if (superclass != null) {
                aboveMe = Math.max(aboveMe, collectTypes(superclass, map));
            }
            map.put(type, Integer.valueOf(aboveMe + 1));
            return aboveMe + 1;
        }

        private static <K, V> ImmutableList<K> sortKeysByValue(Map<K, V> map, Comparator<? super V> valueComparator) {
            return new TypeToken$TypeCollector$4(valueComparator, map).immutableSortedCopy(map.keySet());
        }
    }

    protected TypeToken() {
        this.runtimeType = capture();
        Preconditions.checkState(!(this.runtimeType instanceof TypeVariable), "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", new Object[]{this.runtimeType});
    }

    protected TypeToken(Class<?> declaringClass) {
        Type captured = super.capture();
        if (captured instanceof Class) {
            this.runtimeType = captured;
        } else {
            this.runtimeType = of((Class) declaringClass).resolveType(captured).runtimeType;
        }
    }

    private TypeToken(Type type) {
        this.runtimeType = (Type) Preconditions.checkNotNull(type);
    }

    public static <T> TypeToken<T> of(Class<T> type) {
        return new TypeToken$SimpleTypeToken(type);
    }

    public static TypeToken<?> of(Type type) {
        return new TypeToken$SimpleTypeToken(type);
    }

    public final Class<? super T> getRawType() {
        return (Class) getRawTypes().iterator().next();
    }

    public final Type getType() {
        return this.runtimeType;
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParam, TypeToken<X> typeArg) {
        return new TypeToken$SimpleTypeToken(new TypeResolver().where(ImmutableMap.of(new TypeVariableKey(typeParam.typeVariable), typeArg.runtimeType)).resolveType(this.runtimeType));
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParam, Class<X> typeArg) {
        return where((TypeParameter) typeParam, of((Class) typeArg));
    }

    public final TypeToken<?> resolveType(Type type) {
        Preconditions.checkNotNull(type);
        TypeResolver resolver = this.typeResolver;
        if (resolver == null) {
            TypeResolver accordingTo = TypeResolver.accordingTo(this.runtimeType);
            this.typeResolver = accordingTo;
            resolver = accordingTo;
        }
        return of(resolver.resolveType(type));
    }

    private Type[] resolveInPlace(Type[] types) {
        for (int i = 0; i < types.length; i++) {
            types[i] = resolveType(types[i]).getType();
        }
        return types;
    }

    private TypeToken<?> resolveSupertype(Type type) {
        TypeToken<?> supertype = resolveType(type);
        supertype.typeResolver = this.typeResolver;
        return supertype;
    }

    @Nullable
    final TypeToken<? super T> getGenericSuperclass() {
        if (this.runtimeType instanceof TypeVariable) {
            return boundAsSuperclass(((TypeVariable) this.runtimeType).getBounds()[0]);
        }
        if (this.runtimeType instanceof WildcardType) {
            return boundAsSuperclass(((WildcardType) this.runtimeType).getUpperBounds()[0]);
        }
        Type superclass = getRawType().getGenericSuperclass();
        if (superclass == null) {
            return null;
        }
        return resolveSupertype(superclass);
    }

    @Nullable
    private TypeToken<? super T> boundAsSuperclass(Type bound) {
        TypeToken<?> token = of(bound);
        if (token.getRawType().isInterface()) {
            return null;
        }
        return token;
    }

    final ImmutableList<TypeToken<? super T>> getGenericInterfaces() {
        if (this.runtimeType instanceof TypeVariable) {
            return boundsAsInterfaces(((TypeVariable) this.runtimeType).getBounds());
        }
        if (this.runtimeType instanceof WildcardType) {
            return boundsAsInterfaces(((WildcardType) this.runtimeType).getUpperBounds());
        }
        ImmutableList$Builder<TypeToken<? super T>> builder = ImmutableList.builder();
        for (Type interfaceType : getRawType().getGenericInterfaces()) {
            builder.add(resolveSupertype(interfaceType));
        }
        return builder.build();
    }

    private ImmutableList<TypeToken<? super T>> boundsAsInterfaces(Type[] bounds) {
        ImmutableList$Builder<TypeToken<? super T>> builder = ImmutableList.builder();
        for (Type bound : bounds) {
            TypeToken<? super T> boundType = of(bound);
            if (boundType.getRawType().isInterface()) {
                builder.add(boundType);
            }
        }
        return builder.build();
    }

    public final TypeToken$TypeSet getTypes() {
        return new TypeToken$TypeSet(this);
    }

    public final TypeToken<? super T> getSupertype(Class<? super T> superclass) {
        Preconditions.checkArgument(someRawTypeIsSubclassOf(superclass), "%s is not a super class of %s", new Object[]{superclass, this});
        if (this.runtimeType instanceof TypeVariable) {
            return getSupertypeFromUpperBounds(superclass, ((TypeVariable) this.runtimeType).getBounds());
        }
        if (this.runtimeType instanceof WildcardType) {
            return getSupertypeFromUpperBounds(superclass, ((WildcardType) this.runtimeType).getUpperBounds());
        }
        if (superclass.isArray()) {
            return getArraySupertype(superclass);
        }
        return resolveSupertype(toGenericType(superclass).runtimeType);
    }

    public final TypeToken<? extends T> getSubtype(Class<?> subclass) {
        Preconditions.checkArgument(!(this.runtimeType instanceof TypeVariable), "Cannot get subtype of type variable <%s>", new Object[]{this});
        if (this.runtimeType instanceof WildcardType) {
            return getSubtypeFromLowerBounds(subclass, ((WildcardType) this.runtimeType).getLowerBounds());
        }
        if (isArray()) {
            return getArraySubtype(subclass);
        }
        Preconditions.checkArgument(getRawType().isAssignableFrom(subclass), "%s isn't a subclass of %s", new Object[]{subclass, this});
        return of(replaceTypeVariablesWithWildcard(resolveTypeArgsForSubclass(subclass), (Class) subclass));
    }

    private static final Type replaceTypeVariablesWithWildcard(Type type, Class<?> declaringClass) {
        Preconditions.checkNotNull(declaringClass);
        AtomicReference<Type> result = new AtomicReference();
        result.set(type);
        new TypeToken$1(declaringClass, result).visit(new Type[]{type});
        return (Type) result.get();
    }

    private static final Type[] replaceTypeVariablesWithWildcard(Type[] types, Class<?> declaringClass) {
        Type[] result = new Type[types.length];
        for (int i = 0; i < types.length; i++) {
            result[i] = replaceTypeVariablesWithWildcard(types[i], (Class) declaringClass);
        }
        return result;
    }

    @Deprecated
    public final boolean isAssignableFrom(TypeToken<?> type) {
        return isSupertypeOf((TypeToken) type);
    }

    @Deprecated
    public final boolean isAssignableFrom(Type type) {
        return isSupertypeOf(type);
    }

    public final boolean isSupertypeOf(TypeToken<?> type) {
        return type.isSubtypeOf(getType());
    }

    public final boolean isSupertypeOf(Type type) {
        return of(type).isSubtypeOf(getType());
    }

    public final boolean isSubtypeOf(TypeToken<?> type) {
        return isSubtypeOf(type.getType());
    }

    public final boolean isSubtypeOf(Type supertype) {
        Preconditions.checkNotNull(supertype);
        if (supertype instanceof WildcardType) {
            return any(((WildcardType) supertype).getLowerBounds()).isSupertypeOf(this.runtimeType);
        }
        if (this.runtimeType instanceof WildcardType) {
            return any(((WildcardType) this.runtimeType).getUpperBounds()).isSubtypeOf(supertype);
        }
        boolean z = false;
        if (this.runtimeType instanceof TypeVariable) {
            if (!this.runtimeType.equals(supertype)) {
                if (!any(((TypeVariable) this.runtimeType).getBounds()).isSubtypeOf(supertype)) {
                    return z;
                }
            }
            z = true;
            return z;
        } else if (this.runtimeType instanceof GenericArrayType) {
            return of(supertype).isSuperTypeOfArray((GenericArrayType) this.runtimeType);
        } else {
            if (supertype instanceof Class) {
                return someRawTypeIsSubclassOf((Class) supertype);
            }
            if (supertype instanceof ParameterizedType) {
                return isSubtypeOfParameterizedType((ParameterizedType) supertype);
            }
            if (supertype instanceof GenericArrayType) {
                return isSubTypeOfArrayType((GenericArrayType) supertype);
            }
            return false;
        }
    }

    public final boolean isArray() {
        return getComponentType() != null;
    }

    public final boolean isPrimitive() {
        return (this.runtimeType instanceof Class) && ((Class) this.runtimeType).isPrimitive();
    }

    public final TypeToken<T> wrap() {
        if (isPrimitive()) {
            return of(Primitives.wrap(this.runtimeType));
        }
        return this;
    }

    private boolean isWrapper() {
        return Primitives.allWrapperTypes().contains(this.runtimeType);
    }

    public final TypeToken<T> unwrap() {
        if (isWrapper()) {
            return of(Primitives.unwrap(this.runtimeType));
        }
        return this;
    }

    @Nullable
    public final TypeToken<?> getComponentType() {
        Type componentType = Types.getComponentType(this.runtimeType);
        if (componentType == null) {
            return null;
        }
        return of(componentType);
    }

    public final Invokable<T, Object> method(Method method) {
        Preconditions.checkArgument(someRawTypeIsSubclassOf(method.getDeclaringClass()), "%s not declared by %s", new Object[]{method, this});
        return new TypeToken$2(this, method);
    }

    public final Invokable<T, T> constructor(Constructor<?> constructor) {
        Preconditions.checkArgument(constructor.getDeclaringClass() == getRawType(), "%s not declared by %s", new Object[]{constructor, getRawType()});
        return new TypeToken$3(this, constructor);
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof TypeToken)) {
            return false;
        }
        return this.runtimeType.equals(((TypeToken) o).runtimeType);
    }

    public int hashCode() {
        return this.runtimeType.hashCode();
    }

    public String toString() {
        return Types.toString(this.runtimeType);
    }

    protected Object writeReplace() {
        return of(new TypeResolver().resolveType(this.runtimeType));
    }

    final TypeToken<T> rejectTypeVariables() {
        new TypeToken$4(this).visit(new Type[]{this.runtimeType});
        return this;
    }

    private boolean someRawTypeIsSubclassOf(Class<?> superclass) {
        Iterator i$ = getRawTypes().iterator();
        while (i$.hasNext()) {
            if (superclass.isAssignableFrom((Class) i$.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean isSubtypeOfParameterizedType(ParameterizedType supertype) {
        Class<?> matchedClass = of((Type) supertype).getRawType();
        if (!someRawTypeIsSubclassOf(matchedClass)) {
            return false;
        }
        Type[] typeParams = matchedClass.getTypeParameters();
        Type[] toTypeArgs = supertype.getActualTypeArguments();
        for (int i = 0; i < typeParams.length; i++) {
            if (!resolveType(typeParams[i]).is(toTypeArgs[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isSubTypeOfArrayType(GenericArrayType supertype) {
        if (this.runtimeType instanceof Class) {
            Class<?> fromClass = this.runtimeType;
            if (fromClass.isArray()) {
                return of(fromClass.getComponentType()).isSubtypeOf(supertype.getGenericComponentType());
            }
            return false;
        } else if (this.runtimeType instanceof GenericArrayType) {
            return of(this.runtimeType.getGenericComponentType()).isSubtypeOf(supertype.getGenericComponentType());
        } else {
            return false;
        }
    }

    private boolean isSuperTypeOfArray(GenericArrayType subtype) {
        if (this.runtimeType instanceof Class) {
            Class<?> thisClass = this.runtimeType;
            if (thisClass.isArray()) {
                return of(subtype.getGenericComponentType()).isSubtypeOf(thisClass.getComponentType());
            }
            return thisClass.isAssignableFrom(Object[].class);
        } else if (this.runtimeType instanceof GenericArrayType) {
            return of(subtype.getGenericComponentType()).isSubtypeOf(((GenericArrayType) this.runtimeType).getGenericComponentType());
        } else {
            return false;
        }
    }

    private boolean is(Type formalType) {
        boolean z = true;
        if (this.runtimeType.equals(formalType)) {
            return true;
        }
        if (!(formalType instanceof WildcardType)) {
            return false;
        }
        if (!every(((WildcardType) formalType).getUpperBounds()).isSupertypeOf(this.runtimeType) || !every(((WildcardType) formalType).getLowerBounds()).isSubtypeOf(this.runtimeType)) {
            z = false;
        }
        return z;
    }

    private static TypeToken$Bounds every(Type[] bounds) {
        return new TypeToken$Bounds(bounds, false);
    }

    private static TypeToken$Bounds any(Type[] bounds) {
        return new TypeToken$Bounds(bounds, true);
    }

    private ImmutableSet<Class<? super T>> getRawTypes() {
        Builder<Class<?>> builder = ImmutableSet.builder();
        new TypeToken$5(this, builder).visit(new Type[]{this.runtimeType});
        return builder.build();
    }

    @VisibleForTesting
    static <T> TypeToken<? extends T> toGenericType(Class<T> cls) {
        if (cls.isArray()) {
            return of(Types.newArrayType(toGenericType(cls.getComponentType()).runtimeType));
        }
        TypeVariable<Class<T>>[] typeParams = cls.getTypeParameters();
        Type ownerType = cls.isMemberClass() ? toGenericType(cls.getEnclosingClass()).runtimeType : null;
        if (typeParams.length <= 0) {
            if (ownerType == cls.getEnclosingClass()) {
                return of((Class) cls);
            }
        }
        return of(Types.newParameterizedTypeWithOwner(ownerType, cls, typeParams));
    }

    private TypeToken<? super T> getSupertypeFromUpperBounds(Class<? super T> supertype, Type[] upperBounds) {
        for (Type upperBound : upperBounds) {
            TypeToken<? super T> bound = of(upperBound);
            if (bound.isSubtypeOf((Type) supertype)) {
                return bound.getSupertype(supertype);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(supertype);
        stringBuilder.append(" isn't a super type of ");
        stringBuilder.append(this);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private TypeToken<? extends T> getSubtypeFromLowerBounds(Class<?> subclass, Type[] lowerBounds) {
        Type[] arr$ = lowerBounds;
        if (0 < arr$.length) {
            return of(arr$[0]).getSubtype(subclass);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(subclass);
        stringBuilder.append(" isn't a subclass of ");
        stringBuilder.append(this);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private TypeToken<? super T> getArraySupertype(Class<? super T> supertype) {
        return of(newArrayClassOrGenericArrayType(((TypeToken) Preconditions.checkNotNull(getComponentType(), "%s isn't a super type of %s", new Object[]{supertype, this})).getSupertype(supertype.getComponentType()).runtimeType));
    }

    private TypeToken<? extends T> getArraySubtype(Class<?> subclass) {
        return of(newArrayClassOrGenericArrayType(getComponentType().getSubtype(subclass.getComponentType()).runtimeType));
    }

    private Type resolveTypeArgsForSubclass(Class<?> subclass) {
        if (this.runtimeType instanceof Class) {
            return subclass;
        }
        TypeToken<?> genericSubtype = toGenericType(subclass);
        return new TypeResolver().where(genericSubtype.getSupertype(getRawType()).runtimeType, this.runtimeType).resolveType(genericSubtype.runtimeType);
    }

    private static Type newArrayClassOrGenericArrayType(Type componentType) {
        return Types$JavaVersion.JAVA7.newArrayType(componentType);
    }
}
