package android.support.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.util.Log;
import com.facebook.internal.AnalyticsEvents;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import name.antonsmirnov.firmata.FormatHelper;

final class MultiDexExtractor implements Closeable {
    private static final int BUFFER_SIZE = 16384;
    private static final String DEX_PREFIX = "classes";
    static final String DEX_SUFFIX = ".dex";
    private static final String EXTRACTED_NAME_EXT = ".classes";
    static final String EXTRACTED_SUFFIX = ".zip";
    private static final String KEY_CRC = "crc";
    private static final String KEY_DEX_CRC = "dex.crc.";
    private static final String KEY_DEX_NUMBER = "dex.number";
    private static final String KEY_DEX_TIME = "dex.time.";
    private static final String KEY_TIME_STAMP = "timestamp";
    private static final String LOCK_FILENAME = "MultiDex.lock";
    private static final int MAX_EXTRACT_ATTEMPTS = 3;
    private static final long NO_VALUE = -1;
    private static final String PREFS_FILE = "multidex.version";
    private static final String TAG = "MultiDex";
    private final FileLock cacheLock;
    private final File dexDir;
    private final FileChannel lockChannel;
    private final RandomAccessFile lockRaf;
    private final File sourceApk;
    private final long sourceCrc;

    /* renamed from: android.support.multidex.MultiDexExtractor$1 */
    class C00101 implements FileFilter {
        C00101() {
        }

        public boolean accept(File pathname) {
            return pathname.getName().equals(MultiDexExtractor.LOCK_FILENAME) ^ 1;
        }
    }

    private static class ExtractedDex extends File {
        public long crc = -1;

        public ExtractedDex(File dexDir, String fileName) {
            super(dexDir, fileName);
        }
    }

