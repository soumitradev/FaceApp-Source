package com.parrot.freeflight.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

public class TextureUtils {
    public static long roundPower2(long x) {
        int rval = 256;
        while (((long) rval) < x) {
            rval <<= 1;
        }
        return (long) rval;
    }

    public static Bitmap makeTexture(Resources res, Bitmap bmp) {
        if (bmp == null) {
            throw new IllegalArgumentException("Bitmap can't be null");
        }
        Bitmap result = Bitmap.createBitmap((int) roundPower2((long) bmp.getWidth()), (int) roundPower2((long) bmp.getHeight()), Config.ARGB_8888);
        new Canvas(result).drawBitmap(bmp, 0.0f, 0.0f, null);
        return result;
    }
}
