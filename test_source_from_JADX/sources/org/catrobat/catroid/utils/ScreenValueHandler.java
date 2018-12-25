package org.catrobat.catroid.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import org.catrobat.catroid.common.ScreenValues;

public final class ScreenValueHandler {
    private ScreenValueHandler() {
        throw new AssertionError();
    }

    public static void updateScreenWidthAndHeight(Context context) {
        if (context != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            ScreenValues.SCREEN_WIDTH = displayMetrics.widthPixels;
            ScreenValues.SCREEN_HEIGHT = displayMetrics.heightPixels;
            return;
        }
        ScreenValues.setToDefaultScreenSize();
    }
}
