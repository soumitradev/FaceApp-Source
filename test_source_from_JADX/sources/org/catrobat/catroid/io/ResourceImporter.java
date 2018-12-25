package org.catrobat.catroid.io;

import android.content.res.Resources;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.utils.ImageEditing;

public final class ResourceImporter {
    private ResourceImporter() {
        throw new AssertionError();
    }

    public static File createImageFileFromResourcesInDirectory(Resources resources, int resourceId, File dstDir, String fileName, double scaleFactor) throws IOException {
        if (scaleFactor <= BrickValues.SET_COLOR_TO) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("scaleFactor was: ");
            stringBuilder.append(scaleFactor);
            stringBuilder.append(", it has to be > 0.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        File file = StorageOperations.copyStreamToDir(resources.openRawResource(resourceId), dstDir, fileName);
        if (scaleFactor != 1.0d) {
            ImageEditing.scaleImageFile(file, scaleFactor);
        }
        return file;
    }

    public static File createSoundFileFromResourcesInDirectory(Resources resources, int resourceId, File dstDir, String fileName) throws IOException {
        return StorageOperations.copyStreamToDir(resources.openRawResource(resourceId), dstDir, fileName);
    }
}
