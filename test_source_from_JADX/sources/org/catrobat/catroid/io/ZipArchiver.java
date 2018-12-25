package org.catrobat.catroid.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipArchiver {
    private static final int COMPRESSION_LEVEL = 0;
    private static final String DIRECTORY_LEVEL_UP = "../";

    public void zip(File archive, File[] files) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(archive);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        try {
            zipOutputStream.setLevel(0);
            writeZipEntriesToStream(zipOutputStream, Arrays.asList(files), "");
        } finally {
            zipOutputStream.close();
            fileOutputStream.close();
        }
    }

    private void writeZipEntriesToStream(ZipOutputStream zipOutputStream, List<File> files, String parentDir) throws IOException {
        Iterator it = files.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            if (!file.exists()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("File: ");
                stringBuilder.append(file.getAbsolutePath());
                stringBuilder.append(" does NOT exist.");
                throw new FileNotFoundException(stringBuilder.toString());
            } else if (file.isDirectory()) {
                List asList = Arrays.asList(file.listFiles());
                r3 = new StringBuilder();
                r3.append(parentDir);
                r3.append(file.getName());
                r3.append("/");
                writeZipEntriesToStream(zipOutputStream, asList, r3.toString());
            } else {
                r3 = new StringBuilder();
                r3.append(parentDir);
                r3.append(file.getName());
                zipOutputStream.putNextEntry(new ZipEntry(r3.toString()));
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] b = new byte[8192];
                while (true) {
                    try {
                        int read = fileInputStream.read(b);
                        int len = read;
                        if (read == -1) {
                            break;
                        }
                        zipOutputStream.write(b, 0, len);
                    } finally {
                        fileInputStream.close();
                        zipOutputStream.closeEntry();
                    }
                }
            }
        }
    }

    public void unzip(File archive, File dstDir) throws IOException {
        unzip(new FileInputStream(archive), dstDir);
    }

    public void unzip(InputStream is, File dstDir) throws IOException {
        createDirIfNecessary(dstDir);
        ZipInputStream zipInputStream = new ZipInputStream(is);
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            ZipEntry zipEntry = nextEntry;
            if (nextEntry == null) {
                zipInputStream.close();
                return;
            } else if (!zipEntry.getName().contains(DIRECTORY_LEVEL_UP)) {
                if (zipEntry.isDirectory()) {
                    createDirIfNecessary(new File(dstDir, zipEntry.getName()));
                } else {
                    File zipEntryFile = new File(dstDir, zipEntry.getName());
                    zipEntryFile.getParentFile().mkdirs();
                    FileOutputStream fileOutputStream = new FileOutputStream(zipEntryFile);
                    byte[] b = new byte[8192];
                    while (true) {
                        try {
                            int read = zipInputStream.read(b);
                            int len = read;
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(b, 0, len);
                        } catch (Throwable th) {
                            zipInputStream.close();
                        }
                    }
                    fileOutputStream.close();
                }
            }
        }
    }

    private void createDirIfNecessary(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdir()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could NOT create Dir: ");
            stringBuilder.append(dir.getAbsolutePath());
            throw new IOException(stringBuilder.toString());
        }
    }
}
