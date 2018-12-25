package com.parrot.freeflight.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class FileUtils {
    public static final String MEDIA_PUBLIC_FOLDER_NAME = "AR.Drone";
    public static final String NO_MEDIA_FILE = ".no_media";
    private static final String TAG = "FileUtils";
    public static final String THUMBNAILS_FOLDER = ".thumbnails";

    /* renamed from: com.parrot.freeflight.utils.FileUtils$1 */
    class C16351 implements Comparator<File> {
        C16351() {
        }

        public int compare(File f1, File f2) {
            return Long.valueOf(f1.lastModified()).compareTo(Long.valueOf(f2.lastModified()));
        }
    }

    private static class FileFilterImpl implements FileFilter {
        private String ext;

        public FileFilterImpl(String extension) {
            this.ext = extension;
        }

        public boolean accept(File pathname) {
            return pathname.isDirectory() || pathname.getName().endsWith(this.ext);
        }
    }

    private FileUtils() {
    }

    public static File getMediaFolder(Context context) {
        File dcimFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (dcimFolder == null) {
            Log.w(TAG, "Looks like sd card is not available.");
            return null;
        }
        File mediaFolder = new File(dcimFolder, MEDIA_PUBLIC_FOLDER_NAME);
        if (!mediaFolder.exists()) {
            mediaFolder.mkdirs();
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("Root media folder created ");
            stringBuilder.append(mediaFolder);
            Log.d(str, stringBuilder.toString());
        }
        return mediaFolder;
    }

    public static boolean isExtStorgAvailable() {
        if (Environment.getExternalStorageState().equalsIgnoreCase("mounted")) {
            return true;
        }
        return false;
    }

    public static File getMediaThumbFolder(Context context) {
        File mediaThumbFolder = new File(getMediaFolder(context), THUMBNAILS_FOLDER);
        if (mediaThumbFolder != null) {
            if (!mediaThumbFolder.exists()) {
                mediaThumbFolder.mkdirs();
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder("Thumbnails folder created ");
                stringBuilder.append(mediaThumbFolder);
                Log.d(str, stringBuilder.toString());
            }
            createNoMediaFile(mediaThumbFolder);
        }
        return mediaThumbFolder;
    }

    private static void createNoMediaFile(File file) {
        try {
            File noMediaFile = new File(file, NO_MEDIA_FILE);
            if (!noMediaFile.exists()) {
                noMediaFile.createNewFile();
            }
        } catch (IOException e) {
            Log.w(TAG, e.toString());
        }
    }

    public static void sortFileByDate(List<File> allFileList) {
        File[] files = new File[allFileList.size()];
        for (int i = 0; i < allFileList.size(); i++) {
            files[i] = (File) allFileList.get(i);
        }
        Arrays.sort(files, new C16351());
        allFileList.clear();
        allFileList.addAll(Arrays.asList(files));
    }

    public static File getMediaCopyFolder(Context context) {
        File mediaCopyFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), MEDIA_PUBLIC_FOLDER_NAME);
        if (!mediaCopyFolder.exists()) {
            mediaCopyFolder.mkdirs();
        }
        return mediaCopyFolder;
    }

    public static void copyFileToDir(File sourceFile, File destFile) {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            inStream = new FileInputStream(sourceFile);
            outStream = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = inStream.read(buffer);
                int length = read;
                if (read <= 0) {
                    break;
                }
                outStream.write(buffer, 0, length);
            }
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("File copied: ");
            stringBuilder.append(destFile);
            Log.d(str, stringBuilder.toString());
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (byte[] buffer2) {
                    Log.d(TAG, buffer2.toString());
                }
            }
        } catch (IOException e2) {
            Log.w(TAG, e2.toString());
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e22) {
                    Log.d(TAG, e22.toString());
                }
            }
            if (outStream != null) {
                outStream.close();
            }
        } catch (Throwable th) {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e3) {
                    Log.d(TAG, e3.toString());
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e32) {
                    Log.d(TAG, e32.toString());
                }
            }
        }
    }

    public static void deleteFile(String file) {
        deleteFile(new File(file));
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("File deleted: ");
            stringBuilder.append(file);
            Log.d(str, stringBuilder.toString());
        }
    }

    public static boolean isVideo(String file) {
        if (file.endsWith("mp4")) {
            return true;
        }
        return false;
    }

    public static boolean isVideo(File file) {
        return isVideo(file.getName());
    }

    public static File convertFormat(File file, String newFormat) {
        StringBuilder tmpValue = new StringBuilder(file.getAbsolutePath());
        tmpValue.delete(tmpValue.lastIndexOf(".") + 1, tmpValue.length());
        tmpValue.append(newFormat);
        return new File(tmpValue.toString());
    }

    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public static void getFileList(List<File> fileList, File root, File[] ignoreList) {
        File[] list = root.listFiles();
        if (list != null) {
            for (File f : list) {
                if (!f.isDirectory() || isIgnored(ignoreList, f)) {
                    String filename = getFileExt(f.getName());
                    if (filename.equalsIgnoreCase("jpg") || filename.equalsIgnoreCase("png") || filename.equalsIgnoreCase("mp4")) {
                        fileList.add(f);
                    }
                } else {
                    getFileList((List) fileList, f, ignoreList);
                }
            }
        }
    }

    public static void getFileList(List<File> fileList, File root, FileFilter filter) {
        File[] list = root.listFiles(filter);
        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    fileList.add(f);
                    getFileList((List) fileList, f, filter);
                } else {
                    fileList.add(f);
                }
            }
        }
    }

    public static String getNextFile(File root, String extension) {
        File[] list = root.listFiles(new FileFilterImpl(extension));
        if (list == null) {
            return null;
        }
        for (File f : list) {
            String path;
            if (f.isDirectory()) {
                path = getNextFile(f, extension);
                if (path != null) {
                    return path;
                }
            } else {
                path = f.getAbsolutePath();
                if (path != null) {
                    return path;
                }
            }
        }
        return null;
    }

    private static boolean isIgnored(File[] ignoreList, File file) {
        boolean result = false;
        for (File item : ignoreList) {
            if (item.getName().equalsIgnoreCase(file.getName())) {
                result = true;
            }
        }
        return result;
    }
}
