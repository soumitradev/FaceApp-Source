package com.facebook.internal;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class FileLruCache {
    private static final String HEADER_CACHEKEY_KEY = "key";
    private static final String HEADER_CACHE_CONTENT_TAG_KEY = "tag";
    static final String TAG = FileLruCache.class.getSimpleName();
    private static final AtomicLong bufferIndex = new AtomicLong();
    private final File directory;
    private boolean isTrimInProgress;
    private boolean isTrimPending;
    private AtomicLong lastClearCacheTime = new AtomicLong(0);
    private final Limits limits;
    private final Object lock;
    private final String tag;

    /* renamed from: com.facebook.internal.FileLruCache$3 */
    class C04363 implements Runnable {
        C04363() {
        }

        public void run() {
            FileLruCache.this.trim();
        }
    }

    private static class BufferFile {
        private static final String FILE_NAME_PREFIX = "buffer";
        private static final FilenameFilter filterExcludeBufferFiles = new C04371();
        private static final FilenameFilter filterExcludeNonBufferFiles = new C04382();

        /* renamed from: com.facebook.internal.FileLruCache$BufferFile$1 */
        static class C04371 implements FilenameFilter {
            C04371() {
            }

            public boolean accept(File dir, String filename) {
                return filename.startsWith(BufferFile.FILE_NAME_PREFIX) ^ 1;
            }
        }

        /* renamed from: com.facebook.internal.FileLruCache$BufferFile$2 */
        static class C04382 implements FilenameFilter {
            C04382() {
            }

            public boolean accept(File dir, String filename) {
                return filename.startsWith(BufferFile.FILE_NAME_PREFIX);
            }
        }

        private BufferFile() {
        }

        static void deleteAll(File root) {
            File[] filesToDelete = root.listFiles(excludeNonBufferFiles());
            if (filesToDelete != null) {
                for (File file : filesToDelete) {
                    file.delete();
                }
            }
        }

        static FilenameFilter excludeBufferFiles() {
            return filterExcludeBufferFiles;
        }

        static FilenameFilter excludeNonBufferFiles() {
            return filterExcludeNonBufferFiles;
        }

        static File newFile(File root) {
            String name = new StringBuilder();
            name.append(FILE_NAME_PREFIX);
            name.append(Long.valueOf(FileLruCache.bufferIndex.incrementAndGet()).toString());
            return new File(root, name.toString());
        }
    }

    private static class CloseCallbackOutputStream extends OutputStream {
        final StreamCloseCallback callback;
        final OutputStream innerStream;

        CloseCallbackOutputStream(OutputStream innerStream, StreamCloseCallback callback) {
            this.innerStream = innerStream;
            this.callback = callback;
        }

        public void close() throws IOException {
            try {
                this.innerStream.close();
            } finally {
                this.callback.onClose();
            }
        }

        public void flush() throws IOException {
            this.innerStream.flush();
        }

        public void write(byte[] buffer, int offset, int count) throws IOException {
            this.innerStream.write(buffer, offset, count);
        }

        public void write(byte[] buffer) throws IOException {
            this.innerStream.write(buffer);
        }

        public void write(int oneByte) throws IOException {
            this.innerStream.write(oneByte);
        }
    }

    private static final class CopyingInputStream extends InputStream {
        final InputStream input;
        final OutputStream output;

        CopyingInputStream(InputStream input, OutputStream output) {
            this.input = input;
            this.output = output;
        }

        public int available() throws IOException {
            return this.input.available();
        }

        public void close() throws IOException {
            try {
                this.input.close();
            } finally {
                this.output.close();
            }
        }

        public void mark(int readlimit) {
            throw new UnsupportedOperationException();
        }

        public boolean markSupported() {
            return false;
        }

        public int read(byte[] buffer) throws IOException {
            int count = this.input.read(buffer);
            if (count > 0) {
                this.output.write(buffer, 0, count);
            }
            return count;
        }

        public int read() throws IOException {
            int b = this.input.read();
            if (b >= 0) {
                this.output.write(b);
            }
            return b;
        }

        public int read(byte[] buffer, int offset, int length) throws IOException {
            int count = this.input.read(buffer, offset, length);
            if (count > 0) {
                this.output.write(buffer, offset, count);
            }
            return count;
        }

        public synchronized void reset() {
            throw new UnsupportedOperationException();
        }

        public long skip(long byteCount) throws IOException {
            byte[] buffer = new byte[1024];
            long total = 0;
            while (total < byteCount) {
                int count = read(buffer, 0, (int) Math.min(byteCount - total, (long) buffer.length));
                if (count < 0) {
                    return total;
                }
                total += (long) count;
            }
            return total;
        }
    }

    public static final class Limits {
        private int byteCount = 1048576;
        private int fileCount = 1024;

        int getByteCount() {
            return this.byteCount;
        }

        int getFileCount() {
            return this.fileCount;
        }

        void setByteCount(int n) {
            if (n < 0) {
                throw new InvalidParameterException("Cache byte-count limit must be >= 0");
            }
            this.byteCount = n;
        }

        void setFileCount(int n) {
            if (n < 0) {
                throw new InvalidParameterException("Cache file count limit must be >= 0");
            }
            this.fileCount = n;
        }
    }

    private static final class ModifiedFile implements Comparable<ModifiedFile> {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        private final File file;
        private final long modified;

        ModifiedFile(File file) {
            this.file = file;
            this.modified = file.lastModified();
        }

        File getFile() {
            return this.file;
        }

        long getModified() {
            return this.modified;
        }

        public int compareTo(ModifiedFile another) {
            if (getModified() < another.getModified()) {
                return -1;
            }
            if (getModified() > another.getModified()) {
                return 1;
            }
            return getFile().compareTo(another.getFile());
        }

        public boolean equals(Object another) {
            return (another instanceof ModifiedFile) && compareTo((ModifiedFile) another) == 0;
        }

        public int hashCode() {
            return (((29 * 37) + this.file.hashCode()) * 37) + ((int) (this.modified % 2147483647L));
        }
    }

    private interface StreamCloseCallback {
        void onClose();
    }

    private static final class StreamHeader {
        private static final int HEADER_VERSION = 0;

        private StreamHeader() {
        }

        static void writeHeader(OutputStream stream, JSONObject header) throws IOException {
            byte[] headerBytes = header.toString().getBytes();
            stream.write(0);
            stream.write((headerBytes.length >> 16) & 255);
            stream.write((headerBytes.length >> 8) & 255);
            stream.write((headerBytes.length >> 0) & 255);
            stream.write(headerBytes);
        }

        static JSONObject readHeader(InputStream stream) throws IOException {
            if (stream.read() != 0) {
                return null;
            }
            int count = 0;
            int headerSize = 0;
            for (int i = 0; i < 3; i++) {
                int b = stream.read();
                if (b == -1) {
                    Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read returned -1 while reading header size");
                    return null;
                }
                headerSize = (headerSize << 8) + (b & 255);
            }
            byte[] headerBytes = new byte[headerSize];
            while (count < headerBytes.length) {
                b = stream.read(headerBytes, count, headerBytes.length - count);
                if (b < 1) {
                    LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
                    String str = FileLruCache.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("readHeader: stream.read stopped at ");
                    stringBuilder.append(Integer.valueOf(count));
                    stringBuilder.append(" when expected ");
                    stringBuilder.append(headerBytes.length);
                    Logger.log(loggingBehavior, str, stringBuilder.toString());
                    return null;
                }
                count += b;
            }
            try {
                JSONObject parsed = new JSONTokener(new String(headerBytes)).nextValue();
                if (parsed instanceof JSONObject) {
                    return parsed;
                }
                LoggingBehavior loggingBehavior2 = LoggingBehavior.CACHE;
                String str2 = FileLruCache.TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("readHeader: expected JSONObject, got ");
                stringBuilder2.append(parsed.getClass().getCanonicalName());
                Logger.log(loggingBehavior2, str2, stringBuilder2.toString());
                return null;
            } catch (JSONException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    private void trim() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 11, insn: 0x002b: ARRAY_LENGTH  (r12 int) = (r11 ?[]), block:B:61:?, method: com.facebook.internal.FileLruCache.trim():void
	at jadx.core.dex.visitors.ssa.SSATransform.renameVar(SSATransform.java:168)
	at jadx.core.dex.visitors.ssa.SSATransform.renameVar(SSATransform.java:197)
	at jadx.core.dex.visitors.ssa.SSATransform.renameVar(SSATransform.java:197)
	at jadx.core.dex.visitors.ssa.SSATransform.renameVar(SSATransform.java:197)
	at jadx.core.dex.visitors.ssa.SSATransform.renameVar(SSATransform.java:197)
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:132)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
        /*
        r19 = this;
        r1 = r19;
        r2 = r1.lock;
        monitor-enter(r2);
        r3 = 0;
        r1.isTrimPending = r3;	 Catch:{ all -> 0x0103 }
        r4 = 1;	 Catch:{ all -> 0x0103 }
        r1.isTrimInProgress = r4;	 Catch:{ all -> 0x0103 }
        monitor-exit(r2);	 Catch:{ all -> 0x0103 }
        r2 = com.facebook.LoggingBehavior.CACHE;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r4 = TAG;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r5 = "trim started";	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        com.facebook.internal.Logger.log(r2, r4, r5);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r2 = new java.util.PriorityQueue;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r2.<init>();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r4 = 0;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r6 = 0;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = r1.directory;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r9 = com.facebook.internal.FileLruCache.BufferFile.excludeBufferFiles();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = r8.listFiles(r9);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        if (r8 == 0) goto L_0x008c;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
    L_0x002a:
        r11 = r8;
        r12 = r11.length;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r13 = r6;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r5 = r4;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r4 = 0;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
    L_0x002f:
        if (r4 >= r12) goto L_0x0088;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
    L_0x0031:
        r7 = r11[r4];	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r15 = new com.facebook.internal.FileLruCache$ModifiedFile;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r15.<init>(r7);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r2.add(r15);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r3 = com.facebook.LoggingBehavior.CACHE;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r9 = TAG;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r10.<init>();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r16 = r8;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = "  trim considering time=";	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r10.append(r8);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r17 = r11;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r18 = r12;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r11 = r15.getModified();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = java.lang.Long.valueOf(r11);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r10.append(r8);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = " name=";	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r10.append(r8);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = r15.getFile();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = r8.getName();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r10.append(r8);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = r10.toString();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        com.facebook.internal.Logger.log(r3, r9, r8);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = r7.length();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r3 = 0;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r10 = r5 + r8;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r5 = 1;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r7 = r13 + r5;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r4 = r4 + 1;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r13 = r7;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r5 = r10;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = r16;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r11 = r17;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r12 = r18;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r3 = 0;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        goto L_0x002f;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
    L_0x0088:
        r16 = r8;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r4 = r5;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        goto L_0x008f;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
    L_0x008c:
        r16 = r8;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r13 = r6;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
    L_0x008f:
        r3 = r1.limits;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r3 = r3.getByteCount();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r6 = (long) r3;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        if (r3 > 0) goto L_0x00b8;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
    L_0x009a:
        r3 = r1.limits;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r3 = r3.getFileCount();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r6 = (long) r3;
        r3 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1));
        if (r3 <= 0) goto L_0x00a6;
    L_0x00a5:
        goto L_0x00b8;
    L_0x00a6:
        r2 = r1.lock;
        monitor-enter(r2);
        r3 = 0;
        r1.isTrimInProgress = r3;	 Catch:{ all -> 0x00b4 }
        r3 = r1.lock;	 Catch:{ all -> 0x00b4 }
        r3.notifyAll();	 Catch:{ all -> 0x00b4 }
        monitor-exit(r2);	 Catch:{ all -> 0x00b4 }
        return;	 Catch:{ all -> 0x00b4 }
    L_0x00b4:
        r0 = move-exception;	 Catch:{ all -> 0x00b4 }
        r3 = r0;	 Catch:{ all -> 0x00b4 }
        monitor-exit(r2);	 Catch:{ all -> 0x00b4 }
        throw r3;
    L_0x00b8:
        r3 = r2.remove();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r3 = (com.facebook.internal.FileLruCache.ModifiedFile) r3;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r3 = r3.getFile();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r6 = com.facebook.LoggingBehavior.CACHE;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r7 = TAG;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8.<init>();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r9 = "  trim removing ";	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8.append(r9);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r9 = r3.getName();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8.append(r9);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = r8.toString();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        com.facebook.internal.Logger.log(r6, r7, r8);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r6 = r3.length();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = 0;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r8 = r4 - r6;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r4 = 1;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r6 = r13 - r4;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r3.delete();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r13 = r6;
        r4 = r8;
        goto L_0x008f;
    L_0x00f0:
        r0 = move-exception;
        r2 = r0;
        r3 = r1.lock;
        monitor-enter(r3);
        r4 = 0;
        r1.isTrimInProgress = r4;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r4 = r1.lock;	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        r4.notifyAll();	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        monitor-exit(r3);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        throw r2;
    L_0x00ff:
        r0 = move-exception;
        r2 = r0;
        monitor-exit(r3);	 Catch:{ all -> 0x00f0, all -> 0x00ff }
        throw r2;
    L_0x0103:
        r0 = move-exception;
        r3 = r0;
        monitor-exit(r2);	 Catch:{ all -> 0x0103 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.FileLruCache.trim():void");
    }

    public FileLruCache(String tag, Limits limits) {
        this.tag = tag;
        this.limits = limits;
        this.directory = new File(FacebookSdk.getCacheDir(), tag);
        this.lock = new Object();
        if (this.directory.mkdirs() || this.directory.isDirectory()) {
            BufferFile.deleteAll(this.directory);
        }
    }

    long sizeInBytesForTest() {
        synchronized (this.lock) {
            while (true) {
                if (!this.isTrimPending) {
                    if (!this.isTrimInProgress) {
                        break;
                    }
                }
                try {
                    this.lock.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        File[] files = this.directory.listFiles();
        long total = 0;
        if (files != null) {
            File[] arr$ = files;
            int i$ = 0;
            while (i$ < arr$.length) {
                i$++;
                total += arr$[i$].length();
            }
        }
        return total;
    }

    public InputStream get(String key) throws IOException {
        return get(key, null);
    }

    public InputStream get(String key, String contentTag) throws IOException {
        File file = new File(this.directory, Utility.md5hash(key));
        FileInputStream input = null;
        try {
            BufferedInputStream buffered = new BufferedInputStream(new FileInputStream(file), 8192);
            try {
                JSONObject header = StreamHeader.readHeader(buffered);
                if (header == null) {
                    return null;
                }
                String foundKey = header.optString(HEADER_CACHEKEY_KEY);
                if (foundKey != null) {
                    if (foundKey.equals(key)) {
                        String headerContentTag = header.optString(HEADER_CACHE_CONTENT_TAG_KEY, null);
                        if ((contentTag != null || headerContentTag == null) && (contentTag == null || contentTag.equals(headerContentTag))) {
                            long accessTime = new Date().getTime();
                            LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
                            String str = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Setting lastModified to ");
                            stringBuilder.append(Long.valueOf(accessTime));
                            stringBuilder.append(" for ");
                            stringBuilder.append(file.getName());
                            Logger.log(loggingBehavior, str, stringBuilder.toString());
                            file.setLastModified(accessTime);
                            if (!true) {
                                buffered.close();
                            }
                            return buffered;
                        }
                        if (null == null) {
                            buffered.close();
                        }
                        return null;
                    }
                }
                if (null == null) {
                    buffered.close();
                }
                return null;
            } finally {
                if (null == null) {
                    buffered.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    public OutputStream openPutStream(String key) throws IOException {
        return openPutStream(key, null);
    }

    public OutputStream openPutStream(String key, String contentTag) throws IOException {
        JSONException e;
        JSONException e2;
        LoggingBehavior loggingBehavior;
        String str;
        StringBuilder stringBuilder;
        StreamCloseCallback streamCloseCallback;
        FileOutputStream file;
        Throwable th;
        String str2;
        File buffer = BufferFile.newFile(this.directory);
        buffer.delete();
        if (buffer.createNewFile()) {
            String str3;
            try {
                FileOutputStream file2 = new FileOutputStream(buffer);
                final long currentTimeMillis = System.currentTimeMillis();
                final File file3 = buffer;
                final String str4 = key;
                StreamCloseCallback renameToTargetCallback = new StreamCloseCallback() {
                    public void onClose() {
                        if (currentTimeMillis < FileLruCache.this.lastClearCacheTime.get()) {
                            file3.delete();
                        } else {
                            FileLruCache.this.renameToTargetAndTrim(str4, file3);
                        }
                    }
                };
                BufferedOutputStream buffered = new BufferedOutputStream(new CloseCallbackOutputStream(file2, renameToTargetCallback), 8192);
                try {
                    JSONObject header = new JSONObject();
                    try {
                        header.put(HEADER_CACHEKEY_KEY, key);
                        if (Utility.isNullOrEmpty(contentTag)) {
                            str3 = contentTag;
                        } else {
                            try {
                                header.put(HEADER_CACHE_CONTENT_TAG_KEY, contentTag);
                            } catch (JSONException e3) {
                                e = e3;
                                e2 = e;
                                try {
                                    loggingBehavior = LoggingBehavior.CACHE;
                                    str = TAG;
                                    stringBuilder = new StringBuilder();
                                    streamCloseCallback = renameToTargetCallback;
                                } catch (Throwable th2) {
                                    streamCloseCallback = renameToTargetCallback;
                                    file = th2;
                                    if (!false) {
                                        buffered.close();
                                    }
                                    throw file;
                                }
                                try {
                                    stringBuilder.append("Error creating JSON header for cache file: ");
                                    stringBuilder.append(e2);
                                    Logger.log(loggingBehavior, 5, str, stringBuilder.toString());
                                    throw new IOException(e2.getMessage());
                                } catch (Throwable th3) {
                                    th2 = th3;
                                    file = th2;
                                    if (false) {
                                        buffered.close();
                                    }
                                    throw file;
                                }
                            } catch (Throwable th4) {
                                th2 = th4;
                                file = th2;
                                if (false) {
                                    buffered.close();
                                }
                                throw file;
                            }
                        }
                        StreamHeader.writeHeader(buffered, header);
                        if (!true) {
                            buffered.close();
                        }
                        return buffered;
                    } catch (JSONException e4) {
                        e = e4;
                        str3 = contentTag;
                        e2 = e;
                        loggingBehavior = LoggingBehavior.CACHE;
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        streamCloseCallback = renameToTargetCallback;
                        stringBuilder.append("Error creating JSON header for cache file: ");
                        stringBuilder.append(e2);
                        Logger.log(loggingBehavior, 5, str, stringBuilder.toString());
                        throw new IOException(e2.getMessage());
                    } catch (Throwable th5) {
                        th2 = th5;
                        str3 = contentTag;
                        file = th2;
                        if (false) {
                            buffered.close();
                        }
                        throw file;
                    }
                } catch (JSONException e5) {
                    e = e5;
                    str2 = key;
                    str3 = contentTag;
                    e2 = e;
                    loggingBehavior = LoggingBehavior.CACHE;
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    streamCloseCallback = renameToTargetCallback;
                    stringBuilder.append("Error creating JSON header for cache file: ");
                    stringBuilder.append(e2);
                    Logger.log(loggingBehavior, 5, str, stringBuilder.toString());
                    throw new IOException(e2.getMessage());
                } catch (Throwable th6) {
                    th2 = th6;
                    str2 = key;
                    str3 = contentTag;
                    file = th2;
                    if (false) {
                        buffered.close();
                    }
                    throw file;
                }
            } catch (FileNotFoundException e6) {
                str2 = key;
                str3 = contentTag;
                FileNotFoundException e7 = e6;
                LoggingBehavior loggingBehavior2 = LoggingBehavior.CACHE;
                String str5 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Error creating buffer output stream: ");
                stringBuilder2.append(e7);
                Logger.log(loggingBehavior2, 5, str5, stringBuilder2.toString());
                throw new IOException(e7.getMessage());
            }
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("Could not create file at ");
        stringBuilder3.append(buffer.getAbsolutePath());
        throw new IOException(stringBuilder3.toString());
    }

    public void clearCache() {
        final File[] filesToDelete = this.directory.listFiles(BufferFile.excludeBufferFiles());
        this.lastClearCacheTime.set(System.currentTimeMillis());
        if (filesToDelete != null) {
            FacebookSdk.getExecutor().execute(new Runnable() {
                public void run() {
                    for (File file : filesToDelete) {
                        file.delete();
                    }
                }
            });
        }
    }

    public String getLocation() {
        return this.directory.getPath();
    }

    private void renameToTargetAndTrim(String key, File buffer) {
        if (!buffer.renameTo(new File(this.directory, Utility.md5hash(key)))) {
            buffer.delete();
        }
        postTrim();
    }

    public InputStream interceptAndPut(String key, InputStream input) throws IOException {
        return new CopyingInputStream(input, openPutStream(key));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{FileLruCache: tag:");
        stringBuilder.append(this.tag);
        stringBuilder.append(" file:");
        stringBuilder.append(this.directory.getName());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private void postTrim() {
        synchronized (this.lock) {
            if (!this.isTrimPending) {
                this.isTrimPending = true;
                FacebookSdk.getExecutor().execute(new C04363());
            }
        }
    }
}
