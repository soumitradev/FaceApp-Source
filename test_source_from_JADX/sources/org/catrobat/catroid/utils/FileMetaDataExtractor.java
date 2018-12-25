package org.catrobat.catroid.utils;

import android.content.Context;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;

public final class FileMetaDataExtractor {

    /* renamed from: org.catrobat.catroid.utils.FileMetaDataExtractor$1 */
    static class C20171 implements FilenameFilter {
        C20171() {
        }

        public boolean accept(File dir, String filename) {
            return filename.contentEquals(Constants.CODE_XML_FILE_NAME);
        }
    }

    private FileMetaDataExtractor() {
        throw new AssertionError();
    }

    private static long getSizeOfFileOrDirectoryInByte(File fileOrDirectory) {
        if (!fileOrDirectory.exists()) {
            return 0;
        }
        if (fileOrDirectory.isFile()) {
            return fileOrDirectory.length();
        }
        File[] contents = fileOrDirectory.listFiles();
        if (contents == null) {
            return 0;
        }
        long size = 0;
        int length = contents.length;
        int i = 0;
        while (i < length) {
            File file = contents[i];
            i++;
            size += file.isDirectory() ? getSizeOfFileOrDirectoryInByte(file) : file.length();
        }
        return size;
    }

    public static long getProgressFromBytes(String projectName, Long progress) {
        long fileByteSize = getSizeOfFileOrDirectoryInByte(new File(PathBuilder.buildProjectPath(projectName)));
        if (fileByteSize == 0) {
            return 0;
        }
        return (progress.longValue() * 100) / fileByteSize;
    }

    public static String getSizeAsString(File fileOrDirectory, Context context) {
        return formatFileSize(getSizeOfFileOrDirectoryInByte(fileOrDirectory), context);
    }

    private static String formatFileSize(long sizeInByte, Context context) {
        long j = sizeInByte;
        int exponent = (int) Math.floor(Math.log((double) j) / Math.log(1024.0d));
        Context context2 = context;
        String unitForDisplay = context2.getString(((Integer) Arrays.asList(new Integer[]{Integer.valueOf(R.string.Byte_short), Integer.valueOf(R.string.KiloByte_short), Integer.valueOf(R.string.MegaByte_short), Integer.valueOf(R.string.GigaByte_short), Integer.valueOf(R.string.TeraByte_short), Integer.valueOf(R.string.PetaByte_short), Integer.valueOf(R.string.ExaByte_short)}).get(exponent)).intValue());
        double sizeForDisplay = ((double) j) / Math.pow(1024.0d, (double) exponent);
        return String.format(Locale.getDefault(), "%.1f %s", new Object[]{Double.valueOf(sizeForDisplay), unitForDisplay});
    }

    public static List<String> getProjectNames(File directory) {
        List<String> projectList = new ArrayList();
        File[] fileList = directory.listFiles();
        if (fileList != null) {
            FilenameFilter filenameFilter = new C20171();
            for (File file : fileList) {
                if (file.isDirectory() && file.list(filenameFilter).length != 0) {
                    projectList.add(decodeSpecialCharsForFileSystem(file.getName()));
                }
            }
        }
        return projectList;
    }

    public static String encodeSpecialCharsForFileSystem(String projectName) {
        if (!projectName.equals(".")) {
            if (!projectName.equals("..")) {
                return projectName.replace("%", "%25").replace("\"", "%22").replace("/", "%2F").replace(":", "%3A").replace("<", "%3C").replace(">", "%3E").replace("?", "%3F").replace("\\", "%5C").replace("|", "%7C").replace("*", "%2A");
            }
        }
        return projectName.replace(".", "%2E");
    }

    public static String decodeSpecialCharsForFileSystem(String projectName) {
        return projectName.replace("%2E", ".").replace("%2A", "*").replace("%7C", "|").replace("%5C", "\\").replace("%3F", "?").replace("%3E", ">").replace("%3C", "<").replace("%3A", ":").replace("%2F", "/").replace("%22", "\"").replace("%25", "%");
    }
}
