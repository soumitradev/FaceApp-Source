package com.badlogic.gdx.graphics;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import com.badlogic.gdx.utils.NumberUtils;
import com.facebook.appevents.AppEventsConstants;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;
import org.apache.commons.compress.archivers.cpio.CpioConstants;

public class Color {
    public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f, 1.0f);
    public static final Color CLEAR = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Color CYAN = new Color(0.0f, 1.0f, 1.0f, 1.0f);
    public static final Color DARK_GRAY = new Color(0.25f, 0.25f, 0.25f, 1.0f);
    public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1.0f);
    public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    public static final Color LIGHT_GRAY = new Color(0.75f, 0.75f, 0.75f, 1.0f);
    public static final Color MAGENTA = new Color(1.0f, 0.0f, 1.0f, 1.0f);
    public static final Color MAROON = new Color(0.5f, 0.0f, 0.0f, 1.0f);
    public static final Color NAVY = new Color(0.0f, 0.0f, 0.5f, 1.0f);
    public static final Color OLIVE = new Color(0.5f, 0.5f, 0.0f, 1.0f);
    public static final Color ORANGE = new Color(1.0f, 0.78f, 0.0f, 1.0f);
    public static final Color PINK = new Color(1.0f, 0.68f, 0.68f, 1.0f);
    public static final Color PURPLE = new Color(0.5f, 0.0f, 0.5f, 1.0f);
    public static final Color RED = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    public static final Color TEAL = new Color(0.0f, 0.5f, 0.5f, 1.0f);
    public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f, 1.0f);
    /* renamed from: a */
    public float f1a;
    /* renamed from: b */
    public float f2b;
    /* renamed from: g */
    public float f3g;
    /* renamed from: r */
    public float f4r;

    public Color(int rgba8888) {
        rgba8888ToColor(this, rgba8888);
    }

    public Color(float r, float g, float b, float a) {
        this.f4r = r;
        this.f3g = g;
        this.f2b = b;
        this.f1a = a;
        clamp();
    }

    public Color(Color color) {
        set(color);
    }

    public Color set(Color color) {
        this.f4r = color.f4r;
        this.f3g = color.f3g;
        this.f2b = color.f2b;
        this.f1a = color.f1a;
        return this;
    }

    public Color mul(Color color) {
        this.f4r *= color.f4r;
        this.f3g *= color.f3g;
        this.f2b *= color.f2b;
        this.f1a *= color.f1a;
        return clamp();
    }

    public Color mul(float value) {
        this.f4r *= value;
        this.f3g *= value;
        this.f2b *= value;
        this.f1a *= value;
        return clamp();
    }

    public Color add(Color color) {
        this.f4r += color.f4r;
        this.f3g += color.f3g;
        this.f2b += color.f2b;
        this.f1a += color.f1a;
        return clamp();
    }

    public Color sub(Color color) {
        this.f4r -= color.f4r;
        this.f3g -= color.f3g;
        this.f2b -= color.f2b;
        this.f1a -= color.f1a;
        return clamp();
    }

    public Color clamp() {
        if (this.f4r < 0.0f) {
            this.f4r = 0.0f;
        } else if (this.f4r > 1.0f) {
            this.f4r = 1.0f;
        }
        if (this.f3g < 0.0f) {
            this.f3g = 0.0f;
        } else if (this.f3g > 1.0f) {
            this.f3g = 1.0f;
        }
        if (this.f2b < 0.0f) {
            this.f2b = 0.0f;
        } else if (this.f2b > 1.0f) {
            this.f2b = 1.0f;
        }
        if (this.f1a < 0.0f) {
            this.f1a = 0.0f;
        } else if (this.f1a > 1.0f) {
            this.f1a = 1.0f;
        }
        return this;
    }

    public Color set(float r, float g, float b, float a) {
        this.f4r = r;
        this.f3g = g;
        this.f2b = b;
        this.f1a = a;
        return clamp();
    }

    public Color set(int rgba) {
        rgba8888ToColor(this, rgba);
        return this;
    }

    public Color add(float r, float g, float b, float a) {
        this.f4r += r;
        this.f3g += g;
        this.f2b += b;
        this.f1a += a;
        return clamp();
    }

    public Color sub(float r, float g, float b, float a) {
        this.f4r -= r;
        this.f3g -= g;
        this.f2b -= b;
        this.f1a -= a;
        return clamp();
    }

    public Color mul(float r, float g, float b, float a) {
        this.f4r *= r;
        this.f3g *= g;
        this.f2b *= b;
        this.f1a *= a;
        return clamp();
    }

    public Color lerp(Color target, float t) {
        this.f4r += (target.f4r - this.f4r) * t;
        this.f3g += (target.f3g - this.f3g) * t;
        this.f2b += (target.f2b - this.f2b) * t;
        this.f1a += (target.f1a - this.f1a) * t;
        return clamp();
    }

    public Color lerp(float r, float g, float b, float a, float t) {
        this.f4r += (r - this.f4r) * t;
        this.f3g += (g - this.f3g) * t;
        this.f2b += (b - this.f2b) * t;
        this.f1a += (a - this.f1a) * t;
        return clamp();
    }

    public Color premultiplyAlpha() {
        this.f4r *= this.f1a;
        this.f3g *= this.f1a;
        this.f2b *= this.f1a;
        return this;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o != null) {
            if (getClass() == o.getClass()) {
                if (toIntBits() != ((Color) o).toIntBits()) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        int i = 0;
        int floatToIntBits = (((((this.f4r != 0.0f ? NumberUtils.floatToIntBits(this.f4r) : 0) * 31) + (this.f3g != 0.0f ? NumberUtils.floatToIntBits(this.f3g) : 0)) * 31) + (this.f2b != 0.0f ? NumberUtils.floatToIntBits(this.f2b) : 0)) * 31;
        if (this.f1a != 0.0f) {
            i = NumberUtils.floatToIntBits(this.f1a);
        }
        return floatToIntBits + i;
    }

    public float toFloatBits() {
        return NumberUtils.intToFloatColor((((((int) (this.f1a * 255.0f)) << 24) | (((int) (this.f2b * 255.0f)) << 16)) | (((int) (this.f3g * 255.0f)) << 8)) | ((int) (this.f4r * 255.0f)));
    }

    public int toIntBits() {
        return (((((int) (this.f1a * 255.0f)) << 24) | (((int) (this.f2b * 255.0f)) << 16)) | (((int) (this.f3g * 255.0f)) << 8)) | ((int) (this.f4r * 255.0f));
    }

    public String toString() {
        String value = Integer.toHexString((((((int) (this.f4r * 255.0f)) << 24) | (((int) (this.f3g * 255.0f)) << 16)) | (((int) (this.f2b * 255.0f)) << 8)) | ((int) (this.f1a * 255.0f)));
        while (value.length() < 8) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(AppEventsConstants.EVENT_PARAM_VALUE_NO);
            stringBuilder.append(value);
            value = stringBuilder.toString();
        }
        return value;
    }

    public static Color valueOf(String hex) {
        return new Color(((float) Integer.valueOf(hex.substring(0, 2), 16).intValue()) / 255.0f, ((float) Integer.valueOf(hex.substring(2, 4), 16).intValue()) / 255.0f, ((float) Integer.valueOf(hex.substring(4, 6), 16).intValue()) / 255.0f, ((float) (hex.length() != 8 ? 255 : Integer.valueOf(hex.substring(6, 8), 16).intValue())) / 255.0f);
    }

    public static float toFloatBits(int r, int g, int b, int a) {
        return NumberUtils.intToFloatColor((((a << 24) | (b << 16)) | (g << 8)) | r);
    }

    public static float toFloatBits(float r, float g, float b, float a) {
        return NumberUtils.intToFloatColor(((int) (255.0f * r)) | (((((int) (a * 255.0f)) << 24) | (((int) (b * 255.0f)) << 16)) | (((int) (g * 255.0f)) << 8)));
    }

    public static int toIntBits(int r, int g, int b, int a) {
        return (((a << 24) | (b << 16)) | (g << 8)) | r;
    }

    public static int alpha(float alpha) {
        return (int) (255.0f * alpha);
    }

    public static int luminanceAlpha(float luminance, float alpha) {
        return ((int) (255.0f * alpha)) | (((int) (luminance * 255.0f)) << 8);
    }

    public static int rgb565(float r, float g, float b) {
        return ((int) (31.0f * b)) | ((((int) (r * 31.0f)) << 11) | (((int) (63.0f * g)) << 5));
    }

    public static int rgba4444(float r, float g, float b, float a) {
        return ((int) (15.0f * a)) | (((((int) (r * 15.0f)) << 12) | (((int) (g * 15.0f)) << 8)) | (((int) (b * 15.0f)) << 4));
    }

    public static int rgb888(float r, float g, float b) {
        return ((int) (255.0f * b)) | ((((int) (r * 255.0f)) << 16) | (((int) (g * 255.0f)) << 8));
    }

    public static int rgba8888(float r, float g, float b, float a) {
        return ((int) (255.0f * a)) | (((((int) (r * 255.0f)) << 24) | (((int) (g * 255.0f)) << 16)) | (((int) (b * 255.0f)) << 8));
    }

    public static int argb8888(float a, float r, float g, float b) {
        return ((int) (255.0f * b)) | (((((int) (a * 255.0f)) << 24) | (((int) (r * 255.0f)) << 16)) | (((int) (g * 255.0f)) << 8));
    }

    public static int rgb565(Color color) {
        return ((((int) (color.f4r * 31.0f)) << 11) | (((int) (color.f3g * 63.0f)) << 5)) | ((int) (color.f2b * 31.0f));
    }

    public static int rgba4444(Color color) {
        return (((((int) (color.f4r * 15.0f)) << 12) | (((int) (color.f3g * 15.0f)) << 8)) | (((int) (color.f2b * 15.0f)) << 4)) | ((int) (color.f1a * 15.0f));
    }

    public static int rgb888(Color color) {
        return ((((int) (color.f4r * 255.0f)) << 16) | (((int) (color.f3g * 255.0f)) << 8)) | ((int) (color.f2b * 255.0f));
    }

    public static int rgba8888(Color color) {
        return (((((int) (color.f4r * 255.0f)) << 24) | (((int) (color.f3g * 255.0f)) << 16)) | (((int) (color.f2b * 255.0f)) << 8)) | ((int) (color.f1a * 255.0f));
    }

    public static int argb8888(Color color) {
        return (((((int) (color.f1a * 255.0f)) << 24) | (((int) (color.f4r * 255.0f)) << 16)) | (((int) (color.f3g * 255.0f)) << 8)) | ((int) (color.f2b * 255.0f));
    }

    public static void rgb565ToColor(Color color, int value) {
        color.f4r = ((float) ((63488 & value) >>> 11)) / 31.0f;
        color.f3g = ((float) ((value & 2016) >>> 5)) / 63.0f;
        color.f2b = ((float) ((value & 31) >>> 0)) / 31.0f;
    }

    public static void rgba4444ToColor(Color color, int value) {
        color.f4r = ((float) ((CpioConstants.S_IFMT & value) >>> 12)) / 15.0f;
        color.f3g = ((float) ((value & 3840) >>> 8)) / 15.0f;
        color.f2b = ((float) ((value & SysexMessageWriter.COMMAND_START) >>> 4)) / 15.0f;
        color.f1a = ((float) (value & 15)) / 15.0f;
    }

    public static void rgb888ToColor(Color color, int value) {
        color.f4r = ((float) ((16711680 & value) >>> 16)) / 255.0f;
        color.f3g = ((float) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & value) >>> 8)) / 255.0f;
        color.f2b = ((float) (value & 255)) / 255.0f;
    }

    public static void rgba8888ToColor(Color color, int value) {
        color.f4r = ((float) ((ViewCompat.MEASURED_STATE_MASK & value) >>> 24)) / 255.0f;
        color.f3g = ((float) ((16711680 & value) >>> 16)) / 255.0f;
        color.f2b = ((float) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & value) >>> 8)) / 255.0f;
        color.f1a = ((float) (value & 255)) / 255.0f;
    }

    public static void argb8888ToColor(Color color, int value) {
        color.f1a = ((float) ((ViewCompat.MEASURED_STATE_MASK & value) >>> 24)) / 255.0f;
        color.f4r = ((float) ((16711680 & value) >>> 16)) / 255.0f;
        color.f3g = ((float) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & value) >>> 8)) / 255.0f;
        color.f2b = ((float) (value & 255)) / 255.0f;
    }

    public Color cpy() {
        return new Color(this);
    }
}
