package org.catrobat.catroid.utils;

import java.io.File;
import org.catrobat.catroid.common.FlavoredConstants;

public final class PathBuilder {
    private PathBuilder() {
        throw new AssertionError();
    }

    public static String buildPath(String... elements) {
        StringBuilder result = new StringBuilder("/");
        for (String pathElement : elements) {
            result.append(pathElement);
            result.append('/');
        }
        String returnValue = result.toString().replaceAll("/+", "/");
        if (returnValue.endsWith("/")) {
            return returnValue.substring(0, returnValue.length() - 1);
        }
        return returnValue;
    }

    public static String buildProjectPath(String projectName) {
        return new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(projectName)).getAbsolutePath();
    }

    public static String buildScenePath(String projectName, String sceneName) {
        return buildPath(buildProjectPath(projectName), FileMetaDataExtractor.encodeSpecialCharsForFileSystem(sceneName));
    }
}
