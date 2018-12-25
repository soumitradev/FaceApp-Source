package com.github.johnpersano.supertoasts.library.utils;

import android.graphics.Color;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PaletteUtils {
    private static final String ALPHA_SOLID = "#FF";
    private static final String ALPHA_TRANSPARENT = "#E1";
    public static final String BLACK = "000000";
    public static final String DARK_GREY = "424242";
    public static final String GREY = "757575";
    public static final String LIGHT_GREY = "BDBDBD";
    public static final String MATERIAL_AMBER = "FFC107";
    public static final String MATERIAL_BLUE = "2196F3";
    public static final String MATERIAL_BLUE_GREY = "607D8B";
    public static final String MATERIAL_BROWN = "795548";
    public static final String MATERIAL_CYAN = "00BCD4";
    public static final String MATERIAL_DEEP_ORANGE = "FF5722";
    public static final String MATERIAL_DEEP_PURPLE = "673AB7";
    public static final String MATERIAL_GREEN = "4CAF50";
    public static final String MATERIAL_GREY = "9E9E9E";
    public static final String MATERIAL_INDIGO = "3F51B5";
    public static final String MATERIAL_LIGHT_BLUE = "03A9F4";
    public static final String MATERIAL_LIGHT_GREEN = "8BC34A";
    public static final String MATERIAL_LIME = "CDDC39";
    public static final String MATERIAL_ORANGE = "FF9800";
    public static final String MATERIAL_PINK = "E91E63";
    public static final String MATERIAL_PURPLE = "9C27B0";
    public static final String MATERIAL_RED = "F44336";
    public static final String MATERIAL_TEAL = "009688";
    public static final String MATERIAL_YELLOW = "FFEB3B";
    public static final String WHITE = "FFFFFF";

    @Retention(RetentionPolicy.SOURCE)
    public @interface PaletteColors {
    }

    public static int getSolidColor(String color) {
        return Color.parseColor(ALPHA_SOLID.concat(color));
    }

    public static int getTransparentColor(String color) {
        return Color.parseColor(ALPHA_TRANSPARENT.concat(color));
    }
}
