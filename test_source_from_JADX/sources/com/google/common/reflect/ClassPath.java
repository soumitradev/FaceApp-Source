package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.Attributes.Name;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;

@Beta
public final class ClassPath {
    private static final String CLASS_FILE_NAME_EXTENSION = ".class";
    private static final Splitter CLASS_PATH_ATTRIBUTE_SEPARATOR = Splitter.on(FormatHelper.SPACE).omitEmptyStrings();
    private static final Predicate<ClassInfo> IS_TOP_LEVEL = new C10001();
    private static final Logger logger = Logger.getLogger(ClassPath.class.getName());
    private final ImmutableSet<ResourceInfo> resources;

    @Beta
    public static class ResourceInfo {
        final ClassLoader loader;
        private final String resourceName;

        static ResourceInfo of(String resourceName, ClassLoader loader) {
            if (resourceName.endsWith(ClassPath.CLASS_FILE_NAME_EXTENSION)) {
                return new ClassInfo(resourceName, loader);
            }
            return new ResourceInfo(resourceName, loader);
        }

        ResourceInfo(String resourceName, ClassLoader loader) {
            this.resourceName = (String) Preconditions.checkNotNull(resourceName);
            this.loader = (ClassLoader) Preconditions.checkNotNull(loader);
        }

        public final URL url() throws NoSuchElementException {
            URL url = this.loader.getResource(this.resourceName);
            if (url != null) {
                return url;
            }
            throw new NoSuchElementException(this.resourceName);
        }

        public final String getResourceName() {
            return this.resourceName;
        }

