package org.catrobat.catroid.io;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.InvalidPathException;
import org.catrobat.catroid.common.Constants;

public final class StorageOperations {
    private static final String FILE_NAME_APPENDIX = "_#";

    private StorageOperations() {
        throw new AssertionError();
    }

    public static void createDir(File dir) throws IOException {
        StringBuilder stringBuilder;
        if (dir.isFile()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(dir.getAbsolutePath());
            stringBuilder.append(" exists, but is not a directory.");
            throw new IOException(stringBuilder.toString());
        } else if (!dir.exists() && !dir.mkdir()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot create directory at: ");
            stringBuilder.append(dir.getAbsolutePath());
            throw new IOException(stringBuilder.toString());
        }
    }

    public static void createSceneDirectory(File sceneDir) throws IOException {
        createDir(sceneDir);
        File imageDir = new File(sceneDir, Constants.IMAGE_DIRECTORY_NAME);
        createDir(imageDir);
        new File(imageDir, Constants.NO_MEDIA_FILE).createNewFile();
        File soundDir = new File(sceneDir, Constants.SOUND_DIRECTORY_NAME);
        createDir(soundDir);
        new File(soundDir, Constants.NO_MEDIA_FILE).createNewFile();
    }

    public static String getSanitizedFileName(String fileName) {
        int extensionStartIndex = fileName.lastIndexOf(46);
        int appendixStartIndex = fileName.lastIndexOf(FILE_NAME_APPENDIX);
        if (appendixStartIndex == -1) {
            appendixStartIndex = extensionStartIndex;
        }
        if (appendixStartIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, appendixStartIndex);
    }

