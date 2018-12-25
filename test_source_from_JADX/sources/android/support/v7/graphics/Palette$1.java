package android.support.v7.graphics;

class Palette$1 implements Palette$Filter {
    private static final float BLACK_MAX_LIGHTNESS = 0.05f;
    private static final float WHITE_MIN_LIGHTNESS = 0.95f;

    Palette$1() {
    }

    public boolean isAllowed(int rgb, float[] hsl) {
        return (isWhite(hsl) || isBlack(hsl) || isNearRedILine(hsl)) ? false : true;
    }

    private boolean isBlack(float[] hslColor) {
        return hslColor[2] <= BLACK_MAX_LIGHTNESS;
    }

    private boolean isWhite(float[] hslColor) {
        return hslColor[2] >= WHITE_MIN_LIGHTNESS;
    }

    private boolean isNearRedILine(float[] hslColor) {
        return hslColor[0] >= 10.0f && hslColor[0] <= 37.0f && hslColor[1] <= 0.82f;
    }
}
