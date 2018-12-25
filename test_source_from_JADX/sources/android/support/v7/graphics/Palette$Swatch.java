package android.support.v7.graphics;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewCompat;
import java.util.Arrays;
import org.catrobat.catroid.common.Constants;

public final class Palette$Swatch {
    private final int mBlue;
    private int mBodyTextColor;
    private boolean mGeneratedTextColors;
    private final int mGreen;
    private float[] mHsl;
    private final int mPopulation;
    private final int mRed;
    private final int mRgb;
    private int mTitleTextColor;

    public Palette$Swatch(@ColorInt int color, int population) {
        this.mRed = Color.red(color);
        this.mGreen = Color.green(color);
        this.mBlue = Color.blue(color);
        this.mRgb = color;
        this.mPopulation = population;
    }

    Palette$Swatch(int red, int green, int blue, int population) {
        this.mRed = red;
        this.mGreen = green;
        this.mBlue = blue;
        this.mRgb = Color.rgb(red, green, blue);
        this.mPopulation = population;
    }

    Palette$Swatch(float[] hsl, int population) {
        this(ColorUtils.HSLToColor(hsl), population);
        this.mHsl = hsl;
    }

    @ColorInt
    public int getRgb() {
        return this.mRgb;
    }

    @NonNull
    public float[] getHsl() {
        if (this.mHsl == null) {
            this.mHsl = new float[3];
        }
        ColorUtils.RGBToHSL(this.mRed, this.mGreen, this.mBlue, this.mHsl);
        return this.mHsl;
    }

    public int getPopulation() {
        return this.mPopulation;
    }

    @ColorInt
    public int getTitleTextColor() {
        ensureTextColorsGenerated();
        return this.mTitleTextColor;
    }

    @ColorInt
    public int getBodyTextColor() {
        ensureTextColorsGenerated();
        return this.mBodyTextColor;
    }

    private void ensureTextColorsGenerated() {
        if (!this.mGeneratedTextColors) {
            int lightBodyAlpha = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, 4.5f);
            int lightTitleAlpha = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, 3.0f);
            if (lightBodyAlpha == -1 || lightTitleAlpha == -1) {
                int darkBodyAlpha = ColorUtils.calculateMinimumAlpha(ViewCompat.MEASURED_STATE_MASK, this.mRgb, 4.5f);
                int darkTitleAlpha = ColorUtils.calculateMinimumAlpha(ViewCompat.MEASURED_STATE_MASK, this.mRgb, 3.0f);
                if (darkBodyAlpha == -1 || darkTitleAlpha == -1) {
                    int alphaComponent;
                    int alphaComponent2;
                    if (lightBodyAlpha != -1) {
                        alphaComponent = ColorUtils.setAlphaComponent(-1, lightBodyAlpha);
                    } else {
                        alphaComponent = ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, darkBodyAlpha);
                    }
                    this.mBodyTextColor = alphaComponent;
                    if (lightTitleAlpha != -1) {
                        alphaComponent2 = ColorUtils.setAlphaComponent(-1, lightTitleAlpha);
                    } else {
                        alphaComponent2 = ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, darkTitleAlpha);
                    }
                    this.mTitleTextColor = alphaComponent2;
                    this.mGeneratedTextColors = true;
                } else {
                    this.mBodyTextColor = ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, darkBodyAlpha);
                    this.mTitleTextColor = ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, darkTitleAlpha);
                    this.mGeneratedTextColors = true;
                    return;
                }
            }
            this.mBodyTextColor = ColorUtils.setAlphaComponent(-1, lightBodyAlpha);
            this.mTitleTextColor = ColorUtils.setAlphaComponent(-1, lightTitleAlpha);
            this.mGeneratedTextColors = true;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(getClass().getSimpleName());
        stringBuilder.append(" [RGB: #");
        stringBuilder.append(Integer.toHexString(getRgb()));
        stringBuilder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        stringBuilder.append(" [HSL: ");
        stringBuilder.append(Arrays.toString(getHsl()));
        stringBuilder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        stringBuilder.append(" [Population: ");
        stringBuilder.append(this.mPopulation);
        stringBuilder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        stringBuilder.append(" [Title Text: #");
        stringBuilder.append(Integer.toHexString(getTitleTextColor()));
        stringBuilder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        stringBuilder.append(" [Body Text: #");
        stringBuilder.append(Integer.toHexString(getBodyTextColor()));
        stringBuilder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        return stringBuilder.toString();
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o != null) {
            if (getClass() == o.getClass()) {
                Palette$Swatch swatch = (Palette$Swatch) o;
                if (this.mPopulation != swatch.mPopulation || this.mRgb != swatch.mRgb) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.mRgb * 31) + this.mPopulation;
    }
}
