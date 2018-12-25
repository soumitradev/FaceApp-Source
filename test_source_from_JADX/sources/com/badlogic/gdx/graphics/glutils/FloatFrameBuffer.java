package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.Texture$TextureWrap;

public class FloatFrameBuffer extends FrameBuffer {
    public FloatFrameBuffer(int width, int height, boolean hasDepth) {
        super(null, width, height, hasDepth);
    }

    protected void setupTexture() {
        this.colorTexture = new Texture(new FloatTextureData(this.width, this.height));
        if (Gdx.app.getType() != ApplicationType.Desktop) {
            if (Gdx.app.getType() != ApplicationType.Applet) {
                ((Texture) this.colorTexture).setFilter(Texture$TextureFilter.Nearest, Texture$TextureFilter.Nearest);
                ((Texture) this.colorTexture).setWrap(Texture$TextureWrap.ClampToEdge, Texture$TextureWrap.ClampToEdge);
            }
        }
        ((Texture) this.colorTexture).setFilter(Texture$TextureFilter.Linear, Texture$TextureFilter.Linear);
        ((Texture) this.colorTexture).setWrap(Texture$TextureWrap.ClampToEdge, Texture$TextureWrap.ClampToEdge);
    }
}
