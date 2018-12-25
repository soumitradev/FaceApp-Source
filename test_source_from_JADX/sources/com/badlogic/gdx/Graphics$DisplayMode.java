package com.badlogic.gdx;

public class Graphics$DisplayMode {
    public final int bitsPerPixel;
    public final int height;
    public final int refreshRate;
    public final int width;

    protected Graphics$DisplayMode(int width, int height, int refreshRate, int bitsPerPixel) {
        this.width = width;
        this.height = height;
        this.refreshRate = refreshRate;
        this.bitsPerPixel = bitsPerPixel;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.width);
        stringBuilder.append("x");
        stringBuilder.append(this.height);
        stringBuilder.append(", bpp: ");
        stringBuilder.append(this.bitsPerPixel);
        stringBuilder.append(", hz: ");
        stringBuilder.append(this.refreshRate);
        return stringBuilder.toString();
    }
}
