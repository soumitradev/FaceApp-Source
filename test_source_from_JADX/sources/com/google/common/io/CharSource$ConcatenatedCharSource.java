package com.google.common.io;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;

final class CharSource$ConcatenatedCharSource extends CharSource {
    private final Iterable<? extends CharSource> sources;

    CharSource$ConcatenatedCharSource(Iterable<? extends CharSource> sources) {
        this.sources = (Iterable) Preconditions.checkNotNull(sources);
    }

    public Reader openStream() throws IOException {
        return new MultiReader(this.sources.iterator());
    }

    public boolean isEmpty() throws IOException {
        for (CharSource source : this.sources) {
            if (!source.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Optional<Long> lengthIfKnown() {
        long result = 0;
        for (CharSource source : this.sources) {
            Optional<Long> lengthIfKnown = source.lengthIfKnown();
            if (!lengthIfKnown.isPresent()) {
                return Optional.absent();
            }
            result += ((Long) lengthIfKnown.get()).longValue();
        }
        return Optional.of(Long.valueOf(result));
    }

    public long length() throws IOException {
        long result = 0;
        for (CharSource source : this.sources) {
            result += source.length();
        }
        return result;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CharSource.concat(");
        stringBuilder.append(this.sources);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