    MultiDexExtractor(File sourceApk, File dexDir) throws IOException {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MultiDexExtractor(");
        stringBuilder.append(sourceApk.getPath());
        stringBuilder.append(", ");
        stringBuilder.append(dexDir.getPath());
        stringBuilder.append(")");
        Log.i(str, stringBuilder.toString());
        this.sourceApk = sourceApk;
        this.dexDir = dexDir;
        this.sourceCrc = getZipCrc(sourceApk);
        File lockFile = new File(dexDir, LOCK_FILENAME);
        this.lockRaf = new RandomAccessFile(lockFile, "rw");
        try {
            this.lockChannel = this.lockRaf.getChannel();
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Blocking on lock ");
            stringBuilder2.append(lockFile.getPath());
            Log.i(str2, stringBuilder2.toString());
            this.cacheLock = this.lockChannel.lock();
            str2 = TAG;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(lockFile.getPath());
            stringBuilder2.append(" locked");
            Log.i(str2, stringBuilder2.toString());
        } catch (Throwable e) {
            closeQuietly(this.lockChannel);
            throw e;
        } catch (Throwable e2) {
            closeQuietly(this.lockRaf);
            throw e2;
        }
    }

    List<? extends File> load(Context context, String prefsKeyPrefix, boolean forceReload) throws IOException {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MultiDexExtractor.load(");
        stringBuilder.append(this.sourceApk.getPath());
        stringBuilder.append(", ");
        stringBuilder.append(forceReload);
        stringBuilder.append(", ");
        stringBuilder.append(prefsKeyPrefix);
        stringBuilder.append(")");
        Log.i(str, stringBuilder.toString());
        if (this.cacheLock.isValid()) {
            List<ExtractedDex> files;
            if (forceReload || isModified(context, this.sourceApk, this.sourceCrc, prefsKeyPrefix)) {
                if (forceReload) {
                    Log.i(TAG, "Forced extraction must be performed.");
                } else {
                    Log.i(TAG, "Detected that extraction must be performed.");
                }
                files = performExtractions();
                putStoredApkInfo(context, prefsKeyPrefix, getTimeStamp(this.sourceApk), this.sourceCrc, files);
            } else {
                try {
                    files = loadExistingExtractions(context, prefsKeyPrefix);
                } catch (IOException ioe) {
                    Log.w(TAG, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", ioe);
                    List<ExtractedDex> files2 = performExtractions();
                    putStoredApkInfo(context, prefsKeyPrefix, getTimeStamp(this.sourceApk), this.sourceCrc, files2);
                    files = files2;
                }
            }
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("load found ");
            stringBuilder2.append(files.size());
            stringBuilder2.append(" secondary dex files");
            Log.i(str2, stringBuilder2.toString());
            return files;
        }
        throw new IllegalStateException("MultiDexExtractor was closed");
    }

    public void close() throws IOException {
        this.cacheLock.release();
        this.lockChannel.close();
        this.lockRaf.close();
    }

    private List<ExtractedDex> loadExistingExtractions(Context context, String prefsKeyPrefix) throws IOException {
        SharedPreferences multiDexPreferences;
        String str = prefsKeyPrefix;
        Log.i(TAG, "loading existing secondary dex files");
        String extractedFilePrefix = new StringBuilder();
        extractedFilePrefix.append(this.sourceApk.getName());
        extractedFilePrefix.append(EXTRACTED_NAME_EXT);
        extractedFilePrefix = extractedFilePrefix.toString();
        SharedPreferences multiDexPreferences2 = getMultiDexPreferences(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(KEY_DEX_NUMBER);
        int totalDexNumber = multiDexPreferences2.getInt(stringBuilder.toString(), 1);
        List<ExtractedDex> files = new ArrayList(totalDexNumber - 1);
        int secondaryNumber = 2;
        while (secondaryNumber <= totalDexNumber) {
            String fileName = new StringBuilder();
            fileName.append(extractedFilePrefix);
            fileName.append(secondaryNumber);
            fileName.append(EXTRACTED_SUFFIX);
            ExtractedDex extractedFile = new ExtractedDex(r0.dexDir, fileName.toString());
            if (extractedFile.isFile()) {
                extractedFile.crc = getZipCrc(extractedFile);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append(KEY_DEX_CRC);
                stringBuilder2.append(secondaryNumber);
                long expectedCrc = multiDexPreferences2.getLong(stringBuilder2.toString(), -1);
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append(KEY_DEX_TIME);
                stringBuilder2.append(secondaryNumber);
                long expectedModTime = multiDexPreferences2.getLong(stringBuilder2.toString(), -1);
                long lastModified = extractedFile.lastModified();
                if (expectedModTime == lastModified) {
                    String extractedFilePrefix2 = extractedFilePrefix;
                    multiDexPreferences = multiDexPreferences2;
                    if (expectedCrc == extractedFile.crc) {
                        files.add(extractedFile);
                        secondaryNumber++;
                        extractedFilePrefix = extractedFilePrefix2;
                        multiDexPreferences2 = multiDexPreferences;
                    }
                } else {
                    multiDexPreferences = multiDexPreferences2;
                }
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Invalid extracted dex: ");
                stringBuilder3.append(extractedFile);
                stringBuilder3.append(" (key \"");
                stringBuilder3.append(str);
                stringBuilder3.append("\"), expected modification time: ");
                stringBuilder3.append(expectedModTime);
                stringBuilder3.append(", modification time: ");
                stringBuilder3.append(lastModified);
                stringBuilder3.append(", expected crc: ");
                stringBuilder3.append(expectedCrc);
                stringBuilder3.append(", file crc: ");
                stringBuilder3.append(extractedFile.crc);
                throw new IOException(stringBuilder3.toString());
            }
            multiDexPreferences = multiDexPreferences2;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Missing extracted secondary dex file '");
            stringBuilder4.append(extractedFile.getPath());
            stringBuilder4.append(FormatHelper.QUOTE);
            throw new IOException(stringBuilder4.toString());
        }
        multiDexPreferences = multiDexPreferences2;
        return files;
    }

    private static boolean isModified(Context context, File archive, long currentCrc, String prefsKeyPrefix) {
        SharedPreferences prefs = getMultiDexPreferences(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefsKeyPrefix);
        stringBuilder.append("timestamp");
        if (prefs.getLong(stringBuilder.toString(), -1) == getTimeStamp(archive)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(prefsKeyPrefix);
            stringBuilder.append(KEY_CRC);
            if (prefs.getLong(stringBuilder.toString(), -1) == currentCrc) {
                return false;
            }
        }
        return true;
    }

    private static long getTimeStamp(File archive) {
        long timeStamp = archive.lastModified();
        if (timeStamp == -1) {
            return timeStamp - 1;
        }
        return timeStamp;
    }

    private static long getZipCrc(File archive) throws IOException {
        long computedValue = ZipUtil.getZipCrc(archive);
        if (computedValue == -1) {
            return computedValue - 1;
        }
        return computedValue;
    }

    private List<ExtractedDex> performExtractions() throws IOException {
        String extractedFilePrefix = new StringBuilder();
        extractedFilePrefix.append(this.sourceApk.getName());
        extractedFilePrefix.append(EXTRACTED_NAME_EXT);
        extractedFilePrefix = extractedFilePrefix.toString();
        clearDexDir();
        List<ExtractedDex> files = new ArrayList();
        ZipFile apk = new ZipFile(this.sourceApk);
        int secondaryNumber = 2;
        ExtractedDex extractedFile;
        boolean isExtractionSuccessful;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(DEX_PREFIX);
            stringBuilder.append(2);
            stringBuilder.append(DEX_SUFFIX);
            ZipEntry dexFile = apk.getEntry(stringBuilder.toString());
            while (dexFile != null) {
                StringBuilder stringBuilder2;
                String fileName = new StringBuilder();
                fileName.append(extractedFilePrefix);
                fileName.append(secondaryNumber);
                fileName.append(EXTRACTED_SUFFIX);
                extractedFile = new ExtractedDex(this.dexDir, fileName.toString());
                files.add(extractedFile);
                String str = TAG;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Extraction is needed for file ");
                stringBuilder3.append(extractedFile);
                Log.i(str, stringBuilder3.toString());
                int numAttempts = 0;
                isExtractionSuccessful = false;
                while (numAttempts < 3 && !isExtractionSuccessful) {
                    numAttempts++;
                    extract(apk, dexFile, extractedFile, extractedFilePrefix);
                    extractedFile.crc = getZipCrc(extractedFile);
                    isExtractionSuccessful = true;
                    String str2 = TAG;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Extraction ");
                    stringBuilder2.append(isExtractionSuccessful ? AnalyticsEvents.PARAMETER_SHARE_OUTCOME_SUCCEEDED : "failed");
                    stringBuilder2.append(" '");
                    stringBuilder2.append(extractedFile.getAbsolutePath());
                    stringBuilder2.append("': length ");
                    stringBuilder2.append(extractedFile.length());
                    stringBuilder2.append(" - crc: ");
                    stringBuilder2.append(extractedFile.crc);
                    Log.i(str2, stringBuilder2.toString());
                    if (!isExtractionSuccessful) {
                        extractedFile.delete();
                        if (extractedFile.exists()) {
                            str2 = TAG;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Failed to delete corrupted secondary dex '");
                            stringBuilder2.append(extractedFile.getPath());
                            stringBuilder2.append(FormatHelper.QUOTE);
                            Log.w(str2, stringBuilder2.toString());
                        }
                    }
                }
                if (isExtractionSuccessful) {
                    secondaryNumber++;
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append(DEX_PREFIX);
                    stringBuilder4.append(secondaryNumber);
                    stringBuilder4.append(DEX_SUFFIX);
                    dexFile = apk.getEntry(stringBuilder4.toString());
                } else {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Could not create zip file ");
                    stringBuilder2.append(extractedFile.getAbsolutePath());
                    stringBuilder2.append(" for secondary dex (");
                    stringBuilder2.append(secondaryNumber);
                    stringBuilder2.append(")");
                    throw new IOException(stringBuilder2.toString());
                }
            }
            try {
                apk.close();
            } catch (IOException e) {
                Log.w(TAG, "Failed to close resource", e);
            }
            return files;
        } catch (IOException e2) {
            isExtractionSuccessful = false;
            String str3 = TAG;
            StringBuilder stringBuilder5 = new StringBuilder();
            stringBuilder5.append("Failed to read crc from ");
            stringBuilder5.append(extractedFile.getAbsolutePath());
            Log.w(str3, stringBuilder5.toString(), e2);
        } catch (Throwable th) {
            try {
                apk.close();
            } catch (IOException e3) {
                Log.w(TAG, "Failed to close resource", e3);
            }
        }
    }

    private static void putStoredApkInfo(Context context, String keyPrefix, long timeStamp, long crc, List<ExtractedDex> extractedDexes) {
        Editor edit = getMultiDexPreferences(context).edit();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(keyPrefix);
        stringBuilder.append("timestamp");
        edit.putLong(stringBuilder.toString(), timeStamp);
        stringBuilder = new StringBuilder();
        stringBuilder.append(keyPrefix);
        stringBuilder.append(KEY_CRC);
        edit.putLong(stringBuilder.toString(), crc);
        stringBuilder = new StringBuilder();
        stringBuilder.append(keyPrefix);
        stringBuilder.append(KEY_DEX_NUMBER);
        edit.putInt(stringBuilder.toString(), extractedDexes.size() + 1);
        int extractedDexId = 2;
        for (ExtractedDex dex : extractedDexes) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(keyPrefix);
            stringBuilder2.append(KEY_DEX_CRC);
            stringBuilder2.append(extractedDexId);
            edit.putLong(stringBuilder2.toString(), dex.crc);
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(keyPrefix);
            stringBuilder2.append(KEY_DEX_TIME);
            stringBuilder2.append(extractedDexId);
            edit.putLong(stringBuilder2.toString(), dex.lastModified());
            extractedDexId++;
        }
        edit.commit();
    }

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, VERSION.SDK_INT < 11 ? 0 : 4);
    }

