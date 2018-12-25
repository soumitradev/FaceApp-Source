package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DecalMaterial {
    public static final int NO_BLEND = -1;
    protected int dstBlendFactor;
    protected int srcBlendFactor;
    protected TextureRegion textureRegion;

    public void set() {
        this.textureRegion.getTexture().bind();
        if (!isOpaque()) {
            Gdx.gl.glBlendFunc(this.srcBlendFactor, this.dstBlendFactor);
        }
    }

    public boolean isOpaque() {
        return this.srcBlendFactor == -1;
    }

    public int getSrcBlendFactor() {
        return this.srcBlendFactor;
    }

    public int getDstBlendFactor() {
        return this.dstBlendFactor;
    }

    public boolean equals(Object o) {
        boolean z = false;
        if (o == null) {
            return false;
        }
        DecalMaterial material = (DecalMaterial) o;
        if (this.dstBlendFactor == material.dstBlendFactor && this.srcBlendFactor == material.srcBlendFactor && this.textureRegion.getTexture() == material.textureRegion.getTexture()) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((((this.textureRegion.getTexture() != null ? this.textureRegion.getTexture().hashCode() : 0) * 31) + this.srcBlendFactor) * 31) + this.dstBlendFactor;
    }
}
