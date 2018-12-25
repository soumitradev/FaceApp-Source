package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.j2objc.annotations.Weak;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;

final class SubscriberRegistry {
    private static final LoadingCache<Class<?>, ImmutableSet<Class<?>>> flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build(new C09892());
    private static final LoadingCache<Class<?>, ImmutableList<Method>> subscriberMethodsCache = CacheBuilder.newBuilder().weakKeys().build(new C09881());
    @Weak
    private final EventBus bus;
    private final ConcurrentMap<Class<?>, CopyOnWriteArraySet<Subscriber>> subscribers = Maps.newConcurrentMap();

    private static final class MethodIdentifier {
        private final String name;
        private final List<Class<?>> parameterTypes;

        MethodIdentifier(Method method) {
            this.name = method.getName();
            this.parameterTypes = Arrays.asList(method.getParameterTypes());
        }

        public int hashCode() {
            return Objects.hashCode(new Object[]{this.name, this.parameterTypes});
        }

        public boolean equals(@Nullable Object o) {
            boolean z = false;
            if (!(o instanceof MethodIdentifier)) {
                return false;
            }
            MethodIdentifier ident = (MethodIdentifier) o;
            if (this.name.equals(ident.name) && this.parameterTypes.equals(ident.parameterTypes)) {
                z = true;
            }
            return z;
        }
    }

    /* renamed from: com.google.common.eventbus.SubscriberRegistry$1 */
    static class C09881 extends CacheLoader<Class<?>, ImmutableList<Method>> {
        C09881() {
        }

        public ImmutableList<Method> load(Class<?> concreteClass) throws Exception {
            return SubscriberRegistry.getAnnotatedMethodsNotCached(concreteClass);
        }
    }

    /* renamed from: com.google.common.eventbus.SubscriberRegistry$2 */
    static class C09892 extends CacheLoader<Class<?>, ImmutableSet<Class<?>>> {
        C09892() {
        }

        public ImmutableSet<Class<?>> load(Class<?> concreteClass) {
            return ImmutableSet.copyOf(TypeToken.of(concreteClass).getTypes().rawTypes());
        }
    }

    SubscriberRegistry(EventBus bus) {
        this.bus = (EventBus) Preconditions.checkNotNull(bus);
    }

    void register(Object listener) {
        for (Entry<Class<?>, Collection<Subscriber>> entry : findAllSubscribers(listener).asMap().entrySet()) {
            Class<?> eventType = (Class) entry.getKey();
            Collection<Subscriber> eventMethodsInListener = (Collection) entry.getValue();
            CopyOnWriteArraySet<Subscriber> eventSubscribers = (CopyOnWriteArraySet) this.subscribers.get(eventType);
            if (eventSubscribers == null) {
                CopyOnWriteArraySet<Subscriber> newSet = new CopyOnWriteArraySet();
                eventSubscribers = (CopyOnWriteArraySet) MoreObjects.firstNonNull(this.subscribers.putIfAbsent(eventType, newSet), newSet);
            }
            eventSubscribers.addAll(eventMethodsInListener);
        }
    }

    void unregister(Object listener) {
        for (Entry<Class<?>, Collection<Subscriber>> entry : findAllSubscribers(listener).asMap().entrySet()) {
            Collection<Subscriber> listenerMethodsForType = (Collection) entry.getValue();
            CopyOnWriteArraySet<Subscriber> currentSubscribers = (CopyOnWriteArraySet) this.subscribers.get((Class) entry.getKey());
            if (currentSubscribers != null) {
                if (currentSubscribers.removeAll(listenerMethodsForType)) {
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("missing event subscriber for an annotated method. Is ");
            stringBuilder.append(listener);
            stringBuilder.append(" registered?");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @VisibleForTesting
    Set<Subscriber> getSubscribersForTesting(Class<?> eventType) {
        return (Set) MoreObjects.firstNonNull(this.subscribers.get(eventType), ImmutableSet.of());
    }

    Iterator<Subscriber> getSubscribers(Object event) {
        ImmutableSet<Class<?>> eventTypes = flattenHierarchy(event.getClass());
        List<Iterator<Subscriber>> subscriberIterators = Lists.newArrayListWithCapacity(eventTypes.size());
        Iterator i$ = eventTypes.iterator();
        while (i$.hasNext()) {
            CopyOnWriteArraySet<Subscriber> eventSubscribers = (CopyOnWriteArraySet) this.subscribers.get((Class) i$.next());
            if (eventSubscribers != null) {
                subscriberIterators.add(eventSubscribers.iterator());
            }
        }
        return Iterators.concat(subscriberIterators.iterator());
    }

    private Multimap<Class<?>, Subscriber> findAllSubscribers(Object listener) {
        Multimap<Class<?>, Subscriber> methodsInListener = HashMultimap.create();
        Iterator i$ = getAnnotatedMethods(listener.getClass()).iterator();
        while (i$.hasNext()) {
            Method method = (Method) i$.next();
            methodsInListener.put(method.getParameterTypes()[null], Subscriber.create(this.bus, listener, method));
        }
        return methodsInListener;
    }

    private static ImmutableList<Method> getAnnotatedMethods(Class<?> clazz) {
        return (ImmutableList) subscriberMethodsCache.getUnchecked(clazz);
    }

    private static ImmutableList<Method> getAnnotatedMethodsNotCached(Class<?> clazz) {
        Set<? extends Class<?>> supertypes = TypeToken.of(clazz).getTypes().rawTypes();
        Map<MethodIdentifier, Method> identifiers = Maps.newHashMap();
        for (Class<?> supertype : supertypes) {
            for (Method method : supertype.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Subscribe.class) && !method.isSynthetic()) {
                    Preconditions.checkArgument(method.getParameterTypes().length == 1, "Method %s has @Subscribe annotation but has %s parameters.Subscriber methods must have exactly 1 parameter.", method, Integer.valueOf(method.getParameterTypes().length));
                    MethodIdentifier ident = new MethodIdentifier(method);
                    if (!identifiers.containsKey(ident)) {
                        identifiers.put(ident, method);
                    }
                }
            }
        }
        return ImmutableList.copyOf(identifiers.values());
    }

    @VisibleForTesting
    static ImmutableSet<Class<?>> flattenHierarchy(Class<?> concreteClass) {
        try {
            return (ImmutableSet) flattenHierarchyCache.getUnchecked(concreteClass);
        } catch (UncheckedExecutionException e) {
            throw Throwables.propagate(e.getCause());
        }
    }
}
