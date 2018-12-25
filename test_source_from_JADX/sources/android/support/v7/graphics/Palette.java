package android.support.v7.graphics;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.SparseBooleanArray;
import android.util.TimingLogger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.catrobat.catroid.common.BrickValues;

public final class Palette {
    static final int DEFAULT_CALCULATE_NUMBER_COLORS = 16;
    static final Palette$Filter DEFAULT_FILTER = new Palette$1();
    static final int DEFAULT_RESIZE_BITMAP_AREA = 12544;
    static final String LOG_TAG = "Palette";
    static final boolean LOG_TIMINGS = false;
    static final float MIN_CONTRAST_BODY_TEXT = 4.5f;
    static final float MIN_CONTRAST_TITLE_TEXT = 3.0f;
    private final Palette$Swatch mDominantSwatch = findDominantSwatch();
    private final Map<Target, Palette$Swatch> mSelectedSwatches = new ArrayMap();
    private final List<Palette$Swatch> mSwatches;
    private final List<Target> mTargets;
    private final SparseBooleanArray mUsedColors = new SparseBooleanArray();

    public static final class Builder {
        private final Bitmap mBitmap;
        private final List<Palette$Filter> mFilters = new ArrayList();
        private int mMaxColors = 16;
        private Rect mRegion;
        private int mResizeArea = Palette.DEFAULT_RESIZE_BITMAP_AREA;
        private int mResizeMaxDimension = -1;
        private final List<Palette$Swatch> mSwatches;
        private final List<Target> mTargets = new ArrayList();

        public Builder(@NonNull Bitmap bitmap) {
            if (bitmap != null) {
                if (!bitmap.isRecycled()) {
                    this.mFilters.add(Palette.DEFAULT_FILTER);
                    this.mBitmap = bitmap;
                    this.mSwatches = null;
                    this.mTargets.add(Target.LIGHT_VIBRANT);
                    this.mTargets.add(Target.VIBRANT);
                    this.mTargets.add(Target.DARK_VIBRANT);
                    this.mTargets.add(Target.LIGHT_MUTED);
                    this.mTargets.add(Target.MUTED);
                    this.mTargets.add(Target.DARK_MUTED);
                    return;
                }
            }
            throw new IllegalArgumentException("Bitmap is not valid");
        }

        public Builder(@NonNull List<Palette$Swatch> swatches) {
            if (swatches != null) {
                if (!swatches.isEmpty()) {
                    this.mFilters.add(Palette.DEFAULT_FILTER);
                    this.mSwatches = swatches;
                    this.mBitmap = null;
                    return;
                }
            }
            throw new IllegalArgumentException("List of Swatches is not valid");
        }

        @NonNull
        public Builder maximumColorCount(int colors) {
            this.mMaxColors = colors;
            return this;
        }

        @Deprecated
        @NonNull
        public Builder resizeBitmapSize(int maxDimension) {
            this.mResizeMaxDimension = maxDimension;
            this.mResizeArea = -1;
            return this;
        }

        @NonNull
        public Builder resizeBitmapArea(int area) {
            this.mResizeArea = area;
            this.mResizeMaxDimension = -1;
            return this;
        }

        @NonNull
        public Builder clearFilters() {
            this.mFilters.clear();
            return this;
        }

        @NonNull
        public Builder addFilter(Palette$Filter filter) {
            if (filter != null) {
                this.mFilters.add(filter);
            }
            return this;
        }

        @NonNull
        public Builder setRegion(int left, int top, int right, int bottom) {
            if (this.mBitmap != null) {
                if (this.mRegion == null) {
                    this.mRegion = new Rect();
                }
                this.mRegion.set(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                if (!this.mRegion.intersect(left, top, right, bottom)) {
                    throw new IllegalArgumentException("The given region must intersect with the Bitmap's dimensions.");
                }
            }
            return this;
        }

        @NonNull
        public Builder clearRegion() {
            this.mRegion = null;
            return this;
        }

        @NonNull
        public Builder addTarget(@NonNull Target target) {
            if (!this.mTargets.contains(target)) {
                this.mTargets.add(target);
            }
            return this;
        }

        @NonNull
        public Builder clearTargets() {
            if (this.mTargets != null) {
                this.mTargets.clear();
            }
            return this;
        }

        @NonNull
        public Palette generate() {
            List<Palette$Swatch> swatches;
            TimingLogger logger = null;
            if (this.mBitmap != null) {
                Bitmap bitmap = scaleBitmapDown(this.mBitmap);
                if (logger != null) {
                    logger.addSplit("Processed Bitmap");
                }
                Rect region = this.mRegion;
                if (!(bitmap == this.mBitmap || region == null)) {
                    double scale = ((double) bitmap.getWidth()) / ((double) this.mBitmap.getWidth());
                    region.left = (int) Math.floor(((double) region.left) * scale);
                    region.top = (int) Math.floor(((double) region.top) * scale);
                    region.right = Math.min((int) Math.ceil(((double) region.right) * scale), bitmap.getWidth());
                    region.bottom = Math.min((int) Math.ceil(((double) region.bottom) * scale), bitmap.getHeight());
                }
                ColorCutQuantizer quantizer = new ColorCutQuantizer(getPixelsFromBitmap(bitmap), this.mMaxColors, this.mFilters.isEmpty() ? null : (Palette$Filter[]) this.mFilters.toArray(new Palette$Filter[this.mFilters.size()]));
                if (bitmap != this.mBitmap) {
                    bitmap.recycle();
                }
                swatches = quantizer.getQuantizedColors();
                if (logger != null) {
                    logger.addSplit("Color quantization completed");
                }
            } else {
                swatches = this.mSwatches;
            }
            Palette p = new Palette(swatches, this.mTargets);
            p.generate();
            if (logger != null) {
                logger.addSplit("Created Palette");
                logger.dumpToLog();
            }
            return p;
        }

        @NonNull
        public AsyncTask<Bitmap, Void, Palette> generate(@NonNull Palette$PaletteAsyncListener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("listener can not be null");
            }
            return new Palette$Builder$1(this, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Bitmap[]{this.mBitmap});
        }

