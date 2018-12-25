package com.badlogic.gdx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SharedLibraryLoader {
    public static String abi = (System.getProperty("sun.arch.abi") == null ? System.getProperty("sun.arch.abi") : "");
    public static boolean is64Bit;
    public static boolean isARM = System.getProperty("os.arch").startsWith("arm");
    public static boolean isAndroid;
    public static boolean isIos;
    public static boolean isLinux;
    public static boolean isMac;
    public static boolean isWindows;
    private static final HashSet<String> loadedLibraries = new HashSet();
    private String nativesJar;

    static {
        boolean z;
        String vm;
        isWindows = System.getProperty("os.name").contains("Windows");
        isLinux = System.getProperty("os.name").contains("Linux");
        isMac = System.getProperty("os.name").contains("Mac");
        isIos = false;
        isAndroid = false;
        if (!System.getProperty("os.arch").equals("amd64")) {
            if (!System.getProperty("os.arch").equals("x86_64")) {
                z = false;
                is64Bit = z;
                vm = System.getProperty("java.runtime.name");
                if (vm != null && vm.contains("Android Runtime")) {
                    isAndroid = true;
                    isWindows = false;
                    isLinux = false;
                    isMac = false;
                    is64Bit = false;
                }
                if (!(isAndroid || isWindows || isLinux || isMac)) {
                    isIos = true;
                    is64Bit = false;
                }
            }
        }
        z = true;
        is64Bit = z;
        if (System.getProperty("sun.arch.abi") == null) {
        }
        vm = System.getProperty("java.runtime.name");
        isAndroid = true;
        isWindows = false;
        isLinux = false;
        isMac = false;
        is64Bit = false;
        isIos = true;
        is64Bit = false;
    }

    public SharedLibraryLoader(String nativesJar) {
        this.nativesJar = nativesJar;
    }

    public String crc(InputStream input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        CRC32 crc = new CRC32();
        byte[] buffer = new byte[4096];
        while (true) {
            try {
                int length = input.read(buffer);
                if (length == -1) {
                    break;
                }
                crc.update(buffer, 0, length);
            } catch (Exception e) {
                StreamUtils.closeQuietly(input);
            }
        }
        return Long.toString(crc.getValue(), 16);
    }

    public String mapLibraryName(String libraryName) {
        StringBuilder stringBuilder;
        if (isWindows) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(libraryName);
            stringBuilder.append(is64Bit ? "64.dll" : ".dll");
            return stringBuilder.toString();
        } else if (isLinux) {
            String stringBuilder2;
            stringBuilder = new StringBuilder();
            stringBuilder.append("lib");
            stringBuilder.append(libraryName);
            if (isARM) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("arm");
                stringBuilder3.append(abi);
                stringBuilder2 = stringBuilder3.toString();
            } else {
                stringBuilder2 = "";
            }
            stringBuilder.append(stringBuilder2);
            stringBuilder.append(is64Bit ? "64.so" : ".so");
            return stringBuilder.toString();
        } else if (!isMac) {
            return libraryName;
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("lib");
            stringBuilder.append(libraryName);
            stringBuilder.append(is64Bit ? "64.dylib" : ".dylib");
            return stringBuilder.toString();
        }
    }

    public synchronized void load(String libraryName) {
        if (!isIos) {
            libraryName = mapLibraryName(libraryName);
            if (!loadedLibraries.contains(libraryName)) {
                try {
                    if (isAndroid) {
                        System.loadLibrary(libraryName);
                    } else {
                        loadFile(libraryName);
                    }
                    loadedLibraries.add(libraryName);
                } catch (Throwable ex) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't load shared library '");
                    stringBuilder.append(libraryName);
                    stringBuilder.append("' for target: ");
                    stringBuilder.append(System.getProperty("os.name"));
                    stringBuilder.append(is64Bit ? ", 64-bit" : ", 32-bit");
                    GdxRuntimeException gdxRuntimeException = new GdxRuntimeException(stringBuilder.toString(), ex);
                }
            }
        }
    }

    private InputStream readFile(String path) {
        if (this.nativesJar == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/");
            stringBuilder.append(path);
            InputStream input = SharedLibraryLoader.class.getResourceAsStream(stringBuilder.toString());
            if (input != null) {
                return input;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unable to read file for extraction: ");
            stringBuilder2.append(path);
            throw new GdxRuntimeException(stringBuilder2.toString());
        }
        try {
            ZipFile file = new ZipFile(this.nativesJar);
            ZipEntry entry = file.getEntry(path);
            if (entry != null) {
                return file.getInputStream(entry);
            }
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Couldn't find '");
            stringBuilder3.append(path);
            stringBuilder3.append("' in JAR: ");
            stringBuilder3.append(this.nativesJar);
            throw new GdxRuntimeException(stringBuilder3.toString());
        } catch (IOException ex) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Error reading '");
            stringBuilder2.append(path);
            stringBuilder2.append("' in JAR: ");
            stringBuilder2.append(this.nativesJar);
            throw new GdxRuntimeException(stringBuilder2.toString(), ex);
        }
    }

    public File extractFile(String sourcePath, String dirName) throws IOException {
        try {
            String sourceCrc = crc(readFile(sourcePath));
            if (dirName == null) {
                dirName = sourceCrc;
            }
            return extractFile(sourcePath, sourceCrc, getExtractedFile(dirName, new File(sourcePath).getName()));
        } catch (RuntimeException ex) {
            File file = new File(System.getProperty("java.library.path"), sourcePath);
            if (file.exists()) {
                return file;
            }
            throw ex;
        }
    }

    private File getExtractedFile(String dirName, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.getProperty("java.io.tmpdir"));
        stringBuilder.append("/libgdx");
        stringBuilder.append(System.getProperty("user.name"));
        stringBuilder.append("/");
        stringBuilder.append(dirName);
        File idealFile = new File(stringBuilder.toString(), fileName);
        if (canWrite(idealFile)) {
            return idealFile;
        }
        File file;
        try {
            file = File.createTempFile(dirName, null);
            if (file.delete()) {
                file = new File(file, fileName);
                if (canWrite(file)) {
                    return file;
                }
            }
        } catch (IOException e) {
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(System.getProperty("user.home"));
        stringBuilder2.append("/.libgdx/");
        stringBuilder2.append(dirName);
        file = new File(stringBuilder2.toString(), fileName);
        if (canWrite(file)) {
            return file;
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(".temp/");
        stringBuilder3.append(dirName);
        file = new File(stringBuilder3.toString(), fileName);
        if (canWrite(file)) {
            return file;
        }
        return idealFile;
    }

    private boolean canWrite(File file) {
        File testFile;
        File parent = file.getParentFile();
        boolean z = false;
        if (file.exists()) {
            if (file.canWrite()) {
                if (canExecute(file)) {
                    testFile = new File(parent, UUID.randomUUID().toString());
                }
            }
            return false;
        }
        parent.mkdirs();
        if (!parent.isDirectory()) {
            return false;
        }
        testFile = file;
        try {
            new FileOutputStream(testFile).close();
            if (canExecute(testFile)) {
                z = true;
            }
        } catch (Throwable th) {
            testFile.delete();
        }
        testFile.delete();
        return z;
    }

    private boolean canExecute(File file) {
        try {
            Method canExecute = File.class.getMethod("canExecute", new Class[0]);
            if (((Boolean) canExecute.invoke(file, new Object[0])).booleanValue()) {
                return true;
            }
            File.class.getMethod("setExecutable", new Class[]{Boolean.TYPE, Boolean.TYPE}).invoke(file, new Object[]{Boolean.valueOf(true), Boolean.valueOf(false)});
            return ((Boolean) canExecute.invoke(file, new Object[0])).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    private File extractFile(String sourcePath, String sourceCrc, File extractedFile) throws IOException {
        String extractedCrc = null;
        if (extractedFile.exists()) {
            try {
                extractedCrc = crc(new FileInputStream(extractedFile));
            } catch (FileNotFoundException e) {
            }
        }
        if (extractedCrc == null || !extractedCrc.equals(sourceCrc)) {
            try {
                InputStream input = readFile(sourcePath);
                extractedFile.getParentFile().mkdirs();
                FileOutputStream output = new FileOutputStream(extractedFile);
                byte[] buffer = new byte[4096];
                while (true) {
                    int length = input.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    output.write(buffer, 0, length);
                }
                input.close();
                output.close();
            } catch (IOException ex) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error extracting file: ");
                stringBuilder.append(sourcePath);
                stringBuilder.append("\nTo: ");
                stringBuilder.append(extractedFile.getAbsolutePath());
                throw new GdxRuntimeException(stringBuilder.toString(), ex);
            }
        }
        return extractedFile;
    }

    private void loadFile(String sourcePath) {
        String sourceCrc = crc(readFile(sourcePath));
        String fileName = new File(sourcePath).getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.getProperty("java.io.tmpdir"));
        stringBuilder.append("/libgdx");
        stringBuilder.append(System.getProperty("user.name"));
        stringBuilder.append("/");
        stringBuilder.append(sourceCrc);
        Throwable ex = loadFile(sourcePath, sourceCrc, new File(stringBuilder.toString(), fileName));
        if (ex != null) {
            try {
                File file = File.createTempFile(sourceCrc, null);
                if (!file.delete() || loadFile(sourcePath, sourceCrc, file) != null) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(System.getProperty("user.home"));
                    stringBuilder2.append("/.libgdx/");
                    stringBuilder2.append(sourceCrc);
                    if (loadFile(sourcePath, sourceCrc, new File(stringBuilder2.toString(), fileName)) != null) {
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(".temp/");
                        stringBuilder2.append(sourceCrc);
                        if (loadFile(sourcePath, sourceCrc, new File(stringBuilder2.toString(), fileName)) != null) {
                            file = new File(System.getProperty("java.library.path"), sourcePath);
                            if (file.exists()) {
                                System.load(file.getAbsolutePath());
                                return;
                            }
                            throw new GdxRuntimeException(ex);
                        }
                    }
                }
            } catch (Throwable th) {
            }
        }
    }

    private Throwable loadFile(String sourcePath, String sourceCrc, File extractedFile) {
        try {
            System.load(extractFile(sourcePath, sourceCrc, extractedFile).getAbsolutePath());
            return null;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return ex;
        }
    }
}
