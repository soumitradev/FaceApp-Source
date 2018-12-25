package org.catrobat.catroid.common;

import org.catrobat.catroid.content.Project;

public final class ScreenValues {
    public static final int CAST_SCREEN_HEIGHT = 720;
    public static final int CAST_SCREEN_WIDTH = 1280;
    private static final int DEFAULT_SCREEN_HEIGHT = 768;
    private static final int DEFAULT_SCREEN_WIDTH = 1280;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_WIDTH;

    private ScreenValues() {
        throw new AssertionError();
    }

    public static float getAspectRatio() {
        if (SCREEN_WIDTH == 0 || SCREEN_HEIGHT == 0) {
            setToDefaultScreenSize();
        }
        return ((float) SCREEN_WIDTH) / ((float) SCREEN_HEIGHT);
    }

    public static void setToDefaultScreenSize() {
        SCREEN_WIDTH = 1280;
        SCREEN_HEIGHT = 768;
    }

    public static int getScreenHeightForProject(Project project) {
        if (project.isCastProject()) {
            return CAST_SCREEN_HEIGHT;
        }
        return SCREEN_HEIGHT;
    }

    public static int getScreenWidthForProject(Project project) {
        if (project.isCastProject()) {
            return 1280;
        }
        return SCREEN_WIDTH;
    }
}
