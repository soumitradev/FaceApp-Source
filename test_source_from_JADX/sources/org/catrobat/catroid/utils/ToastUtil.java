package org.catrobat.catroid.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public final class ToastUtil {
    private static SuperToast customToast;

    private ToastUtil() {
    }

    public static void showError(Context context, String message) {
        createToast(context, message, true);
    }

    public static void showError(Context context, @StringRes int messageId) {
        createToast(context, context.getResources().getString(messageId), true);
    }

    public static void showSuccess(Context context, String message) {
        createToast(context, message, false);
    }

    public static void showSuccess(Context context, @StringRes int messageId) {
        createToast(context, context.getResources().getString(messageId), false);
    }

    private static void createToast(Context context, String message, boolean error) {
        if (customToast != null) {
            if (customToast.isShowing()) {
                setLook(error);
                customToast.setText(message);
                return;
            }
        }
        customToast = new SuperToast(context);
        customToast.setText(message);
        customToast.setTextSize(16);
        customToast.setAnimations(4);
        setLook(error);
        customToast.show();
    }

    private static void setLook(boolean error) {
        if (error) {
            customToast.setDuration(2000);
            customToast.setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED));
            return;
        }
        customToast.setDuration(1500);
        customToast.setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN));
    }
}
