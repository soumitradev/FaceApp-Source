package com.squareup.okhttp.internal;

import com.squareup.okhttp.internal.io.FileSystem;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import name.antonsmirnov.firmata.FormatHelper;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class DiskLruCache implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final Sink NULL_SINK = new C20334();
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    static final String VERSION_1 = "1";
    private final int appVersion;
    private final Runnable cleanupRunnable = new C16471();
    private boolean closed;
    private final File directory;
    private final Executor executor;
    private final FileSystem fileSystem;
    private boolean hasJournalErrors;
    private boolean initialized;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    private BufferedSink journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75f, true);
    private long maxSize;
    private long nextSequenceNumber = 0;
    private int redundantOpCount;
    private long size = 0;
    private final int valueCount;

    /* renamed from: com.squareup.okhttp.internal.DiskLruCache$1 */
    class C16471 implements Runnable {
        C16471() {
        }

        public void run() {
            synchronized (DiskLruCache.this) {
                if (((DiskLruCache.this.initialized ^ 1) | DiskLruCache.this.closed) != 0) {
                    return;
                }
                try {
                    DiskLruCache.this.trimToSize();
                    if (DiskLruCache.this.journalRebuildRequired()) {
                        DiskLruCache.this.rebuildJournal();
                        DiskLruCache.this.redundantOpCount = 0;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /* renamed from: com.squareup.okhttp.internal.DiskLruCache$3 */
    class C16483 implements Iterator<Snapshot> {
        final Iterator<Entry> delegate = new ArrayList(DiskLruCache.this.lruEntries.values()).iterator();
        Snapshot nextSnapshot;
        Snapshot removeSnapshot;

        C16483() {
        }

        public boolean hasNext() {
            if (this.nextSnapshot != null) {
                return true;
            }
            synchronized (DiskLruCache.this) {
                if (DiskLruCache.this.closed) {
                    return false;
                }
                while (this.delegate.hasNext()) {
                    Snapshot snapshot = ((Entry) this.delegate.next()).snapshot();
                    if (snapshot != null) {
                        this.nextSnapshot = snapshot;
                        return true;
                    }
                }
                return false;
            }
        }

        public Snapshot next() {
            if (hasNext()) {
                this.removeSnapshot = this.nextSnapshot;
                this.nextSnapshot = null;
                return this.removeSnapshot;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            if (this.removeSnapshot == null) {
                throw new IllegalStateException("remove() before next()");
            }
            try {
                DiskLruCache.this.remove(this.removeSnapshot.key);
            } catch (IOException e) {
            } catch (Throwable th) {
                this.removeSnapshot = null;
            }
            this.removeSnapshot = null;
        }
    }

    public final class Editor {
        private boolean committed;
        private final Entry entry;
        private boolean hasErrors;
        private final boolean[] written;

        private Editor(Entry entry) {
            this.entry = entry;
            this.written = entry.readable ? null : new boolean[DiskLruCache.this.valueCount];
        }

        public Source newSource(int index) throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                } else if (this.entry.readable) {
                    try {
                        Source source = DiskLruCache.this.fileSystem.source(this.entry.cleanFiles[index]);
                        return source;
                    } catch (FileNotFoundException e) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }

        public Sink newSink(int index) throws IOException {
            Sink c21511;
            synchronized (DiskLruCache.this) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                }
                if (!this.entry.readable) {
                    this.written[index] = true;
                }
                try {
                    c21511 = new FaultHidingSink(DiskLruCache.this.fileSystem.sink(this.entry.dirtyFiles[index])) {
                        protected void onException(IOException e) {
                            synchronized (DiskLruCache.this) {
                                Editor.this.hasErrors = true;
                            }
                        }
                    };
                } catch (FileNotFoundException e) {
                    return DiskLruCache.NULL_SINK;
                }
            }
            return c21511;
        }

        public void commit() throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.hasErrors) {
                    DiskLruCache.this.completeEdit(this, false);
                    DiskLruCache.this.removeEntry(this.entry);
                } else {
                    DiskLruCache.this.completeEdit(this, true);
                }
                this.committed = true;
            }
        }

        public void abort() throws IOException {
            synchronized (DiskLruCache.this) {
                DiskLruCache.this.completeEdit(this, false);
            }
        }

        public void abortUnlessCommitted() {
            synchronized (DiskLruCache.this) {
                if (!this.committed) {
                    try {
                        DiskLruCache.this.completeEdit(this, false);
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    private final class Entry {
        private final File[] cleanFiles;
        private Editor currentEditor;
        private final File[] dirtyFiles;
        private final String key;
        private final long[] lengths;
        private boolean readable;
        private long sequenceNumber;

        private Entry(String key) {
            this.key = key;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.cleanFiles = new File[DiskLruCache.this.valueCount];
            this.dirtyFiles = new File[DiskLruCache.this.valueCount];
            StringBuilder fileBuilder = new StringBuilder(key).append('.');
            int truncateTo = fileBuilder.length();
            for (int i = 0; i < DiskLruCache.this.valueCount; i++) {
                fileBuilder.append(i);
                this.cleanFiles[i] = new File(DiskLruCache.this.directory, fileBuilder.toString());
                fileBuilder.append(".tmp");
                this.dirtyFiles[i] = new File(DiskLruCache.this.directory, fileBuilder.toString());
                fileBuilder.setLength(truncateTo);
            }
        }

        private void setLengths(String[] strings) throws IOException {
            if (strings.length != DiskLruCache.this.valueCount) {
                throw invalidLengths(strings);
            }
            int i = 0;
            while (i < strings.length) {
                try {
                    this.lengths[i] = Long.parseLong(strings[i]);
                    i++;
                } catch (NumberFormatException e) {
                    throw invalidLengths(strings);
                }
            }
        }

        void writeLengths(BufferedSink writer) throws IOException {
            for (long length : this.lengths) {
                writer.writeByte(32).writeDecimalLong(length);
            }
        }

        private IOException invalidLengths(String[] strings) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected journal line: ");
            stringBuilder.append(Arrays.toString(strings));
            throw new IOException(stringBuilder.toString());
        }

        Snapshot snapshot() {
            if (Thread.holdsLock(DiskLruCache.this)) {
                Source[] sources = new Source[DiskLruCache.this.valueCount];
                long[] lengths = (long[]) this.lengths.clone();
                int i = 0;
                int i2 = 0;
                while (i2 < DiskLruCache.this.valueCount) {
                    try {
                        sources[i2] = DiskLruCache.this.fileSystem.source(this.cleanFiles[i2]);
                        i2++;
                    } catch (FileNotFoundException e) {
                        while (true) {
                            int i3 = i;
                            if (i3 < DiskLruCache.this.valueCount && sources[i3] != null) {
                                Util.closeQuietly(sources[i3]);
                                i = i3 + 1;
                            }
                        }
                        return null;
                    }
                }
                return new Snapshot(this.key, this.sequenceNumber, sources, lengths);
            }
            throw new AssertionError();
        }
    }

    public final class Snapshot implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final Source[] sources;

        private Snapshot(String key, long sequenceNumber, Source[] sources, long[] lengths) {
            this.key = key;
            this.sequenceNumber = sequenceNumber;
            this.sources = sources;
            this.lengths = lengths;
        }

        public String key() {
            return this.key;
        }

        public Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public Source getSource(int index) {
            return this.sources[index];
        }

        public long getLength(int index) {
            return this.lengths[index];
        }

        public void close() {
            for (Closeable in : this.sources) {
                Util.closeQuietly(in);
            }
        }
    }

    /* renamed from: com.squareup.okhttp.internal.DiskLruCache$4 */
    static class C20334 implements Sink {
        C20334() {
        }

        public void write(Buffer source, long byteCount) throws IOException {
            source.skip(byteCount);
        }

        public void flush() throws IOException {
        }

        public Timeout timeout() {
            return Timeout.NONE;
        }

        public void close() throws IOException {
        }
    }

    DiskLruCache(FileSystem fileSystem, File directory, int appVersion, int valueCount, long maxSize, Executor executor) {
        this.fileSystem = fileSystem;
        this.directory = directory;
        this.appVersion = appVersion;
        this.journalFile = new File(directory, JOURNAL_FILE);
        this.journalFileTmp = new File(directory, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(directory, JOURNAL_FILE_BACKUP);
        this.valueCount = valueCount;
        this.maxSize = maxSize;
        this.executor = executor;
    }

    void initialize() throws IOException {
        if (!this.initialized) {
            if (this.fileSystem.exists(this.journalFileBackup)) {
                if (this.fileSystem.exists(this.journalFile)) {
                    this.fileSystem.delete(this.journalFileBackup);
                } else {
                    this.fileSystem.rename(this.journalFileBackup, this.journalFile);
                }
            }
            if (this.fileSystem.exists(this.journalFile)) {
                try {
                    readJournal();
                    processJournal();
                    this.initialized = true;
                    return;
                } catch (IOException journalIsCorrupt) {
                    Platform platform = Platform.get();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DiskLruCache ");
                    stringBuilder.append(this.directory);
                    stringBuilder.append(" is corrupt: ");
                    stringBuilder.append(journalIsCorrupt.getMessage());
                    stringBuilder.append(", removing");
                    platform.logW(stringBuilder.toString());
                    delete();
                    this.closed = false;
                }
            }
            rebuildJournal();
            this.initialized = true;
        }
    }

    public static DiskLruCache create(FileSystem fileSystem, File directory, int appVersion, int valueCount, long maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (valueCount <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        } else {
            return new DiskLruCache(fileSystem, directory, appVersion, valueCount, maxSize, new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true)));
        }
    }

    private void readJournal() throws IOException {
        int lineCount;
        Closeable source = Okio.buffer(this.fileSystem.source(this.journalFile));
        try {
            String magic = source.readUtf8LineStrict();
            String version = source.readUtf8LineStrict();
            String appVersionString = source.readUtf8LineStrict();
            String valueCountString = source.readUtf8LineStrict();
            String blank = source.readUtf8LineStrict();
            if (MAGIC.equals(magic) && "1".equals(version) && Integer.toString(this.appVersion).equals(appVersionString) && Integer.toString(this.valueCount).equals(valueCountString)) {
                if ("".equals(blank)) {
                    lineCount = 0;
                    while (true) {
                        readJournalLine(source.readUtf8LineStrict());
                        lineCount++;
                    }
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected journal header: [");
            stringBuilder.append(magic);
            stringBuilder.append(", ");
            stringBuilder.append(version);
            stringBuilder.append(", ");
            stringBuilder.append(valueCountString);
            stringBuilder.append(", ");
            stringBuilder.append(blank);
            stringBuilder.append("]");
            throw new IOException(stringBuilder.toString());
        } catch (EOFException e) {
            this.redundantOpCount = lineCount - this.lruEntries.size();
            if (source.exhausted()) {
                this.journalWriter = newJournalWriter();
            } else {
                rebuildJournal();
            }
            Util.closeQuietly(source);
        } catch (Throwable th) {
            Util.closeQuietly(source);
        }
    }

    private BufferedSink newJournalWriter() throws FileNotFoundException {
        return Okio.buffer(new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile)) {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class cls = DiskLruCache.class;
            }

            protected void onException(IOException e) {
                DiskLruCache.this.hasJournalErrors = true;
            }
        });
    }

    private void readJournalLine(String line) throws IOException {
        int firstSpace = line.indexOf(32);
        if (firstSpace == -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected journal line: ");
            stringBuilder.append(line);
            throw new IOException(stringBuilder.toString());
        }
        String key;
        int keyBegin = firstSpace + 1;
        int secondSpace = line.indexOf(32, keyBegin);
        if (secondSpace == -1) {
            key = line.substring(keyBegin);
            if (firstSpace == REMOVE.length() && line.startsWith(REMOVE)) {
                this.lruEntries.remove(key);
                return;
            }
        }
        key = line.substring(keyBegin, secondSpace);
        Entry entry = (Entry) this.lruEntries.get(key);
        if (entry == null) {
            entry = new Entry(key);
            this.lruEntries.put(key, entry);
        }
        if (secondSpace != -1 && firstSpace == CLEAN.length() && line.startsWith(CLEAN)) {
            String[] parts = line.substring(secondSpace + 1).split(FormatHelper.SPACE);
            entry.readable = true;
            entry.currentEditor = null;
            entry.setLengths(parts);
        } else if (secondSpace == -1 && firstSpace == DIRTY.length() && line.startsWith(DIRTY)) {
            entry.currentEditor = new Editor(entry);
        } else if (!(secondSpace == -1 && firstSpace == READ.length() && line.startsWith(READ))) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("unexpected journal line: ");
            stringBuilder2.append(line);
            throw new IOException(stringBuilder2.toString());
        }
    }

    private void processJournal() throws IOException {
        this.fileSystem.delete(this.journalFileTmp);
        Iterator<Entry> i = this.lruEntries.values().iterator();
        while (i.hasNext()) {
            Entry entry = (Entry) i.next();
            int t = 0;
            int t2;
            if (entry.currentEditor == null) {
                while (true) {
                    t2 = t;
                    if (t2 >= this.valueCount) {
                        break;
                    }
                    this.size += entry.lengths[t2];
                    t = t2 + 1;
                }
            } else {
                entry.currentEditor = null;
                while (true) {
                    t2 = t;
                    if (t2 >= this.valueCount) {
                        break;
                    }
                    this.fileSystem.delete(entry.cleanFiles[t2]);
                    this.fileSystem.delete(entry.dirtyFiles[t2]);
                    t = t2 + 1;
                }
                i.remove();
            }
        }
    }

    private synchronized void rebuildJournal() throws IOException {
        if (this.journalWriter != null) {
            this.journalWriter.close();
        }
        BufferedSink writer = Okio.buffer(this.fileSystem.sink(this.journalFileTmp));
        try {
            writer.writeUtf8(MAGIC).writeByte(10);
            writer.writeUtf8("1").writeByte(10);
            writer.writeDecimalLong((long) this.appVersion).writeByte(10);
            writer.writeDecimalLong((long) this.valueCount).writeByte(10);
            writer.writeByte(10);
            for (Entry entry : this.lruEntries.values()) {
                if (entry.currentEditor != null) {
                    writer.writeUtf8(DIRTY).writeByte(32);
                    writer.writeUtf8(entry.key);
                    writer.writeByte(10);
                } else {
                    writer.writeUtf8(CLEAN).writeByte(32);
                    writer.writeUtf8(entry.key);
                    entry.writeLengths(writer);
                    writer.writeByte(10);
                }
            }
            if (this.fileSystem.exists(this.journalFile)) {
                this.fileSystem.rename(this.journalFile, this.journalFileBackup);
            }
            this.fileSystem.rename(this.journalFileTmp, this.journalFile);
            this.fileSystem.delete(this.journalFileBackup);
            this.journalWriter = newJournalWriter();
            this.hasJournalErrors = false;
        } finally {
            writer.close();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.squareup.okhttp.internal.DiskLruCache.Snapshot get(java.lang.String r5) throws java.io.IOException {
        /*
        r4 = this;
        monitor-enter(r4);
        r4.initialize();	 Catch:{ all -> 0x0052 }
        r4.checkNotClosed();	 Catch:{ all -> 0x0052 }
        r4.validateKey(r5);	 Catch:{ all -> 0x0052 }
        r0 = r4.lruEntries;	 Catch:{ all -> 0x0052 }
        r0 = r0.get(r5);	 Catch:{ all -> 0x0052 }
        r0 = (com.squareup.okhttp.internal.DiskLruCache.Entry) r0;	 Catch:{ all -> 0x0052 }
        r1 = 0;
        if (r0 == 0) goto L_0x0050;
    L_0x0015:
        r2 = r0.readable;	 Catch:{ all -> 0x0052 }
        if (r2 != 0) goto L_0x001c;
    L_0x001b:
        goto L_0x0050;
    L_0x001c:
        r2 = r0.snapshot();	 Catch:{ all -> 0x0052 }
        if (r2 != 0) goto L_0x0024;
    L_0x0022:
        monitor-exit(r4);
        return r1;
    L_0x0024:
        r1 = r4.redundantOpCount;	 Catch:{ all -> 0x0052 }
        r1 = r1 + 1;
        r4.redundantOpCount = r1;	 Catch:{ all -> 0x0052 }
        r1 = r4.journalWriter;	 Catch:{ all -> 0x0052 }
        r3 = "READ";
        r1 = r1.writeUtf8(r3);	 Catch:{ all -> 0x0052 }
        r3 = 32;
        r1 = r1.writeByte(r3);	 Catch:{ all -> 0x0052 }
        r1 = r1.writeUtf8(r5);	 Catch:{ all -> 0x0052 }
        r3 = 10;
        r1.writeByte(r3);	 Catch:{ all -> 0x0052 }
        r1 = r4.journalRebuildRequired();	 Catch:{ all -> 0x0052 }
        if (r1 == 0) goto L_0x004e;
    L_0x0047:
        r1 = r4.executor;	 Catch:{ all -> 0x0052 }
        r3 = r4.cleanupRunnable;	 Catch:{ all -> 0x0052 }
        r1.execute(r3);	 Catch:{ all -> 0x0052 }
    L_0x004e:
        monitor-exit(r4);
        return r2;
    L_0x0050:
        monitor-exit(r4);
        return r1;
    L_0x0052:
        r5 = move-exception;
        monitor-exit(r4);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.get(java.lang.String):com.squareup.okhttp.internal.DiskLruCache$Snapshot");
    }

    public Editor edit(String key) throws IOException {
        return edit(key, -1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.squareup.okhttp.internal.DiskLruCache.Editor edit(java.lang.String r6, long r7) throws java.io.IOException {
        /*
        r5 = this;
        monitor-enter(r5);
        r5.initialize();	 Catch:{ all -> 0x0069 }
        r5.checkNotClosed();	 Catch:{ all -> 0x0069 }
        r5.validateKey(r6);	 Catch:{ all -> 0x0069 }
        r0 = r5.lruEntries;	 Catch:{ all -> 0x0069 }
        r0 = r0.get(r6);	 Catch:{ all -> 0x0069 }
        r0 = (com.squareup.okhttp.internal.DiskLruCache.Entry) r0;	 Catch:{ all -> 0x0069 }
        r1 = -1;
        r3 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1));
        r1 = 0;
        if (r3 == 0) goto L_0x0025;
    L_0x0019:
        if (r0 == 0) goto L_0x0023;
    L_0x001b:
        r2 = r0.sequenceNumber;	 Catch:{ all -> 0x0069 }
        r4 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1));
        if (r4 == 0) goto L_0x0025;
    L_0x0023:
        monitor-exit(r5);
        return r1;
    L_0x0025:
        if (r0 == 0) goto L_0x002f;
    L_0x0027:
        r2 = r0.currentEditor;	 Catch:{ all -> 0x0069 }
        if (r2 == 0) goto L_0x002f;
    L_0x002d:
        monitor-exit(r5);
        return r1;
    L_0x002f:
        r2 = r5.journalWriter;	 Catch:{ all -> 0x0069 }
        r3 = "DIRTY";
        r2 = r2.writeUtf8(r3);	 Catch:{ all -> 0x0069 }
        r3 = 32;
        r2 = r2.writeByte(r3);	 Catch:{ all -> 0x0069 }
        r2 = r2.writeUtf8(r6);	 Catch:{ all -> 0x0069 }
        r3 = 10;
        r2.writeByte(r3);	 Catch:{ all -> 0x0069 }
        r2 = r5.journalWriter;	 Catch:{ all -> 0x0069 }
        r2.flush();	 Catch:{ all -> 0x0069 }
        r2 = r5.hasJournalErrors;	 Catch:{ all -> 0x0069 }
        if (r2 == 0) goto L_0x0051;
    L_0x004f:
        monitor-exit(r5);
        return r1;
    L_0x0051:
        if (r0 != 0) goto L_0x005e;
    L_0x0053:
        r2 = new com.squareup.okhttp.internal.DiskLruCache$Entry;	 Catch:{ all -> 0x0069 }
        r2.<init>(r6);	 Catch:{ all -> 0x0069 }
        r0 = r2;
        r2 = r5.lruEntries;	 Catch:{ all -> 0x0069 }
        r2.put(r6, r0);	 Catch:{ all -> 0x0069 }
    L_0x005e:
        r2 = new com.squareup.okhttp.internal.DiskLruCache$Editor;	 Catch:{ all -> 0x0069 }
        r2.<init>(r0);	 Catch:{ all -> 0x0069 }
        r1 = r2;
        r0.currentEditor = r1;	 Catch:{ all -> 0x0069 }
        monitor-exit(r5);
        return r1;
    L_0x0069:
        r6 = move-exception;
        monitor-exit(r5);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.edit(java.lang.String, long):com.squareup.okhttp.internal.DiskLruCache$Editor");
    }

    public File getDirectory() {
        return this.directory;
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
        if (this.initialized) {
            this.executor.execute(this.cleanupRunnable);
        }
    }

    public synchronized long size() throws IOException {
        initialize();
        return this.size;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void completeEdit(com.squareup.okhttp.internal.DiskLruCache.Editor r13, boolean r14) throws java.io.IOException {
        /*
        r12 = this;
        monitor-enter(r12);
        r0 = r13.entry;	 Catch:{ all -> 0x011e }
        r1 = r0.currentEditor;	 Catch:{ all -> 0x011e }
        if (r1 == r13) goto L_0x0011;
    L_0x000b:
        r1 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x011e }
        r1.<init>();	 Catch:{ all -> 0x011e }
        throw r1;	 Catch:{ all -> 0x011e }
    L_0x0011:
        r1 = 0;
        if (r14 == 0) goto L_0x0057;
    L_0x0014:
        r2 = r0.readable;	 Catch:{ all -> 0x011e }
        if (r2 != 0) goto L_0x0057;
    L_0x001a:
        r2 = 0;
    L_0x001b:
        r3 = r12.valueCount;	 Catch:{ all -> 0x011e }
        if (r2 >= r3) goto L_0x0057;
    L_0x001f:
        r3 = r13.written;	 Catch:{ all -> 0x011e }
        r3 = r3[r2];	 Catch:{ all -> 0x011e }
        if (r3 != 0) goto L_0x0041;
    L_0x0027:
        r13.abort();	 Catch:{ all -> 0x011e }
        r1 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x011e }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x011e }
        r3.<init>();	 Catch:{ all -> 0x011e }
        r4 = "Newly created entry didn't create value for index ";
        r3.append(r4);	 Catch:{ all -> 0x011e }
        r3.append(r2);	 Catch:{ all -> 0x011e }
        r3 = r3.toString();	 Catch:{ all -> 0x011e }
        r1.<init>(r3);	 Catch:{ all -> 0x011e }
        throw r1;	 Catch:{ all -> 0x011e }
    L_0x0041:
        r3 = r12.fileSystem;	 Catch:{ all -> 0x011e }
        r4 = r0.dirtyFiles;	 Catch:{ all -> 0x011e }
        r4 = r4[r2];	 Catch:{ all -> 0x011e }
        r3 = r3.exists(r4);	 Catch:{ all -> 0x011e }
        if (r3 != 0) goto L_0x0054;
    L_0x004f:
        r13.abort();	 Catch:{ all -> 0x011e }
        monitor-exit(r12);
        return;
    L_0x0054:
        r2 = r2 + 1;
        goto L_0x001b;
    L_0x0058:
        r2 = r12.valueCount;	 Catch:{ all -> 0x011e }
        if (r1 >= r2) goto L_0x009c;
    L_0x005c:
        r2 = r0.dirtyFiles;	 Catch:{ all -> 0x011e }
        r2 = r2[r1];	 Catch:{ all -> 0x011e }
        if (r14 == 0) goto L_0x0094;
    L_0x0064:
        r3 = r12.fileSystem;	 Catch:{ all -> 0x011e }
        r3 = r3.exists(r2);	 Catch:{ all -> 0x011e }
        if (r3 == 0) goto L_0x0099;
    L_0x006c:
        r3 = r0.cleanFiles;	 Catch:{ all -> 0x011e }
        r3 = r3[r1];	 Catch:{ all -> 0x011e }
        r4 = r12.fileSystem;	 Catch:{ all -> 0x011e }
        r4.rename(r2, r3);	 Catch:{ all -> 0x011e }
        r4 = r0.lengths;	 Catch:{ all -> 0x011e }
        r5 = r4[r1];	 Catch:{ all -> 0x011e }
        r4 = r5;
        r6 = r12.fileSystem;	 Catch:{ all -> 0x011e }
        r6 = r6.size(r3);	 Catch:{ all -> 0x011e }
        r8 = r0.lengths;	 Catch:{ all -> 0x011e }
        r8[r1] = r6;	 Catch:{ all -> 0x011e }
        r8 = r12.size;	 Catch:{ all -> 0x011e }
        r10 = 0;
        r10 = r8 - r4;
        r8 = r10 + r6;
        r12.size = r8;	 Catch:{ all -> 0x011e }
        goto L_0x0099;
    L_0x0094:
        r3 = r12.fileSystem;	 Catch:{ all -> 0x011e }
        r3.delete(r2);	 Catch:{ all -> 0x011e }
    L_0x0099:
        r1 = r1 + 1;
        goto L_0x0058;
    L_0x009c:
        r1 = r12.redundantOpCount;	 Catch:{ all -> 0x011e }
        r2 = 1;
        r1 = r1 + r2;
        r12.redundantOpCount = r1;	 Catch:{ all -> 0x011e }
        r1 = 0;
        r0.currentEditor = r1;	 Catch:{ all -> 0x011e }
        r1 = r0.readable;	 Catch:{ all -> 0x011e }
        r1 = r1 | r14;
        r3 = 10;
        r4 = 32;
        if (r1 == 0) goto L_0x00e0;
    L_0x00b1:
        r0.readable = r2;	 Catch:{ all -> 0x011e }
        r1 = r12.journalWriter;	 Catch:{ all -> 0x011e }
        r2 = "CLEAN";
        r1 = r1.writeUtf8(r2);	 Catch:{ all -> 0x011e }
        r1.writeByte(r4);	 Catch:{ all -> 0x011e }
        r1 = r12.journalWriter;	 Catch:{ all -> 0x011e }
        r2 = r0.key;	 Catch:{ all -> 0x011e }
        r1.writeUtf8(r2);	 Catch:{ all -> 0x011e }
        r1 = r12.journalWriter;	 Catch:{ all -> 0x011e }
        r0.writeLengths(r1);	 Catch:{ all -> 0x011e }
        r1 = r12.journalWriter;	 Catch:{ all -> 0x011e }
        r1.writeByte(r3);	 Catch:{ all -> 0x011e }
        if (r14 == 0) goto L_0x0102;
    L_0x00d4:
        r1 = r12.nextSequenceNumber;	 Catch:{ all -> 0x011e }
        r3 = 1;
        r5 = r1 + r3;
        r12.nextSequenceNumber = r5;	 Catch:{ all -> 0x011e }
        r0.sequenceNumber = r1;	 Catch:{ all -> 0x011e }
        goto L_0x0102;
    L_0x00e0:
        r1 = r12.lruEntries;	 Catch:{ all -> 0x011e }
        r2 = r0.key;	 Catch:{ all -> 0x011e }
        r1.remove(r2);	 Catch:{ all -> 0x011e }
        r1 = r12.journalWriter;	 Catch:{ all -> 0x011e }
        r2 = "REMOVE";
        r1 = r1.writeUtf8(r2);	 Catch:{ all -> 0x011e }
        r1.writeByte(r4);	 Catch:{ all -> 0x011e }
        r1 = r12.journalWriter;	 Catch:{ all -> 0x011e }
        r2 = r0.key;	 Catch:{ all -> 0x011e }
        r1.writeUtf8(r2);	 Catch:{ all -> 0x011e }
        r1 = r12.journalWriter;	 Catch:{ all -> 0x011e }
        r1.writeByte(r3);	 Catch:{ all -> 0x011e }
    L_0x0102:
        r1 = r12.journalWriter;	 Catch:{ all -> 0x011e }
        r1.flush();	 Catch:{ all -> 0x011e }
        r1 = r12.size;	 Catch:{ all -> 0x011e }
        r3 = r12.maxSize;	 Catch:{ all -> 0x011e }
        r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
        if (r5 > 0) goto L_0x0115;
    L_0x010f:
        r1 = r12.journalRebuildRequired();	 Catch:{ all -> 0x011e }
        if (r1 == 0) goto L_0x011c;
    L_0x0115:
        r1 = r12.executor;	 Catch:{ all -> 0x011e }
        r2 = r12.cleanupRunnable;	 Catch:{ all -> 0x011e }
        r1.execute(r2);	 Catch:{ all -> 0x011e }
    L_0x011c:
        monitor-exit(r12);
        return;
    L_0x011e:
        r13 = move-exception;
        monitor-exit(r12);
        throw r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.completeEdit(com.squareup.okhttp.internal.DiskLruCache$Editor, boolean):void");
    }

    private boolean journalRebuildRequired() {
        return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
    }

    public synchronized boolean remove(String key) throws IOException {
        initialize();
        checkNotClosed();
        validateKey(key);
        Entry entry = (Entry) this.lruEntries.get(key);
        if (entry == null) {
            return false;
        }
        return removeEntry(entry);
    }

    private boolean removeEntry(Entry entry) throws IOException {
        if (entry.currentEditor != null) {
            entry.currentEditor.hasErrors = true;
        }
        for (int i = 0; i < this.valueCount; i++) {
            this.fileSystem.delete(entry.cleanFiles[i]);
            this.size -= entry.lengths[i];
            entry.lengths[i] = 0;
        }
        this.redundantOpCount++;
        this.journalWriter.writeUtf8(REMOVE).writeByte(32).writeUtf8(entry.key).writeByte(10);
        this.lruEntries.remove(entry.key);
        if (journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return true;
    }

    public synchronized boolean isClosed() {
        return this.closed;
    }

    private synchronized void checkNotClosed() {
        if (isClosed()) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() throws IOException {
        if (this.initialized) {
            checkNotClosed();
            trimToSize();
            this.journalWriter.flush();
        }
    }

    public synchronized void close() throws IOException {
        if (this.initialized) {
            if (!this.closed) {
                for (Entry entry : (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
                    if (entry.currentEditor != null) {
                        entry.currentEditor.abort();
                    }
                }
                trimToSize();
                this.journalWriter.close();
                this.journalWriter = null;
                this.closed = true;
                return;
            }
        }
        this.closed = true;
    }

    private void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            removeEntry((Entry) this.lruEntries.values().iterator().next());
        }
    }

    public void delete() throws IOException {
        close();
        this.fileSystem.deleteContents(this.directory);
    }

    public synchronized void evictAll() throws IOException {
        initialize();
        for (Entry entry : (Entry[]) this.lruEntries.values().toArray(new Entry[this.lruEntries.size()])) {
            removeEntry(entry);
        }
    }

    private void validateKey(String key) {
        if (!LEGAL_KEY_PATTERN.matcher(key).matches()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("keys must match regex [a-z0-9_-]{1,120}: \"");
            stringBuilder.append(key);
            stringBuilder.append("\"");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public synchronized Iterator<Snapshot> snapshots() throws IOException {
        initialize();
        return new C16483();
    }
}
