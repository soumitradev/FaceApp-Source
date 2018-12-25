package com.badlogic.gdx.graphics;

import com.badlogic.gdx.utils.GdxRuntimeException;

public enum Pixmap$Format {
    Alpha,
    Intensity,
    LuminanceAlpha,
    RGB565,
    RGBA4444,
    RGB888,
    RGBA8888;

    public static int toGdx2DPixmapFormat(Pixmap$Format format) {
        if (format == Alpha || format == Intensity) {
            return 1;
        }
        if (format == LuminanceAlpha) {
            return 2;
        }
        if (format == RGB565) {
            return 5;
        }
        if (format == RGBA4444) {
            return 6;
        }
        if (format == RGB888) {
            return 3;
        }
        if (format == RGBA8888) {
            return 4;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown Format: ");
        stringBuilder.append(format);
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    public static Pixmap$Format fromGdx2DPixmapFormat(int format) {
        if (format == 1) {
            return Alpha;
        }
        if (format == 2) {
            return LuminanceAlpha;
        }
        if (format == 5) {
            return RGB565;
        }
        if (format == 6) {
            return RGBA4444;
        }
        if (format == 3) {
            return RGB888;
        }
        if (format == 4) {
            return RGBA8888;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown Gdx2DPixmap Format: ");
        stringBuilder.append(format);
        throw new GdxRuntimeException(stringBuilder.toString());
    }
}
