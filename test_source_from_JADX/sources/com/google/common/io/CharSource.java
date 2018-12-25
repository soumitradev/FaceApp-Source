package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

public abstract class CharSource {
    public abstract Reader openStream() throws IOException;

    protected CharSource() {
    }

    public BufferedReader openBufferedStream() throws IOException {
        Reader reader = openStream();
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    @Beta
    public Optional<Long> lengthIfKnown() {
        return Optional.absent();
    }

    @Beta
    public long length() throws IOException {
        Optional<Long> lengthIfKnown = lengthIfKnown();
        if (lengthIfKnown.isPresent()) {
            return ((Long) lengthIfKnown.get()).longValue();
        }
        Closer closer = Closer.create();
        try {
            long countBySkipping = countBySkipping((Reader) closer.register(openStream()));
            closer.close();
            return countBySkipping;
        } catch (Throwable th) {
            closer.close();
        }
    }

    private long countBySkipping(Reader reader) throws IOException {
        long count = 0;
        while (true) {
            long skip = reader.skip(Long.MAX_VALUE);
            long read = skip;
            if (skip == 0) {
                return count;
            }
            count += read;
        }
    }

    public long copyTo(Appendable appendable) throws IOException {
        Preconditions.checkNotNull(appendable);
        Closer closer = Closer.create();
        try {
            long copy = CharStreams.copy((Reader) closer.register(openStream()), appendable);
            closer.close();
            return copy;
        } catch (Throwable th) {
            closer.close();
        }
    }

    public long copyTo(CharSink sink) throws IOException {
        Preconditions.checkNotNull(sink);
        Closer closer = Closer.create();
        try {
            long copy = CharStreams.copy((Reader) closer.register(openStream()), (Writer) closer.register(sink.openStream()));
            closer.close();
            return copy;
        } catch (Throwable th) {
            closer.close();
        }
    }

    public String read() throws IOException {
        Closer closer = Closer.create();
        try {
            String charStreams = CharStreams.toString((Reader) closer.register(openStream()));
            closer.close();
            return charStreams;
        } catch (Throwable th) {
            closer.close();
        }
    }

    @Nullable
    public String readFirstLine() throws IOException {
        Closer closer = Closer.create();
        try {
            String readLine = ((BufferedReader) closer.register(openBufferedStream())).readLine();
            closer.close();
            return readLine;
        } catch (Throwable th) {
            closer.close();
        }
    }

    public ImmutableList<String> readLines() throws IOException {
        Closer closer = Closer.create();
        try {
            BufferedReader reader = (BufferedReader) closer.register(openBufferedStream());
            Collection result = Lists.newArrayList();
            while (true) {
                String readLine = reader.readLine();
                String line = readLine;
                if (readLine != null) {
                    result.add(line);
                } else {
                    ImmutableList<String> copyOf = ImmutableList.copyOf(result);
                    closer.close();
                    return copyOf;
                }
            }
        } catch (Throwable th) {
            closer.close();
        }
    }

    @Beta
    public <T> T readLines(LineProcessor<T> processor) throws IOException {
        Preconditions.checkNotNull(processor);
        Closer closer = Closer.create();
        try {
            T readLines = CharStreams.readLines((Reader) closer.register(openStream()), processor);
            closer.close();
            return readLines;
        } catch (Throwable th) {
            closer.close();
        }
    }

    public boolean isEmpty() throws IOException {
        Optional<Long> lengthIfKnown = lengthIfKnown();
        boolean z = true;
        if (lengthIfKnown.isPresent() && ((Long) lengthIfKnown.get()).longValue() == 0) {
            return true;
        }
        Closer closer = Closer.create();
        try {
            if (((Reader) closer.register(openStream())).read() != -1) {
                z = false;
            }
            closer.close();
            return z;
        } catch (Throwable th) {
            closer.close();
        }
    }

    public static CharSource concat(Iterable<? extends CharSource> sources) {
        return new CharSource$ConcatenatedCharSource(sources);
    }

    public static CharSource concat(Iterator<? extends CharSource> sources) {
        return concat(ImmutableList.copyOf((Iterator) sources));
    }

    public static CharSource concat(CharSource... sources) {
        return concat(ImmutableList.copyOf((Object[]) sources));
    }

    public static CharSource wrap(CharSequence charSequence) {
        return new CharSource$CharSequenceCharSource(charSequence);
    }

    public static CharSource empty() {
        return CharSource$EmptyCharSource.access$000();
    }
}
