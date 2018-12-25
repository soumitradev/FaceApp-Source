package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TiledDrawable extends TextureRegionDrawable {
    public TiledDrawable(TextureRegion region) {
        super(region);
    }

    public TiledDrawable(TextureRegionDrawable drawable) {
        super(drawable);
    }

    public void draw(Batch batch, float x, float y, float width, float height) {
        float u2;
        float y2;
        TextureRegion region = getRegion();
        float regionWidth = (float) region.getRegionWidth();
        float regionHeight = (float) region.getRegionHeight();
        int fullX = (int) (width / regionWidth);
        int fullY = (int) (height / regionHeight);
        float remainingX = width - (((float) fullX) * regionWidth);
        float remainingY = height - (((float) fullY) * regionHeight);
        float startX = x;
        float startY = y;
        float endX = (x + width) - remainingX;
        float endY = (y + height) - remainingY;
        int i = 0;
        float x2 = x;
        float y3 = y;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= fullX) {
                break;
            }
            float y4 = startY;
            i2 = 0;
            while (true) {
                int ii = i2;
                if (ii >= fullY) {
                    break;
                }
                int ii2 = ii;
                batch.draw(region, x2, y4, regionWidth, regionHeight);
                y4 += regionHeight;
                i2 = ii2 + 1;
            }
            x2 += regionWidth;
            i2 = i3 + 1;
            y3 = y4;
        }
        Texture texture = region.getTexture();
        float u = region.getU();
        float v2 = region.getV2();
        if (remainingX > 0.0f) {
            u2 = (remainingX / ((float) texture.getWidth())) + u;
            float v = region.getV();
            y2 = startY;
            for (int y5 = 0; y5 < fullY; y5++) {
                batch.draw(texture, x2, y2, remainingX, regionHeight, u, v2, u2, v);
                y2 += regionHeight;
            }
            if (remainingY > 0.0f) {
                batch.draw(texture, x2, y2, remainingX, remainingY, u, v2, u2, v2 - (remainingY / ((float) texture.getHeight())));
            }
        } else {
            y2 = y3;
        }
        int fullX2;
        float regionHeight2;
        float regionWidth2;
        if (remainingY > 0.0f) {
            y3 = region.getU2();
            float v3 = v2 - (remainingY / ((float) texture.getHeight()));
            u2 = startX;
            while (true) {
                i3 = i;
                if (i3 < fullX) {
                    int i4 = i3;
                    int fullY2 = fullY;
                    fullX2 = fullX;
                    regionHeight2 = regionHeight;
                    regionWidth2 = regionWidth;
                    batch.draw(texture, u2, y2, regionWidth, remainingY, u, v2, y3, v3);
                    u2 += regionWidth2;
                    i = i4 + 1;
                    fullY = fullY2;
                    fullX = fullX2;
                    regionHeight = regionHeight2;
                    regionWidth = regionWidth2;
                } else {
                    fullX2 = fullX;
                    regionHeight2 = regionHeight;
                    regionWidth2 = regionWidth;
                    x2 = u2;
                    return;
                }
            }
        }
        fullY2 = fullY;
        fullX2 = fullX;
        regionHeight2 = regionHeight;
        regionWidth2 = regionWidth;
    }
}
