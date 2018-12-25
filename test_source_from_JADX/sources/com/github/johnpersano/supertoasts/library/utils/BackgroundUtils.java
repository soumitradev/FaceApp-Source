package com.github.johnpersano.supertoasts.library.utils;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.util.TypedValue;
import com.github.johnpersano.supertoasts.library.C0507R;
import com.github.johnpersano.supertoasts.library.Style;

public class BackgroundUtils {
    public static Drawable getBackground(Style style, int color) {
        if (style.frame > 0) {
            switch (style.frame) {
                case 1:
                    return getStandardBackground(color);
                case 2:
                    return getKitkatBackground(color);
                case 3:
                    return getLollipopBackground(color);
                default:
                    break;
            }
        }
        int sdkVersion = VERSION.SDK_INT;
        if (sdkVersion >= 21) {
            style.frame = 3;
            return getLollipopBackground(color);
        } else if (sdkVersion >= 19) {
            style.frame = 2;
            return getKitkatBackground(color);
        } else {
            style.frame = 1;
            return getStandardBackground(color);
        }
    }

    public static int getButtonBackgroundResource(int frame) {
        switch (frame) {
            case 1:
                return C0507R.drawable.selector_button_standard;
            case 2:
                return C0507R.drawable.selector_button_kitkat;
            case 3:
                return C0507R.drawable.selector_button_lollipop;
            default:
                return C0507R.drawable.selector_button_standard;
        }
    }

    public static int convertToDIP(int pixels) {
        return Math.round(TypedValue.applyDimension(1, (float) pixels, Resources.getSystem().getDisplayMetrics()));
    }

    private static ColorDrawable getLollipopBackground(int color) {
        return new ColorDrawable(color);
    }

    private static GradientDrawable getKitkatBackground(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius((float) convertToDIP(24));
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    private static GradientDrawable getStandardBackground(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius((float) convertToDIP(4));
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }
}
