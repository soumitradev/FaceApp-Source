package com.google.common.io;

import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.regex.Pattern;

class CharSource$CharSequenceCharSource extends CharSource {
    private static final Splitter LINE_SPLITTER = Splitter.on(Pattern.compile("\r\n|\n|\r"));
    private final CharSequence seq;

    /* renamed from: com.google.common.io.CharSource$CharSequenceCharSource$1 */
    class C05731 implements Iterable<String> {

        /* renamed from: com.google.common.io.CharSource$CharSequenceCharSource$1$1 */
        class C13121 extends AbstractIterator<String> {
            Iterator<String> lines = CharSource$CharSequenceCharSource.LINE_SPLITTER.split(CharSource$CharSequenceCharSource.this.seq).iterator();

            C13121() {
            }

            protected String computeNext() {
                if (this.lines.hasNext()) {
                    String next = (String) this.lines.next();
                    if (this.lines.hasNext() || !next.isEmpty()) {
                        return next;
                    }
                }
                return (String) endOfData();
            }
        }

        C05731() {
        }

        public Iterator<String> iterator() {
            return new C13121();
        }
    }

    protected CharSource$CharSequenceCharSource(CharSequence seq) {
        this.seq = (CharSequence) Preconditions.checkNotNull(seq);
    }

    public Reader openStream() {
        return new CharSequenceReader(this.seq);
    }

    public String read() {
        return this.seq.toString();
    }

    public boolean isEmpty() {
        return this.seq.length() == 0;
    }

    public long length() {
        return (long) this.seq.length();
    }

    public Optional<Long> lengthIfKnown() {
        return Optional.of(Long.valueOf((long) this.seq.length()));
    }

    private Iterable<String> lines() {
        return new C05731();
    }

    public String readFirstLine() {
        Iterator<String> lines = lines().iterator();
        return lines.hasNext() ? (String) lines.next() : null;
    }

    public ImmutableList<String> readLines() {
        return ImmutableList.copyOf(lines());
    }

    public <T> T readLines(LineProcessor<T> processor) throws IOException {
        for (String line : lines()) {
            if (!processor.processLine(line)) {
                break;
            }
        }
        return processor.getResult();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CharSource.wrap(");
        stringBuilder.append(Ascii.truncate(this.seq, 30, "..."));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
