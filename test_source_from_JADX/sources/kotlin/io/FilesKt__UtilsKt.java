package kotlin.io;

import com.facebook.internal.FacebookRequestErrorClassification;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.transfers.MediaDownloadService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u001a(\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a(\u0010\r\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a8\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013\u001a&\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u000f*\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0002*\u00020\u0002\u001a\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0002¢\u0006\u0002\b\u001e\u001a\u0011\u0010\u001c\u001a\u00020\u001f*\u00020\u001fH\u0002¢\u0006\u0002\b\u001e\u001a\u0012\u0010 \u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0014\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u001b\u0010)\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002H\u0002¢\u0006\u0002\b*\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004¨\u0006+"}, d2 = {"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"}, k = 5, mv = {1, 1, 10}, xi = 1, xs = "kotlin/io/FilesKt")
/* compiled from: Utils.kt */
class FilesKt__UtilsKt extends FilesKt__FileTreeWalkKt {
    @NotNull
    public static /* synthetic */ File createTempDir$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != null) {
            str = "tmp";
        }
        if ((i & 2) != null) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            file = null;
        }
        return createTempDir(str, str2, file);
    }

    @NotNull
    public static final File createTempDir(@NotNull String prefix, @Nullable String suffix, @Nullable File directory) {
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        File dir = File.createTempFile(prefix, suffix, directory);
        dir.delete();
        if (dir.mkdir()) {
            Intrinsics.checkExpressionValueIsNotNull(dir, "dir");
            return dir;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to create temporary directory ");
        stringBuilder.append(dir);
        stringBuilder.append('.');
        throw new IOException(stringBuilder.toString());
    }

    @NotNull
    public static /* synthetic */ File createTempFile$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != null) {
            str = "tmp";
        }
        if ((i & 2) != null) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            file = null;
        }
        return createTempFile(str, str2, file);
    }

    @NotNull
    public static final File createTempFile(@NotNull String prefix, @Nullable String suffix, @Nullable File directory) {
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        File createTempFile = File.createTempFile(prefix, suffix, directory);
        Intrinsics.checkExpressionValueIsNotNull(createTempFile, "File.createTempFile(prefix, suffix, directory)");
        return createTempFile;
    }

    @NotNull
    public static final String getExtension(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        String name = $receiver.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "name");
        return StringsKt__StringsKt.substringAfterLast(name, '.', "");
    }

    @NotNull
    public static final String getInvariantSeparatorsPath(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if (File.separatorChar != '/') {
            String path = $receiver.getPath();
            Intrinsics.checkExpressionValueIsNotNull(path, MediaDownloadService.MEDIA_FILE_PATH);
            return StringsKt__StringsJVMKt.replace$default(path, File.separatorChar, '/', false, 4, null);
        }
        String path2 = $receiver.getPath();
        Intrinsics.checkExpressionValueIsNotNull(path2, MediaDownloadService.MEDIA_FILE_PATH);
        return path2;
    }

    @NotNull
    public static final String getNameWithoutExtension(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        String name = $receiver.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "name");
        return StringsKt__StringsKt.substringBeforeLast$default(name, ".", null, 2, null);
    }

    @NotNull
    public static final String toRelativeString(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String toRelativeStringOrNull$FilesKt__UtilsKt = toRelativeStringOrNull$FilesKt__UtilsKt($receiver, base);
        if (toRelativeStringOrNull$FilesKt__UtilsKt != null) {
            return toRelativeStringOrNull$FilesKt__UtilsKt;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("this and base files have different roots: ");
        stringBuilder.append($receiver);
        stringBuilder.append(" and ");
        stringBuilder.append(base);
        stringBuilder.append('.');
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @NotNull
    public static final File relativeTo(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        return new File(toRelativeString($receiver, base));
    }

    @NotNull
    public static final File relativeToOrSelf(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String p1 = toRelativeStringOrNull$FilesKt__UtilsKt($receiver, base);
        return p1 != null ? new File(p1) : $receiver;
    }

    @Nullable
    public static final File relativeToOrNull(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String p1 = toRelativeStringOrNull$FilesKt__UtilsKt($receiver, base);
        return p1 != null ? new File(p1) : null;
    }

    private static final String toRelativeStringOrNull$FilesKt__UtilsKt(@NotNull File $receiver, File base) {
        FilePathComponents thisComponents = normalize$FilesKt__UtilsKt(FilesKt__FilePathComponentsKt.toComponents($receiver));
        FilePathComponents baseComponents = normalize$FilesKt__UtilsKt(FilesKt__FilePathComponentsKt.toComponents(base));
        if ((Intrinsics.areEqual(thisComponents.getRoot(), baseComponents.getRoot()) ^ 1) != 0) {
            return null;
        }
        int baseCount = baseComponents.getSize();
        int thisCount = thisComponents.getSize();
        int i = 0;
        int maxSameCount = Math.min(thisCount, baseCount);
        while (i < maxSameCount && Intrinsics.areEqual((File) thisComponents.getSegments().get(i), (File) baseComponents.getSegments().get(i))) {
            i++;
        }
        int sameCount = i;
        StringBuilder res = new StringBuilder();
        maxSameCount = baseCount - 1;
        if (maxSameCount >= sameCount) {
            while (!Intrinsics.areEqual(((File) baseComponents.getSegments().get(maxSameCount)).getName(), "..")) {
                res.append("..");
                if (maxSameCount != sameCount) {
                    res.append(File.separatorChar);
                }
                if (maxSameCount != sameCount) {
                    maxSameCount--;
                }
            }
            return null;
        }
        if (sameCount < thisCount) {
            if (sameCount < baseCount) {
                res.append(File.separatorChar);
            }
            Iterable drop = CollectionsKt___CollectionsKt.drop(thisComponents.getSegments(), sameCount);
            Appendable appendable = res;
            String str = File.separator;
            Intrinsics.checkExpressionValueIsNotNull(str, "File.separator");
            CollectionsKt___CollectionsKt.joinTo$default(drop, appendable, str, null, null, 0, null, null, 124, null);
        }
        return res.toString();
    }

    @NotNull
    public static /* synthetic */ File copyTo$default(File file, File file2, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != null) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 8192;
        }
        return copyTo(file, file2, z, i);
    }

    @NotNull
    public static final File copyTo(@NotNull File $receiver, @NotNull File target, boolean overwrite, int bufferSize) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(target, "target");
        if ($receiver.exists()) {
            if (target.exists()) {
                boolean stillExists = true;
                if (overwrite) {
                    if (target.delete()) {
                        stillExists = false;
                        if (stillExists) {
                            throw ((Throwable) new FileAlreadyExistsException($receiver, target, "The destination file already exists."));
                        }
                    }
                }
                if (stillExists) {
                    throw ((Throwable) new FileAlreadyExistsException($receiver, target, "The destination file already exists."));
                }
            }
            if (!$receiver.isDirectory()) {
                File parentFile = target.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                Closeable fileInputStream = new FileInputStream($receiver);
                Throwable th = (Throwable) null;
                Closeable fileOutputStream;
                try {
                    int $i$a$1$use = 0;
                    fileOutputStream = new FileOutputStream(target);
                    Throwable th2 = (Throwable) null;
                    ByteStreamsKt.copyTo((FileInputStream) fileInputStream, (FileOutputStream) fileOutputStream, bufferSize);
                    CloseableKt.closeFinally(fileOutputStream, th2);
                    CloseableKt.closeFinally(fileInputStream, th);
                } catch (Throwable th3) {
                    try {
                    } catch (Throwable th4) {
                        CloseableKt.closeFinally(fileInputStream, th3);
                    }
                }
            } else if (!target.mkdirs()) {
                throw new FileSystemException($receiver, target, "Failed to create target directory.");
            }
            return target;
        }
        throw new NoSuchFileException($receiver, null, "The source file doesn't exist.", 2, null);
    }

    public static /* synthetic */ boolean copyRecursively$default(File file, File file2, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 2) != null) {
            z = false;
        }
        if ((i & 4) != 0) {
            function2 = FilesKt__UtilsKt$copyRecursively$1.INSTANCE;
        }
        return copyRecursively(file, file2, z, function2);
    }

    public static final boolean copyRecursively(@NotNull File $receiver, @NotNull File target, boolean overwrite, @NotNull Function2<? super File, ? super IOException, ? extends OnErrorAction> onError) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(target, "target");
        Intrinsics.checkParameterIsNotNull(onError, "onError");
        boolean z = true;
        if ($receiver.exists()) {
            try {
                Iterator it = FilesKt__FileTreeWalkKt.walkTopDown($receiver).onFail(new FilesKt__UtilsKt$copyRecursively$2(onError)).iterator();
                while (it.hasNext()) {
                    File src = (File) it.next();
                    if (src.exists()) {
                        File dstFile = new File(target, toRelativeString(src, $receiver));
                        if (dstFile.exists() && !(src.isDirectory() && dstFile.isDirectory())) {
                            boolean stillExists;
                            if (overwrite) {
                                if (dstFile.isDirectory()) {
                                    if (!deleteRecursively(dstFile)) {
                                    }
                                } else if (dstFile.delete()) {
                                }
                                stillExists = false;
                                if (stillExists) {
                                    if (((OnErrorAction) onError.invoke(dstFile, new FileAlreadyExistsException(src, dstFile, "The destination file already exists."))) == OnErrorAction.TERMINATE) {
                                        return false;
                                    }
                                }
                            }
                            stillExists = true;
                            if (stillExists) {
                                if (((OnErrorAction) onError.invoke(dstFile, new FileAlreadyExistsException(src, dstFile, "The destination file already exists."))) == OnErrorAction.TERMINATE) {
                                    return false;
                                }
                            }
                        }
                        if (src.isDirectory()) {
                            dstFile.mkdirs();
                        } else if (copyTo$default(src, dstFile, overwrite, 0, 4, null).length() != src.length() && ((OnErrorAction) onError.invoke(src, new IOException("Source file wasn't copied completely, length of destination file differs."))) == OnErrorAction.TERMINATE) {
                            return false;
                        }
                    }
                    if (((OnErrorAction) onError.invoke(src, new NoSuchFileException(src, null, "The source file doesn't exist.", 2, null))) == OnErrorAction.TERMINATE) {
                        return false;
                    }
                }
                return true;
            } catch (TerminateException e) {
                return false;
            }
        }
        if (((OnErrorAction) onError.invoke($receiver, new NoSuchFileException($receiver, null, "The source file doesn't exist.", 2, null))) == OnErrorAction.TERMINATE) {
            z = false;
        }
        return z;
    }

    public static final boolean deleteRecursively(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        boolean accumulator$iv = true;
        for (File it : FilesKt__FileTreeWalkKt.walkBottomUp($receiver)) {
            boolean z = (it.delete() || !it.exists()) && accumulator$iv;
            accumulator$iv = z;
        }
        return accumulator$iv;
    }

    public static final boolean startsWith(@NotNull File $receiver, @NotNull File other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FacebookRequestErrorClassification.KEY_OTHER);
        FilePathComponents components = FilesKt__FilePathComponentsKt.toComponents($receiver);
        FilePathComponents otherComponents = FilesKt__FilePathComponentsKt.toComponents(other);
        boolean z = false;
        if ((Intrinsics.areEqual(components.getRoot(), otherComponents.getRoot()) ^ 1) != 0) {
            return false;
        }
        if (components.getSize() >= otherComponents.getSize()) {
            z = components.getSegments().subList(0, otherComponents.getSize()).equals(otherComponents.getSegments());
        }
        return z;
    }

    public static final boolean startsWith(@NotNull File $receiver, @NotNull String other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FacebookRequestErrorClassification.KEY_OTHER);
        return startsWith($receiver, new File(other));
    }

    public static final boolean endsWith(@NotNull File $receiver, @NotNull File other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FacebookRequestErrorClassification.KEY_OTHER);
        FilePathComponents components = FilesKt__FilePathComponentsKt.toComponents($receiver);
        FilePathComponents otherComponents = FilesKt__FilePathComponentsKt.toComponents(other);
        if (otherComponents.isRooted()) {
            return Intrinsics.areEqual($receiver, other);
        }
        boolean z;
        int shift = components.getSize() - otherComponents.getSize();
        if (shift < 0) {
            z = false;
        } else {
            z = components.getSegments().subList(shift, components.getSize()).equals(otherComponents.getSegments());
        }
        return z;
    }

    public static final boolean endsWith(@NotNull File $receiver, @NotNull String other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, FacebookRequestErrorClassification.KEY_OTHER);
        return endsWith($receiver, new File(other));
    }

    @NotNull
    public static final File normalize(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        FilePathComponents $receiver2 = FilesKt__FilePathComponentsKt.toComponents($receiver);
        File root = $receiver2.getRoot();
        Iterable normalize$FilesKt__UtilsKt = normalize$FilesKt__UtilsKt($receiver2.getSegments());
        String str = File.separator;
        Intrinsics.checkExpressionValueIsNotNull(str, "File.separator");
        return resolve(root, CollectionsKt___CollectionsKt.joinToString$default(normalize$FilesKt__UtilsKt, str, null, null, 0, null, null, 62, null));
    }

    private static final FilePathComponents normalize$FilesKt__UtilsKt(@NotNull FilePathComponents $receiver) {
        return new FilePathComponents($receiver.getRoot(), normalize$FilesKt__UtilsKt($receiver.getSegments()));
    }

    private static final List<File> normalize$FilesKt__UtilsKt(@NotNull List<? extends File> $receiver) {
        List list = new ArrayList($receiver.size());
        for (File file : $receiver) {
            String name = file.getName();
            if (name != null) {
                int hashCode = name.hashCode();
                if (hashCode != 46) {
                    if (hashCode == 1472) {
                        if (name.equals("..")) {
                            if (list.isEmpty() || (Intrinsics.areEqual(((File) CollectionsKt___CollectionsKt.last(list)).getName(), "..") ^ 1) == 0) {
                                list.add(file);
                            } else {
                                list.remove(list.size() - 1);
                            }
                        }
                    }
                } else if (name.equals(".")) {
                }
            }
            list.add(file);
        }
        return list;
    }

    @NotNull
    public static final File resolve(@NotNull File $receiver, @NotNull File relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        if (FilesKt__FilePathComponentsKt.isRooted(relative)) {
            return relative;
        }
        StringBuilder stringBuilder;
        File file;
        String baseName = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(baseName, "baseName");
        if ((((CharSequence) baseName).length() == 0 ? 1 : null) == null) {
            if (!StringsKt__StringsKt.endsWith$default((CharSequence) baseName, File.separatorChar, false, 2, null)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(baseName);
                stringBuilder.append(File.separatorChar);
                stringBuilder.append(relative);
                file = new File(stringBuilder.toString());
                return file;
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(baseName);
        stringBuilder.append(relative);
        file = new File(stringBuilder.toString());
        return file;
    }

    @NotNull
    public static final File resolve(@NotNull File $receiver, @NotNull String relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        return resolve($receiver, new File(relative));
    }

    @NotNull
    public static final File resolveSibling(@NotNull File $receiver, @NotNull File relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        FilePathComponents components = FilesKt__FilePathComponentsKt.toComponents($receiver);
        return resolve(resolve(components.getRoot(), components.getSize() == 0 ? new File("..") : components.subPath(null, components.getSize() - 1)), relative);
    }

    @NotNull
    public static final File resolveSibling(@NotNull File $receiver, @NotNull String relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        return resolveSibling($receiver, new File(relative));
    }
}