    private void clearDexDir() {
        File[] files = this.dexDir.listFiles(new C00101());
        if (files == null) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to list secondary dex dir content (");
            stringBuilder.append(this.dexDir.getPath());
            stringBuilder.append(").");
            Log.w(str, stringBuilder.toString());
            return;
        }
        for (File oldFile : files) {
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Trying to delete old file ");
            stringBuilder2.append(oldFile.getPath());
            stringBuilder2.append(" of size ");
            stringBuilder2.append(oldFile.length());
            Log.i(str2, stringBuilder2.toString());
            if (oldFile.delete()) {
                str2 = TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Deleted old file ");
                stringBuilder2.append(oldFile.getPath());
                Log.i(str2, stringBuilder2.toString());
            } else {
                str2 = TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Failed to delete old file ");
                stringBuilder2.append(oldFile.getPath());
                Log.w(str2, stringBuilder2.toString());
            }
        }
    }

    private static void extract(ZipFile apk, ZipEntry dexFile, File extractTo, String extractedFilePrefix) throws IOException, FileNotFoundException {
        InputStream in = apk.getInputStream(dexFile);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tmp-");
        stringBuilder.append(extractedFilePrefix);
        File tmp = File.createTempFile(stringBuilder.toString(), EXTRACTED_SUFFIX, extractTo.getParentFile());
        String str = TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Extracting ");
        stringBuilder2.append(tmp.getPath());
        Log.i(str, stringBuilder2.toString());
        ZipOutputStream out;
        try {
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(tmp)));
            ZipEntry classesDex = new ZipEntry("classes.dex");
            classesDex.setTime(dexFile.getTime());
            out.putNextEntry(classesDex);
            byte[] buffer = new byte[16384];
            for (int length = in.read(buffer); length != -1; length = in.read(buffer)) {
                out.write(buffer, 0, length);
            }
            out.closeEntry();
            out.close();
            if (tmp.setReadOnly()) {
                str = TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Renaming to ");
                stringBuilder2.append(extractTo.getPath());
                Log.i(str, stringBuilder2.toString());
                if (tmp.renameTo(extractTo)) {
                    closeQuietly(in);
                    tmp.delete();
                    return;
                }
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Failed to rename \"");
                stringBuilder2.append(tmp.getAbsolutePath());
                stringBuilder2.append("\" to \"");
                stringBuilder2.append(extractTo.getAbsolutePath());
                stringBuilder2.append("\"");
                throw new IOException(stringBuilder2.toString());
            }
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Failed to mark readonly \"");
            stringBuilder2.append(tmp.getAbsolutePath());
            stringBuilder2.append("\" (tmp of \"");
            stringBuilder2.append(extractTo.getAbsolutePath());
            stringBuilder2.append("\")");
            throw new IOException(stringBuilder2.toString());
        } catch (Throwable th) {
            closeQuietly(in);
            tmp.delete();
        }
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.w(TAG, "Failed to close resource", e);
        }
    }
}
