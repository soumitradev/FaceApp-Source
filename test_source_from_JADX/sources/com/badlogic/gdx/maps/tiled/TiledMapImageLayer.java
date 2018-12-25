package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;

public class TiledMapImageLayer extends MapLayer {
    private TextureRegion region;
    /* renamed from: x */
    private float f109x;
    /* renamed from: y */
    private float f110y;

    public TiledMapImageLayer(TextureRegion region, float x, float y) {
        this.region = region;
        this.f109x = x;
        this.f110y = y;
    }

    public TextureRegion getTextureRegion() {
        return this.region;
    }

    public void setTextureRegion(TextureRegion region) {
        this.region = region;
    }

    public float getX() {
        return this.f109x;
    }

    public void setX(float x) {
        this.f109x = x;
    }

    public float getY() {
        return this.f110y;
    }

    public void setY(float y) {
        this.f110y = y;
    }
}
