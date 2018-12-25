package com.koushikdutta.async.util;

import android.support.v4.media.session.PlaybackStateCompat;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class FileCache {
    private static String hashAlgorithm = CommonUtils.MD5_INSTANCE;
    static MessageDigest messageDigest;
    long blockSize = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
    InternalCache cache;
    Comparator<File> dateCompare = new C06691();
    File directory;
    boolean loadAsync;
    boolean loading;
    Random random = new Random();
    long size;

    /* renamed from: com.koushikdutta.async.util.FileCache$1 */
    class C06691 implements Comparator<File> {
        C06691() {
        }

        public int compare(File lhs, File rhs) {
            long l = lhs.lastModified();
            long r = rhs.lastModified();
            if (l < r) {
                return -1;
            }
            if (r > l) {
                return 1;
            }
            return 0;
        }
    }

    /* renamed from: com.koushikdutta.async.util.FileCache$2 */
    class C06702 extends Thread {
        C06702() {
        }

        public void run() {
            FileCache.this.load();
        }
    }

    class CacheEntry {
        final long size;

        public CacheEntry(File file) {
            this.size = file.length();
        }
    }

    public static class Snapshot {
        FileInputStream[] fins;
        long[] lens;

        Snapshot(FileInputStream[] fins, long[] lens) {
            this.fins = fins;
            this.lens = lens;
        }

        public long getLength(int index) {
            return this.lens[index];
        }

        public void close() {
            StreamUtility.closeQuietly(this.fins);
        }
    }

    class InternalCache extends LruCache<String, CacheEntry> {
        public InternalCache() {
            super(FileCache.this.size);
        }

        protected long sizeOf(String key, CacheEntry value) {
            return Math.max(FileCache.this.blockSize, value.size);
        }

        protected void entryRemoved(boolean evicted, String key, CacheEntry oldValue, CacheEntry newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
            if (newValue == null && !FileCache.this.loading) {
                new File(FileCache.this.directory, key).delete();
            }
        }
    }

    static {
        try {
            messageDigest = MessageDigest.getInstance(hashAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            messageDigest = findAlternativeMessageDigest();
            if (messageDigest == null) {
                throw new RuntimeException(e);
            }
        }
        try {
            messageDigest = (MessageDigest) messageDigest.clone();
        } catch (CloneNotSupportedException e2) {
        }
    }

    private static MessageDigest findAlternativeMessageDigest() {
        if (CommonUtils.MD5_INSTANCE.equals(hashAlgorithm)) {
            for (Provider provider : Security.getProviders()) {
                for (Service service : provider.getServices()) {
                    hashAlgorithm = service.getAlgorithm();
                    try {
                        MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm);
                        if (messageDigest != null) {
                            return messageDigest;
                        }
                    } catch (NoSuchAlgorithmException e) {
                    }
                }
            }
        }
        return null;
    }

    public static synchronized String toKeyString(Object... parts) {
        synchronized (FileCache.class) {
            messageDigest.reset();
            for (Object part : parts) {
                messageDigest.update(part.toString().getBytes());
            }
        }
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }

    public File getTempFile() {
        while (true) {
            File file = new File(this.directory, new BigInteger(128, this.random).toString(16));
            File f = file;
            if (!file.exists()) {
                return f;
            }
        }
    }

    public File[] getTempFiles(int count) {
        File[] ret = new File[count];
        for (int i = 0; i < count; i++) {
            ret[i] = getTempFile();
        }
        return ret;
    }

    public static void removeFiles(File... files) {
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    public void remove(String key) {
        for (int i = 0; this.cache.remove(getPartName(key, i)) != null; i++) {
        }
        removePartFiles(key);
    }

    public boolean exists(String key, int part) {
        return getPartFile(key, part).exists();
    }

    public boolean exists(String key) {
        return getPartFile(key, 0).exists();
    }

    public File touch(File file) {
        this.cache.get(file.getName());
        file.setLastModified(System.currentTimeMillis());
        return file;
    }

    public FileInputStream get(String key) throws IOException {
        return new FileInputStream(touch(getPartFile(key, 0)));
    }

    public File getFile(String key) {
        return touch(getPartFile(key, 0));
    }

    public FileInputStream[] get(String key, int count) throws IOException {
        FileInputStream[] ret = new FileInputStream[count];
        int i = 0;
        while (i < count) {
            try {
                ret[i] = new FileInputStream(touch(getPartFile(key, i)));
                i++;
            } catch (IOException e) {
                int length = ret.length;
                for (int i2 = 0; i2 < length; i2++) {
                    StreamUtility.closeQuietly(ret[i2]);
                }
                remove(key);
                throw e;
            }
        }
        return ret;
    }

    String getPartName(String key, int part) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(key);
        stringBuilder.append(".");
        stringBuilder.append(part);
        return stringBuilder.toString();
    }

    public void commitTempFiles(String key, File... tempFiles) {
        removePartFiles(key);
        int i = 0;
        while (i < tempFiles.length) {
            File tmp = tempFiles[i];
            File partFile = getPartFile(key, i);
            if (tmp.renameTo(partFile)) {
                remove(tmp.getName());
                this.cache.put(getPartName(key, i), new CacheEntry(partFile));
                i++;
            } else {
                removeFiles(tempFiles);
                remove(key);
                return;
            }
        }
    }

    void removePartFiles(String key) {
        int i = 0;
        while (true) {
            File partFile = getPartFile(key, i);
            File f = partFile;
            if (partFile.exists()) {
                f.delete();
                i++;
            } else {
                return;
            }
        }
    }

    File getPartFile(String key, int part) {
        return new File(this.directory, getPartName(key, part));
    }

    public void setBlockSize(long blockSize) {
        this.blockSize = blockSize;
    }

    void load() {
        this.loading = true;
        try {
            File[] files = this.directory.listFiles();
            if (files != null) {
                ArrayList<File> list = new ArrayList();
                Collections.addAll(list, files);
                Collections.sort(list, this.dateCompare);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    File file = (File) it.next();
                    String name = file.getName();
                    this.cache.put(name, new CacheEntry(file));
                    this.cache.get(name);
                }
                this.loading = false;
            }
        } finally {
            this.loading = false;
        }
    }

    private void doLoad() {
        if (this.loadAsync) {
            new C06702().start();
        } else {
            load();
        }
    }

    public FileCache(File directory, long size, boolean loadAsync) {
        this.directory = directory;
        this.size = size;
        this.loadAsync = loadAsync;
        this.cache = new InternalCache();
        directory.mkdirs();
        doLoad();
    }

    public long size() {
        return this.cache.size();
    }

    public void clear() {
        removeFiles(this.directory.listFiles());
        this.cache.evictAll();
    }

    public Set<String> keySet() {
        HashSet<String> ret = new HashSet();
        File[] files = this.directory.listFiles();
        if (files == null) {
            return ret;
        }
        for (File file : files) {
            String name = file.getName();
            int last = name.lastIndexOf(46);
            if (last != -1) {
                ret.add(name.substring(0, last));
            }
        }
        return ret;
    }

    public void setMaxSize(long maxSize) {
        this.cache.setMaxSize(maxSize);
        doLoad();
    }
}