    public static String resolveFileName(ContentResolver contentResolver, Uri uri) {
        String result = null;
        if (uri.getScheme().equals(FirebaseAnalytics$Param.CONTENT)) {
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex("_display_name"));
                    }
                } catch (Throwable th) {
                    cursor.close();
                }
            }
            cursor.close();
        }
        if (result != null) {
            return result;
        }
        result = uri.getPath();
        int cut = result.lastIndexOf(47);
        if (cut != -1) {
            return result.substring(cut + 1);
        }
        return result;
    }

    public static File duplicateFile(File src) throws IOException {
        return copyFileToDir(src, src.getParentFile());
    }

    public static File copyFileToDir(File srcFile, File dstDir) throws IOException {
        StringBuilder stringBuilder;
        if (!srcFile.exists()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(srcFile.getAbsolutePath());
            stringBuilder.append(" does not exist.");
            throw new FileNotFoundException(stringBuilder.toString());
        } else if (!dstDir.exists()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(dstDir.getAbsolutePath());
            stringBuilder.append(" does not exist.");
            throw new FileNotFoundException(stringBuilder.toString());
        } else if (dstDir.isDirectory()) {
            File dstFile = getUniqueFile(srcFile.getName(), dstDir);
            transferData(srcFile, dstFile);
            return dstFile;
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(dstDir.getAbsolutePath());
            stringBuilder.append(" is not a directory.");
            throw new IOException(stringBuilder.toString());
        }
    }

    public static File copyStreamToFile(InputStream inputStream, File dstFile) throws IOException {
        if (dstFile.exists() || dstFile.createNewFile()) {
            return transferData(inputStream, dstFile);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot create file: ");
        stringBuilder.append(dstFile.getAbsolutePath());
        stringBuilder.append(".");
        throw new IOException(stringBuilder.toString());
    }

    public static File copyStreamToDir(InputStream inputStream, File dstDir, String fileName) throws IOException, InvalidPathException {
        StringBuilder stringBuilder;
        if (!dstDir.exists()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Destination directory: ");
            stringBuilder.append(dstDir.getAbsolutePath());
            stringBuilder.append(" does not exist.");
            throw new FileNotFoundException(stringBuilder.toString());
        } else if (dstDir.isDirectory()) {
            File dstFile = getUniqueFile(fileName, dstDir);
            if (dstFile.createNewFile()) {
                return transferData(inputStream, dstFile);
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Cannot create file: ");
            stringBuilder2.append(dstFile.getAbsolutePath());
            stringBuilder2.append(".");
            throw new IOException(stringBuilder2.toString());
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(dstDir.getAbsolutePath());
            stringBuilder.append(" is not a directory.");
            throw new IOException(stringBuilder.toString());
        }
    }

    public static File copyUriToDir(ContentResolver contentResolver, Uri uri, File dstDir, String fileName) throws IOException {
        return copyStreamToDir(contentResolver.openInputStream(uri), dstDir, fileName);
    }

    public static void transferData(File srcFile, File dstFile) throws IOException {
        FileChannel ic = new FileInputStream(srcFile).getChannel();
        FileChannel oc = new FileOutputStream(dstFile).getChannel();
        try {
            ic.transferTo(0, ic.size(), oc);
        } finally {
            if (ic != null) {
                ic.close();
            }
            oc.close();
        }
    }

    private static File transferData(InputStream inputStream, File dstFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
        byte[] b = new byte[8192];
        while (true) {
            try {
                int read = inputStream.read(b);
                int len = read;
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(b, 0, len);
            } finally {
                inputStream.close();
                fileOutputStream.close();
            }
        }
        return dstFile;
    }

    public static File copyDir(File srcDir, File dstDir) throws IOException {
        StringBuilder stringBuilder;
        if (!srcDir.exists()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Directory: ");
            stringBuilder.append(srcDir.getAbsolutePath());
            stringBuilder.append(" does not exist.");
            throw new FileNotFoundException(stringBuilder.toString());
        } else if (srcDir.isDirectory()) {
            dstDir.mkdir();
            if (dstDir.isDirectory()) {
                for (File file : srcDir.listFiles()) {
                    if (file.isDirectory()) {
                        copyDir(file, new File(dstDir, file.getName()));
                    } else {
                        copyFileToDir(file, dstDir);
                    }
                }
                return dstDir;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot create directory: ");
            stringBuilder.append(dstDir.getAbsolutePath());
            throw new IOException(stringBuilder.toString());
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(srcDir.getAbsolutePath());
            stringBuilder.append(" is not a directory.");
            throw new IOException(stringBuilder.toString());
        }
    }

    private static synchronized File getUniqueFile(String originalName, File dstDir) throws IOException {
        synchronized (StorageOperations.class) {
            File dstFile = new File(dstDir, originalName);
            if (dstFile.exists()) {
                int extensionStartIndex = originalName.lastIndexOf(46);
                if (extensionStartIndex == -1) {
                    extensionStartIndex = originalName.length() - 1;
                }
                int appendixStartIndex = originalName.lastIndexOf(FILE_NAME_APPENDIX);
                if (appendixStartIndex == -1) {
                    appendixStartIndex = extensionStartIndex;
                }
                String extension = originalName.substring(extensionStartIndex);
                int appendix = 0;
                String fileName = originalName.substring(0, appendixStartIndex);
                while (appendix < Integer.MAX_VALUE) {
                    String dstFileName = new StringBuilder();
                    dstFileName.append(fileName);
                    dstFileName.append(FILE_NAME_APPENDIX);
                    dstFileName.append(appendix);
                    dstFileName.append(extension);
                    dstFile = new File(dstDir, dstFileName.toString());
                    if (dstFile.exists()) {
                        appendix++;
                    } else {
                        return dstFile;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot find a unique file name in ");
                stringBuilder.append(dstDir.getAbsolutePath());
                stringBuilder.append(".");
                throw new IOException(stringBuilder.toString());
            }
            return dstFile;
        }
    }

    public static void deleteFile(File file) throws IOException {
        StringBuilder stringBuilder;
        if (!file.exists()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("File: ");
            stringBuilder.append(file.getAbsolutePath());
            stringBuilder.append(" does not exist.");
            throw new FileNotFoundException(stringBuilder.toString());
        } else if (!file.delete()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot delete file: ");
            stringBuilder.append(file.getAbsolutePath());
            throw new IOException(stringBuilder.toString());
        }
    }

    public static void deleteDir(File dir) throws IOException {
        StringBuilder stringBuilder;
        if (!dir.exists()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(dir.getAbsolutePath());
            stringBuilder.append(" does not exist.");
            throw new FileNotFoundException(stringBuilder.toString());
        } else if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    deleteFile(file);
                }
            }
            if (!dir.delete()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot delete directory: ");
                stringBuilder.append(dir.getAbsolutePath());
                throw new IOException(stringBuilder.toString());
            }
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(dir.getAbsolutePath());
            stringBuilder.append(" is not a directory.");
            throw new FileNotFoundException(stringBuilder.toString());
        }
    }
}
