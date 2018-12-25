package com.parrot.freeflight.utils;

import android.os.Build;
import android.os.Environment;
import java.io.File;

public final class NookUtils {
    private NookUtils() {
    }

    public static boolean isNook() {
        if (Build.BRAND.equalsIgnoreCase("nook")) {
            return true;
        }
        return false;
    }

    public static File getMediaFolder() {
        File result = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (FileUtils.isExtStorgAvailable()) {
            return result;
        }
        return new File("/mnt/media/");
    }
}
