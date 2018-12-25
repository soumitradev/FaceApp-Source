package android.support.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.util.Log;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;
import org.catrobat.catroid.transfers.MediaDownloadService;

public final class MultiDex {
    private static final String CODE_CACHE_NAME = "code_cache";
    private static final String CODE_CACHE_SECONDARY_FOLDER_NAME = "secondary-dexes";
    private static final boolean IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"));
    private static final int MAX_SUPPORTED_SDK_VERSION = 20;
    private static final int MIN_SDK_VERSION = 4;
    private static final String NO_KEY_PREFIX = "";
    private static final String OLD_SECONDARY_FOLDER_NAME = "secondary-dexes";
    static final String TAG = "MultiDex";
    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;
    private static final Set<File> installedApk = new HashSet();

    private static final class V14 {
        private static final int EXTRACTED_SUFFIX_LENGTH = ".zip".length();
        private final ElementConstructor elementConstructor;

        private interface ElementConstructor {
            Object newInstance(File file, DexFile dexFile) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException;
        }

        private static class ICSElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            ICSElementConstructor(Class<?> elementClass) throws SecurityException, NoSuchMethodException {
                this.elementConstructor = elementClass.getConstructor(new Class[]{File.class, ZipFile.class, DexFile.class});
                this.elementConstructor.setAccessible(true);
            }

