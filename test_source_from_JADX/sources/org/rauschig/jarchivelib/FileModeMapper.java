package org.rauschig.jarchivelib;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.commons.compress.archivers.ArchiveEntry;

abstract class FileModeMapper {
    private static final Logger LOG = Logger.getLogger(FileModeMapper.class.getCanonicalName());
    private ArchiveEntry archiveEntry;

    public interface ChmodCommand {
        void chmod(int i, File file) throws Exception;
    }

    public static class FallbackFileModeMapper extends FileModeMapper {
        public /* bridge */ /* synthetic */ ArchiveEntry getArchiveEntry() {
            return super.getArchiveEntry();
        }

        public FallbackFileModeMapper(ArchiveEntry archiveEntry) {
            super(archiveEntry);
        }

        public void map(File file) throws IOException {
        }
    }

    public static class FileSystemPreferencesReflectionChmodCommand implements ChmodCommand {
        private static Method method;

        public void chmod(int mode, File file) throws Exception {
            getMethod().invoke(null, new Object[]{file.getAbsolutePath(), Integer.valueOf(mode)});
        }

        private Method getMethod() throws Exception {
            if (method == null) {
                method = Class.forName("java.util.prefs.FileSystemPreferences").getDeclaredMethod("chmod", new Class[]{String.class, Integer.TYPE});
                method.setAccessible(true);
            }
            return method;
        }
    }

    public static class RuntimeExecChmodCommand implements ChmodCommand {
        public void chmod(int mode, File file) throws Exception {
            String cmd = new StringBuilder();
            cmd.append("chmod ");
            cmd.append(Integer.toOctalString(mode));
            cmd.append(FormatHelper.SPACE);
            cmd.append(file.getAbsolutePath());
            Runtime.getRuntime().exec(cmd.toString());
        }
    }

    public static class UnixPermissionMapper extends FileModeMapper {
        public static final int UNIX_PERMISSION_MASK = 511;

        public /* bridge */ /* synthetic */ ArchiveEntry getArchiveEntry() {
            return super.getArchiveEntry();
        }

        public UnixPermissionMapper(ArchiveEntry archiveEntry) {
            super(archiveEntry);
        }

        public void map(File file) throws IOException {
            int perm = getMode() & 511;
            if (perm > 0) {
                chmod(perm, file);
            }
        }

        public int getMode() throws IOException {
            return AttributeAccessor.create(getArchiveEntry()).getMode();
        }

        public ChmodCommand getChmodCommand() {
            return new FileSystemPreferencesReflectionChmodCommand();
        }

        private void chmod(int mode, File file) throws IOException {
            try {
                getChmodCommand().chmod(mode, file);
            } catch (Exception e) {
                Logger access$000 = FileModeMapper.LOG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not set file permissions of ");
                stringBuilder.append(file);
                stringBuilder.append(". Exception was: ");
                stringBuilder.append(e.getMessage());
                access$000.warning(stringBuilder.toString());
            }
        }
    }

    public abstract void map(File file) throws IOException;

    public FileModeMapper(ArchiveEntry archiveEntry) {
        this.archiveEntry = archiveEntry;
    }

    public ArchiveEntry getArchiveEntry() {
        return this.archiveEntry;
    }

    public static void map(ArchiveEntry entry, File file) throws IOException {
        create(entry).map(file);
    }

    public static FileModeMapper create(ArchiveEntry entry) {
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            return new FallbackFileModeMapper(entry);
        }
        return new UnixPermissionMapper(entry);
    }
}
