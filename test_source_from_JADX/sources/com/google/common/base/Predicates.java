package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.Constants;

@GwtCompatible(emulated = true)
@CheckReturnValue
public final class Predicates {
    private static final Joiner COMMA_JOINER = Joiner.on((char) Constants.REMIX_URL_SEPARATOR);

    private Predicates() {
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> alwaysTrue() {
        return Predicates$ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> alwaysFalse() {
        return Predicates$ObjectPredicate.ALWAYS_FALSE.withNarrowedType();
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> isNull() {
        return Predicates$ObjectPredicate.IS_NULL.withNarrowedType();
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> notNull() {
        return Predicates$ObjectPredicate.NOT_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new Predicates$NotPredicate(predicate);
    }

    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> components) {
        return new Predicates$AndPredicate(defensiveCopy((Iterable) components), null);
    }

    public static <T> Predicate<T> and(Predicate<? super T>... components) {
        return new Predicates$AndPredicate(defensiveCopy((Object[]) components), null);
    }

    public static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T> second) {
        return new Predicates$AndPredicate(asList((Predicate) Preconditions.checkNotNull(first), (Predicate) Preconditions.checkNotNull(second)), null);
    }

    public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> components) {
        return new Predicates$OrPredicate(defensiveCopy((Iterable) components), null);
    }

    public static <T> Predicate<T> or(Predicate<? super T>... components) {
        return new Predicates$OrPredicate(defensiveCopy((Object[]) components), null);
    }

    public static <T> Predicate<T> or(Predicate<? super T> first, Predicate<? super T> second) {
        return new Predicates$OrPredicate(asList((Predicate) Preconditions.checkNotNull(first), (Predicate) Preconditions.checkNotNull(second)), null);
    }

    public static <T> Predicate<T> equalTo(@Nullable T target) {
        return target == null ? isNull() : new Predicates$IsEqualToPredicate(target, null);
    }

    @GwtIncompatible("Class.isInstance")
    public static Predicate<Object> instanceOf(Class<?> clazz) {
        return new Predicates$InstanceOfPredicate(clazz, null);
    }

    @GwtIncompatible("Class.isAssignableFrom")
    @Beta
    public static Predicate<Class<?>> assignableFrom(Class<?> clazz) {
        return new Predicates$AssignableFromPredicate(clazz, null);
    }

    public static <T> Predicate<T> in(Collection<? extends T> target) {
        return new Predicates$InPredicate(target, null);
    }

    public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
        return new Predicates$CompositionPredicate(predicate, function, null);
    }

    @GwtIncompatible("java.util.regex.Pattern")
    public static Predicate<CharSequence> containsPattern(String pattern) {
        return new Predicates$ContainsPatternFromStringPredicate(pattern);
    }

    @GwtIncompatible("java.util.regex.Pattern")
    public static Predicate<CharSequence> contains(Pattern pattern) {
        return new Predicates$ContainsPatternPredicate(pattern);
    }

    private static <T> List<Predicate<? super T>> asList(Predicate<? super T> first, Predicate<? super T> second) {
        return Arrays.asList(new Predicate[]{first, second});
    }

    private static <T> List<T> defensiveCopy(T... array) {
        return defensiveCopy(Arrays.asList(array));
    }

    static <T> List<T> defensiveCopy(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList();
        for (T element : iterable) {
            list.add(Preconditions.checkNotNull(element));
        }
        return list;
    }
}
