package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.CheckReturnValue;

@GwtCompatible(emulated = true)
public final class Splitter {
    private final int limit;
    private final boolean omitEmptyStrings;
    private final Splitter$Strategy strategy;
    private final CharMatcher trimmer;

    @Beta
    public static final class MapSplitter {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter entrySplitter;
        private final Splitter outerSplitter;

        private MapSplitter(Splitter outerSplitter, Splitter entrySplitter) {
            this.outerSplitter = outerSplitter;
            this.entrySplitter = (Splitter) Preconditions.checkNotNull(entrySplitter);
        }

        @CheckReturnValue
        public Map<String, String> split(CharSequence sequence) {
            Map<String, String> map = new LinkedHashMap();
            for (String entry : this.outerSplitter.split(sequence)) {
                Iterator<String> entryFields = this.entrySplitter.splittingIterator(entry);
                Preconditions.checkArgument(entryFields.hasNext(), INVALID_ENTRY_MESSAGE, new Object[]{entry});
                String key = (String) entryFields.next();
                Preconditions.checkArgument(map.containsKey(key) ^ true, "Duplicate key [%s] found.", new Object[]{key});
                Preconditions.checkArgument(entryFields.hasNext(), INVALID_ENTRY_MESSAGE, new Object[]{entry});
                map.put(key, (String) entryFields.next());
                Preconditions.checkArgument(entryFields.hasNext() ^ true, INVALID_ENTRY_MESSAGE, new Object[]{entry});
            }
            return Collections.unmodifiableMap(map);
        }
    }

    private Splitter(Splitter$Strategy strategy) {
        this(strategy, false, CharMatcher.NONE, Integer.MAX_VALUE);
    }

    private Splitter(Splitter$Strategy strategy, boolean omitEmptyStrings, CharMatcher trimmer, int limit) {
        this.strategy = strategy;
        this.omitEmptyStrings = omitEmptyStrings;
        this.trimmer = trimmer;
        this.limit = limit;
    }

    @CheckReturnValue
    public static Splitter on(char separator) {
        return on(CharMatcher.is(separator));
    }

    @CheckReturnValue
    public static Splitter on(CharMatcher separatorMatcher) {
        Preconditions.checkNotNull(separatorMatcher);
        return new Splitter(new Splitter$1(separatorMatcher));
    }

    @CheckReturnValue
    public static Splitter on(String separator) {
        Preconditions.checkArgument(separator.length() != 0, "The separator may not be the empty string.");
        if (separator.length() == 1) {
            return on(separator.charAt(0));
        }
        return new Splitter(new Splitter$2(separator));
    }

    @CheckReturnValue
    @GwtIncompatible("java.util.regex")
    public static Splitter on(Pattern separatorPattern) {
        Preconditions.checkNotNull(separatorPattern);
        Preconditions.checkArgument(separatorPattern.matcher("").matches() ^ true, "The pattern may not match the empty string: %s", new Object[]{separatorPattern});
        return new Splitter(new Splitter$3(separatorPattern));
    }

    @CheckReturnValue
    @GwtIncompatible("java.util.regex")
    public static Splitter onPattern(String separatorPattern) {
        return on(Pattern.compile(separatorPattern));
    }

    @CheckReturnValue
    public static Splitter fixedLength(int length) {
        Preconditions.checkArgument(length > 0, "The length may not be less than 1");
        return new Splitter(new Splitter$4(length));
    }

    @CheckReturnValue
    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }

    @CheckReturnValue
    public Splitter limit(int limit) {
        Preconditions.checkArgument(limit > 0, "must be greater than zero: %s", new Object[]{Integer.valueOf(limit)});
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, limit);
    }

    @CheckReturnValue
    public Splitter trimResults() {
        return trimResults(CharMatcher.WHITESPACE);
    }

    @CheckReturnValue
    public Splitter trimResults(CharMatcher trimmer) {
        Preconditions.checkNotNull(trimmer);
        return new Splitter(this.strategy, this.omitEmptyStrings, trimmer, this.limit);
    }

    @CheckReturnValue
    public Iterable<String> split(CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return new Splitter$5(this, sequence);
    }

    private Iterator<String> splittingIterator(CharSequence sequence) {
        return this.strategy.iterator(this, sequence);
    }

    @CheckReturnValue
    @Beta
    public List<String> splitToList(CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        Iterator<String> iterator = splittingIterator(sequence);
        List<String> result = new ArrayList();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return Collections.unmodifiableList(result);
    }

    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(String separator) {
        return withKeyValueSeparator(on(separator));
    }

    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(char separator) {
        return withKeyValueSeparator(on(separator));
    }

    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(Splitter keyValueSplitter) {
        return new MapSplitter(keyValueSplitter);
    }
}