        public int hashCode() {
            return this.resourceName.hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof ResourceInfo)) {
                return false;
            }
            ResourceInfo that = (ResourceInfo) obj;
            if (this.resourceName.equals(that.resourceName) && this.loader == that.loader) {
                z = true;
            }
            return z;
        }

        public String toString() {
            return this.resourceName;
        }
    }

    static abstract class Scanner {
        private final Set<File> scannedUris = Sets.newHashSet();

        protected abstract void scanDirectory(ClassLoader classLoader, File file) throws IOException;

        protected abstract void scanJarFile(ClassLoader classLoader, JarFile jarFile) throws IOException;

        Scanner() {
        }

        public final void scan(ClassLoader classloader) throws IOException {
            Iterator i$ = getClassPathEntries(classloader).entrySet().iterator();
            while (i$.hasNext()) {
                Entry<File, ClassLoader> entry = (Entry) i$.next();
                scan((File) entry.getKey(), (ClassLoader) entry.getValue());
            }
        }

        @VisibleForTesting
        final void scan(File file, ClassLoader classloader) throws IOException {
            if (this.scannedUris.add(file.getCanonicalFile())) {
                scanFrom(file, classloader);
            }
        }

        private void scanFrom(File file, ClassLoader classloader) throws IOException {
            if (file.exists()) {
                if (file.isDirectory()) {
                    scanDirectory(classloader, file);
                } else {
                    scanJar(file, classloader);
                }
            }
        }

        private void scanJar(File file, ClassLoader classloader) throws IOException {
            try {
                JarFile jarFile = new JarFile(file);
                try {
                    Iterator i$ = getClassPathFromManifest(file, jarFile.getManifest()).iterator();
                    while (i$.hasNext()) {
                        scan((File) i$.next(), classloader);
                    }
                    scanJarFile(classloader, jarFile);
                } finally {
                    try {
                        jarFile.close();
                    } catch (IOException e) {
                    }
                }
            } catch (IOException e2) {
            }
        }

        @VisibleForTesting
        static ImmutableSet<File> getClassPathFromManifest(File jarFile, @Nullable Manifest manifest) {
            if (manifest == null) {
                return ImmutableSet.of();
            }
            Builder<File> builder = ImmutableSet.builder();
            String classpathAttribute = manifest.getMainAttributes().getValue(Name.CLASS_PATH.toString());
            if (classpathAttribute != null) {
                for (String path : ClassPath.CLASS_PATH_ATTRIBUTE_SEPARATOR.split(classpathAttribute)) {
                    try {
                        URL url = getClassPathEntry(jarFile, path);
                        if (url.getProtocol().equals(PocketMusicActivity.ABSOLUTE_FILE_PATH)) {
                            builder.add(new File(url.getFile()));
                        }
                    } catch (MalformedURLException e) {
                        Logger access$200 = ClassPath.logger;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Invalid Class-Path entry: ");
                        stringBuilder.append(path);
                        access$200.warning(stringBuilder.toString());
                    }
                }
            }
            return builder.build();
        }

        @VisibleForTesting
        static ImmutableMap<File, ClassLoader> getClassPathEntries(ClassLoader classloader) {
            LinkedHashMap<File, ClassLoader> entries = Maps.newLinkedHashMap();
            ClassLoader parent = classloader.getParent();
            if (parent != null) {
                entries.putAll(getClassPathEntries(parent));
            }
            if (classloader instanceof URLClassLoader) {
                for (URL entry : ((URLClassLoader) classloader).getURLs()) {
                    if (entry.getProtocol().equals(PocketMusicActivity.ABSOLUTE_FILE_PATH)) {
                        File file = new File(entry.getFile());
                        if (!entries.containsKey(file)) {
                            entries.put(file, classloader);
                        }
                    }
                }
            }
            return ImmutableMap.copyOf(entries);
        }

        @VisibleForTesting
        static URL getClassPathEntry(File jarFile, String path) throws MalformedURLException {
            return new URL(jarFile.toURI().toURL(), path);
        }
    }

    /* renamed from: com.google.common.reflect.ClassPath$1 */
    static class C10001 implements Predicate<ClassInfo> {
        C10001() {
        }

        public boolean apply(ClassInfo info) {
            return info.className.indexOf(36) == -1;
        }
    }

    @Beta
    public static final class ClassInfo extends ResourceInfo {
        private final String className;

        ClassInfo(String resourceName, ClassLoader loader) {
            super(resourceName, loader);
            this.className = ClassPath.getClassName(resourceName);
        }

        public String getPackageName() {
            return Reflection.getPackageName(this.className);
        }

        public String getSimpleName() {
            int lastDollarSign = this.className.lastIndexOf(36);
            if (lastDollarSign != -1) {
                return CharMatcher.DIGIT.trimLeadingFrom(this.className.substring(lastDollarSign + 1));
            }
            String packageName = getPackageName();
            if (packageName.isEmpty()) {
                return this.className;
            }
            return this.className.substring(packageName.length() + 1);
        }

        public String getName() {
            return this.className;
        }

        public Class<?> load() {
            try {
                return this.loader.loadClass(this.className);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }

        public String toString() {
            return this.className;
        }
    }

    @VisibleForTesting
    static final class DefaultScanner extends Scanner {
        private final SetMultimap<ClassLoader, String> resources = MultimapBuilder.hashKeys().linkedHashSetValues().build();

        DefaultScanner() {
        }

        ImmutableSet<ResourceInfo> getResources() {
            Builder<ResourceInfo> builder = ImmutableSet.builder();
            for (Entry<ClassLoader, String> entry : this.resources.entries()) {
                builder.add(ResourceInfo.of((String) entry.getValue(), (ClassLoader) entry.getKey()));
            }
            return builder.build();
        }

        protected void scanJarFile(ClassLoader classloader, JarFile file) {
            Enumeration<JarEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                if (!entry.isDirectory()) {
                    if (!entry.getName().equals("META-INF/MANIFEST.MF")) {
                        this.resources.get(classloader).add(entry.getName());
                    }
                }
            }
        }

        protected void scanDirectory(ClassLoader classloader, File directory) throws IOException {
            scanDirectory(directory, classloader, "");
        }

        private void scanDirectory(File directory, ClassLoader classloader, String packagePrefix) throws IOException {
            File[] files = directory.listFiles();
            if (files == null) {
                Logger access$200 = ClassPath.logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot read directory ");
                stringBuilder.append(directory);
                access$200.warning(stringBuilder.toString());
                return;
            }
            for (File f : files) {
                String name = f.getName();
                if (f.isDirectory()) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(packagePrefix);
                    stringBuilder2.append(name);
                    stringBuilder2.append("/");
                    scanDirectory(f, classloader, stringBuilder2.toString());
                } else {
                    String resourceName = new StringBuilder();
                    resourceName.append(packagePrefix);
                    resourceName.append(name);
                    resourceName = resourceName.toString();
                    if (!resourceName.equals("META-INF/MANIFEST.MF")) {
                        this.resources.get(classloader).add(resourceName);
                    }
                }
            }
        }
    }

    private ClassPath(ImmutableSet<ResourceInfo> resources) {
        this.resources = resources;
    }

    public static ClassPath from(ClassLoader classloader) throws IOException {
        DefaultScanner scanner = new DefaultScanner();
        scanner.scan(classloader);
        return new ClassPath(scanner.getResources());
    }

    public ImmutableSet<ResourceInfo> getResources() {
        return this.resources;
    }

    public ImmutableSet<ClassInfo> getAllClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).filter(IS_TOP_LEVEL).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses(String packageName) {
        Preconditions.checkNotNull(packageName);
        Builder<ClassInfo> builder = ImmutableSet.builder();
        Iterator i$ = getTopLevelClasses().iterator();
        while (i$.hasNext()) {
            Object classInfo = (ClassInfo) i$.next();
            if (classInfo.getPackageName().equals(packageName)) {
                builder.add(classInfo);
            }
        }
        return builder.build();
    }

    public ImmutableSet<ClassInfo> getTopLevelClassesRecursive(String packageName) {
        Preconditions.checkNotNull(packageName);
        String packagePrefix = new StringBuilder();
        packagePrefix.append(packageName);
        packagePrefix.append('.');
        packagePrefix = packagePrefix.toString();
        Builder<ClassInfo> builder = ImmutableSet.builder();
        Iterator i$ = getTopLevelClasses().iterator();
        while (i$.hasNext()) {
            Object classInfo = (ClassInfo) i$.next();
            if (classInfo.getName().startsWith(packagePrefix)) {
                builder.add(classInfo);
            }
        }
        return builder.build();
    }

    @VisibleForTesting
    static String getClassName(String filename) {
        return filename.substring(0, filename.length() - CLASS_FILE_NAME_EXTENSION.length()).replace('/', '.');
    }
}