            public Object newInstance(File file, DexFile dex) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
                return this.elementConstructor.newInstance(new Object[]{file, new ZipFile(file), dex});
            }
        }

        private static class JBMR11ElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            JBMR11ElementConstructor(Class<?> elementClass) throws SecurityException, NoSuchMethodException {
                this.elementConstructor = elementClass.getConstructor(new Class[]{File.class, File.class, DexFile.class});
                this.elementConstructor.setAccessible(true);
            }

            public Object newInstance(File file, DexFile dex) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
                return this.elementConstructor.newInstance(new Object[]{file, file, dex});
            }
        }

        private static class JBMR2ElementConstructor implements ElementConstructor {
            private final Constructor<?> elementConstructor;

            JBMR2ElementConstructor(Class<?> elementClass) throws SecurityException, NoSuchMethodException {
                this.elementConstructor = elementClass.getConstructor(new Class[]{File.class, Boolean.TYPE, File.class, DexFile.class});
                this.elementConstructor.setAccessible(true);
            }

            public Object newInstance(File file, DexFile dex) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
                return this.elementConstructor.newInstance(new Object[]{file, Boolean.FALSE, file, dex});
            }
        }

        static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries) throws IOException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            Object[] elements = new V14().makeDexElements(additionalClassPathEntries);
            try {
                MultiDex.expandFieldArray(dexPathList, "dexElements", elements);
            } catch (NoSuchFieldException e) {
                Log.w(MultiDex.TAG, "Failed find field 'dexElements' attempting 'pathElements'", e);
                MultiDex.expandFieldArray(dexPathList, "pathElements", elements);
            }
        }

        private V14() throws ClassNotFoundException, SecurityException, NoSuchMethodException {
            ElementConstructor constructor;
            Class<?> elementClass = Class.forName("dalvik.system.DexPathList$Element");
            try {
                constructor = new ICSElementConstructor(elementClass);
            } catch (NoSuchMethodException e) {
                try {
                    constructor = new JBMR11ElementConstructor(elementClass);
                } catch (NoSuchMethodException e2) {
                    constructor = new JBMR2ElementConstructor(elementClass);
                }
            }
            this.elementConstructor = constructor;
        }

        private Object[] makeDexElements(List<? extends File> files) throws IOException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
            Object[] elements = new Object[files.size()];
            for (int i = 0; i < elements.length; i++) {
                File file = (File) files.get(i);
                elements[i] = this.elementConstructor.newInstance(file, DexFile.loadDex(file.getPath(), optimizedPathFor(file), 0));
            }
            return elements;
        }

        private static String optimizedPathFor(File path) {
            File optimizedDirectory = path.getParentFile();
            String fileName = path.getName();
            String optimizedFileName = new StringBuilder();
            optimizedFileName.append(fileName.substring(0, fileName.length() - EXTRACTED_SUFFIX_LENGTH));
            optimizedFileName.append(".dex");
            return new File(optimizedDirectory, optimizedFileName.toString()).getPath();
        }
    }

    private static final class V19 {
        private V19() {
        }

        static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
            Object dexPathList = MultiDex.findField(loader, "pathList").get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList();
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                Iterator it = suppressedExceptions.iterator();
                while (it.hasNext()) {
                    Log.w(MultiDex.TAG, "Exception in makeDexElement", (IOException) it.next());
                }
                Field suppressedExceptionsField = MultiDex.findField(dexPathList, "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions = (IOException[]) suppressedExceptionsField.get(dexPathList);
                if (dexElementsSuppressedExceptions == null) {
                    dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[(suppressedExceptions.size() + dexElementsSuppressedExceptions.length)];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions, 0, combined, suppressedExceptions.size(), dexElementsSuppressedExceptions.length);
                    dexElementsSuppressedExceptions = combined;
                }
                suppressedExceptionsField.set(dexPathList, dexElementsSuppressedExceptions);
                IOException exception = new IOException("I/O exception during makeDexElement");
                exception.initCause((Throwable) suppressedExceptions.get(0));
                throw exception;
            }
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
        }
    }

    private static final class V4 {
        private V4() {
        }

        static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int extraSize = additionalClassPathEntries.size();
            Field pathField = MultiDex.findField(loader, MediaDownloadService.MEDIA_FILE_PATH);
            StringBuilder path = new StringBuilder((String) pathField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            ListIterator<? extends File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = (File) iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                path.append(':');
                path.append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                extraZips[index] = new ZipFile(additionalEntry);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(entryPath);
                stringBuilder.append(".dex");
                extraDexs[index] = DexFile.loadDex(entryPath, stringBuilder.toString(), 0);
            }
            pathField.set(loader, path.toString());
            MultiDex.expandFieldArray(loader, "mPaths", extraPaths);
            MultiDex.expandFieldArray(loader, "mFiles", extraFiles);
            MultiDex.expandFieldArray(loader, "mZips", extraZips);
            MultiDex.expandFieldArray(loader, "mDexs", extraDexs);
        }
    }

    private MultiDex() {
    }

    public static void install(Context context) {
        Log.i(TAG, "Installing application");
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i(TAG, "VM has multidex support, MultiDex support library is disabled.");
        } else if (VERSION.SDK_INT < 4) {
            r2 = new StringBuilder();
            r2.append("MultiDex installation failed. SDK ");
            r2.append(VERSION.SDK_INT);
            r2.append(" is unsupported. Min SDK version is ");
            r2.append(4);
            r2.append(".");
            throw new RuntimeException(r2.toString());
        } else {
            try {
                ApplicationInfo applicationInfo = getApplicationInfo(context);
                if (applicationInfo == null) {
                    Log.i(TAG, "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                }
                doInstallation(context, new File(applicationInfo.sourceDir), new File(applicationInfo.dataDir), "secondary-dexes", "", true);
                Log.i(TAG, "install done");
            } catch (Exception e) {
                Log.e(TAG, "MultiDex installation failure", e);
                r2 = new StringBuilder();
                r2.append("MultiDex installation failed (");
                r2.append(e.getMessage());
                r2.append(").");
                throw new RuntimeException(r2.toString());
            }
        }
    }

    public static void installInstrumentation(Context instrumentationContext, Context targetContext) {
        Log.i(TAG, "Installing instrumentation");
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i(TAG, "VM has multidex support, MultiDex support library is disabled.");
        } else if (VERSION.SDK_INT < 4) {
            r3 = new StringBuilder();
            r3.append("MultiDex installation failed. SDK ");
            r3.append(VERSION.SDK_INT);
            r3.append(" is unsupported. Min SDK version is ");
            r3.append(4);
            r3.append(".");
            throw new RuntimeException(r3.toString());
        } else {
            try {
                ApplicationInfo instrumentationInfo = getApplicationInfo(instrumentationContext);
                if (instrumentationInfo == null) {
                    Log.i(TAG, "No ApplicationInfo available for instrumentation, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                }
                ApplicationInfo applicationInfo = getApplicationInfo(targetContext);
                if (applicationInfo == null) {
                    Log.i(TAG, "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                }
                String instrumentationPrefix = new StringBuilder();
                instrumentationPrefix.append(instrumentationContext.getPackageName());
                instrumentationPrefix.append(".");
                instrumentationPrefix = instrumentationPrefix.toString();
                File dataDir = new File(applicationInfo.dataDir);
                File file = new File(instrumentationInfo.sourceDir);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(instrumentationPrefix);
                stringBuilder.append("secondary-dexes");
                doInstallation(targetContext, file, dataDir, stringBuilder.toString(), instrumentationPrefix, false);
                doInstallation(targetContext, new File(applicationInfo.sourceDir), dataDir, "secondary-dexes", "", false);
                Log.i(TAG, "Installation done");
            } catch (Exception e) {
                Exception e2 = e;
                Log.e(TAG, "MultiDex installation failure", e2);
                r3 = new StringBuilder();
                r3.append("MultiDex installation failed (");
                r3.append(e2.getMessage());
                r3.append(").");
                throw new RuntimeException(r3.toString());
            }
        }
    }

    private static void doInstallation(Context mainContext, File sourceApk, File dataDir, String secondaryFolderName, String prefsKeyPrefix, boolean reinstallOnPatchRecoverableException) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException {
        synchronized (installedApk) {
            if (installedApk.contains(sourceApk)) {
                return;
            }
            installedApk.add(sourceApk);
            if (VERSION.SDK_INT > 20) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("MultiDex is not guaranteed to work in SDK version ");
                stringBuilder.append(VERSION.SDK_INT);
                stringBuilder.append(": SDK version higher than ");
                stringBuilder.append(20);
                stringBuilder.append(" should be backed by ");
                stringBuilder.append("runtime with built-in multidex capabilty but it's not the ");
                stringBuilder.append("case here: java.vm.version=\"");
                stringBuilder.append(System.getProperty("java.vm.version"));
                stringBuilder.append("\"");
                Log.w(str, stringBuilder.toString());
            }
            try {
                ClassLoader loader = mainContext.getClassLoader();
                if (loader == null) {
                    Log.e(TAG, "Context class loader is null. Must be running in test mode. Skip patching.");
                    return;
                }
                try {
                    clearOldDexDir(mainContext);
                } catch (Throwable t) {
                    Log.w(TAG, "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.", t);
                }
                File dexDir = getDexDir(mainContext, dataDir, secondaryFolderName);
                MultiDexExtractor extractor = new MultiDexExtractor(sourceApk, dexDir);
                IOException closeException = null;
                try {
                    installSecondaryDexes(loader, dexDir, extractor.load(mainContext, prefsKeyPrefix, null));
                } catch (IOException e) {
                    if (reinstallOnPatchRecoverableException) {
                        Log.w(TAG, "Failed to install extracted secondary dex files, retrying with forced extraction", e);
                        installSecondaryDexes(loader, dexDir, extractor.load(mainContext, prefsKeyPrefix, true));
                    } else {
                        throw e;
                    }
                } catch (Throwable th) {
                    try {
                        extractor.close();
                    } catch (IOException e2) {
                        closeException = e2;
                    }
                }
                try {
                    extractor.close();
                } catch (IOException e3) {
                    closeException = e3;
                }
                if (closeException != null) {
                    throw closeException;
                }
            } catch (RuntimeException e4) {
                Log.w(TAG, "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", e4);
            }
        }
    }

    private static ApplicationInfo getApplicationInfo(Context context) {
        try {
            return context.getApplicationInfo();
        } catch (RuntimeException e) {
            Log.w(TAG, "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    static boolean isVMMultidexCapable(String versionString) {
        boolean isMultidexCapable = false;
        if (versionString != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
            if (matcher.matches()) {
                boolean z = true;
                try {
                    int major = Integer.parseInt(matcher.group(1));
                    int minor = Integer.parseInt(matcher.group(2));
                    if (major <= 2) {
                        if (major != 2 || minor < 1) {
                            z = false;
                        }
                    }
                    isMultidexCapable = z;
                } catch (NumberFormatException e) {
                }
            }
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VM with version ");
        stringBuilder.append(versionString);
        stringBuilder.append(isMultidexCapable ? " has multidex support" : " does not have multidex support");
        Log.i(str, stringBuilder.toString());
        return isMultidexCapable;
    }

    private static void installSecondaryDexes(ClassLoader loader, File dexDir, List<? extends File> files) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException, SecurityException, ClassNotFoundException, InstantiationException {
        if (!files.isEmpty()) {
            if (VERSION.SDK_INT >= 19) {
                V19.install(loader, files, dexDir);
            } else if (VERSION.SDK_INT >= 14) {
                V14.install(loader, files);
            } else {
                V4.install(loader, files);
            }
        }
    }

    private static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Field ");
        stringBuilder.append(name);
        stringBuilder.append(" not found in ");
        stringBuilder.append(instance.getClass());
        throw new NoSuchFieldException(stringBuilder.toString());
    }

    private static Method findMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Method ");
        stringBuilder.append(name);
        stringBuilder.append(" with parameters ");
        stringBuilder.append(Arrays.asList(parameterTypes));
        stringBuilder.append(" not found in ");
        stringBuilder.append(instance.getClass());
        throw new NoSuchMethodException(stringBuilder.toString());
    }

    private static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }

    private static void clearOldDexDir(Context context) throws Exception {
        File dexDir = new File(context.getFilesDir(), "secondary-dexes");
        if (dexDir.isDirectory()) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Clearing old secondary dex dir (");
            stringBuilder.append(dexDir.getPath());
            stringBuilder.append(").");
            Log.i(str, stringBuilder.toString());
            File[] files = dexDir.listFiles();
            String str2;
            StringBuilder stringBuilder2;
            if (files == null) {
                str2 = TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Failed to list secondary dex dir content (");
                stringBuilder2.append(dexDir.getPath());
                stringBuilder2.append(").");
                Log.w(str2, stringBuilder2.toString());
                return;
            }
            for (File oldFile : files) {
                String str3 = TAG;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Trying to delete old file ");
                stringBuilder3.append(oldFile.getPath());
                stringBuilder3.append(" of size ");
                stringBuilder3.append(oldFile.length());
                Log.i(str3, stringBuilder3.toString());
                if (oldFile.delete()) {
                    str3 = TAG;
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Deleted old file ");
                    stringBuilder3.append(oldFile.getPath());
                    Log.i(str3, stringBuilder3.toString());
                } else {
                    str3 = TAG;
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Failed to delete old file ");
                    stringBuilder3.append(oldFile.getPath());
                    Log.w(str3, stringBuilder3.toString());
                }
            }
            if (dexDir.delete()) {
                str2 = TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Deleted old secondary dex dir ");
                stringBuilder2.append(dexDir.getPath());
                Log.i(str2, stringBuilder2.toString());
            } else {
                str2 = TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Failed to delete secondary dex dir ");
                stringBuilder2.append(dexDir.getPath());
                Log.w(str2, stringBuilder2.toString());
            }
        }
    }

    private static File getDexDir(Context context, File dataDir, String secondaryFolderName) throws IOException {
        File cache = new File(dataDir, CODE_CACHE_NAME);
        try {
            mkdirChecked(cache);
        } catch (IOException e) {
            cache = new File(context.getFilesDir(), CODE_CACHE_NAME);
            mkdirChecked(cache);
        }
        File dexDir = new File(cache, secondaryFolderName);
        mkdirChecked(dexDir);
        return dexDir;
    }

    private static void mkdirChecked(File dir) throws IOException {
        dir.mkdir();
        if (!dir.isDirectory()) {
            StringBuilder stringBuilder;
            File parent = dir.getParentFile();
            String str;
            if (parent == null) {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to create dir ");
                stringBuilder.append(dir.getPath());
                stringBuilder.append(". Parent file is null.");
                Log.e(str, stringBuilder.toString());
            } else {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to create dir ");
                stringBuilder.append(dir.getPath());
                stringBuilder.append(". parent file is a dir ");
                stringBuilder.append(parent.isDirectory());
                stringBuilder.append(", a file ");
                stringBuilder.append(parent.isFile());
                stringBuilder.append(", exists ");
                stringBuilder.append(parent.exists());
                stringBuilder.append(", readable ");
                stringBuilder.append(parent.canRead());
                stringBuilder.append(", writable ");
                stringBuilder.append(parent.canWrite());
                Log.e(str, stringBuilder.toString());
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to create directory ");
            stringBuilder.append(dir.getPath());
            throw new IOException(stringBuilder.toString());
        }
    }
}