        private int[] getPixelsFromBitmap(Bitmap bitmap) {
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            int[] pixels = new int[(bitmapWidth * bitmapHeight)];
            bitmap.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);
            if (this.mRegion == null) {
                return pixels;
            }
            int regionWidth = this.mRegion.width();
            int regionHeight = this.mRegion.height();
            int[] subsetPixels = new int[(regionWidth * regionHeight)];
            for (int row = 0; row < regionHeight; row++) {
                System.arraycopy(pixels, ((this.mRegion.top + row) * bitmapWidth) + this.mRegion.left, subsetPixels, row * regionWidth, regionWidth);
            }
            return subsetPixels;
        }

        private Bitmap scaleBitmapDown(Bitmap bitmap) {
            double scaleRatio = -1.0d;
            int bitmapArea;
            if (this.mResizeArea > 0) {
                bitmapArea = bitmap.getWidth() * bitmap.getHeight();
                if (bitmapArea > this.mResizeArea) {
                    scaleRatio = Math.sqrt(((double) this.mResizeArea) / ((double) bitmapArea));
                }
            } else if (this.mResizeMaxDimension > 0) {
                bitmapArea = Math.max(bitmap.getWidth(), bitmap.getHeight());
                if (bitmapArea > this.mResizeMaxDimension) {
                    scaleRatio = ((double) this.mResizeMaxDimension) / ((double) bitmapArea);
                }
            }
            if (scaleRatio <= BrickValues.SET_COLOR_TO) {
                return bitmap;
            }
            return Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(((double) bitmap.getWidth()) * scaleRatio), (int) Math.ceil(((double) bitmap.getHeight()) * scaleRatio), false);
        }
    }

    @NonNull
    public static Builder from(@NonNull Bitmap bitmap) {
        return new Builder(bitmap);
    }

    @NonNull
    public static Palette from(@NonNull List<Palette$Swatch> swatches) {
        return new Builder((List) swatches).generate();
    }

    @Deprecated
    public static Palette generate(Bitmap bitmap) {
        return from(bitmap).generate();
    }

    @Deprecated
    public static Palette generate(Bitmap bitmap, int numColors) {
        return from(bitmap).maximumColorCount(numColors).generate();
    }

    @Deprecated
    public static AsyncTask<Bitmap, Void, Palette> generateAsync(Bitmap bitmap, Palette$PaletteAsyncListener listener) {
        return from(bitmap).generate(listener);
    }

    @Deprecated
    public static AsyncTask<Bitmap, Void, Palette> generateAsync(Bitmap bitmap, int numColors, Palette$PaletteAsyncListener listener) {
        return from(bitmap).maximumColorCount(numColors).generate(listener);
    }

    Palette(List<Palette$Swatch> swatches, List<Target> targets) {
        this.mSwatches = swatches;
        this.mTargets = targets;
    }

    @NonNull
    public List<Palette$Swatch> getSwatches() {
        return Collections.unmodifiableList(this.mSwatches);
    }

    @NonNull
    public List<Target> getTargets() {
        return Collections.unmodifiableList(this.mTargets);
    }

    @Nullable
    public Palette$Swatch getVibrantSwatch() {
        return getSwatchForTarget(Target.VIBRANT);
    }

    @Nullable
    public Palette$Swatch getLightVibrantSwatch() {
        return getSwatchForTarget(Target.LIGHT_VIBRANT);
    }

    @Nullable
    public Palette$Swatch getDarkVibrantSwatch() {
        return getSwatchForTarget(Target.DARK_VIBRANT);
    }

    @Nullable
    public Palette$Swatch getMutedSwatch() {
        return getSwatchForTarget(Target.MUTED);
    }

    @Nullable
    public Palette$Swatch getLightMutedSwatch() {
        return getSwatchForTarget(Target.LIGHT_MUTED);
    }

    @Nullable
    public Palette$Swatch getDarkMutedSwatch() {
        return getSwatchForTarget(Target.DARK_MUTED);
    }

    @ColorInt
    public int getVibrantColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.VIBRANT, defaultColor);
    }

    @ColorInt
    public int getLightVibrantColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.LIGHT_VIBRANT, defaultColor);
    }

    @ColorInt
    public int getDarkVibrantColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.DARK_VIBRANT, defaultColor);
    }

    @ColorInt
    public int getMutedColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.MUTED, defaultColor);
    }

    @ColorInt
    public int getLightMutedColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.LIGHT_MUTED, defaultColor);
    }

    @ColorInt
    public int getDarkMutedColor(@ColorInt int defaultColor) {
        return getColorForTarget(Target.DARK_MUTED, defaultColor);
    }

    @Nullable
    public Palette$Swatch getSwatchForTarget(@NonNull Target target) {
        return (Palette$Swatch) this.mSelectedSwatches.get(target);
    }

    @ColorInt
    public int getColorForTarget(@NonNull Target target, @ColorInt int defaultColor) {
        Palette$Swatch swatch = getSwatchForTarget(target);
        return swatch != null ? swatch.getRgb() : defaultColor;
    }

    @Nullable
    public Palette$Swatch getDominantSwatch() {
        return this.mDominantSwatch;
    }

    @ColorInt
    public int getDominantColor(@ColorInt int defaultColor) {
        return this.mDominantSwatch != null ? this.mDominantSwatch.getRgb() : defaultColor;
    }

    void generate() {
        int count = this.mTargets.size();
        for (int i = 0; i < count; i++) {
            Target target = (Target) this.mTargets.get(i);
            target.normalizeWeights();
            this.mSelectedSwatches.put(target, generateScoredTarget(target));
        }
        this.mUsedColors.clear();
    }

    private Palette$Swatch generateScoredTarget(Target target) {
        Palette$Swatch maxScoreSwatch = getMaxScoredSwatchForTarget(target);
        if (maxScoreSwatch != null && target.isExclusive()) {
            this.mUsedColors.append(maxScoreSwatch.getRgb(), true);
        }
        return maxScoreSwatch;
    }

    private Palette$Swatch getMaxScoredSwatchForTarget(Target target) {
        float maxScore = 0.0f;
        Palette$Swatch maxScoreSwatch = null;
        int count = this.mSwatches.size();
        for (int i = 0; i < count; i++) {
            Palette$Swatch swatch = (Palette$Swatch) this.mSwatches.get(i);
            if (shouldBeScoredForTarget(swatch, target)) {
                float score = generateScore(swatch, target);
                if (maxScoreSwatch == null || score > maxScore) {
                    maxScoreSwatch = swatch;
                    maxScore = score;
                }
            }
        }
        return maxScoreSwatch;
    }

    private boolean shouldBeScoredForTarget(Palette$Swatch swatch, Target target) {
        float[] hsl = swatch.getHsl();
        if (hsl[1] < target.getMinimumSaturation() || hsl[1] > target.getMaximumSaturation() || hsl[2] < target.getMinimumLightness() || hsl[2] > target.getMaximumLightness() || this.mUsedColors.get(swatch.getRgb())) {
            return false;
        }
        return true;
    }

    private float generateScore(Palette$Swatch swatch, Target target) {
        float[] hsl = swatch.getHsl();
        float saturationScore = 0.0f;
        float luminanceScore = 0.0f;
        float populationScore = 0.0f;
        int maxPopulation = this.mDominantSwatch != null ? this.mDominantSwatch.getPopulation() : 1;
        if (target.getSaturationWeight() > 0.0f) {
            saturationScore = target.getSaturationWeight() * (1.0f - Math.abs(hsl[1] - target.getTargetSaturation()));
        }
        if (target.getLightnessWeight() > 0.0f) {
            luminanceScore = target.getLightnessWeight() * (1.0f - Math.abs(hsl[2] - target.getTargetLightness()));
        }
        if (target.getPopulationWeight() > 0.0f) {
            populationScore = target.getPopulationWeight() * (((float) swatch.getPopulation()) / ((float) maxPopulation));
        }
        return (saturationScore + luminanceScore) + populationScore;
    }

    private Palette$Swatch findDominantSwatch() {
        int maxPop = Integer.MIN_VALUE;
        Palette$Swatch maxSwatch = null;
        int count = this.mSwatches.size();
        for (int i = 0; i < count; i++) {
            Palette$Swatch swatch = (Palette$Swatch) this.mSwatches.get(i);
            if (swatch.getPopulation() > maxPop) {
                maxSwatch = swatch;
                maxPop = swatch.getPopulation();
            }
        }
        return maxSwatch;
    }

    private static float[] copyHslValues(Palette$Swatch color) {
        float[] newHsl = new float[3];
        System.arraycopy(color.getHsl(), 0, newHsl, 0, 3);
        return newHsl;
    }
}
